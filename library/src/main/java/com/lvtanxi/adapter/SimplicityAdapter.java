package com.lvtanxi.adapter;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lvtanxi.adapter.convert.ViewConvert;
import com.lvtanxi.adapter.convert.SimplicityConvert;
import com.lvtanxi.adapter.holder.SimplicityViewHolder;
import com.lvtanxi.adapter.listener.ViewHolderCreator;
import com.lvtanxi.adapter.listener.OnItemChildClickListener;
import com.lvtanxi.adapter.listener.OnItemClickListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SimplicityAdapter extends AbstractSimplicityAdapter {

    private List<Type> mDataTypes = new ArrayList<>();

    private Map<Type, ViewHolderCreator> mCreators = new ArrayMap<>();
    private ViewHolderCreator mDefaultCreator = null;

    private Map<Type, OnItemClickListener> mItemClickListeners = new ArrayMap<>();
    private OnItemClickListener mDefaultItemClickListener = null;

    private OnItemChildClickListener mOnItemClickListener = null;

    protected List<Object> mDatas;

    protected SimplicityAdapter() {
        mDatas = new ArrayList<>();
    }

    public static SimplicityAdapter create() {
        return new SimplicityAdapter();
    }

    public static <T extends SimplicityAdapter> T create(Class<T> clazz) {
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

    @Override
    public SimplicityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Type dataType = mDataTypes.get(viewType);
        ViewHolderCreator creator = mCreators.get(dataType);
        if (creator == null) {
            if (mDefaultCreator == null)
                throw new IllegalArgumentException(String.format("Neither the TYPE: %s not The DEFAULT injector found...", dataType));
            creator = mDefaultCreator;
            mCreators.put(dataType, mDefaultCreator);
        }

        SimplicityViewHolder simplicityViewHolder = creator.create(parent);
        if (simplicityViewHolder != null) {
            simplicityViewHolder.bindOnItemClickListener(mDefaultItemClickListener, mItemClickListeners.get(dataType));
            simplicityViewHolder.setOnItemChildClickListener(mOnItemClickListener);
        }
        return simplicityViewHolder;
    }


    public <T> SimplicityAdapter register(int layoutRes, SimplicityConvert<T> simplicityConvert) {
        Type type = getConvertActualTypeArguments(simplicityConvert);
        if (type == null) {
            throw new IllegalArgumentException();
        }
        mCreators.put(type, createSimplicityViewHolder(layoutRes, simplicityConvert));
        return this;
    }

    public <T> SimplicityAdapter registerDefault(int layoutRes, SimplicityConvert<T> simplicityConvert) {
        mDefaultCreator = createSimplicityViewHolder(layoutRes, simplicityConvert);
        return this;
    }

    public <T> SimplicityAdapter registerOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        Type type = getConvertActualTypeArguments(onItemClickListener);
        if (type == null)
            throw new IllegalArgumentException();
        mItemClickListeners.put(type, onItemClickListener);
        return this;
    }

    public <T> SimplicityAdapter registerDefaultOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        mDefaultItemClickListener= onItemClickListener;
        return this;
    }

    public SimplicityAdapter registerOnItemChildClickListener(OnItemChildClickListener itemChildClickListener) {
        this.mOnItemClickListener = itemChildClickListener;
        return this;
    }


    private <T> ViewHolderCreator<T> createSimplicityViewHolder(final int layoutRes, final SimplicityConvert<T> simplicityConvert) {
        return new ViewHolderCreator<T>() {
            @Override
            public SimplicityViewHolder<T> create(ViewGroup parent) {
                return new SimplicityViewHolder<T>(parent, layoutRes) {
                    @Override
                    protected void convert(ViewConvert viewConvert, T t, int position) {
                        simplicityConvert.convert(viewConvert, t, position);
                    }
                };
            }
        };
    }

    private Type getConvertActualTypeArguments(Object convert) {
        Type[] interfaces = convert.getClass().getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType) {
                Type rawType = ((ParameterizedType) type).getRawType();
                if (rawType.equals(SimplicityConvert.class) || rawType.equals(OnItemClickListener.class)) {
                    Type actualType = ((ParameterizedType) type).getActualTypeArguments()[0];
                    if (actualType instanceof Class) {
                        return actualType;
                    } else {
                        throw new IllegalArgumentException("The generic type argument of SimplicityConvert is NOT support Generic Parameterized Type now, Please using a WRAPPER class install of it directly.");
                    }
                }
            }
        }
        return null;
    }

    public SimplicityAdapter attachTo(RecyclerView... recyclerViews) {
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
        return mDatas.size()==0;
    }


    public <T>T convert(){
        return (T) this;
    }


    public boolean isLast(int position) {
        return (mDatas.size() - 1) == position;
    }

    public <T> T getItemModel(int position){
        return (T) getItem(position);
    }

}
