package com.amindset.ve.amindset.Splash;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.amindset.ve.amindset.R;
import com.amindset.ve.amindset.TextChat.MainActivity;
import com.amindset.ve.amindset.Utils.CommonUtils;
import com.amindset.ve.amindset.VdoCall.VideoActivity;
import com.amindset.ve.amindset.VoiceCAll.VoiceCallActivity;
import com.amindset.ve.amindset.data.AppPreferencesHelper;

public class GcmBroadcastReceiver extends BroadcastReceiver {

   AppPreferencesHelper appPreferencesHelper;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private static final String NOTIFICATION_CHANNEL_ID = "10001";


    @Override
    public void onReceive(Context context, Intent intent) {

        appPreferencesHelper=new AppPreferencesHelper(context);
        try {
            Log.e("Test", intent.getExtras().getString("type"));
            if (true && intent.getExtras().getString("type") != null
                    && intent.getExtras().getString("type").equalsIgnoreCase("chat")) {

                if(!MainActivity.active) {
                    Intent intentMain = new Intent(context.getApplicationContext(), MainActivity.class);
                    intentMain.putExtra("providerNumber", intent.getExtras().getString("T_mobile"));
                    intentMain.putExtra("providerName", intent.getExtras().getString("userName"));
                    intentMain.putExtra("providerImage", intent.getExtras().getString("my_profile_pic_url"));
                    intentMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//
                    createNotification(intentMain, context);
                }


            } else  if (true && intent.getExtras().getString("type") != null
                    && intent.getExtras().getString("type").equalsIgnoreCase("video")){
                Intent i = new Intent(context.getApplicationContext(), VideoActivity.class);
                i.putExtra("roomName", intent.getExtras().getString("roomName"));
                i.putExtra("userName", intent.getExtras().getString("userName"));
                i.putExtra("userPicUrl", intent.getExtras().getString("my_profile_pic_url"));
                i.putExtra("fcmAccessToken", intent.getExtras().getString("fcmToken"));
                i.putExtra("type", intent.getExtras().getString("type"));
                i.putExtra("pat_id",  intent.getExtras().getString("Patient_id"));
                i.putExtra("proficiency_type",  intent.getExtras().getString("proficiency_type"));

                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );

                context.startActivity(i);
            }
            else
            {

                    Intent intentV = new Intent(context.getApplicationContext(), VoiceCallActivity.class);
                    intentV.putExtra("roomName",intent.getExtras().getString("roomName"));
                    intentV.putExtra("userName", intent.getExtras().getString("userName"));
                    intentV.putExtra("userPicUrl", intent.getExtras().getString("my_profile_pic_url"));
                    intentV.putExtra("type", intent.getExtras().getString("type"));
                    intentV.putExtra("fcmAccessToken", intent.getExtras().getString("fcmToken"));
                    intentV.putExtra("pat_id",  intent.getExtras().getString("Patient_id"));
                    intentV.putExtra("proficiency_type",  intent.getExtras().getString("proficiency_type"));
                    intentV.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intentV.putExtra("callerId", appPreferencesHelper.getProviderT_mobile());
                    context.startActivity(intentV);




            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void createNotification(Intent resultIntent, Context context) {
        /**Creates an explicit intent for an Activity in your app**/


        Bitmap rawBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.mipmap.ic_launcher);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0 , resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Bitmap bmp = null;

        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.mipmap.appicon)
                .setLargeIcon(rawBitmap);

        mBuilder.setContentTitle("Amindset Message")
                .setContentText("A new message from user").setContentIntent(resultPendingIntent).setSound(Settings.System.DEFAULT_NOTIFICATION_URI).setAutoCancel(true);
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("Body")
//                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                        .setContentIntent(resultPendingIntent)
//                        .setAutoCancel(true));

        if (bmp != null) {
            mBuilder.setLargeIcon(bmp);
            mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bmp));
        }
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);



        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 100, 100, 100});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(0, mBuilder.build());
    }

}