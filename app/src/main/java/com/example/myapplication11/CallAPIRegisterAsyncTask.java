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
import java.net.ProtocolException;
import java.net.URL;

class CallAPIRegisterAsyncTask extends AsyncTask<String, Integer, String> {
    boolean isAck = false;
    protected String firstName, lastName,
                     phoneNumber, email,
                     password, passwordRepeat;

    CallAPIRegisterAsyncTask(String firstName, String lastName,
                                    String phoneNumber, String email,
                                    String password, String passwordRepeat) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
    }

    protected void setVariables(JSONObject jsonRequest) throws JSONException {
        jsonRequest.put("username", email);
        jsonRequest.put("password", password);
        jsonRequest.put("password2", passwordRepeat);
        jsonRequest.put("email", email);
        jsonRequest.put("first_name", firstName);
        jsonRequest.put("last_name", lastName);
        jsonRequest.put("phone_number", phoneNumber);
    }

    @Override
    protected String doInBackground(String... strings) {
        String result = null;

        HttpURLConnection connection = null;
        try {
            URL url = new URL("https://didbin.ir/api/register");
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
                Log.i("ALI", "Im hereeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("ALI", "Im h1111111111111111");

        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("ALI", "Im h222222222222222222");

        } finally {
            connection.disconnect();
            Log.i("ALI", "Im h3333333333333333");

        }
        return result;
    }
}
