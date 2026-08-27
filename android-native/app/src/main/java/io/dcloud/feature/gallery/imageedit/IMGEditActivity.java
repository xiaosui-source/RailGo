package io.dcloud.feature.gallery.imageedit;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import androidx.core.app.ActivityOptionsCompat;
import com.taobao.weex.common.Constants;
import io.dcloud.common.util.PdrUtil;
import io.dcloud.p.j2;
import io.dcloud.p.q2;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class IMGEditActivity extends a {
    private boolean j = false;
    private int k = 0;
    private int l = 0;
    private int m = 80;
    private boolean n = true;
    private boolean o = false;
    private String p;
    int q;

    @Override // io.dcloud.feature.gallery.imageedit.a, io.dcloud.p.i2.b
    public /* bridge */ /* synthetic */ void a() {
        super.a();
    }

    @Override // io.dcloud.feature.gallery.imageedit.a, io.dcloud.p.i2.b
    public /* bridge */ /* synthetic */ void b() {
        super.b();
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public /* bridge */ /* synthetic */ void c(int i) {
        super.c(i);
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void e() {
        finish();
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void f() {
        if (!this.j) {
            this.a.a();
            b(this.a.getMode() == j2.CLIP ? 1 : 0);
        } else if (PdrUtil.isEmpty(this.p) || !this.p.equals("camera")) {
            finish();
        } else {
            startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), 0, ActivityOptionsCompat.makeCustomAnimation(this, 0, 0).toBundle());
        }
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void g() {
        JSONObject jSONObject;
        int i;
        String stringExtra = getIntent().getStringExtra("IMAGE_CROP");
        this.q = getIntent().getIntExtra("IMAGE_MEDIA_ID", 0);
        if (TextUtils.isEmpty(stringExtra)) {
            jSONObject = null;
        } else {
            try {
                jSONObject = new JSONObject(stringExtra);
            } catch (JSONException unused) {
            }
        }
        if (jSONObject != null && jSONObject.length() > 1) {
            Pattern patternCompile = Pattern.compile("[^0-9]");
            try {
                this.k = Integer.parseInt(patternCompile.matcher(jSONObject.optString("width")).replaceAll(""));
                this.l = Integer.parseInt(patternCompile.matcher(jSONObject.optString("height")).replaceAll(""));
            } catch (Exception unused2) {
            }
            this.m = jSONObject.optInt(Constants.Name.QUALITY, 80);
            this.n = jSONObject.optBoolean("resize", true);
            int i2 = this.k;
            if (i2 > 0 && (i = this.l) > 0) {
                this.a.b(i2, i);
            }
            if (jSONObject.has("saveToAlbum")) {
                this.o = jSONObject.optBoolean("saveToAlbum", false);
            }
            a(j2.CLIP);
            this.j = true;
        }
        this.p = getIntent().getStringExtra("IMAGE_SOURCE");
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void h() throws IOException {
        Bitmap bitmapI;
        Uri uriInsert;
        int i;
        String stringExtra = getIntent().getStringExtra("IMAGE_SAVE_PATH");
        String str = System.currentTimeMillis() + ".jpg";
        if (TextUtils.isEmpty(stringExtra)) {
            if (Build.VERSION.SDK_INT >= 29) {
                stringExtra = getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/DImage/" + str;
            } else {
                stringExtra = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/DImage/" + str;
            }
        }
        String str2 = stringExtra;
        if (TextUtils.isEmpty(str2) || (bitmapI = this.a.i()) == null) {
            setResult(0);
            finish();
            return;
        }
        if (this.q == -1001) {
            File file = new File(str2);
            if (!file.exists()) {
                finish();
            }
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                Bitmap bitmapA = a(bitmapI);
                Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.JPEG;
                int i2 = this.m;
                bitmapA.compress(compressFormat, i2 > 100 ? 100 : i2 < 0 ? 80 : i2, fileOutputStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            setResult(-1, new Intent());
            finish();
            return;
        }
        if (this.j && !this.o) {
            String str3 = getExternalCacheDir().getPath() + "/DImage/" + str;
            File file2 = new File(str3);
            try {
                if (!file2.getParentFile().exists()) {
                    file2.getParentFile().mkdirs();
                }
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                Bitmap bitmapA2 = a(bitmapI);
                Bitmap.CompressFormat compressFormat2 = Bitmap.CompressFormat.JPEG;
                int i3 = this.m;
                bitmapA2.compress(compressFormat2, i3 > 100 ? 100 : i3 < 0 ? 80 : i3, fileOutputStream2);
            } catch (IOException unused) {
                setResult(0);
                finish();
            }
            Intent intent = new Intent();
            intent.putExtra("_id", Integer.MAX_VALUE);
            intent.putExtra("IMAGE_INDEX", getIntent().getIntExtra("IMAGE_INDEX", -1));
            intent.putExtra("PATH", str3);
            setResult(-1, intent);
            finish();
            return;
        }
        if (Build.VERSION.SDK_INT >= 29) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            contentValues.put("mime_type", "image/jpeg");
            contentValues.put("is_pending", (Integer) 0);
            contentValues.put("_display_name", str);
            contentValues.put("relative_path", Environment.DIRECTORY_PICTURES + "/DImage");
            uriInsert = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            try {
                OutputStream outputStreamOpenOutputStream = getContentResolver().openOutputStream(uriInsert);
                if (this.j) {
                    Bitmap bitmapA3 = a(bitmapI);
                    Bitmap.CompressFormat compressFormat3 = Bitmap.CompressFormat.JPEG;
                    int i4 = this.m;
                    if (i4 > 100) {
                        i4 = 100;
                    } else if (i4 < 0) {
                        i4 = 80;
                    }
                    bitmapA3.compress(compressFormat3, i4, outputStreamOpenOutputStream);
                } else {
                    bitmapI.compress(Bitmap.CompressFormat.JPEG, 100, outputStreamOpenOutputStream);
                }
            } catch (FileNotFoundException unused2) {
            }
        } else {
            File file3 = new File(str2);
            if (!file3.exists()) {
                try {
                    File parentFile = file3.getParentFile();
                    if (parentFile != null && !parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    file3.createNewFile();
                } catch (IOException unused3) {
                }
            }
            try {
                FileOutputStream fileOutputStream3 = new FileOutputStream(file3);
                if (this.j) {
                    Bitmap bitmapA4 = a(bitmapI);
                    Bitmap.CompressFormat compressFormat4 = Bitmap.CompressFormat.JPEG;
                    int i5 = this.m;
                    if (i5 > 100) {
                        i5 = 100;
                    } else if (i5 < 0) {
                        i5 = 80;
                    }
                    bitmapA4.compress(compressFormat4, i5, fileOutputStream3);
                } else {
                    bitmapI.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream3);
                }
            } catch (FileNotFoundException e2) {
                e2.printStackTrace();
            }
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("_data", str2);
            contentValues2.put("datetaken", Long.valueOf(System.currentTimeMillis()));
            contentValues2.put("mime_type", "image/jpeg");
            uriInsert = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues2);
        }
        Uri uri = uriInsert;
        Intent intent2 = new Intent();
        Cursor cursorQuery = getContentResolver().query(uri, new String[]{"_display_name", "date_added", "mime_type", "_size", "_data", "_id"}, null, null, null);
        if (cursorQuery != null) {
            cursorQuery.moveToNext();
            String string = cursorQuery.getString(cursorQuery.getColumnIndex("_display_name"));
            long j = cursorQuery.getLong(cursorQuery.getColumnIndex("date_added"));
            int i6 = cursorQuery.getInt(cursorQuery.getColumnIndex("mime_type"));
            long j2 = cursorQuery.getLong(cursorQuery.getColumnIndex("_size"));
            int i7 = cursorQuery.getInt(cursorQuery.getColumnIndex("_id"));
            String string2 = cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("_data"));
            String strA = a(string2);
            cursorQuery.close();
            intent2.putExtra("_display_name", string);
            intent2.putExtra("date_added", j);
            intent2.putExtra("mime_type", i6);
            intent2.putExtra("_size", j2);
            intent2.putExtra("_id", i7);
            intent2.putExtra("PARENTPATH", strA);
            i = -1;
            intent2.putExtra("IMAGE_INDEX", getIntent().getIntExtra("IMAGE_INDEX", -1));
            intent2.putExtra("PATH", string2);
        } else {
            i = -1;
        }
        setResult(i, intent2);
        finish();
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void i() throws IOException {
        this.a.b();
        if (this.j) {
            h();
        } else {
            b(this.a.getMode() == j2.CLIP ? 1 : 0);
        }
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void j() {
        this.a.h();
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void k() {
        this.a.c();
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public /* bridge */ /* synthetic */ void l() {
        super.l();
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void m() {
        j2 mode = this.a.getMode();
        if (mode == j2.DOODLE) {
            this.a.k();
        } else if (mode == j2.MOSAIC) {
            this.a.l();
        }
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public /* bridge */ /* synthetic */ void n() {
        super.n();
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
    }

    @Override // io.dcloud.feature.gallery.imageedit.a, android.app.Activity
    public /* bridge */ /* synthetic */ void onBackPressed() {
        super.onBackPressed();
    }

    @Override // io.dcloud.feature.gallery.imageedit.a, android.view.View.OnClickListener
    public /* bridge */ /* synthetic */ void onClick(View view) {
        super.onClick(view);
    }

    @Override // io.dcloud.feature.gallery.imageedit.a, android.content.DialogInterface.OnDismissListener
    public /* bridge */ /* synthetic */ void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
    }

    @Override // io.dcloud.feature.gallery.imageedit.a, android.content.DialogInterface.OnShowListener
    public /* bridge */ /* synthetic */ void onShow(DialogInterface dialogInterface) {
        super.onShow(dialogInterface);
    }

    @Override // io.dcloud.feature.gallery.imageedit.b.a
    public void a(q2 q2Var) {
        this.a.a(q2Var);
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public /* bridge */ /* synthetic */ void b(int i) {
        super.b(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0041  */
    @Override // io.dcloud.feature.gallery.imageedit.a
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public android.graphics.Bitmap c() throws java.io.FileNotFoundException {
        /*
            r8 = this;
            android.content.Intent r0 = r8.getIntent()
            r1 = 0
            if (r0 != 0) goto L8
            return r1
        L8:
            java.lang.String r2 = "IMAGE_URI"
            android.os.Parcelable r0 = r0.getParcelableExtra(r2)
            android.net.Uri r0 = (android.net.Uri) r0
            if (r0 != 0) goto L13
            return r1
        L13:
            java.lang.String r2 = r0.getPath()
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L41
            java.lang.String r2 = r0.getScheme()
            r2.getClass()
            java.lang.String r3 = "file"
            boolean r3 = r2.equals(r3)
            if (r3 != 0) goto L3b
            java.lang.String r3 = "asset"
            boolean r2 = r2.equals(r3)
            if (r2 != 0) goto L35
            goto L41
        L35:
            io.dcloud.p.a2 r2 = new io.dcloud.p.a2
            r2.<init>(r8, r0)
            goto L42
        L3b:
            io.dcloud.p.e2 r2 = new io.dcloud.p.e2
            r2.<init>(r0)
            goto L42
        L41:
            r2 = r1
        L42:
            if (r2 != 0) goto L45
            return r1
        L45:
            android.graphics.BitmapFactory$Options r3 = new android.graphics.BitmapFactory$Options
            r3.<init>()
            r4 = 1
            r3.inSampleSize = r4
            r3.inJustDecodeBounds = r4
            r2.a(r3)
            int r4 = r3.outWidth
            r5 = 1149239296(0x44800000, float:1024.0)
            r6 = 1065353216(0x3f800000, float:1.0)
            r7 = 1024(0x400, float:1.435E-42)
            if (r4 <= r7) goto L6a
            float r4 = (float) r4
            float r4 = r4 * r6
            float r4 = r4 / r5
            int r4 = java.lang.Math.round(r4)
            int r4 = io.dcloud.p.r2.a(r4)
            r3.inSampleSize = r4
        L6a:
            int r4 = r3.outHeight
            if (r4 <= r7) goto L82
            int r7 = r3.inSampleSize
            float r4 = (float) r4
            float r4 = r4 * r6
            float r4 = r4 / r5
            int r4 = java.lang.Math.round(r4)
            int r4 = io.dcloud.p.r2.a(r4)
            int r4 = java.lang.Math.max(r7, r4)
            r3.inSampleSize = r4
        L82:
            r4 = 0
            r3.inJustDecodeBounds = r4
            android.graphics.Bitmap r2 = r2.a(r3)
            if (r2 != 0) goto Ld0
            android.content.Intent r2 = r8.getIntent()
            java.lang.String r4 = "IMAGE_MEDIA_ID"
            r5 = -1
            int r2 = r2.getIntExtra(r4, r5)
            if (r2 != r5) goto L99
            return r1
        L99:
            r4 = -1001(0xfffffffffffffc17, float:NaN)
            if (r2 != r4) goto Lb6
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.io.FileNotFoundException -> Lac
            java.io.File r4 = new java.io.File     // Catch: java.io.FileNotFoundException -> Lac
            java.lang.String r0 = r0.getPath()     // Catch: java.io.FileNotFoundException -> Lac
            r4.<init>(r0)     // Catch: java.io.FileNotFoundException -> Lac
            r2.<init>(r4)     // Catch: java.io.FileNotFoundException -> Lac
            goto Lae
        Lac:
            r2 = r1
        Lae:
            if (r2 != 0) goto Lb1
            return r1
        Lb1:
            android.graphics.Bitmap r0 = android.graphics.BitmapFactory.decodeStream(r2, r1, r3)
            return r0
        Lb6:
            android.net.Uri r0 = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            long r4 = (long) r2
            android.net.Uri r0 = android.content.ContentUris.withAppendedId(r0, r4)
            android.content.ContentResolver r2 = r8.getContentResolver()     // Catch: java.io.FileNotFoundException -> Lc6
            java.io.InputStream r0 = r2.openInputStream(r0)     // Catch: java.io.FileNotFoundException -> Lc6
            goto Lc8
        Lc6:
            r0 = r1
        Lc8:
            if (r0 != 0) goto Lcb
            return r1
        Lcb:
            android.graphics.Bitmap r0 = io.dcloud.p.z.a(r0, r3)
            return r0
        Ld0:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: io.dcloud.feature.gallery.imageedit.IMGEditActivity.c():android.graphics.Bitmap");
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void a(j2 j2Var) {
        if (this.a.getMode() == j2Var) {
            j2Var = j2.NONE;
        }
        this.a.setMode(j2Var);
        n();
        if (j2Var == j2.CLIP) {
            b(1);
        }
    }

    private Bitmap a(Bitmap bitmap) {
        if (!this.n || this.l <= 0 || this.k <= 0) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.postScale(this.k / bitmap.getWidth(), this.l / bitmap.getHeight());
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }

    @Override // io.dcloud.feature.gallery.imageedit.a
    public void a(int i) {
        this.a.setPenColor(i);
    }

    public String a(String str) {
        return str.split("/")[r2.length - 2];
    }
}
