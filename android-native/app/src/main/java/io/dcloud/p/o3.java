package io.dcloud.p;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import androidx.core.text.HtmlCompat;
import com.alibaba.fastjson.asm.Opcodes;
import com.alibaba.fastjson.parser.JSONLexer;
import com.facebook.imagepipeline.transcoder.JpegTranscoderUtils;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.performance.WXInstanceApm;
import com.taobao.weex.ui.component.list.template.TemplateDom;
import com.taobao.weex.utils.WXUtils;
import io.dcloud.common.DHInterface.IMgr;
import io.dcloud.common.adapter.util.SP;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.ThreadPool;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.text.Typography;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import org.json.JSONObject;
import org.mozilla.universalchardet.prober.CharsetProber;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public abstract class o3 {
    private static boolean a = false;
    private static boolean b = false;

    public static void a(final Context context) {
        ThreadPool.self().addThreadTask(new Runnable() { // from class: io.dcloud.p.o3$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() throws PackageManager.NameNotFoundException {
                o3.c(context);
            }
        }, true);
    }

    private static String b(Context context) throws IOException {
        try {
            String[] list = context.getAssets().list("");
            ArrayList arrayList = new ArrayList();
            if (list != null) {
                for (String str : list) {
                    if (!str.startsWith(AbsoluteConst.XML_APPS) && !str.equals("data") && !str.startsWith("uni-js") && !str.startsWith("fonts") && !str.startsWith("dcloud") && !str.startsWith("supplierconfig") && !str.startsWith("amap_") && !str.startsWith("location_") && !str.startsWith("map_") && !str.endsWith(".png") && !str.endsWith(".xml")) {
                        arrayList.add(str);
                        if (!b) {
                            b = a(str);
                        }
                    }
                }
            }
            return TextUtils.join(",", arrayList);
        } catch (Exception unused) {
            return "";
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void c(Context context) throws PackageManager.NameNotFoundException {
        HashMap map = new HashMap();
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String str = packageInfo.versionName;
            if (SP.getBundleData(context, "pdr", "vc").equals(str)) {
                return;
            }
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            map.put("man", applicationInfo.name);
            map.put("ran", applicationInfo.className);
            map.put("sofs", a(applicationInfo));
            map.put("afs", b(context));
            map.put("isjg", a | b ? "1" : WXInstanceApm.VALUE_ERROR_CODE_DEFAULT);
            SP.setBundleData(context, "pdr", "vc", str);
            SP.setBundleData(context, "pdr", "packdata", new JSONObject(map).toString());
        } catch (Exception unused) {
        }
    }

    private static String a(ApplicationInfo applicationInfo) {
        File file = new File(applicationInfo.nativeLibraryDir);
        if (!file.isDirectory()) {
            return "";
        }
        File[] fileArrListFiles = file.listFiles();
        ArrayList arrayList = new ArrayList();
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                String name = file2.getName();
                if (!name.startsWith("libweex") && !name.startsWith("libc++_") && !name.startsWith("libimage") && !name.startsWith("libnative-imagetranscoder") && !name.startsWith("libbreakpad-core") && !name.startsWith("libneonui_shared") && !name.startsWith("libgifimage") && !name.startsWith("libpl_droidsonroids_gif") && !name.startsWith("libnative-filters") && !name.startsWith("libAMapSDK")) {
                    arrayList.add(name);
                    if (!a) {
                        a = b(name);
                    }
                }
            }
        }
        return TextUtils.join(",", arrayList);
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static boolean b(String str) {
        str.getClass();
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2134158120:
                if (str.equals("libuusafe.so")) {
                    c = 0;
                    break;
                }
                break;
            case -2018608874:
                if (str.equals("libijmDataEncryption.so")) {
                    c = 1;
                    break;
                }
                break;
            case -1985752059:
                if (str.equals("libashield.so")) {
                    c = 2;
                    break;
                }
                break;
            case -1984868023:
                if (str.equals("libkwscmm.so")) {
                    c = 3;
                    break;
                }
                break;
            case -1865052775:
                if (str.equals("libkwscr.so")) {
                    c = 4;
                    break;
                }
                break;
            case -1827573641:
                if (str.equals("libsecenh.so")) {
                    c = 5;
                    break;
                }
                break;
            case -1818427804:
                if (str.equals("libsecexe.so")) {
                    c = 6;
                    break;
                }
                break;
            case -1815798633:
                if (str.equals("libexecv3.so")) {
                    c = 7;
                    break;
                }
                break;
            case -1755954320:
                if (str.equals("libapktoolplus_jiagu.so")) {
                    c = '\b';
                    break;
                }
                break;
            case -1673317148:
                if (str.equals("libxloader.so")) {
                    c = '\t';
                    break;
                }
                break;
            case -1645444250:
                if (str.equals("libdemolishdata.so")) {
                    c = '\n';
                    break;
                }
                break;
            case -1627800481:
                if (str.equals("libshell.so")) {
                    c = 11;
                    break;
                }
                break;
            case -1580880316:
                if (str.equals("libgeiri-x86.so")) {
                    c = '\f';
                    break;
                }
                break;
            case -1530914335:
                if (str.equals("libkwslinker.so")) {
                    c = '\r';
                    break;
                }
                break;
            case -1496547210:
                if (str.equals("libzuma.so")) {
                    c = 14;
                    break;
                }
                break;
            case -1461833317:
                if (str.equals("OPPOProtect.so")) {
                    c = 15;
                    break;
                }
                break;
            case -1390166100:
                if (str.equals("libchaosvmp.so")) {
                    c = 16;
                    break;
                }
                break;
            case -1322457268:
                if (str.equals("libSafeManageService.so")) {
                    c = 17;
                    break;
                }
                break;
            case -1236264417:
                if (str.equals("libvdog.so")) {
                    c = 18;
                    break;
                }
                break;
            case -1222315281:
                if (str.equals("libjiagu_ls.so")) {
                    c = 19;
                    break;
                }
                break;
            case -1142792868:
                if (str.equals("libprotectClass.so")) {
                    c = 20;
                    break;
                }
                break;
            case -1090990197:
                if (str.equals("libuusafe.jar.so")) {
                    c = 21;
                    break;
                }
                break;
            case -1042237095:
                if (str.equals("libjgdtc.so")) {
                    c = 22;
                    break;
                }
                break;
            case -1032518224:
                if (str.equals("libpreverify1.so")) {
                    c = 23;
                    break;
                }
                break;
            case -1025535434:
                if (str.equals("libtosprotection.x86.so")) {
                    c = 24;
                    break;
                }
                break;
            case -971296287:
                if (str.equals("librsprotect.so")) {
                    c = 25;
                    break;
                }
                break;
            case -839517943:
                if (str.equals("kdpdata.so")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case -801661360:
                if (str.equals("libdemolish.so")) {
                    c = 27;
                    break;
                }
                break;
            case -776414063:
                if (str.equals("libhdog.so")) {
                    c = 28;
                    break;
                }
                break;
            case -716134127:
                if (str.equals("libbaiduprotect.so")) {
                    c = 29;
                    break;
                }
                break;
            case -500661150:
                if (str.equals("libSecShell_art.so")) {
                    c = 30;
                    break;
                }
                break;
            case -364172656:
                if (str.equals("libmogosec_dex.so")) {
                    c = 31;
                    break;
                }
                break;
            case -355300191:
                if (str.equals("libSecShel1.so")) {
                    c = ' ';
                    break;
                }
                break;
            case -353542522:
                if (str.equals("libSecShell.so")) {
                    c = '!';
                    break;
                }
                break;
            case -341704342:
                if (str.equals("libDexHelper.so")) {
                    c = '\"';
                    break;
                }
                break;
            case -212159630:
                if (str.equals("libBugly-yaq.so")) {
                    c = '#';
                    break;
                }
                break;
            case -139075217:
                if (str.equals("libshell-super.2019.so")) {
                    c = '$';
                    break;
                }
                break;
            case -97837887:
                if (str.equals("libxgVipSecurity.so")) {
                    c = WXUtils.PERCENT;
                    break;
                }
                break;
            case -42102419:
                if (str.equals("libbaiduprotect_art.so")) {
                    c = Typography.amp;
                    break;
                }
                break;
            case -31461491:
                if (str.equals("libddog.so")) {
                    c = Operators.SINGLE_QUOTE;
                    break;
                }
                break;
            case -23531543:
                if (str.equals("libreincp_x86.so")) {
                    c = Operators.BRACKET_START;
                    break;
                }
                break;
            case 26379962:
                if (str.equals("libshellx-super.2021.so")) {
                    c = Operators.BRACKET_END;
                    break;
                }
                break;
            case 45417304:
                if (str.equals("libapssec.so")) {
                    c = '*';
                    break;
                }
                break;
            case 160098941:
                if (str.equals("libomesStdSco.so")) {
                    c = '+';
                    break;
                }
                break;
            case 274614037:
                if (str.equals("libnesec.so")) {
                    c = Operators.ARRAY_SEPRATOR;
                    break;
                }
                break;
            case 284229221:
                if (str.equals("DexHelper.so")) {
                    c = '-';
                    break;
                }
                break;
            case 287292665:
                if (str.equals("libnqshield.so")) {
                    c = Operators.DOT;
                    break;
                }
                break;
            case 488367136:
                if (str.equals("libashieldAdapter.so")) {
                    c = '/';
                    break;
                }
                break;
            case 560956794:
                if (str.equals("libbaiduprotect_x86.so")) {
                    c = '0';
                    break;
                }
                break;
            case 578150281:
                if (str.equals("libitsec.so")) {
                    c = '1';
                    break;
                }
                break;
            case 584784501:
                if (str.equals("OPPOProtect2019.so")) {
                    c = '2';
                    break;
                }
                break;
            case 594853830:
                if (str.equals("ibvirbox32.so")) {
                    c = '3';
                    break;
                }
                break;
            case 597437074:
                if (str.equals("libAPKProtect.so")) {
                    c = '4';
                    break;
                }
                break;
            case 611072698:
                if (str.equals("libtosprotection.armeabi-v7a.so")) {
                    c = '5';
                    break;
                }
                break;
            case 635413279:
                if (str.equals("libjiagu.so")) {
                    c = '6';
                    break;
                }
                break;
            case 685736589:
                if (str.equals("libgeiri.so")) {
                    c = '7';
                    break;
                }
                break;
            case 705326479:
                if (str.equals("libuusafeempty.so")) {
                    c = '8';
                    break;
                }
                break;
            case 737048669:
                if (str.equals("libenvid-ashield-sdk.so")) {
                    c = '9';
                    break;
                }
                break;
            case 747858267:
                if (str.equals("libexecmain.so")) {
                    c = Operators.CONDITION_IF_MIDDLE;
                    break;
                }
                break;
            case 792950433:
                if (str.equals("libDexHelper-x86.so")) {
                    c = ';';
                    break;
                }
                break;
            case 856042190:
                if (str.equals("libedog.so")) {
                    c = Typography.less;
                    break;
                }
                break;
            case 874097308:
                if (str.equals("libvenustech.so")) {
                    c = '=';
                    break;
                }
                break;
            case 888370528:
                if (str.equals("libtup.so")) {
                    c = Typography.greater;
                    break;
                }
                break;
            case 935811675:
                if (str.equals("libmogosecurity.so")) {
                    c = Operators.CONDITION_IF;
                    break;
                }
                break;
            case 936746009:
                if (str.equals("libegis.so")) {
                    c = TemplateDom.SEPARATOR;
                    break;
                }
                break;
            case 941666627:
                if (str.equals("libx3g.so")) {
                    c = 'A';
                    break;
                }
                break;
            case 1007652514:
                if (str.equals("libNSaferOnly.so")) {
                    c = 'B';
                    break;
                }
                break;
            case 1025443362:
                if (str.equals("libshel1x.so")) {
                    c = 'C';
                    break;
                }
                break;
            case 1049241474:
                if (str.equals("libzBugly-yaq.so")) {
                    c = 'D';
                    break;
                }
                break;
            case 1079931101:
                if (str.equals("libshellx.so")) {
                    c = 'E';
                    break;
                }
                break;
            case 1109914022:
                if (str.equals("libdSafeShell.so")) {
                    c = 'F';
                    break;
                }
                break;
            case 1182294592:
                if (str.equals("libcmvmp.so")) {
                    c = 'G';
                    break;
                }
                break;
            case 1245443399:
                if (str.equals("ibmogosecurity.so")) {
                    c = 'H';
                    break;
                }
                break;
            case 1257677568:
                if (str.equals("libreincp.so")) {
                    c = 'I';
                    break;
                }
                break;
            case 1275966198:
                if (str.equals("libmogosec_sodecrypt.so")) {
                    c = 'J';
                    break;
                }
                break;
            case 1324647711:
                if (str.equals("libfakejni.so")) {
                    c = 'K';
                    break;
                }
                break;
            case 1413289991:
                if (str.equals("libmobisec.so")) {
                    c = 'L';
                    break;
                }
                break;
            case 1419270836:
                if (str.equals("libexec.so")) {
                    c = 'M';
                    break;
                }
                break;
            case 1469742639:
                if (str.equals("libsgsecuritybody.so")) {
                    c = 'N';
                    break;
                }
                break;
            case 1580679069:
                if (str.equals("libbasec.so")) {
                    c = 'O';
                    break;
                }
                break;
            case 1615439237:
                if (str.equals("libsecmain.so")) {
                    c = 'P';
                    break;
                }
                break;
            case 1630403175:
                if (str.equals("libtprt.so")) {
                    c = 'Q';
                    break;
                }
                break;
            case 1743545871:
                if (str.equals("libfdog.so")) {
                    c = 'R';
                    break;
                }
                break;
            case 1824193784:
                if (str.equals("libsgmain.so")) {
                    c = 'S';
                    break;
                }
                break;
            case 1831927323:
                if (str.equals("libvirbox64.so")) {
                    c = 'T';
                    break;
                }
                break;
            case 1938372896:
                if (str.equals("liblegudb.so")) {
                    c = 'U';
                    break;
                }
                break;
            case 1988818099:
                if (str.equals("libvenSec.so")) {
                    c = 'V';
                    break;
                }
                break;
            case 2084629453:
                if (str.equals("libtosprotection.armeabi.so")) {
                    c = 'W';
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case ' ':
            case '!':
            case '\"':
            case '#':
            case '$':
            case '%':
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case Opcodes.LSTORE /* 55 */:
            case '8':
            case Opcodes.DSTORE /* 57 */:
            case ':':
            case ';':
            case '<':
            case LockFreeTaskQueueCore.CLOSED_SHIFT /* 61 */:
            case '>':
            case HtmlCompat.FROM_HTML_MODE_COMPACT /* 63 */:
            case '@':
            case CharsetProber.ASCII_A_CAPITAL /* 65 */:
            case 'B':
            case 'C':
            case 'D':
            case 'E':
            case IMgr.WindowEvent.WINDOW_ANIMATION_END /* 70 */:
            case IMgr.WindowEvent.ADD_ANIMATION_CALLBACK /* 71 */:
            case IMgr.WindowEvent.WINDOW_CRATE_TITLENVIEW /* 72 */:
            case IMgr.WindowEvent.WINDOW_APPEND_TITLENVIEW /* 73 */:
            case IMgr.WindowEvent.WINDOW_BACKGROUND_SET_WEBPARENT /* 74 */:
            case IMgr.WindowEvent.WINDOW_UPDATE_BACKGROUND /* 75 */:
            case IMgr.WindowEvent.CHECK_RESTART_TOP_WEBVIEW /* 76 */:
            case IMgr.WindowEvent.TITLE_BAR_MENU_ITEM_CLICK /* 77 */:
            case IMgr.WindowEvent.OBTAIN_MP_TOP_PAGE_URL /* 78 */:
            case 'O':
            case 'P':
            case IMgr.WindowEvent.OBTAIN_APP_TOP_PAGE_DIRECT /* 81 */:
            case 'R':
            case 'S':
            case 'T':
            case JpegTranscoderUtils.DEFAULT_JPEG_QUALITY /* 85 */:
            case 'V':
            case Opcodes.POP /* 87 */:
                return true;
            default:
                return false;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private static boolean a(String str) {
        str.getClass();
        str.hashCode();
        char c = 65535;
        switch (str.hashCode()) {
            case -2134158120:
                if (str.equals("libuusafe.so")) {
                    c = 0;
                    break;
                }
                break;
            case -2018608874:
                if (str.equals("libijmDataEncryption.so")) {
                    c = 1;
                    break;
                }
                break;
            case -1878631039:
                if (str.equals("baiduprotect.jar")) {
                    c = 2;
                    break;
                }
                break;
            case -1604614430:
                if (str.equals("mogosec_data")) {
                    c = 3;
                    break;
                }
                break;
            case -1598892812:
                if (str.equals("ijiami3.ajm")) {
                    c = 4;
                    break;
                }
                break;
            case -1582744544:
                if (str.equals("mxsafe.data")) {
                    c = 5;
                    break;
                }
                break;
            case -1496547210:
                if (str.equals("libzuma.so")) {
                    c = 6;
                    break;
                }
                break;
            case -1090990197:
                if (str.equals("libuusafe.jar.so")) {
                    c = 7;
                    break;
                }
                break;
            case -1060044872:
                if (str.equals("mxsafe")) {
                    c = '\b';
                    break;
                }
                break;
            case -1032518224:
                if (str.equals("libpreverify1.so")) {
                    c = '\t';
                    break;
                }
                break;
            case -1025535434:
                if (str.equals("libtosprotection.x86.so")) {
                    c = '\n';
                    break;
                }
                break;
            case -671959570:
                if (str.equals("mogosec_classes")) {
                    c = 11;
                    break;
                }
                break;
            case -614954440:
                if (str.equals("mxsafe.config")) {
                    c = '\f';
                    break;
                }
                break;
            case -328145179:
                if (str.equals("mxsafe.jar")) {
                    c = '\r';
                    break;
                }
                break;
            case -51676853:
                if (str.equals("ijiami.ajm")) {
                    c = 14;
                    break;
                }
                break;
            case -23531543:
                if (str.equals("libreincp_x86.so")) {
                    c = 15;
                    break;
                }
                break;
            case 3243197:
                if (str.equals("itse")) {
                    c = 16;
                    break;
                }
                break;
            case 26379962:
                if (str.equals("libshellx-super.2021.so")) {
                    c = 17;
                    break;
                }
                break;
            case 36078349:
                if (str.equals("mogosec_dexinfo")) {
                    c = 18;
                    break;
                }
                break;
            case 176284834:
                if (str.equals("secData0.jar")) {
                    c = 19;
                    break;
                }
                break;
            case 310189398:
                if (str.equals("sign.bin")) {
                    c = 20;
                    break;
                }
                break;
            case 611072698:
                if (str.equals("libtosprotection.armeabi-v7a.so")) {
                    c = 21;
                    break;
                }
                break;
            case 1097732274:
                if (str.equals("jiagu_data.bin")) {
                    c = 22;
                    break;
                }
                break;
            case 1257677568:
                if (str.equals("libreincp.so")) {
                    c = 23;
                    break;
                }
                break;
            case 1727583249:
                if (str.equals("maindata/fake_classes.dex")) {
                    c = 24;
                    break;
                }
                break;
            case 1804870155:
                if (str.equals("mogosec_march")) {
                    c = 25;
                    break;
                }
                break;
            case 1836897074:
                if (str.equals("ijm_lib")) {
                    c = JSONLexer.EOI;
                    break;
                }
                break;
            case 1892971274:
                if (str.equals("baiduprotect1.jar")) {
                    c = 27;
                    break;
                }
                break;
            case 2084629453:
                if (str.equals("libtosprotection.armeabi.so")) {
                    c = 28;
                    break;
                }
                break;
            case 2102911628:
                if (str.equals("libzumadata.so")) {
                    c = 29;
                    break;
                }
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
                return true;
            default:
                return false;
        }
    }
}
