package com.app.lawapp;/* Created By Ashwini Saraf on 2/18/2021*/

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.lawapp.webservices.ApiClient;
import com.app.lawapp.webservices.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationAdapter extends BaseAdapter {

    Context context;
    ArrayList<Notification> notificationArrayList;
    ApiInterface apiInterface;

    public NotificationAdapter(Context context, ArrayList<Notification> notificationArrayList) {
        this.context = context;
        this.notificationArrayList = notificationArrayList;
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);
    }

    @Override
    public int getCount() {
        return notificationArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return notificationArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.notification_listview, viewGroup, false);

        Notification notification = notificationArrayList.get(i);

        TextView text_user_name = view.findViewById(R.id.text_user_name);
        TextView text_msg = view.findViewById(R.id.text_msg);
        TextView text_time = view.findViewById(R.id.text_time);
        Button text_accept = view.findViewById(R.id.text_accept);
        Button text_reject = view.findViewById(R.id.text_reject);
        Button text_chat = view.findViewById(R.id.text_chat);

        if (notification.getIs_accepted() == 2) {

            text_accept.setVisibility(View.GONE);
            text_reject.setVisibility(View.GONE);

        } else {
            text_chat.setVisibility(View.GONE);
        }

        text_time.setText(notification.getCreated_at());
        text_user_name.setText(notification.getUser_name());

        text_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateNotification(String.valueOf(notification.getId()), notification.getUser_id(), "2");

            }
        });

        text_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification(String.valueOf(notification.getId()), notification.getUser_id(), "1");
            }
        });
        text_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatActivity.class).putExtra("notification", notification));
            }
        });

        return view;
    }

    private void updateNotification(String id, String user_id, String is_accepted) {

        apiInterface.updateNotification(id, user_id, is_accepted).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {

                        Toast.makeText(context, "Notification updated successfully", Toast.LENGTH_SHORT);
                        context.startActivity(new Intent(context, LawyerHomePage.class));

                    } else {
                        Toast.makeText(context, "Notification not updated successfully", Toast.LENGTH_SHORT);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

}
