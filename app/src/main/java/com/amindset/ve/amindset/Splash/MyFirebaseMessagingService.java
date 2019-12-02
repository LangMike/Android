package com.amindset.ve.amindset.Splash;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String TAG = "MsgFirebaseServ";
    private static final String NOTIFICATION_CHANNEL_ID = "10001";
    AppPreferencesHelper appPreferencesHelper;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        appPreferencesHelper=new AppPreferencesHelper(getApplicationContext());
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        JSONObject json = new JSONObject(remoteMessage.getData());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            try {
                if (true && json.getString("type") != null
                        && json.getString("type").equalsIgnoreCase("chat")) {

                    if(!MainActivity.active) {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("providerNumber", json.getString("T_mobile"));
                        intent.putExtra("providerName", json.getString("userName"));
                        intent.putExtra("providerImage", json.getString("my_profile_pic_url"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        Log.e("Chat", "Chat notification");
                        createNotification(intent, getApplicationContext());
                    }


                } else if(json.getString("type") != null
                        && json.getString("type").equalsIgnoreCase("video")){

                    Intent intent = new Intent(this, VideoActivity.class);
                    intent.putExtra("roomName", json.optString("roomName"));
                    intent.putExtra("userName", json.optString("userName"));
                    intent.putExtra("userPicUrl", json.optString("my_profile_pic_url"));
                    intent.putExtra("type", json.optString("type"));
                    intent.putExtra("fcmAccessToken", json.optString("fcmToken"));
                    intent.putExtra("pat_id",  json.optString("Patient_id"));
                    intent.putExtra("proficiency_type",  json.optString("proficiency_type"));
                    Log.e("proficiency_typeSer", ""+intent.getStringExtra("proficiency_type"));

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }

                else
                {


//                    Intent intent = new Intent(this, VoiceCallActivity.class);
//                    intent.putExtra("roomName", json.optString("roomName"));
//                    intent.putExtra("userName", json.optString("userName"));
//                    intent.putExtra("userPicUrl", json.optString("my_profile_pic_url"));
//                    intent.putExtra("type", json.optString("type"));
//                    intent.putExtra("fcmAccessToken", json.optString("fcmToken"));
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("callerId", appPreferencesHelper.getProviderT_mobile());
//                    startActivity(intent);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getData().size());
            if (remoteMessage.getData().size() > 0) {
                try {
                    if (true && json.getString("type") != null
                            && json.getString("type").equalsIgnoreCase("chat")) {


                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("providerNumber", json.getString("T_mobile"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
                        startActivity(intent);
                        Log.e("Chat", "Chat notification");

                        createNotification(intent, getApplicationContext());


                    } else if (json.getString("type") != null
                            && json.getString("type").equalsIgnoreCase("video")) {
                        Intent intent = new Intent(this, VideoActivity.class);
                        intent.putExtra("roomName", json.optString("roomName"));
                        intent.putExtra("userName", json.optString("userName"));
                        intent.putExtra("userPicUrl", json.optString("my_profile_pic_url"));
                        intent.putExtra("type", json.optString("type"));
                        intent.putExtra("fcmAccessToken", json.optString("fcmToken"));
                        intent.putExtra("pat_id", json.optString("Patient_id"));

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else {
//                        if (CommonUtils.isAppRunning(this, "com.amindset.ve.amindset")) {
//                            Intent intent = new Intent(this, VoiceCallActivity.class);
//                            intent.putExtra("roomName", json.optString("roomName"));
//                            intent.putExtra("userName", json.optString("userName"));
//                            intent.putExtra("userPicUrl", json.optString("my_profile_pic_url"));
//                            intent.putExtra("type", json.optString("type"));
//                            intent.putExtra("fcmAccessToken", json.optString("fcmToken"));
//                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            intent.putExtra("callerId", appPreferencesHelper.getProviderT_mobile());
//                            startActivity(intent);
//                        } else {
//                            // App is not running
//                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);


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