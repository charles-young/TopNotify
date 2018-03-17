package design.cyoung.topnotify;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

final class APIQuestionCheck {
    private APIQuestionCheck() { }

    static boolean isQuestion(String URI) {
        String uri = URI;
        String data = "";
        try {
            URL url = new URL(uri);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data += line;
            }
            if (data.equals("{\"total_results\":0,\"meta\":{\"previous\":null,\"next\":null},\"objects\":[]}" + null)) {
                Log.i("EVAL", "New Question");
                return false;
            } else {
                Log.i("EVAL", "No Question");
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
