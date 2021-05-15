package com.app.lawapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.lawapp.webservices.ApiClient;
import com.app.lawapp.webservices.ApiInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LawyerHomePage extends AppCompatActivity {

    ApiInterface apiInterface;
    Lawyer lawyer;
    String token;
    ArrayList<Notification> notificationArrayList;
    NotificationAdapter notificationAdapter;
    ListView notification_listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_home_page);

        notification_listview = findViewById(R.id.notification_listview);

        apiInterface = ApiClient.getClient(LawyerHomePage.this).create(ApiInterface.class);

        SharedPreferences sharedPreferences = getSharedPreferences("LAWAPP", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Lawyer", "");
        lawyer = gson.fromJson(json, Lawyer.class);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("DoctorHomeActivity", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        updateFirebaseToken();

                    }
                });

        getNotifications();

        notification_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Notification notification = (Notification) adapterView.getAdapter().getItem(i);

                startActivity(new Intent(getBaseContext(), DocumentViewActivity.class).putExtra("notification", notification));
            }
        });
    }

    private void updateFirebaseToken() {

        apiInterface.updateLawyerFirebaseToken(lawyer.getId(), token).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {


                    } else {

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

    private void getNotifications() {

        notificationArrayList = new ArrayList<>();
        notificationAdapter = new NotificationAdapter(LawyerHomePage.this, notificationArrayList);

        apiInterface.getNotifications(lawyer.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("notification");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            Notification notification = new Notification();
                            notification.setId(Integer.parseInt(object.getString("id")));
                            notification.setIs_accepted(Integer.parseInt(object.getString("is_accepted")));
                            notification.setUser_name(object.getString("user_name"));
                            notification.setLawyer_id(object.getString("lawyer_id"));
                            notification.setUser_id(object.getString("user_id"));
                            notification.setCreated_at(object.getString("created_at"));
                            notification.setImage_name(object.getString("image_name"));
                            notification.setDesc(object.getString("desc"));

                            notificationArrayList.add(notification);
                            notificationAdapter.notifyDataSetChanged();

                        }

                    } else {

                        Toast.makeText(LawyerHomePage.this, "Failed", Toast.LENGTH_SHORT);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                notification_listview.setAdapter(notificationAdapter);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });

    }


}