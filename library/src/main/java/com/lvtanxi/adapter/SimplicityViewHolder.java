package com.lvtanxi.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lvtanxi.adapter.convert.DefaultViewConvert;
import com.lvtanxi.adapter.convert.IViewConvert;
import com.lvtanxi.adapter.listener.OnItemChildClickListener;
import com.lvtanxi.adapter.listener.OnItemClickListener;
import com.lvtanxi.adapter.listener.OnNoDoubleClickListener;


public abstract class SimplicityViewHolder<D> extends RecyclerView.ViewHolder {

    private SparseArray<View> mViewSparseArray;

    private IViewConvert mIViewConvert;

    private D mData;

    private OnItemChildClickListener mOnItemChildClickListener;


    public SimplicityViewHolder(ViewGroup parent, int itemLayoutRes) {
        this(LayoutInflater.from(parent.getContext()).inflate(itemLayoutRes, parent, false));
    }

    public SimplicityViewHolder(View itemView) {
        super(itemView);
        mViewSparseArray = new SparseArray<>();
    }

    final void convert(D d, int position) {
        if (mIViewConvert == null)
            mIViewConvert = new DefaultViewConvert(this);
        mData = d;
        convert(mIViewConvert, d, position);
    }

    public void bindOnItemClickListener(final OnItemClickListener<D> defaultItemClickListener, final OnItemClickListener<D> onItemClickListener) {
        if (onItemClickListener != null || defaultItemClickListener != null) {
            itemView.setOnClickListener(new OnNoDoubleClickListener() {
                @Override
                public void onNoDoubleClick(View v) {
                    if (v.getId() == SimplicityViewHolder.this.itemView.getId()) {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(v, mData, getAdapterPosition());
                        else
                            defaultItemClickListener.onItemClick(v, mData, getAdapterPosition());
                    }
                }
            });
        }
    }


    protected abstract void convert(IViewConvert convert, D d, int position);


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
