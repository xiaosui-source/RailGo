package com.alibaba.android.bindingx.core.internal;

import android.text.TextUtils;

/* loaded from: classes.dex */
public class ExpressionPair {
    public final String origin;
    public final String transformed;

    public ExpressionPair(String str, String str2) {
        this.origin = str;
        this.transformed = str2;
    }

    public static ExpressionPair create(String str, String str2) {
        return new ExpressionPair(str, str2);
    }

    public static boolean isValid(ExpressionPair expressionPair) {
        return (expressionPair == null || TextUtils.isEmpty(expressionPair.transformed) || "{}".equals(expressionPair.transformed)) ? false : true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ExpressionPair expressionPair = (ExpressionPair) obj;
            String str = this.origin;
            if (str == null ? expressionPair.origin != null : !str.equals(expressionPair.origin)) {
                return false;
            }
            String str2 = this.transformed;
            String str3 = expressionPair.transformed;
            if (str2 != null) {
                return str2.equals(str3);
            }
            if (str3 == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String str = this.origin;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.transformed;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }
}
