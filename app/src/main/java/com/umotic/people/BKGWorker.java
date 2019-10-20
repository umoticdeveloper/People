package com.umotic.people;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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

public class BKGWorker extends AsyncTask<String, Void, String> {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    String result = "ERROR";

    @Override
    protected String doInBackground(String... strings) {
        try {
            POST(strings[0], strings[1]);
        } catch (IOException e) {
            e.printStackTrace();
            this.result = "1";
        }
        return result;
    }


    public String POST(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            this.result = response.body().string();
            Log.i("RESPONSE", result);
            return result;
        }

    }

    public String getter() {
        return this.result;
    }
}