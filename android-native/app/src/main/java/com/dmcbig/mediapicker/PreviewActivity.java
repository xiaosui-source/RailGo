package com.dmcbig.mediapicker;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.OnApplyWindowInsetsListener;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.utils.ScreenUtils;
import com.dmcbig.mediapicker.view.PreviewFragment;
import com.taobao.weex.common.Constants;
import com.taobao.weex.el.parse.Operators;
import io.dcloud.base.R;
import io.dcloud.common.adapter.util.AndroidResources;
import io.dcloud.common.adapter.util.DeviceInfo;
import io.dcloud.common.util.BaseInfo;
import io.dcloud.common.util.PdrUtil;
import java.util.ArrayList;
import java.util.List;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class PreviewActivity extends FragmentActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {
    TextView bar_title;
    View bottom;
    ImageView check_image;
    LinearLayout check_layout;
    Button done;
    private Class editActivityClass;
    ArrayList<Fragment> fragmentArrayList;
    ImageView fullImage;
    TextView imageEdit;
    private int max;
    ArrayList<Media> preRawList;
    ArrayList<Media> selects;
    View top;
    ViewPager viewpager;
    boolean isSingle = false;
    private boolean editable = true;
    String doneBtnText = "";

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class AdapterFragment extends FragmentStatePagerAdapter {
        private List<Fragment> mFragments;

        public AdapterFragment(FragmentManager fragmentManager, List<Fragment> list) {
            super(fragmentManager);
            this.mFragments = list;
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getCount() {
            return this.mFragments.size();
        }

        @Override // androidx.fragment.app.FragmentStatePagerAdapter
        public Fragment getItem(int i) {
            return this.mFragments.get(i);
        }

        @Override // androidx.viewpager.widget.PagerAdapter
        public int getItemPosition(Object obj) {
            return -2;
        }
    }

    public void done(ArrayList<Media> arrayList, int i) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(PickerConfig.EXTRA_RESULT, arrayList);
        intent.putIntegerArrayListExtra(PickerConfig.ORIGINAL_PREVIEW_INDEX, getIntent().getIntegerArrayListExtra(PickerConfig.ORIGINAL_PREVIEW_INDEX));
        intent.putParcelableArrayListExtra(PickerConfig.EDITED_PREVIEW_DATA, this.preRawList);
        intent.putExtra(PickerConfig.FULL_IMAGE, this.fullImage.isSelected());
        setResult(i, intent);
        finish();
    }

    public void fitAndroid16WindowInsets(Activity activity, View view, final int i) {
        if (Build.VERSION.SDK_INT < 36 || AndroidResources.sAppTargetSdkVersion < 36) {
            return;
        }
        ViewCompat.setOnApplyWindowInsetsListener(view, new OnApplyWindowInsetsListener() { // from class: com.dmcbig.mediapicker.PreviewActivity$$ExternalSyntheticLambda0
            @Override // androidx.core.view.OnApplyWindowInsetsListener
            public final WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
                return this.f$0.m199x6dc081f3(i, view2, windowInsetsCompat);
            }
        });
    }

    public int isSelect(Media media, ArrayList<Media> arrayList) {
        if (arrayList.size() <= 0) {
            return -1;
        }
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).path.equals(media.path)) {
                return i;
            }
        }
        return -1;
    }

    /* renamed from: lambda$fitAndroid16WindowInsets$0$com-dmcbig-mediapicker-PreviewActivity, reason: not valid java name */
    /* synthetic */ WindowInsetsCompat m199x6dc081f3(int i, View view, WindowInsetsCompat windowInsetsCompat) {
        if (windowInsetsCompat != null) {
            int i2 = getResources().getConfiguration().orientation;
            if (i2 != 3 && i2 != 1) {
                Insets insets = windowInsetsCompat.getInsets(i);
                view.setPadding(insets.left, 0, insets.right, 0);
                return windowInsetsCompat;
            }
            view.setPadding(0, 0, 0, 0);
        }
        return windowInsetsCompat;
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i2 == -1 && i == 0 && intent != null) {
            int intExtra = intent.getIntExtra("IMAGE_INDEX", -1);
            int intExtra2 = intent.getIntExtra("_id", -1);
            if (intExtra2 == -1 || intExtra == -1) {
                return;
            }
            Media media = new Media(intent.getStringExtra("PATH"), intent.getStringExtra("_display_name"), intent.getLongExtra("date_added", System.currentTimeMillis()), intent.getIntExtra("mime_type", 0), intent.getLongExtra("_size", 0L), intExtra2, intent.getStringExtra("PARENTPATH"));
            int iIsSelect = isSelect(this.preRawList.get(intExtra), this.selects);
            if (iIsSelect >= 0) {
                this.selects.set(iIsSelect, media);
            }
            this.preRawList.set(intExtra, media);
            this.fragmentArrayList.set(intExtra, PreviewFragment.newInstance(media, ""));
            this.viewpager.getAdapter().notifyDataSetChanged();
        }
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        done(this.selects, PickerConfig.RESULT_UPDATE_CODE);
        super.onBackPressed();
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == MediaPickerR.MP_ID_BTN_BACK) {
            done(this.selects, PickerConfig.RESULT_UPDATE_CODE);
            return;
        }
        if (id == MediaPickerR.MP_ID_DONE) {
            done(this.selects, PickerConfig.RESULT_CODE);
            return;
        }
        if (id == MediaPickerR.MP_ID_CHECK_LAYOUT) {
            Media media = this.preRawList.get(this.viewpager.getCurrentItem());
            int iIsSelect = isSelect(media, this.selects);
            if (iIsSelect < 0) {
                this.check_image.setImageDrawable(ContextCompat.getDrawable(this, MediaPickerR.MP_DRAWABLE_BNT_SELECTED));
                this.selects.add(media);
            } else {
                this.check_image.setImageDrawable(ContextCompat.getDrawable(this, MediaPickerR.MP_DRAWABLE_BNT_UNSELECTED));
                this.selects.remove(iIsSelect);
            }
            setDoneView(this.selects.size());
            return;
        }
        if (id != R.id.gallery_preview_edit) {
            if (id == R.id.check_origin_image_layout) {
                this.fullImage.setSelected(!r5.isSelected());
                return;
            }
            return;
        }
        int currentItem = this.viewpager.getCurrentItem();
        Media media2 = this.preRawList.get(currentItem);
        Intent intent = new Intent(this, (Class<?>) this.editActivityClass);
        intent.putExtra("IMAGE_URI", Uri.parse(DeviceInfo.FILE_PROTOCOL + media2.path));
        intent.putExtra("IMAGE_MEDIA_ID", media2.id);
        intent.putExtra("IMAGE_INDEX", currentItem);
        startActivityForResult(intent, 0);
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) throws Resources.NotFoundException {
        super.onCreate(bundle);
        requestWindowFeature(1);
        if (Build.VERSION.SDK_INT >= 29) {
            getWindow().setNavigationBarContrastEnforced(false);
        }
        setContentView(MediaPickerR.MP_LAYOUT_PREVIEW_MAIN);
        setTopAndBottomBarColor();
        findViewById(MediaPickerR.MP_ID_BTN_BACK).setOnClickListener(this);
        this.isSingle = getIntent().getBooleanExtra(PickerConfig.SINGLE_SELECT, false);
        this.check_image = (ImageView) findViewById(MediaPickerR.MP_ID_CHECK_IMAGE);
        LinearLayout linearLayout = (LinearLayout) findViewById(MediaPickerR.MP_ID_CHECK_LAYOUT);
        this.check_layout = linearLayout;
        linearLayout.setOnClickListener(this);
        this.bar_title = (TextView) findViewById(MediaPickerR.MP_ID_BAR_TITLE);
        Button button = (Button) findViewById(MediaPickerR.MP_ID_DONE);
        this.done = button;
        button.setOnClickListener(this);
        this.top = findViewById(MediaPickerR.MP_ID_TOP);
        this.bottom = findViewById(MediaPickerR.MP_ID_BOTTOM);
        TextView textView = (TextView) findViewById(R.id.gallery_preview_edit);
        this.imageEdit = textView;
        textView.setOnClickListener(this);
        this.fullImage = (ImageView) findViewById(R.id.check_origin_image);
        this.fullImage.setSelected(getIntent().getBooleanExtra(PickerConfig.FULL_IMAGE, false));
        String stringExtra = getIntent().getStringExtra(PickerConfig.SIZE_TYPE);
        if (stringExtra == null) {
            findViewById(R.id.check_origin_image_layout).setOnClickListener(this);
        } else if (stringExtra.contains(Constants.Value.ORIGINAL) && stringExtra.contains("compressed")) {
            findViewById(R.id.check_origin_image_layout).setOnClickListener(this);
        } else if (!stringExtra.contains(Constants.Value.ORIGINAL)) {
            findViewById(R.id.check_origin_image_layout).setVisibility(8);
        }
        if (getIntent().getIntExtra(PickerConfig.SELECT_MODE, PickerConfig.PICKER_IMAGE_VIDEO) == 102) {
            findViewById(R.id.check_origin_image_layout).setVisibility(8);
        }
        this.editable = getIntent().getBooleanExtra(PickerConfig.IMAGE_EDITABLE, true);
        String stringExtra2 = getIntent().getStringExtra(PickerConfig.DONE_BUTTON_TEXT);
        this.doneBtnText = stringExtra2;
        if (!PdrUtil.isEmpty(stringExtra2)) {
            this.done.setText(this.doneBtnText);
        }
        this.max = getIntent().getIntExtra(PickerConfig.MAX_SELECT_COUNT, Integer.MAX_VALUE);
        try {
            this.editActivityClass = Class.forName("io.dcloud.feature.gallery.imageedit.IMGEditActivity");
        } catch (ClassNotFoundException unused) {
        }
        if (this.isSingle) {
            this.bottom.setVisibility(8);
        }
        this.viewpager = (ViewPager) findViewById(MediaPickerR.MP_ID_VIEWPAGER);
        this.preRawList = getIntent().getParcelableArrayListExtra(PickerConfig.PRE_RAW_LIST);
        this.selects = new ArrayList<>();
        ArrayList<Media> arrayList = this.preRawList;
        if (arrayList == null || arrayList.size() == 0) {
            onBackPressed();
            return;
        }
        this.selects.addAll(this.preRawList);
        if (BaseInfo.sGlobalFullScreen) {
            setFullScreen(this, true);
        }
        setView(this.preRawList);
        if (Build.VERSION.SDK_INT < 36 || AndroidResources.sAppTargetSdkVersion < 36) {
            return;
        }
        ScreenUtils.fitAndroid16WindowInsets(this, findViewById(R.id.top), WindowInsets.Type.statusBars());
        ScreenUtils.fitAndroid16WindowInsets(this, findViewById(R.id.bottom), WindowInsets.Type.navigationBars());
        fitAndroid16WindowInsets(this, findViewById(R.id.root), WindowInsets.Type.navigationBars());
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i, float f, int i2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i) {
        this.bar_title.setText((i + 1) + "/" + this.preRawList.size());
        Media media = this.preRawList.get(i);
        if (".gif".equalsIgnoreCase(media.extension) || this.editActivityClass == null || media.mediaType == 3 || !this.editable) {
            this.imageEdit.setVisibility(4);
        } else {
            this.imageEdit.setVisibility(0);
        }
        this.check_image.setImageDrawable(ContextCompat.getDrawable(this, isSelect(media, this.selects) < 0 ? MediaPickerR.MP_DRAWABLE_BNT_UNSELECTED : MediaPickerR.MP_DRAWABLE_BNT_SELECTED));
    }

    public void setBarStatus() {
        Log.e("setBarStatus", "setBarStatus");
        if (this.top.getVisibility() == 0) {
            this.top.setVisibility(8);
            if (this.isSingle) {
                return;
            }
            this.bottom.setVisibility(8);
            return;
        }
        this.top.setVisibility(0);
        if (this.isSingle) {
            return;
        }
        this.bottom.setVisibility(0);
    }

    void setDoneView(int i) {
        if (PdrUtil.isEmpty(this.doneBtnText)) {
            this.doneBtnText = getString(MediaPickerR.MP_STRING_DONE);
        }
        if (this.max == Integer.MAX_VALUE) {
            this.done.setText(this.doneBtnText + Operators.BRACKET_START_STR + i + Operators.BRACKET_END_STR);
            return;
        }
        this.done.setText(this.doneBtnText + Operators.BRACKET_START_STR + i + "/" + this.max + Operators.BRACKET_END_STR);
    }

    public void setFullScreen(Activity activity, boolean z) {
        Window window = activity.getWindow();
        if (z) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.flags |= 1024;
            window.setAttributes(attributes);
        } else {
            WindowManager.LayoutParams attributes2 = window.getAttributes();
            attributes2.flags &= -1025;
            window.setAttributes(attributes2);
        }
    }

    void setView(ArrayList<Media> arrayList) throws Resources.NotFoundException {
        setDoneView(arrayList.size());
        this.bar_title.setText("1/" + this.preRawList.size());
        int i = 0;
        Media media = this.preRawList.get(0);
        if (".gif".equalsIgnoreCase(media.extension) || this.editActivityClass == null || media.mediaType == 3 || !this.editable) {
            this.imageEdit.setVisibility(4);
        }
        this.fragmentArrayList = new ArrayList<>();
        int size = arrayList.size();
        while (i < size) {
            Media media2 = arrayList.get(i);
            i++;
            this.fragmentArrayList.add(PreviewFragment.newInstance(media2, ""));
        }
        this.viewpager.setAdapter(new AdapterFragment(getSupportFragmentManager(), this.fragmentArrayList));
        this.viewpager.addOnPageChangeListener(this);
    }

    private void setTopAndBottomBarColor() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.setStatusBarColor(Color.parseColor("#21282C"));
        window.setNavigationBarColor(Color.parseColor("#21282C"));
    }
}
