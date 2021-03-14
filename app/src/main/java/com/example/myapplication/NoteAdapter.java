package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import java.util.ArrayList;
import java.util.HashMap;
//自定义适配器，用于匹配NoteActivity中的listview

public class NoteAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//layoutInflater引导布局
    private ArrayList<HashMap<String,Object>> listItem;
    private Context context;
    final NoteDataViewmodel model;
    //构造函数
    public NoteAdapter(Context context, ArrayList<HashMap<String,Object>>listItem){
        this.mInflater=LayoutInflater.from(context);
        this.listItem=listItem;
        this.context=context;
        this.model = new ViewModelProvider((ViewModelStoreOwner) context).get(NoteDataViewmodel.class);
    }

    @Override
    public int getCount() {
        return listItem.size();
    }


    @Override
    public Object getItem(int position) {
        return listItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void clear(){
        listItem.clear();
        notifyDataSetChanged();

    }

    public void change(ArrayList<HashMap<String,Object>> listItem){
       // this.listItem.clear();
        this.listItem=listItem;
        notifyDataSetChanged();
    }


    static class ViewHolder{
        public Button btn;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //
        final ViewHolder holder;
        if (convertView==null){
            holder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.listviewitem_note,null);
            holder.btn=(Button) convertView.findViewById(R.id.btn_item);
            convertView.setTag(holder);
        }
        else {
            holder=(ViewHolder) convertView.getTag();
        }

        holder.btn.setText((String) listItem.get(position).get("btn_name"));
        //btton
        holder.btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                model.setItem(position);
            }
        });

        return convertView;
    }



}
