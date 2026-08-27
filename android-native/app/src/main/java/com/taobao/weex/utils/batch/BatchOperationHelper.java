package com.taobao.weex.utils.batch;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: r8-map-id-8c2cfb1903e4c438e50e725a9caf630fe5e3f409ce4448463e9f9d02c32f38ea */
/* loaded from: classes.dex */
public class BatchOperationHelper implements Interceptor {
    private boolean isCollecting;
    private BactchExecutor mExecutor;
    private CopyOnWriteArrayList<Runnable> sRegisterTasks = new CopyOnWriteArrayList<>();

    public BatchOperationHelper(BactchExecutor bactchExecutor) {
        this.isCollecting = false;
        this.mExecutor = bactchExecutor;
        bactchExecutor.setInterceptor(this);
        this.isCollecting = true;
    }

    public void flush() {
        this.isCollecting = false;
        this.mExecutor.post(new Runnable() { // from class: com.taobao.weex.utils.batch.BatchOperationHelper.1
            @Override // java.lang.Runnable
            public void run() {
                Iterator it = BatchOperationHelper.this.sRegisterTasks.iterator();
                while (it.hasNext()) {
                    Runnable runnable = (Runnable) it.next();
                    runnable.run();
                    BatchOperationHelper.this.sRegisterTasks.remove(runnable);
                }
            }
        });
        this.mExecutor.setInterceptor(null);
    }

    @Override // com.taobao.weex.utils.batch.Interceptor
    public boolean take(Runnable runnable) {
        if (!this.isCollecting) {
            return false;
        }
        this.sRegisterTasks.add(runnable);
        return true;
    }
}
