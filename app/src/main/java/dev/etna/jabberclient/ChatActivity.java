package dev.etna.jabberclient;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        bSend = (Button) findViewById(R.id.button);
        bSend.setOnClickListener(this);
    }

    private void sendMessage(String message) {
        TextView tv;
        LinearLayout messageContener;
        LinearLayout.LayoutParams lparams;

        messageContener = (LinearLayout) findViewById(R.id.messageContener);
        lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.gravity = Gravity.RIGHT;

        tv = new TextView(this);
        tv.setLayoutParams(lparams);
        tv.setBackgroundColor(ContextCompat.getColor(this, R.color.myChat));
        tv.setPadding(0,10,10,0);
        tv.setText(message);
        messageContener.addView(tv);
        Log.e("INFO", "send message : " + message);
    }

    public void displayMessage(String message) {
        TextView tv;
        LinearLayout messageContener;
        LinearLayout.LayoutParams lparams;

        messageContener = (LinearLayout) findViewById(R.id.messageContener);
        lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lparams.gravity = Gravity.LEFT;

        tv = new TextView(this);
        tv.setLayoutParams(lparams);
        tv.setBackgroundColor(ContextCompat.getColor(this, R.color.isChat));
        tv.setPadding(10,10,0,0);
        tv.setText(message);
        messageContener.addView(tv);
        Log.e("INFO", "received message : " + message);
    }

    @Override
    public void onClick(View view) {
        TextView tv = (TextView) findViewById(R.id.editText);
        if (!tv.getText().toString().equals(""))
            sendMessage(tv.getText().toString());
        tv.setText(null);
    }
}
