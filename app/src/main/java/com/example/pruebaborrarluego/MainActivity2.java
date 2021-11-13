package com.example.pruebaborrarluego;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        //UI
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

        String URL = "http://10.0.2.2:3001/v1/user/"+ id;
        System.out.println(URL + "VERIFICAR");
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
            
            String name = et1Name.getText().toString().trim();
            String email = et1Email.getText().toString().trim();
            String password = et1Password.getText().toString().trim();
            String phone = et1Phone.getText().toString().trim();
            
            updateUser(name, email, password, phone);
            
        } else if (id == R.id.btnDelete) {
            String idUser = et1Id.getText().toString().trim();

            removeUser(idUser);
        }
    }

    private void updateUser(final String name, final String email, final String password, final String phone) {
        String URL = "http://10.0.2.2:3001/v1/user/" + id;
        StringRequest stringRequest = new StringRequest(
                Request.Method.PUT,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity2.this,"Updated!",Toast.LENGTH_SHORT).show();
                        System.out.println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);
                params.put("phone", phone);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void removeUser(final String idUser) {
        String URL = "http://10.0.2.2:3001/v1/user/" + idUser;
        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(stringRequest);
    }
}