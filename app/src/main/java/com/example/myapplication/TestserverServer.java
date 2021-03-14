package com.example.myapplication;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

public class TestserverServer  extends Service {

    private MyBinder myBinder=new MyBinder();
    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("service oncreated");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        System.out.println("onstartcommand 执行");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("bind已绑定");
        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        System.out.println("bind已解绑");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("service destoryed");
    }

    class MyBinder extends Binder{
        public void service_connect_activity(){
            System.out.println("service connected the activity");
        }
    }

}
