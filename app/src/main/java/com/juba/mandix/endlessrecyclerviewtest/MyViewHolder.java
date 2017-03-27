package com.juba.mandix.endlessrecyclerviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Mandix on 27/03/2017.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

    private TextView title,price;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolder(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView

        title = (TextView) itemView.findViewById(R.id.name);
        price = (TextView) itemView.findViewById(R.id.salePrice);
        imageView = (ImageView) itemView.findViewById(R.id.image);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un MyObject
    public void bind(Products myObject){
        title.setText(myObject.name);
        price.setText(myObject.salePrice);
        Picasso.with(imageView.getContext()).load(myObject.url).centerCrop().fit().into(imageView);
    }
}
