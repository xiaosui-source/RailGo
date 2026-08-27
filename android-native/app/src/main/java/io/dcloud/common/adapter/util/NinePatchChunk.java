package io.dcloud.common.adapter.util;

import android.graphics.Rect;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class NinePatchChunk {
    public static final int NO_COLOR = 1;
    public static final int TRANSPARENT_COLOR = 0;
    public int[] mColor;
    public int[] mDivX;
    public int[] mDivY;
    public Rect mPaddings = new Rect();

    private static void checkDivCount(int i) {
        if (i == 0 || (i & 1) != 0) {
            throw new RuntimeException("invalid nine-patch: " + i);
        }
    }

    public static NinePatchChunk deserialize(byte[] bArr) {
        ByteBuffer byteBufferOrder = ByteBuffer.wrap(bArr).order(ByteOrder.nativeOrder());
        if (byteBufferOrder.get() == 0) {
            return null;
        }
        NinePatchChunk ninePatchChunk = new NinePatchChunk();
        ninePatchChunk.mDivX = new int[byteBufferOrder.get()];
        ninePatchChunk.mDivY = new int[byteBufferOrder.get()];
        ninePatchChunk.mColor = new int[byteBufferOrder.get()];
        checkDivCount(ninePatchChunk.mDivX.length);
        checkDivCount(ninePatchChunk.mDivY.length);
        byteBufferOrder.getInt();
        byteBufferOrder.getInt();
        ninePatchChunk.mPaddings.left = byteBufferOrder.getInt();
        ninePatchChunk.mPaddings.right = byteBufferOrder.getInt();
        ninePatchChunk.mPaddings.top = byteBufferOrder.getInt();
        ninePatchChunk.mPaddings.bottom = byteBufferOrder.getInt();
        byteBufferOrder.getInt();
        readIntArray(ninePatchChunk.mDivX, byteBufferOrder);
        readIntArray(ninePatchChunk.mDivY, byteBufferOrder);
        readIntArray(ninePatchChunk.mColor, byteBufferOrder);
        return ninePatchChunk;
    }

    private static void readIntArray(int[] iArr, ByteBuffer byteBuffer) {
        int length = iArr.length;
        for (int i = 0; i < length; i++) {
            iArr[i] = byteBuffer.getInt();
        }
    }
}
