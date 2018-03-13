package com.example.android.mybookskeeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button read = (Button) findViewById(R.id.read);
        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReadActivity.class);
                String sIsRead = "1";
                intent.putExtra("isRead", sIsRead);
                startActivity(intent);
            }
        });


        Button wannaRead = (Button) findViewById(R.id.wannaRead);
        wannaRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ReadActivity.class);
                String sIsRead = "0";
                intent.putExtra("isRead", sIsRead);
                startActivity(intent);


            }
        });

    }



}
