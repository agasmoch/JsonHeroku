package com.dev.agasmfauzan.jsonheroku;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class AdapterHeroku {
    private static final String TAG = AdapterHeroku.class.getSimpleName();
    public  AdapterHeroku(){}

    public String Call(String requestUrl){
        String response=null;
        try{
            URL url = new URL( requestUrl );
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod( "GET" );
            InputStream inputStream= new BufferedInputStream( connection.getInputStream() );
            response=convertStreamToString(inputStream);
        }catch (MalformedURLException e) {
            Log.e( TAG,"MalformedURLException: "+e.getMessage() );
        } catch (ProtocolException e) {
            Log.e( TAG,"ProtocolException: "+e.getMessage() );
        } catch (IOException e) {
            Log.e( TAG,"IOException: "+e.getMessage() );
        }catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
    }return response;



}

    private String convertStreamToString(InputStream is) {
        {
            BufferedReader bufferedReader=new BufferedReader( new InputStreamReader( is ) );
            StringBuilder stringBuilder=new StringBuilder(  );
            String line;
            try {
                while ((line= bufferedReader.readLine())!=null){
                    stringBuilder.append( line ).append( '\n' );
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }return stringBuilder.toString();
        }
    }
}



