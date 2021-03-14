package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


public class NoteFragment extends Fragment  {
    private NoteDataViewmodel model;
    private TextView textTitle=null;
    private TextView textView=null;
    private Button btn_solvetext;
    private String str=null;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_dailynote,container,false);
        //初始化：赋值对应组件
         textView=view.findViewById(R.id.Daily_fragment_textnote);
         textTitle=view.findViewById(R.id.Daily_fragment_title);

         model=new ViewModelProvider(requireActivity()).get(NoteDataViewmodel.class);

         btn_solvetext=view.findViewById(R.id.Daily_fragment_btnsolve);

         //button监听事件，改变title
         btn_solvetext.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String str=textTitle.getText().toString();
                 model.setTitle_name(str);      //同步至viewmodel
                // System.out.println(str);

             }
         });
         //观测position值，变化则改变fragment数据，使其与listview对应的positio对应
        model.getPosition().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int k=model.getPosition().getValue();
                System.out.println("fragment"+k);
                textTitle.setText(model.getDataList().get(k).get("title").toString());
                textView.setText(model.getDataList().get(k).get("text").toString());
            }
        });

        //button

        return view;
    }


}
