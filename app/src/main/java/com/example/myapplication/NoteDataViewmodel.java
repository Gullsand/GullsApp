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

public class NoteDataViewmodel extends ViewModel {
    //根据item值获取当前的listview的position ，根据item值改变text
    private MutableLiveData<Integer> item;
    private MutableLiveData<String> title;
    private HashMap<Integer,Object> titleMap=new HashMap<>();  //存储title
    private HashMap<Integer,Object> textMap=new HashMap<>();    //存储text

    public void Init(){
        item=new MutableLiveData<Integer>();
        title=new MutableLiveData<String>();

        titleMap.put(0,"newtitle");
        textMap.put(0,"newtext");
        titleMap.put(1,"secondtitle");
        textMap.put(1,"testtext2");
       // item.postValue(0);
        //title.postValue("test");

    }

    public HashMap<Integer, Object> getTitleMap() {
        return titleMap;
    }

    public HashMap<Integer, Object> getTextMap() {
        return textMap;
    }

    public void setItem(Integer integer){   //得到position
        if (item==null)
            item=new MutableLiveData<Integer>();
        item.postValue(integer);
    }

    public MutableLiveData<Integer> getItem(){  //获取position
        if (item==null)
            item=new MutableLiveData<Integer>();
        return item;
    }

    public void setTitle(String string) {   //根据当前item值改变title值
        if (title==null)
            title=new MutableLiveData<String>();

        title.postValue(string);

        titleMap.put(item.getValue(),string);

    }

    public MutableLiveData<String> getTitle(){      //根据item值获取对应的title；
        if (title==null)
            title=new MutableLiveData<>();

        return title;
    }

    public String getText(){      //根据item值获取对应的text；
        if (item==null)
            item=new MutableLiveData<Integer>();

        return textMap.get(item.getValue()).toString();
    }

    public void addItem(Integer integer,String title,String text){  //添加一个单元
        titleMap.put(integer,title);
        textMap.put(integer,text);
    }

    public void getFiledata(String path){       //xml文件获取数据
        File file=new File(path);

        int i=0;
        try {
            FileInputStream fileInputStream=new FileInputStream(file);
            //获取pull解析器
            XmlPullParser parser= Xml.newPullParser();
            //输出格式
            parser.setInput(fileInputStream,"utf-8");    //xml   <note><item><title/><text/></item></note>

            int eventType=parser.getEventType();

            while (eventType!= XmlPullParser.END_DOCUMENT){
                String tagName=parser.getName();

                switch (eventType){
                    case XmlPullParser.START_TAG:
                        if("note".equals(tagName)){

                        }else if("item".equals(tagName)){

                        }else if ("title".equals(tagName)){
                            titleMap.put(i,parser.nextText());  //将数据加载到title
                        }else if ("text".equals(tagName)){
                            textMap.put(i,parser.nextText()); //copy text to textMap
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if("item".equals(tagName)){
                            ++i;
                        }
                        break;
                }
                eventType=parser.next();
            }

        }catch (IOException e){
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public void addFiledata(String path){
        File file=new File(path);
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            //获取序列化工具
            XmlSerializer serializer=Xml.newSerializer();
            serializer.setOutput(fileOutputStream,"UTF-8");

            String tagName=serializer.getName();

            //开始输出数据到文件
            serializer.startDocument("UTF-8",true);

            serializer.startTag(null,"note");

            for (int i=0;i<titleMap.size();i++){
                serializer.startTag(null,"item");

                //title
                serializer.startTag(null,"title");
                serializer.text(titleMap.get(i).toString());
                serializer.endTag(null,"title");
                //text
                serializer.startTag(null,"text");
                serializer.text(textMap.get(i).toString());
                serializer.endTag(null,"text");

                serializer.endTag(null,"item");//item结束标签
            }

            serializer.endTag(null,"note"); //note结束标签

            serializer.endDocument();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
