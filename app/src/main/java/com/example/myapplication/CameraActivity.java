package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {
    static final int REQUEST_VIDEO_CAPTURE = 1;
    private VideoView videoView;
    private Button btn_startVideo;
    private Button btn_stopVideo;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        btn_startVideo = findViewById(R.id.camera_btn_start);
        btn_startVideo.setOnClickListener(this);
        btn_stopVideo = findViewById(R.id.camera_btn_stop);
        btn_stopVideo.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.camera_btn_start:
                takeIntentVideo();
                break;
            case R.id.camera_btn_stop:


                break;
        }
    }


    private void takeIntentVideo() {

        Intent takevideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        if (takevideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takevideoIntent, REQUEST_VIDEO_CAPTURE);
        }

        onActivityResult(REQUEST_VIDEO_CAPTURE,RESULT_OK,takevideoIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri uri = intent.getData();
            videoView.setVideoURI(uri);
        }
    }

}
