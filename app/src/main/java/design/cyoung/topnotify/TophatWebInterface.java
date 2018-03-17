package design.cyoung.topnotify;

import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by young on 3/16/18.
 */

public class TophatWebInterface {
    Context mContext;

    /** Instantiate the interface and set the context */
    TophatWebInterface(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page */
    @JavascriptInterface
    public void storeClassIndex(int num) {
        SharedPreferences sPrefs = mContext.getSharedPreferences("TopHatInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putInt("ClassIndex", num);
        editor.apply();
    }
    @JavascriptInterface
    public void storeClassID(String key, String ID) {
        SharedPreferences sPrefs = mContext.getSharedPreferences("TopHatInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPrefs.edit();
        editor.putString(key, ID);
        editor.apply();
    }
}
