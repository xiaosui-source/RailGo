package io.dcloud.common.ui.Info;

import io.dcloud.common.DHInterface.IReflectAble;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class AndroidPrivacyResponse implements IReflectAble {
    public String buttonAccept;
    public String buttonRefuse;
    public String message;
    public String prompt;
    public String title;
    public String version;
    public String hrefLoader = "default";
    public boolean backToExit = false;
    public SecondDTO second = new SecondDTO();
    public StylesDTO styles = new StylesDTO();
    public disagreeModeDTO disagreeMode = new disagreeModeDTO();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class SecondDTO implements IReflectAble {
        public String buttonAccept;
        public String buttonRefuse;
        public String message;
        public String title;
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class StylesDTO implements IReflectAble {
        public String backgroundColor;
        public String borderRadius;
        public ButtonAcceptDTO buttonAccept;
        public ButtonRefuseDTO buttonRefuse;
        public ButtonRefuseDTO buttonVisitor;
        public ContentDTO content;
        public TitleDTO title;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        public static class ButtonAcceptDTO implements IReflectAble {
            public String color;
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        public static class ButtonRefuseDTO implements IReflectAble {
            public String color;
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        public static class ContentDTO implements IReflectAble {
            public String color;
        }

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        public static class TitleDTO implements IReflectAble {
            public String color;
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static class disagreeModeDTO implements IReflectAble {
        public boolean support = false;
        public boolean loadNativePlugins = true;
        public boolean visitorEntry = false;
        public boolean showAlways = false;
    }
}
