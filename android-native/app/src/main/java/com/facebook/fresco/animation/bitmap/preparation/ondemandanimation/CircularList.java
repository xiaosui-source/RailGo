package com.facebook.fresco.animation.bitmap.preparation.ondemandanimation;

import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;

/* compiled from: CircularList.kt */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003J\u000e\u0010\r\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0003J\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00030\u000f2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\f\u001a\u00020\u0003R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0010"}, d2 = {"Lcom/facebook/fresco/animation/bitmap/preparation/ondemandanimation/CircularList;", "", AbsoluteConst.JSON_KEY_SIZE, "", "<init>", "(I)V", "getSize", "()I", "isTargetAhead", "", "from", IApp.ConfigProperty.CONFIG_TARGET, "length", "getPosition", "sublist", "", "animated-drawable_release"}, k = 1, mv = {2, 0, 0}, xi = 48)
/* loaded from: classes.dex */
public final class CircularList {
    private final int size;

    public CircularList(int i) {
        this.size = i;
    }

    public final int getSize() {
        return this.size;
    }

    public final boolean isTargetAhead(int from, int target, int length) {
        int position = getPosition(length + from);
        return from < position ? from <= target && target <= position : (from <= target && target <= this.size) || (target >= 0 && target <= position);
    }

    public final int getPosition(int target) {
        int i = target % this.size;
        Integer numValueOf = Integer.valueOf(i);
        if (numValueOf.intValue() < 0) {
            numValueOf = null;
        }
        return numValueOf != null ? numValueOf.intValue() : i + this.size;
    }

    public final List<Integer> sublist(int from, int length) {
        IntRange intRangeUntil = RangesKt.until(0, length);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
        Iterator<Integer> it = intRangeUntil.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(getPosition(((IntIterator) it).nextInt() + from)));
        }
        return arrayList;
    }
}
