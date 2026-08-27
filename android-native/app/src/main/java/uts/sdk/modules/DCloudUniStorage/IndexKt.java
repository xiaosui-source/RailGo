package uts.sdk.modules.DCloudUniStorage;

import com.facebook.common.util.UriUtil;
import com.taobao.weex.common.Constants;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.common.constant.AbsoluteConst;
import io.dcloud.common.util.db.DCStorage;
import io.dcloud.uniapp.UniError;
import io.dcloud.uts.Date;
import io.dcloud.uts.JSON;
import io.dcloud.uts.Map;
import io.dcloud.uts.StringKt;
import io.dcloud.uts.UTSAndroid;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.UTSArrayKt;
import io.dcloud.uts.UTSCallback;
import io.dcloud.uts.UTSJSONObject;
import io.dcloud.uts.UTSJSONObjectKt;
import io.dcloud.uts.UTSRegExp;
import io.dcloud.uts.UTSTimerKt;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* compiled from: index.kt */
@Metadata(d1 = {"\u0000ú\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u000e\u00104\u001a\u00020\u00102\u0006\u00105\u001a\u00020\u0010\u001a\u0010\u00106\u001a\u0004\u0018\u00010\n2\u0006\u00107\u001a\u00020\n\u001a\u0018\u00108\u001a\u0004\u0018\u00010\n2\u0006\u00109\u001a\u00020\u00102\u0006\u00107\u001a\u00020\u0010\u001aq\u0010:\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\n26\u0010;\u001a2\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00060\u000f2!\u0010<\u001a\u001d\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00060\u0001\u001ai\u0010=\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\f26\u0010>\u001a2\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00060\u000f2!\u0010?\u001a\u001d\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00060\u0001\u001a5\u0010@\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\u00102#\u0010A\u001a\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u0001\u001aV\u0010B\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00182#\u0010C\u001a\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0006\u0012\u0004\u0018\u00010\u00100\u00012!\u0010D\u001a\u001d\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020E0\u0001\u001a\u000e\u0010g\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020h\u001a\u0016\u0010i\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\n\u001a\u000e\u0010j\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020k\u001a\u0010\u0010l\u001a\u0004\u0018\u00010\n2\u0006\u0010\u0011\u001a\u00020\u0010\u001a\u000e\u0010m\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020n\u001a\u0006\u0010o\u001a\u00020\u001b\u001a\u000e\u0010p\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020q\u001a\u000e\u0010r\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u0010\u001a\u0010\u0010s\u001a\u00020\u00062\b\u0010/\u001a\u0004\u0018\u00010t\u001a\u0006\u0010u\u001a\u00020\u0006\"\u0014\u00101\u001a\u00020\u0010X\u0086D¢\u0006\b\n\u0000\u001a\u0004\b2\u00103\"0\u0010F\u001a!\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`G¢\u0006\b\n\u0000\u001a\u0004\bH\u0010I\"E\u0010J\u001a6\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00060\u000fj\u0002`K¢\u0006\b\n\u0000\u001a\u0004\bL\u0010M\"0\u0010N\u001a!\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`O¢\u0006\b\n\u0000\u001a\u0004\bP\u0010I\"2\u0010Q\u001a#\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0001j\u0002`R¢\u0006\b\n\u0000\u001a\u0004\bS\u0010I\"0\u0010T\u001a!\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`U¢\u0006\b\n\u0000\u001a\u0004\bV\u0010I\"\u001b\u0010W\u001a\f\u0012\u0004\u0012\u00020\u001b0!j\u0002`X¢\u0006\b\n\u0000\u001a\u0004\bY\u0010Z\"0\u0010[\u001a!\u0012\u0013\u0012\u00110'¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`\\¢\u0006\b\n\u0000\u001a\u0004\b]\u0010I\"0\u0010^\u001a!\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`_¢\u0006\b\n\u0000\u001a\u0004\b`\u0010I\"2\u0010a\u001a#\u0012\u0015\u0012\u0013\u0018\u00010.¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(/\u0012\u0004\u0012\u00020\u00060\u0001j\u0002`b¢\u0006\b\n\u0000\u001a\u0004\bc\u0010I\"\u001b\u0010d\u001a\f\u0012\u0004\u0012\u00020\u00060!j\u0002`e¢\u0006\b\n\u0000\u001a\u0004\bf\u0010Z*@\u0010\u0000\"\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\u0007\"\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\t\"\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\u000b\"\u001d\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\f¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u0001*j\u0010\u000e\"2\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00060\u000f22\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u00060\u000f*@\u0010\u0013\"\u001d\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\u0014¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\u0015\"\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\u0016\"\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\u0017\"\u001d\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\u0018¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u0001*D\u0010\u0019\"\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0006\u0012\u0004\u0018\u00010\n0\u00012\u001f\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0006\u0012\u0004\u0018\u00010\n0\u0001*@\u0010\u001a\"\u001d\u0012\u0013\u0012\u00110\u001b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\u001b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\u001c\"\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\u001d\"\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010\u001e\"\u001d\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\u001f¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u0001*\u0016\u0010 \"\b\u0012\u0004\u0012\u00020\u001b0!2\b\u0012\u0004\u0012\u00020\u001b0!*@\u0010\"\"\u001d\u0012\u0013\u0012\u00110#¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110#¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010$\"\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010%\"\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010&\"\u001d\u0012\u0013\u0012\u00110'¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110'¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\r\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010(\"\u001d\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\u0010¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0011\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010)\"\u001d\u0012\u0013\u0012\u00110*¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110*¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010+\"\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\b¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*@\u0010,\"\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u00012\u001d\u0012\u0013\u0012\u00110\n¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(\u0005\u0012\u0004\u0012\u00020\u00060\u0001*D\u0010-\"\u001f\u0012\u0015\u0012\u0013\u0018\u00010.¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(/\u0012\u0004\u0012\u00020\u00060\u00012\u001f\u0012\u0015\u0012\u0013\u0018\u00010.¢\u0006\f\b\u0003\u0012\b\b\u0004\u0012\u0004\b\b(/\u0012\u0004\u0012\u00020\u00060\u0001*\u0016\u00100\"\b\u0012\u0004\u0012\u00020\u00060!2\b\u0012\u0004\u0012\u00020\u00060!¨\u0006v"}, d2 = {"SetStorageSuccessCallback", "Lkotlin/Function1;", "Luts/sdk/modules/DCloudUniStorage/SetStorageSuccess;", "Lkotlin/ParameterName;", "name", UriUtil.LOCAL_RESOURCE_SCHEME, "", "SetStorageFailCallback", "Lio/dcloud/uniapp/UniError;", "SetStorageCompleteCallback", "", "SetStorage", "Luts/sdk/modules/DCloudUniStorage/SetStorageOptions;", "options", "SetStorageSync", "Lkotlin/Function2;", "", IApp.ConfigProperty.CONFIG_KEY, "data", "GetStorageSuccessCallback", "Luts/sdk/modules/DCloudUniStorage/GetStorageSuccess;", "GetStorageFailCallback", "GetStorageCompleteCallback", "GetStorage", "Luts/sdk/modules/DCloudUniStorage/GetStorageOptions;", "GetStorageSync", "GetStorageInfoSuccessCallback", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfoSuccess;", "GetStorageInfoFailCallback", "GetStorageInfoCompleteCallback", "GetStorageInfo", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfoOptions;", "GetStorageInfoSync", "Lkotlin/Function0;", "RemoveStorageSuccessCallback", "Luts/sdk/modules/DCloudUniStorage/RemoveStorageSuccess;", "RemoveStorageFailCallback", "RemoveStorageCompleteCallback", "RemoveStorage", "Luts/sdk/modules/DCloudUniStorage/RemoveStorageOptions;", "RemoveStorageSync", "ClearStorageSuccessCallback", "Luts/sdk/modules/DCloudUniStorage/ClearStorageSuccess;", "ClearStorageFailCallback", "ClearStorageCompleteCallback", "ClearStorage", "Luts/sdk/modules/DCloudUniStorage/ClearStorageOptions;", AbsoluteConst.JSON_KEY_OPTION, "ClearStorageSync", "STORAGE_DATA_TYPE", "getSTORAGE_DATA_TYPE", "()Ljava/lang/String;", "filterNativeType", "src", "parseValue", "value", "praseGetStorage", "type", "uni_setStorageSync", "saveItemHandler", "removeItemHandler", "uni_setStorageAsync", "saveItemAsyncHandler", "removeItemAsyncHandler", "uni_getStorageSync", "getItemHandler", "uni_getStorageAsync", "getItemAsyncHandler", "includesKey", "", "setStorage", "Luts/sdk/modules/DCloudUniStorage/SetStorage;", "getSetStorage", "()Lkotlin/jvm/functions/Function1;", "setStorageSync", "Luts/sdk/modules/DCloudUniStorage/SetStorageSync;", "getSetStorageSync", "()Lkotlin/jvm/functions/Function2;", "getStorage", "Luts/sdk/modules/DCloudUniStorage/GetStorage;", "getGetStorage", "getStorageSync", "Luts/sdk/modules/DCloudUniStorage/GetStorageSync;", "getGetStorageSync", "getStorageInfo", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfo;", "getGetStorageInfo", "getStorageInfoSync", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfoSync;", "getGetStorageInfoSync", "()Lkotlin/jvm/functions/Function0;", "removeStorage", "Luts/sdk/modules/DCloudUniStorage/RemoveStorage;", "getRemoveStorage", "removeStorageSync", "Luts/sdk/modules/DCloudUniStorage/RemoveStorageSync;", "getRemoveStorageSync", "clearStorage", "Luts/sdk/modules/DCloudUniStorage/ClearStorage;", "getClearStorage", "clearStorageSync", "Luts/sdk/modules/DCloudUniStorage/ClearStorageSync;", "getClearStorageSync", "setStorageByJs", "Luts/sdk/modules/DCloudUniStorage/SetStorageOptionsJSONObject;", "setStorageSyncByJs", "getStorageByJs", "Luts/sdk/modules/DCloudUniStorage/GetStorageOptionsJSONObject;", "getStorageSyncByJs", "getStorageInfoByJs", "Luts/sdk/modules/DCloudUniStorage/GetStorageInfoOptionsJSONObject;", "getStorageInfoSyncByJs", "removeStorageByJs", "Luts/sdk/modules/DCloudUniStorage/RemoveStorageOptionsJSONObject;", "removeStorageSyncByJs", "clearStorageByJs", "Luts/sdk/modules/DCloudUniStorage/ClearStorageOptionsJSONObject;", "clearStorageSyncByJs", "uni-storage_release"}, k = 2, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class IndexKt {
    private static final String STORAGE_DATA_TYPE = "__TYPE";
    private static final Function1<SetStorageOptions, Unit> setStorage = new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda35
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.setStorage$lambda$3((SetStorageOptions) obj);
        }
    };
    private static final Function2<String, Object, Unit> setStorageSync = new Function2() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda36
        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return IndexKt.setStorageSync$lambda$6((String) obj, obj2);
        }
    };
    private static final Function1<GetStorageOptions, Unit> getStorage = new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda37
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.getStorage$lambda$12((GetStorageOptions) obj);
        }
    };
    private static final Function1<String, Object> getStorageSync = new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.getStorageSync$lambda$14((String) obj);
        }
    };
    private static final Function1<GetStorageInfoOptions, Unit> getStorageInfo = new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.getStorageInfo$lambda$17((GetStorageInfoOptions) obj);
        }
    };
    private static final Function0<GetStorageInfoSuccess> getStorageInfoSync = new Function0() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda3
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.getStorageInfoSync$lambda$19();
        }
    };
    private static final Function1<RemoveStorageOptions, Unit> removeStorage = new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda4
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.removeStorage$lambda$21((RemoveStorageOptions) obj);
        }
    };
    private static final Function1<String, Unit> removeStorageSync = new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda5
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.removeStorageSync$lambda$22((String) obj);
        }
    };
    private static final Function1<ClearStorageOptions, Unit> clearStorage = new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda6
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object invoke2(Object obj) {
            return IndexKt.clearStorage$lambda$24((ClearStorageOptions) obj);
        }
    };
    private static final Function0<Unit> clearStorageSync = new Function0() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda7
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return IndexKt.clearStorageSync$lambda$25();
        }
    };

    public static final String getSTORAGE_DATA_TYPE() {
        return STORAGE_DATA_TYPE;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0067 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static final java.lang.String filterNativeType(java.lang.String r1) {
        /*
            java.lang.String r0 = "src"
            kotlin.jvm.internal.Intrinsics.checkNotNullParameter(r1, r0)
            int r0 = r1.hashCode()
            switch(r0) {
                case -1781628633: goto L5e;
                case 73679: goto L55;
                case 2086184: goto L4c;
                case 2374300: goto L43;
                case 2605914: goto L3a;
                case 67973692: goto L31;
                case 79860828: goto L28;
                case 80585469: goto L1f;
                case 80873585: goto L16;
                case 2052876273: goto Ld;
                default: goto Lc;
            }
        Lc:
            goto L69
        Ld:
            java.lang.String r0 = "Double"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L16:
            java.lang.String r0 = "ULong"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L1f:
            java.lang.String r0 = "UByte"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L28:
            java.lang.String r0 = "Short"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L69
            goto L67
        L31:
            java.lang.String r0 = "Float"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L3a:
            java.lang.String r0 = "UInt"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L43:
            java.lang.String r0 = "Long"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L4c:
            java.lang.String r0 = "Byte"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L55:
            java.lang.String r0 = "Int"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L5e:
            java.lang.String r0 = "UShort"
            boolean r0 = r1.equals(r0)
            if (r0 != 0) goto L67
            goto L69
        L67:
            java.lang.String r1 = "number"
        L69:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: uts.sdk.modules.DCloudUniStorage.IndexKt.filterNativeType(java.lang.String):java.lang.String");
    }

    public static final Object parseValue(Object value) throws IllegalAccessException, IllegalArgumentException {
        boolean z;
        Map<String, Object> map;
        String str;
        Intrinsics.checkNotNullParameter(value, "value");
        UTSArray uTSArray_uA = UTSArrayKt._uA("object", "string", "number", "boolean", Constants.Name.UNDEFINED);
        if (Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(value), "string")) {
            value = JSON.parse((String) value);
        }
        if (value == null) {
            return null;
        }
        String strTypeof = UTSAndroid.INSTANCE.typeof(value);
        if (uTSArray_uA.indexOf(strTypeof) >= 0 && (((z = value instanceof UTSJSONObject)) || (value instanceof Map))) {
            if (z) {
                map = ((UTSJSONObject) value).toMap();
            } else {
                map = (Map) value;
            }
            if (map.size() == 2 && map.has("data") && map.has("type")) {
                if (map.get("type") == null) {
                    str = "";
                } else {
                    Object obj = map.get("type");
                    Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
                    str = (String) obj;
                }
                if (Intrinsics.areEqual(filterNativeType(UTSAndroid.INSTANCE.typeof(map.get("data"))), str) && !Intrinsics.areEqual(str, "string")) {
                    return map.get("data");
                }
                if (Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(map.get("data")), str) && Intrinsics.areEqual(str, "string")) {
                    UTSRegExp uTSRegExp = new UTSRegExp("^\\d{4}-\\d{2}-\\d{2}T\\d{2}\\:\\d{2}\\:\\d{2}\\.\\d{3}Z$", "");
                    if (Intrinsics.areEqual(strTypeof, "object")) {
                        Object obj2 = map.get("data");
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.String");
                        if (uTSRegExp.test((String) obj2)) {
                            Object obj3 = map.get("data");
                            Intrinsics.checkNotNull(obj3, "null cannot be cast to non-null type kotlin.String");
                            return new Date((String) obj3);
                        }
                    }
                    return map.get("data");
                }
            } else if (map.size() >= 1) {
                return "";
            }
        }
        return null;
    }

    public static final Object praseGetStorage(String type, String value) throws IllegalAccessException, IllegalArgumentException {
        Object obj;
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(value, "value");
        if ((!Intrinsics.areEqual(type, "string") || (Intrinsics.areEqual(type, "string") && Intrinsics.areEqual(value, "{\"type\":\"undefined\"}"))) && (obj = JSON.parse(value)) != null) {
            Object value2 = parseValue(obj);
            if (value2 != null) {
                return value2;
            }
            if (!Intrinsics.areEqual(type, "")) {
                if (Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(obj), "string")) {
                    Object obj2 = JSON.parse((String) obj);
                    String strTypeof = UTSAndroid.INSTANCE.typeof(obj2);
                    if (Intrinsics.areEqual(strTypeof, "number") && Intrinsics.areEqual(type, "date")) {
                        Intrinsics.checkNotNull(obj2, "null cannot be cast to non-null type kotlin.Number");
                        return new Date((Number) obj2);
                    }
                    if (UTSArrayKt._uA("null", "array").indexOf(type) >= 0) {
                        type = "object";
                    }
                    if (Intrinsics.areEqual(strTypeof, type)) {
                        return obj2;
                    }
                }
                return obj;
            }
        }
        return value;
    }

    public static final void uni_setStorageSync(String key, Object data, Function2<? super String, ? super String, Unit> saveItemHandler, Function1<? super String, Unit> removeItemHandler) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(data, "data");
        Intrinsics.checkNotNullParameter(saveItemHandler, "saveItemHandler");
        Intrinsics.checkNotNullParameter(removeItemHandler, "removeItemHandler");
        String strFilterNativeType = filterNativeType(UTSAndroid.INSTANCE.typeof(data));
        String strStringify = Intrinsics.areEqual(strFilterNativeType, "string") ? (String) data : JSON.stringify(UTSJSONObjectKt._uO(TuplesKt.to("type", strFilterNativeType), TuplesKt.to("data", data)));
        if (Intrinsics.areEqual(strFilterNativeType, "string") && parseValue(data) != null) {
            saveItemHandler.invoke(key + STORAGE_DATA_TYPE, strFilterNativeType);
        } else {
            removeItemHandler.invoke2(key + STORAGE_DATA_TYPE);
        }
        if (strStringify == null) {
            strStringify = "";
        }
        saveItemHandler.invoke(key, strStringify);
    }

    public static final void uni_setStorageAsync(SetStorageOptions options, Function2<? super String, ? super String, Unit> saveItemAsyncHandler, Function1<? super String, Unit> removeItemAsyncHandler) {
        String strStringify;
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(saveItemAsyncHandler, "saveItemAsyncHandler");
        Intrinsics.checkNotNullParameter(removeItemAsyncHandler, "removeItemAsyncHandler");
        String strFilterNativeType = filterNativeType(UTSAndroid.INSTANCE.typeof(options.getData()));
        if (Intrinsics.areEqual(strFilterNativeType, "string")) {
            Object data = options.getData();
            Intrinsics.checkNotNull(data, "null cannot be cast to non-null type kotlin.String");
            strStringify = (String) data;
        } else {
            strStringify = JSON.stringify(UTSJSONObjectKt._uO(TuplesKt.to("type", strFilterNativeType), TuplesKt.to("data", options.getData())));
        }
        if (strStringify == null) {
            UniError uniError = new UniError("uni-storage", (Number) (-1), "data can not be stringify");
            Function1<UniError, Unit> fail = options.getFail();
            if (fail != null) {
                fail.invoke2(uniError);
            }
            Function1<Object, Unit> complete = options.getComplete();
            if (complete != null) {
                complete.invoke2(uniError);
                return;
            }
            return;
        }
        if (Intrinsics.areEqual(strFilterNativeType, "string") && parseValue(options.getData()) != null) {
            saveItemAsyncHandler.invoke(options.getKey() + STORAGE_DATA_TYPE, strFilterNativeType);
        } else {
            removeItemAsyncHandler.invoke2(options.getKey() + STORAGE_DATA_TYPE);
        }
        saveItemAsyncHandler.invoke(options.getKey(), strStringify);
        SetStorageSuccess setStorageSuccess = new SetStorageSuccess();
        Function1<SetStorageSuccess, Unit> success = options.getSuccess();
        if (success != null) {
            success.invoke2(setStorageSuccess);
        }
        Function1<Object, Unit> complete2 = options.getComplete();
        if (complete2 != null) {
            complete2.invoke2(setStorageSuccess);
        }
    }

    public static final Object uni_getStorageSync(String key, Function1<? super String, String> getItemHandler) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(getItemHandler, "getItemHandler");
        String strInvoke2 = getItemHandler.invoke2(key);
        String strInvoke22 = getItemHandler.invoke2(key + STORAGE_DATA_TYPE);
        if (strInvoke22 == null) {
            strInvoke22 = "";
        }
        String lowerCase = StringKt.toLowerCase(strInvoke22);
        if (!Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(strInvoke2), "string")) {
            return "";
        }
        if (strInvoke2 == null) {
            strInvoke2 = "";
        }
        return praseGetStorage(lowerCase, strInvoke2);
    }

    public static final void uni_getStorageAsync(GetStorageOptions options, Function1<? super String, String> getItemAsyncHandler, Function1<? super String, Boolean> includesKey) {
        Intrinsics.checkNotNullParameter(options, "options");
        Intrinsics.checkNotNullParameter(getItemAsyncHandler, "getItemAsyncHandler");
        Intrinsics.checkNotNullParameter(includesKey, "includesKey");
        if (!includesKey.invoke2(options.getKey()).booleanValue()) {
            UniError uniError = new UniError("uni-storage", (Number) (-2), "getStorage:fail data not found");
            Function1<UniError, Unit> fail = options.getFail();
            if (fail != null) {
                fail.invoke2(uniError);
            }
            Function1<Object, Unit> complete = options.getComplete();
            if (complete != null) {
                complete.invoke2(uniError);
                return;
            }
            return;
        }
        String strInvoke2 = getItemAsyncHandler.invoke2(options.getKey());
        if (strInvoke2 == null) {
            strInvoke2 = "";
        }
        String strInvoke22 = getItemAsyncHandler.invoke2(options.getKey() + STORAGE_DATA_TYPE);
        if (strInvoke22 == null) {
            strInvoke22 = "";
        }
        String lowerCase = StringKt.toLowerCase(strInvoke22);
        if (!Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(strInvoke2), "string")) {
            GetStorageSuccess getStorageSuccess = new GetStorageSuccess("");
            Function1<GetStorageSuccess, Unit> success = options.getSuccess();
            if (success != null) {
                success.invoke2(getStorageSuccess);
            }
            Function1<Object, Unit> complete2 = options.getComplete();
            if (complete2 != null) {
                complete2.invoke2(getStorageSuccess);
                return;
            }
            return;
        }
        GetStorageSuccess getStorageSuccess2 = new GetStorageSuccess(praseGetStorage(lowerCase, strInvoke2));
        Function1<GetStorageSuccess, Unit> success2 = options.getSuccess();
        if (success2 != null) {
            success2.invoke2(getStorageSuccess2);
        }
        Function1<Object, Unit> complete3 = options.getComplete();
        if (complete3 != null) {
            complete3.invoke2(getStorageSuccess2);
        }
    }

    public static final Function1<SetStorageOptions, Unit> getSetStorage() {
        return setStorage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setStorage$lambda$3(final SetStorageOptions setStorageOptions) {
        UTSTimerKt.setTimeout(new Function0() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.setStorage$lambda$3$lambda$2(setStorageOptions);
            }
        }, (Number) 0);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, io.dcloud.common.util.db.DCStorage] */
    public static final Unit setStorage$lambda$3$lambda$2(SetStorageOptions setStorageOptions) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (objectRef.element == 0) {
            UniError uniError = new UniError("uni-setStorage", (Number) (-1), "storage not found.");
            Function1<UniError, Unit> fail = setStorageOptions.getFail();
            if (fail != null) {
                fail.invoke2(uniError);
            }
            Function1<Object, Unit> complete = setStorageOptions.getComplete();
            if (complete != null) {
                complete.invoke2(uniError);
            }
            return Unit.INSTANCE;
        }
        uni_setStorageAsync(setStorageOptions, new Function2() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda22
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                return IndexKt.setStorage$lambda$3$lambda$2$lambda$0(objectRef, (String) obj, (String) obj2);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda31
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.setStorage$lambda$3$lambda$2$lambda$1(objectRef, (String) obj);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setStorage$lambda$3$lambda$2$lambda$0(Ref.ObjectRef<DCStorage> objectRef, String str, String str2) {
        objectRef.element.performSetItem(UTSAndroid.INSTANCE.getAppContext(), UTSAndroid.INSTANCE.getAppId(), str, str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setStorage$lambda$3$lambda$2$lambda$1(Ref.ObjectRef<DCStorage> objectRef, String str) {
        objectRef.element.performRemoveItem(UTSAndroid.INSTANCE.getAppContext(), UTSAndroid.INSTANCE.getAppId(), str);
        return Unit.INSTANCE;
    }

    public static final Function2<String, Object, Unit> getSetStorageSync() {
        return setStorageSync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, io.dcloud.common.util.db.DCStorage] */
    public static final Unit setStorageSync$lambda$6(String str, Object obj) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (objectRef.element == 0) {
            return Unit.INSTANCE;
        }
        uni_setStorageSync(str, obj, new Function2() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj2, Object obj3) {
                return IndexKt.setStorageSync$lambda$6$lambda$4(objectRef, (String) obj2, (String) obj3);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj2) {
                return IndexKt.setStorageSync$lambda$6$lambda$5(objectRef, (String) obj2);
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setStorageSync$lambda$6$lambda$4(Ref.ObjectRef<DCStorage> objectRef, String str, String str2) {
        objectRef.element.performSetItem(UTSAndroid.INSTANCE.getAppContext(), UTSAndroid.INSTANCE.getAppId(), str, str2);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setStorageSync$lambda$6$lambda$5(Ref.ObjectRef<DCStorage> objectRef, String str) {
        objectRef.element.performRemoveItem(UTSAndroid.INSTANCE.getAppContext(), UTSAndroid.INSTANCE.getAppId(), str);
        return Unit.INSTANCE;
    }

    public static final Function1<GetStorageOptions, Unit> getGetStorage() {
        return getStorage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Type inference failed for: r1v2, types: [T, io.dcloud.common.util.db.DCStorage] */
    public static final Unit getStorage$lambda$12(final GetStorageOptions getStorageOptions) {
        final Ref.ObjectRef objectRef = new Ref.ObjectRef();
        objectRef.element = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (objectRef.element == 0) {
            UniError uniError = new UniError("uni-setStorage", (Number) (-1), "storage not found.");
            Function1<UniError, Unit> fail = getStorageOptions.getFail();
            if (fail != null) {
                fail.invoke2(uniError);
            }
            Function1<Object, Unit> complete = getStorageOptions.getComplete();
            if (complete != null) {
                complete.invoke2(uniError);
            }
            return Unit.INSTANCE;
        }
        UTSTimerKt.setTimeout(new Function0() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.getStorage$lambda$12$lambda$11(getStorageOptions, objectRef);
            }
        }, (Number) 0);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorage$lambda$12$lambda$11(GetStorageOptions getStorageOptions, final Ref.ObjectRef<DCStorage> objectRef) {
        uni_getStorageAsync(getStorageOptions, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.getStorage$lambda$12$lambda$11$lambda$7(objectRef, (String) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return Boolean.valueOf(IndexKt.getStorage$lambda$12$lambda$11$lambda$10(objectRef, (String) obj));
            }
        });
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getStorage$lambda$12$lambda$11$lambda$7(Ref.ObjectRef<DCStorage> objectRef, String str) {
        DCStorage.StorageInfo storageInfoPerformGetItem = objectRef.element.performGetItem(UTSAndroid.INSTANCE.getAppId(), str);
        if (storageInfoPerformGetItem == null || storageInfoPerformGetItem.code != 1 || storageInfoPerformGetItem.v == null) {
            return null;
        }
        Object obj = storageInfoPerformGetItem.v;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
        return (String) obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getStorage$lambda$12$lambda$11$lambda$10$lambda$9(String str, String str2) {
        if (!Intrinsics.areEqual(UTSAndroid.INSTANCE.typeof(str2), "string")) {
            return false;
        }
        Intrinsics.checkNotNull(str2, "null cannot be cast to non-null type kotlin.String");
        return Intrinsics.areEqual(str2, str);
    }

    public static final Function1<String, Object> getGetStorageSync() {
        return getStorageSync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Object getStorageSync$lambda$14(String str) {
        return uni_getStorageSync(str, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda33
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.getStorageSync$lambda$14$lambda$13((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String getStorageSync$lambda$14$lambda$13(String str) {
        DCStorage.StorageInfo storageInfoPerformGetItem;
        DCStorage dCStorage = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (dCStorage == null || (storageInfoPerformGetItem = dCStorage.performGetItem(UTSAndroid.INSTANCE.getAppId(), str)) == null || storageInfoPerformGetItem.code != 1 || storageInfoPerformGetItem.v == null) {
            return "";
        }
        Object obj = storageInfoPerformGetItem.v;
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type kotlin.String");
        return (String) obj;
    }

    public static final Function1<GetStorageInfoOptions, Unit> getGetStorageInfo() {
        return getStorageInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorageInfo$lambda$17(final GetStorageInfoOptions getStorageInfoOptions) {
        UTSTimerKt.setTimeout(new Function0() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.getStorageInfo$lambda$17$lambda$16(getStorageInfoOptions);
            }
        }, (Number) 0);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorageInfo$lambda$17$lambda$16(GetStorageInfoOptions getStorageInfoOptions) {
        DCStorage dCStorage = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (dCStorage == null) {
            UniError uniError = new UniError("uni-setStorage", (Number) (-1), "storage not found.");
            Function1<UniError, Unit> fail = getStorageInfoOptions.getFail();
            if (fail != null) {
                fail.invoke2(uniError);
            }
            Function1<Object, Unit> complete = getStorageInfoOptions.getComplete();
            if (complete != null) {
                complete.invoke2(uniError);
            }
        }
        GetStorageInfoSuccess getStorageInfoSuccess = new GetStorageInfoSuccess(new UTSArray(), (Number) 0, (Number) 10240);
        DCStorage.StorageInfo storageInfoPerformGetAllKeys = dCStorage.performGetAllKeys(UTSAndroid.INSTANCE.getAppId());
        if (storageInfoPerformGetAllKeys.code == 1 && storageInfoPerformGetAllKeys.v != null) {
            UTSArray<String> uTSArray = new UTSArray<>();
            Object obj = storageInfoPerformGetAllKeys.v;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.util.List<kotlin.String>");
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                uTSArray.push((String) it.next());
            }
            try {
                getStorageInfoSuccess.setKeys(uTSArray);
                double d = 1024;
                getStorageInfoSuccess.setLimitSize(Double.valueOf(dCStorage.getDBMaxLength(UTSAndroid.INSTANCE.getAppId()).longValue() / d));
                getStorageInfoSuccess.setCurrentSize(Double.valueOf(dCStorage.getDBCurrentLength(UTSAndroid.INSTANCE.getAppId()).longValue() / d));
            } catch (Throwable unused) {
            }
            Function1<GetStorageInfoSuccess, Unit> success = getStorageInfoOptions.getSuccess();
            if (success != null) {
                success.invoke2(getStorageInfoSuccess);
            }
        }
        return Unit.INSTANCE;
    }

    public static final Function0<GetStorageInfoSuccess> getGetStorageInfoSync() {
        return getStorageInfoSync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final GetStorageInfoSuccess getStorageInfoSync$lambda$19() {
        GetStorageInfoSuccess getStorageInfoSuccess = new GetStorageInfoSuccess(new UTSArray(), (Number) 0, (Number) 10240);
        DCStorage dCStorage = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (dCStorage != null) {
            DCStorage.StorageInfo storageInfoPerformGetAllKeys = dCStorage.performGetAllKeys(UTSAndroid.INSTANCE.getAppId());
            if (storageInfoPerformGetAllKeys.code == 1 && storageInfoPerformGetAllKeys.v != null) {
                UTSArray<String> uTSArray = new UTSArray<>();
                Object obj = storageInfoPerformGetAllKeys.v;
                Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.util.List<kotlin.String>");
                Iterator it = ((List) obj).iterator();
                while (it.hasNext()) {
                    uTSArray.push((String) it.next());
                }
                getStorageInfoSuccess.setKeys(uTSArray);
            }
            try {
                double d = 1024;
                getStorageInfoSuccess.setLimitSize(Double.valueOf(dCStorage.getDBMaxLength(UTSAndroid.INSTANCE.getAppId()).longValue() / d));
                getStorageInfoSuccess.setCurrentSize(Double.valueOf(dCStorage.getDBCurrentLength(UTSAndroid.INSTANCE.getAppId()).longValue() / d));
            } catch (Throwable unused) {
            }
        }
        return getStorageInfoSuccess;
    }

    public static final Function1<RemoveStorageOptions, Unit> getRemoveStorage() {
        return removeStorage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeStorage$lambda$21(final RemoveStorageOptions removeStorageOptions) {
        UTSTimerKt.setTimeout(new Function0() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda32
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.removeStorage$lambda$21$lambda$20(removeStorageOptions);
            }
        }, (Number) 0);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeStorage$lambda$21$lambda$20(RemoveStorageOptions removeStorageOptions) {
        DCStorage dCStorage = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (dCStorage == null) {
            UniError uniError = new UniError("uni-removeStorage", (Number) (-1), "storage not found.");
            Function1<UniError, Unit> fail = removeStorageOptions.getFail();
            if (fail != null) {
                fail.invoke2(uniError);
            }
            Function1<Object, Unit> complete = removeStorageOptions.getComplete();
            if (complete != null) {
                complete.invoke2(uniError);
            }
            return Unit.INSTANCE;
        }
        dCStorage.performRemoveItem(UTSAndroid.INSTANCE.getAppContext(), UTSAndroid.INSTANCE.getAppId(), removeStorageOptions.getKey());
        RemoveStorageSuccess removeStorageSuccess = new RemoveStorageSuccess();
        Function1<RemoveStorageSuccess, Unit> success = removeStorageOptions.getSuccess();
        if (success != null) {
            success.invoke2(removeStorageSuccess);
        }
        Function1<Object, Unit> complete2 = removeStorageOptions.getComplete();
        if (complete2 != null) {
            complete2.invoke2(removeStorageSuccess);
        }
        return Unit.INSTANCE;
    }

    public static final Function1<String, Unit> getRemoveStorageSync() {
        return removeStorageSync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeStorageSync$lambda$22(String str) {
        DCStorage dCStorage = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (dCStorage == null) {
            return Unit.INSTANCE;
        }
        dCStorage.performRemoveItem(UTSAndroid.INSTANCE.getAppContext(), UTSAndroid.INSTANCE.getAppId(), str);
        return Unit.INSTANCE;
    }

    public static final Function1<ClearStorageOptions, Unit> getClearStorage() {
        return clearStorage;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit clearStorage$lambda$24(final ClearStorageOptions clearStorageOptions) {
        UTSTimerKt.setTimeout(new Function0() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return IndexKt.clearStorage$lambda$24$lambda$23(clearStorageOptions);
            }
        }, (Number) 0);
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit clearStorage$lambda$24$lambda$23(ClearStorageOptions clearStorageOptions) {
        Function1<Object, Unit> complete;
        Function1<ClearStorageSuccess, Unit> success;
        Function1<Object, Unit> complete2;
        Function1<UniError, Unit> fail;
        DCStorage dCStorage = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (dCStorage == null) {
            UniError uniError = new UniError("uni-clearStorage", (Number) (-1), "error:storage not found.");
            if (clearStorageOptions != null && (fail = clearStorageOptions.getFail()) != null) {
                fail.invoke2(uniError);
            }
            if (clearStorageOptions != null && (complete2 = clearStorageOptions.getComplete()) != null) {
                complete2.invoke2(uniError);
            }
            return Unit.INSTANCE;
        }
        dCStorage.performClear(UTSAndroid.INSTANCE.getAppContext(), UTSAndroid.INSTANCE.getAppId());
        ClearStorageSuccess clearStorageSuccess = new ClearStorageSuccess();
        if (clearStorageOptions != null && (success = clearStorageOptions.getSuccess()) != null) {
            success.invoke2(clearStorageSuccess);
        }
        if (clearStorageOptions != null && (complete = clearStorageOptions.getComplete()) != null) {
            complete.invoke2(clearStorageSuccess);
        }
        return Unit.INSTANCE;
    }

    public static final Function0<Unit> getClearStorageSync() {
        return clearStorageSync;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit clearStorageSync$lambda$25() {
        DCStorage dCStorage = DCStorage.getDCStorage(UTSAndroid.INSTANCE.getAppContext());
        if (dCStorage == null) {
            return Unit.INSTANCE;
        }
        dCStorage.performClear(UTSAndroid.INSTANCE.getAppContext(), UTSAndroid.INSTANCE.getAppId());
        return Unit.INSTANCE;
    }

    public static final void setStorageByJs(final SetStorageOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        setStorage.invoke2(new SetStorageOptions(options.getKey(), options.getData(), new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.setStorageByJs$lambda$26(options, (SetStorageSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.setStorageByJs$lambda$27(options, (UniError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.setStorageByJs$lambda$28(options, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setStorageByJs$lambda$26(SetStorageOptionsJSONObject setStorageOptionsJSONObject, SetStorageSuccess setStorageSuccess) throws SecurityException {
        UTSCallback success = setStorageOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(setStorageSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setStorageByJs$lambda$27(SetStorageOptionsJSONObject setStorageOptionsJSONObject, UniError uniError) throws SecurityException {
        UTSCallback fail = setStorageOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(uniError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit setStorageByJs$lambda$28(SetStorageOptionsJSONObject setStorageOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = setStorageOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final void setStorageSyncByJs(String key, Object data) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(data, "data");
        setStorageSync.invoke(key, data);
    }

    public static final void getStorageByJs(final GetStorageOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        getStorage.invoke2(new GetStorageOptions(options.getKey(), new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda25
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.getStorageByJs$lambda$29(options, (GetStorageSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda26
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.getStorageByJs$lambda$30(options, (UniError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda27
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.getStorageByJs$lambda$31(options, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorageByJs$lambda$29(GetStorageOptionsJSONObject getStorageOptionsJSONObject, GetStorageSuccess getStorageSuccess) throws SecurityException {
        UTSCallback success = getStorageOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(getStorageSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorageByJs$lambda$30(GetStorageOptionsJSONObject getStorageOptionsJSONObject, UniError uniError) throws SecurityException {
        UTSCallback fail = getStorageOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(uniError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorageByJs$lambda$31(GetStorageOptionsJSONObject getStorageOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = getStorageOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final Object getStorageSyncByJs(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        return getStorageSync.invoke2(key);
    }

    public static final void getStorageInfoByJs(final GetStorageInfoOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        getStorageInfo.invoke2(new GetStorageInfoOptions(new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda28
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.getStorageInfoByJs$lambda$32(options, (GetStorageInfoSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda29
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.getStorageInfoByJs$lambda$33(options, (UniError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda30
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.getStorageInfoByJs$lambda$34(options, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorageInfoByJs$lambda$32(GetStorageInfoOptionsJSONObject getStorageInfoOptionsJSONObject, GetStorageInfoSuccess getStorageInfoSuccess) throws SecurityException {
        UTSCallback success = getStorageInfoOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(getStorageInfoSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorageInfoByJs$lambda$33(GetStorageInfoOptionsJSONObject getStorageInfoOptionsJSONObject, UniError uniError) throws SecurityException {
        UTSCallback fail = getStorageInfoOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(uniError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit getStorageInfoByJs$lambda$34(GetStorageInfoOptionsJSONObject getStorageInfoOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = getStorageInfoOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final GetStorageInfoSuccess getStorageInfoSyncByJs() {
        return getStorageInfoSync.invoke();
    }

    public static final void removeStorageByJs(final RemoveStorageOptionsJSONObject options) {
        Intrinsics.checkNotNullParameter(options, "options");
        removeStorage.invoke2(new RemoveStorageOptions(options.getKey(), new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.removeStorageByJs$lambda$35(options, (RemoveStorageSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda23
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.removeStorageByJs$lambda$36(options, (UniError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda24
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.removeStorageByJs$lambda$37(options, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeStorageByJs$lambda$35(RemoveStorageOptionsJSONObject removeStorageOptionsJSONObject, RemoveStorageSuccess removeStorageSuccess) throws SecurityException {
        UTSCallback success = removeStorageOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(removeStorageSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeStorageByJs$lambda$36(RemoveStorageOptionsJSONObject removeStorageOptionsJSONObject, UniError uniError) throws SecurityException {
        UTSCallback fail = removeStorageOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(uniError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit removeStorageByJs$lambda$37(RemoveStorageOptionsJSONObject removeStorageOptionsJSONObject, Object obj) throws SecurityException {
        UTSCallback complete = removeStorageOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final void removeStorageSyncByJs(String key) {
        Intrinsics.checkNotNullParameter(key, "key");
        removeStorageSync.invoke2(key);
    }

    public static final void clearStorageByJs(final ClearStorageOptionsJSONObject clearStorageOptionsJSONObject) {
        clearStorage.invoke2(clearStorageOptionsJSONObject != null ? new ClearStorageOptions(new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.clearStorageByJs$lambda$38(clearStorageOptionsJSONObject, (ClearStorageSuccess) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.clearStorageByJs$lambda$39(clearStorageOptionsJSONObject, (UniError) obj);
            }
        }, new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj) {
                return IndexKt.clearStorageByJs$lambda$40(clearStorageOptionsJSONObject, obj);
            }
        }) : null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit clearStorageByJs$lambda$38(ClearStorageOptionsJSONObject clearStorageOptionsJSONObject, ClearStorageSuccess clearStorageSuccess) throws SecurityException {
        Intrinsics.checkNotNull(clearStorageOptionsJSONObject);
        UTSCallback success = clearStorageOptionsJSONObject.getSuccess();
        if (success != null) {
            success.invoke(clearStorageSuccess);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit clearStorageByJs$lambda$39(ClearStorageOptionsJSONObject clearStorageOptionsJSONObject, UniError uniError) throws SecurityException {
        Intrinsics.checkNotNull(clearStorageOptionsJSONObject);
        UTSCallback fail = clearStorageOptionsJSONObject.getFail();
        if (fail != null) {
            fail.invoke(uniError);
        }
        return Unit.INSTANCE;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit clearStorageByJs$lambda$40(ClearStorageOptionsJSONObject clearStorageOptionsJSONObject, Object obj) throws SecurityException {
        Intrinsics.checkNotNull(clearStorageOptionsJSONObject);
        UTSCallback complete = clearStorageOptionsJSONObject.getComplete();
        if (complete != null) {
            complete.invoke(obj);
        }
        return Unit.INSTANCE;
    }

    public static final void clearStorageSyncByJs() {
        clearStorageSync.invoke();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean getStorage$lambda$12$lambda$11$lambda$10(Ref.ObjectRef<DCStorage> objectRef, final String str) {
        UTSArray uTSArray = new UTSArray();
        DCStorage.StorageInfo storageInfoPerformGetAllKeys = objectRef.element.performGetAllKeys(UTSAndroid.INSTANCE.getAppId());
        if (storageInfoPerformGetAllKeys.code == 1 && storageInfoPerformGetAllKeys.v != null) {
            uTSArray = new UTSArray();
            Object obj = storageInfoPerformGetAllKeys.v;
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type java.util.List<kotlin.String>");
            Iterator it = ((List) obj).iterator();
            while (it.hasNext()) {
                uTSArray.push((String) it.next());
            }
        }
        return ((String) uTSArray.find(new Function1() { // from class: uts.sdk.modules.DCloudUniStorage.IndexKt$$ExternalSyntheticLambda34
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object invoke2(Object obj2) {
                return Boolean.valueOf(IndexKt.getStorage$lambda$12$lambda$11$lambda$10$lambda$9(str, (String) obj2));
            }
        })) != null;
    }
}
