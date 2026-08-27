package com.alibaba.android.bindingx.core.internal;

import java.util.Collections;
import java.util.Map;

/* loaded from: classes.dex */
final class ExpressionHolder {
    Map<String, Object> config;
    String eventType;
    ExpressionPair expressionPair;
    String prop;
    String targetInstanceId;
    String targetRef;

    ExpressionHolder(String str, String str2, ExpressionPair expressionPair, String str3, String str4, Map<String, Object> map) {
        this.targetRef = str;
        this.targetInstanceId = str2;
        this.expressionPair = expressionPair;
        this.prop = str3;
        this.eventType = str4;
        if (map == null) {
            this.config = Collections.EMPTY_MAP;
        } else {
            this.config = Collections.unmodifiableMap(map);
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ExpressionHolder expressionHolder = (ExpressionHolder) obj;
            String str = this.targetRef;
            if (str == null ? expressionHolder.targetRef != null : !str.equals(expressionHolder.targetRef)) {
                return false;
            }
            ExpressionPair expressionPair = this.expressionPair;
            if (expressionPair == null ? expressionHolder.expressionPair != null : !expressionPair.equals(expressionHolder.expressionPair)) {
                return false;
            }
            String str2 = this.prop;
            if (str2 == null ? expressionHolder.prop != null : !str2.equals(expressionHolder.prop)) {
                return false;
            }
            String str3 = this.eventType;
            if (str3 == null ? expressionHolder.eventType != null : !str3.equals(expressionHolder.eventType)) {
                return false;
            }
            Map<String, Object> map = this.config;
            Map<String, Object> map2 = expressionHolder.config;
            if (map != null) {
                return map.equals(map2);
            }
            if (map2 == null) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        String str = this.targetRef;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        ExpressionPair expressionPair = this.expressionPair;
        int iHashCode2 = (iHashCode + (expressionPair != null ? expressionPair.hashCode() : 0)) * 31;
        String str2 = this.prop;
        int iHashCode3 = (iHashCode2 + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.eventType;
        int iHashCode4 = (iHashCode3 + (str3 != null ? str3.hashCode() : 0)) * 31;
        Map<String, Object> map = this.config;
        return iHashCode4 + (map != null ? map.hashCode() : 0);
    }
}
