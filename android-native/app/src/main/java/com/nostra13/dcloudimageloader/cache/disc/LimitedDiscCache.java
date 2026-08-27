package com.nostra13.dcloudimageloader.cache.disc;

import com.nostra13.dcloudimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.dcloudimageloader.core.DefaultConfigurationFactory;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public abstract class LimitedDiscCache extends BaseDiscCache {
    private static final int INVALID_SIZE = -1;
    private final AtomicInteger cacheSize;
    private final Map<File, Long> lastUsageDates;
    private final int sizeLimit;

    protected abstract int getSize(File file);

    public LimitedDiscCache(File file, int i) {
        this(file, DefaultConfigurationFactory.createFileNameGenerator(), i);
    }

    public LimitedDiscCache(File file, FileNameGenerator fileNameGenerator, int i) {
        super(file, fileNameGenerator);
        this.lastUsageDates = Collections.synchronizedMap(new HashMap());
        this.sizeLimit = i;
        this.cacheSize = new AtomicInteger();
        calculateCacheSizeAndFillUsageMap();
    }

    private void calculateCacheSizeAndFillUsageMap() {
        new Thread(new Runnable() { // from class: com.nostra13.dcloudimageloader.cache.disc.LimitedDiscCache.1
            @Override // java.lang.Runnable
            public void run() {
                File[] fileArrListFiles = LimitedDiscCache.this.cacheDir.listFiles();
                if (fileArrListFiles != null) {
                    int size = 0;
                    for (File file : fileArrListFiles) {
                        size += LimitedDiscCache.this.getSize(file);
                        LimitedDiscCache.this.lastUsageDates.put(file, Long.valueOf(file.lastModified()));
                    }
                    LimitedDiscCache.this.cacheSize.set(size);
                }
            }
        }).start();
    }

    @Override // com.nostra13.dcloudimageloader.cache.disc.DiscCacheAware
    public void put(String str, File file) {
        int iRemoveNext;
        int size = getSize(file);
        int iAddAndGet = this.cacheSize.get();
        while (iAddAndGet + size > this.sizeLimit && (iRemoveNext = removeNext()) != -1) {
            iAddAndGet = this.cacheSize.addAndGet(-iRemoveNext);
        }
        this.cacheSize.addAndGet(size);
        long jCurrentTimeMillis = System.currentTimeMillis();
        Long lValueOf = Long.valueOf(jCurrentTimeMillis);
        lValueOf.getClass();
        file.setLastModified(jCurrentTimeMillis);
        this.lastUsageDates.put(file, lValueOf);
    }

    @Override // com.nostra13.dcloudimageloader.cache.disc.BaseDiscCache, com.nostra13.dcloudimageloader.cache.disc.DiscCacheAware
    public File get(String str) {
        File file = super.get(str);
        long jCurrentTimeMillis = System.currentTimeMillis();
        Long lValueOf = Long.valueOf(jCurrentTimeMillis);
        lValueOf.getClass();
        file.setLastModified(jCurrentTimeMillis);
        this.lastUsageDates.put(file, lValueOf);
        return file;
    }

    @Override // com.nostra13.dcloudimageloader.cache.disc.BaseDiscCache, com.nostra13.dcloudimageloader.cache.disc.DiscCacheAware
    public void clear() {
        this.lastUsageDates.clear();
        this.cacheSize.set(0);
        super.clear();
    }

    private int removeNext() {
        File key;
        if (this.lastUsageDates.isEmpty()) {
            return -1;
        }
        Set<Map.Entry<File, Long>> setEntrySet = this.lastUsageDates.entrySet();
        synchronized (this.lastUsageDates) {
            key = null;
            Long value = null;
            for (Map.Entry<File, Long> entry : setEntrySet) {
                if (key == null) {
                    key = entry.getKey();
                    value = entry.getValue();
                } else {
                    Long value2 = entry.getValue();
                    if (value2.longValue() < value.longValue()) {
                        key = entry.getKey();
                        value = value2;
                    }
                }
            }
        }
        if (key != null) {
            if (key.exists()) {
                int size = getSize(key);
                if (key.delete()) {
                    this.lastUsageDates.remove(key);
                }
                return size;
            }
            this.lastUsageDates.remove(key);
        }
        return 0;
    }
}
