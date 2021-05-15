package com.app.lawapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.lawapp.webservices.ApiClient;
import com.app.lawapp.webservices.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    Notification notification;
    ListView listOfMessages;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        apiInterface = ApiClient.getClient(ChatActivity.this).create(ApiInterface.class);

        notification = (Notification) getIntent().getSerializableExtra("notification");

        input = findViewById(R.id.input);
        listOfMessages = (ListView) findViewById(R.id.list_of_messages);


        getChat();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  databaseReference.child(String.valueOf(System.currentTimeMillis())).setValue(input.getText().toString());

                sendMessage(input.getText().toString());

                // Clear the input
                input.setText("");
            }
        });
    }

    private void sendMessage(String msg) {

        apiInterface.insertChat(msg, notification.getLawyer_id(), notification.getUser_id(),"Lawyer").enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                getChat();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

    private void getChat() {

        ArrayList<Chat> chatArrayList = new ArrayList<>();
        ChatAdapter chatAdapter = new ChatAdapter(ChatActivity.this, chatArrayList);

        apiInterface.getChat(notification.getLawyer_id(), notification.getUser_id()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    JSONArray jsonArray = jsonObject.getJSONArray("chat");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        Chat chat = new Chat();

                        chat.setMsg(object.getString("msg"));
                        chat.setCreated_at(object.getString("created_at"));
                        chat.setFrom_id(object.getString("from_id"));
                        chat.setTo_id(object.getString("to_id"));
                        chat.setId(Integer.parseInt(object.getString("id")));

                        chatArrayList.add(chat);
                        chatAdapter.notifyDataSetChanged();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listOfMessages.setAdapter(chatAdapter);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }

}