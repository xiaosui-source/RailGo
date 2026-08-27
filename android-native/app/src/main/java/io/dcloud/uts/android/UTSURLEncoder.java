package io.dcloud.uts.android;

import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.list.template.TemplateDom;
import com.taobao.weex.utils.WXUtils;
import java.io.CharArrayWriter;
import java.nio.charset.Charset;
import java.util.BitSet;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Typography;

/* compiled from: UTSURLEncoder.kt */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0013\b\u0016\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\b\b\u0002\u0010\u0010\u001a\u00020\u000bR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\n\u001a\n \f*\u0004\u0018\u00010\u000b0\u000bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0011"}, d2 = {"Lio/dcloud/uts/android/UTSURLEncoder;", "", "isComponent", "", "<init>", "(Z)V", "shouldNotNeedEncoding", "Ljava/util/BitSet;", "caseDiff", "", "dfltEncName", "Ljava/nio/charset/Charset;", "kotlin.jvm.PlatformType", "encode", "", "s", "charset", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class UTSURLEncoder {
    private final int caseDiff;
    private Charset dfltEncName;
    private BitSet shouldNotNeedEncoding;

    public UTSURLEncoder(boolean z) {
        this.shouldNotNeedEncoding = new BitSet(256);
        this.caseDiff = 32;
        this.dfltEncName = Charset.forName("UTF-8");
        for (int i = 97; i < 123; i++) {
            this.shouldNotNeedEncoding.set(i);
        }
        for (int i2 = 65; i2 < 91; i2++) {
            this.shouldNotNeedEncoding.set(i2);
        }
        for (int i3 = 48; i3 < 58; i3++) {
            this.shouldNotNeedEncoding.set(i3);
        }
        Character[] chArr = {' ', '-', '_', Character.valueOf(Operators.DOT), '!', '~', '*', Character.valueOf(Operators.SINGLE_QUOTE), Character.valueOf(Operators.BRACKET_START), Character.valueOf(Operators.BRACKET_END)};
        for (int i4 = 0; i4 < 10; i4++) {
            this.shouldNotNeedEncoding.set(chArr[i4].charValue());
        }
        if (z) {
            return;
        }
        Character[] chArr2 = {';', Character.valueOf(Operators.ARRAY_SEPRATOR), '/', Character.valueOf(Operators.CONDITION_IF), Character.valueOf(Operators.CONDITION_IF_MIDDLE), Character.valueOf(TemplateDom.SEPARATOR), Character.valueOf(Typography.amp), '=', '+', '$', '#'};
        for (int i5 = 0; i5 < 11; i5++) {
            this.shouldNotNeedEncoding.set(chArr2[i5].charValue());
        }
    }

    public /* synthetic */ UTSURLEncoder(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z);
    }

    public static /* synthetic */ String encode$default(UTSURLEncoder uTSURLEncoder, String str, Charset dfltEncName, int i, Object obj) {
        if ((i & 2) != 0) {
            dfltEncName = uTSURLEncoder.dfltEncName;
            Intrinsics.checkNotNullExpressionValue(dfltEncName, "dfltEncName");
        }
        return uTSURLEncoder.encode(str, dfltEncName);
    }

    public final String encode(String s, Charset charset) {
        BitSet bitSet;
        int i;
        char cCharAt;
        Intrinsics.checkNotNullParameter(s, "s");
        Intrinsics.checkNotNullParameter(charset, "charset");
        StringBuilder sb = new StringBuilder(s.length());
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        int i2 = 0;
        boolean z = false;
        while (i2 < s.length()) {
            char cCharAt2 = s.charAt(i2);
            if (this.shouldNotNeedEncoding.get(cCharAt2)) {
                if (cCharAt2 == ' ') {
                    sb.append("%20");
                    z = true;
                } else {
                    sb.append(cCharAt2);
                }
                i2++;
            } else {
                do {
                    charArrayWriter.write(cCharAt2);
                    if (55296 <= cCharAt2 && cCharAt2 < 56320 && (i = i2 + 1) < s.length() && 56320 <= (cCharAt = s.charAt(i)) && cCharAt < 57344) {
                        charArrayWriter.write(cCharAt);
                        i2 = i;
                    }
                    i2++;
                    if (i2 >= s.length()) {
                        break;
                    }
                    bitSet = this.shouldNotNeedEncoding;
                    cCharAt2 = s.charAt(i2);
                } while (!bitSet.get(cCharAt2));
                charArrayWriter.flush();
                char[] charArray = charArrayWriter.toCharArray();
                Intrinsics.checkNotNullExpressionValue(charArray, "toCharArray(...)");
                byte[] bytes = new String(charArray).getBytes(charset);
                Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
                int length = bytes.length;
                for (int i3 = 0; i3 < length; i3++) {
                    sb.append(WXUtils.PERCENT);
                    char cForDigit = Character.forDigit((bytes[i3] >> 4) & 15, 16);
                    if (Character.isLetter(cForDigit)) {
                        cForDigit = (char) (cForDigit - this.caseDiff);
                    }
                    sb.append(cForDigit);
                    char cForDigit2 = Character.forDigit(bytes[i3] & 15, 16);
                    if (Character.isLetter(cForDigit2)) {
                        cForDigit2 = (char) (cForDigit2 - this.caseDiff);
                    }
                    sb.append(cForDigit2);
                }
                charArrayWriter.reset();
                z = true;
            }
        }
        if (!z) {
            return s;
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
