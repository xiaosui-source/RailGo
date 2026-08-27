package io.dcloud.sdk.core.entry;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
@Deprecated
/* loaded from: classes2.dex */
public class SplashAOLConfig {
    private int a;
    private int b;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static final class Builder {
        private int a;
        private int b;

        public SplashAOLConfig build() {
            return new SplashAOLConfig(this);
        }

        public Builder height(int i) {
            this.b = i;
            return this;
        }

        public Builder width(int i) {
            this.a = i;
            return this;
        }
    }

    public int getHeight() {
        return this.b;
    }

    public int getWidth() {
        return this.a;
    }

    private SplashAOLConfig(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
    }
}
