package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //转换activity
    private Button btndaily;
    private Button btnphoto;
    private Button btnservice;
    private Button btncamera;
    private Button btntest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        //dailynote&&photo
        btndaily=(Button) findViewById(R.id.btn_service);
        btnphoto=(Button) findViewById(R.id.btn_photo);
        btndaily.setOnClickListener(this);
        btnphoto.setOnClickListener(this);
        //service
        btnservice=findViewById(R.id.btn_dailynote);
        btnservice.setOnClickListener(this);
        //camera
        btncamera=findViewById(R.id.btn_camera);
        btncamera.setOnClickListener(this);
        //test
        btntest=findViewById(R.id.btn_test);
        btntest.setOnClickListener(this);


    }

    public void onClick(View view){

        switch (view.getId()){
               //启动各个activity
            case R.id.btn_dailynote:
                Intent intent=new Intent(this,NoteActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_photo:
                Intent intent1=new Intent(this,PhotoActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_service:
                Intent intent2=new Intent(this,TestserverActivity.class);
                startActivity(intent2);
                break;

            case R.id.btn_camera:
                Intent intent3=new Intent(this,CameraActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_test:
                Intent intent4=new Intent(this,Testcode.class);
                startActivity(intent4);
                break;

        }

        }



}

