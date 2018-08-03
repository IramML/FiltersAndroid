package com.example.iram.photofilters.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.iram.photofilters.R;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Button btnEdit=findViewById(R.id.btnEdit);
        Button btnCamera=findViewById(R.id.btnCamera);
        initToolbar();
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this, EditActivity.class);
                intent.putExtra("ACTION", 1);
                startActivity(intent);
            }
        });
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this, EditActivity.class);
                intent.putExtra("ACTION", 2);
                startActivity(intent);
            }
        });
    }
    public void initToolbar(){
        Toolbar toolbar=findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Photo Filter");
    }
}
