package com.ice.qchat;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.logging.SocketHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ChatActivity extends AppCompatActivity {
    String targetUsername;

    private MyBaseAdapter adapter;
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
        setContentView(R.layout.activity_chat);
        targetUsername = getIntent().getStringExtra("target_username");
        setTitle(targetUsername);
        adapter = new MyBaseAdapter();
        Tool.handlers.add(mHandler);
        ((ListView)findViewById(R.id.msg_list)).setAdapter(adapter);
        ((Button) findViewById(R.id.send_msg_Button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String my_userName =
                        getApplicationContext().getSharedPreferences("data", Context.MODE_PRIVATE)
                                .getString("username", "null");
                EditText msg_editText = ((EditText)findViewById(R.id.msg_EditText));
                String msg = msg_editText.getText().toString();
                if (  Tool.sendmsg(my_userName, targetUsername ,msg )  ) {
                    MainActivity.map.get(targetUsername).add("m"+msg);
//                    MainActivity.map.get(targetUsername).add("o"+my_userName);
                }
                msg_editText.setText("");
            }
        });
    }

    class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return MainActivity.map.get(targetUsername).size();
        }

        @Override
        public Object getItem(int position) {
            return MainActivity.map.get(targetUsername).get(position).substring(1);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if ((MainActivity.map.get(targetUsername).get(position)).charAt(0) == 'o') { // other
                View view = View.inflate(ChatActivity.this, R.layout.msg_left_item, null);
                ((TextView) view.findViewById(R.id.msg_other_textView))
                        .setText((String)MainActivity.map.get(targetUsername).get(position).substring(1));
                return view;
            } else if ((MainActivity.map.get(targetUsername).get(position)).charAt(0) == 'm') { // me
                View view = View.inflate(ChatActivity.this, R.layout.msg_right_item, null);
                ((TextView) view.findViewById(R.id.msg_me_textView))
                        .setText((String)MainActivity.map.get(targetUsername).get(position).substring(1));
                return view;
            }else {
                Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG);
            }

            return null;
        }
    }
}