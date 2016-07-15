package com.example.brunovalente.rotativoazul.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.brunovalente.rotativoazul.Models.LoginRequest;
import com.example.brunovalente.rotativoazul.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText etLogin, etPassword;
    Button bLogin;
    TextView tvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etLogin = (EditText) findViewById(R.id.etLogin);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        tvRegister = (TextView) findViewById(R.id.tvRegisterLink);

        tvRegister.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent regIntent = new Intent(MainActivity.this,Register.class);
                MainActivity.this.startActivity(regIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etLogin.getText().toString();
                final String password = etPassword.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {

                                int user_id = jsonResponse.getInt("user_id");
                                String name = jsonResponse.getString("name");
                                long CPF = jsonResponse.getLong("CPF");
                                String mail = jsonResponse.getString("email");
                                int qtdTicket = jsonResponse.getInt("qtdTicket");
                                int tipo_user = jsonResponse.getInt("tipo_user");

                                if (tipo_user == 0) {
                                    Intent intent = new Intent(MainActivity.this, Principal.class);
                                    intent.putExtra("user_id",user_id);
                                    intent.putExtra("name", name);
                                    intent.putExtra("email", mail);
                                    intent.putExtra("qtdTicket", qtdTicket);
                                    MainActivity.this.startActivity(intent);

                                } else if (tipo_user == 1){
                                    Intent mainIntent = new Intent(MainActivity.this,Principal.class);
                                    MainActivity.this.startActivity(mainIntent);
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setMessage("Login falhou.")
                                        .setNegativeButton("Tente novamente.",null)
                                        .create()
                                        .show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                LoginRequest loginRequest = new LoginRequest(email, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                queue.add(loginRequest);

            }
        });

    }

}
