package design.cyoung.topnotify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

final class APIQuestionCheck {
    private APIQuestionCheck() { }

    static boolean isQuestion(String URI) {
        StringBuilder data = new StringBuilder();
        try {
            URL url = new URL(URI);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while (line != null) {
                line = bufferedReader.readLine();
                data.append(line);
            }
            if ((data.toString().equals("{\"total_results\":0,\"meta\":{\"previous\":null,\"next\":null},\"objects\":[]}" + null) && data.toString().contains("{\"total_results\":"))) {
                //Log.i("EVAL", "No Question");
                return false;
            } else if (data.toString().contains("module_id\":\"question")) {
                //Log.i("EVAL", "New Question");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
