package io.dcloud.p;

import com.facebook.cache.disk.DefaultDiskStorage;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.common.util.JSUtil;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes2.dex */
public final class x0 implements Closeable {
    static final Pattern o = Pattern.compile("[a-z0-9_-]{1,64}");
    private static final OutputStream p = new b();
    private final File a;
    private final File b;
    private final File c;
    private final File d;
    private final int e;
    private long f;
    private final int g;
    private Writer i;
    private int k;
    private long h = 0;
    private final LinkedHashMap j = new LinkedHashMap(0, 0.75f, true);
    private long l = 0;
    final ThreadPoolExecutor m = new ThreadPoolExecutor(0, 1, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
    private final Callable n = new a();

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class a implements Callable {
        a() {
        }

        @Override // java.util.concurrent.Callable
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public Void call() {
            synchronized (x0.this) {
                if (x0.this.i == null) {
                    return null;
                }
                x0.this.h();
                if (x0.this.d()) {
                    x0.this.g();
                    x0.this.k = 0;
                }
                return null;
            }
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    class b extends OutputStream {
        b() {
        }

        @Override // java.io.OutputStream
        public void write(int i) {
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public final class c {
        private final d a;
        private final boolean[] b;
        private boolean c;
        private boolean d;

        /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
        private class a extends FilterOutputStream {
            /* synthetic */ a(c cVar, OutputStream outputStream, a aVar) {
                this(outputStream);
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                try {
                    ((FilterOutputStream) this).out.close();
                } catch (IOException unused) {
                    c.this.c = true;
                }
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Flushable
            public void flush() throws IOException {
                try {
                    ((FilterOutputStream) this).out.flush();
                } catch (IOException unused) {
                    c.this.c = true;
                }
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public void write(int i) throws IOException {
                try {
                    ((FilterOutputStream) this).out.write(i);
                } catch (IOException unused) {
                    c.this.c = true;
                }
            }

            private a(OutputStream outputStream) {
                super(outputStream);
            }

            @Override // java.io.FilterOutputStream, java.io.OutputStream
            public void write(byte[] bArr, int i, int i2) throws IOException {
                try {
                    ((FilterOutputStream) this).out.write(bArr, i, i2);
                } catch (IOException unused) {
                    c.this.c = true;
                }
            }
        }

        /* synthetic */ c(x0 x0Var, d dVar, a aVar) {
            this(dVar);
        }

        private c(d dVar) {
            this.a = dVar;
            this.b = dVar.c ? null : new boolean[x0.this.g];
        }

        public void b() {
            if (this.c) {
                x0.this.a(this, false);
                x0.this.d(this.a.a);
            } else {
                x0.this.a(this, true);
            }
            this.d = true;
        }

        public OutputStream a(int i) {
            FileOutputStream fileOutputStream;
            a aVar;
            synchronized (x0.this) {
                if (this.a.d == this) {
                    if (!this.a.c) {
                        this.b[i] = true;
                    }
                    File fileB = this.a.b(i);
                    try {
                        fileOutputStream = new FileOutputStream(fileB);
                    } catch (FileNotFoundException unused) {
                        x0.this.a.mkdirs();
                        try {
                            fileOutputStream = new FileOutputStream(fileB);
                        } catch (FileNotFoundException unused2) {
                            return x0.p;
                        }
                    }
                    aVar = new a(this, fileOutputStream, null);
                } else {
                    throw new IllegalStateException();
                }
            }
            return aVar;
        }

        public void a() {
            x0.this.a(this, false);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    private final class d {
        private final String a;
        private final long[] b;
        private boolean c;
        private c d;
        private long e;

        /* synthetic */ d(x0 x0Var, String str, a aVar) {
            this(str);
        }

        private d(String str) {
            this.a = str;
            this.b = new long[x0.this.g];
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void b(String[] strArr) throws IOException {
            if (strArr.length != x0.this.g) {
                throw a(strArr);
            }
            for (int i = 0; i < strArr.length; i++) {
                try {
                    this.b[i] = Long.parseLong(strArr[i]);
                } catch (NumberFormatException unused) {
                    throw a(strArr);
                }
            }
        }

        public String a() {
            StringBuilder sb = new StringBuilder();
            for (long j : this.b) {
                sb.append(' ');
                sb.append(j);
            }
            return sb.toString();
        }

        private IOException a(String[] strArr) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(strArr));
        }

        public File a(int i) {
            return new File(x0.this.a, this.a + Operators.DOT_STR + i);
        }

        public File b(int i) {
            return new File(x0.this.a, this.a + Operators.DOT_STR + i + DefaultDiskStorage.FileType.TEMP);
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public final class e implements Closeable {
        private final String a;
        private final long b;
        private final InputStream[] c;
        private final long[] d;

        /* synthetic */ e(x0 x0Var, String str, long j, InputStream[] inputStreamArr, long[] jArr, a aVar) {
            this(str, j, inputStreamArr, jArr);
        }

        public InputStream a(int i) {
            return this.c[i];
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            for (InputStream inputStream : this.c) {
                z4.a(inputStream);
            }
        }

        public String getString(int i) {
            return x0.b(a(i));
        }

        private e(String str, long j, InputStream[] inputStreamArr, long[] jArr) {
            this.a = str;
            this.b = j;
            this.c = inputStreamArr;
            this.d = jArr;
        }
    }

    private x0(File file, int i, int i2, long j) {
        this.a = file;
        this.e = i;
        this.b = new File(file, "journal");
        this.c = new File(file, "journal.tmp");
        this.d = new File(file, "journal.bkp");
        this.g = i2;
        this.f = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void g() {
        Writer writer = this.i;
        if (writer != null) {
            writer.close();
        }
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.c), z4.a));
        try {
            bufferedWriter.write("libcore.io.DiskLruCache");
            bufferedWriter.write("\n");
            bufferedWriter.write("1");
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.e));
            bufferedWriter.write("\n");
            bufferedWriter.write(Integer.toString(this.g));
            bufferedWriter.write("\n");
            bufferedWriter.write("\n");
            for (d dVar : this.j.values()) {
                if (dVar.d != null) {
                    bufferedWriter.write("DIRTY " + dVar.a + '\n');
                } else {
                    bufferedWriter.write("CLEAN " + dVar.a + dVar.a() + '\n');
                }
            }
            bufferedWriter.close();
            if (this.b.exists()) {
                a(this.b, this.d, true);
            }
            a(this.c, this.b, false);
            this.d.delete();
            this.i = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.b, true), z4.a));
        } catch (Throwable th) {
            bufferedWriter.close();
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void h() {
        while (this.h > this.f) {
            d((String) ((Map.Entry) this.j.entrySet().iterator().next()).getKey());
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() {
        if (this.i == null) {
            return;
        }
        ArrayList arrayList = new ArrayList(this.j.values());
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            d dVar = (d) obj;
            if (dVar.d != null) {
                dVar.d.a();
            }
        }
        h();
        this.i.close();
        this.i = null;
    }

    public synchronized void flush() {
        b();
        h();
        this.i.flush();
    }

    private void c(String str) throws IOException {
        String strSubstring;
        int iIndexOf = str.indexOf(32);
        if (iIndexOf == -1) {
            throw new IOException("unexpected journal line: " + str);
        }
        int i = iIndexOf + 1;
        int iIndexOf2 = str.indexOf(32, i);
        if (iIndexOf2 == -1) {
            strSubstring = str.substring(i);
            if (iIndexOf == 6 && str.startsWith("REMOVE")) {
                this.j.remove(strSubstring);
                return;
            }
        } else {
            strSubstring = str.substring(i, iIndexOf2);
        }
        d dVar = (d) this.j.get(strSubstring);
        a aVar = null;
        if (dVar == null) {
            dVar = new d(this, strSubstring, aVar);
            this.j.put(strSubstring, dVar);
        }
        if (iIndexOf2 != -1 && iIndexOf == 5 && str.startsWith("CLEAN")) {
            String[] strArrSplit = str.substring(iIndexOf2 + 1).split(Operators.SPACE_STR);
            dVar.c = true;
            dVar.d = null;
            dVar.b(strArrSplit);
            return;
        }
        if (iIndexOf2 == -1 && iIndexOf == 5 && str.startsWith("DIRTY")) {
            dVar.d = new c(this, dVar, aVar);
        } else {
            if (iIndexOf2 == -1 && iIndexOf == 4 && str.startsWith("READ")) {
                return;
            }
            throw new IOException("unexpected journal line: " + str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean d() {
        int i = this.k;
        return i >= 2000 && i >= this.j.size();
    }

    private void e() throws IOException {
        a(this.c);
        Iterator it = this.j.values().iterator();
        while (it.hasNext()) {
            d dVar = (d) it.next();
            int i = 0;
            if (dVar.d == null) {
                while (i < this.g) {
                    this.h += dVar.b[i];
                    i++;
                }
            } else {
                dVar.d = null;
                while (i < this.g) {
                    a(dVar.a(i));
                    a(dVar.b(i));
                    i++;
                }
                it.remove();
            }
        }
    }

    private void f() throws IOException {
        p4 p4Var = new p4(new FileInputStream(this.b), z4.a);
        try {
            String strB = p4Var.b();
            String strB2 = p4Var.b();
            String strB3 = p4Var.b();
            String strB4 = p4Var.b();
            String strB5 = p4Var.b();
            if (!"libcore.io.DiskLruCache".equals(strB) || !"1".equals(strB2) || !Integer.toString(this.e).equals(strB3) || !Integer.toString(this.g).equals(strB4) || !"".equals(strB5)) {
                throw new IOException("unexpected journal header: [" + strB + ", " + strB2 + ", " + strB4 + ", " + strB5 + Operators.ARRAY_END_STR);
            }
            int i = 0;
            while (true) {
                try {
                    c(p4Var.b());
                    i++;
                } catch (EOFException unused) {
                    this.k = i - this.j.size();
                    z4.a(p4Var);
                    return;
                }
            }
        } catch (Throwable th) {
            z4.a(p4Var);
            throw th;
        }
    }

    public synchronized e b(String str) throws Throwable {
        Throwable th;
        InputStream inputStream;
        try {
            b();
            e(str);
            d dVar = (d) this.j.get(str);
            if (dVar == null) {
                return null;
            }
            if (!dVar.c) {
                return null;
            }
            InputStream[] inputStreamArr = new InputStream[this.g];
            for (int i = 0; i < this.g; i++) {
                try {
                    try {
                        try {
                            inputStreamArr[i] = new FileInputStream(dVar.a(i));
                        } catch (Throwable th2) {
                            th = th2;
                            throw th;
                        }
                    } catch (FileNotFoundException unused) {
                        for (int i2 = 0; i2 < this.g && (inputStream = inputStreamArr[i2]) != null; i2++) {
                            z4.a(inputStream);
                        }
                        return null;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    th = th;
                    throw th;
                }
            }
            this.k++;
            this.i.append((CharSequence) ("READ " + str + '\n'));
            if (d()) {
                this.m.submit(this.n);
            }
            return new e(this, str, dVar.e, inputStreamArr, dVar.b, null);
        } catch (Throwable th4) {
            th = th4;
        }
    }

    public synchronized boolean d(String str) {
        b();
        e(str);
        d dVar = (d) this.j.get(str);
        if (dVar != null && dVar.d == null) {
            for (int i = 0; i < this.g; i++) {
                File fileA = dVar.a(i);
                if (fileA.exists() && !fileA.delete()) {
                    throw new IOException("failed to delete " + fileA);
                }
                this.h -= dVar.b[i];
                dVar.b[i] = 0;
            }
            this.k++;
            this.i.append((CharSequence) ("REMOVE " + str + '\n'));
            this.j.remove(str);
            if (d()) {
                this.m.submit(this.n);
            }
            return true;
        }
        return false;
    }

    public static x0 a(File file, int i, int i2, long j) throws IOException {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (i2 > 0) {
            File file2 = new File(file, "journal.bkp");
            if (file2.exists()) {
                File file3 = new File(file, "journal");
                if (file3.exists()) {
                    file2.delete();
                } else {
                    a(file2, file3, false);
                }
            }
            x0 x0Var = new x0(file, i, i2, j);
            if (x0Var.b.exists()) {
                try {
                    x0Var.f();
                    x0Var.e();
                    x0Var.i = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(x0Var.b, true), z4.a));
                    return x0Var;
                } catch (IOException e2) {
                    System.out.println("DiskLruCache " + file + " is corrupt: " + e2.getMessage() + ", removing");
                    x0Var.c();
                }
            }
            file.mkdirs();
            x0 x0Var2 = new x0(file, i, i2, j);
            x0Var2.g();
            return x0Var2;
        }
        throw new IllegalArgumentException("valueCount <= 0");
    }

    private void e(String str) {
        if (o.matcher(str).matches()) {
            return;
        }
        throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,64}: \"" + str + JSUtil.QUOTE);
    }

    public void c() throws IOException {
        close();
        z4.a(this.a);
    }

    private void b() {
        if (this.i == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String b(InputStream inputStream) {
        return z4.a((Reader) new InputStreamReader(inputStream, z4.b));
    }

    private static void a(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    private static void a(File file, File file2, boolean z) throws IOException {
        if (z) {
            a(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    public c a(String str) {
        return a(str, -1L);
    }

    private synchronized c a(String str, long j) {
        b();
        e(str);
        d dVar = (d) this.j.get(str);
        a aVar = null;
        if (j != -1 && (dVar == null || dVar.e != j)) {
            return null;
        }
        if (dVar != null) {
            if (dVar.d != null) {
                return null;
            }
        } else {
            dVar = new d(this, str, aVar);
            this.j.put(str, dVar);
        }
        c cVar = new c(this, dVar, aVar);
        dVar.d = cVar;
        this.i.write("DIRTY " + str + '\n');
        this.i.flush();
        return cVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(c cVar, boolean z) {
        d dVar = cVar.a;
        if (dVar.d == cVar) {
            if (z && !dVar.c) {
                for (int i = 0; i < this.g; i++) {
                    if (cVar.b[i]) {
                        if (!dVar.b(i).exists()) {
                            cVar.a();
                            return;
                        }
                    } else {
                        cVar.a();
                        throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                    }
                }
            }
            for (int i2 = 0; i2 < this.g; i2++) {
                File fileB = dVar.b(i2);
                if (z) {
                    if (fileB.exists()) {
                        File fileA = dVar.a(i2);
                        fileB.renameTo(fileA);
                        long j = dVar.b[i2];
                        long length = fileA.length();
                        dVar.b[i2] = length;
                        this.h = (this.h - j) + length;
                    }
                } else {
                    a(fileB);
                }
            }
            this.k++;
            dVar.d = null;
            if (!(dVar.c | z)) {
                this.j.remove(dVar.a);
                this.i.write("REMOVE " + dVar.a + '\n');
            } else {
                dVar.c = true;
                this.i.write("CLEAN " + dVar.a + dVar.a() + '\n');
                if (z) {
                    long j2 = this.l;
                    this.l = 1 + j2;
                    dVar.e = j2;
                }
            }
            this.i.flush();
            if (this.h > this.f || d()) {
                this.m.submit(this.n);
                return;
            }
            return;
        }
        throw new IllegalStateException();
    }
}
