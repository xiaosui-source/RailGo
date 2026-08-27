<div align="center">

<a href="https://railgo.dev/">
    <img src="https://img.cdn.zenglingkun.cn/i/2025/08/19/uonfcl.png" width="200" height="200" alt="RailGo">
  </a>

# RailGo

_✨ 现代化的专业铁路信息查询工具✨_

<p align="center">
  <img src="https://img.shields.io/badge/Vue3-green" alt="Vue3">
  <img src="https://img.shields.io/badge/uniapp-pink" alt="uniapp">
  <a href="https://qm.qq.com/q/lGJBEj1t0A">
    <img src="https://img.shields.io/badge/RailGo官方交流群-yellow" alt="qq group">
  </a>
</p>

</div>

### 统计
![Star History Chart](https://api.star-history.com/svg?repos=RailGoApps/RailGo&type=Date)

---

## 🔬 逆向工程与脱壳成果（整合附加）

本仓库在官方前端源码基础上，附带了 RailGo APK 的**逆向分析与脱壳**成果，仅供学习研究。

### 目录结构

```
.
├── pages/ components/ api/ ...   # 官方 uni-app 前端源码
├── docs/                         # ★ 逆向分析 + 脱壳成果
│   ├── 反编译分析报告.md
│   └── 脱壳-oaid/                # OAID SDK (已脱壳还原)
└── android-native/               # ★ APK 壳层反编译 (jadx)
    └── app/src/main/
        ├── java/                 # DCloud 5+ 运行时等反编译类 (7053)
        ├── res/  assets/  jniLibs/
        └── AndroidManifest.xml
```

### 脱壳说明

APK 中的 `assets/39285EFA.dex` 由 DCloud 官方加壳保护，采用**循环密钥 XOR** 加密：

- **密钥**: `AB 12 34 CD`
- **算法**: `密文[i] = 明文[i] XOR KEY[i % 4]`

解密后为**移动安全联盟 (MSA) OAID 设备标识 SDK** (`com.bun.miitmdid`) ，
校验全部通过 ✅（checksum / signature / 表结构）。

脱壳脚本 `unpack_railgo.py` 见 [docs/脱壳-oaid/](docs/脱壳-oaid/)。

### ⚠️ 许可声明

- **前端源码** 遵循本仓库 `LICENSE`。
- **android-native/** 与 **docs/** 为 APK 逆向产物，DCloud 5+ 运行时与 OAID SDK 属
  第三方/商业组件，仅用于学习研究，请勿用于商业用途或侵权。

*逆向内容仅供学习研究，请遵守相关法律法规与软件许可协议。*
---

## 🤖 GitHub Actions 自动编译 (CI)

仓库已配置 [`.github/workflows/build-apk.yml`](.github/workflows/build-apk.yml)，基于
HBuilderX CLI 云端打包，每次 push/PR 或手动触发时自动生成 Android APK。

### 使用前提
在 GitHub 仓库 **Settings → Secrets and variables → Actions** 配置：

| Secret | 说明 |
|--------|------|
| `DCLOUD_USERNAME` | DCloud 开发者账号（邮箱） |
| `DCLOUD_PASSWORD` | DCloud 账号密码 |
| `DCLOUD_APPKEY` | HBuilderX「发行→原生App-云打包」获取的应用 Key |

### 触发方式
- **push** 到 `main` 分支
- **pull request** 到 `main`
- **手动**：Actions 页签 → "Build RailGo APK" → Run workflow

构建完成后，在对应 run 的 **Artifacts** 处下载 `RailGo-APK.zip`。

> 说明：uniapp 的 App 打包依赖 DCloud 云打包服务（在线授权），故采用 HBuilderX CLI
> 而非纯本地构建。如需完全本地 CI，需配置授权后的 Android 离线打包 SDK。
