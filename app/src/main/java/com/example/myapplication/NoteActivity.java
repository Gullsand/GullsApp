package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import java.util.ArrayList;
import java.util.HashMap;

//整体思路：viewmodel存储数据，并设置生命周期感知数据，根据改变生命周期感知数据动态重载数据
public class NoteActivity extends AppCompatActivity  {
    private NoteDataViewmodel model;    //viwemodel
    private Button btn;                 //test
    private ListView list;               //listview
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailynote);


        //动态创建fragment
        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.Daily_fragment,NoteFragment.class,null)
                    .commit();
        }

        //获取viewmodel对象
        model=new ViewModelProvider(this).get(NoteDataViewmodel.class);
        model.Init();



        //listview  数据
        list=findViewById(R.id.note_listview);
        //定义一个HashMap类型的动态数组存储数据key-value：
        final ArrayList<HashMap<String,Object>> listItem=new ArrayList<HashMap<String,Object>>();
        for(int i=0;i<model.getTitleMap().size();i++){
            HashMap<String,Object> map=new HashMap<String, Object>();
            map.put("btn_name",model.getTitleMap().get(i));
            map.put("text_src",model.getTextMap().get(i));
            listItem.add(map);
        }


    //自定义BaseAdapter实现adapter： NoteAdapter
        final NoteAdapter noteAdapter=new NoteAdapter(this,listItem);
        list.setAdapter(noteAdapter);

        btn=findViewById(R.id.testButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteAdapter.change(listItem); //自定义change函数。
                System.out.println(listItem.size());

            }
        });



    }


}
