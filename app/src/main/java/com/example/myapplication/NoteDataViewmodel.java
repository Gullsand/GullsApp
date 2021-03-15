package com.example.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Xml;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class NoteDataViewmodel extends ViewModel {
    //根据item值获取当前的listview的position ，根据item值改变text
    private MutableLiveData<Integer> position=new MutableLiveData<>();//代表数据在数组中的位置
    private MutableLiveData<String> title_name=new MutableLiveData<>();     //title_name
    private ArrayList<HashMap<String,Object>> dataList=new ArrayList<>();//key-value值存储数据，title—...text...
    private String filePath;
    //listdata 存储数据
    public void setDataList(ArrayList<HashMap<String, Object>> dataList) {      //设置listdata
        this.dataList = dataList;
    }

    public void Init(String path){
        filePath=path;

        System.out.println(filePath);
        //从xml文件中获取数据
        dataList=getFiledata(path);
        if(dataList.get(0).get("title")==null){
            HashMap<String,Object> hashMap=new HashMap<>(); //先初始化
            hashMap.put("title","title");
            hashMap.put("text","text");
            dataList.add(hashMap);
            addFiledata(path,dataList);
            System.out.println("xml is empty or data error");
            dataList=getFiledata(path);
        }
        Integer k=0;
        position.postValue(k);
        title_name.postValue("name");

    }

    public void saveData(){
        addFiledata(filePath,dataList);
    }


    public ArrayList<HashMap<String, Object>> getDataList() {           //获取listdata
        return dataList;
    }


    public void addDataList(String title,String text){      //考虑是否同时更新xml文件
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("title",title);
        hashMap.put("text",text);
        dataList.add(hashMap);
    }

    //position
    public void setPosition(Integer integer) {

        position.postValue(integer);

    }

    public MutableLiveData<Integer> getPosition() {
        return position;
    }

    //title_name
    public void setTitle_name(String string) {              //更改titlename时，同时需要更改listdata中的数据

        title_name.postValue(string);
        dataList.get(position.getValue()).put("title",string);   //更改listdata中的数据
    }

    public void setText(String string){                         //更改text数据
        dataList.get(position.getValue()).put("text",string);
    }

    public MutableLiveData<String> getTitle_name() {
        return title_name;
    }

    protected ArrayList<HashMap<String,Object>>getFiledata(String path){       //从xml文件获取数据
        ArrayList<HashMap<String,Object>> arrayList=new ArrayList<>();
        File file=new File(path,"note.xml");

        int i=0;
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            //获取pull解析器
            XmlPullParser parser= Xml.newPullParser();
            //输出格式
            parser.setInput(fileInputStream,"utf-8");    //xml   <note><item><title/><text/></item></note>

            int eventType=parser.getEventType();
            HashMap<String,Object> map=new HashMap<>();
            while (eventType!= XmlPullParser.END_DOCUMENT){
                String tagName=parser.getName();
                Boolean isnull=false;
                switch (eventType){
                    case XmlPullParser.START_DOCUMENT:

                    case XmlPullParser.START_TAG:
                        if("note".equals(tagName)){

                        }else if("item".equals(tagName)){
                                     //用于判断item内数据是否为空
                        }else if ("title".equals(tagName)){
                           // System.out.println("getfile"+parser.nextText());
                            map.put("title",parser.nextText());  //将数据加载到title
                            System.out.println(map.get("title"));
                        }else if ("text".equals(tagName)){
                         //   System.out.println(parser.nextText());
                            map.put("text",parser.nextText()); //copy text to textMap
                            System.out.println(map.get("text"));
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if("item".equals(tagName)){
                                      //判断数据是否有效
                                arrayList.add(map);
                                System.out.println(arrayList.get(i).get("title")+"---"+arrayList.get(i).get("text"));
                                ++i;
                            map=new HashMap<>();
                        }

                        break;
                }

                eventType=parser.next();
            }

            fileInputStream.close();
        }catch (IOException e){
            e.printStackTrace();
        }catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    protected void addFiledata(String path,ArrayList<HashMap<String,Object>> arrayList){    //输出数据到xml文件
        File file=new File(path,"note.xml");
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            //获取序列化工具
            XmlSerializer serializer=Xml.newSerializer();
            serializer.setOutput(fileOutputStream,"UTF-8");

            String tagName=serializer.getName();

            //开始输出数据到文件
            serializer.startDocument("UTF-8",true);

            serializer.startTag(null,"note");
            for (int i=0;i<arrayList.size();i++){
                serializer.startTag(null,"item");

                //title------
                serializer.startTag(null,"title");
                serializer.text(arrayList.get(i).get("title").toString());//不为空

                System.out.println(serializer.getName()+"------"+arrayList.get(i).get("title"));
                serializer.endTag(null,"title");
                //-------
                //text----
                serializer.startTag(null,"text");
                serializer.text(arrayList.get(i).get("text").toString());
                System.out.println(serializer.getName()+"------"+arrayList.get(i).get("text"));
                serializer.endTag(null,"text");
                //-------
                serializer.endTag(null,"item");//item结束标签
            }

            serializer.endTag(null,"note"); //note结束标签

            //文本结束
            serializer.endDocument();
            //输出流结束
            fileOutputStream.flush();
            fileOutputStream.close();

        } catch (IOException e) {
            System.out.println("addfile error");
            e.printStackTrace();
        }
    }

}
