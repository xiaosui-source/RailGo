#!/usr/bin/env python3
"""
RailGo assets/39285EFA.dex 脱壳脚本

加壳方式: 循环密钥 XOR 加密, 密钥 = AB 12 34 CD

用法:
    python3 unpack_railgo.py <加密dex> [输出dex]

示例:
    python3 unpack_railgo.py assets/39285EFA.dex 39285EFA.decrypted.dex
"""
import sys
import struct
import zlib
import hashlib

KEY = bytes.fromhex("AB 12 34 CD")  # 循环 XOR 密钥


def unpack(input_path, output_path):
    with open(input_path, "rb") as f:
        enc = f.read()

    # 对每个字节与循环 key 异或解密
    dec = bytes(b ^ KEY[i % len(KEY)] for i, b in enumerate(enc))

    # ===== 完整性校验 =====
    ok = True
    if dec[:4] != b"dex\n":
        print("[!] 警告: 解密后不是 dex 魔数")
        ok = False
    else:
        print("[+] magic: %s" % dec[:8].decode("latin1"))

    declared_ck = struct.unpack("<I", dec[8:12])[0]
    real_ck = zlib.adler32(dec[12:]) & 0xFFFFFFFF
    print("[+] checksum 匹配: %s" % (declared_ck == real_ck))
    ok = ok and (declared_ck == real_ck)

    declared_sig = dec[12:32]
    real_sig = hashlib.sha1(dec[32:]).digest()
    print("[+] signature 匹配: %s" % (declared_sig == real_sig))
    ok = ok and (declared_sig == real_sig)

    file_size = struct.unpack("<I", dec[32:36])[0]
    print("[+] file_size: %d (实际 %d)" % (file_size, len(dec)))

    with open(output_path, "wb") as f:
        f.write(dec)
    print("[+] 已写出: %s (%d bytes)" % (output_path, len(dec)))

    if ok:
        print("[!] 脱壳成功, 解密后的 dex 有效且校验通过")
    else:
        print("[!] 校验未完全通过, 请检查密钥或加密方式")
    return ok


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print(__doc__)
        sys.exit(1)
    inp = sys.argv[1]
    out = sys.argv[2] if len(sys.argv) > 2 else "decrypted.dex"
    sys.exit(0 if unpack(inp, out) else 1)