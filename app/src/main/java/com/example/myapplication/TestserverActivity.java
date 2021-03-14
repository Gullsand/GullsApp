package com.example.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class TestserverActivity extends AppCompatActivity implements View.OnClickListener {

    //创建serviceconnect   Interface for monitoring the state of an application service.监视服务状态
    private TestserverServer.MyBinder myBinder;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {  //Called when a connection to the Service has been established,
            myBinder=(TestserverServer.MyBinder)service;
            myBinder.service_connect_activity();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    // oncreate 初始化
    private Button bindservice;
    private Button unbindservice;
    private TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serverexample);
        //开启服务
        Intent startserviceIntent=new Intent(this,TestserverServer.class);
        startService(startserviceIntent);
        //按钮赋值
        bindservice=findViewById(R.id.server_btnbindserver);
        unbindservice=findViewById(R.id.server_btnunbindserver);
        bindservice.setOnClickListener(this);
        unbindservice.setOnClickListener(this);

        textView=findViewById(R.id.server_textvShow);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent stopserviceIntet=new Intent(this,TestserverServer.class);
        stopService(stopserviceIntet);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.server_btnbindserver:
                Intent bindIntent=new Intent(this,TestserverServer.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);
                textView.setText("已绑定");
                break;

            case R.id.server_btnunbindserver:
                unbindService(connection);
                textView.setText("已解绑");
                break;
        }
    }
}
