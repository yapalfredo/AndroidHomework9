package com.example.androidhomework9;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Dynamically handle broadcast receiver
  //  BroadcastReceiver mBroadcastReceiver;

    private static final String CHANNEL_ID = "anti_theft";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialized to your own custom Broadcast receiver class
      //  mBroadcastReceiver = new MyBroadcastReceiver();

    }




}
