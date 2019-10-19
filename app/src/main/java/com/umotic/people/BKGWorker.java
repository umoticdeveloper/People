package com.umotic.people;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class to handle BasicAuth post web-api call.
 */

/*TODO : modifica la classe per dare url e valori nel params e iviare la richiesta a "http://peopleapp.altervista.org/DbPhpFiles/InsertUser.php"
   i parametri della post sono:
   1 	ID (AI)
   2	user_sex
   3	user_age
   4	is_special_guest
   5	user_latitude
   6	user_longitude
   7	user_name
   8	user_surname
   9	user_password
   10	user_mail
 */

public class BKGWorker extends AsyncTask<String, String, String> {

    @Override
    protected String doInBackground(String... params) {

        try {
            // Creating & connection Connection with url and required Header.
            URL url = new URL("https://example.com/wp-json/jwt-auth/v1/token");
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("header-param_3", "value-3");
            urlConnection.setRequestProperty("header-param_4", "value-4");
            urlConnection.setRequestProperty("Authorization", "Basic Y2tfNDIyODg0NWI1YmZiZT1234ZjZWNlOTA3ZDYyZjI4MDMxY2MyNmZkZjpjc181YjdjYTY5ZGM0OTUwODE3NzYwMWJhMmQ2OGQ0YTY3Njk1ZGYwYzcw");
            urlConnection.setRequestMethod("POST");   //POST or GET
            urlConnection.connect();

            // Create JSONObject Request
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("username", "user.name");
            jsonRequest.put("password", "pass@123");

            // Write Request to output stream to server.
            OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
            out.write(jsonRequest.toString());
            out.close();

            // Check the connection status.
            int statusCode = urlConnection.getResponseCode();
            String statusMsg = urlConnection.getResponseMessage();

            // Connection success. Proceed to fetch the response.
            if (statusCode == 200) {
                InputStream it = new BufferedInputStream(urlConnection.getInputStream());
                InputStreamReader read = new InputStreamReader(it);
                BufferedReader buff = new BufferedReader(read);
                StringBuilder dta = new StringBuilder();
                String chunks;
                while ((chunks = buff.readLine()) != null) {
                    dta.append(chunks);
                }
                String returndata = dta.toString();
                return returndata;
            } else {
                //Handle else case
            }
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}