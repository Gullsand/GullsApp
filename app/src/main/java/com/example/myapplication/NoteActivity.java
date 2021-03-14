package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.HashMap;

//整体思路：viewmodel存储数据，并设置生命周期感知数据，根据改变生命周期感知数据动态重载数据
public class NoteActivity extends AppCompatActivity  {
    private NoteDataViewmodel model;    //viwemodel
    private Button btn;                 //test
    private ListView list;              //listview
    private NoteAdapter noteAdapter;    //自定义baseAdapter，实现与listView数据绑定
    private ArrayList<HashMap<String,Object>> listItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailynote);


        //动态创建fragment,绑定布局
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.Daily_fragment,NoteFragment.class,null)
                    .commit();
        }

        //获取viewmodel对象
        model=new ViewModelProvider(this).get(NoteDataViewmodel.class);
        //初始化
        model.Init();
        //listview  对象
        list=findViewById(R.id.note_listview);

        //自定义BaseAdapter实现adapter： NoteAdapter
        //listItem=model.getDataList();
        noteAdapter=new NoteAdapter(this,model.getDataList());
        list.setAdapter(noteAdapter);

        //obesrve title_name ,发生变化更新list
        model.getTitle_name().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
               // noteAdapter.change(model.getDataList());        //内部有数据变化函数调用
                noteAdapter.notifyDataSetChanged();         // 数据变化
            }
        });
        //test
        btn=findViewById(R.id.testButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.addDataList("test_title","test_text");
                noteAdapter.notifyDataSetChanged();         //babababa 当adapter的数据变化时，一定要加此函数，
            }
        });



    }


}
