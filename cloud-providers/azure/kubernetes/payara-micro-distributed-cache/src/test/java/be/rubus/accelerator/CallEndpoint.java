package be.rubus.accelerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketException;
import java.net.URL;
import java.util.Date;

public class CallEndpoint {

    public static void main(String[] args) throws IOException, InterruptedException {
        for (int i = 0; i < 100; i++) {

            //callEndpoint("Rest", "http://192.168.1.2:30001/loadbalancer/pod");
            //callEndpoint("Rest", "http://192.168.1.2:30001/loadbalancer/pod");
            callEndpoint("health", "http://localhost:30001/health");
            callEndpoint("Rest", "http://localhost:30001/loadbalancer/pod");
            Thread.sleep(1000);
        }
    }

    private static void callEndpoint(String name, String url) throws IOException {
        URL urlForGetRequest = new URL(url);

        HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
        conection.setRequestMethod("GET");
        try {
            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String readLine;
                while ((readLine = in.readLine()) != null) {
                    response.append(readLine);
                }
                in.close();

                // print result
                System.out.println(new Date().toString() + " " + name + " - Result " + response.toString());


            } else {
                System.out.println(new Date().toString() + " " + name + " - GET NOT WORKED " + responseCode);
            }
        } catch (SocketException e) {
            System.out.println(new Date().toString() + " " + name + " - Endpoint down");
        }
    }
}
