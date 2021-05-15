package com.app.lawapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.lawapp.webservices.ApiClient;
import com.app.lawapp.webservices.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText edit_enter_name, edit_enter_email, edit_enter_mobile, edit_enter_password, edit_confirm_password;
    Button btn_register;
    ApiInterface apiInterface;
    Spinner spinner_user, spinner_type;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<String> lawyerTypeArrayList = new ArrayList<>();
    String lawyer_type, user_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        apiInterface = ApiClient.getClient(RegisterActivity.this).create(ApiInterface.class);
        edit_enter_name = findViewById(R.id.edit_enter_name);
        edit_enter_email = findViewById(R.id.edit_enter_email);
        edit_enter_mobile = findViewById(R.id.edit_enter_mobile);
        edit_enter_password = findViewById(R.id.edit_enter_password);
        edit_confirm_password = findViewById(R.id.edit_confirm_password);
        btn_register = findViewById(R.id.btn_register);
        spinner_user = findViewById(R.id.spinner_user);
        spinner_type = findViewById(R.id.spinner_type);

        stringArrayList.add("User");
        stringArrayList.add("Lawyer");

        lawyerTypeArrayList.add("Divorce Lawyer");
        lawyerTypeArrayList.add("Criminal Lawyer");

        spinner_user.setAdapter(new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, stringArrayList));
        spinner_type.setAdapter(new ArrayAdapter<>(RegisterActivity.this, android.R.layout.simple_list_item_1, lawyerTypeArrayList));

        spinner_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                lawyer_type = (String) adapterView.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner_user.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                user_type = (String) adapterView.getSelectedItem();

                if (user_type.equals("User")) {

                    spinner_type.setVisibility(View.GONE);

                } else {
                    spinner_type.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                spinner_type.setVisibility(View.GONE);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edit_enter_name.getText().toString().isEmpty()) {

                } else {
                    if (edit_enter_email.getText().toString().isEmpty()) {

                    } else {
                        if (edit_enter_mobile.getText().toString().isEmpty()) {

                        } else {

                            if (edit_enter_mobile.getText().toString().length() == 10) {

                                if (edit_enter_password.getText().toString().isEmpty()) {

                                } else {

                                    if (edit_confirm_password.getText().toString().isEmpty()) {

                                    } else {

                                        if (edit_enter_password.getText().toString().equals(edit_confirm_password.getText().toString())) {

                                            if (user_type.equals("User")) {
                                                userReg();
                                            } else {
                                                lawyerReg();
                                            }


                                        } else {

                                        }
                                    }
                                }

                            } else {

                            }
                        }
                    }

                }

            }
        });
    }

    private void lawyerReg() {

        apiInterface.lawyerReg(edit_enter_name.getText().toString(), edit_enter_email.getText().toString(), edit_enter_mobile.getText().toString(), lawyer_type, edit_confirm_password.getText().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {
                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(), MainActivity.class));

                    } else if (jsonObject.getString("code").equals("401")) {
                        Toast.makeText(RegisterActivity.this, "Mobile number already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
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

    private void userReg() {

        apiInterface.userReg(edit_enter_name.getText().toString(), edit_enter_email.getText().toString(), edit_enter_mobile.getText().toString(), edit_confirm_password.getText().toString()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.body().toString());

                    if (jsonObject.getString("code").equals("200")) {
                        Toast.makeText(RegisterActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(), MainActivity.class));

                    } else if (jsonObject.getString("code").equals("401")) {
                        Toast.makeText(RegisterActivity.this, "Mobile number already exists", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
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