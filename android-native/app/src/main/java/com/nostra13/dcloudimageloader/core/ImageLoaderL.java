package com.nostra13.dcloudimageloader.core;

import com.nostra13.dcloudimageloader.core.assist.ImageLoadingListener;
import com.nostra13.dcloudimageloader.core.assist.SimpleImageLoadingListener;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class ImageLoaderL extends ImageLoader {
    private static volatile ImageLoaderL instance;
    private ImageLoaderConfiguration configuration;
    private final ImageLoadingListener emptyListener = new SimpleImageLoadingListener();
    private ImageLoaderEngine engine;

    protected ImageLoaderL() {
    }

    private void checkConfiguration() {
        if (this.configuration == null) {
            throw new IllegalStateException("ImageLoader must be init with configuration before using");
        }
    }

    public static ImageLoaderL getInstance() {
        if (instance == null) {
            synchronized (ImageLoaderL.class) {
                if (instance == null) {
                    instance = new ImageLoaderL();
                }
            }
        }
        return instance;
    }
}
