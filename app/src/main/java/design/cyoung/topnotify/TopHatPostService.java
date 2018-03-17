package design.cyoung.topnotify;

import static design.cyoung.topnotify.APIQuestionCheck.*;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class TopHatPostService extends Service {
    public TopHatPostService() {
    }

    private int counter = 0;
    static String JSONdata = "";

    public TopHatPostService(Context applicationContext) {
        super();
        Log.i("START", "Monitor service started");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent broadcastIntent = new Intent("design.cyoung.topnotify.ActivityRecognition.RestartTopHatPost");
        sendBroadcast(broadcastIntent);
        Log.i("EXIT", "Attempting restart with broadcast intent.");
    }


    private Timer timer;
    private TimerTask timerTask;

    public void startTimer() {
        // set a new Timer
        timer = new Timer();

        // initialize the TimerTask's job
        initializeTimerTask();

        // schedule the timer, to wake up every 5 seconds
        timer.schedule(timerTask, 10000, 10000); //
    }

    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
            Log.i("EVAL", "Checking API for student_viewable_module_item");
            //String questionID = "102429";
            //isQuestion("https://app.tophat.com/api/v3/course/" + questionID + "/student_viewable_module_item/");
            SharedPreferences sPrefs = getSharedPreferences("TopHatInfo", MODE_PRIVATE);

            Log.i("KEY", sPrefs.getInt("ClassIndex", -1) + "");

            if (sPrefs.getInt("ClassIndex", -1) != -1) {
                for (int i = 0; i < sPrefs.getInt("ClassIndex", -1); i++) {
                    sPrefs.getString("Course" + i, null);
                    Log.i("KEY", sPrefs.getString("Course" + i, null) + "");

                }
            }
            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
