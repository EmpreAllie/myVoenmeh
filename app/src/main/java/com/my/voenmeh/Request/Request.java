package com.my.voenmeh.Request;

import java.io.IOException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public class Request {
    private static final String access_token = "f7652192f7652192f76521921bf472ef15ff765f7652192916d4a1bf1565a447203cb16";
    private static final String domain = "chipinkostv";
    private static final String offset = "0";
    private static final String count = "5";
    private static final String v = "5.199";

    public static void makeRequest() throws IOException {
        URL url = new URL("https://api.vk.com/method/wall.get" + "?" +
                "access_token=" + access_token + "&domain=" + domain + "&offset=" + offset + "&count=" + count + "&v=" + v);
        //String body = "{\"name\": \"Apple iPad Air\", \"data\": { \"Generation\": \"4th\", \"Price\": \"519.99\", \"Capacity\": \"256 GB\" }}";
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        int responseCode = conn.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            System.out.println("Response Code: " + responseCode);
        } else {
            System.out.println("Error in sending a GET request");
        }
    }
/*
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
            dos.writeBytes(body);
        }

        try (BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
*/
}
