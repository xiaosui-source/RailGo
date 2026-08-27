package io.dcloud.uts.gson.internal.reflect;

import java.lang.reflect.AccessibleObject;

/* loaded from: classes2.dex */
final class PreJava9ReflectionAccessor extends ReflectionAccessor {
    PreJava9ReflectionAccessor() {
    }

    @Override // io.dcloud.uts.gson.internal.reflect.ReflectionAccessor
    public void makeAccessible(AccessibleObject accessibleObject) throws SecurityException {
        accessibleObject.setAccessible(true);
    }
}
