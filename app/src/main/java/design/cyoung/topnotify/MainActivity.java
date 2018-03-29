package design.cyoung.topnotify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    Context ctx;

    public Context getCtx() {
        return ctx;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        TopHatPostService mTopHatPostService;
        SharedPreferences sp = getSharedPreferences("TopHatInfo", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        setContentView(R.layout.activity_main);

        WebView wv = findViewById(R.id.webview);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.clearHistory();
        wv.addJavascriptInterface(new TophatWebInterface(this), "Android");
        wv.setWebViewClient(new TopHatWebView());
        wv.loadUrl("https://app.tophat.com/login/?next=/emobile");

        //Tests
        editor.putInt("Token0", 99479);
        editor.putInt("Token1", 106024);
        editor.putInt("Token2", 105663);
        editor.putInt("Token3", 102429);
        editor.apply();

        //TODO Change to Fragment
        //TODO Start Service after Tokens are captured
        //TODO Change Fragment to blank screen after setup is completed

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}