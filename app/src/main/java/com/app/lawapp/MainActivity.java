package com.app.lawapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.lawapp.webservices.ApiClient;
import com.app.lawapp.webservices.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText edit_number, edit_password;
    String from, user_type;
    Button btn_submit;
    TextView text_register;
    ApiInterface apiInterface;
    Spinner spinner_user;
    ArrayList<String> stringArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = ApiClient.getClient(MainActivity.this).create(ApiInterface.class);
        edit_number = findViewById(R.id.edit_number);
        edit_password = findViewById(R.id.edit_password);
        btn_submit = findViewById(R.id.btn_submit);
        text_register = findViewById(R.id.text_register);
        spinner_user = findViewById(R.id.spinner_user);

        stringArrayList.add("User");
        stringArrayList.add("Lawyer");

        spinner_user.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, stringArrayList));

        spinner_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                user_type = (String) adapterView.getSelectedItem();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (user_type.equals("User")) {

                    userLogin();

                } else {
                    lawyerLogin();
                }


            }
        });

        text_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getBaseContext(), RegisterActivity.class));
            }
        });


    }

    private void userLogin() {

        apiInterface.userLogin(edit_number.getText().toString(), edit_password.getText().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("user");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            User users = new User();
                            users.setId(object.getString("id"));
                            users.setName(object.getString("name"));
                            users.setEmail(object.getString("email"));
                            users.setPassword(object.getString("password"));
                            users.setMobile(object.getString("mobile"));
                            users.setFirebase_token(object.getString("firebase_token"));
                            users.setCreated_at(object.getString("created_at"));

                            SharedPreferences sharedPreferences = getSharedPreferences("LAWAPP", Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(users);
                            prefsEditor.putString("User", json);
                            prefsEditor.apply();

                            startActivity(new Intent(getBaseContext(), UserHomepage.class));

                        }

                    } else if (jsonObject.getString("code").equals("401")) {

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

    private void lawyerLogin() {

        apiInterface.lawyerLogin(edit_number.getText().toString(), edit_password.getText().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {

                        JSONArray jsonArray = jsonObject.getJSONArray("lawyer");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);

                            Lawyer lawyer = new Lawyer();
                            lawyer.setId(object.getString("id"));
                            lawyer.setName(object.getString("name"));
                            lawyer.setEmail(object.getString("email"));
                            lawyer.setPassword(object.getString("password"));
                            lawyer.setMobile(object.getString("mobile"));
                            lawyer.setFirebase_token(object.getString("firebase_token"));
                            lawyer.setCreated_at(object.getString("created_at"));
                            lawyer.setLawyer_type(object.getString("lawyer_type"));


                            SharedPreferences sharedPreferences = getSharedPreferences("LAWAPP", Context.MODE_PRIVATE);
                            SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
                            Gson gson = new Gson();
                            String json = gson.toJson(lawyer);
                            prefsEditor.putString("Lawyer", json);
                            prefsEditor.apply();

                            startActivity(new Intent(getBaseContext(), LawyerHomePage.class));

                        }

                    } else if (jsonObject.getString("code").equals("401")) {

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

}