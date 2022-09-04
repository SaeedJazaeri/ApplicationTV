package com.example.myapplication11;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

class CallAPILoginAsyncTask extends AsyncTask<String, Integer, String>{
    boolean isAck = false;
    protected String username, password;

    CallAPILoginAsyncTask(String username, String password) {
        this.username = username;
        this.password = password;
    }

    protected void setVariables(JSONObject jsonRequest) throws JSONException {
        jsonRequest.put("username", username);
        jsonRequest.put("password", password);
    }


    @Override
    protected String doInBackground(String... strings) {
        String result = null;

        HttpURLConnection connection = null;

        try {
            URL url = new URL("https://didbin.ir/api/login/");
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);



            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);

            DataOutputStream writeDataOutputStream = new DataOutputStream(connection.getOutputStream());
            JSONObject jsonRequest = new JSONObject();
            setVariables(jsonRequest);

            writeDataOutputStream.writeBytes(jsonRequest.toString());
            writeDataOutputStream.flush();
            writeDataOutputStream.close();

            int httpResult = connection.getResponseCode();

            if(httpResult == HttpURLConnection.HTTP_OK) {
                isAck = true;
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                try {
                    while ((line = reader.readLine()) != null) {
                        stringBuilder.append(line + "\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                result = stringBuilder.toString();
            } else {
                result = connection.getResponseMessage();
                Log.d("ALI", "Im hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return result;
    }
}
