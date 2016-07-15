package com.example.brunovalente.rotativoazul.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.brunovalente.rotativoazul.R;

public class Admin extends AppCompatActivity {

    EditText etPlaca,etResult;
    Button bSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        etPlaca = (EditText) findViewById(R.id.etPlaca);
        etResult = (EditText) findViewById(R.id.etResult);
        bSearch = (Button) findViewById(R.id.bSearch);

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(Admin.this,Admin.class);
                Admin.this.startActivity(searchIntent);
            }
        });
    }
}
