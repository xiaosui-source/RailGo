package com.alibaba.android.bindingx.core.internal;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.view.Choreographer;

/* loaded from: classes.dex */
abstract class AnimationFrame {

    interface Callback {
        void doFrame();
    }

    abstract void clear();

    abstract void requestAnimationFrame(Callback callback);

    abstract void terminate();

    AnimationFrame() {
    }

    static AnimationFrame newInstance() {
        return new ChoreographerAnimationFrameImpl();
    }

    private static class ChoreographerAnimationFrameImpl extends AnimationFrame implements Choreographer.FrameCallback {
        private Callback callback;
        private Choreographer choreographer;
        private boolean isRunning;

        ChoreographerAnimationFrameImpl() {
            if (Looper.myLooper() == null) {
                Looper.prepare();
            }
            this.choreographer = Choreographer.getInstance();
        }

        @Override // com.alibaba.android.bindingx.core.internal.AnimationFrame
        void clear() {
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                choreographer.removeFrameCallback(this);
            }
            this.isRunning = false;
        }

        @Override // com.alibaba.android.bindingx.core.internal.AnimationFrame
        void terminate() {
            clear();
            this.choreographer = null;
        }

        @Override // com.alibaba.android.bindingx.core.internal.AnimationFrame
        void requestAnimationFrame(Callback callback) {
            this.callback = callback;
            this.isRunning = true;
            Choreographer choreographer = this.choreographer;
            if (choreographer != null) {
                choreographer.postFrameCallback(this);
            }
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            Callback callback = this.callback;
            if (callback != null) {
                callback.doFrame();
            }
            Choreographer choreographer = this.choreographer;
            if (choreographer == null || !this.isRunning) {
                return;
            }
            choreographer.postFrameCallback(this);
        }
    }

    private static class HandlerAnimationFrameImpl extends AnimationFrame implements Handler.Callback {
        private static final long DEFAULT_DELAY_MILLIS = 16;
        private static final int MSG_FRAME_CALLBACK = 100;
        private Callback callback;
        private boolean isRunning;
        private Handler mInnerHandler;
        private HandlerThread mInnerHandlerThread;

        HandlerAnimationFrameImpl() {
            if (this.mInnerHandlerThread != null) {
                terminate();
            }
            HandlerThread handlerThread = new HandlerThread("expression-timing-thread");
            this.mInnerHandlerThread = handlerThread;
            handlerThread.start();
            this.mInnerHandler = new Handler(this.mInnerHandlerThread.getLooper(), this);
        }

        @Override // com.alibaba.android.bindingx.core.internal.AnimationFrame
        void clear() {
            Handler handler = this.mInnerHandler;
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            this.isRunning = false;
        }

        @Override // com.alibaba.android.bindingx.core.internal.AnimationFrame
        void terminate() {
            clear();
            this.mInnerHandlerThread.quitSafely();
            this.mInnerHandler = null;
            this.mInnerHandlerThread = null;
        }

        @Override // com.alibaba.android.bindingx.core.internal.AnimationFrame
        void requestAnimationFrame(Callback callback) {
            this.callback = callback;
            this.isRunning = true;
            Handler handler = this.mInnerHandler;
            if (handler != null) {
                handler.sendEmptyMessage(100);
            }
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message == null || message.what != 100 || this.mInnerHandler == null) {
                return false;
            }
            Callback callback = this.callback;
            if (callback != null) {
                callback.doFrame();
            }
            if (!this.isRunning) {
                return true;
            }
            this.mInnerHandler.sendEmptyMessageDelayed(100, DEFAULT_DELAY_MILLIS);
            return true;
        }
    }
}
