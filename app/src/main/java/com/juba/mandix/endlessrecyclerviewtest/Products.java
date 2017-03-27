package com.juba.mandix.endlessrecyclerviewtest;

/**
 * Created by Mandix on 27/03/2017.
 */

public class Products {
    public String url;
    public String name;
    public String salePrice;

    public Products(String sku,String name,String url){
        this.salePrice=sku;
        this.name=name;
        this.url=url;
    }
}
