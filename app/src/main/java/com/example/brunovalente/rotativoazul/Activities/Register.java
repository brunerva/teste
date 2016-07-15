package com.example.brunovalente.rotativoazul.Activities;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.brunovalente.rotativoazul.Models.RegisterRequest;
import com.example.brunovalente.rotativoazul.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Register extends AppCompatActivity {

    EditText etName, etEmail, etCPF, etPass;
    Button bRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etCPF = (EditText) findViewById(R.id.etCPF);
        etPass = (EditText) findViewById(R.id.etPass);
        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String email = etEmail.getText().toString();
                final long CPF = Integer.parseInt(etCPF.getText().toString());
                final String password = etPass.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent registerIntent = new Intent(Register.this,MainActivity.class);
                                Register.this.startActivity(registerIntent);
                            } else {
                                AlertDialog.Builder  builder = new AlertDialog.Builder(Register.this);
                                builder.setMessage("Registro falhou.")
                                        .setNegativeButton("Tente novamente.",null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name,email,CPF,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(Register.this);
                queue.add(registerRequest);
            }
        });
    }


}
