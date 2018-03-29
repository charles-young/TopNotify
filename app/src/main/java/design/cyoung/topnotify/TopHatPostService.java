package design.cyoung.topnotify;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import static design.cyoung.topnotify.APIQuestionCheck.isQuestion;

public class TopHatPostService extends Service {
    static String JSONdata = "";
    Context mContext;
    private Timer timer;
    private TimerTask timerTask;
    private int classes;
    private Boolean builtNotification = false;
    private Boolean hasAnyQuestion = false;

    public TopHatPostService() {
    }

    public TopHatPostService(Context c, int classIndex) {
        super();
        classes = classIndex;
        mContext = c;
        Log.i("START", "Monitor service started");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent broadcastIntent = new Intent("design.cyoung.topnotify.ActivityRecognition.RestartTopHatPost");
        sendBroadcast(broadcastIntent);
        Log.i("EXIT", "Attempting restart with broadcast intent.");
    }

    public void startTimer() {
        timer = new Timer();
        initializeTimerTask();

        // schedule the timer, to wake up every 2.5 seconds
        timer.schedule(timerTask, 4000, 4000);
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("EVAL", "Checking API for student_viewable_module_item");
                SharedPreferences sp = getSharedPreferences("TopHatInfo", MODE_PRIVATE);

                Log.i("KEY", sp.getInt("ClassIndex", -1) + "");
                classes = sp.getInt("ClassIndex", -1);
                hasAnyQuestion = false;
                if (classes > 0) {
                    for (int i = 0; i < classes; i++) {
                        final String token = "Token" + i;
                        if (isQuestion("https://app.tophat.com/api/v3/course/" + sp.getInt(token, 0) + "/student_viewable_module_item/")) {
                            hasAnyQuestion = true;
                            Log.i("URI", "Token: " + sp.getInt("Token" + i, 0) + ": hasQuestion()");
                        }
                    }
                    if (hasAnyQuestion && !builtNotification) {
                        buildNotification();
                        builtNotification = true;
                        hasAnyQuestion = false;
                    }
                    if (!hasAnyQuestion && builtNotification) {
                        clearNotification();
                    }
                }
            }
        };
    }

    public void clearNotification() {
        NotificationManager notificationManager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.cancel(1);
        }
    }

    private void buildNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel("Question Push Notification", name, importance);
            mChannel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager nm = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            assert nm != null;
            nm.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "Question Push Notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("TopNotify")
                .setContentText("New question posted.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(false);
        notificationManager.notify(1, mBuilder.build());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
