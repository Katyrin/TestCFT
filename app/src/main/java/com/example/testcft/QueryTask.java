package com.example.testcft;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.testcft.NetworkUtils.getResponseFromURL;

public class QueryTask extends AsyncTask<URL,Void,String> {

    private Context context;

    public QueryTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(URL... urls) {
        String valute = null;
        try {
            valute = getResponseFromURL(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valute;
    }

    @Override
    protected void onPostExecute(String valutes) {
        String id;
        String numCode;
        String charCode;
        int nominal;
        String name;
        double value;
        double previous;
        ArrayList<Valute> listValutes = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(valutes);
            JSONObject jsonValutes = jsonObject.getJSONObject("Valute");

            for (int i = 0; i < jsonValutes.length(); i++) {
                JSONObject valuteInfo = jsonValutes.getJSONObject(jsonValutes.names().getString(i));
                id = valuteInfo.getString("ID");
                numCode = valuteInfo.getString("NumCode");
                charCode = valuteInfo.getString("CharCode");
                nominal = valuteInfo.getInt("Nominal");
                name = valuteInfo.getString("Name");
                value = valuteInfo.getDouble("Value");
                previous = valuteInfo.getDouble("Previous");
                listValutes.add(new Valute(id,numCode,charCode,nominal,name,value,previous));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // отправляю в MainActivity список валют
        MainActivity.setListValute(listValutes);
        // инициализирую RecyclerView
        MainActivity.initRV(context);
    }
}
