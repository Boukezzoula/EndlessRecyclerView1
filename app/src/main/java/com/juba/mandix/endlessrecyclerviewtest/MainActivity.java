package com.juba.mandix.endlessrecyclerviewtest;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    List<Products> list = new ArrayList<Products>();
    LinearLayoutManager linearLayoutManager;
    MyAdapter adapter;
    int x=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       // final Parser parser = new Parser(x, (ArrayList<Products>) list);
       //  list = parser.requestdata();
        load_data_from_server(x);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
          linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(list);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() == list.size()-1){
                    x++;
                    Toast.makeText(getApplicationContext(),"last"+String.valueOf(x),Toast.LENGTH_LONG).show();
                  // list = parser.requestdata();
                    load_data_from_server(x);
                 //   adapter = new MyAdapter(list);
                   // adapter.notifyDataSetChanged();
                   // load_data_from_server(data_list.get(data_list.size()-1).getId());
                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private void load_data_from_server(int id) {

        AsyncTask<Integer,Void,Void> task = new AsyncTask<Integer, Void, Void>() {
             JSONArray myproducts;
            public  String readAll(Reader rd) throws IOException {
                StringBuilder sb = new StringBuilder();
                int cp;
                while ((cp = rd.read()) != -1) {
                    sb.append((char) cp);
                }
                return sb.toString();
            }

            public  JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
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

            @Override
            protected Void doInBackground(Integer... integers) {


                      //  .url("http://192.168.178.26/test/script.php?id="+integers[0])
                try {
                    JSONObject json;

                    json = readJsonFromUrl("https://api.bestbuy.com/v1/products?format=json&show=mediumImage,name,salePrice&pageSize=25&page="+integers[0]+"&apiKey=tghcgc6qnf72tat8a5kbja9r");




                    myproducts = json.getJSONArray("products");
                   // list.clear();
                    for(int i = 0; i< myproducts.length(); i++){
                        JSONObject prod = myproducts.getJSONObject(i);
                        list.add(new Products(
                                prod.getString("salePrice"),
                                prod.getString("name"),
                                prod.getString("mediumImage")
                        ));
                    }}catch (IOException | JSONException e) {
                    System.out.println("something went wrong");
                    System.out.println(e.getMessage());

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        };

        task.execute(id);
    }
}
