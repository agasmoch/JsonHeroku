package com.dev.agasmfauzan.jsonheroku;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ListView listview;
    ArrayList<HashMap<String,String>>contactApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        contactApps=new ArrayList<>(  );
        listview=(ListView)findViewById( R.id.list );
        new GetContactApps().execute();
    }
    private class GetContactApps extends AsyncTask<Void,Void,Void>{

        protected void onPreExetuce(){
            super.onPreExecute();
            Toast.makeText( MainActivity.this,"Data sedang dalam proses download",Toast.LENGTH_LONG ).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            AdapterHeroku sh=new AdapterHeroku();
            String url = "https://simple-contact-crud.herokuapp.com/contact";
            String jsonStr=sh.Call(url );

            Log.e(TAG,"Response from url: "+jsonStr);
            if (jsonStr !=null){
                try{
                    JSONObject jsonObject = new JSONObject( jsonStr );
                    JSONArray data = jsonObject.getJSONArray( "data" );

                    for (int i=0 ; i<data.length();i++){
                        JSONObject d=data.getJSONObject( i );
                        String id = d.getString( "id" );
                        String firstName = d.getString( "firstName" );
                        String lastName = d.getString( "lastName" );
                        String age = d.getString( "age" );
                        String photo=d.getString( "photo" );

                        HashMap<String, String> datas = new HashMap<>();
                        datas.put("id", id);
                        datas.put("firstName", firstName);
                        datas.put("lastName", lastName);
                        datas.put("age", age);
                        datas.put( "photo",photo );

                        contactApps.add(datas);



                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Parse JSON Error: "+e.getMessage());
                    runOnUiThread( new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText( getApplicationContext() ,
                                    "Parse JSON Error: "+e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    } );
                }
            } else {
                Log.e(TAG, "Tidak dapat menerima data JSON");
                runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText( getApplicationContext(),
                                "Tidak dapat menerima data JSON, silahkan cek Logcat",Toast.LENGTH_LONG ).show();
                    }
                } );
            }return null;

        }
            protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter( MainActivity.this,contactApps,
                    R.layout.list_item,new String[] {"firstName","lastName","age","photo"},new int[]{R.id.age,R.id.firstName,R.id.lastName,R.id.photo});
            listview.setAdapter( adapter);
             }


    }
}
