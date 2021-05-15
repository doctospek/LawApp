package com.app.lawapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.app.lawapp.webservices.ApiClient;
import com.app.lawapp.webservices.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CriminalLawyerFragment extends Fragment {

    ListView listView;
    ArrayList<Lawyer> lawyerArrayList;
    ApiInterface apiInterface;
    String type;
    LawyerAdapter lawyerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_criminal_lawyer, container, false);

        apiInterface = ApiClient.getClient(getActivity()).create(ApiInterface.class);

        listView = view.findViewById(R.id.listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Lawyer lawyer = (Lawyer) adapterView.getAdapter().getItem(i);
                startActivity(new Intent(getActivity(), UserChatActivity.class).putExtra("lawyer", lawyer));
            }
        });

        type = "Criminal Lawyer";

        getLawyer();

        return view;
    }

    private void getLawyer() {

        lawyerArrayList = new ArrayList<>();
        lawyerAdapter = new LawyerAdapter(getActivity(), lawyerArrayList);

        apiInterface.getLawyer(type).enqueue(new Callback<JsonObject>() {
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

                            lawyerArrayList.add(lawyer);
                            lawyerAdapter.notifyDataSetChanged();


                        }

                    } else if (jsonObject.getString("code").equals("401")) {

                    } else {

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                listView.setAdapter(lawyerAdapter);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
}