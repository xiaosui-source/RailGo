package com.dmcbig.mediapicker.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.dcloud.android.widget.toast.ToastCompat;
import com.dmcbig.mediapicker.MediaPickerR;
import com.dmcbig.mediapicker.PickerConfig;
import com.dmcbig.mediapicker.entity.Media;
import com.dmcbig.mediapicker.utils.FileUtils;
import com.dmcbig.mediapicker.utils.ScreenUtils;
import io.dcloud.common.util.HarmonyUtils;
import io.dcloud.common.util.ThreadPool;
import java.io.IOException;
import java.util.ArrayList;

/* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
/* loaded from: classes.dex */
public class MediaGridAdapter extends RecyclerView.Adapter<MyViewHolder> {
    Context context;
    private final Handler handler;
    boolean isSingle;
    long maxSelect;
    long maxSize;
    ArrayList<Media> medias;
    ArrayList<Media> selectMedias;
    FileUtils fileUtils = new FileUtils();
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private OnPickerSelectMaxListener mOnMaxListener = null;

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView check_image;
        public RelativeLayout gif_info;
        public View mask_view;
        public ImageView media_image;
        public TextView textView_size;
        public RelativeLayout video_info;

        public MyViewHolder(View view) {
            super(view);
            this.media_image = (ImageView) view.findViewById(MediaPickerR.MP_ID_MEDIA_IMAGE);
            this.check_image = (ImageView) view.findViewById(MediaPickerR.MP_ID_CHECK_IMAGE);
            this.mask_view = view.findViewById(MediaPickerR.MP_ID_MASK_VIEW);
            this.video_info = (RelativeLayout) view.findViewById(MediaPickerR.MP_ID_VIDEO_INFO);
            this.gif_info = (RelativeLayout) view.findViewById(MediaPickerR.MP_ID_GIF_INFO);
            this.textView_size = (TextView) view.findViewById(MediaPickerR.MP_ID_TEXTVIEW_SIZE);
            this.itemView.setLayoutParams(new AbsListView.LayoutParams(-1, MediaGridAdapter.this.getItemWidth()));
        }
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface OnPickerSelectMaxListener {
        void onMaxed();
    }

    /* compiled from: r8-map-id-104f5cb9443f97f226975c9c3da4326f7fb829858955ebeffa5836ad8678ba8c */
    public interface OnRecyclerViewItemClickListener {
        void onAddMore();

        void onItemClick(View view, Media media, ArrayList<Media> arrayList);
    }

    public MediaGridAdapter(ArrayList<Media> arrayList, Context context, ArrayList<Media> arrayList2, int i, long j, boolean z) {
        this.selectMedias = new ArrayList<>();
        this.isSingle = false;
        if (arrayList2 != null) {
            this.selectMedias = arrayList2;
        }
        this.maxSelect = i;
        this.maxSize = j;
        this.medias = arrayList;
        this.context = context;
        this.isSingle = z;
        this.handler = new Handler(Looper.getMainLooper());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.medias.size();
    }

    public int getItemIndex(Object obj) {
        return this.medias.indexOf(obj);
    }

    int getItemWidth() {
        int screenWidth = ScreenUtils.getScreenWidth(this.context);
        int i = PickerConfig.GridSpanCount;
        return (screenWidth / i) - i;
    }

    public ArrayList<Media> getMedias() {
        return this.medias;
    }

    public ArrayList<Media> getSelectMedias() {
        return this.selectMedias;
    }

    public int isSelect(Media media) {
        if (this.selectMedias.size() <= 0) {
            return -1;
        }
        for (int i = 0; i < this.selectMedias.size(); i++) {
            Media media2 = this.selectMedias.get(i);
            if (media2.path.equals(media.path)) {
                media2.id = media.id;
                return i;
            }
        }
        return -1;
    }

