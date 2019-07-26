package com.example.androidhomework9;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

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
            //-----------------------

            //-----------------------
            //send call logs
            if (str.startsWith(pw) && str.contains("SendCalllog")){
                sendSMS(context, originatingSender, "calls");
            }

            //------------------------
            // RING / MUSIC Service
            //start ring and stop ring the phone
            Intent serviceIntent = new Intent(context, MusicService.class);
            if (str.startsWith(pw) && str.contains("StartRing")){
                context.startService(serviceIntent);
            }else if (str.startsWith(pw) && str.contains("StopRing")) {
                context.stopService(serviceIntent);
            }

            //--------------------------
            // Location Service
            // sends a text of the phones coordinates/location
            Intent locationServiceIntent = new Intent(context, LocationService.class);
            if (str.startsWith(pw) && str.contains("StartMonitor")){
                context.startService(locationServiceIntent);
                sendSMS(context,originatingSender,"coordinates");
            }else if (str.startsWith(pw) && str.contains("StopMonitor")){
                context.stopService(locationServiceIntent);
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

    private String getLocationCoordinates()
    {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Your phones location coordinates are: \n");
        stringBuffer.append("Latitude: " + MainActivity.latitude+ "\n");
        stringBuffer.append("Longitude: "+ MainActivity.longitude+ "\n");

        return stringBuffer.toString();
    }

    private void sendSMS(Context context, String sender, String type)
    {
        SmsManager manager = SmsManager.getDefault();
        switch (type)
        {
            case "calls":
                manager.sendTextMessage(sender,null,
                        getLast10CallHistory(context), null, null);
                break;
            case "coordinates":
                manager.sendTextMessage(sender, null,
                        getLocationCoordinates(),null,null);
                break;
        }
    }
}
