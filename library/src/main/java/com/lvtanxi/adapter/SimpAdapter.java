package com.lvtanxi.adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lvtanxi.adapter.convert.LayoutConvert;
import com.lvtanxi.adapter.convert.SimpConvert;
import com.lvtanxi.adapter.convert.ViewConvert;
import com.lvtanxi.adapter.holder.SimpViewHolder;
import com.lvtanxi.adapter.listener.OnItemChildClickListener;
import com.lvtanxi.adapter.listener.OnItemClickListener;
import com.lvtanxi.adapter.listener.ViewHolderCreator;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SimpAdapter extends AbsSimpAdapter {

    private List<Type> mDataTypes = new ArrayList<>();

    private Map<Type, ViewHolderCreator> mCreators = new ArrayMap<>();

    private Map<Type, OnItemClickListener> mItemClickListeners = new ArrayMap<>();

    private OnItemChildClickListener mOnItemClickListener = null;

    protected List<Object> mDatas;

    protected SimpAdapter() {
        mDatas = new ArrayList<>();
    }

    public static SimpAdapter create() {
        return new SimpAdapter();
    }

    public static <T extends SimpAdapter> T create(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("创建adapter失败");
    }


    public void addItems(List<?> data, boolean isRefresh) {//刷新
        if (isRefresh) {
            mDatas.clear();
            notifyDataSetChanged();
        }
        addItems(data);
    }


    public void addItems(List<?> items) {
        if (items != null && !items.isEmpty()) {
            if (mDatas.addAll(items))
                notifyDataSetChanged();
        }
    }

    public List<Object> getDatas() {
        return mDatas;
    }


    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @SuppressWarnings("ConstantConditions")
    @NonNull
    @Override
    public SimpViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Type dataType = mDataTypes.get(viewType);
        ViewHolderCreator creator = mCreators.get(dataType);
        if (creator == null)
            throw new IllegalArgumentException(String.format("Neither the TYPE: %s not The DEFAULT injector found...", dataType));

        SimpViewHolder simpViewHolder = creator.create(parent);
        if (simpViewHolder != null) {
            simpViewHolder.bindOnItemClickListener(mItemClickListeners.get(dataType));
            simpViewHolder.setOnItemChildClickListener(mOnItemClickListener);
        }
        return simpViewHolder;
    }

    public <T> SimpAdapter map(int layoutRes, SimpConvert<T> simpConvert) {
        Type type = getConvertActualTypeArguments(simpConvert);
        mCreators.put(type, createSimpViewHolder(layoutRes, simpConvert));
        return this;
    }

    public <T> SimpAdapter map(int layoutRes,Class<T> cla,SimpConvert<T> simpConvert) {
        mCreators.put(cla, createSimpViewHolder(layoutRes, simpConvert));
        return this;
    }


    public <T> SimpAdapter register(OnItemClickListener<T> onItemClickListener) {
        Type type = getConvertActualTypeArguments(onItemClickListener);
        mItemClickListeners.put(type, onItemClickListener);
        return this;
    }

    public <T> SimpAdapter register(Class<T> cla,OnItemClickListener<T> onItemClickListener) {
        mItemClickListeners.put(cla, onItemClickListener);
        return this;
    }


    public SimpAdapter register(OnItemChildClickListener itemChildClickListener) {
        this.mOnItemClickListener = itemChildClickListener;
        return this;
    }


    protected <T> ViewHolderCreator<T> createSimpViewHolder(final int layoutRes, final SimpConvert<T> simpConvert) {
        return new ViewHolderCreator<T>() {
            @Override
            public SimpViewHolder<T> create(ViewGroup parent) {
                return new SimpViewHolder<T>(parent, layoutRes) {
                    @Override
                    protected void convert(ViewConvert viewConvert, T t, int position) {
                        simpConvert.convert(viewConvert, t, position);
                    }
                };
            }
        };
    }


    private Type getConvertActualTypeArguments(Object convert) {
        if (convert == null)
            throw new IllegalArgumentException("SimpConvert not null");
        if (convert instanceof LayoutConvert) {
            return ((LayoutConvert) convert).getTagetClass();
        }
        Type[] interfaces = convert.getClass().getGenericInterfaces();
        for (Type type : interfaces) {
            if (!(type instanceof ParameterizedType))
                continue;
            Type rawType = ((ParameterizedType) type).getRawType();
            if (!rawType.equals(SimpConvert.class) && !rawType.equals(OnItemClickListener.class))
                continue;
            Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];

            if (actualType instanceof Class)
                return actualType;
        }
        throw new IllegalArgumentException("The generic type argument of SimpConvert is NOT support Generic Parameterized Type now");
    }

    public SimpAdapter attachTo(RecyclerView... recyclerViews) {
        for (RecyclerView recyclerView : recyclerViews) {
            recyclerView.setAdapter(this);
        }
        return this;
    }

    @Override
    public int getItemViewType(int position) {
        Object item = getItem(position);
        int index = mDataTypes.indexOf(item.getClass());
        if (index == -1) {
            mDataTypes.add(item.getClass());
        }
        index = mDataTypes.indexOf(item.getClass());
        return index;
    }


    public void clearDatas() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

    public boolean isEmpty() {
        return mDatas.size() == 0;
    }


    public <T> T convert() {
        return (T) this;
    }


    public boolean isLast(int position) {
        return (mDatas.size() - 1) == position;
    }

    public <T> T getItemModel(int position) {
        return (T) getItem(position);
    }

}
