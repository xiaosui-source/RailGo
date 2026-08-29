#!/bin/bash
set -e
echo "=== RailGo 全源码编译 + 离线打包（和云打包一致）==="

WORKSPACE="${GITHUB_WORKSPACE:-$(pwd)}"
PKG="com.azstudio.railgo"
APPID="__UNI__1A91000"
APPKEY="848cdaafa5cfc97987dba6641f7cb403"

# ============ 1. 获取官方源码 ============
echo "克隆官方源码 RailGoApps/RailGo..."
SRC_DIR="/tmp/railgo-src"
rm -rf "$SRC_DIR"
git clone --depth 1 https://github.com/RailGoApps/RailGo.git "$SRC_DIR"
cd "$SRC_DIR"

# ============ 2. 配置 uniapp CLI 环境 ============
echo "配置 uniapp CLI 编译环境..."
V="3.0.0-alpha-5020520260824002"
# 备份并重写 package.json (保留原有dependencies, 增加uni编译devDeps/scripts)
python3 - <<PYEOF
import json, os
p='package.json'
d=json.load(open(p))
d.setdefault('devDependencies',{})['@dcloudio/vite-plugin-uni']='$V'
d['devDependencies']['vite']='^5.2.0'
d['scripts']={'build:app':'uni build -p app'}
json.dump(d, open(p,'w'), indent=2)
print('package.json 已配置')
PYEOF

# 创建 vite.config.js
cat > vite.config.js <<'EOF'
import { defineConfig } from 'vite'
import uni from '@dcloudio/vite-plugin-uni'
export default defineConfig({ plugins: [uni()] })
EOF

# 调整源码到 src/（uniapp CLI 布局）: manifest/app/vue/pages 需在 src 下
mkdir -p src
for f in App.vue main.js pages.json manifest.json uni.scss index.html uni.promisify.adaptor.js; do
  [ -f "$f" ] && mv "$f" src/
done
for d in pages components static uni_modules js_sdk api scripts; do
  [ -d "$d" ] && mv "$d" src/
done
echo "src/ 布局: $(ls src/)"

# 安装依赖
echo "安装依赖 (uniapp CLI)..."
npm install --legacy-peer-deps 2>&1 | tail -5

# ============ 3. 编译生成 www ============
echo "uniapp CLI 编译 App 资源..."
# patch os.networkInterfaces 防沙箱崩溃
cat > patch.js <<'EOF'
const os=require('os');const o=os.networkInterfaces;
os.networkInterfaces=function(){try{return o.call(os)}catch(e){return{eth0:[{family:'IPv4',mac:'02:42:ac:11:00:02',address:'127.0.0.1'}]}}};
EOF
timeout 300 node --require ./patch.js ./node_modules/@dcloudio/vite-plugin-uni/dist/cli/index.js build -p app 2>&1 | tail -20 || echo "编译可能有警告"
WWW="/tmp/railgo-src/dist/build/app"
if [ ! -d "$WWW" ]; then
  echo "::error::www 编译失败"; ls dist/build/app 2>/dev/null; exit 1
fi
echo "www 文件数: $(find "$WWW" -type f | wc -l)"

# ============ 4. 下载离线打包 SDK ============
SDK_ZIP="/tmp/Android-SDK-5.24.zip"
SDK_URL="https://github.com/xiaosui-source/RailGo/releases/download/sdk-5.24-v1/Android-SDK-5.24.zip"
if [ ! -f "$SDK_ZIP" ]; then
  echo "下载离线SDK (293MB)..."
  curl -L -o "$SDK_ZIP" "$SDK_URL" --retry 3
fi
rm -rf /tmp/sdk && mkdir -p /tmp/sdk
unzip -q -o "$SDK_ZIP" -d /tmp/sdk
SDK_ROOT=$(find /tmp/sdk -maxdepth 1 -type d -name "Android-SDK*" | head -1)

# ============ 5. 搭建离线打包工程 ============
PROJ="/tmp/railgo-project"
rm -rf "$PROJ"
cp -r "$SDK_ROOT/HBuilder-Integrate-AS" "$PROJ"
MODULE="$PROJ/simpleDemo"

# 放入编译的 www
echo "放入 www 到 assets/apps/$APPID/www..."
rm -rf "$MODULE/src/main/assets/apps/__UNI__A"
mkdir -p "$MODULE/src/main/assets/apps/$APPID/www"
cp -r "$WWW"/* "$MODULE/src/main/assets/apps/$APPID/www/"
echo "www 文件数: $(find "$MODULE/src/main/assets/apps/$APPID/www" -type f | wc -l)"

# 配置 manifest appkey
MANIFEST="$MODULE/src/main/AndroidManifest.xml"
sed -i "s|开发者需登录https://dev.dcloud.net.cn/申请签名|$APPKEY|g" "$MANIFEST"

# 配置 build.gradle（严格原版:包名/版本/SDK）
GRADLE="$MODULE/build.gradle"
sed -i "s|namespace 'com.android.simple'|namespace '$PKG'|" "$GRADLE"
sed -i "s|applicationId \"com.android.simple\"|applicationId \"$PKG\"|" "$GRADLE"
sed -i 's|versionCode 1|versionCode 36|' "$GRADLE"
sed -i 's|versionName "1.0"|versionName "2.0.2 Build 20002"|' "$GRADLE"
sed -i 's|minSdkVersion [0-9][0-9]*|minSdkVersion 21|' "$GRADLE"
sed -i 's|targetSdkVersion [0-9][0-9]*|targetSdkVersion 28|' "$GRADLE"

# 应用名和图标（原版）
STRINGS="$MODULE/src/main/res/values/strings.xml"
[ -f "$STRINGS" ] && sed -i 's|<string name="app_name">.*</string>|<string name="app_name">RailGo</string>|' "$STRINGS"
ICON_DIR="$WORKSPACE/offline-pkg/icon"
for d in "$MODULE/src/main/res/drawable" "$MODULE/src/main/res/drawable-hdpi"; do
  [ -d "$d" ] && cp "$ICON_DIR/railgo_icon_72.png" "$d/icon.png" 2>/dev/null || true
done

echo "sdk.dir=${ANDROID_HOME:-/usr/local/lib/android/sdk}" > "$PROJ/local.properties"

# ============ 6. Gradle 编译 ============
cd "$PROJ"
echo "=== Gradle 编译 APK ==="
chmod +x gradlew 2>/dev/null || true
if [ -x gradlew ]; then
  ./gradlew :simpleDemo:assembleRelease --no-daemon 2>&1 | tail -50
else
  gradle :simpleDemo:assembleRelease --no-daemon 2>&1 | tail -50
fi

# ============ 7. 收集 APK ============
APK=$(find "$PROJ" -name "*.apk" 2>/dev/null | head -1)
if [ -n "$APK" ]; then
  echo "=== APK 编译成功 ==="
  cp "$APK" "$WORKSPACE/RailGo.apk"
  ls -lh "$WORKSPACE/RailGo.apk"
else
  echo "=== 未找到 APK ==="
  find "$PROJ" -name "*.apk" 2>/dev/null
  exit 1
fi
