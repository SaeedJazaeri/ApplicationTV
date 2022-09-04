package com.example.myapplication11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterAcknowledgeActivity extends AppCompatActivity {

    TextView registerAckOptNumber;
    Button registerAckBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acknowledge);
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

        initView();

        Log.i("CHECK OPT_NUMBER", registerAckOptNumber.getText().toString());

        String firstName = getIntent().getStringExtra("firstName");
        String lastName = getIntent().getStringExtra("lastName");
        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String email = getIntent().getStringExtra("email");
        String password = getIntent().getStringExtra("password");
        String passwordRepeat = getIntent().getStringExtra("passwordRepeat");
        String username = getIntent().getStringExtra("username");

        registerAckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AckCallAPIRegisterAsyncTask task = new AckCallAPIRegisterAsyncTask(
                        firstName,
                        lastName,
                        email,
                        phoneNumber,
                        password,
                        passwordRepeat,
                        username,
                        registerAckOptNumber.getText().toString());

                Log.i("OPT CHECKING: ", registerAckOptNumber.getText().toString());

                task.execute();
            }
        });






    }

    private void initView(){
        registerAckOptNumber = findViewById(R.id.register_tv_opt_number);
        registerAckBtn = findViewById(R.id.btn_register);
    }

    private void ackActivity() {
        Toast.makeText(this, "حساب کاربری شما با موفقیت ساخته شد.", Toast.LENGTH_SHORT).show();
    }

    private void unable2Ack() {
        Toast.makeText(this, "ساخت حساب کاربری برای شما با مشکل مواجه شد.", Toast.LENGTH_SHORT).show();
    }

    private class AckCallAPIRegisterAsyncTask extends CallAPIRegisterAsyncTask {
        private String opt_number;

        AckCallAPIRegisterAsyncTask(String firstName, String lastName,
                                    String email, String phoneNumber,
                                    String password, String passwordRepeat,
                                    String username, String opt_number) {
            super(firstName, lastName,
                  email, phoneNumber,
                  password, passwordRepeat,
                  username);
            this.opt_number = opt_number;
        }

        @Override
        protected void setVariables(JSONObject jsonRequest) throws JSONException {
            jsonRequest.put("username", username);
            jsonRequest.put("password", password);
            jsonRequest.put("password2", passwordRepeat);
            jsonRequest.put("email", email);
            jsonRequest.put("first_name", firstName);
            jsonRequest.put("last_name", lastName);
            jsonRequest.put("phone_number", phoneNumber);
            jsonRequest.put("opt_number", opt_number);
        }

        @Override
        protected void onPostExecute(String result) {
            Log.i("ALIIIIIIIIIIIIIIIII", result);
            if(isAck) {
                ackActivity();
            } else {
                unable2Ack();
            }
        }
    }

}