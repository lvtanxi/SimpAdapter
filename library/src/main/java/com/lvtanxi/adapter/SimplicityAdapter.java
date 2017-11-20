package com.lvtanxi.adapter;

import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.lvtanxi.adapter.convert.IViewConvert;
import com.lvtanxi.adapter.listener.IViewHolderCreator;
import com.lvtanxi.adapter.listener.OnItemClickListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SimplicityAdapter extends AbstractSimplicityAdapter {

    private List<Type> dataTypes = new ArrayList<>();

    private Map<Type, IViewHolderCreator> creators = new ArrayMap<>();

    private IViewHolderCreator defaultCreator = null;
    private OnItemClickListener mOnItemClickListener = null;

    private List<Object> mDatas;

    protected SimplicityAdapter() {
        mDatas = new ArrayList<>();
    }

    public static SimplicityAdapter create() {
        return new SimplicityAdapter();
    }

    public static <T extends SimplicityAdapter> T create(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void addItems(List<Object> data, boolean isRefresh) {//刷新
        if (isRefresh) {
            mDatas.clear();
            notifyDataSetChanged();
        }
        addItems(data);
    }


    public void addItems(List<Object> items) {
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
        Type dataType = dataTypes.get(viewType);
        IViewHolderCreator creator = creators.get(dataType);
        if (creator == null) {
            for (Type t : creators.keySet()) {
                if (isTypeMatch(t, dataType)) {
                    creator = creators.get(t);
                    break;
                }
            }
        }
        if (creator == null) {
            if (defaultCreator == null) {
                throw new IllegalArgumentException(String.format("Neither the TYPE: %s not The DEFAULT injector found...", dataType));
            }
            creator = defaultCreator;
        }
        return creator.create(parent);
    }

    private boolean isTypeMatch(Type type, Type targetType) {
        if (type instanceof Class && targetType instanceof Class) {
            if (((Class) type).isAssignableFrom((Class) targetType)) {
                return true;
            }
        } else if (type instanceof ParameterizedType && targetType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            ParameterizedType parameterizedTargetType = (ParameterizedType) targetType;
            if (isTypeMatch(parameterizedType.getRawType(), ((ParameterizedType) targetType).getRawType())) {
                Type[] types = parameterizedType.getActualTypeArguments();
                Type[] targetTypes = parameterizedTargetType.getActualTypeArguments();
                if (types == null || targetTypes == null || types.length != targetTypes.length) {
                    return false;
                }
                int len = types.length;
                for (int i = 0; i < len; i++) {
                    if (!isTypeMatch(types[i], targetTypes[i])) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }


    public <T> SimplicityAdapter registerDefault(final int layoutRes, final SimplicityConvert<T> simplicityConvert) {
        defaultCreator = SimplicityViewHolder(layoutRes, simplicityConvert);
        return this;
    }

    public <T> SimplicityAdapter registerDefaultOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        return this;
    }


    public <T> SimplicityAdapter register(final int layoutRes, final SimplicityConvert<T> simplicityConvert) {
        Type type = getConvertActualTypeArguments(simplicityConvert);
        if (type == null) {
            throw new IllegalArgumentException();
        }
        creators.put(type, SimplicityViewHolder(layoutRes, simplicityConvert));
        return this;
    }


    private <T> IViewHolderCreator<T> SimplicityViewHolder(final int layoutRes, final SimplicityConvert<T> simplicityConvert) {
        return new IViewHolderCreator<T>() {
            @Override
            public SimplicityViewHolder<T> create(ViewGroup parent) {
                return new SimplicityViewHolder<T>(parent, layoutRes) {
                    @Override
                    protected void convert(IViewConvert viewConvert, T t, int position) {
                        simplicityConvert.convert(viewConvert, t, position);
                    }
                };
            }
        };
    }

    private <T> Type getConvertActualTypeArguments(SimplicityConvert<T> convert) {
        Type[] interfaces = convert.getClass().getGenericInterfaces();
        for (Type type : interfaces) {
            if (type instanceof ParameterizedType) {
                if (((ParameterizedType) type).getRawType().equals(SimplicityConvert.class)) {
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
        Object item = mDatas.get(position);
        int index = dataTypes.indexOf(item.getClass());
        if (index == -1) {
            dataTypes.add(item.getClass());
        }
        index = dataTypes.indexOf(item.getClass());
        return index;
    }


    public void clearDatas() {
        if (mDatas != null) {
            mDatas.clear();
            notifyDataSetChanged();
        }
    }

}
