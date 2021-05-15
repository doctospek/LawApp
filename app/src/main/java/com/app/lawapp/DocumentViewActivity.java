package com.app.lawapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.lawapp.webservices.ApiClient;
import com.app.lawapp.webservices.ApiInterface;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentViewActivity extends AppCompatActivity {

    TextView text_desc;
    ImageView image_;
    Notification notification;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_view);

        text_desc = findViewById(R.id.text_desc);
        image_ = findViewById(R.id.image_);
        Button text_accept = findViewById(R.id.text_accept);
        Button text_reject = findViewById(R.id.text_reject);
        Button text_chat = findViewById(R.id.text_chat);

        apiInterface = ApiClient.getClient(DocumentViewActivity.this).create(ApiInterface.class);

        notification = (Notification) getIntent().getSerializableExtra("notification");

        Picasso.get().load(ApiClient.MENU_SERVER_URL + notification.getImage_name()).into(image_);

        text_desc.setText(notification.getDesc());

        if (notification.getIs_accepted() == 2) {

            text_accept.setVisibility(View.GONE);
            text_reject.setVisibility(View.GONE);

        } else {
            text_chat.setVisibility(View.GONE);
        }

        text_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updateNotification(String.valueOf(notification.getId()), "2");

            }
        });

        text_reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateNotification(String.valueOf(notification.getId()), "1");
            }
        });


        text_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), ChatActivity.class).putExtra("notification", notification));
            }
        });

    }

    private void updateNotification(String id, String is_accepted) {

        apiInterface.updateNotification(id, notification.getUser_id(), is_accepted).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {

                        Toast.makeText(getBaseContext(), "Notification updated successfully", Toast.LENGTH_SHORT);
                        startActivity(new Intent(getBaseContext(), LawyerHomePage.class));

                    } else {
                        Toast.makeText(getBaseContext(), "Notification not updated successfully", Toast.LENGTH_SHORT);
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