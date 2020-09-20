package com.example.testcft;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.example.testcft.NetworkUtils.generateURL;

public class MainActivity extends AppCompatActivity {

    public static final String MY_URL = "https://www.cbr-xml-daily.ru/daily_json.js";
    private Context context = this;
    private static RecyclerView mRecyclerView;
    private static RecyclerViewAdapter myRVAdapter;
    private static ArrayList<Valute> listValutes = new ArrayList<>();
    private static String LIST_VALUTES = "list_valutes";
    private Parcelable savedRecyclerLayoutState;
    private static String BUNDLE_RECYCLER_LAYOUT = "recycler_layout";

    private BroadcastReceiver minuteUpdateReceiver;
    private int timeRefreshInMinutes = 2;
    private int timeCount = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            listValutes = savedInstanceState.getParcelableArrayList(LIST_VALUTES);
            savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            displayData();
        }else {
            initJSON();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.updateData:
                initJSON();
                return true;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    // обновление данных раз в минуту
    public void startMinuteUpdater() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        minuteUpdateReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                timeCount++;
                if (timeCount == timeRefreshInMinutes){
                    timeCount = 0;
                    initJSON();
                }
            }
        };
        registerReceiver(minuteUpdateReceiver, intentFilter);
    }

    public static void setListValute(ArrayList<Valute> lv) {
        listValutes = lv;
    }
    public static void initRV(Context context){
        mRecyclerView = ((Activity)context).findViewById(R.id.recyclerview_id);
        myRVAdapter = new RecyclerViewAdapter(context,listValutes);
        mRecyclerView.setLayoutManager(new GridLayoutManager(context,1));
        mRecyclerView.setAdapter(myRVAdapter);
    }

    // обработка поворот экрана
    private void displayData(){
        initRV(context);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (savedRecyclerLayoutState != null) {
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
        myRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList(LIST_VALUTES, listValutes);
        savedInstanceState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        listValutes = savedInstanceState.getParcelableArrayList(LIST_VALUTES);
        savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMinuteUpdater();
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(minuteUpdateReceiver);
    }

    // проверка интернета
    private boolean internet_connection(){
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private void initJSON(){
        if (internet_connection()){
            new QueryTask(context).execute(generateURL(MY_URL));
        }else if (!internet_connection()){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Отсутствует подключение к интернету");
            builder.create().show();
        }
    }
}