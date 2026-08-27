package io.dcloud.uts.gson.internal.reflect;

import io.dcloud.uts.gson.internal.JavaVersion;
import java.lang.reflect.AccessibleObject;

/* loaded from: classes2.dex */
public abstract class ReflectionAccessor {
    private static final ReflectionAccessor instance;

    public abstract void makeAccessible(AccessibleObject accessibleObject);

    static {
        instance = JavaVersion.getMajorJavaVersion() < 9 ? new PreJava9ReflectionAccessor() : new UnsafeReflectionAccessor();
    }

    public static ReflectionAccessor getInstance() {
        return instance;
    }
}
