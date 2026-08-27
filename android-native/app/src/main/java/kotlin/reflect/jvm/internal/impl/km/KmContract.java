package kotlin.reflect.jvm.internal.impl.km;

import java.util.ArrayList;
import java.util.List;

/* compiled from: Contracts.kt */
/* loaded from: classes2.dex */
public final class KmContract {
    private final List<KmEffect> effects = new ArrayList(1);

    public final List<KmEffect> getEffects() {
        return this.effects;
    }
}
