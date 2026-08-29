#!/bin/bash
set -e

echo "=== RailGo 离线打包（和云打包一致）==="
WWW_DIR="$(cd "$(dirname "$0")/.." && pwd)/www"
APPID="__UNI__1A91000"
APPKEY="848cdaafa5cfc97987dba6641f7cb403"
PKG="com.azstudio.railgo"

# 1. 下载离线SDK（从GitHub Release）
SDK_ZIP="/tmp/Android-SDK-5.24.zip"
SDK_URL="https://github.com/xiaosui-source/RailGo/releases/download/sdk-5.24-v1/Android-SDK-5.24.zip"
if [ ! -f "$SDK_ZIP" ]; then
  echo "下载离线SDK (293MB)..."
  curl -L -o "$SDK_ZIP" "$SDK_URL" --retry 3
fi
echo "解压SDK..."
rm -rf /tmp/sdk && mkdir -p /tmp/sdk
unzip -q -o "$SDK_ZIP" -d /tmp/sdk
SDK_ROOT=$(find /tmp/sdk -maxdepth 1 -type d -name "Android-SDK*" | head -1)
echo "SDK目录: $SDK_ROOT"

# 2. 用整个 HBuilder-Integrate-AS 作为项目根
PROJ="/tmp/railgo-project"
rm -rf "$PROJ"
cp -r "$SDK_ROOT/HBuilder-Integrate-AS" "$PROJ"
echo "项目根: $PROJ"
ls "$PROJ"

# 3. simpleDemo 模块就是 app
MODULE="$PROJ/simpleDemo"
echo "模块目录: $MODULE"

# 4. 复制SDK运行时到模块
echo "复制SDK运行时(libs/res/src)..."
mkdir -p "$MODULE/libs"
cp "$SDK_ROOT/SDK/libs/"*.aar "$MODULE/libs/" 2>/dev/null || true
cp "$SDK_ROOT/SDK/libs/"*.jar "$MODULE/libs/" 2>/dev/null || true
cp -r "$SDK_ROOT/SDK/res/." "$MODULE/src/main/res/" 2>/dev/null || true
cp -r "$SDK_ROOT/SDK/src/." "$MODULE/src/main/java/" 2>/dev/null || true
cp "$SDK_ROOT/SDK/proguard.cfg" "$MODULE/proguard-rules.pro" 2>/dev/null || true

# 5. 放入www应用资源
echo "放入编译好的www资源..."
rm -rf "$MODULE/src/main/assets/apps/__UNI__A"
mkdir -p "$MODULE/src/main/assets/apps/$APPID/www"
cp -r "$WWW_DIR"/* "$MODULE/src/main/assets/apps/$APPID/www/"
echo "www文件数: $(find "$MODULE/src/main/assets/apps/$APPID/www" -type f | wc -l)"

# 6. 配置AndroidManifest（appkey）
MANIFEST="$MODULE/src/main/AndroidManifest.xml"
sed -i "s|开发者需登录https://dev.dcloud.net.cn/申请签名|$APPKEY|g" "$MANIFEST"
echo "appkey已配置"

# 7. 配置build.gradle（包名/版本）
GRADLE="$MODULE/build.gradle"
sed -i "s|namespace 'com.android.simple'|namespace '$PKG'|" "$GRADLE"
sed -i "s|applicationId \"com.android.simple\"|applicationId \"$PKG\"|" "$GRADLE"
sed -i 's|versionCode 1|versionCode 20004|' "$GRADLE"
sed -i 's|versionName "1.0"|versionName "2.0.4"|' "$GRADLE"
echo "build.gradle已配置"

# 8. 配置应用名
STRINGS="$MODULE/src/main/res/values/strings.xml"
if [ -f "$STRINGS" ]; then
  sed -i 's|<string name="app_name">.*</string>|<string name="app_name">RailGo 铁路行</string>|' "$STRINGS"
fi

# 9. local.properties
echo "sdk.dir=${ANDROID_HOME:-/usr/local/lib/android/sdk}" > "$PROJ/local.properties"

# 10. Gradle编译
cd "$PROJ"
echo "=== Gradle 编译 APK ==="
chmod +x gradlew 2>/dev/null || true
if [ -f "gradlew" ]; then
  ./gradlew :simpleDemo:assembleRelease --no-daemon 2>&1 | tail -60
else
  gradle :simpleDemo:assembleRelease --no-daemon 2>&1 | tail -60
fi

# 11. 收集APK
APK=$(find "$PROJ" -name "*.apk" 2>/dev/null | head -1)
if [ -n "$APK" ]; then
  echo "=== APK编译成功 ==="
  cp "$APK" "${GITHUB_WORKSPACE:-$(pwd)}/RailGo-2.0.4.apk"
  ls -lh "${GITHUB_WORKSPACE:-$(pwd)}/RailGo-2.0.4.apk"
else
  echo "=== 未找到APK ==="
  find "$PROJ" -name "*.apk" 2>/dev/null
  exit 1
fi
