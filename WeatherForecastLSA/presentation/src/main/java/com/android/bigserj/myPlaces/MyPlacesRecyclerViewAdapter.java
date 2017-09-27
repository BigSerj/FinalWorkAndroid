package com.android.bigserj.myPlaces;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.bigserj.R;

import java.util.ArrayList;

import static com.android.bigserj.Constants.ADD_NEW_PLACE;

class MyPlacesRecyclerViewAdapter extends
        RecyclerView.Adapter<MyPlacesRecyclerViewAdapter.Holder> {

    private ArrayList<String> items;
    private OnItemClickListener listener;
    private OnItemClickDeleteListener deleteListener;

    MyPlacesRecyclerViewAdapter(ArrayList<String> items) {
        this.items = items;
    }


    void swap(ArrayList<String> datas) {

        if (items != null && items.size()>=0)
            items.clear();
        if (datas.size() != 0)
            items.addAll(datas);
        notifyDataSetChanged();
    }


    // создаем Holder
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycle_view_my_places,parent,false);
        Log.i("AAA","onCreateViewHolder()");
        return new Holder(root);
    }


    // заполняем Holder
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Log.i("AAA","onBindViewHolder() position = "+position);

        final String item = items.get(position);
        final int itemCount = position;

        holder.textView.setText(item);

        holder.itemView.findViewById(R.id.deleteFromItem)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteListener!=null)
                    deleteListener.onItemClickDelete(itemCount);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener!=null)
                    listener.onItemClick(itemCount);
            }
        });

    }

    // считает сколько раз нужно вызвать onBindViewHolder()
    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }


    public static class Holder extends RecyclerView.ViewHolder {
        TextView textView;

        public Holder(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.textCityName);
        }
    }

    interface OnItemClickListener{
        void onItemClick(Integer item);
    }
    interface OnItemClickDeleteListener{
        void onItemClickDelete(Integer item);
    }


    public void setDeleteListener(OnItemClickDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }
    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
