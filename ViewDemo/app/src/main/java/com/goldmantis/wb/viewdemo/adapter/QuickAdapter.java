package com.goldmantis.wb.viewdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 00029935 on 2017/1/11.
 */

public abstract class QuickAdapter<T> extends RecyclerView.Adapter<QuickAdapter.VH> {

    public static final int HEADER_VIEW_TYPE = 0x1001;
    public static final int FOOTER_VIEW_TYPE = 0x1002;
    public static final int CONTENT_VIEW_TYPE = 0x1003;
    public static final int SPECIAL_VIEW_TYPE = 0x1004;//列表中有多种不同的布局

    protected View headerView;
    protected View footerView;

    private List<T> data;

    private OnItemClickListener<T> onItemClickListener;


    public QuickAdapter() {

    }

    public QuickAdapter(List<T> data){
        this.data = data;
    }


    public abstract int getLayoutID(int viewType);
    public abstract void convert(VH holder,T itemData,int position);

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case  HEADER_VIEW_TYPE:
                break;
            case FOOTER_VIEW_TYPE:
                break;
            case CONTENT_VIEW_TYPE:
                if(null != headerView && null != footerView){ // 头 和脚都有

                    convert(holder,data.get(position -1),position-1);

                    if(null != onItemClickListener){
                        holder.itemView.setClickable(true);
                        holder.itemView.setFocusable(true);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.OnItemClick(data.get(position -1),position -1);
                            }
                        });
                    }

                }else if(null != headerView && null == footerView){  // 只有头 没有脚

                    convert(holder,data.get(position -1),position-1);

                    if(null != onItemClickListener){
                        holder.itemView.setClickable(true);
                        holder.itemView.setFocusable(true);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.OnItemClick(data.get(position -1),position -1);
                            }
                        });
                    }

                }else if(null == headerView && null != footerView){   //只有脚 ，没有头

                    convert(holder,data.get(position ),position);

                    if(null != onItemClickListener){
                        holder.itemView.setClickable(true);
                        holder.itemView.setFocusable(true);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onItemClickListener.OnItemClick(data.get(position),position);
                            }
                        });
                    }

                } else if(null == headerView && null == footerView){   //头脚 都没有

                    convert(holder,data.get(position ),position);

                    if(null != onItemClickListener){
                        holder.itemView.setClickable(true);
                        holder.itemView.setFocusable(true);
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (data.size()>position) {
                                    onItemClickListener.OnItemClick(data.get(position), position);
                                }
                            }
                        });
                    }

                }

                break;
        }

    }

    @Override
    public int getItemCount() {
        int count = data.size();

        if(null != footerView){
            ++count;
        }

        if(null != headerView){
            ++count;
        }

        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if(null != headerView && null != footerView){ // 头 和脚都有

            if(0 == position){
                return HEADER_VIEW_TYPE;
            } else if(position == (getItemCount() -1)){
                return FOOTER_VIEW_TYPE;
            }else {
                return CONTENT_VIEW_TYPE;
            }

        }else if(null != headerView && null == footerView){  // 只有头 没有脚

            if(0 == position){
                return HEADER_VIEW_TYPE;
            }else {
                return CONTENT_VIEW_TYPE;
            }

        }else if(null == headerView && null != footerView){   //只有脚 ，没有头

             if(position == (getItemCount() -1)){
                return FOOTER_VIEW_TYPE;
            }else {
                return CONTENT_VIEW_TYPE;
            }

        } else if(null == headerView && null == footerView){   //头脚 都没有

            return CONTENT_VIEW_TYPE;
        }

        return CONTENT_VIEW_TYPE;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {

            switch (viewType){
                case HEADER_VIEW_TYPE:
                    if(null != headerView){
                        return  new VH(headerView);
                    }
                    break;
                case FOOTER_VIEW_TYPE:
                    if(null != footerView){
                        return  new VH(footerView);
                    }
                    break;
                case CONTENT_VIEW_TYPE:
                    return VH.get(parent,getLayoutID(viewType));
            }

        return VH.get(parent,getLayoutID(viewType));
    }

    public static class VH extends RecyclerView.ViewHolder{
        private SparseArray<View> mViews;

        public VH(View itemView) {
            super(itemView);
            mViews = new SparseArray<>();
        }

        public static VH get(ViewGroup parent, int layoutID){
            View converView = LayoutInflater.from(parent.getContext()).inflate(layoutID,parent,false);
            return new VH(converView);
        }

        public <T extends View> T getView(int id){
            View v = mViews.get(id);
            if(null == v){
                v= itemView.findViewById(id);
                mViews.put(id,v);
            }
            return (T)v;
        }



    }

    public void reflashData(List<T> data){
        this.data = data;
        notifyDataSetChanged();
    }

    public void setHeadView(View view){
        this.headerView = view;
    }

    public void setFooterView(View view){
        this.footerView = view;
    }

    public void setData(List<T> data){
        this.data = data;
    }

    public List<T> getListData(){
        return this.data;
    }

    public interface OnItemClickListener<T>{
         void OnItemClick(T data, int postion);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }
}
