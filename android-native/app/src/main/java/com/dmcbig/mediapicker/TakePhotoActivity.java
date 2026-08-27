package com.dmcbig.mediapicker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import androidx.core.content.FileProvider;
import com.dcloud.android.widget.toast.ToastCompat;
import com.dmcbig.mediapicker.entity.Media;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class TakePhotoActivity extends Activity {
    Uri NuriForFile;
    File mTmpFile = null;

    private File createImageFile() throws IOException {
        return File.createTempFile("JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + "_", ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        ArrayList<? extends Parcelable> arrayList = new ArrayList<>();
        if (i == 100 || (i == 101 && i2 == -1 && this.mTmpFile.length() > 0)) {
            arrayList.add(new Media(this.mTmpFile.getPath(), this.mTmpFile.getName(), 0L, 1, this.mTmpFile.length(), 0, ""));
        }
        Intent intent2 = new Intent();
        intent2.putParcelableArrayListExtra(PickerConfig.EXTRA_RESULT, arrayList);
        setResult(PickerConfig.RESULT_CODE, intent2);
        finish();
    }

    @Override // android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        try {
            this.mTmpFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".dmc", this.mTmpFile);
            this.NuriForFile = uriForFile;
            intent.putExtra("output", uriForFile);
            startActivityForResult(intent, 100);
            return;
        }
        File file = this.mTmpFile;
        if (file == null || !file.exists()) {
            ToastCompat.makeText((Context) this, (CharSequence) "take error", 0).show();
            finish();
        } else {
            intent.putExtra("output", Uri.fromFile(this.mTmpFile));
            startActivityForResult(intent, PickerConfig.PICKER_IMAGE_VIDEO);
        }
    }
}
