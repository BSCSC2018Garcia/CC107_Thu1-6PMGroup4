package com.example.taguighealthconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText phonenumber_edit, password_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        phonenumber_edit = findViewById(R.id.phonenumber_edit);
        password_edit = findViewById(R.id.password_edit);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String phonenumber = phonenumber_edit.getText().toString();
            String password = password_edit.getText().toString();
            if (phonenumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
            } else {
                   login(phonenumber,password);
            }
            }
        });
    }
    private void login(final String mobilenumber, final String password){
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Logging An Account!");
        progressDialog.show();
        String uRl = "https://taguighealthconsultation.000webhostapp.com/loginregister/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Login Successful")) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent I = new Intent(LoginActivity.this, DashBoardActivity.class);
                   startActivity(I);
                    finish();
                } else if (response.contains("Phone number and Password doesn't match")) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Phone number and Password doesn't match", Toast.LENGTH_SHORT).show();
                }
                else if (response.contains("This number is not yet registered")) {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "This number is not yet registered", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(LoginActivity.this, "Unable to login. Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();

                param.put("mobilenumber",mobilenumber);
                param.put("password",password);
                return  param;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
    }
}
