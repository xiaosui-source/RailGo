package com.bun.miitmdid.b;

import com.bun.lib.sysParamters;
import com.bun.miitmdid.b.b;
import java.util.HashMap;
import java.util.Map;

/* loaded from: /workspace/39285EFA.decrypted.dex */
public class c {
    private Map<String, String> a = new HashMap();

    class a implements b.InterfaceC0002b {
        a(c cVar) {
        }

        @Override // com.bun.miitmdid.b.b.InterfaceC0002b
        public void a(Exception exc, int i, String str) {
        }
    }

    private Map<String, String> a() {
        if (this.a.isEmpty()) {
            this.a.put("av", sysParamters.f().a());
            this.a.put("sv", sysParamters.f().d());
            this.a.put("md", sysParamters.f().c());
            this.a.put("mf", sysParamters.f().b());
            this.a.put("pn", sysParamters.g());
        }
        this.a.put("tm", String.valueOf(System.currentTimeMillis()));
        return this.a;
    }

    public boolean a(boolean z, String str, String str2, String str3, String str4) {
        try {
            String strA = com.bun.miitmdid.d.b.a(String.format("UDID=%s&OAID=%s&VAID=%s&AAID=%s", str, str2, str3, str4));
            b bVarA = b.a(com.bun.lib.b.b());
            bVarA.a(a());
            bVarA.a("support", String.valueOf(z ? 1 : 0));
            bVarA.a((Object) strA);
            bVarA.a("http://sdk.api.oaid.wocloud.cn/stat");
            bVarA.a((b.InterfaceC0002b) new a(this)).a();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
