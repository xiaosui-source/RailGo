package com.alibaba.android.bindingx.core.internal;

/* loaded from: classes.dex */
class OrientationEvaluator {
    private Double constraintAlpha;
    private Double constraintBeta;
    private Double constraintGamma;
    private Quaternion quaternion = new Quaternion(0.0d, 0.0d, 0.0d, 1.0d);
    private double constraintAlphaOffset = 0.0d;
    private double constraintBetaOffset = 0.0d;
    private double constraintGammaOffset = 0.0d;
    private final Vector3 ZEE = new Vector3(0.0d, 0.0d, 1.0d);
    private final Euler EULER = new Euler();
    private final Quaternion Q0 = new Quaternion();
    private final Quaternion Q1 = new Quaternion(-Math.sqrt(0.5d), 0.0d, 0.0d, Math.sqrt(0.5d));

    OrientationEvaluator(Double d, Double d2, Double d3) {
        this.constraintAlpha = null;
        this.constraintBeta = null;
        this.constraintGamma = null;
        this.constraintAlpha = d;
        this.constraintBeta = d2;
        this.constraintGamma = d3;
    }

    Quaternion calculate(double d, double d2, double d3, double d4) {
        Double d5 = this.constraintAlpha;
        double radians = Math.toRadians(d5 != null ? d5.doubleValue() : d4 + this.constraintAlphaOffset);
        Double d6 = this.constraintBeta;
        double radians2 = Math.toRadians(d6 != null ? d6.doubleValue() : this.constraintBetaOffset + d2);
        Double d7 = this.constraintGamma;
        setObjectQuaternion(this.quaternion, radians, radians2, Math.toRadians(d7 != null ? d7.doubleValue() : this.constraintGammaOffset + d3), 0.0d);
        return this.quaternion;
    }

    private void setObjectQuaternion(Quaternion quaternion, double d, double d2, double d3, double d4) {
        this.EULER.setValue(d2, d, -d3, "YXZ");
        quaternion.setFromEuler(this.EULER);
        quaternion.multiply(this.Q1);
        quaternion.multiply(this.Q0.setFromAxisAngle(this.ZEE, -d4));
    }
}