    /* renamed from: lambda$onBindViewHolder$1$com-dmcbig-mediapicker-adapter-MediaGridAdapter, reason: not valid java name */
    /* synthetic */ void m200x45afd60c(Uri uri, Media media, final MyViewHolder myViewHolder) {
        try {
            final Bitmap bitmapLoadThumbnail = Build.VERSION.SDK_INT >= 29 ? this.context.getContentResolver().loadThumbnail(uri, new Size(getItemWidth(), getItemWidth()), null) : media.mediaType == 3 ? MediaStore.Video.Thumbnails.getThumbnail(this.context.getContentResolver(), media.id, 1, null) : MediaStore.Images.Thumbnails.getThumbnail(this.context.getContentResolver(), media.id, 1, null);
            this.handler.post(new Runnable() { // from class: com.dmcbig.mediapicker.adapter.MediaGridAdapter$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    myViewHolder.media_image.setImageBitmap(bitmapLoadThumbnail);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener onRecyclerViewItemClickListener) {
        this.mOnItemClickListener = onRecyclerViewItemClickListener;
    }

    public void setOnMaxListener(OnPickerSelectMaxListener onPickerSelectMaxListener) {
        this.mOnMaxListener = onPickerSelectMaxListener;
    }

    public void setSelectMedias(Media media) {
        int iIsSelect = isSelect(media);
        if (iIsSelect == -1) {
            this.selectMedias.add(media);
        } else {
            this.selectMedias.remove(iIsSelect);
        }
    }

    public void updateAdapter(ArrayList<Media> arrayList) {
        this.medias = arrayList;
        notifyDataSetChanged();
    }

    public void updateSelectAdapter(ArrayList<Media> arrayList) {
        if (arrayList != null) {
            this.selectMedias = arrayList;
        }
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(final MyViewHolder myViewHolder, int i) {
        Context context;
        int i2;
        final Media media = this.medias.get(i);
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        int i3 = media.mediaType;
        if (i3 != 1 && i3 == 3) {
            uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        }
        int i4 = media.id;
        if (i4 != Integer.MIN_VALUE) {
            final Uri uriWithAppendedId = ContentUris.withAppendedId(uri, i4);
            if (HarmonyUtils.isHarmonyOs()) {
                ThreadPool.self().addThreadTask(new Runnable() { // from class: com.dmcbig.mediapicker.adapter.MediaGridAdapter$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m200x45afd60c(uriWithAppendedId, media, myViewHolder);
                    }
                });
            } else {
                Glide.with(this.context).load(uriWithAppendedId).into(myViewHolder.media_image);
            }
        } else {
            myViewHolder.media_image.setImageResource(Integer.parseInt(media.path));
        }
        if (media.id == Integer.MIN_VALUE) {
            myViewHolder.gif_info.setVisibility(4);
            myViewHolder.video_info.setVisibility(4);
            myViewHolder.textView_size.setVisibility(4);
        } else if (media.mediaType == 3) {
            myViewHolder.gif_info.setVisibility(4);
            myViewHolder.video_info.setVisibility(0);
            myViewHolder.textView_size.setVisibility(0);
            myViewHolder.textView_size.setText(this.fileUtils.getSizeByUnit(media.size));
        } else {
            myViewHolder.video_info.setVisibility(4);
            myViewHolder.textView_size.setVisibility(0);
            myViewHolder.gif_info.setVisibility(".gif".equalsIgnoreCase(media.extension) ? 0 : 4);
        }
        int iIsSelect = isSelect(media);
        if (media.id == Integer.MIN_VALUE || this.isSingle) {
            myViewHolder.check_image.setVisibility(8);
            myViewHolder.mask_view.setVisibility(8);
        } else {
            myViewHolder.mask_view.setVisibility(iIsSelect >= 0 ? 0 : 4);
            myViewHolder.check_image.setVisibility(0);
            ImageView imageView = myViewHolder.check_image;
            if (iIsSelect >= 0) {
                context = this.context;
                i2 = MediaPickerR.MP_DRAWABLE_BNT_SELECTED;
            } else {
                context = this.context;
                i2 = MediaPickerR.MP_DRAWABLE_BNT_UNSELECTED;
            }
            imageView.setImageDrawable(ContextCompat.getDrawable(context, i2));
        }
        myViewHolder.media_image.setOnClickListener(new View.OnClickListener() { // from class: com.dmcbig.mediapicker.adapter.MediaGridAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                Context context2;
                int i5;
                Media media2 = media;
                if (media2.id == Integer.MIN_VALUE) {
                    MediaGridAdapter.this.mOnItemClickListener.onAddMore();
                    return;
                }
                MediaGridAdapter mediaGridAdapter = MediaGridAdapter.this;
                if (mediaGridAdapter.isSingle) {
                    mediaGridAdapter.mOnItemClickListener.onItemClick(view, media, null);
                    return;
                }
                int iIsSelect2 = mediaGridAdapter.isSelect(media2);
                long size = MediaGridAdapter.this.selectMedias.size();
                MediaGridAdapter mediaGridAdapter2 = MediaGridAdapter.this;
                if (size >= mediaGridAdapter2.maxSelect && iIsSelect2 < 0) {
                    if (mediaGridAdapter2.mOnMaxListener != null) {
                        MediaGridAdapter.this.mOnMaxListener.onMaxed();
                        return;
                    }
                    return;
                }
                if (media.size > mediaGridAdapter2.maxSize) {
                    ToastCompat.makeText(mediaGridAdapter2.context, (CharSequence) (MediaGridAdapter.this.context.getString(MediaPickerR.MP_STRING_MSG_SIZE_LIMIT) + FileUtils.fileSize(MediaGridAdapter.this.maxSize)), 1).show();
                    return;
                }
                myViewHolder.mask_view.setVisibility(iIsSelect2 >= 0 ? 4 : 0);
                ImageView imageView2 = myViewHolder.check_image;
                if (iIsSelect2 >= 0) {
                    context2 = MediaGridAdapter.this.context;
                    i5 = MediaPickerR.MP_DRAWABLE_BNT_UNSELECTED;
                } else {
                    context2 = MediaGridAdapter.this.context;
                    i5 = MediaPickerR.MP_DRAWABLE_BNT_SELECTED;
                }
                imageView2.setImageDrawable(ContextCompat.getDrawable(context2, i5));
                MediaGridAdapter.this.setSelectMedias(media);
                MediaGridAdapter.this.mOnItemClickListener.onItemClick(view, media, MediaGridAdapter.this.selectMedias);
            }
        });
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(MediaPickerR.MP_LAYOUT_MEDIA_VIEW_ITEM, viewGroup, false));
    }
}
