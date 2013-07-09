package com.webshrub.festivity.holi.androidapp;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.telephony.SmsManager;

import java.util.ArrayList;

public class MessageItemSenderTask extends AsyncTask<Contact, Integer, Void> {
    public static final String ADDRESS = "address";
    public static final String DATE = "date";
    public static final String READ = "read";
    public static final String STATUS = "status";
    public static final String TYPE = "type";
    public static final String BODY = "body";

    private Context context;
    private String message;
    private ProgressDialog progressDialog;

    public MessageItemSenderTask(Context context, String message) {
        this.context = context;
        this.message = message;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sending message to selected contacts..");
        progressDialog.setTitle("Please wait");
        progressDialog.setIndeterminate(true);
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
    }

    @Override
    protected Void doInBackground(Contact... contacts) {
        try {
            for (int count = 0; count < contacts.length; count++) {
                Contact contact = contacts[count];
                sendSMS(contact.getNumber(), message);
                saveSentSms(contact.getNumber(), message);
//            sendSMS("9810572052", messageItem.getDetails());
//            saveSentSms("9810572052", messageItem.getDetails());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
                publishProgress(count + 1, contacts.length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        if (progress[0].equals(progress[1])) {
            progressDialog.setMessage("Message sent  to all " + progress[1] + " selected contacts..");
        } else {
            progressDialog.setMessage("Message sent  to " + progress[0] + " out of " + progress[1] + " selected contacts..");
        }
    }

    private void sendSMS(String number, String message) {
        ArrayList<String> messages = SmsManager.getDefault().divideMessage(message);
        SmsManager.getDefault().sendMultipartTextMessage(number, null, messages, null, null);
    }

    private void saveSentSms(String phoneNumber, String message) {
        ContentValues values = new ContentValues();
        values.put(ADDRESS, phoneNumber);
        values.put(DATE, System.currentTimeMillis());
        values.put(READ, 1);
        values.put(STATUS, -1);
        values.put(TYPE, 2);
        values.put(BODY, message);
        context.getContentResolver().insert(Uri.parse("content://sms"), values);
    }
}