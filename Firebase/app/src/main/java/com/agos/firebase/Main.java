package com.agos.firebase;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //validamos el acceso por primera vez
        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        if (sharedPreferences.getBoolean("firstRun", false)) {
            initChat();
        } else {
            init();
        }


    }

    private void initChat() {
        startActivity(new Intent(Main.this, Chat.class));
        finish();
    }

    private void init() {

        final EditText userName = (EditText) findViewById(R.id.userName);

        ((Button) findViewById(R.id.login)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userName.getText().toString() != "" && userName.getText().toString().length() > 1) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstRun", true);
                    editor.putString("userName", userName.getText().toString());
                    editor.commit();
                    initChat();
                } else {
                    Toast.makeText(Main.this, getString(R.string.question_no_name), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
