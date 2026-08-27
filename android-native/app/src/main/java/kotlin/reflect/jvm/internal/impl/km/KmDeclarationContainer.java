package kotlin.reflect.jvm.internal.impl.km;

import java.util.List;

/* compiled from: Nodes.kt */
/* loaded from: classes2.dex */
public interface KmDeclarationContainer {
    List<KmFunction> getFunctions();

    List<KmProperty> getProperties();

    List<KmTypeAlias> getTypeAliases();
}
