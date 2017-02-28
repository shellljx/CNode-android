package com.licrafter.cnode.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.licrafter.cnode.R;

/**
 * author: shell
 * date 2017/2/27 下午1:15
 **/
public class TopicDividerDecoration extends RecyclerView.ItemDecoration {
    private int dividerHeight;
    private Paint dividerPaint;

    public TopicDividerDecoration(Context context) {
        dividerPaint = new Paint();
        dividerPaint.setColor(ContextCompat.getColor(context, R.color.gray));
        dividerHeight = 2;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom = dividerHeight;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        for (int i = 0; i < childCount - 1; i++) {
            View view = parent.getChildAt(i);
            float top = view.getBottom();
            float bottom = view.getBottom() + dividerHeight;
            c.drawRect(left, top, right, bottom, dividerPaint);
        }
    }
}
