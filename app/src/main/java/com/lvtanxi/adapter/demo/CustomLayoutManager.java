package com.lvtanxi.adapter.demo;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;


public class CustomLayoutManager extends RecyclerView.LayoutManager {
    private int mVerticalOffset;//竖直偏移量 每次换行时，要根据这个offset判断
    private int mFirstVisiPos;//屏幕可见的第一个View的Position
    private int mLastVisiPos;//屏幕可见的最后一个View的Position
    private int mWidthpixels; //获取RecyclerView宽度
    private int mHeightPixels;//获取RecyclerView高度
    private int mTotalPixels;//item的总高度
    private SpanSizeLookup mSpanSizeLookup; //获取每一条数据应该跨越的行
    private int mIndexOffLine;//记录每一种SpanCount的下标
    private int mLastSpanCount;//记录最后一次SpanCount
    private SparseArray<Rect> mItemRects;//key 是View的position，保存View的bounds 和 显示标志，


    public CustomLayoutManager() {
        setAutoMeasureEnabled(true);
        mItemRects = new SparseArray<>();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {//没有Item，界面空着吧
            // 先解绑和回收所有的ViewHolder
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {//state.isPreLayout()是支持动画的
            return;
        }
        //onLayoutChildren方法在RecyclerView 初始化时 会执行两遍
        detachAndScrapAttachedViews(recycler);

        //初始化区域
        mVerticalOffset = 0;
        mLastVisiPos = getItemCount();
        if (mWidthpixels == 0) {
            mWidthpixels = getHorizontalSpace();
            mHeightPixels = getVerticalSpace();
        }
        //初始化时调用 填充childView
        fill(recycler, 0);

    }


    /**
     * 填充childView的核心方法,应该先填充，再移动。
     * 在填充时，预先计算dy的在内，如果View越界，回收掉。
     * 一般情况是返回dy，如果出现View数量不足，则返回修正后的dy.
     *
     * @param dy RecyclerView给我们的位移量,+,显示底端， -，显示头部
     * @return 修正以后真正的dy（可能剩余空间不够移动那么多了 所以return <|dy|）
     */
    private int fill(RecyclerView.Recycler recycler, int dy) {
        //重置子View的高度
        mTotalPixels = 0;
        //获取PaddingTop
        int topOffset = getPaddingTop();

        //回收越界子View
        if (getChildCount() > 0) {//滑动时进来的
            for (int i = getChildCount() - 1; i >= 0; i--) {
                View child = getChildAt(i);
                if (dy > 0) {//需要回收当前屏幕，上越界的View
                    if (getDecoratedBottom(child) - dy < topOffset) {
                        //回收View
                        removeAndRecycleView(child, recycler);
                        mFirstVisiPos++;
                    }
                } else if (dy < 0) {//回收当前屏幕，下越界的View
                    if (getDecoratedTop(child) - dy > getHeight() - getPaddingBottom()) {
                        removeAndRecycleView(child, recycler);
                        mLastVisiPos--;
                    }
                }
            }
        }

        int leftOffset = getPaddingLeft();
        int lineMaxHeight = 0;
        //布局子View阶段
        if (dy >= 0) {
            int minPos = mFirstVisiPos;
            mLastVisiPos = getItemCount() - 1;
            if (getChildCount() > 0) {
                View lastView = getChildAt(getChildCount() - 1);
                minPos = getPosition(lastView) + 1;//从最后一个View+1开始吧
                topOffset = getDecoratedTop(lastView);
                leftOffset = getDecoratedRight(lastView);
                lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(lastView));
            }
            if (minPos > mLastVisiPos)
                mTotalPixels = mHeightPixels;
            //顺序addChildView
            View child;
            ViewGroup.LayoutParams layoutParams;
            for (int i = minPos; i <= mLastVisiPos; i++) {
                //获取每一个item的SpanCount
                int spanCount = getSpanCount(i);
                //把RecyclerView的宽度均分
                int itemWith = mWidthpixels / spanCount;
                //找recycler要一个childItemView,我们不管它是从scrap里取，还是从RecyclerViewPool里取，亦或是onCreateViewHolder里拿。
                child = recycler.getViewForPosition(i);
                //获取child的LayoutParams，一杯后面强制改变
                layoutParams = child.getLayoutParams();
                //设置View的宽度(不然View的实际宽度不会改变)
                layoutParams.width=itemWith;
                //重新设置LayoutParams
                child.setLayoutParams(layoutParams);
                // 然后我们将得到的View添加到Recycler中
                addView(child);
                // 然后测量View带Margin的的尺寸
                measureChildWithMargins(child, 0, 0);
                //计算宽度 包括margin
                int decoratedMeasurementVertical = getDecoratedMeasurementVertical(child);
                //判断是否换行，三种情况需要换行。1：i==0；2：spanCount发生改变，3：spanCount未变但是mIndexOffLine与mIndexOffLine相等了
                if (((mLastSpanCount == spanCount) && (mIndexOffLine % spanCount != 0)) || i == 0) {//当前行还排列的下
                    // 然后layout带Margin的View，将View放置到对应的位置
                    layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + itemWith, topOffset + decoratedMeasurementVertical);

                    //保存Rect供逆序layout用
                    Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset, leftOffset + itemWith, topOffset + decoratedMeasurementVertical + mVerticalOffset);
                    mItemRects.put(i, rect);

                    //改变 left  lineHeight
                    leftOffset += itemWith;
                    lineMaxHeight = Math.max(lineMaxHeight, decoratedMeasurementVertical);
                    //记录当前spanCount内放了几个View了
                    mIndexOffLine++;
                } else {//当前行排列不下
                    //改变top  left  lineHeight
                    leftOffset = getPaddingLeft();
                    //计算PaddingTop
                    topOffset += lineMaxHeight;
                    lineMaxHeight = 0;


                 /*   //重置LastSpanCount
                    mLastSpanCount = spanCount;
                    //重置IndexOffLine
                    mIndexOffLine = 1;
                    // 然后layout带Margin的View，将View放置到对应的位置
                    layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + itemWith, topOffset + decoratedMeasurementVertical);

                    //保存Rect供逆序layout用
                    Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset, leftOffset + itemWith, mTotalPixels = (topOffset + decoratedMeasurementVertical + mVerticalOffset));
                    mItemRects.put(i, rect);

                    //改变 left  lineHeight
                    leftOffset += itemWith;
                    lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(child));*/


                    //新起一行的时候要判断一下边界
                    if (topOffset - dy > getHeight() - getPaddingBottom()) {
                        //越界了 就回收
                        removeAndRecycleView(child, recycler);
                        mLastVisiPos = i - 1;
                        //获取最后渲染项的SpanCount
                        mLastSpanCount = getSpanCount(mLastVisiPos);
                        //把RecyclerView的高度直接成子View的高度
                        mTotalPixels = mHeightPixels;
                    } else {
                        //重置LastSpanCount
                        mLastSpanCount = spanCount;
                        //重置IndexOffLine
                        mIndexOffLine = 1;
                        // 然后layout带Margin的View，将View放置到对应的位置
                        layoutDecoratedWithMargins(child, leftOffset, topOffset, leftOffset + itemWith, topOffset + decoratedMeasurementVertical);

                        //保存Rect供逆序layout用
                        Rect rect = new Rect(leftOffset, topOffset + mVerticalOffset, leftOffset + itemWith, mTotalPixels = (topOffset + decoratedMeasurementVertical + mVerticalOffset));
                        mItemRects.put(i, rect);

                        //改变 left  lineHeight
                        leftOffset += itemWith;
                        lineMaxHeight = Math.max(lineMaxHeight, getDecoratedMeasurementVertical(child));
                    }
                }
            }
            //添加完后，判断是否已经没有更多的ItemView，并且此时屏幕仍有空白，则需要修正dy
            View lastChild = getChildAt(getChildCount() - 1);
            if (getPosition(lastChild) == getItemCount() - 1) {
                int gap = getHeight() - getPaddingBottom() - getDecoratedBottom(lastChild);
                if (gap > 0)
                    dy -= gap;
            }

        } else {
            //把RecyclerView的高度直接成子View的高度(因为这是滑出界面，所以已经是大于了)
            mTotalPixels = mHeightPixels;
            /**
             * ##  利用Rect保存子View边界
             正序排列时，保存每个子View的Rect，逆序时，直接拿出来layout。
             */
            int maxPos = getItemCount() - 1;
            mFirstVisiPos = 0;
            if (getChildCount() > 0) {
                View firstView = getChildAt(0);
                maxPos = getPosition(firstView) - 1;
            }
            for (int i = maxPos; i >= mFirstVisiPos; i--) {
                Rect rect = mItemRects.get(i);

                if (rect.bottom - mVerticalOffset - dy < getPaddingTop()) {
                    mFirstVisiPos = i + 1;
                    break;
                } else {
                    View child = recycler.getViewForPosition(i);
                    addView(child, 0);//将View添加至RecyclerView中，childIndex为1，但是View的位置还是由layout的位置决定
                    measureChildWithMargins(child, 0, 0);

                    layoutDecoratedWithMargins(child, rect.left, rect.top - mVerticalOffset, rect.right, rect.bottom - mVerticalOffset);
                }
            }
        }


        return dy;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        //位移0、没有子View、没有满屏 当然不移动
        if (dy == 0 || getChildCount() == 0 || mHeightPixels > mTotalPixels) {
            return 0;
        }

        int realOffset = dy;//实际滑动的距离， 可能会在边界处被修复
        //边界修复代码
        if (mVerticalOffset + realOffset < 0) {//上边界
            realOffset = -mVerticalOffset;
        } else if (realOffset > 0) {//下边界
            //利用最后一个子View比较修正
            View lastChild = getChildAt(getChildCount() - 1);
            if (getPosition(lastChild) == getItemCount() - 1) {
                int gap = getHeight() - getPaddingBottom() - getDecoratedBottom(lastChild);
                if (gap > 0) {
                    realOffset = -gap;
                } else if (gap == 0) {
                    realOffset = 0;
                } else {
                    realOffset = Math.min(realOffset, -gap);
                }
            }
        }

        realOffset = fill(recycler, realOffset);//先填充，再位移。

        mVerticalOffset += realOffset;//累加实际滑动距离

        offsetChildrenVertical(-realOffset);//滑动

        return realOffset;
    }


    /**
     * 获取某个childView在竖直方向所占的空间
     *
     * @param view
     * @return
     */
    private int getDecoratedMeasurementVertical(View view) {
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)
                view.getLayoutParams();
        return getDecoratedMeasuredHeight(view) + params.topMargin + params.bottomMargin;
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        mSpanSizeLookup = spanSizeLookup;
    }

    private int getSpanCount(int position) {
        if (mSpanSizeLookup != null)
            return mSpanSizeLookup.getSpanSize(position);
        return 1;
    }


    public interface SpanSizeLookup {
        int getSpanSize(int position);
    }
}
