package com.app.lawapp;/* Created By Ashwini Saraf on 3/8/2021*/

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ChatAdapter extends BaseAdapter {

    Context context;
    ArrayList<Chat> chatArrayList;
    Lawyer users;

    public ChatAdapter(Context context, ArrayList<Chat> chatArrayList) {
        this.context = context;
        this.chatArrayList = chatArrayList;

        SharedPreferences sharedPreferences = context.getSharedPreferences("LAWAPP", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Lawyer", "");
        users = gson.fromJson(json, Lawyer.class);
    }

    @Override
    public int getCount() {
        return chatArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return chatArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.chat_listview, viewGroup, false);

        Chat chat = chatArrayList.get(i);

        TextView message_text = view.findViewById(R.id.message_text);
        TextView message_time = view.findViewById(R.id.message_time);
        LinearLayout layout = view.findViewById(R.id.layout);

        message_text.setText(chat.getMsg());
        message_time.setText(chat.getCreated_at());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.weight = 1.0f;


        if (chat.getFrom_id().equals(String.valueOf(users.getId()))) {

            Log.e("SAME : ", "RIGHT");

            params.gravity = Gravity.RIGHT;
            message_text.setLayoutParams(params);
            message_time.setLayoutParams(params);

        } else {

            Log.e("NOT SAME : ", "LEFT");

            params.gravity = Gravity.LEFT;
            message_text.setLayoutParams(params);
            message_time.setLayoutParams(params);

        }


        return view;
    }
}
