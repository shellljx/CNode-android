package com.licrafter.cnode.widget.richTextView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.TextView;

import com.licrafter.cnode.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * author: shell
 * date 2017/3/2 上午10:34
 **/
public class AsyncImageGetter implements Html.ImageGetter {

    private Context mContext;
    private TextView mContainer;
    private Drawable mDefaultDrawable;
    private int mMaxWidth;

    public AsyncImageGetter(Context context, TextView view) {
        this.mContext = context;
        this.mContainer = view;
        mMaxWidth = getDisplayWidth(context);
        mDefaultDrawable = context.getResources().getDrawable(R.mipmap.ic_launcher);
    }

    /**
     * 当解析到img标签的时候调用,source是获取到的链接
     *
     * @param source
     * @return
     */
    @Override
    public Drawable getDrawable(String source) {
        final URLDrawable urlDrawable = new URLDrawable();
        source = source.indexOf("http") < 0 ? "https:" + source : source;
        Picasso.with(mContext)
                .load(source)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        if (bitmap != null) {
                            int width;
                            int height;
                            if (bitmap.getWidth() > mMaxWidth) {
                                width = mMaxWidth;
                                height = mMaxWidth * bitmap.getHeight() / bitmap.getWidth();
                            } else {
                                width = bitmap.getWidth();
                                height = bitmap.getHeight();
                            }
                            Drawable drawable = new BitmapDrawable(mContext.getResources(), bitmap);
                            drawable.setBounds(0, 0, width, height);
                            urlDrawable.setBounds(0, 0, width, height);
                            urlDrawable.mDrawable = drawable;
                            mContainer.setText(mContainer.getText());
                        }
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        return urlDrawable;
    }

    public class URLDrawable extends BitmapDrawable {

        public Drawable mDrawable;

        @Override
        public void draw(Canvas canvas) {
            if (mDrawable != null) {
                mDrawable.draw(canvas);
            } else {
                mDefaultDrawable.draw(canvas);
            }
        }
    }

    public int getDisplayWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        return displayWidth;
    }

}
