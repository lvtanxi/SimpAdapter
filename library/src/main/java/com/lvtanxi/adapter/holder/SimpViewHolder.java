package com.lvtanxi.adapter.holder;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;
import com.lvtanxi.adapter.convert.DefaultViewConvert;
import com.lvtanxi.adapter.convert.ViewConvert;
import com.lvtanxi.adapter.listener.OnItemChildClickListener;
import com.lvtanxi.adapter.listener.OnItemClickListener;
import com.lvtanxi.adapter.listener.OnNoDoubleClickListener;

/**
 * Date: 2017-12-08
 * Time: 15:34
 * Description: RecyclerView.ViewHolder 实体
 */
public abstract class SimpViewHolder<D> extends RecyclerView.ViewHolder {

    /**
     * 记录获取过的View
     */
    private SparseArray<View> mViewSparseArray;

    /**
     * 为View设值的接口
     */
    private ViewConvert mViewConvert;

    /**
     * 某个item的数据
     */
    private D mData;

    /**
     * item子View的点击事件
     */
    private OnItemChildClickListener mOnItemChildClickListener;

    /**
     * 根据layout获取itemView
     */
    public SimpViewHolder(ViewGroup parent, int itemLayoutRes) {
        this(LayoutInflater.from(parent.getContext()).inflate(itemLayoutRes, parent, false));
    }

    /**
     * 初始化SparseArray
     */
    public SimpViewHolder(View itemView) {
        super(itemView);
        mViewSparseArray = new SparseArray<>();
    }

    /**
     * 为在Adaper onBindViewHolder的时候调用
     */
    final public void convert(D d, int position) {
        if (mViewConvert == null)
            mViewConvert = new DefaultViewConvert(this);
        mData = d;
        convert(mViewConvert, d, position);
    }

    public void bindOnItemClickListener(final OnItemClickListener<D> onItemClickListener) {
        if (onItemClickListener != null) {
            itemView.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (v.getId() == SimpViewHolder.this.itemView.getId())
                        onItemClickListener.onItemClick(v, mData, getAdapterPosition());
                }
            });
        }
    }


    protected abstract void convert(ViewConvert convert, D d, int position);

    @SuppressWarnings({"unchecked"})
    public final <T extends View> T getView(int id) {
        View view = mViewSparseArray.get(id);
        if (view == null) {
            view = itemView.findViewById(id);
            mViewSparseArray.put(id, view);
        }
        return (T) view;
    }

    public void setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        mOnItemChildClickListener = onItemChildClickListener;
    }

    public void invokeOnItemChildClickListener(View v) {
        if (mOnItemChildClickListener != null)
            mOnItemChildClickListener.onItemChildClick(v, getAdapterPosition());
    }
}
