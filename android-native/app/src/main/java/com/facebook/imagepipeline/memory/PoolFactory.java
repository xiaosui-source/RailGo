package com.facebook.imagepipeline.memory;

import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.ByteArrayPool;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;
import com.facebook.imagepipeline.core.NativeCodeSetup;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public class PoolFactory {

    @Nullable
    private MemoryChunkPool mAshmemMemoryChunkPool;

    @Nullable
    private BitmapPool mBitmapPool;

    @Nullable
    private MemoryChunkPool mBufferMemoryChunkPool;
    private final PoolConfig mConfig;

    @Nullable
    private FlexByteArrayPool mFlexByteArrayPool;

    @Nullable
    private MemoryChunkPool mNativeMemoryChunkPool;

    @Nullable
    private PooledByteBufferFactory mPooledByteBufferFactory;

    @Nullable
    private PooledByteStreams mPooledByteStreams;

    @Nullable
    private SharedByteArray mSharedByteArray;

    @Nullable
    private ByteArrayPool mSmallByteArrayPool;

    public PoolFactory(PoolConfig poolConfig) {
        this.mConfig = (PoolConfig) Preconditions.checkNotNull(poolConfig);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0092  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.facebook.imagepipeline.memory.BitmapPool getBitmapPool() {
        /*
            r5 = this;
            com.facebook.imagepipeline.memory.BitmapPool r0 = r5.mBitmapPool
            if (r0 != 0) goto Lb1
            com.facebook.imagepipeline.memory.PoolConfig r0 = r5.mConfig
            java.lang.String r0 = r0.getBitmapPoolType()
            int r1 = r0.hashCode()
            switch(r1) {
                case -1868884870: goto L6c;
                case -1106578487: goto L65;
                case -404562712: goto L35;
                case -402149703: goto L24;
                case 95945896: goto L13;
                default: goto L11;
            }
        L11:
            goto L92
        L13:
            java.lang.String r1 = "dummy"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L92
            com.facebook.imagepipeline.memory.DummyBitmapPool r0 = new com.facebook.imagepipeline.memory.DummyBitmapPool
            r0.<init>()
            r5.mBitmapPool = r0
            goto Lb1
        L24:
            java.lang.String r1 = "dummy_with_tracking"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L92
            com.facebook.imagepipeline.memory.DummyTrackingInUseBitmapPool r0 = new com.facebook.imagepipeline.memory.DummyTrackingInUseBitmapPool
            r0.<init>()
            r5.mBitmapPool = r0
            goto Lb1
        L35:
            java.lang.String r1 = "experimental"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L92
            com.facebook.imagepipeline.memory.LruBitmapPool r0 = new com.facebook.imagepipeline.memory.LruBitmapPool
            com.facebook.imagepipeline.memory.PoolConfig r1 = r5.mConfig
            int r1 = r1.getBitmapPoolMaxPoolSize()
            com.facebook.imagepipeline.memory.PoolConfig r2 = r5.mConfig
            int r2 = r2.getBitmapPoolMaxBitmapSize()
            com.facebook.imagepipeline.memory.NoOpPoolStatsTracker r3 = com.facebook.imagepipeline.memory.NoOpPoolStatsTracker.getInstance()
            com.facebook.imagepipeline.memory.PoolConfig r4 = r5.mConfig
            boolean r4 = r4.isRegisterLruBitmapPoolAsMemoryTrimmable()
            if (r4 == 0) goto L5e
            com.facebook.imagepipeline.memory.PoolConfig r4 = r5.mConfig
            com.facebook.common.memory.MemoryTrimmableRegistry r4 = r4.getMemoryTrimmableRegistry()
            goto L5f
        L5e:
            r4 = 0
        L5f:
            r0.<init>(r1, r2, r3, r4)
            r5.mBitmapPool = r0
            goto Lb1
        L65:
            java.lang.String r1 = "legacy"
            boolean r0 = r0.equals(r1)
            goto L92
        L6c:
            java.lang.String r1 = "legacy_default_params"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L92
            com.facebook.imagepipeline.memory.BucketsBitmapPool r0 = new com.facebook.imagepipeline.memory.BucketsBitmapPool
            com.facebook.imagepipeline.memory.PoolConfig r1 = r5.mConfig
            com.facebook.common.memory.MemoryTrimmableRegistry r1 = r1.getMemoryTrimmableRegistry()
            com.facebook.imagepipeline.memory.PoolParams r2 = com.facebook.imagepipeline.memory.DefaultBitmapPoolParams.get()
            com.facebook.imagepipeline.memory.PoolConfig r3 = r5.mConfig
            com.facebook.imagepipeline.memory.PoolStatsTracker r3 = r3.getBitmapPoolStatsTracker()
            com.facebook.imagepipeline.memory.PoolConfig r4 = r5.mConfig
            boolean r4 = r4.isIgnoreBitmapPoolHardCap()
            r0.<init>(r1, r2, r3, r4)
            r5.mBitmapPool = r0
            goto Lb1
        L92:
            com.facebook.imagepipeline.memory.BucketsBitmapPool r0 = new com.facebook.imagepipeline.memory.BucketsBitmapPool
            com.facebook.imagepipeline.memory.PoolConfig r1 = r5.mConfig
            com.facebook.common.memory.MemoryTrimmableRegistry r1 = r1.getMemoryTrimmableRegistry()
            com.facebook.imagepipeline.memory.PoolConfig r2 = r5.mConfig
            com.facebook.imagepipeline.memory.PoolParams r2 = r2.getBitmapPoolParams()
            com.facebook.imagepipeline.memory.PoolConfig r3 = r5.mConfig
            com.facebook.imagepipeline.memory.PoolStatsTracker r3 = r3.getBitmapPoolStatsTracker()
            com.facebook.imagepipeline.memory.PoolConfig r4 = r5.mConfig
            boolean r4 = r4.isIgnoreBitmapPoolHardCap()
            r0.<init>(r1, r2, r3, r4)
            r5.mBitmapPool = r0
        Lb1:
            com.facebook.imagepipeline.memory.BitmapPool r0 = r5.mBitmapPool
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.memory.PoolFactory.getBitmapPool():com.facebook.imagepipeline.memory.BitmapPool");
    }

    @Nullable
    public MemoryChunkPool getBufferMemoryChunkPool() {
        if (this.mBufferMemoryChunkPool == null) {
            try {
                this.mBufferMemoryChunkPool = (MemoryChunkPool) Class.forName("com.facebook.imagepipeline.memory.BufferMemoryChunkPool").getConstructor(MemoryTrimmableRegistry.class, PoolParams.class, PoolStatsTracker.class).newInstance(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getMemoryChunkPoolParams(), this.mConfig.getMemoryChunkPoolStatsTracker());
            } catch (ClassNotFoundException unused) {
                this.mBufferMemoryChunkPool = null;
            } catch (IllegalAccessException unused2) {
                this.mBufferMemoryChunkPool = null;
            } catch (InstantiationException unused3) {
                this.mBufferMemoryChunkPool = null;
            } catch (NoSuchMethodException unused4) {
                this.mBufferMemoryChunkPool = null;
            } catch (InvocationTargetException unused5) {
                this.mBufferMemoryChunkPool = null;
            }
        }
        return this.mBufferMemoryChunkPool;
    }

    public FlexByteArrayPool getFlexByteArrayPool() {
        if (this.mFlexByteArrayPool == null) {
            this.mFlexByteArrayPool = new FlexByteArrayPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getFlexByteArrayPoolParams());
        }
        return this.mFlexByteArrayPool;
    }

    public int getFlexByteArrayPoolMaxNumThreads() {
        return this.mConfig.getFlexByteArrayPoolParams().maxNumThreads;
    }

    @Nullable
    public MemoryChunkPool getNativeMemoryChunkPool() {
        if (this.mNativeMemoryChunkPool == null) {
            try {
                this.mNativeMemoryChunkPool = (MemoryChunkPool) Class.forName("com.facebook.imagepipeline.memory.NativeMemoryChunkPool").getConstructor(MemoryTrimmableRegistry.class, PoolParams.class, PoolStatsTracker.class).newInstance(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getMemoryChunkPoolParams(), this.mConfig.getMemoryChunkPoolStatsTracker());
            } catch (ClassNotFoundException e) {
                FLog.e("PoolFactory", "", e);
                this.mNativeMemoryChunkPool = null;
            } catch (IllegalAccessException e2) {
                FLog.e("PoolFactory", "", e2);
                this.mNativeMemoryChunkPool = null;
            } catch (InstantiationException e3) {
                FLog.e("PoolFactory", "", e3);
                this.mNativeMemoryChunkPool = null;
            } catch (NoSuchMethodException e4) {
                FLog.e("PoolFactory", "", e4);
                this.mNativeMemoryChunkPool = null;
            } catch (InvocationTargetException e5) {
                FLog.e("PoolFactory", "", e5);
                this.mNativeMemoryChunkPool = null;
            }
        }
        return this.mNativeMemoryChunkPool;
    }

    @Nullable
    private MemoryChunkPool getAshmemMemoryChunkPool() {
        if (this.mAshmemMemoryChunkPool == null) {
            try {
                this.mAshmemMemoryChunkPool = (MemoryChunkPool) Class.forName("com.facebook.imagepipeline.memory.AshmemMemoryChunkPool").getConstructor(MemoryTrimmableRegistry.class, PoolParams.class, PoolStatsTracker.class).newInstance(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getMemoryChunkPoolParams(), this.mConfig.getMemoryChunkPoolStatsTracker());
            } catch (ClassNotFoundException unused) {
                this.mAshmemMemoryChunkPool = null;
            } catch (IllegalAccessException unused2) {
                this.mAshmemMemoryChunkPool = null;
            } catch (InstantiationException unused3) {
                this.mAshmemMemoryChunkPool = null;
            } catch (NoSuchMethodException unused4) {
                this.mAshmemMemoryChunkPool = null;
            } catch (InvocationTargetException unused5) {
                this.mAshmemMemoryChunkPool = null;
            }
        }
        return this.mAshmemMemoryChunkPool;
    }

    public PooledByteBufferFactory getPooledByteBufferFactory() {
        return getPooledByteBufferFactory(!NativeCodeSetup.getUseNativeCode() ? 1 : 0);
    }

    public PooledByteBufferFactory getPooledByteBufferFactory(int i) {
        if (this.mPooledByteBufferFactory == null) {
            MemoryChunkPool memoryChunkPool = getMemoryChunkPool(i);
            Preconditions.checkNotNull(memoryChunkPool, "failed to get pool for chunk type: " + i);
            this.mPooledByteBufferFactory = new MemoryPooledByteBufferFactory(memoryChunkPool, getPooledByteStreams());
        }
        return this.mPooledByteBufferFactory;
    }

    public PooledByteStreams getPooledByteStreams() {
        if (this.mPooledByteStreams == null) {
            this.mPooledByteStreams = new PooledByteStreams(getSmallByteArrayPool());
        }
        return this.mPooledByteStreams;
    }

    public SharedByteArray getSharedByteArray() {
        if (this.mSharedByteArray == null) {
            this.mSharedByteArray = new SharedByteArray(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getFlexByteArrayPoolParams());
        }
        return this.mSharedByteArray;
    }

    public ByteArrayPool getSmallByteArrayPool() {
        if (this.mSmallByteArrayPool == null) {
            this.mSmallByteArrayPool = new GenericByteArrayPool(this.mConfig.getMemoryTrimmableRegistry(), this.mConfig.getSmallByteArrayPoolParams(), this.mConfig.getSmallByteArrayPoolStatsTracker());
        }
        return this.mSmallByteArrayPool;
    }

    @Nullable
    private MemoryChunkPool getMemoryChunkPool(int i) {
        if (i == 0) {
            return getNativeMemoryChunkPool();
        }
        if (i == 1) {
            return getBufferMemoryChunkPool();
        }
        if (i == 2) {
            return getAshmemMemoryChunkPool();
        }
        throw new IllegalArgumentException("Invalid MemoryChunkType");
    }
}
