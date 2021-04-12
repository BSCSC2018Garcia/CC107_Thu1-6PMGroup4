package com.example.taguighealthconsult;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
        MaterialEditText firstname_edit,middlename_edit ,lastname_edit,suffixname_edit,birth_edit,
                mobilenumber_edit,emailaddress_edit,contactperson_edit,contactpersonmobilenumber_edit,
                houseblockno_edit, street_edit,barangay_edit,region_edit,city_edit, password_edit,passwordconfirm_edit;
        Button btn_finish,btn_newback;
        Spinner sex_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstname_edit = findViewById(R.id.firstname_edit);
        middlename_edit = findViewById(R.id.middlename_edit);
        lastname_edit = findViewById(R.id.lastname_edit);
        suffixname_edit = findViewById(R.id.suffixname_edit);
        birth_edit = findViewById(R.id.birth_edit);
        sex_edit = findViewById(R.id.sex_edit);
        mobilenumber_edit = findViewById(R.id.mobilenumber_edit);
        emailaddress_edit = findViewById(R.id.emailaddress_edit);
        contactperson_edit = findViewById(R.id.contactperson_edit);
        contactpersonmobilenumber_edit = findViewById(R.id.contactpersonmobilenumber_edit);
        btn_finish = findViewById(R.id.btn_finish);
        btn_newback = findViewById(R.id.btn_newback);
        houseblockno_edit = findViewById(R.id.houseblockno_edit);
        street_edit = findViewById(R.id.street_edit);
        barangay_edit = findViewById(R.id.barangay_edit);
        region_edit = findViewById(R.id.region_edit);
        city_edit = findViewById(R.id.city_edit);
        password_edit = findViewById(R.id.password_edit);
        passwordconfirm_edit = findViewById(R.id.passwordconfirm_edit);
         populatGender();
         btn_finish.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String firstname = firstname_edit.getText().toString();
                 String middlename = middlename_edit.getText().toString();
                 String lastname = lastname_edit.getText().toString();
                 String suffix = suffixname_edit.getText().toString();
                 String birthdate = birth_edit.getText().toString();
                 String sex = sex_edit.getSelectedItem().toString();
                 String mobilenumber = mobilenumber_edit.getText().toString();
                 String emailaddress= emailaddress_edit.getText().toString();
                 String contactperson = contactperson_edit.getText().toString();
                 String contactpersonmobilenumber = contactpersonmobilenumber_edit.getText().toString();
                 String houseblockno = houseblockno_edit.getText().toString();
                 String street = street_edit.getText().toString();
                 String barangay = barangay_edit.getText().toString();
                 String region = region_edit.getText().toString();
                 String city = city_edit.getText().toString();
                 String password = password_edit.getText().toString();
                 String confirmpassword = passwordconfirm_edit.getText().toString();
                 if (firstname.isEmpty() || lastname.isEmpty()||birthdate.isEmpty()||sex.isEmpty()||mobilenumber.isEmpty()||
                 emailaddress.isEmpty()||contactperson.isEmpty()||contactpersonmobilenumber.isEmpty()||houseblockno.isEmpty()
                 || street.isEmpty()||barangay.isEmpty()||region.isEmpty()||city.isEmpty()||password.isEmpty()||confirmpassword.isEmpty()){
                     Toast.makeText(RegisterActivity.this, "Please Fill up the Empty Fields", Toast.LENGTH_SHORT).show();
                 }
                 else if (!password.equals(confirmpassword)){

                     Toast.makeText(RegisterActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                 }
                else if (contactpersonmobilenumber_edit.getText().toString().trim().length() <=10) {
                     Toast.makeText(RegisterActivity.this, "Invalid Phone Number! The Phone Number should be 11 digits", Toast.LENGTH_SHORT).show();
                 }
                 else if (password_edit.getText().toString().trim().length() <4) {
                     Toast.makeText(RegisterActivity.this, "The Password should not been less than 4 letters/numbers.", Toast.LENGTH_SHORT).show();
                 }
                else if (mobilenumber_edit.getText().toString().trim().length() <=10) {
                     Toast.makeText(RegisterActivity.this, "Invalid Phone Number! The Phone Number should be 11 digits", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     if(sex.equals("Select Gender")){
                         Toast.makeText(RegisterActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                     } else{

                         registerNewAccounts(firstname, middlename, lastname, suffix,birthdate,sex ,mobilenumber,emailaddress,contactperson,contactpersonmobilenumber,houseblockno,street,barangay, region,city,password );
                     }

                 }

             }
         });

    btn_newback.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RegisterActivity.this,StartingPageActivity.class);
            startActivity(intent);
        }
    });
        sex_edit.setOnItemSelectedListener(this);
    }
    private  void populatGender(){
        ArrayAdapter<String> hospitalsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.gender));
        hospitalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex_edit.setAdapter(hospitalsAdapter);
    }
    private void  registerNewAccounts(final String firstname, final String middlename, final String lastname, final String suffix, final String birthdate,
                                      final String sex, final String mobilenumber, final String emailaddress, final String contactperson, final String contactpersonmobilenumber, final String houseblock,
                                      final String street, final String barangay, final String region, final String city, final String password ){
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Registering new Account!");
        progressDialog.show();
        String uRl = "https://taguighealthconsultation.000webhostapp.com/loginregister/register.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                  if (response.contains("Successfully Registered")) {
                      progressDialog.dismiss();
                      Toast.makeText(RegisterActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                      Intent I = new Intent(RegisterActivity.this,LoginActivity.class);
                      startActivity(I);
                      finish();
                  } else if(response.contains("Failed To Register")){
                      progressDialog.dismiss();
                      Toast.makeText(RegisterActivity.this, "Failed To Register", Toast.LENGTH_SHORT).show();
                  }
                  else if (response.contains("This Email is already used")){
                      progressDialog.dismiss();
                      Toast.makeText(RegisterActivity.this, "This Email is already used", Toast.LENGTH_SHORT).show();
                  }
                  else if (response.contains("This phone number is already used")) {
                      progressDialog.dismiss();
                      Toast.makeText(RegisterActivity.this, "This phone number is already used", Toast.LENGTH_SHORT).show();
                  }
                  else {
                      progressDialog.dismiss();
                      Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_SHORT).show();
                  }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(RegisterActivity.this, "Unable to register. Please check your connection", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("firstname",firstname);
                param.put("middlename",middlename);
                param.put("lastname",lastname);
                param.put("suffix",suffix);
                param.put("birthdate",birthdate);
                param.put("sex",sex);
                param.put("mobilenumber",mobilenumber);
                param.put("emailaddress", emailaddress);
                param.put("contactperson",contactperson);
                param.put("contactpersonmobilenumber", contactpersonmobilenumber);
                param.put("houseblock",houseblock);
                param.put("street", street);
                param.put("barangay",barangay);
                param.put("region",region);
                param.put("city",city);
                param.put("password",password);
                return  param;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(RegisterActivity.this).addToRequestQueue(request);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.sex_edit) {
            String selectedHospitals = parent.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
