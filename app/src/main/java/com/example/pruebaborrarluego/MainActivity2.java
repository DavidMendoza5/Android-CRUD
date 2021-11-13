package com.example.pruebaborrarluego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    EditText et1Name, et1Password, et1Email, et1Phone, et1Id;
    Button btnDelete, btnEdit;
    String id;

    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        requestQueue = Volley.newRequestQueue(this);
        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            id = extras.getString("id");
        }
        initUI();
        readUser();

        btnDelete.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
    }

    private void initUI() {
        et1Name = findViewById(R.id.et1Name);
        et1Password = findViewById(R.id.et1Password);
        et1Email = findViewById(R.id.et1Email);
        et1Phone = findViewById(R.id.et1Phone);
        et1Id = findViewById(R.id.et1Id);

        btnEdit = findViewById(R.id.btnEdit);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void readUser(){

        String URL = "http://192.168.0.16:3000/v1/user/"+ id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, response -> {
            JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++){
                        try {
                            jsonObject = response.getJSONObject(i);

                            et1Name.setText(jsonObject.getString("name"));
                            et1Password.setText(jsonObject.getString("name"));
                            et1Email.setText(jsonObject.getString("email"));
                            et1Phone.setText(jsonObject.getString("phone"));

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error+" --------------------------------------");
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btnEdit) {

        } else if (id == R.id.btnDelete) {

        }
    }
}