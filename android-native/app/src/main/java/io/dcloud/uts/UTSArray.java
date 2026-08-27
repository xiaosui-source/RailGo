package io.dcloud.uts;

import com.alibaba.android.bindingx.core.internal.BindingXConstants;
import com.taobao.weex.bridge.WXBridgeManager;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import com.taobao.weex.ui.component.WXBasicComponentType;
import io.dcloud.common.DHInterface.IApp;
import io.dcloud.uts.UTSArray;
import io.dcloud.uts.utils.IndexKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.text.Charsets;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.SupervisorKt;

/* compiled from: UTSArray.kt */
@Metadata(d1 = {"\u0000r\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0002\b\r\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010)\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u001c\b\u0016\u0018\u0000 k*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001kB\u0007¢\u0006\u0004\b\u0003\u0010\u0004B\u0017\b\u0016\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006¢\u0006\u0004\b\u0003\u0010\u0007B\u001d\b\u0016\u0012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\t\"\u00028\u0000¢\u0006\u0004\b\u0003\u0010\nB\u0019\b\u0016\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00028\u0000¢\u0006\u0004\b\u0003\u0010\u000eJ\u0016\u0010\u0015\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\fH\u0086\u0002¢\u0006\u0002\u0010\u0017J\u000e\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0016J'\u0010\u0019\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u001a0\t\"\u00020\u001aH\u0016¢\u0006\u0002\u0010\u001bJ.\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001d\u001a\u00020\f2\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\fH\u0016J+\u0010 \u001a\u00020!2!\u0010\"\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020!0#H\u0016J@\u0010 \u001a\u00020!26\u0010\"\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020!0&H\u0016J[\u0010 \u001a\u00020!2Q\u0010\"\u001aM\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u00020!0'H\u0016J3\u0010)\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u000f\u001a\u00028\u00002\n\b\u0002\u0010\u001e\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010\fH\u0016¢\u0006\u0002\u0010*J1\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002!\u0010\"\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020!0#H\u0016JF\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\u000026\u0010\"\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020!0&H\u0016Ja\u0010+\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002Q\u0010\"\u001aM\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u00020!0'H\u0016J2\u0010,\u001a\u0004\u0018\u00018\u00002!\u0010\"\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020!0#H\u0016¢\u0006\u0002\u0010-JG\u0010,\u001a\u0004\u0018\u00018\u000026\u0010\"\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020!0&H\u0016¢\u0006\u0002\u0010.Jb\u0010,\u001a\u0004\u0018\u00018\u00002Q\u0010\"\u001aM\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u00020!0'H\u0016¢\u0006\u0002\u0010/J+\u00100\u001a\u0002012!\u0010\"\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020!0#H\u0016J@\u00100\u001a\u00020126\u0010\"\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020!0&H\u0016J[\u00100\u001a\u0002012Q\u0010\"\u001aM\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u00020!0'H\u0016J\u0016\u00102\u001a\u0006\u0012\u0002\b\u00030\u00002\b\b\u0002\u00103\u001a\u00020\fH\u0016J3\u00104\u001a\u0006\u0012\u0002\b\u00030\u00002%\u0010\"\u001a!\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000#H\u0016JH\u00104\u001a\u0006\u0012\u0002\b\u00030\u00002:\u0010\"\u001a6\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000&H\u0016Jc\u00104\u001a\u0006\u0012\u0002\b\u00030\u00002U\u0010\"\u001aQ\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00000'H\u0016J\u0015\u00105\u001a\u00020!2\u0006\u00106\u001a\u00028\u0000H\u0016¢\u0006\u0002\u00107J\u001d\u00105\u001a\u00020!2\u0006\u00106\u001a\u00028\u00002\u0006\u00108\u001a\u00020\fH\u0016¢\u0006\u0002\u00109J@\u0010:\u001a\u00020;26\u0010\"\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020;0&H\u0016J[\u0010:\u001a\u00020;2Q\u0010\"\u001aM\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u00020;0'H\u0016J\u0015\u0010<\u001a\u0002012\u0006\u00106\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010=J\u001f\u0010<\u001a\u0002012\u0006\u00106\u001a\u00028\u00002\b\b\u0002\u00108\u001a\u00020\fH\u0016¢\u0006\u0002\u0010>J\u0012\u0010?\u001a\u00020@2\b\b\u0002\u0010A\u001a\u00020@H\u0016J\u000e\u0010B\u001a\b\u0012\u0004\u0012\u00020\f0CH\u0016J\u0015\u0010D\u001a\u0002012\u0006\u00106\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010=J\u001f\u0010D\u001a\u0002012\u0006\u00106\u001a\u00028\u00002\b\b\u0002\u00108\u001a\u00020\fH\u0016¢\u0006\u0002\u0010>J7\u0010E\u001a\b\u0012\u0004\u0012\u0002HF0\u0000\"\u0004\b\u0001\u0010F2!\u0010\"\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u0002HF0#H\u0016JL\u0010E\u001a\b\u0012\u0004\u0012\u0002HF0\u0000\"\u0004\b\u0001\u0010F26\u0010\"\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u0002HF0&H\u0016Jg\u0010E\u001a\b\u0012\u0004\u0012\u0002HF0\u0000\"\u0004\b\u0001\u0010F2Q\u0010\"\u001aM\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u0002HF0'H\u0016J\u000f\u0010G\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010HJ!\u0010I\u001a\u0002012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\t\"\u00028\u0000H\u0016¢\u0006\u0002\u0010JJd\u0010K\u001a\u00028\u0000\"\b\b\u0001\u0010L*\u00028\u00002K\u0010\"\u001aG\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u0011HL¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(O\u0012\u0004\u0012\u00028\u00000'H\u0016¢\u0006\u0002\u0010/J\u007f\u0010K\u001a\u00028\u0000\"\b\b\u0001\u0010L*\u00028\u00002f\u0010\"\u001ab\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u0011HL¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(O\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u00028\u00000PH\u0016¢\u0006\u0002\u0010QJS\u0010K\u001a\u0002HR\"\u0004\b\u0001\u0010R26\u0010\"\u001a2\u0012\u0013\u0012\u0011HR¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HR0&2\u0006\u0010S\u001a\u0002HRH\u0016¢\u0006\u0002\u0010TJh\u0010K\u001a\u0002HR\"\u0004\b\u0001\u0010R2K\u0010\"\u001aG\u0012\u0013\u0012\u0011HR¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(O\u0012\u0004\u0012\u0002HR0'2\u0006\u0010S\u001a\u0002HRH\u0016¢\u0006\u0002\u0010UJ\u0083\u0001\u0010K\u001a\u0002HR\"\u0004\b\u0001\u0010R2f\u0010\"\u001ab\u0012\u0013\u0012\u0011HR¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(O\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u0002HR0P2\u0006\u0010S\u001a\u0002HRH\u0016¢\u0006\u0002\u0010VJO\u0010W\u001a\u00028\u0000\"\b\b\u0001\u0010L*\u00028\u000026\u0010\"\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u0011HL¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0004\u0012\u00028\u00000&H\u0016¢\u0006\u0002\u0010.Jd\u0010W\u001a\u00028\u0000\"\b\b\u0001\u0010L*\u00028\u00002K\u0010\"\u001aG\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u0011HL¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(O\u0012\u0004\u0012\u00028\u00000'H\u0016¢\u0006\u0002\u0010/J\u007f\u0010W\u001a\u00028\u0000\"\b\b\u0001\u0010L*\u00028\u00002f\u0010\"\u001ab\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u0011HL¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(O\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u00028\u00000PH\u0016¢\u0006\u0002\u0010QJS\u0010W\u001a\u0002HR\"\u0004\b\u0001\u0010R26\u0010\"\u001a2\u0012\u0013\u0012\u0011HR¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HR0&2\u0006\u0010S\u001a\u0002HRH\u0016¢\u0006\u0002\u0010TJh\u0010W\u001a\u0002HR\"\u0004\b\u0001\u0010R2K\u0010\"\u001aG\u0012\u0013\u0012\u0011HR¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(O\u0012\u0004\u0012\u0002HR0'2\u0006\u0010S\u001a\u0002HRH\u0016¢\u0006\u0002\u0010UJ\u0083\u0001\u0010W\u001a\u0002HR\"\u0004\b\u0001\u0010R2f\u0010\"\u001ab\u0012\u0013\u0012\u0011HR¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(M\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(N\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(O\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u0002HR0P2\u0006\u0010S\u001a\u0002HRH\u0016¢\u0006\u0002\u0010VJ\u000e\u0010X\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000H\u0016J\u000f\u0010Y\u001a\u0004\u0018\u00018\u0000H\u0016¢\u0006\u0002\u0010HJ\"\u0010Z\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u001e\u001a\u00020\f2\b\b\u0002\u0010\u001f\u001a\u00020\fH\u0016J+\u0010[\u001a\u00020!2!\u0010\"\u001a\u001d\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0004\u0012\u00020!0#H\u0016J@\u0010[\u001a\u00020!26\u0010\"\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020!0&H\u0016J[\u0010[\u001a\u00020!2Q\u0010\"\u001aM\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u000f\u0012\u0013\u0012\u00110\f¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(\u0016\u0012\u0019\u0012\u0017\u0012\u0004\u0012\u00028\u00000\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b((\u0012\u0004\u0012\u00020!0'H\u0016J\f\u0010\\\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006JF\u0010\u0018\u001a\b\u0012\u0004\u0012\u00028\u00000\u000026\u0010]\u001a2\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(^\u0012\u0013\u0012\u00118\u0000¢\u0006\f\b$\u0012\b\b%\u0012\u0004\b\b(_\u0012\u0004\u0012\u00020\f0&H\u0016J\u0016\u0010`\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001e\u001a\u00020\fH\u0016J\u001e\u0010`\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010a\u001a\u00020\fH\u0016J7\u0010`\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001e\u001a\u00020\f2\u0006\u0010a\u001a\u00020\f2\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\t\"\u00028\u0000H\u0016¢\u0006\u0002\u0010bJ!\u0010c\u001a\u0002012\u0012\u0010\b\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\t\"\u00028\u0000H\u0016¢\u0006\u0002\u0010JJ\b\u0010d\u001a\u000201H\u0016J\b\u0010e\u001a\u00020@H\u0016J\u001e\u0010f\u001a\u00028\u00002\u0006\u0010\u0016\u001a\u00020\f2\u0006\u0010g\u001a\u00028\u0000H\u0086\u0002¢\u0006\u0002\u0010hJ\u0013\u0010i\u001a\u00020!2\b\u0010j\u001a\u0004\u0018\u00010\u001aH\u0096\u0002R$\u0010\u0010\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\f8F@FX\u0086\u000e¢\u0006\f\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006l"}, d2 = {"Lio/dcloud/uts/UTSArray;", "E", "Ljava/util/ArrayList;", "<init>", "()V", WXBasicComponentType.LIST, "", "(Ljava/util/List;)V", "items", "", "([Ljava/lang/Object;)V", "initSize", "", "defaultValue", "(Ljava/lang/Number;Ljava/lang/Object;)V", "value", "length", "getLength", "()Ljava/lang/Number;", "setLength", "(Ljava/lang/Number;)V", "get", "index", "(Ljava/lang/Number;)Ljava/lang/Object;", "sort", "concat", "", "([Ljava/lang/Object;)Lio/dcloud/uts/UTSArray;", "copyWithin", IApp.ConfigProperty.CONFIG_TARGET, "start", "end", "every", "", WXBridgeManager.METHOD_CALLBACK, "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlin/Function2;", "Lkotlin/Function3;", "array", "fill", "(Ljava/lang/Object;Ljava/lang/Number;Ljava/lang/Number;)Lio/dcloud/uts/UTSArray;", Constants.Name.FILTER, "find", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "findIndex", "", Constants.Name.FLAT, "depth", "flatMap", "includes", "searchElement", "(Ljava/lang/Object;)Z", "fromIndex", "(Ljava/lang/Object;Ljava/lang/Number;)Z", "forEach", "", "indexOf", "(Ljava/lang/Object;)I", "(Ljava/lang/Object;Ljava/lang/Number;)I", "join", "", "separator", "keys", "", "lastIndexOf", "map", "R", "pop", "()Ljava/lang/Object;", "push", "([Ljava/lang/Object;)I", "reduce", "T", "previousValue", "currentValue", "currentIndex", "Lkotlin/Function4;", "(Lkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "S", "initialValue", "(Lkotlin/jvm/functions/Function2;Ljava/lang/Object;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function3;Ljava/lang/Object;)Ljava/lang/Object;", "(Lkotlin/jvm/functions/Function4;Ljava/lang/Object;)Ljava/lang/Object;", "reduceRight", "reverse", "shift", "slice", "some", "toKotlinList", "compareFn", "a", "b", "splice", "deleteCount", "(Ljava/lang/Number;Ljava/lang/Number;[Ljava/lang/Object;)Lio/dcloud/uts/UTSArray;", "unshift", "hashCode", "toString", "set", BindingXConstants.KEY_ELEMENT, "(Ljava/lang/Number;Ljava/lang/Object;)Ljava/lang/Object;", "equals", "other", "Companion", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
/* loaded from: classes2.dex */
public class UTSArray<E> extends ArrayList<E> {

    /* renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.Collection, java.util.List
    public boolean equals(java.lang.Object other) {
        return this == other;
    }

    public UTSArray() {
    }

    public /* bridge */ int getSize() {
        return super.size();
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public final /* bridge */ E remove(int i) {
        return (E) removeAt(i);
    }

    public /* bridge */ java.lang.Object removeAt(int i) {
        return super.remove(i);
    }

    @Override // java.util.ArrayList, java.util.AbstractCollection, java.util.Collection, java.util.List
    public final /* bridge */ int size() {
        return getSize();
    }

    /* compiled from: UTSArray.kt */
    @Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0010\u000e\n\u0002\u0010\r\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0002\u0010\u0012\n\u0002\u0010\u0016\n\u0002\u0010\u0014\n\u0002\u0010\u0013\n\u0002\u0010\u0017\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u000b\n\u0002\u0010\u0018\n\u0000\n\u0002\u0010 \n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003Jb\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00060\u0005\"\u0004\b\u0001\u0010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2<\b\u0002\u0010\n\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u000bJg\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00060\u0005\"\u0004\b\u0001\u0010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00112<\b\u0002\u0010\n\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u000b¢\u0006\u0002\u0010\u0012Jd\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00060\u0005\"\u0004\b\u0001\u0010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00142<\b\u0002\u0010\n\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u000bH\u0002Jb\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u00060\u0005\"\u0004\b\u0001\u0010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00142<\b\u0002\u0010\n\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u000bJV\u0010\u0004\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00150\u00060\u00052\u0006\u0010\b\u001a\u00020\u00162:\b\u0002\u0010\n\u001a4\u0012\u0013\u0012\u00110\u0015¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u000bJ\\\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0001\u0010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\t2<\b\u0002\u0010\n\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u000bJa\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0001\u0010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00112<\b\u0002\u0010\n\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u000b¢\u0006\u0002\u0010\u0018Jc\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00062\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00112>\b\u0002\u0010\n\u001a8\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u000bH\u0007¢\u0006\u0004\b\u0019\u0010\u0018J\\\u0010\u0017\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0001\u0010\u00072\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00142<\b\u0002\u0010\n\u001a6\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u0007\u0018\u00010\u000bJa\u0010\u0017\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u00062\n\u0010\b\u001a\u0006\u0012\u0002\b\u00030\u00142>\b\u0002\u0010\n\u001a8\u0012\u0015\u0012\u0013\u0018\u00010\u0001¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0001\u0018\u00010\u000bH\u0007¢\u0006\u0002\b\u001aJP\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00150\u00062\u0006\u0010\b\u001a\u00020\u00162:\b\u0002\u0010\n\u001a4\u0012\u0013\u0012\u00110\u0015¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0013\u0012\u00110\u000f¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u0015\u0018\u00010\u000bJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0006\u0010\b\u001a\u00020\u001cJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0006\u0010\b\u001a\u00020\u001dJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0006\u0010\b\u001a\u00020\u001eJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0006\u0010\b\u001a\u00020\u001fJ\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0006\u0010\b\u001a\u00020 J\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00062\u0006\u0010\b\u001a\u00020!J\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\"0\u00062\u0006\u0010\b\u001a\u00020#J\u0014\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020$0\u00062\u0006\u0010\b\u001a\u00020%J \u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H&0\u0006\"\u0004\b\u0001\u0010&2\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H&0'J%\u0010\u001b\u001a\b\u0012\u0004\u0012\u0002H&0\u0006\"\u0004\b\u0001\u0010&2\f\u0010(\u001a\b\u0012\u0004\u0012\u0002H&0\u0011¢\u0006\u0002\u0010)J\u0014\u0010*\u001a\u00020$2\n\b\u0002\u0010+\u001a\u0004\u0018\u00010\u0001H\u0016J+\u0010,\u001a\b\u0012\u0004\u0012\u0002H&0\u0006\"\u0004\b\u0001\u0010&2\u0012\u0010-\u001a\n\u0012\u0006\b\u0001\u0012\u0002H&0\u0011\"\u0002H&¢\u0006\u0002\u0010)J\u000e\u0010,\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u0006¨\u0006."}, d2 = {"Lio/dcloud/uts/UTSArray$Companion;", "", "<init>", "()V", "fromAsync", "Lio/dcloud/uts/UTSPromise;", "Lio/dcloud/uts/UTSArray;", "T", WXBasicComponentType.LIST, "Lio/dcloud/uts/UTSValueIterable;", "mapFn", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", BindingXConstants.KEY_ELEMENT, "", "index", "", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Lio/dcloud/uts/UTSPromise;", "fromAsyncImpl", "", "", "", "from", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)Lio/dcloud/uts/UTSArray;", "UTSArray_from_Array_no_type", "UTSArray_from_Iterable_no_type", "fromNative", "", "", "", "", "", "", "", "", "", "", "E", "", "array", "([Ljava/lang/Object;)Lio/dcloud/uts/UTSArray;", "isArray", "input", "of", "items", "utsplugin_release"}, k = 1, mv = {2, 2, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public static /* synthetic */ UTSPromise fromAsync$default(Companion companion, UTSValueIterable uTSValueIterable, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.fromAsync((UTSValueIterable<?>) uTSValueIterable, function2);
        }

        public final <T> UTSPromise<UTSArray<T>> fromAsync(final UTSValueIterable<?> list, final Function2<java.lang.Object, ? super Number, ? extends T> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            final CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default((Job) null, 1, (java.lang.Object) null).plus(Dispatchers.getDefault()));
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSArray$Companion$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSArray.Companion.fromAsync$lambda$0(CoroutineScope, list, mapFn, (Function1) obj, (Function1) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit fromAsync$lambda$0(CoroutineScope coroutineScope, UTSValueIterable<?> uTSValueIterable, Function2<java.lang.Object, ? super Number, ? extends T> function2, Function1<? super UTSArray<T>, Unit> function1, Function1<java.lang.Object, Unit> function12) {
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new UTSArray$Companion$fromAsync$p$1$1(uTSValueIterable, function1, function12, function2, null), 3, null);
            return Unit.INSTANCE;
        }

        public static /* synthetic */ UTSPromise fromAsync$default(Companion companion, java.lang.Object[] objArr, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.fromAsync(objArr, function2);
        }

        public final <T> UTSPromise<UTSArray<T>> fromAsync(java.lang.Object[] list, Function2<java.lang.Object, ? super Number, ? extends T> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            return fromAsyncImpl(ArraysKt.toList(list), mapFn);
        }

        static /* synthetic */ UTSPromise fromAsyncImpl$default(Companion companion, Iterable iterable, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.fromAsyncImpl(iterable, function2);
        }

        private final <T> UTSPromise<UTSArray<T>> fromAsyncImpl(final Iterable<?> list, final Function2<java.lang.Object, ? super Number, ? extends T> mapFn) {
            final CoroutineScope CoroutineScope = CoroutineScopeKt.CoroutineScope(SupervisorKt.SupervisorJob$default((Job) null, 1, (java.lang.Object) null).plus(Dispatchers.getDefault()));
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSArray$Companion$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSArray.Companion.fromAsyncImpl$lambda$1(CoroutineScope, list, mapFn, (Function1) obj, (Function1) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final <T> Unit fromAsyncImpl$lambda$1(CoroutineScope coroutineScope, Iterable<?> iterable, Function2<java.lang.Object, ? super Number, ? extends T> function2, Function1<? super UTSArray<T>, Unit> function1, Function1<java.lang.Object, Unit> function12) {
            BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new UTSArray$Companion$fromAsyncImpl$p$1$1(iterable, function1, function12, function2, null), 3, null);
            return Unit.INSTANCE;
        }

        public static /* synthetic */ UTSPromise fromAsync$default(Companion companion, Iterable iterable, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.fromAsync((Iterable<?>) iterable, function2);
        }

        public final <T> UTSPromise<UTSArray<T>> fromAsync(Iterable<?> list, Function2<java.lang.Object, ? super Number, ? extends T> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            return fromAsyncImpl(list, mapFn);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ UTSPromise fromAsync$default(Companion companion, CharSequence charSequence, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.fromAsync(charSequence, (Function2<? super String, ? super Number, String>) function2);
        }

        public final UTSPromise<UTSArray<String>> fromAsync(final CharSequence list, final Function2<? super String, ? super Number, String> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            return new UTSPromise<>(new Function2() { // from class: io.dcloud.uts.UTSArray$Companion$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function2
                public final java.lang.Object invoke(java.lang.Object obj, java.lang.Object obj2) {
                    return UTSArray.Companion.fromAsync$lambda$3(list, mapFn, (Function1) obj, (Function1) obj2);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final Unit fromAsync$lambda$3(CharSequence charSequence, Function2<? super String, ? super Number, String> function2, Function1<? super UTSArray<String>, Unit> function1, Function1<java.lang.Object, Unit> function12) {
            UTSArray uTSArray = new UTSArray();
            int i = 0;
            int i2 = 0;
            while (i < charSequence.length()) {
                int i3 = i2 + 1;
                String strValueOf = String.valueOf(charSequence.charAt(i));
                if (function2 != null) {
                    strValueOf = function2.invoke(strValueOf, Integer.valueOf(i2));
                }
                uTSArray.add(strValueOf);
                i++;
                i2 = i3;
            }
            function1.invoke(uTSArray);
            return Unit.INSTANCE;
        }

        public static /* synthetic */ UTSArray from$default(Companion companion, UTSValueIterable uTSValueIterable, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.from((UTSValueIterable<?>) uTSValueIterable, function2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final <T> UTSArray<T> from(UTSValueIterable<?> list, Function2<java.lang.Object, ? super Number, ? extends T> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            RegExpMatchArray regExpMatchArray = (UTSArray<T>) new UTSArray();
            UTSIterator<?> uTSIteratorValueIterator = list.valueIterator();
            int i = 0;
            while (uTSIteratorValueIterator.hasNext()) {
                java.lang.Object next = uTSIteratorValueIterator.next();
                if (mapFn != null) {
                    next = mapFn.invoke(next, Integer.valueOf(i));
                }
                regExpMatchArray.add(next);
                i++;
            }
            return regExpMatchArray;
        }

        public static /* synthetic */ UTSArray from$default(Companion companion, java.lang.Object[] objArr, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.from(objArr, function2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final <T> UTSArray<T> from(java.lang.Object[] list, Function2<java.lang.Object, ? super Number, ? extends T> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            RegExpMatchArray regExpMatchArray = (UTSArray<T>) new UTSArray();
            int length = list.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                java.lang.Object objInvoke = list[i];
                int i3 = i2 + 1;
                if (mapFn != null) {
                    objInvoke = mapFn.invoke(objInvoke, Integer.valueOf(i2));
                }
                regExpMatchArray.add(objInvoke);
                i++;
                i2 = i3;
            }
            return regExpMatchArray;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ UTSArray UTSArray_from_Array_no_type$default(Companion companion, java.lang.Object[] objArr, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.UTSArray_from_Array_no_type(objArr, function2);
        }

        public final UTSArray<java.lang.Object> UTSArray_from_Array_no_type(java.lang.Object[] list, Function2<java.lang.Object, ? super Number, ? extends java.lang.Object> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<java.lang.Object> uTSArray = new UTSArray<>();
            int length = list.length;
            int i = 0;
            int i2 = 0;
            while (i < length) {
                java.lang.Object objInvoke = list[i];
                int i3 = i2 + 1;
                if (mapFn != null) {
                    objInvoke = mapFn.invoke(objInvoke, Integer.valueOf(i2));
                }
                uTSArray.add(objInvoke);
                i++;
                i2 = i3;
            }
            return uTSArray;
        }

        public static /* synthetic */ UTSArray from$default(Companion companion, Iterable iterable, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.from((Iterable<?>) iterable, function2);
        }

        /* JADX WARN: Multi-variable type inference failed */
        public final <T> UTSArray<T> from(Iterable<?> list, Function2<java.lang.Object, ? super Number, ? extends T> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            RegExpMatchArray regExpMatchArray = (UTSArray<T>) new UTSArray();
            int i = 0;
            for (java.lang.Object objInvoke : list) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                if (mapFn != null) {
                    objInvoke = mapFn.invoke(objInvoke, Integer.valueOf(i));
                }
                regExpMatchArray.add(objInvoke);
                i = i2;
            }
            return regExpMatchArray;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ UTSArray UTSArray_from_Iterable_no_type$default(Companion companion, Iterable iterable, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.UTSArray_from_Iterable_no_type(iterable, function2);
        }

        public final UTSArray<java.lang.Object> UTSArray_from_Iterable_no_type(Iterable<?> list, Function2<java.lang.Object, ? super Number, ? extends java.lang.Object> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<java.lang.Object> uTSArray = new UTSArray<>();
            int i = 0;
            for (java.lang.Object objInvoke : list) {
                int i2 = i + 1;
                if (i < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                if (mapFn != null) {
                    objInvoke = mapFn.invoke(objInvoke, Integer.valueOf(i));
                }
                uTSArray.add(objInvoke);
                i = i2;
            }
            return uTSArray;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ UTSArray from$default(Companion companion, CharSequence charSequence, Function2 function2, int i, java.lang.Object obj) {
            if ((i & 2) != 0) {
                function2 = null;
            }
            return companion.from(charSequence, (Function2<? super String, ? super Number, String>) function2);
        }

        public final UTSArray<String> from(CharSequence list, Function2<? super String, ? super Number, String> mapFn) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<String> uTSArray = new UTSArray<>();
            int i = 0;
            int i2 = 0;
            while (i < list.length()) {
                int i3 = i2 + 1;
                String strValueOf = String.valueOf(list.charAt(i));
                if (mapFn != null) {
                    strValueOf = mapFn.invoke(strValueOf, Integer.valueOf(i2));
                }
                uTSArray.add(strValueOf);
                i++;
                i2 = i3;
            }
            return uTSArray;
        }

        public final UTSArray<Number> fromNative(int[] list) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<Number> uTSArray = new UTSArray<>();
            for (int i : list) {
                uTSArray.add(Integer.valueOf(i));
            }
            return uTSArray;
        }

        public final UTSArray<Number> fromNative(byte[] list) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<Number> uTSArray = new UTSArray<>();
            for (byte b : list) {
                uTSArray.add(Byte.valueOf(b));
            }
            return uTSArray;
        }

        public final UTSArray<Number> fromNative(long[] list) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<Number> uTSArray = new UTSArray<>();
            for (long j : list) {
                uTSArray.add(Long.valueOf(j));
            }
            return uTSArray;
        }

        public final UTSArray<Number> fromNative(float[] list) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<Number> uTSArray = new UTSArray<>();
            for (float f : list) {
                uTSArray.add(Float.valueOf(f));
            }
            return uTSArray;
        }

        public final UTSArray<Number> fromNative(double[] list) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<Number> uTSArray = new UTSArray<>();
            for (double d : list) {
                uTSArray.add(Double.valueOf(d));
            }
            return uTSArray;
        }

        public final UTSArray<Number> fromNative(short[] list) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<Number> uTSArray = new UTSArray<>();
            for (short s : list) {
                uTSArray.add(Short.valueOf(s));
            }
            return uTSArray;
        }

        public final UTSArray<Character> fromNative(char[] list) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<Character> uTSArray = new UTSArray<>();
            for (char c : list) {
                uTSArray.add(Character.valueOf(c));
            }
            return uTSArray;
        }

        public final UTSArray<Boolean> fromNative(boolean[] list) {
            Intrinsics.checkNotNullParameter(list, "list");
            UTSArray<Boolean> uTSArray = new UTSArray<>();
            for (boolean z : list) {
                uTSArray.add(Boolean.valueOf(z));
            }
            return uTSArray;
        }

        public final <E> UTSArray<E> fromNative(List<? extends E> list) {
            Intrinsics.checkNotNullParameter(list, "list");
            return new UTSArray<>(list);
        }

        public final <E> UTSArray<E> fromNative(E[] array) {
            Intrinsics.checkNotNullParameter(array, "array");
            return new UTSArray<>(Arrays.copyOf(array, array.length));
        }

        public static /* synthetic */ boolean isArray$default(Companion companion, java.lang.Object obj, int i, java.lang.Object obj2) {
            if ((i & 1) != 0) {
                obj = null;
            }
            return companion.isArray(obj);
        }

        public boolean isArray(java.lang.Object input) {
            if (input == null) {
                return false;
            }
            return input instanceof UTSArray;
        }

        public final <E> UTSArray<E> of(E... items) {
            Intrinsics.checkNotNullParameter(items, "items");
            return new UTSArray<>(Arrays.copyOf(items, items.length));
        }

        public final UTSArray<java.lang.Object> of() {
            return new UTSArray<>();
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UTSArray(List<? extends E> list) {
        this();
        Intrinsics.checkNotNullParameter(list, "list");
        Iterator<? extends E> it = list.iterator();
        while (it.hasNext()) {
            add(it.next());
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UTSArray(E... items) {
        this();
        Intrinsics.checkNotNullParameter(items, "items");
        for (E e : items) {
            add(e);
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public UTSArray(Number initSize, E e) {
        this();
        Intrinsics.checkNotNullParameter(initSize, "initSize");
        int iIntValue = initSize.intValue();
        for (int i = 0; i < iIntValue; i++) {
            add(e);
        }
    }

    public final Number getLength() {
        return Integer.valueOf(size());
    }

    public final void setLength(Number value) {
        Intrinsics.checkNotNullParameter(value, "value");
        splice(value);
    }

    public final E get(Number index) {
        Intrinsics.checkNotNullParameter(index, "index");
        return get(index.intValue());
    }

    public UTSArray<E> sort() {
        if (NumberKt.compareTo(getLength(), (Number) 2) < 0) {
            return this;
        }
        Collections.sort(this, new Comparator() { // from class: io.dcloud.uts.UTSArray$$ExternalSyntheticLambda1
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                return UTSArray.sort$lambda$0(obj, obj2);
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sort$lambda$0(java.lang.Object obj, java.lang.Object obj2) {
        byte[] bytes = obj.toString().getBytes(Charsets.UTF_16BE);
        Intrinsics.checkNotNullExpressionValue(bytes, "getBytes(...)");
        byte[] bytes2 = obj2.toString().getBytes(Charsets.UTF_16BE);
        Intrinsics.checkNotNullExpressionValue(bytes2, "getBytes(...)");
        if (bytes.length == 0) {
            return -1;
        }
        if (bytes2.length == 0) {
            return 1;
        }
        int length = bytes.length;
        int length2 = bytes2.length;
        int iIntValue = Math.min(Integer.valueOf(length), Integer.valueOf(length2)).intValue();
        for (int i = 0; i < iIntValue; i++) {
            byte b = bytes[i];
            byte b2 = bytes2[i];
            if (b < b2) {
                return -1;
            }
            if (b > b2) {
                return 1;
            }
        }
        return length - length2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public UTSArray<E> concat(java.lang.Object... items) {
        Intrinsics.checkNotNullParameter(items, "items");
        RegExpMatchArray regExpMatchArray = (UTSArray<E>) new UTSArray(this);
        for (java.lang.Object obj : items) {
            if (obj instanceof UTSArray) {
                Iterator<E> it = ((UTSArray) obj).iterator();
                Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
                while (it.hasNext()) {
                    regExpMatchArray.add(it.next());
                }
            } else {
                regExpMatchArray.add(obj);
            }
        }
        return regExpMatchArray;
    }

    public static /* synthetic */ UTSArray copyWithin$default(UTSArray uTSArray, Number number, Number number2, Number number3, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: copyWithin");
        }
        if ((i & 2) != 0) {
            number2 = (Number) 0;
        }
        if ((i & 4) != 0) {
            number3 = Integer.valueOf(uTSArray.size());
        }
        return uTSArray.copyWithin(number, number2, number3);
    }

    public UTSArray<E> copyWithin(Number target, Number start, Number end) {
        int iMin;
        int iMin2;
        int iMin3;
        int i;
        Intrinsics.checkNotNullParameter(target, "target");
        int size = size();
        int iIntValue = target.intValue();
        if (NumberKt.compareTo(target, (Number) 0) < 0) {
            iMin = java.lang.Math.max(iIntValue + size, 0);
        } else {
            iMin = java.lang.Math.min(iIntValue, size);
        }
        int iIntValue2 = start != null ? start.intValue() : 0;
        if (iIntValue2 < 0) {
            iMin2 = java.lang.Math.max(iIntValue2 + size, 0);
        } else {
            iMin2 = java.lang.Math.min(iIntValue2, size);
        }
        int iIntValue3 = end != null ? end.intValue() : size;
        if (iIntValue3 < 0) {
            iMin3 = java.lang.Math.max(iIntValue3 + size, 0);
        } else {
            iMin3 = java.lang.Math.min(iIntValue3, size);
        }
        int iMin4 = java.lang.Math.min(iMin3 - iMin2, size - iMin);
        if (iMin2 >= iMin || iMin >= iMin2 + iMin4) {
            i = 1;
        } else {
            int i2 = iMin4 - 1;
            iMin2 += i2;
            iMin += i2;
            i = -1;
        }
        while (iMin4 > 0) {
            if (iMin2 < size()) {
                set(iMin, (int) get(iMin2));
            } else {
                remove(iMin);
            }
            iMin2 += i;
            iMin += i;
            iMin4--;
        }
        return this;
    }

    public boolean every(Function1<? super E, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (i < size() && !callback.invoke(get(i)).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean every(Function2<? super E, ? super Number, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (i < size() && !callback.invoke(get(i), Integer.valueOf(i)).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public boolean every(Function3<? super E, ? super Number, ? super UTSArray<E>, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (i < size() && !callback.invoke(get(i), Integer.valueOf(i), this).booleanValue()) {
                return false;
            }
        }
        return true;
    }

    public static /* synthetic */ UTSArray fill$default(UTSArray uTSArray, java.lang.Object obj, Number number, Number number2, int i, java.lang.Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: fill");
        }
        if ((i & 2) != 0) {
            number = (Number) 0;
        }
        if ((i & 4) != 0) {
            number2 = Integer.valueOf(uTSArray.size());
        }
        return uTSArray.fill(obj, number, number2);
    }

    public UTSArray<E> fill(E value, Number start, Number end) {
        int iMin;
        int iMin2;
        int size = size();
        int iIntValue = start != null ? start.intValue() : 0;
        if (iIntValue < 0) {
            iMin = java.lang.Math.max(iIntValue + size, 0);
        } else {
            iMin = java.lang.Math.min(iIntValue, size);
        }
        int iIntValue2 = end != null ? end.intValue() : size;
        if (iIntValue2 < 0) {
            iMin2 = java.lang.Math.max(size + iIntValue2, 0);
        } else {
            iMin2 = java.lang.Math.min(iIntValue2, size);
        }
        while (iMin < iMin2) {
            set(iMin, (int) value);
            iMin++;
        }
        return this;
    }

    public UTSArray<E> filter(Function1<? super E, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        UTSArray<E> uTSArray = new UTSArray<>();
        int size = size();
        for (int i = 0; i < size; i++) {
            E e = get(i);
            if (callback.invoke(e).booleanValue()) {
                uTSArray.add(e);
            }
        }
        return uTSArray;
    }

    public UTSArray<E> filter(Function2<? super E, ? super Number, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        UTSArray<E> uTSArray = new UTSArray<>();
        int size = size();
        for (int i = 0; i < size; i++) {
            E e = get(i);
            if (callback.invoke(e, Integer.valueOf(i)).booleanValue()) {
                uTSArray.add(e);
            }
        }
        return uTSArray;
    }

    public UTSArray<E> filter(Function3<? super E, ? super Number, ? super UTSArray<E>, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        UTSArray<E> uTSArray = new UTSArray<>();
        int size = size();
        for (int i = 0; i < size; i++) {
            E e = get(i);
            if (callback.invoke(e, Integer.valueOf(i), this).booleanValue()) {
                uTSArray.add(e);
            }
        }
        return uTSArray;
    }

    public E find(Function1<? super E, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            E e = get(i);
            if (callback.invoke(e).booleanValue()) {
                return e;
            }
        }
        return null;
    }

    public E find(Function2<? super E, ? super Number, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            E e = get(i);
            if (callback.invoke(e, Integer.valueOf(i)).booleanValue()) {
                return e;
            }
        }
        return null;
    }

    public E find(Function3<? super E, ? super Number, ? super UTSArray<E>, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            E e = get(i);
            if (callback.invoke(e, Integer.valueOf(i), this).booleanValue()) {
                return e;
            }
        }
        return null;
    }

    public int findIndex(Function1<? super E, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (callback.invoke(get(i)).booleanValue()) {
                return i;
            }
        }
        return -1;
    }

    public int findIndex(Function2<? super E, ? super Number, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (callback.invoke(get(i), Integer.valueOf(i)).booleanValue()) {
                return i;
            }
        }
        return -1;
    }

    public int findIndex(Function3<? super E, ? super Number, ? super UTSArray<E>, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (callback.invoke(get(i), Integer.valueOf(i), this).booleanValue()) {
                return i;
            }
        }
        return -1;
    }

    public static /* synthetic */ UTSArray flat$default(UTSArray uTSArray, Number number, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: flat");
        }
        if ((i & 1) != 0) {
            number = (Number) 1;
        }
        return uTSArray.flat(number);
    }

    public UTSArray<?> flat(Number depth) {
        Intrinsics.checkNotNullParameter(depth, "depth");
        UTSArray<?> uTSArray = new UTSArray<>();
        flat$flatDeep(uTSArray, this, depth);
        return uTSArray;
    }

    private static final void flat$flatDeep(UTSArray<java.lang.Object> uTSArray, List<?> list, Number number) {
        for (java.lang.Object obj : list) {
            if (NumberKt.compareTo(number, (Number) 0) > 0 && TypeIntrinsics.isMutableList(obj)) {
                flat$flatDeep(uTSArray, (List) obj, NumberKt.minus(number, (Number) 1));
            } else {
                uTSArray.add(obj);
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public UTSArray<?> flatMap(Function1<? super E, ? extends UTSArray<?>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return map(callback).flat((Number) 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public UTSArray<?> flatMap(Function2<? super E, ? super Number, ? extends UTSArray<?>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return map(callback).flat((Number) 1);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public UTSArray<?> flatMap(Function3<? super E, ? super Number, ? super UTSArray<E>, ? extends UTSArray<?>> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        return map(callback).flat((Number) 1);
    }

    public boolean includes(E searchElement) {
        return includes(searchElement, (Number) 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean includes(E searchElement, Number fromIndex) {
        Intrinsics.checkNotNullParameter(fromIndex, "fromIndex");
        int iIntValue = fromIndex.intValue();
        int size = size();
        if (iIntValue < 0) {
            iIntValue = size - java.lang.Math.abs(iIntValue);
        }
        for (int iMax = java.lang.Math.max(iIntValue, 0); iMax < size; iMax++) {
            E e = get(iMax);
            if ((searchElement instanceof Number) && (e instanceof Number)) {
                if (((Number) e).doubleValue() == ((Number) searchElement).doubleValue()) {
                    return true;
                }
            } else if (Intrinsics.areEqual(e, searchElement)) {
                return true;
            }
        }
        return false;
    }

    public void forEach(Function2<? super E, ? super Number, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        for (int i = 0; i < size(); i++) {
            callback.invoke(get(i), Integer.valueOf(i));
        }
    }

    public void forEach(Function3<? super E, ? super Number, ? super UTSArray<E>, Unit> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        for (int i = 0; i < size(); i++) {
            callback.invoke(get(i), Integer.valueOf(i), this);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public int indexOf(java.lang.Object searchElement) {
        return indexOf(searchElement, (Number) 0);
    }

    public static /* synthetic */ int indexOf$default(UTSArray uTSArray, java.lang.Object obj, Number number, int i, java.lang.Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: indexOf");
        }
        if ((i & 2) != 0) {
            number = (Number) 0;
        }
        return uTSArray.indexOf(obj, number);
    }

    public int indexOf(E searchElement, Number fromIndex) {
        int iIntValue;
        Intrinsics.checkNotNullParameter(fromIndex, "fromIndex");
        int size = size();
        if (size == 0 || (iIntValue = fromIndex.intValue()) >= size) {
            return -1;
        }
        if (iIntValue < 0) {
            iIntValue = size - java.lang.Math.abs(iIntValue);
        }
        for (int iMax = java.lang.Math.max(iIntValue, 0); iMax < size; iMax++) {
            if (Intrinsics.areEqual(get(iMax), searchElement)) {
                return iMax;
            }
        }
        return -1;
    }

    public static /* synthetic */ String join$default(UTSArray uTSArray, String str, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: join");
        }
        if ((i & 1) != 0) {
            str = ",";
        }
        return uTSArray.join(str);
    }

    public String join(String separator) {
        Intrinsics.checkNotNullParameter(separator, "separator");
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<E> it = iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            E next = it.next();
            if (next == null) {
                stringBuffer.append("");
            } else {
                stringBuffer.append(next.toString());
            }
            if (it.hasNext()) {
                stringBuffer.append(separator);
            }
        }
        String string = stringBuffer.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    public Iterator<Number> keys() {
        UTSArray uTSArray = new UTSArray();
        int size = size();
        for (int i = 0; i < size; i++) {
            uTSArray.add(Integer.valueOf(i));
        }
        Iterator<Number> it = uTSArray.iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        return it;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // java.util.ArrayList, java.util.AbstractList, java.util.List
    public int lastIndexOf(java.lang.Object searchElement) {
        if (size() < 1) {
            return -1;
        }
        return lastIndexOf(searchElement, Integer.valueOf(size() - 1));
    }

    public static /* synthetic */ int lastIndexOf$default(UTSArray uTSArray, java.lang.Object obj, Number number, int i, java.lang.Object obj2) {
        if (obj2 != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: lastIndexOf");
        }
        if ((i & 2) != 0) {
            number = (Number) 0;
        }
        return uTSArray.lastIndexOf(obj, number);
    }

    public int lastIndexOf(E searchElement, Number fromIndex) {
        Intrinsics.checkNotNullParameter(fromIndex, "fromIndex");
        int size = size();
        if (size == 0) {
            return -1;
        }
        int iIntValue = fromIndex.intValue();
        for (int iMin = iIntValue >= 0 ? java.lang.Math.min(iIntValue, size - 1) : size - java.lang.Math.abs(iIntValue); iMin >= 0; iMin--) {
            if (Intrinsics.areEqual(get(iMin), searchElement)) {
                return iMin;
            }
        }
        return -1;
    }

    public <R> UTSArray<R> map(Function1<? super E, ? extends R> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        UTSArray<R> uTSArray = new UTSArray<>();
        for (int i = 0; i < size; i++) {
            uTSArray.add(callback.invoke(get(i)));
        }
        return uTSArray;
    }

    public <R> UTSArray<R> map(Function2<? super E, ? super Number, ? extends R> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        UTSArray<R> uTSArray = new UTSArray<>();
        for (int i = 0; i < size; i++) {
            uTSArray.add(callback.invoke(get(i), Integer.valueOf(i)));
        }
        return uTSArray;
    }

    public <R> UTSArray<R> map(Function3<? super E, ? super Number, ? super UTSArray<E>, ? extends R> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        UTSArray<R> uTSArray = new UTSArray<>();
        for (int i = 0; i < size; i++) {
            uTSArray.add(callback.invoke(get(i), Integer.valueOf(i), this));
        }
        return uTSArray;
    }

    public E pop() {
        return (E) CollectionsKt.removeLastOrNull(this);
    }

    public int push(E... items) {
        Intrinsics.checkNotNullParameter(items, "items");
        CollectionsKt.addAll(this, items);
        return size();
    }

    public <T extends E> E reduce(Function3<? super E, ? super T, ? super Number, ? extends E> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        E eInvoke = get(0);
        for (int i = 1; i < size; i++) {
            eInvoke = callback.invoke(eInvoke, get(i), Integer.valueOf(i));
        }
        return eInvoke;
    }

    public <T extends E> E reduce(Function4<? super E, ? super T, ? super Number, ? super UTSArray<E>, ? extends E> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        E eInvoke = get(0);
        for (int i = 1; i < size; i++) {
            eInvoke = callback.invoke(eInvoke, get(i), Integer.valueOf(i), this);
        }
        return eInvoke;
    }

    public <S> S reduce(Function2<? super S, ? super E, ? extends S> callback, S initialValue) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            initialValue = callback.invoke(initialValue, get(i));
        }
        return initialValue;
    }

    public <S> S reduce(Function3<? super S, ? super E, ? super Number, ? extends S> callback, S initialValue) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            initialValue = callback.invoke(initialValue, get(i), Integer.valueOf(i));
        }
        return initialValue;
    }

    public <S> S reduce(Function4<? super S, ? super E, ? super Number, ? super UTSArray<E>, ? extends S> callback, S initialValue) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            initialValue = callback.invoke(initialValue, get(i), Integer.valueOf(i), this);
        }
        return initialValue;
    }

    public <T extends E> E reduceRight(Function2<? super E, ? super T, ? extends E> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        int i = size - 1;
        if (i < 0) {
            throw new Error("Reduce of empty array with no initial value");
        }
        E eInvoke = get(i);
        for (int i2 = size - 2; i2 >= 0; i2--) {
            eInvoke = callback.invoke(eInvoke, get(i2));
        }
        return eInvoke;
    }

    public <T extends E> E reduceRight(Function3<? super E, ? super T, ? super Number, ? extends E> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        int i = size - 1;
        if (i < 0) {
            throw new Error("Reduce of empty array with no initial value");
        }
        E eInvoke = get(i);
        for (int i2 = size - 2; i2 >= 0; i2--) {
            eInvoke = callback.invoke(eInvoke, get(i2), Integer.valueOf(i2));
        }
        return eInvoke;
    }

    public <T extends E> E reduceRight(Function4<? super E, ? super T, ? super Number, ? super UTSArray<E>, ? extends E> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        int i = size - 1;
        if (i < 0) {
            throw new Error("Reduce of empty array with no initial value");
        }
        E eInvoke = get(i);
        for (int i2 = size - 2; i2 >= 0; i2--) {
            eInvoke = callback.invoke(eInvoke, get(i2), Integer.valueOf(i2), this);
        }
        return eInvoke;
    }

    public <S> S reduceRight(Function2<? super S, ? super E, ? extends S> callback, S initialValue) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        for (int size = size() - 1; size >= 0; size--) {
            initialValue = callback.invoke(initialValue, get(size));
        }
        return initialValue;
    }

    public <S> S reduceRight(Function3<? super S, ? super E, ? super Number, ? extends S> callback, S initialValue) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        for (int size = size() - 1; size >= 0; size--) {
            initialValue = callback.invoke(initialValue, get(size), Integer.valueOf(size));
        }
        return initialValue;
    }

    public <S> S reduceRight(Function4<? super S, ? super E, ? super Number, ? super UTSArray<E>, ? extends S> callback, S initialValue) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        for (int size = size() - 1; size >= 0; size--) {
            initialValue = callback.invoke(initialValue, get(size), Integer.valueOf(size), this);
        }
        return initialValue;
    }

    public UTSArray<E> reverse() {
        Collections.reverse(this);
        return this;
    }

    public E shift() {
        return (E) CollectionsKt.removeFirstOrNull(this);
    }

    public static /* synthetic */ UTSArray slice$default(UTSArray uTSArray, Number number, Number number2, int i, java.lang.Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: slice");
        }
        if ((i & 1) != 0) {
            number = (Number) 0;
        }
        if ((i & 2) != 0) {
            number2 = Integer.valueOf(uTSArray.size());
        }
        return uTSArray.slice(number, number2);
    }

    public UTSArray<E> slice(Number start, Number end) {
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(end, "end");
        UTSArray<E> uTSArray = new UTSArray<>();
        int sliceIndex = IndexKt.toSliceIndex(end, size());
        for (int sliceIndex2 = IndexKt.toSliceIndex(start, size()); sliceIndex2 < sliceIndex; sliceIndex2++) {
            uTSArray.add(get(sliceIndex2));
        }
        return uTSArray;
    }

    public boolean some(Function1<? super E, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (i < size() && callback.invoke(get(i)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public boolean some(Function2<? super E, ? super Number, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (i < size() && callback.invoke(get(i), Integer.valueOf(i)).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public boolean some(Function3<? super E, ? super Number, ? super UTSArray<E>, Boolean> callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        int size = size();
        for (int i = 0; i < size; i++) {
            if (i < size() && callback.invoke(get(i), Integer.valueOf(i), this).booleanValue()) {
                return true;
            }
        }
        return false;
    }

    public final List<E> toKotlinList() {
        ArrayList arrayList = new ArrayList();
        Iterator<E> it = iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        return arrayList;
    }

    public UTSArray<E> sort(final Function2<? super E, ? super E, ? extends Number> compareFn) {
        Intrinsics.checkNotNullParameter(compareFn, "compareFn");
        Collections.sort(this, new Comparator() { // from class: io.dcloud.uts.UTSArray$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(java.lang.Object obj, java.lang.Object obj2) {
                return UTSArray.sort$lambda$2(compareFn, obj, obj2);
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int sort$lambda$2(Function2 function2, java.lang.Object obj, java.lang.Object obj2) {
        Number number = (Number) function2.invoke(obj, obj2);
        if (NumberKt.compareTo(number, (Number) 0) > 0) {
            return 1;
        }
        return NumberKt.compareTo(number, (Number) 0) < 0 ? -1 : 0;
    }

    public UTSArray<E> splice(Number start) {
        Intrinsics.checkNotNullParameter(start, "start");
        UTSArray<E> uTSArray = new UTSArray<>();
        int sliceIndex = IndexKt.toSliceIndex(start, size());
        int size = size();
        for (int i = sliceIndex; i < size; i++) {
            uTSArray.add(get(i));
        }
        while (size() > sliceIndex) {
            CollectionsKt.removeLastOrNull(this);
        }
        return uTSArray;
    }

    public UTSArray<E> splice(Number start, Number deleteCount) {
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(deleteCount, "deleteCount");
        UTSArray<E> uTSArray = new UTSArray<>();
        int sliceIndex = IndexKt.toSliceIndex(start, size());
        int iIntValue = 0;
        if (NumberKt.compareTo(deleteCount, (Number) 0) >= 0) {
            if (NumberKt.compareTo(deleteCount, Integer.valueOf(size() - sliceIndex)) > 0) {
                iIntValue = size() - sliceIndex;
            } else {
                iIntValue = deleteCount.intValue();
            }
        }
        int i = iIntValue + sliceIndex;
        for (int i2 = sliceIndex; i2 < i; i2++) {
            uTSArray.add(get(i2));
        }
        while (iIntValue > 0) {
            remove(sliceIndex);
            iIntValue--;
        }
        return uTSArray;
    }

    public UTSArray<E> splice(Number start, Number deleteCount, E... items) {
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(deleteCount, "deleteCount");
        Intrinsics.checkNotNullParameter(items, "items");
        UTSArray<E> uTSArray = new UTSArray<>();
        int sliceIndex = IndexKt.toSliceIndex(start, size());
        int iIntValue = 0;
        if (NumberKt.compareTo(deleteCount, (Number) 0) >= 0) {
            if (NumberKt.compareTo(deleteCount, Integer.valueOf(size() - sliceIndex)) > 0) {
                iIntValue = size() - sliceIndex;
            } else {
                iIntValue = deleteCount.intValue();
            }
        }
        int i = iIntValue + sliceIndex;
        for (int i2 = sliceIndex; i2 < i; i2++) {
            uTSArray.add(get(i2));
        }
        while (iIntValue > 0) {
            remove(sliceIndex);
            iIntValue--;
        }
        int length = items.length;
        while (true) {
            length--;
            if (-1 >= length) {
                return uTSArray;
            }
            add(sliceIndex, items[length]);
        }
    }

    public int unshift(E... items) {
        Intrinsics.checkNotNullParameter(items, "items");
        addAll(0, ArraysKt.toList(items));
        return size();
    }

    @Override // java.util.ArrayList, java.util.AbstractList, java.util.Collection, java.util.List
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override // java.util.AbstractCollection
    public String toString() {
        String string;
        Iterator<E> it = iterator();
        Intrinsics.checkNotNullExpressionValue(it, "iterator(...)");
        if (!it.hasNext()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        while (true) {
            E next = it.next();
            if (next == null) {
                sb.append("");
            } else {
                if (next instanceof Number) {
                    string = NumberKt.toStringAsJS((Number) next);
                } else {
                    string = next.toString();
                }
                sb.append(string);
            }
            if (!it.hasNext()) {
                String string2 = sb.toString();
                Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                return string2;
            }
            sb.append(Operators.ARRAY_SEPRATOR);
        }
    }

    public final E set(Number index, E element) {
        Intrinsics.checkNotNullParameter(index, "index");
        return set(index.intValue(), (int) element);
    }
}
