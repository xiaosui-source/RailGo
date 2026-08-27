package com.alibaba.android.bindingx.core.internal;

import com.taobao.weex.el.parse.Operators;

/* loaded from: classes.dex */
class Quaternion {
    double w;
    double x;
    double y;
    double z;

    Quaternion() {
    }

    Quaternion(double d, double d2, double d3, double d4) {
        this.x = d;
        this.y = d2;
        this.z = d3;
        this.w = d4;
    }

    Quaternion setFromEuler(Euler euler) {
        if (euler == null || !euler.isEuler) {
            return null;
        }
        double dCos = Math.cos(euler.x / 2.0d);
        double dCos2 = Math.cos(euler.y / 2.0d);
        double dCos3 = Math.cos(euler.z / 2.0d);
        double dSin = Math.sin(euler.x / 2.0d);
        double dSin2 = Math.sin(euler.y / 2.0d);
        double dSin3 = Math.sin(euler.z / 2.0d);
        String str = euler.order;
        if ("XYZ".equals(str)) {
            double d = dSin * dCos2;
            double d2 = dCos * dSin2;
            this.x = (d * dCos3) + (d2 * dSin3);
            this.y = (d2 * dCos3) - (d * dSin3);
            double d3 = dCos * dCos2;
            double d4 = dSin * dSin2;
            this.z = (d3 * dSin3) + (d4 * dCos3);
            this.w = (d3 * dCos3) - (d4 * dSin3);
            return this;
        }
        if ("YXZ".equals(str)) {
            double d5 = dSin * dCos2;
            double d6 = dCos * dSin2;
            this.x = (d5 * dCos3) + (d6 * dSin3);
            this.y = (d6 * dCos3) - (d5 * dSin3);
            double d7 = dCos * dCos2;
            double d8 = dSin * dSin2;
            this.z = (d7 * dSin3) - (d8 * dCos3);
            this.w = (d7 * dCos3) + (d8 * dSin3);
            return this;
        }
        if ("ZXY".equals(str)) {
            double d9 = dSin * dCos2;
            double d10 = dCos * dSin2;
            this.x = (d9 * dCos3) - (d10 * dSin3);
            this.y = (d10 * dCos3) + (d9 * dSin3);
            double d11 = dCos * dCos2;
            double d12 = dSin * dSin2;
            this.z = (d11 * dSin3) + (d12 * dCos3);
            this.w = (d11 * dCos3) - (d12 * dSin3);
            return this;
        }
        if ("ZYX".equals(str)) {
            double d13 = dSin * dCos2;
            double d14 = dCos * dSin2;
            this.x = (d13 * dCos3) - (d14 * dSin3);
            this.y = (d14 * dCos3) + (d13 * dSin3);
            double d15 = dCos * dCos2;
            double d16 = dSin * dSin2;
            this.z = (d15 * dSin3) - (d16 * dCos3);
            this.w = (d15 * dCos3) + (d16 * dSin3);
            return this;
        }
        if ("YZX".equals(str)) {
            double d17 = dSin * dCos2;
            double d18 = dCos * dSin2;
            this.x = (d17 * dCos3) + (d18 * dSin3);
            this.y = (d18 * dCos3) + (d17 * dSin3);
            double d19 = dCos * dCos2;
            double d20 = dSin * dSin2;
            this.z = (d19 * dSin3) - (d20 * dCos3);
            this.w = (d19 * dCos3) - (d20 * dSin3);
            return this;
        }
        if ("XZY".equals(str)) {
            double d21 = dSin * dCos2;
            double d22 = dCos * dSin2;
            this.x = (d21 * dCos3) - (d22 * dSin3);
            this.y = (d22 * dCos3) - (d21 * dSin3);
            double d23 = dCos * dCos2;
            double d24 = dSin * dSin2;
            this.z = (d23 * dSin3) + (d24 * dCos3);
            this.w = (d23 * dCos3) + (d24 * dSin3);
        }
        return this;
    }

    Quaternion setFromAxisAngle(Vector3 vector3, double d) {
        double d2 = d / 2.0d;
        double dSin = Math.sin(d2);
        this.x = vector3.x * dSin;
        this.y = vector3.y * dSin;
        this.z = vector3.z * dSin;
        this.w = Math.cos(d2);
        return this;
    }

    Quaternion multiply(Quaternion quaternion) {
        return multiplyQuaternions(this, quaternion);
    }

    Quaternion multiplyQuaternions(Quaternion quaternion, Quaternion quaternion2) {
        double d = quaternion.x;
        double d2 = quaternion.y;
        double d3 = quaternion.z;
        double d4 = quaternion.w;
        double d5 = quaternion2.x;
        double d6 = quaternion2.y;
        double d7 = quaternion2.z;
        double d8 = quaternion2.w;
        this.x = (((d * d8) + (d4 * d5)) + (d2 * d7)) - (d3 * d6);
        this.y = (((d2 * d8) + (d4 * d6)) + (d3 * d5)) - (d * d7);
        this.z = (((d3 * d8) + (d4 * d7)) + (d * d6)) - (d2 * d5);
        this.w = (((d4 * d8) - (d * d5)) - (d2 * d6)) - (d3 * d7);
        return this;
    }

    public String toString() {
        return "Quaternion{x=" + this.x + ", y=" + this.y + ", z=" + this.z + ", w=" + this.w + Operators.BLOCK_END;
    }
}
