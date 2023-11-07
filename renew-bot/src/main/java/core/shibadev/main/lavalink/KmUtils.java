package core.shibadev.main.lavalink;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KmUtils {

    public static JsonObject searchLavaLink(String URL, String Pass) {
        StringBuilder Final = new StringBuilder();
        try {
            java.net.URL URI = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) URI.openConnection();
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Authorization", Pass);
            conn.addRequestProperty("Accept", "application/json");
            switch (conn.getResponseCode()) {
                case 200 -> {
                    BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                    String output;
                    int i = 0;
                    while ((output = br.readLine()) != null) {
                        if (i != 0) Final.append("\n");
                        Final.append(output);
                        i++;
                    }
                }
                case 401 -> Final.append("KmGet: No password provided!");
                case 404 -> Final.append("KmGet: Not Found!");
                default -> Final.append("KmGet: Bad Request!");
            }
            conn.disconnect();
        } catch (IOException ignored) {
            Final.append("KmGet: Bad Request!");
        }
        return (JsonObject) (new JsonParser()).parse(Final.toString());
    }

    public static boolean IsURL(String s) {
        String regex = "https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)";
//        String regex = "\\b(https?|ftp|file)://[-A-Z0-9+&@#/%?=~_|!:,.;]*[-A-Z0-9+&@#/%=~_|]";
        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public static boolean IsRegex(String s, String regex) {
        try {
            Pattern patt = Pattern.compile(regex);
            Matcher matcher = patt.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    public static JsonObject StringJson(String s) {

        return (JsonObject) (new JsonParser()).parse(s);
    }

//    public String renewToken() {
//
//    }

}
