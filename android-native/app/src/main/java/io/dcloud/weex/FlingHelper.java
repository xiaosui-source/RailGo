package io.dcloud.weex;

import android.content.Context;
import android.view.ViewConfiguration;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes2.dex */
public class FlingHelper {
    private static float DECELERATION_RATE = (float) (Math.log(0.78d) / Math.log(0.9d));
    private static float mFlingFriction = ViewConfiguration.getScrollFriction();
    private static float mPhysicalCoeff;

    public FlingHelper(Context context) {
        mPhysicalCoeff = context.getResources().getDisplayMetrics().density * 160.0f * 386.0878f * 0.84f;
    }

    private double getSplineDeceleration(int i) {
        return Math.log((Math.abs(i) * 0.35f) / (mFlingFriction * mPhysicalCoeff));
    }

    private double getSplineDecelerationByDistance(double d) {
        return ((DECELERATION_RATE - 1.0d) * Math.log(d / (mFlingFriction * mPhysicalCoeff))) / DECELERATION_RATE;
    }

    public double getSplineFlingDistance(int i) {
        double splineDeceleration = getSplineDeceleration(i);
        double d = DECELERATION_RATE;
        return Math.exp(splineDeceleration * (d / (d - 1.0d))) * mFlingFriction * mPhysicalCoeff;
    }

    public int getVelocityByDistance(double d) {
        return Math.abs((int) (((Math.exp(getSplineDecelerationByDistance(d)) * mFlingFriction) * mPhysicalCoeff) / 0.3499999940395355d));
    }
}
