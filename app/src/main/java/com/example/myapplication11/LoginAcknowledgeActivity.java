package com.example.myapplication11;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginAcknowledgeActivity extends AppCompatActivity {

    TextView loginAckOptNumber;
    Button loginAckBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_acknowledge);
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        initView();

        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");

        loginAckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AckCallAPILoginAsyncTask task = new AckCallAPILoginAsyncTask(
                        username,
                        password,
                        loginAckOptNumber.getText().toString());
                task.execute();
            }
        });

    }

    private void initView(){
        loginAckOptNumber = findViewById(R.id.tv_opt_number);
        loginAckBtn = findViewById(R.id.btn_login);
    }

    private void ackActivity() {
        Toast.makeText(this, "ورود شما با موفقیت انجام شد", Toast.LENGTH_SHORT).show();
    }

    private void unable2Ack() {
        Toast.makeText(this, "کد صحت سنجی صحیح نمی‌باشد!!", Toast.LENGTH_SHORT).show();
    }

    private class AckCallAPILoginAsyncTask extends CallAPILoginAsyncTask {
        private String opt_number;

        AckCallAPILoginAsyncTask(String username, String password, String opt_number) {
            super(username, password);
            this.opt_number = opt_number;
        }

        @Override
        protected void setVariables(JSONObject jsonRequest) throws JSONException {
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);
            jsonRequest.put("opt_number", opt_number);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.d("SAEEEEED", result);
            if(isAck) {
                ackActivity();
            } else {
                unable2Ack();
            }
        }
    }


}