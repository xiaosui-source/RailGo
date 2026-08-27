package com.nostra13.dcloudimageloader.cache.disc;

import com.nostra13.dcloudimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.dcloudimageloader.core.DefaultConfigurationFactory;
import com.taobao.weex.common.WXConfig;
import java.io.File;

/* loaded from: classes.dex */
public abstract class BaseDiscCache implements DiscCacheAware {
    private static final String ERROR_ARG_NULL = "\"%s\" argument must be not null";
    protected File cacheDir;
    private FileNameGenerator fileNameGenerator;

    public BaseDiscCache(File file) {
        this(file, DefaultConfigurationFactory.createFileNameGenerator());
    }

    public BaseDiscCache(File file, FileNameGenerator fileNameGenerator) {
        if (file == null) {
            throw new IllegalArgumentException(String.format(ERROR_ARG_NULL, WXConfig.cacheDir));
        }
        if (fileNameGenerator == null) {
            throw new IllegalArgumentException(String.format(ERROR_ARG_NULL, "fileNameGenerator"));
        }
        this.cacheDir = file;
        this.fileNameGenerator = fileNameGenerator;
    }

    @Override // com.nostra13.dcloudimageloader.cache.disc.DiscCacheAware
    public File get(String str) {
        return new File(this.cacheDir, this.fileNameGenerator.generate(str));
    }

    @Override // com.nostra13.dcloudimageloader.cache.disc.DiscCacheAware
    public void clear() {
        File[] fileArrListFiles = this.cacheDir.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                file.delete();
            }
        }
    }
}
