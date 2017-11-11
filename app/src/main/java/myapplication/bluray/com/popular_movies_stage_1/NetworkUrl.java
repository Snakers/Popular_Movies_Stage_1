package myapplication.bluray.com.popular_movies_stage_1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


class NetworkUrl {


    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream is = urlConnection.getInputStream();
            Scanner scanner = new Scanner(is);
            scanner.useDelimiter("\\A");
            boolean input = scanner.hasNext();
            if (input) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {

            urlConnection.disconnect();
        }
    }
}

