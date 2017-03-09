package com.licrafter.cnode.widget.exp;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

/**
 * author: shell
 * date 2017/1/11 下午9:49
 **/
public class ExpandableLayout extends FrameLayout implements ExpandableHeader.HeaderCollapseListener {

    private static final float DEFAULT_FACTOR = 0.5f;

    private ExpandableHeader mHeader;
    private OnLayoutScrollListener mListener;

    private int mTouchSlop;
    private int mMinMargin;
    private int mMaxMargin;
    private int mThreshold;
    private float mFactor;
    private float mLastedY;
    private boolean mDraging;
    private boolean mCollapsed;//完全遮盖住header为坍塌状态
    private boolean mSticky;
    private Animator mAnimator;
    private int start;
    private int end;

    public ExpandableLayout(Context context) {
        this(context, null);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mFactor = DEFAULT_FACTOR;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) {
            throw new IllegalStateException("ExpandableLayout can host only one direct child");
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float y = ev.getRawY();
        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastedY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastedY - y;
                if (!mSticky && Math.abs(dy) > mTouchSlop || mSticky && dy < 0 && isTop()) {
                    mLastedY = y;
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getRawY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mLastedY = y;
                return true;
            case MotionEvent.ACTION_MOVE:
                float dy = mLastedY - y;
                if (Math.abs(dy) > mTouchSlop && !mDraging) {
                    mDraging = true;
                }
                if (mDraging) {
                    setTopMargin((int) (getTopMargin() - dy));
                    mLastedY = y;
                }
                return true;
            case MotionEvent.ACTION_UP:
                if (mHeader != null && mHeader.isScrolling()) {
                    //do nothing.
                } else {
                    fling();
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 使header坍塌
     */
    public void collapse() {
        startFlingAnimation(getTopMargin(), mMinMargin);
    }

    private void fling() {
        start = getTopMargin();
        if (mCollapsed) {
            if (Math.abs(getTopMargin() - mMinMargin) < mThreshold) {
                end = mMinMargin;
            } else {
                end = mMaxMargin;
            }
        } else {
            if (getScrollDistance() < mThreshold) {
                end = mMaxMargin;
            } else {
                end = mMinMargin;
            }
        }
        startFlingAnimation(start, end);
    }

    @Override
    public void onHeaderCollapse() {
        startFlingAnimation(getTopMargin(), mMinMargin);
    }

    private void startFlingAnimation(int start, int end) {
        if (mAnimator == null || !mAnimator.isRunning()) {
            mAnimator = ObjectAnimator.ofInt(this, "TopMargin", start, end);
            mAnimator.setDuration(300);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.start();
        }
    }

    @Override
    public void onHeaderHeightChange(int height) {
        initMargin(height);
    }

    public void setTopMargin(int topMargin) {
        getMarginLayoutParams().topMargin = topMargin;

        if (mListener != null) {
            mListener.onScroll(getScrollDistance());
        }

        if (getTopMargin() >= mHeader.getBottom()) {
            mCollapsed = false;
            getMarginLayoutParams().topMargin = mHeader.getBottom();
        }
        if (getTopMargin() <= mMinMargin) {
            mSticky = true;
            mCollapsed = true;
            getMarginLayoutParams().topMargin = mMinMargin;
        } else {
            mSticky = false;
        }
        if (mHeader != null) {
            mHeader.setTopMargin((int) (-(mMaxMargin - getTopMargin()) * mFactor));
        }
        requestLayout();
    }

    private int getScrollDistance() {
        return mMaxMargin - getTopMargin();
    }

    public int getThreshold() {
        return mThreshold;
    }

    public int getMinMargin() {
        return mMinMargin;
    }

    public int getMaxMargin() {
        return mMaxMargin;
    }

    public int getTopMargin() {
        return getMarginLayoutParams().topMargin;
    }

    public void setFactor(float factor) {
        mFactor = factor;
    }

    public float getFactor() {
        return mFactor;
    }

    private MarginLayoutParams getMarginLayoutParams() {
        return (MarginLayoutParams) getLayoutParams();
    }

    /**
     * 设置header
     *
     * @param header ExpandableHeader
     */
    public void setUpWithHeader(ExpandableHeader header) {
        if (header == null) {
            throw new RuntimeException("The ExpandableHeader is null!");
        }
        mHeader = header;
        mHeader.setCollapseListener(this);
        initMargin(mHeader.getBottom());
    }

    private void initMargin(int topMargin) {
        getMarginLayoutParams().topMargin = topMargin;
        mMaxMargin = topMargin;
        requestLayout();
    }

    public void setThreshold(int threshold) {
        mThreshold = threshold;
    }

    public void setMinMargin(int minMargin) {
        mMinMargin = minMargin;
    }

    public boolean isCollapsed() {
        return mCollapsed;
    }

    private boolean isTop() {
        View scrollableView = getChildAt(0);
        if (scrollableView == null) {
            return true;
        }
        if (scrollableView instanceof AdapterView) {
            return isAdapterViewTop((AdapterView) scrollableView);
        }
        if (scrollableView instanceof ScrollView) {
            return isScrollViewTop((ScrollView) scrollableView);
        }
        if (scrollableView instanceof RecyclerView) {
            return isRecyclerViewTop((RecyclerView) scrollableView);
        }
        if (scrollableView instanceof WebView) {
            return isWebViewTop((WebView) scrollableView);
        }
        return true;
    }

    private boolean isAdapterViewTop(AdapterView adapterView) {
        if (adapterView != null) {
            int firstVisiblePosition = adapterView.getFirstVisiblePosition();
            View childAt = adapterView.getChildAt(0);
            if (childAt == null || (firstVisiblePosition == 0 && childAt.getTop() == 0)) {
                return true;
            }
        }
        return false;
    }

    private boolean isScrollViewTop(ScrollView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    private boolean isWebViewTop(WebView scrollView) {
        if (scrollView != null) {
            int scrollViewY = scrollView.getScrollY();
            return scrollViewY <= 0;
        }
        return false;
    }

    private boolean isRecyclerViewTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                View childAt = recyclerView.getChildAt(0);
                if (childAt == null || (firstVisibleItemPosition == 0 && childAt.getTop() == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setOnLayoutScrollListener(OnLayoutScrollListener listener) {
        mListener = listener;
    }

    public interface OnLayoutScrollListener {
        void onScroll(int scrollDistance);
    }
}
