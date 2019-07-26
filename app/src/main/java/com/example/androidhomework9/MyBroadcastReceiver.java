package com.example.androidhomework9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;
import java.net.URI;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       // Toast.makeText(context, "Broadcast Receiver Triggered", Toast.LENGTH_SHORT).show();

        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        String originatingSender = "";

        if (bundle != null){
            //-----------------------
            //Retrieve the sms received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];
            for (int i = 0; i < msgs.length; i++)
            {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                str += msgs[i].getDisplayMessageBody();
                originatingSender += msgs[i].getOriginatingAddress();
            }
            //-----------------------
            String pw = "1234"; //this serves as the password for requesting call logs
            if (str.startsWith(pw) && str.contains("SendCalllog")){
                SmsManager manager = SmsManager.getDefault();
                manager.sendTextMessage(originatingSender,null,
                getLast10CallHistory(context), null, null);
            }

            //  float max=100*.01f;
            Intent serviceIntent = new Intent(context, MusicService.class);
            if (str.startsWith(pw) && str.contains("StartRing")){
                context.startService(serviceIntent);
            }else if (str.startsWith(pw) && str.contains("StopRing")) {
                context.stopService(serviceIntent);
            }
        }
    }


    //--------------------------
    //Retrieving last 10 call logs
    private String getLast10CallHistory(Context context)
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Last 10 Calls made: \n");
        int counter = 0;    //will be used to count the number of rows traversed

        //Needs to be on a try catch with SecurityException for permission checking
        try
        {   //use Cursor object to query the call logs
            Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null,null, CallLog.Calls.DATE +" DESC");
            int phoneNumber = cursor.getColumnIndex(CallLog.Calls.NUMBER);  //this variable will hold the column index for Phone Number
            int callType = cursor.getColumnIndex(CallLog.Calls.TYPE);   //this variable will hold the column index for call type

            while (cursor.moveToNext()) //Traverse through call logs
            {
                String _callType = cursor.getString(callType); //get the value of call type

                //checks for outgoing calls only and the last 10 most recent made calls
                if (Integer.parseInt(_callType) == CallLog.Calls.OUTGOING_TYPE && counter < 10)
                {
                    counter++;  //increment counter
                    String phNum = cursor.getString(phoneNumber);   //stores the phoneNumber column value
                    stringBuffer.append(phNum + "\n");  //appends the phone number to the string buffer variable
                }
                else
                {
                    break;
                }
            }
            cursor.close(); //closes cursor after traversing (ALWAYS IMPORTANT!)
        }
        catch (SecurityException e)
        {
            e.getStackTrace();
        }
        catch (Exception e)
        {
            e.getStackTrace();
        }
        return stringBuffer.toString(); //returns the stringbuffer that contains the phone numbers
    }
}
