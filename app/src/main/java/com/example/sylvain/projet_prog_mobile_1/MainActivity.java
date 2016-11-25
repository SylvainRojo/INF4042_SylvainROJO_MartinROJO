package com.example.sylvain.projet_prog_mobile_1;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void fct_1_test(View button) {
        Toast.makeText(getApplicationContext(),getString(R.string.msg),Toast.LENGTH_LONG).show();

    }

    public void notif_1_test(View v) {
        NotificationCompat.Builder Notification_1 = (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                .setContentTitle("Notifcation de Test")
                .setContentText("C'est juste pour tester que ça marche !")
                .setSmallIcon(R.mipmap.ic_launcher);

        int notification_id = 001;
        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(notification_id, Notification_1.build());

    }

    public void actCall(View v){
        Intent intent_1 = new Intent(this,SecondeActivity.class);
        startActivity(intent_1);
    }
}
