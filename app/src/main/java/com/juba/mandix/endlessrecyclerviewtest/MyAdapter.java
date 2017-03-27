package com.juba.mandix.endlessrecyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Mandix on 27/03/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
    List<Products> list;

    public  MyAdapter(List<Products> list){
        this.list=list;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Products myObject = list.get(position);
        holder.bind(myObject);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
