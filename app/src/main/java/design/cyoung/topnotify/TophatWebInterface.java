package design.cyoung.topnotify;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.webkit.JavascriptInterface;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by young on 3/16/18.
 */

public class TophatWebInterface {
    private Context mContext;
    private Intent mServiceIntent;

    /**
     * Instantiate the interface and set the context
     */
    TophatWebInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void storeClassID(String key, String ID) {
        SharedPreferences sPrefs = mContext.getSharedPreferences("TopHatInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString(key, ID);
        editor.apply();
    }

    /**
     * Show a toast from the web page
     */
    @JavascriptInterface
    public void storeClassIndex(int num) {
        SharedPreferences sPrefs = mContext.getSharedPreferences("TopHatInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putInt("ClassIndex", num);
        editor.apply();
    }

    @JavascriptInterface
    public void storeClassToken(String key, String ID) {
        SharedPreferences sPrefs = mContext.getSharedPreferences("TopHatInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString(key, ID);
        editor.apply();
    }

    @JavascriptInterface
    public void startQuestionService() {
        TopHatPostService mTopHatPostService;
        SharedPreferences sPrefs = mContext.getSharedPreferences("TopHatInfo", MODE_PRIVATE);

        if (sPrefs.getInt("ClassIndex", -1) > 0) {
            mTopHatPostService = new TopHatPostService(mContext, sPrefs.getInt("ClassIndex", -1));
            this.mServiceIntent = new Intent(mContext, mTopHatPostService.getClass());
            if (!isServiceRunning(mTopHatPostService.getClass())) {
                mContext.startService(mServiceIntent);
                Log.i("INTENT", "Sending Intent from JS Interface.");
            }
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        assert manager != null;
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isServiceRunning?", false + "");
        return false;
    }
}
