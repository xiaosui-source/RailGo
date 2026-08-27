package com.facebook.soloader.recovery;

import com.facebook.soloader.AsyncInitSoSource;
import com.facebook.soloader.LogUtil;
import com.facebook.soloader.SoLoader;
import com.facebook.soloader.SoLoaderULError;
import com.facebook.soloader.SoSource;
import com.facebook.soloader.UnpackingSoSource;

/* loaded from: classes.dex */
public class WaitForAsyncInit implements RecoveryStrategy {
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.facebook.soloader.recovery.RecoveryStrategy
    public boolean recover(UnsatisfiedLinkError unsatisfiedLinkError, SoSource[] soSourceArr) {
        String soName = unsatisfiedLinkError instanceof SoLoaderULError ? ((SoLoaderULError) unsatisfiedLinkError).getSoName() : null;
        StringBuilder sb = new StringBuilder("Waiting on SoSources due to ");
        sb.append(unsatisfiedLinkError);
        sb.append(soName == null ? "" : ", retrying for specific library " + soName);
        LogUtil.e(SoLoader.TAG, sb.toString());
        for (UnpackingSoSource unpackingSoSource : soSourceArr) {
            if (unpackingSoSource instanceof AsyncInitSoSource) {
                LogUtil.e(SoLoader.TAG, "Waiting on SoSource " + unpackingSoSource.getName());
                unpackingSoSource.waitUntilInitCompleted();
            }
        }
        return true;
    }
}
