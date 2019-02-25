package com.lvtanxi.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;

import com.lvtanxi.adapter.listener.OnSectionedListener;
import com.lvtanxi.adapter.listener.Section;


/**
 * Date: 2018-01-04
 * Time: 13:34
 * Description: 分组adapter(设计相对简单)
 */
@SuppressWarnings({"unchecked"})
public class SimpSectionedAdapter extends SimpAdapter {
    //存储Sectioned对应的Position
    private final SparseIntArray mHeaderLocationMap;
    //存储Position对应的Sectioned
    private final SparseIntArray mSectionMap;
    //存储Position对应的子View的Position
    private final SparseIntArray mPositionMap;

    private OnSectionedListener mSectionedListener;

    private boolean mOpenSctionChange=true;


    public SimpSectionedAdapter() {
        super();
        mHeaderLocationMap = new SparseIntArray();
        mSectionMap = new SparseIntArray();
        mPositionMap = new SparseIntArray();
    }


    /**
     * 获取条目中子View的个数
     */
    private int getSectionedCount(int section) {
        if (mSectionedListener != null)
            return mSectionedListener.getSectionedChildCount(mDatas.get(section), section);
        else if (mDatas.get(section) instanceof Section)
            return ((Section) mDatas.get(section)).getSectionChildCount();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        int section = getSectionIndex(position);
        if (isSectioned(position)) {
            return mDatas.get(section);
        } else if (mSectionedListener != null) {
            int child = mPositionMap.get(position);
            return mSectionedListener.getSectionedChildItem(mDatas.get(section), section, child);
        } else if (mDatas.get(section) instanceof Section) {
            int child = mPositionMap.get(position);
            return ((Section) mDatas.get(section)).getSectionChildItem(child);
        }
        throw new RuntimeException("没有找到对应的item");
    }


    /**
     * 判断是否是条目
     */
    public boolean isSectioned(int position) {
        return mHeaderLocationMap.get(position, -1) != -1;
    }

    /**
     * 根据position获取条目位置
     */
    private int getSectionIndex(int itemPosition) {
        //判断是否已经计算过了，避免重读计算
        if (mSectionMap.get(itemPosition, -1) == -1 || mPositionMap.get(itemPosition, -1) == -1) {
            //枷锁计算
            synchronized (mHeaderLocationMap) {
                int lastSectionIndex = -1;
                int count = mHeaderLocationMap.size();
                for (int i = 0; i < count; i++) {
                    //得到条目
                    if (itemPosition >= mHeaderLocationMap.keyAt(i)) {
                        lastSectionIndex = mHeaderLocationMap.keyAt(i);
                    } else {
                        break;
                    }
                }
                //存储次itemPosition的条目位置和itemPosition子view的位置
                mSectionMap.put(itemPosition, mHeaderLocationMap.get(lastSectionIndex));
                mPositionMap.put(itemPosition, itemPosition - lastSectionIndex - 1);
            }
        }
        return mSectionMap.get(itemPosition);

    }

    @Override
    public final int getItemCount() {
        //计算ItemCount
        int count = 0;
        for (int s = 0; s < mDatas.size(); s++) {
            int itemCount = getSectionedCount(s);
            if (itemCount > 0) {
                //这里先存储条目位置
                mHeaderLocationMap.put(count, s);
                count += itemCount + 1;
            }
        }
        return count;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(!mOpenSctionChange)
            return;
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return isSectioned(position) ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public void setOpenSctionChange(boolean openSctionChange) {
        mOpenSctionChange = openSctionChange;
    }

    public <T> SimpSectionedAdapter registerSectionedListener(OnSectionedListener<T> sectionedListener) {
        this.mSectionedListener = sectionedListener;
        return this;
    }


    public void clearSectionedData() {
        mHeaderLocationMap.clear();
        mSectionMap.clear();
        mPositionMap.clear();
    }

}
