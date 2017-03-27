package com.juba.mandix.endlessrecyclerviewtest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Mandix on 27/03/2017.
 */

public class Parser implements Serializable{
    static JSONArray myproducts;
     static ArrayList<Products> mesproduits;
    int page ;



public  Parser(int  page ,ArrayList<Products> mesproduits ){
    this.page = page;
    this.mesproduits = mesproduits;
}



    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }
/*
    public static void readjsonobjects() throws JSONException{
        for (int i = 0; i< myproducts.length(); i++){
            mesproduits.add(myproducts.getJSONObject(i).get("img_src").toString());
            System.out.println("picture number :"+i + mesproduits.get(i));
        }}*/

    public  ArrayList<Products> requestdata()  {
        try {
            JSONObject json;

            json = readJsonFromUrl("https://api.bestbuy.com/v1/products?format=json&show=mediumImage,name,salePrice&pageSize=25&page="+(page)+"&apiKey=tghcgc6qnf72tat8a5kbja9r");




            myproducts = json.getJSONArray("products");
            mesproduits.clear();
            for(int i = 0; i< myproducts.length(); i++){
                JSONObject prod = myproducts.getJSONObject(i);
                mesproduits.add(new Products(
                        prod.getString("salePrice"),
                        prod.getString("name"),
                        prod.getString("mediumImage")
                ));
            }}catch (IOException | JSONException e) {
            System.out.println("something went wrong");
            System.out.println(e.getMessage());

        }
        return mesproduits;
    }

}
