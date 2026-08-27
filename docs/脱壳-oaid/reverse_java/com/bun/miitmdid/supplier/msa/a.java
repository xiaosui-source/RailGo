package com.bun.miitmdid.supplier.msa;

import android.os.AsyncTask;
import android.os.RemoteException;
import com.bun.lib.c;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class a extends AsyncTask<Void, Void, Boolean> {
    public c a;
    public com.bun.miitmdid.c.e.a b;

    public a(c cVar, com.bun.miitmdid.c.e.a aVar) {
        this.a = cVar;
        this.b = aVar;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public Boolean doInBackground(Void... voidArr) throws InterruptedException {
        com.bun.miitmdid.c.e.a aVar;
        boolean zC = false;
        if (this.a == null) {
            return false;
        }
        int i = 0;
        do {
            try {
                zC = this.a.c();
                if (zC) {
                    break;
                }
                Thread.sleep(10L);
                i++;
            } catch (RemoteException | InterruptedException e) {
                com.bun.lib.a.a("MsaAsyncTask", "doInBackground", e);
            }
        } while (i < 30);
        if (zC && (aVar = this.b) != null) {
            aVar.a(true);
        }
        return Boolean.valueOf(zC);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    /* renamed from: a, reason: merged with bridge method [inline-methods] */
    public void onPostExecute(Boolean bool) {
        super.onPostExecute(bool);
    }
}
