package io.dcloud.common.adapter.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class MessageHandler {
    private static Handler myHandler = new Handler(Looper.getMainLooper()) { // from class: io.dcloud.common.adapter.util.MessageHandler.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            try {
                Object[] objArr = (Object[]) message.obj;
                Object obj = objArr[0];
                if (obj instanceof IMessages) {
                    ((IMessages) obj).execute(objArr[1]);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Logger.e("MessageHandler.handleMessage e=" + e);
            }
        }
    };

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface IMessages {
        void execute(Object obj);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface UncheckedCallable {
        void run(WaitableRunnable waitableRunnable);
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public static abstract class WaitableRunnable implements Runnable {
        private Exception mException;
        private String mTimeOutKey = "evalJSSync_time_out";
        private Object mValue;

        protected WaitableRunnable() {
        }

        private void join() {
            synchronized (this) {
                try {
                    wait(500L);
                    if (this.mValue == null) {
                        this.mValue = this.mTimeOutKey;
                    }
                } catch (InterruptedException unused) {
                }
            }
        }

        public void callBack(Object obj) {
            this.mValue = obj;
            synchronized (this) {
                notifyAll();
            }
        }

        public Object invoke(Handler handler) {
            if (!handler.post(this)) {
                throw new RuntimeException("Handler.post() returned false");
            }
            join();
            if (this.mException == null) {
                return this.mValue;
            }
            throw new RuntimeException(this.mException);
        }

        protected abstract void onRun(WaitableRunnable waitableRunnable);

        @Override // java.lang.Runnable
        public final void run() {
            try {
                onRun(this);
                this.mException = null;
            } catch (Exception e) {
                this.mValue = null;
                this.mException = e;
            }
        }
    }

    public static boolean hasCallbacks(Runnable runnable) {
        return myHandler.hasCallbacks(runnable);
    }

    public static void post(Runnable runnable) {
        myHandler.post(runnable);
    }

    public static Object postAndWait(final UncheckedCallable uncheckedCallable) {
        return new WaitableRunnable() { // from class: io.dcloud.common.adapter.util.MessageHandler.2
            @Override // io.dcloud.common.adapter.util.MessageHandler.WaitableRunnable
            protected void onRun(WaitableRunnable waitableRunnable) {
                uncheckedCallable.run(waitableRunnable);
            }
        }.invoke(myHandler);
    }

    public static void postDelayed(Runnable runnable, long j) {
        myHandler.postDelayed(runnable, j);
    }

    public static void removeCallbacks(Runnable runnable) {
        myHandler.removeCallbacks(runnable);
    }

    public static void removeCallbacksAndMessages() {
    }

    public static void sendMessage(IMessages iMessages, Object obj) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 0;
        messageObtain.obj = new Object[]{iMessages, obj};
        myHandler.sendMessage(messageObtain);
    }

    public static void sendMessage(IMessages iMessages, long j, Object obj) {
        Message messageObtain = Message.obtain();
        messageObtain.what = 0;
        messageObtain.obj = new Object[]{iMessages, obj};
        myHandler.sendMessageDelayed(messageObtain, j);
    }
}
