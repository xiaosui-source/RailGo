# RailGo APK 反编译分析报告

## 1. 基本信息

| 项目 | 内容 |
|------|------|
| 应用名称 | RailGo（一个现代化的专业铁路查询工具） |
| APK 文件 | `RailGo_2.0.2 Build 20002.apk` |
| 包名 | `com.azstudio.railgo` |
| 版本 | `2.0.2 Build 20002` (versionCode=36) |
| SDK | compileSdk=35, targetSdk=28, minSdk=21 |
| 开发框架 | **DCloud UniApp（HBuilderX 5.0.7 / Vue3）**——混合 App |
| 架构 | 壳层（Java/Kotlin）+ 核心业务（HTML/JS，uniapp） |

## 2. 入口与架构

- **应用类**：`io.dcloud.application.DCloudApplication`
- **主入口 Activity**：`io.dcloud.PandoraEntry`（DCloud 标准 Pandora 入口）
- 应用核心业务为 **uniapp Vue3** 打包的 JS 代码，位于 APK 内：
  - `assets/apps/__UNI__1A91000/www/` 目录
  - 核心文件 `app-service.js`（约 628KB，业务逻辑）
  - `app-renderjs.js`（约 450KB，渲染逻辑）
  - `manifest.json`（应用配置）

## 3. 加固情况

- `assets/39285EFA.dex` 文件 **magic 头为 `cf 77 4c c7 9b 21 01 cd`**，不是标准 `dex\n0xx` 头，说明它被**加密（加壳）**处理，无法直接反编译（DCloud 官方加固或第三方加固）。
- 主 `classes.dex` 和 `classes2.dex` 为标准 dex，**已成功反编译**。
- 因此 Java 原生层（io.dcloud 框架、第三方库等）已完整反编译，而加密的核心 dex 无法还原。

## 4. Java 源码反编译成果（jadx）

共反编译出 **7053 个 Java 源文件**，主要结构：

```
sources/
├── android                # 兼容层
├── androidx               # AndroidX 支持库
├── com/azstudio/railgo    # 应用自身（壳）
│   ├── R.java
│   ├── BuildConfig.java
│   └── CustomTrustMgr.java  # 自定义SSL信任管理器(忽略证书校验)
├── com/bumptech/glide     # Glide 图片加载
├── com/facebook/soloader  # SoLoader
├── bolts / okhttp3 / okio # 第三方库
├── io/dcloud              # DCloud 5+/uniapp 运行时框架
│   ├── PandoraEntry.java / CandoraEntryActivity.java
│   ├── application/  api/  base/  common/  feature/  invocation/  js/  net/
│   ├── nineoldandroids/  sdk/  share/  uniapp/  unicloud/  uts/  weex/
├── kotlin / kotlinx       # Kotlin 标准库
├── net / org / pl / uts   # UT、工具库
└── ...
```

### 应用自身代码亮点
```java
// com.azstudio.railgo.CustomTrustMgr.java —— 自定义 SSL 信任管理器
public class CustomTrustMgr implements X509TrustManager {
    @Override public void checkServerTrusted(...) {}  // 空实现=信任所有证书
    @Override public void checkClientTrusted(...) {}
    @Override public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
}
```
说明应用实现了**绕过证书校验（信任所有 SSL 证书）**的逻辑，多用于调试、HTTPS 抓包或躲避证书校验。

## 5. uniapp 业务代码（页面结构）

核心业务页面位于 `pages/` 目录下：

| 目录 | 功能 |
|------|------|
| `404/` | 404 错误页、彩蛋(e)新年版 |
| `about/` | 关于、更新信息、会员、个性化、模式、协议、赞助 |
| `assignment/` | 车底配属查询(query/result) |
| `debug/` | 调试页 |
| `emu/` | EMU（动车组）信息/查询/结果 |
| `gallery/` | 相册查询/结果 |
| `index/` | 首页 |
| `oobe/` | 首次启动引导(授权/下载/模式/协议/欢迎) |
| `route/` | 线路(addRoute/route/routeDetail) |
| `simulate/` | 模拟驾驶(trainscreen) |
| `speed/` | 速度 |
| `station/` | 车站查询(/中转/车站选择) |
| `train/` | 车次(TrainPics) |
| `update/` | 更新 |

## 6. 后端 API（从 JS 业务代码提取）

应用使用自建后端服务（`*.zenglingkun.cn` / `railgo.dev`），主要接口：

