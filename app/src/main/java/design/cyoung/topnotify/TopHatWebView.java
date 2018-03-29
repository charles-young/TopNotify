package design.cyoung.topnotify;

import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

class TopHatWebView extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Uri.parse(url).getHost().equals("app.tophat.com")) {
            // This is my web site, so do not override; let my WebView load the page
            return false;
        }

        // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
        view.loadUrl("https://app.tophat.com/emobile/");
        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        System.out.println(Uri.parse(url).getQuery());
        StringBuilder jscontent = new StringBuilder();
        try {
            InputStream is = view.getContext().getAssets().open("scrape.js");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;
            while ((line = br.readLine()) != null) {
                jscontent.append(line);
            }
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        view.loadUrl("javascript:(" + jscontent.toString() + ")()");
    }
}