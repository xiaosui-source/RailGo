package io.dcloud.uts.nativeregex;

import com.taobao.weex.el.parse.Operators;
import io.dcloud.uts.RegExpExecArray;
import java.util.Arrays;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

/* compiled from: JSRegexp.kt */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0010\u0018\u0000 (2\u00020\u0001:\u0002()B!\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0004\b\u0007\u0010\bJ\u001a\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u00052\b\b\u0002\u0010\u001c\u001a\u00020\u000fJ\u000e\u0010\u001d\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u0005J\u0006\u0010\u001e\u001a\u00020\u0005J\u0006\u0010\u001f\u001a\u00020\u0016J\u0006\u0010 \u001a\u00020\u0016J\u0006\u0010!\u001a\u00020\u0016J\u0006\u0010\"\u001a\u00020\u0016J\u0006\u0010#\u001a\u00020\u0016J\u0006\u0010$\u001a\u00020\u0016J\u0006\u0010%\u001a\u00020\u0016J\u0006\u0010&\u001a\u00020\u0016J\b\u0010'\u001a\u00020\u0005H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR&\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u000f8F@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0015\u001a\u00020\u00168BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018¨\u0006*"}, d2 = {"Lio/dcloud/uts/nativeregex/JSRegexp;", "", "bytecode", "", "pattern", "", "flags", "<init>", "([BLjava/lang/String;Ljava/lang/String;)V", "getBytecode", "()[B", "getPattern", "()Ljava/lang/String;", "getFlags", "value", "", "lastIndex", "getLastIndex", "()I", "setLastIndex", "(I)V", "hasGlobalOrSticky", "", "getHasGlobalOrSticky", "()Z", "exec", "Lio/dcloud/uts/RegExpExecArray;", "input", "startIndex", "test", "getSource", "isGlobal", "isIgnoreCase", "isMultiline", "isDotAll", "isUnicode", "isUnicodeSets", "isSticky", "hasIndices", "toString", "Companion", "MatchResult", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class JSRegexp {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final byte[] bytecode;
    private final String flags;
    private int lastIndex;
    private final String pattern;

    public /* synthetic */ JSRegexp(byte[] bArr, String str, String str2, DefaultConstructorMarker defaultConstructorMarker) {
        this(bArr, str, str2);
    }

    private JSRegexp(byte[] bArr, String str, String str2) {
        this.bytecode = bArr;
        this.pattern = str;
        this.flags = str2;
    }

    public final byte[] getBytecode() {
        return this.bytecode;
    }

    public final String getPattern() {
        return this.pattern;
    }

    public final String getFlags() {
        return this.flags;
    }

    /* compiled from: JSRegexp.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0018\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007¨\u0006\t"}, d2 = {"Lio/dcloud/uts/nativeregex/JSRegexp$Companion;", "", "<init>", "()V", "create", "Lio/dcloud/uts/nativeregex/JSRegexp;", "pattern", "", "flags", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ JSRegexp create$default(Companion companion, String str, String str2, int i, Object obj) {
            if ((i & 2) != 0) {
                str2 = "";
            }
            return companion.create(str, str2);
        }

        public final JSRegexp create(String pattern, String flags) {
            Intrinsics.checkNotNullParameter(pattern, "pattern");
            Intrinsics.checkNotNullParameter(flags, "flags");
            int length = flags.length();
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = flags.charAt(i2);
                if (cCharAt == 'd') {
                    i |= 64;
                } else if (cCharAt == 'g') {
                    i |= 1;
                } else if (cCharAt == 'i') {
                    i |= 2;
                } else if (cCharAt == 'm') {
                    i |= 4;
                } else if (cCharAt == 's') {
                    i |= 8;
                } else if (cCharAt == 'y') {
                    i |= 32;
                } else if (cCharAt == 'u') {
                    i |= 16;
                } else {
                    if (cCharAt != 'v') {
                        throw new IllegalArgumentException("无效的正则表达式标志: " + cCharAt);
                    }
                    i |= 128;
                }
            }
            return new JSRegexp(RegexpBridge.INSTANCE.compileRegexp(pattern, i), pattern, flags, null);
        }
    }

    /* compiled from: JSRegexp.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001BY\u0012\u000e\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0004\u0012\u0018\b\u0002\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\t\u0012\u0016\b\u0002\u0010\n\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0003\u0018\u00010\u0003¢\u0006\u0004\b\u000b\u0010\fJ\u0013\u0010\u001b\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0086\u0002J\u0013\u0010\u001c\u001a\u00020\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u001f\u001a\u00020\u0006H\u0016J\u0016\u0010 \u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003HÆ\u0003¢\u0006\u0002\u0010\u000eJ\t\u0010!\u001a\u00020\u0006HÆ\u0003J\t\u0010\"\u001a\u00020\u0004HÆ\u0003J\u0019\u0010#\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\tHÆ\u0003J\u001c\u0010$\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0003\u0018\u00010\u0003HÆ\u0003¢\u0006\u0002\u0010\u0017Jf\u0010%\u001a\u00020\u00002\u0010\b\u0002\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00042\u0018\b\u0002\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\t2\u0016\b\u0002\u0010\n\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0003\u0018\u00010\u0003HÆ\u0001¢\u0006\u0002\u0010&J\t\u0010'\u001a\u00020\u0004HÖ\u0001R\u001b\u0010\u0002\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003¢\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R!\u0010\b\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R!\u0010\n\u001a\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u0003\u0018\u00010\u0003¢\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0019\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u0011¨\u0006("}, d2 = {"Lio/dcloud/uts/nativeregex/JSRegexp$MatchResult;", "", "matches", "", "", "index", "", "input", "namedGroups", "", "matchIndices", "<init>", "([Ljava/lang/String;ILjava/lang/String;Ljava/util/Map;[[Ljava/lang/Integer;)V", "getMatches", "()[Ljava/lang/String;", "[Ljava/lang/String;", "getIndex", "()I", "getInput", "()Ljava/lang/String;", "getNamedGroups", "()Ljava/util/Map;", "getMatchIndices", "()[[Ljava/lang/Integer;", "[[Ljava/lang/Integer;", "length", "getLength", "get", "equals", "", "other", "hashCode", "component1", "component2", "component3", "component4", "component5", "copy", "([Ljava/lang/String;ILjava/lang/String;Ljava/util/Map;[[Ljava/lang/Integer;)Lio/dcloud/uts/nativeregex/JSRegexp$MatchResult;", "toString", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final /* data */ class MatchResult {
        private final int index;
        private final String input;
        private final Integer[][] matchIndices;
        private final String[] matches;
        private final Map<String, String> namedGroups;

        public static /* synthetic */ MatchResult copy$default(MatchResult matchResult, String[] strArr, int i, String str, Map map, Integer[][] numArr, int i2, Object obj) {
            if ((i2 & 1) != 0) {
                strArr = matchResult.matches;
            }
            if ((i2 & 2) != 0) {
                i = matchResult.index;
            }
            if ((i2 & 4) != 0) {
                str = matchResult.input;
            }
            if ((i2 & 8) != 0) {
                map = matchResult.namedGroups;
            }
            if ((i2 & 16) != 0) {
                numArr = matchResult.matchIndices;
            }
            Integer[][] numArr2 = numArr;
            String str2 = str;
            return matchResult.copy(strArr, i, str2, map, numArr2);
        }

        /* renamed from: component1, reason: from getter */
        public final String[] getMatches() {
            return this.matches;
        }

        /* renamed from: component2, reason: from getter */
        public final int getIndex() {
            return this.index;
        }

        /* renamed from: component3, reason: from getter */
        public final String getInput() {
            return this.input;
        }

        public final Map<String, String> component4() {
            return this.namedGroups;
        }

        /* renamed from: component5, reason: from getter */
        public final Integer[][] getMatchIndices() {
            return this.matchIndices;
        }

        public final MatchResult copy(String[] matches, int index, String input, Map<String, String> namedGroups, Integer[][] matchIndices) {
            Intrinsics.checkNotNullParameter(matches, "matches");
            Intrinsics.checkNotNullParameter(input, "input");
            return new MatchResult(matches, index, input, namedGroups, matchIndices);
        }

        public String toString() {
            return "MatchResult(matches=" + Arrays.toString(this.matches) + ", index=" + this.index + ", input=" + this.input + ", namedGroups=" + this.namedGroups + ", matchIndices=" + Arrays.toString(this.matchIndices) + Operators.BRACKET_END;
        }

        public MatchResult(String[] matches, int i, String input, Map<String, String> map, Integer[][] numArr) {
            Intrinsics.checkNotNullParameter(matches, "matches");
            Intrinsics.checkNotNullParameter(input, "input");
            this.matches = matches;
            this.index = i;
            this.input = input;
            this.namedGroups = map;
            this.matchIndices = numArr;
        }

        public /* synthetic */ MatchResult(String[] strArr, int i, String str, Map map, Integer[][] numArr, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            this(strArr, i, str, (i2 & 8) != 0 ? null : map, (i2 & 16) != 0 ? null : numArr);
        }

        public final String[] getMatches() {
            return this.matches;
        }

        public final int getIndex() {
            return this.index;
        }

        public final String getInput() {
            return this.input;
        }

        public final Map<String, String> getNamedGroups() {
            return this.namedGroups;
        }

        public final Integer[][] getMatchIndices() {
            return this.matchIndices;
        }

        public final int getLength() {
            return this.matches.length;
        }

        public final String get(int index) {
            return this.matches[index];
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
                return false;
            }
            Intrinsics.checkNotNull(other, "null cannot be cast to non-null type io.dcloud.uts.nativeregex.JSRegexp.MatchResult");
            MatchResult matchResult = (MatchResult) other;
            return Arrays.equals(this.matches, matchResult.matches) && this.index == matchResult.index && Intrinsics.areEqual(this.input, matchResult.input);
        }

        public int hashCode() {
            return (((Arrays.hashCode(this.matches) * 31) + this.index) * 31) + this.input.hashCode();
        }
    }

    public final int getLastIndex() {
        return this.lastIndex;
    }

    public final void setLastIndex(int i) {
        if (i < 0) {
            this.lastIndex = 0;
        } else {
            this.lastIndex = i;
        }
    }

    private final boolean getHasGlobalOrSticky() {
        return StringsKt.contains$default((CharSequence) this.flags, 'g', false, 2, (Object) null) || StringsKt.contains$default((CharSequence) this.flags, 'y', false, 2, (Object) null);
    }

    public static /* synthetic */ RegExpExecArray exec$default(JSRegexp jSRegexp, String str, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -1;
        }
        return jSRegexp.exec(str, i);
    }

    public final RegExpExecArray exec(String input, int startIndex) {
        Intrinsics.checkNotNullParameter(input, "input");
        if (startIndex < 0) {
            startIndex = getHasGlobalOrSticky() ? getLastIndex() : 0;
        }
        RegExpExecArray regExpExecArrayExecRegexp = RegexpBridge.INSTANCE.execRegexp(this.bytecode, input, startIndex);
        if (regExpExecArrayExecRegexp == null) {
            if (getHasGlobalOrSticky()) {
                setLastIndex(0);
            }
            return null;
        }
        if (getHasGlobalOrSticky()) {
            Object orNull = CollectionsKt.getOrNull(regExpExecArrayExecRegexp, 0);
            String str = orNull instanceof String ? (String) orNull : null;
            setLastIndex(regExpExecArrayExecRegexp.getIndex() + (str != null ? str.length() : 0));
        }
        return regExpExecArrayExecRegexp;
    }

    public final boolean test(String input) {
        Intrinsics.checkNotNullParameter(input, "input");
        return exec$default(this, input, 0, 2, null) != null;
    }

    public final String getSource() {
        return RegexpBridge.INSTANCE.getSource(this.pattern);
    }

    public final boolean isGlobal() {
        return StringsKt.contains$default((CharSequence) this.flags, 'g', false, 2, (Object) null);
    }

    public final boolean isIgnoreCase() {
        return StringsKt.contains$default((CharSequence) this.flags, 'i', false, 2, (Object) null);
    }

    public final boolean isMultiline() {
        return StringsKt.contains$default((CharSequence) this.flags, 'm', false, 2, (Object) null);
    }

    public final boolean isDotAll() {
        return StringsKt.contains$default((CharSequence) this.flags, 's', false, 2, (Object) null);
    }

    public final boolean isUnicode() {
        return StringsKt.contains$default((CharSequence) this.flags, 'u', false, 2, (Object) null);
    }

    public final boolean isUnicodeSets() {
        return StringsKt.contains$default((CharSequence) this.flags, 'v', false, 2, (Object) null);
    }

    public final boolean isSticky() {
        return StringsKt.contains$default((CharSequence) this.flags, 'y', false, 2, (Object) null);
    }

    public final boolean hasIndices() {
        return StringsKt.contains$default((CharSequence) this.flags, 'd', false, 2, (Object) null);
    }

    public String toString() {
        return "/" + this.pattern + '/' + this.flags;
    }
}
