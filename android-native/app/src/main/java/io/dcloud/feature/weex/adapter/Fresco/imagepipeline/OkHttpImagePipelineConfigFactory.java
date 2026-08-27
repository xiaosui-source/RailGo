package io.dcloud.feature.weex.adapter.Fresco.imagepipeline;

import android.content.Context;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import dc.squareup.okhttp3.OkHttpClient;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class OkHttpImagePipelineConfigFactory {
    public static ImagePipelineConfig.Builder newBuilder(Context context, OkHttpClient okHttpClient) {
        return ImagePipelineConfig.newBuilder(context).setNetworkFetcher(new OkHttpNetworkFetcher(okHttpClient));
    }
}
