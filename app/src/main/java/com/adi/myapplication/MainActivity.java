package com.adi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<DataAdapter> ListOfDataAdapter;
    RecyclerView recyclerView;
    String URL_JSON = "http://192.168.25.153/db_youtube/json.php";

    String TAG_ID = "id";
    String TAG_JUDUL = "judul";
    String TAG_URL = "gambar_url";

    JsonArrayRequest RequestOFJSonArray;
    RequestQueue requestQueue;

    View view;
    int RecyclerViewItemPosition;
    RecyclerView.LayoutManager layoutManagerOfrecyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    ArrayList<String> ImageTitleidArrayListForClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageTitleidArrayListForClick = new ArrayList<>();
        ListOfDataAdapter = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview_layout);
        recyclerView.setHasFixedSize(true);
        layoutManagerOfrecyclerView = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManagerOfrecyclerView);

        JSON_HTTF();

    }
    public void JSON_HTTF() {
        RequestOFJSonArray = new JsonArrayRequest(URL_JSON,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ParseJSonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(RequestOFJSonArray);

    }

        public void ParseJSonResponse (JSONArray array){
            for (int i = 0; i<array.length(); i++){
               DataAdapter GetDataAdapter2 = new DataAdapter();
                JSONObject json=null;
                try {
                    json = array.getJSONObject(i);
                    ImageTitleidArrayListForClick.add(json.getString(TAG_ID));
                    GetDataAdapter2.setid(json.getString(TAG_ID));
                    GetDataAdapter2.setjudul(json.getString(TAG_JUDUL));
                    GetDataAdapter2.seturl(json.getString(TAG_URL));

                } catch (JSONException e){
                    e.printStackTrace();
                }
                ListOfDataAdapter.add(GetDataAdapter2);

            }
            recyclerViewAdapter = new RecyclerViewAdapter(ListOfDataAdapter,this);
            recyclerView.setAdapter(recyclerViewAdapter);
                }

    }