```
https://data.railgo.zenglingkun.cn/api/station/query           # 车站查询
https://data.railgo.zenglingkun.cn/api/train/query             # 车次查询
https://data.railgo.zenglingkun.cn/api/train/preselect
https://delay.data.railgo.zenglingkun.cn/api/trainAssignment/queryEmu  # 晚点/配属
https://gateway.zenglingkun.cn/api/v2/service_endpoints        # 服务端点网关
https://api.bspapp.com / api.next.bspapp.com                   # uniCloud 后端
https://center.zenglingkun.cn/beta/api/check/                  # 更新检查
https://feedback.railgo.dev/api/get_users                      # 反馈
http://t0.tianditu.gov.cn/.../wmts                              # 天地图瓦片(电子地图)
https://mobile.12306.cn/.../navigation/listInfo                # 12306 数据
https://tongji.dcloud.io/uni/stat                              # uni统计
```

## 7. 权限清单（AndroidManifest 提取）

- 网络：`INTERNET`, `ACCESS_NETWORK_STATE`, `CHANGE_NETWORK_STATE`, `ACCESS_WIFI_STATE`
- 存储：`WRITE/READ_EXTERNAL_STORAGE`, `READ_MEDIA_IMAGES/VIDEO`
- 其他：`CAMERA`, `GET_ACCOUNTS`, `READ_PHONE_STATE`, `READ_LOGS`, `INSTALL_PACKAGES`, `REQUEST_INSTALL_PACKAGES`, `WRITE_SETTINGS`, `ACCESS_COARSE_LOCATION`, `VIBRATE`, `WAKE_LOCK`, `FLASHLIGHT`, 各厂商角标权限等。

## 8. 反编译产物（工作区 /workspace）目录

```
/workspace/
├── RailGo_decompiled_results.zip     # 反编译成果压缩包（46MB, 9192文件）
├── RailGo_jadx/                      # jadx Java 源码（sources/ + resources/ + AndroidManifest）
├── RailGo_decompiled/                # apktool 资源+smali（AndroidManifest.xml, res/, smali*/）
├── RailGo_unzip_assets/              # 从APK提取的 assets（uniapp业务代码）
│   └── assets/apps/__UNI__1A91000/www/
└── RailGo_unzip/                     # 提取的 classes*.dex 与加密dex(39285EFA.dex)
```

## 9. 加固机制与脱壳

`assets/39285EFA.dex` 的保护由 **DCloud 官方代码防混淆（VMP 指令流）加固** 提供，配套 native 解密器：

- `lib/arm64-v8a/lib39285EFA.so` —— 与加密 dex **同名 hash**，是核心 native 加固层
- 该 so hook 了 ART 内部函数（`OriRead`/`OriPread64`/`OriDvmRawDexFileOpenArray` 保存原指针），
  通过 `FdFile::Read/PreadFully` 拦截 dex 读取，用 `mprotect` 修改内存页、`CallMakeInMemoryDexElements` 内存加载，
  实现运行时解密与指令流 VM 执行。

### ✅ 脱壳过程（已成功）

加密文件实际为**简单 XOR 加密**，密钥为循环字节组 **`AB 12 34 CD`**：

1. 通过字节频率分析发现高频模式 `12 34 cd`、`ab 12 34 cd`
2. 与标准 dex 头部魔数 XOR 求得 key：`64 65 78 0a 30 33 35` (dex\n035) ⊕ `cf 77 4c c7 9b 21 01` = `ab 12 34 cd ab 12 34`
3. 用 key 对全文件 XOR 解密 → 得到标准 dex 035
4. **验证通过**：
   - `file_size` 与文件实际大小一致（71500）
   - `endian_tag 0x12345678` 正确
   - ADLER32 checksum 完全匹配
   - 各表（string/type/proto/field/method/class_defs）全部校验边界内，无越界

**脱壳产物**：`/workspace/39285EFA.decrypted.dex`

### 脱壳后的内容

恢复的 dex 实际是 **移动安全联盟（MSA）OAID 设备标识 SDK**（`com.bun.miitmdid`）、华硕 MSA、华为/三星/魅族/联想/OPPO/HeyTap 等各家厂商的 OAID/设备标识获取代码（69+ 个类，jadx 反编译干净无错误）。这与 App 需要获取设备匿名标识做统计/广告归因相关。

## 10. 结论

- **应用技术栈**：DCloud UniApp (Vue3) 混合开发，核心业务全在 JS 中，可作为可读的业务参考。
- **原生层反编译**：完整成功（7000+ Java 源文件），含 DCloud 5+/uniapp 运行时、OkHttp、Glide 等。
- **加固保护脱壳**：`assets/39285EFA.dex` 为 XOR(`AB1234CD`) 简单加密 → 已成功还原为有效 dex，实为 MSA OAID SDK。
- **注意**：应用实现了忽略证书校验的 CustomTrustMgr，且权限较宽（含 INSTALL、READ_LOGS 等）。
