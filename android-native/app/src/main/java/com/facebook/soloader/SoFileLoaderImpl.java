package com.facebook.soloader;

import io.dcloud.common.util.Md5Utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class SoFileLoaderImpl implements SoFileLoader {
    private static final String TAG = "SoFileLoaderImpl";

    @Nullable
    private final String mLocalLdLibraryPath;

    @Nullable
    private final String mLocalLdLibraryPathNoZips;

    @Nullable
    private final Method mNativeLoadRuntimeMethod;
    private final Runtime mRuntime = Runtime.getRuntime();

    public SoFileLoaderImpl() throws NoSuchMethodException, SecurityException {
        Method nativeLoadRuntimeMethod = SysUtil.getNativeLoadRuntimeMethod();
        this.mNativeLoadRuntimeMethod = nativeLoadRuntimeMethod;
        String classLoaderLdLoadLibrary = nativeLoadRuntimeMethod != null ? SysUtil.getClassLoaderLdLoadLibrary() : null;
        this.mLocalLdLibraryPath = classLoaderLdLoadLibrary;
        this.mLocalLdLibraryPathNoZips = SysUtil.makeNonZipPath(classLoaderLdLoadLibrary);
    }

    @Override // com.facebook.soloader.SoFileLoader
    public void loadBytes(String str, ElfByteChannel elfByteChannel, int i) {
        throw new UnsupportedOperationException();
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:26:0x0082
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1178)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    @Override // com.facebook.soloader.SoFileLoader
    public void load(java.lang.String r10, int r11) {
        /*
            r9 = this;
            java.lang.String r0 = "nativeLoad() returned error for "
            java.lang.reflect.Method r1 = r9.mNativeLoadRuntimeMethod
            if (r1 != 0) goto La
            java.lang.System.load(r10)
            return
        La:
            r1 = 4
            r11 = r11 & r1
            if (r11 != r1) goto L11
            java.lang.String r11 = r9.mLocalLdLibraryPath
            goto L13
        L11:
            java.lang.String r11 = r9.mLocalLdLibraryPathNoZips
        L13:
            r1 = 0
            java.lang.Runtime r2 = r9.mRuntime     // Catch: java.lang.Throwable -> L85 java.lang.Throwable -> L87
            monitor-enter(r2)     // Catch: java.lang.Throwable -> L85 java.lang.Throwable -> L87
            java.lang.reflect.Method r3 = r9.mNativeLoadRuntimeMethod     // Catch: java.lang.Throwable -> L82
            java.lang.Runtime r4 = r9.mRuntime     // Catch: java.lang.Throwable -> L82
            java.lang.Class<com.facebook.soloader.SoLoader> r5 = com.facebook.soloader.SoLoader.class
            java.lang.ClassLoader r5 = r5.getClassLoader()     // Catch: java.lang.Throwable -> L82
            r6 = 3
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch: java.lang.Throwable -> L82
            r7 = 0
            r6[r7] = r10     // Catch: java.lang.Throwable -> L82
            r7 = 1
            r6[r7] = r5     // Catch: java.lang.Throwable -> L82
            r5 = 2
            r6[r5] = r11     // Catch: java.lang.Throwable -> L82
            java.lang.Object r3 = r3.invoke(r4, r6)     // Catch: java.lang.Throwable -> L82
            java.lang.String r3 = (java.lang.String) r3     // Catch: java.lang.Throwable -> L82
            if (r3 != 0) goto L63
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L60
            if (r3 == 0) goto L5f
            java.lang.String r0 = "SoFileLoaderImpl"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Error when loading library: "
            r1.<init>(r2)
            r1.append(r3)
            java.lang.String r2 = ", library hash is "
            r1.append(r2)
            java.lang.String r10 = r9.getLibHash(r10)
            r1.append(r10)
            java.lang.String r10 = ", LD_LIBRARY_PATH is "
            r1.append(r10)
            r1.append(r11)
            java.lang.String r10 = r1.toString()
            com.facebook.soloader.LogUtil.e(r0, r10)
        L5f:
            return
        L60:
            r0 = move-exception
            r1 = r3
            goto L83
        L63:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L60
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L60
            r1.append(r10)     // Catch: java.lang.Throwable -> L60
            java.lang.String r0 = ": "
            r1.append(r0)     // Catch: java.lang.Throwable -> L60
            r1.append(r3)     // Catch: java.lang.Throwable -> L60
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L60
            com.facebook.soloader.SoLoaderULError r1 = new com.facebook.soloader.SoLoaderULError     // Catch: java.lang.Throwable -> L7d
            r1.<init>(r10, r0)     // Catch: java.lang.Throwable -> L7d
            throw r1     // Catch: java.lang.Throwable -> L7d
        L7d:
            r1 = move-exception
            r8 = r1
            r1 = r0
            r0 = r8
            goto L83
        L82:
            r0 = move-exception
        L83:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L82
            throw r0     // Catch: java.lang.Throwable -> L85 java.lang.Throwable -> L87 java.lang.Throwable -> L87 java.lang.Throwable -> L87
        L85:
            r0 = move-exception
            goto La6
        L87:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L85
            r0.<init>()     // Catch: java.lang.Throwable -> L85
            java.lang.String r2 = "nativeLoad() error during invocation for "
            r0.append(r2)     // Catch: java.lang.Throwable -> L85
            r0.append(r10)     // Catch: java.lang.Throwable -> L85
            java.lang.String r2 = ": "
            r0.append(r2)     // Catch: java.lang.Throwable -> L85
            r0.append(r1)     // Catch: java.lang.Throwable -> L85
            java.lang.String r1 = r0.toString()     // Catch: java.lang.Throwable -> L85
            java.lang.RuntimeException r0 = new java.lang.RuntimeException     // Catch: java.lang.Throwable -> L85
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L85
            throw r0     // Catch: java.lang.Throwable -> L85
        La6:
            if (r1 == 0) goto Lcf
            java.lang.String r2 = "SoFileLoaderImpl"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "Error when loading library: "
            r3.<init>(r4)
            r3.append(r1)
            java.lang.String r1 = ", library hash is "
            r3.append(r1)
            java.lang.String r10 = r9.getLibHash(r10)
            r3.append(r10)
            java.lang.String r10 = ", LD_LIBRARY_PATH is "
            r3.append(r10)
            r3.append(r11)
            java.lang.String r10 = r3.toString()
            com.facebook.soloader.LogUtil.e(r2, r10)
        Lcf:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoFileLoaderImpl.load(java.lang.String, int):void");
    }

    private String getLibHash(String str) throws NoSuchAlgorithmException, IOException {
        try {
            File file = new File(str);
            MessageDigest messageDigest = MessageDigest.getInstance(Md5Utils.ALGORITHM);
            FileInputStream fileInputStream = new FileInputStream(file);
            try {
                byte[] bArr = new byte[4096];
                while (true) {
                    int i = fileInputStream.read(bArr);
                    if (i > 0) {
                        messageDigest.update(bArr, 0, i);
                    } else {
                        String str2 = String.format("%32x", new BigInteger(1, messageDigest.digest()));
                        fileInputStream.close();
                        return str2;
                    }
                }
            } catch (Throwable th) {
                try {
                    fileInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | SecurityException | NoSuchAlgorithmException e) {
            return e.toString();
        }
    }
}
