package com.ice.qchat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;

public class MainActivity extends AppCompatActivity {

    String[] items = {
//            "大鸟转转转酒吧",
//            "科大桂东人",
//            "研发三部",
//            "ACM队日常集训群",
//            "18物二官方群",
//            "湖科大帅哥美女私密交友群",
//            "2020年Reworld如花群",
//            "h20282",
//            "h20283",
    };
    public static MyBaseAdapter adapter;
    public static LinkedHashMap<String, ArrayList<String>> map = new LinkedHashMap<String, ArrayList<String>>();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                adapter.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tool.handlers.add(mHandler);
        Tool.waitServerMsg();
//        ((ListView)findViewById(R.id.list_view)).setAdapter(new ArrayAdapter<String>(this, R.layout.chat_list_item, items));
        adapter = new MyBaseAdapter();
        ((ListView)findViewById(R.id.list_view)).setAdapter(adapter);

        ((ListView)findViewById(R.id.list_view)).setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,com.ice.qchat.ChatActivity.class);
                intent.putExtra("target_username", (String)map.keySet().toArray()[position]);
                startActivity(intent);
            }
        });
        for ( String s : items ) {
            map.put(s, new ArrayList<String>());
        }
//        map.get("18物二官方群").add("o实验报告下周一晚8点前交！");
//        map.get("18物二官方群").add("o...");
//        map.get(items[0]).add("m好耶！");
//        map.get(items[0]).add("o好耶！");
//        map.get(items[0]).add("m好耶！");
    }

//    class MyBaseAdapter extends BaseAdapter{
//        @Override
//        public int getCount() {
//            return items.length;
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return items[position];
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = View.inflate(MainActivity.this, R.layout.chat_list_item, null);
//            ((TextView) view.findViewById(R.id.list_item_text))
//                    .setText(items[position]);
//            return view;
//        }
//    }
    class MyBaseAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return map.size();
        }

        @Override
        public Object getItem(int position) {
            ArrayList<String > msgs = map.get(map.keySet().toArray()[position]);

            if ( msgs.size()!=0 ) {
                return msgs.get(msgs.size()-1);
            }
            return map.keySet().toArray()[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(MainActivity.this, R.layout.chat_list_item, null);
            ((TextView) view.findViewById(R.id.list_item_text))
                    .setText((String)map.keySet().toArray()[position]);
            ArrayList<String > msgs = map.get(map.keySet().toArray()[position]);

            if ( msgs.size()!=0 ) {
                ((TextView) view.findViewById(R.id.list_item_text_more))
                        .setText(msgs.get(msgs.size()-1).substring(1));
            }
            return view;
        }
    }
}