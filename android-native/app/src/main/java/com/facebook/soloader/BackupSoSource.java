package com.facebook.soloader;

import android.content.Context;
import android.os.Parcel;
import com.facebook.soloader.ExtractFromZipSoSource;
import com.facebook.soloader.UnpackingSoSource;
import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;

/* loaded from: classes.dex */
public class BackupSoSource extends ExtractFromZipSoSource {
    private static final byte APK_SO_SOURCE_SIGNATURE_VERSION = 2;
    private static final byte LIBS_DIR_DOESNT_EXIST = 1;
    private static final byte LIBS_DIR_DONT_CARE = 0;
    private static final byte LIBS_DIR_SNAPSHOT = 2;
    public static final int PREFER_ANDROID_LIBS_DIRECTORY = 1;
    private static final String TAG = "BackupSoSource";
    private final int mFlags;

    public BackupSoSource(Context context, String str, int i) {
        this(context, new File(context.getApplicationInfo().sourceDir), str, i);
    }

    public BackupSoSource(Context context, File file, String str, int i) {
        super(context, str, file, "^lib/([^/]+)/([^/]+\\.so)$");
        this.mFlags = i;
    }

    @Override // com.facebook.soloader.ExtractFromZipSoSource, com.facebook.soloader.DirectorySoSource, com.facebook.soloader.SoSource
    public String getName() {
        return TAG;
    }

    public boolean hasZippedLibs() throws IOException {
        ApkUnpacker apkUnpacker = new ApkUnpacker(this, false);
        try {
            boolean z = apkUnpacker.computeDsosFromZip().length != 0;
            apkUnpacker.close();
            return z;
        } catch (Throwable th) {
            try {
                apkUnpacker.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.facebook.soloader.ExtractFromZipSoSource, com.facebook.soloader.UnpackingSoSource
    protected UnpackingSoSource.Unpacker makeUnpacker(boolean z) throws IOException {
        return new ApkUnpacker(this, z);
    }

    protected class ApkUnpacker extends ExtractFromZipSoSource.ZipUnpacker {
        private final int mFlags;
        private final boolean mForceUnpacking;
        private final File mLibDir;

        ApkUnpacker(ExtractFromZipSoSource extractFromZipSoSource, boolean z) throws IOException {
            super(extractFromZipSoSource);
            this.mForceUnpacking = z;
            this.mLibDir = new File(BackupSoSource.this.mContext.getApplicationInfo().nativeLibraryDir);
            this.mFlags = BackupSoSource.this.mFlags;
        }

        @Override // com.facebook.soloader.ExtractFromZipSoSource.ZipUnpacker
        protected ExtractFromZipSoSource.ZipDso[] getExtractableDsosFromZip() {
            if (this.mDsos != null) {
                return this.mDsos;
            }
            ExtractFromZipSoSource.ZipDso[] zipDsoArrComputeDsosFromZip = computeDsosFromZip();
            this.mDsos = zipDsoArrComputeDsosFromZip;
            if (this.mForceUnpacking) {
                LogUtil.w(BackupSoSource.TAG, "Unconditonally extracting all DSOs from zip");
                return this.mDsos;
            }
            if ((this.mFlags & 1) == 0) {
                LogUtil.w(BackupSoSource.TAG, "Self-extraction preferred (PREFER_ANDROID_LIBS_DRIECTORY not set)");
                return this.mDsos;
            }
            for (ExtractFromZipSoSource.ZipDso zipDso : zipDsoArrComputeDsosFromZip) {
                if (shouldExtract(zipDso.backingEntry, zipDso.name)) {
                    return this.mDsos;
                }
            }
            this.mDsos = new ExtractFromZipSoSource.ZipDso[0];
            return this.mDsos;
        }

        boolean shouldExtract(ZipEntry zipEntry, String str) {
            String name = zipEntry.getName();
            File file = new File(this.mLibDir, str);
            try {
                if (!file.getCanonicalPath().startsWith(this.mLibDir.getCanonicalPath())) {
                    LogUtil.d(BackupSoSource.TAG, "Not allowing consideration of " + name + ": " + str + " not in lib dir.");
                    return false;
                }
                if (!file.isFile()) {
                    LogUtil.w(BackupSoSource.TAG, "Allowing consideration of " + name + ": " + str + " not in system lib dir");
                    return true;
                }
                long length = file.length();
                long size = zipEntry.getSize();
                if (length == size) {
                    LogUtil.w(BackupSoSource.TAG, "Not allowing consideration of " + name + ": deferring to libdir");
                    return false;
                }
                LogUtil.w(BackupSoSource.TAG, "Allowing consideration of " + file + ": sysdir file length is " + length + ", but the file is " + size + " bytes long in the APK");
                return true;
            } catch (IOException e) {
                LogUtil.w(BackupSoSource.TAG, "Not allowing consideration of " + name + ": " + str + ", IOException when constructing path: " + e.toString());
                return false;
            }
        }
    }

    @Override // com.facebook.soloader.UnpackingSoSource
    protected byte[] getDepsBlock() throws IOException {
        File canonicalFile = this.mZipFileName.getCanonicalFile();
        Parcel parcelObtain = Parcel.obtain();
        try {
            parcelObtain.writeByte((byte) 2);
            parcelObtain.writeString(canonicalFile.getPath());
            parcelObtain.writeLong(canonicalFile.lastModified());
            parcelObtain.writeInt(SysUtil.getAppVersionCode(this.mContext));
            if ((this.mFlags & 1) == 0) {
                parcelObtain.writeByte((byte) 0);
                return parcelObtain.marshall();
            }
            String str = this.mContext.getApplicationInfo().nativeLibraryDir;
            if (str == null) {
                parcelObtain.writeByte((byte) 1);
                return parcelObtain.marshall();
            }
            File canonicalFile2 = new File(str).getCanonicalFile();
            if (!canonicalFile2.exists()) {
                parcelObtain.writeByte((byte) 1);
                return parcelObtain.marshall();
            }
            parcelObtain.writeByte((byte) 2);
            parcelObtain.writeString(canonicalFile2.getPath());
            parcelObtain.writeLong(canonicalFile2.lastModified());
            return parcelObtain.marshall();
        } finally {
            parcelObtain.recycle();
        }
    }
}
