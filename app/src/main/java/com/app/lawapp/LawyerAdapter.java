package com.app.lawapp;/* Created By Ashwini Saraf on 3/6/2021*/

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.app.lawapp.firebase.Config;
import com.app.lawapp.firebase.MyVolleySingleton;
import com.app.lawapp.webservices.ApiClient;
import com.app.lawapp.webservices.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class LawyerAdapter extends BaseAdapter {

    final private String serverKey = "key=" + Config.FIREBASE_SERVER_KEY;
    final private String contentType = "application/json";
    Context context;
    ArrayList<Lawyer> lawyerArrayList;
    ApiInterface apiInterface;
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;
    User user;

    public LawyerAdapter(Context context, ArrayList<Lawyer> lawyerArrayList) {
        this.context = context;
        this.lawyerArrayList = lawyerArrayList;
        apiInterface = ApiClient.getClient(context).create(ApiInterface.class);

        SharedPreferences sharedPreferences = context.getSharedPreferences("LAWAPP", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("User", "");
        user = gson.fromJson(json, User.class);
    }

    @Override
    public int getCount() {
        return lawyerArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return lawyerArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.lawyer_view, viewGroup, false);

        Lawyer lawyer = lawyerArrayList.get(i);


        TextView text_name = view.findViewById(R.id.text_name);
        TextView text_mobile = view.findViewById(R.id.text_mobile);
        TextView text_email = view.findViewById(R.id.text_email);
        Button btn_request = view.findViewById(R.id.btn_request);

        text_name.setText(lawyer.getName());
        text_mobile.setText("Contact No : " + lawyer.getMobile());
        text_email.setText("Email : " + lawyer.getEmail());

        btn_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                context.startActivity(new Intent(context, AddDocumentActivity.class).putExtra("lawyer", lawyer));

          /*      String msg = "Hello , " + lawyer.getName();


                TOPIC = "/topics/userABC"; //topic must match with what the receiver subscribed to
                NOTIFICATION_TITLE = user.getName() + " requested";
                NOTIFICATION_MESSAGE = msg;


                JSONObject notification = new JSONObject();
                JSONObject notifcationBody = new JSONObject();
                try {
                    notifcationBody.put("title", NOTIFICATION_TITLE);
                    notifcationBody.put("message", NOTIFICATION_MESSAGE);

                    notification.put("to", lawyer.getFirebase_token());
                    notification.put("data", notifcationBody);

                } catch (JSONException e) {
                    Log.e("JSONException", "JSONException: " + e.getMessage());
                }

                sendNotification(notification);

                addNotifiction(String.valueOf(lawyer.getId()), String.valueOf(user.getId()));*/

            }
        });

        return view;
    }

    private void sendNotification(JSONObject notification) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Config.FIREBASE_API, notification,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i(TAG, "onResponse: " + response.toString());

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", contentType);
                params.put("Authorization", serverKey);

                return params;
            }
        };
        MyVolleySingleton.getmInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void addNotifiction(String lawyer_id, String user_id) {


        apiInterface.addNotification(lawyer_id, user_id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {

                        Toast.makeText(context, "Notified to lawyer successfully", Toast.LENGTH_SHORT).show();


                    } else {
                        Toast.makeText(context, "Notification not sent", Toast.LENGTH_SHORT).show();

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
