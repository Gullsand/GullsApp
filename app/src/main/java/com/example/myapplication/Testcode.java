package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Testcode extends AppCompatActivity implements View.OnClickListener {

    Button btn1,btn2,btn3,btn4;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //创建view
        setContentView(R.layout.activity_testcycleview);
        //按钮监听
        btn1=findViewById(R.id.test_btn1);
        btn2=findViewById(R.id.test_btn2);
        btn3=findViewById(R.id.test_btn3);
        btn4=findViewById(R.id.test_btn4);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

    }

    //测试
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.test_btn1:
                File file=new File(getExternalFilesDir(null),"test.txt");
                try {
                    file.createNewFile();
                }catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("create file failed");
                }


                break;
            case R.id.test_btn2:
                try {
                    File file1=new File(getExternalFilesDir(null),"note.xml");
                    FileOutputStream fos=new FileOutputStream(file1);   //输出流
                    //获取序列化工具：
                    XmlSerializer serializer= Xml.newSerializer();
                    serializer.setOutput(fos,"utf-8");
                    //设置文件头
                    serializer.startDocument("utf-8",true);
                    serializer.startTag(null,"note");

                    serializer.startTag(null,"item");
                    serializer.attribute(null,"id","0");

                    serializer.startTag(null,"text");
                    serializer.text("ddddddddddddd");
                    serializer.endTag(null,"text");
                    serializer.endTag(null,"item");


                    serializer.endTag(null,"note");

                    serializer.endDocument();

                    fos.close();
                    System.out.println("write to text succeed" );

                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("failed to write to xml");
                }


               /* SqlDatabaseHelper dbHelper_upgrade=new SqlDatabaseHelper(this,"test_database",2);
                SQLiteDatabase database1=dbHelper_upgrade.getWritableDatabase();*/
               break;
            case R.id.test_btn3:
                System.out.println(getExternalFilesDir(null));
                File fileinput=new File(getExternalFilesDir(null),"note.xml");
                String[] id={null};
                String[] text={null};
                int k=0;
                int j=0;
                try {
                    FileInputStream fin=new FileInputStream(fileinput);
                    //获得pull解析器
                    XmlPullParser parser=Xml.newPullParser();
                    //指定解析的文件类型
                    parser.setInput(fin,"utf-8");

                    int eventType=parser.getEventType();//获取事件类型
                    while(eventType!=XmlPullParser.END_DOCUMENT){
                        String Tagname=parser.getName();//获取当前节点name

                        switch (eventType){
                            case XmlPullParser.START_TAG:
                                if("note".equals(Tagname)){
                                }else if ("item".equals(Tagname)){
                                    id[k++]=parser.getAttributeValue(null,"id");
                                }else if("text".equals(Tagname)){
                                    text[j++]=parser.nextText();
                                }
                                break;
                           /* case XmlPullParser.END_TAG:
                                if("item".equals(Tagname)){
                                    System.out.println("item");
                                    System.out.println(id);
                                    System.out.println(text);
                                }
                                break;*/
                        }

                        eventType=parser.next();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i=0;i<k;i++){
                    System.out.println("item"+i+"  "+id[i]+"   "+text[i]);
                }
                /*SqlDatabaseHelper dbHelper_insert=new SqlDatabaseHelper(this,"test_database",2);
                SQLiteDatabase database_insert=dbHelper_insert.getWritableDatabase();
                //create contentValues
                ContentValues values=new ContentValues();
                //insert data into contentValues
                values.put("id",2);
                values.put("note_title","FirstNote");
                values.put("note_text","hello world");
                //insert into the table:note
                database_insert.insert("note",null,values);
                database_insert.close();

                System.out.println("had inserted");*/
                break;
            case R.id.test_btn4:
               /* SqlDatabaseHelper dbHelper_select=new SqlDatabaseHelper(this,"test_database",2);
                SQLiteDatabase database_select=dbHelper_select.getReadableDatabase();

                //String sql="select * from note where id=?";
                Cursor c=database_select.query("note",new String[]{"id","note_title","note_text"},"id=?",new String[]{"1"},null,null,null);
                String title=null;
                String text=null;
                while(c.moveToNext()){
                    title=c.getString(c.getColumnIndex("note_title"));
                    text=c.getString(c.getColumnIndex("note_text"));
                    System.out.println("note_title:"+title+"------"+"note_text:"+text);
                }
                database_select.close();
                System.out.println("select ");*/
                break;

        }
    }
}
