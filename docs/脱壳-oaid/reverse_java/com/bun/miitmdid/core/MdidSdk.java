package com.bun.miitmdid.core;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Keep;
import com.bun.miitmdid.a.b;
import com.bun.miitmdid.b.c;
import com.bun.supplier.DefaultSupplier;
import com.bun.supplier.IIdentifierListener;
import com.bun.supplier.IdSupplier;
import com.bun.supplier.InnerIdSupplier;
import com.bun.supplier.SupplierListener;
import java.io.IOException;

@Keep
/* loaded from: /workspace/39285EFA.decrypted.dex */
public class MdidSdk implements SupplierListener {
    private IIdentifierListener _InnerListener;
    private b _setting;

    static /* synthetic */ class a {
        static final /* synthetic */ int[] a = new int[com.bun.miitmdid.c.a.values().length];

        static {
            try {
                a[com.bun.miitmdid.c.a.XIAOMI.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                a[com.bun.miitmdid.c.a.BLACKSHARK.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                a[com.bun.miitmdid.c.a.VIVO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                a[com.bun.miitmdid.c.a.HUA_WEI.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                a[com.bun.miitmdid.c.a.OPPO.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                a[com.bun.miitmdid.c.a.ONEPLUS.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                a[com.bun.miitmdid.c.a.MOTO.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                a[com.bun.miitmdid.c.a.LENOVO.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
            try {
                a[com.bun.miitmdid.c.a.ASUS.ordinal()] = 9;
            } catch (NoSuchFieldError e9) {
            }
            try {
                a[com.bun.miitmdid.c.a.SAMSUNG.ordinal()] = 10;
            } catch (NoSuchFieldError e10) {
            }
            try {
                a[com.bun.miitmdid.c.a.MEIZU.ordinal()] = 11;
            } catch (NoSuchFieldError e11) {
            }
            try {
                a[com.bun.miitmdid.c.a.NUBIA.ordinal()] = 12;
            } catch (NoSuchFieldError e12) {
            }
            try {
                a[com.bun.miitmdid.c.a.ZTE.ordinal()] = 13;
            } catch (NoSuchFieldError e13) {
            }
            try {
                a[com.bun.miitmdid.c.a.FREEMEOS.ordinal()] = 14;
            } catch (NoSuchFieldError e14) {
            }
            try {
                a[com.bun.miitmdid.c.a.SSUIOS.ordinal()] = 15;
            } catch (NoSuchFieldError e15) {
            }
        }
    }

    @Keep
    public MdidSdk() {
        try {
            com.bun.lib.a.a(true);
        } catch (Exception e) {
            com.bun.lib.a.b("mdidsdk", "extractor exception!", e);
        }
    }

    @Keep
    public MdidSdk(boolean z) {
        try {
            com.bun.lib.a.a(z);
        } catch (Exception e) {
            com.bun.lib.a.b("mdidsdk", "extractor exception!", e);
        }
    }

    private int _InnerFailed(int i, IdSupplier idSupplier) {
        OnSupport(idSupplier != null ? idSupplier.isSupported() : false, idSupplier);
        return i;
    }

    @Keep
    public int InitSdk(Context context, IIdentifierListener iIdentifierListener) throws IOException {
        InnerIdSupplier bVar;
        this._InnerListener = iIdentifierListener;
        com.bun.lib.b.a(context);
        com.bun.miitmdid.c.a aVarA = com.bun.miitmdid.c.a.a(Build.MANUFACTURER);
        if (aVarA == com.bun.miitmdid.c.a.UNSUPPORT && (aVarA = com.bun.miitmdid.c.c.a.a()) == com.bun.miitmdid.c.a.UNSUPPORT) {
            return _InnerFailed(ErrorCode.INIT_ERROR_MANUFACTURER_NOSUPPORT, new DefaultSupplier());
        }
        com.bun.miitmdid.a.a aVarA2 = com.bun.miitmdid.a.a.a(context);
        if (aVarA2 == null) {
            return _InnerFailed(ErrorCode.INIT_ERROR_LOAD_CONFIGFILE, new DefaultSupplier());
        }
        this._setting = aVarA2;
        switch (a.a[aVarA.ordinal()]) {
            case 1:
            case 2:
                bVar = new com.bun.miitmdid.c.k.b(context);
                break;
            case 3:
                com.bun.miitmdid.c.j.a aVar = new com.bun.miitmdid.c.j.a(context);
                aVar.a(this._setting.a());
                bVar = aVar;
                break;
            case 4:
                bVar = new com.bun.miitmdid.c.d.a(context);
                break;
            case 5:
            case 6:
                bVar = new com.bun.miitmdid.c.i.a(context);
                break;
            case 7:
            case 8:
                bVar = new com.bun.miitmdid.c.g.b(context, this);
                break;
            case 9:
                bVar = new com.bun.miitmdid.c.b.a(context, this);
                break;
            case 10:
                bVar = new com.bun.miitmdid.supplier.sumsung.a(context, this);
                break;
            case 11:
                bVar = new com.bun.miitmdid.c.f.a(context);
                break;
            case 12:
                bVar = new com.bun.miitmdid.c.h.b(context);
                break;
            case 13:
            case 14:
            case 15:
                bVar = new com.bun.miitmdid.supplier.msa.b(context);
                break;
            default:
                bVar = null;
                break;
        }
        if (bVar == null) {
            return _InnerFailed(ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT, new DefaultSupplier());
        }
        if (!bVar.a()) {
            bVar.a(this);
            return ErrorCode.INIT_ERROR_RESULT_DELAY;
        }
        if (!bVar.isSupported()) {
            return _InnerFailed(ErrorCode.INIT_ERROR_DEVICE_NOSUPPORT, bVar);
        }
        OnSupport(true, bVar);
        return 0;
    }

    @Override // com.bun.supplier.SupplierListener
    public void OnSupport(boolean z, IdSupplier idSupplier) {
        IIdentifierListener iIdentifierListener = this._InnerListener;
        if (iIdentifierListener != null) {
            iIdentifierListener.OnSupport(z, idSupplier);
        }
        c cVar = new c();
        String oaid = "";
        String vaid = "";
        String aaid = "";
        if (idSupplier != null) {
            oaid = idSupplier.getOAID();
            vaid = idSupplier.getVAID();
            aaid = idSupplier.getAAID();
            if (idSupplier instanceof InnerIdSupplier) {
                ((InnerIdSupplier) idSupplier).shutDown();
            }
        }
        cVar.a(z, "", oaid, vaid, aaid);
    }

    public void UnInitSdk() {
    }
}
