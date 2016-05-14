package com.example.ajay.pocketmoneymanager;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class spend extends AppCompatActivity {
    EditText Spendon,Amount;
    String spendon,amount;
    Button update;
    Context ctx =this;
    public static int k;
    String c="Your wallet cash is below 100 rupees. Get cash from the ATM immediately";

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        spend w=new spend();
        setContentView(R.layout.activity_spend);
        getSupportActionBar().hide();
        Spendon=(EditText)findViewById(R.id.spendon);
        Amount=(EditText)findViewById(R.id.amount);
        update=(Button)findViewById(R.id.button);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spendon = Spendon.getText().toString();
                amount = Amount.getText().toString();
                if (Integer.parseInt(amount) < Addcash.total_cash) {
                    DataOperations DB = new DataOperations(ctx);
                    Addcash.total_cash = Addcash.total_cash - Integer.parseInt(amount);
                    k = Addcash.total_cash;
                    long ret = DB.putInformation(DB, spendon, amount);
                    if (ret != -1) {
                        Toast.makeText(getBaseContext(), "Record Updated", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(getBaseContext(), "Records couldn't be updated", Toast.LENGTH_LONG).show();
                        finish();
                    }
                } else {
                    Toast.makeText(getBaseContext(), "You don't have sufficient balance", Toast.LENGTH_LONG).show();
                }
                if (k < 100) {
                    sendBalanceNotification();
                }
            }

        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void sendBalanceNotification(){
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setAutoCancel(true);
        builder.setContentTitle("!!Balance Low");
        builder.setContentText(c);
        builder.setDefaults(Notification.DEFAULT_SOUND);
      //  builder.setWhen(System.currentTimeMillis());
      builder.setSmallIcon(R.drawable.ic);

        Notification notification=builder.build();
       // Intent inte=new Intent(spend.this,MainActivity.class);
       // PendingIntent penin=PendingIntent.getActivity(spend.this,0,inte,PendingIntent.FLAG_UPDATE_CURRENT);
        //builder.setContentIntent(penin);
        NotificationManager manager=(NotificationManager)this.getSystemService(NOTIFICATION_SERVICE);
        manager.notify(8,notification);

    }
}
