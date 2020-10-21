package com.example.musicplayer_practice;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MusicService extends Service {
    public MusicService() {
    }
    private static final String LOG_TAG = "ForegroundService";
    MediaPlayer musicplayer;
    int time;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        musicplayer = MediaPlayer.create(this,R.raw.yen);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
       // Log.d("action",intent.getAction()+" ");
       if (intent.getAction().equals("com.example.foregroundservice.action.startforeground")) {

            String channel_Id = "my_channel_01";// The id of the channel.
            CharSequence channelName = "NotifChannel";// The user-visible name of the channel.
            int channelImportance = NotificationManager.IMPORTANCE_HIGH;
            NotificationManager manager = null;
            NotificationChannel channel = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                manager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Create a notification and set the notification channel.
                channel = new NotificationChannel(channel_Id, channelName,
                        channelImportance);
                channel.setDescription("Channel description");
                manager.createNotificationChannel(channel);

            }
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction("com.example.foregroundservice.action.main");
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,
                    0, notificationIntent, 0);

           Intent previousIntent = new Intent(this, MusicService.class);
           previousIntent.setAction("com.example.foregroundservice.action.prev");
           PendingIntent ppreviousIntent = PendingIntent.getService(this, 0,
                   previousIntent, 0);

           Intent playIntent = new Intent(this, MusicService.class);
           playIntent.setAction("com.example.foregroundservice.action.play");
           PendingIntent pplayIntent = PendingIntent.getService(this, 0,
                   playIntent, 0);

           Intent nextIntent = new Intent(this, MusicService.class);
           nextIntent.setAction("com.example.foregroundservice.action.next");
           PendingIntent pnextIntent = PendingIntent.getService(this, 0,
                   nextIntent, 0);

           Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_music_1);
           Notification notification = new NotificationCompat.Builder(this, channel_Id)
                    .setContentTitle(" Music Player")
                    .setTicker(" Music Player")
                    .setContentText("My Music")
                    // Show controls on lock screen even when user hides sensitive content.
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                   .setSmallIcon(R.drawable.ic_music)
                   .setLargeIcon(
                           Bitmap.createScaledBitmap(icon, 128, 128, false))

                   .setContentIntent(pendingIntent)
                    .addAction(android.R.drawable.ic_media_previous,
                   "Previous", ppreviousIntent)
                   .addAction(android.R.drawable.ic_media_play, "Play",
                           pplayIntent)
                   .addAction(android.R.drawable.ic_media_next, "Next",
                           pnextIntent)

                   .build();
            startForeground(101, notification);
        }else if (intent.getAction().equals("com.example.foregroundservice.action.prev")) {
           Log.i(LOG_TAG, "Clicked Previous");
       } else if (intent.getAction().equals("com.example.foregroundservice.action.play")) {
           if(musicplayer.isPlaying()){
               time = musicplayer.getCurrentPosition();
               musicplayer.stop();
               //musicplayer = MediaPlayer.create(this,R.raw.yen);
               //Log.i(LOG_TAG, "Clicked Play");
           }
           else{
               musicplayer = MediaPlayer.create(this,R.raw.yen);
               musicplayer.seekTo(time);
               musicplayer.start();

           }
           Log.i(LOG_TAG, "Clicked Play");
       } else if (intent.getAction().equals("com.example.foregroundservice.action.next")) {
           Log.i(LOG_TAG, "Clicked Next");
       } else if (intent.getAction().equals("com.example.foregroundservice.action.stopforeground")) {
           Log.i(LOG_TAG, "Received Stop Foreground Intent");
           stopForeground(true);
           stopSelf();
       }


        musicplayer.start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        musicplayer.stop();
    }
}