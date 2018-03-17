package design.cyoung.topnotify;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    Intent mServiceIntent;
    Context ctx;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        TopHatPostService mTopHatPostService;
        setContentView(R.layout.activity_main);
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        */

        WebView wv = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new TophatWebInterface(this), "Android");
        wv.setWebViewClient(new TopHatWebView());
        //wv.loadUrl("https://app.tophat.com/logout/?next=/emobile");
        wv.loadUrl("https://app.tophat.com/login/?next=/emobile");

        mTopHatPostService = new TopHatPostService(getCtx());
        mServiceIntent = new Intent(getCtx(), mTopHatPostService.getClass());
        if (!isServiceRunning(mTopHatPostService.getClass())) {
            startService(mServiceIntent);
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isServiceRunning?", false + "");
        return false;
    }


    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("DEAD", "Service Stopped.");
        super.onDestroy();

    }
}