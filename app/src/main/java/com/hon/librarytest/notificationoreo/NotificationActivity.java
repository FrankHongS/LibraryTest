package com.hon.librarytest.notificationoreo;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hon.librarytest.R;

/**
 * Created by Frank on 2018/6/20.
 * E-mail:frank_hon@foxmail.com
 */

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener{

    private Button mChatButton;
    private Button mSubscribeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mChatButton=findViewById(R.id.btn_chat);
        mSubscribeButton=findViewById(R.id.btn_subscribe);

        mChatButton.setOnClickListener(this);
        mSubscribeButton.setOnClickListener(this);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){

            createNotificationChannel("chat",
                    "chat",
                    NotificationManager.IMPORTANCE_HIGH);

            createNotificationChannel("subscribe",
                    "subscribe",
                    NotificationManager.IMPORTANCE_DEFAULT);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_chat:
                if(checkNotificationChannel("chat"))
                    buildNotification(
                            "chat",
                            "chat request",
                            "(: I want to chat with you"
                    );
                break;
            case R.id.btn_subscribe:
                if(checkNotificationChannel("subscribe"))
                    buildNotification(
                            "subscribe",
                            "received a message about subscribing",
                            "please subscribe me :)"
                    );
                break;
            default:
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId,
                                           String channelName,
                                           int importance){
        NotificationChannel channel=new NotificationChannel(channelId,
                channelName,importance);
        NotificationManager notificationManager=
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);

    }

    private void buildNotification(String channelId,String title,String content){
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(this,channelId)
                .setContentTitle(title)
                .setContentText(content)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_android_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.test05))
                .setAutoCancel(true)
                .build();
        manager.notify(2, notification);
    }

    private boolean checkNotificationChannel(String channelName){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = manager.getNotificationChannel(channelName);
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "please open notification manually", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }
}
