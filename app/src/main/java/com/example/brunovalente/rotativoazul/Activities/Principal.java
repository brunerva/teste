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
import com.example.brunovalente.rotativoazul.Models.BuyRequest;
import com.example.brunovalente.rotativoazul.Models.RegisterRequest;
import com.example.brunovalente.rotativoazul.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Principal extends AppCompatActivity {

    EditText eName, etTickets;
    Button bBuy,bActive, bLogout;
    int user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        eName = (EditText) findViewById(R.id.eName);
        etTickets = (EditText) findViewById(R.id.etTickets);
        //welcomeMessage = (TextView) findViewById(R.id.tvWelcomeMSG);
        bActive = (Button) findViewById(R.id.bActive);
        bBuy = (Button) findViewById(R.id.bBuy);
        bLogout = (Button) findViewById(R.id.bLogout);

        Intent intent = getIntent();
        user_id = intent.getIntExtra("user_id",-1);
        final String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");
        final int qtdTicket = intent.getIntExtra("qtdTicket", -1);

        eName.setText(name);
        etTickets.setText(qtdTicket+"");

        bActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activeIntent = new Intent(Principal.this,Activate.class);
                Principal.this.startActivity(activeIntent);
            }
        });

        bBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                Intent buyIntent = new Intent(Principal.this,Principal.class);
                                Principal.this.startActivity(buyIntent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
                                builder.setMessage("Compra falhou")
                                        .setNegativeButton("Tente novamente", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                BuyRequest buyRequest = new BuyRequest(user_id,responseListener);
                RequestQueue queue = Volley.newRequestQueue(Principal.this);
                queue.add(buyRequest);
            }
        });

        bLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutIntent = new Intent(Principal.this,MainActivity.class);
                Principal.this.startActivity(logoutIntent);
            }
        });
    }


}
