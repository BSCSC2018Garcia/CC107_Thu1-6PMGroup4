package com.example.taguighealthconsult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;


import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;


import android.os.Environment;
import android.util.Log;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class AppointmentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private  EditText date_edit;
    Button  btn_back;
    EditText patientname_edit, age_edit,mobileno_edit,address_edit,medical_edit,time_edit;
   //for date


     int t2Hour, t2Minute;

     //for qrcode
    Button save_btn, download_btn;

    private final static int QRCodeWidth = 500;
    Bitmap bitmap;
    ImageView qrcode;


    //for list view

      Spinner hospitals;
      Spinner gender_edit;

      RadioGroup radioGroup;
      RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        btn_back = findViewById(R.id.btn_back);
        patientname_edit = findViewById(R.id.patientname_edit);
        age_edit = findViewById(R.id.age_edit);
        gender_edit = findViewById(R.id.gender_edit);
        mobileno_edit = findViewById(R.id.mobileno_edit);
        address_edit = findViewById(R.id.address_edit);
        medical_edit = findViewById(R.id.medical_edit);
        time_edit = findViewById(R.id.time_edit);
        date_edit = findViewById(R.id.date_edit);
        radioGroup = findViewById(R.id.radioGroup);


        //Initializing variables and assign the values.

        save_btn = findViewById(R.id.save_btn);

        qrcode = findViewById(R.id.qrcode);
        download_btn = findViewById(R.id.download_btn);


          //initializing list view for barangay
           hospitals = findViewById(R.id.hospitals);
          populateHospitals();
          populateGender();
        time_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(AppointmentActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                t2Hour = hourOfDay;
                                t2Minute = minute;
                                String time = t2Hour + ":" + t2Minute;
                                SimpleDateFormat f24Hours =  new SimpleDateFormat("HH:mm");
                                try {
                                    Date date = f24Hours.parse(time);
                                    SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");

                                    time_edit.setText(f12Hours.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        },12,0,false);

                timePickerDialog.updateTime(t2Hour, t2Minute);
                timePickerDialog.show();
            }
        });

          date_edit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  showDatePickerDialog();
              }
          });





        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dash = new Intent(AppointmentActivity.this,DashBoardActivity.class);
                startActivity(dash);
            }
        });



       save_btn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AppointmentActivity.this);
               alertDialogBuilder.setMessage("Are you sure you want  to proceed?");
               alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface arg0, int arg1) {
                       save_btn.setText("Generate");
                       download_btn.setVisibility(View.INVISIBLE);
                       final String hospitalbranches = hospitals.getSelectedItem().toString();
                       final String patientname  = patientname_edit.getText().toString();
                       final String age = age_edit.getText().toString();
                       final String gender = gender_edit.getSelectedItem().toString();
                       final String mobile = mobileno_edit.getText().toString();
                       final String address = address_edit.getText().toString();
                       final String  medical = medical_edit.getText().toString();
                       final String time = time_edit.getText().toString();
                       final String date = date_edit.getText().toString();
                       int radioId = radioGroup.getCheckedRadioButtonId();
                       radioButton = findViewById(radioId);
                       final String emergency = radioButton.getText().toString();
                       if (patientname.isEmpty() || age.isEmpty() || gender.isEmpty() || mobile.isEmpty() || address.isEmpty()
                               || medical.isEmpty() || time.isEmpty() || date.isEmpty() || emergency.isEmpty()){
                           Toast.makeText(AppointmentActivity.this, "Please Fill up the Empty Fields", Toast.LENGTH_SHORT).show();
                       }
                       else if (gender.equals("Select Gender")){
                           Toast.makeText(AppointmentActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
                       }
                       else {
                           save_btn.setOnClickListener(new View.OnClickListener() {
                               @Override
                               public void onClick(View v) {
                                   setAppointmentSql(patientname,age,gender,mobile,address,medical,time,date,emergency,hospitalbranches);

                               }
                           });
                       }

                   }
               });
               alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
               AlertDialog alertDialog = alertDialogBuilder.create();
               alertDialog.show();
           }
       });
           download_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(AppointmentActivity.this);
               alertDialogBuilder.setMessage("Are you sure you want to cancel?");
               alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface arg0, int arg1h) {
                       Intent i = new Intent(AppointmentActivity.this,AppointmentActivity.class);
                       startActivity(i);

                   }
               });
               alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                   }
               });
                   AlertDialog alertDialog = alertDialogBuilder.create();
                   alertDialog.show();
               }
           });
        hospitals.setOnItemSelectedListener(this);
        gender_edit.setOnItemSelectedListener(this);

    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this,Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
    private  void setAppointmentSql(final String fullname, final String age, final String gender, final String mobileno, final String address ,
                                    final String medicalconcern, final String time, final String date, final String Emergency , final String hospital_branch){
        final ProgressDialog progressDialog = new ProgressDialog(AppointmentActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setTitle("Setting an Appointment!");
        progressDialog.show();
        String uRl = "https://taguighealthconsultation.000webhostapp.com/loginregister/user_appointments.php";
        StringRequest request =  new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.contains("Successfully Set an Appointment")) {
                    progressDialog.dismiss();

                    save_btn.setText("Generate");
                    download_btn.setVisibility(View.INVISIBLE);
                    final String hospitalbranches = hospitals.getSelectedItem().toString();
                    final String patientname  = patientname_edit.getText().toString();
                    final String age = age_edit.getText().toString();
                    final String gender = gender_edit.getSelectedItem().toString();
                    final String mobile = mobileno_edit.getText().toString();
                    final String address = address_edit.getText().toString();
                    final String  medical = medical_edit.getText().toString();
                    final String time = time_edit.getText().toString();
                    final String date = date_edit.getText().toString();
                    int radioId = radioGroup.getCheckedRadioButtonId();
                    radioButton = findViewById(radioId);
                    final String emergency = radioButton.getText().toString();

                    try {
                        bitmap = textToImageEncode("Name: "+ " "+ patientname_edit.getText().toString()+"\n"+ "Age:"+ " " + age_edit.getText().toString()+
                                "\n"+ "Gender: "  + " " +gender + "\n"+ "Mobile Number: " +  " "+ mobileno_edit.getText().toString()+
                                "\n"+"Appointment Date and Time" + " " + date_edit.getText().toString() + " " + time_edit.getText().toString() +"\n" +
                                "\n"+"Hospital Health Center Branches:" + " "+hospitalbranches +"\n"+  "Urgent/ Need Ambulance:"+ " " + emergency);
                        qrcode.setImageBitmap(bitmap);
                        download_btn.setText("Download");
                        download_btn.setVisibility(View.VISIBLE);
                        Toast.makeText(AppointmentActivity.this, "Successfully Set an Appointment", Toast.LENGTH_LONG).show();
                        download_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ActivityCompat.requestPermissions(AppointmentActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                                ActivityCompat.requestPermissions(AppointmentActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);
                                saveToGallery();
                            }
                        });
                    } catch (WriterException e){
                        e.printStackTrace();
                    }
                } else if (response.contains("The schedule for this day is already full. Please choose another day")){
                    progressDialog.dismiss();
                    Toast.makeText(AppointmentActivity.this, "The schedule for this day is already full. Please choose another day", Toast.LENGTH_SHORT).show();
                } else if (response.contains("You're already choose this day")) {
                    progressDialog.dismiss();
                    Toast.makeText(AppointmentActivity.this, "You're already choose this day", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.dismiss();
                    Toast.makeText(AppointmentActivity.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(AppointmentActivity.this, "Unable to Set Appointment Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AppointmentActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("fullname",fullname);
                param.put("age",age);
                param.put("gender",gender);
                param.put("mobileno",mobileno);
                param.put("address",address);
                param.put("medicalconcern",medicalconcern);
                param.put("time",time);
                param.put("date", date);
                param.put("Emergency",Emergency);
                param.put("hospital_branch",hospital_branch);
                return  param;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(AppointmentActivity.this).addToRequestQueue(request);
    }


    private  void  saveToGallery(){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) qrcode.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        FileOutputStream outputStream = null;
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File dir = new File(file.getAbsolutePath() + "/TaguigHealthConsult");
        dir.mkdirs();
        String filename = String.format("%d.png", System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try {

             outputStream  = new FileOutputStream(outFile);

        }catch (Exception e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.PNG, 100,outputStream);
        try {
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        }   catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(this, "Image Saved", Toast.LENGTH_SHORT).show();
    }
    private void populateHospitals() {
        ArrayAdapter<String> hospitalsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.hospitals));
        hospitalsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        hospitals.setAdapter(hospitalsAdapter);

    }

    private void populateGender(){
        ArrayAdapter<String>  genderAdapter= new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.sex));
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender_edit.setAdapter(genderAdapter);
    }
    private  Bitmap  textToImageEncode(String value) throws WriterException {
        BitMatrix bitMatrix;
        try{
            bitMatrix = new MultiFormatWriter().encode(value, BarcodeFormat.DATA_MATRIX.QR_CODE, QRCodeWidth, QRCodeWidth,
                    null);
        }catch (IllegalArgumentException e) {
            return  null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();
        int bitMatrixHeight = bitMatrix.getHeight();
        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];
        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;
            for (int x = 0; x < bitMatrixWidth; x++) {
                pixels[offset + x] = bitMatrix.get(x, y) ?
                 ResourcesCompat.getColor(getResources(),R.color.black, null):ResourcesCompat.getColor(getResources(),R.color.colorWhite,null);

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          if (parent.getId() == R.id.hospitals) {
              String selectedHospitals = parent.getSelectedItem().toString();
          } else if (parent.getId()==R.id.gender_edit) {
              String selectedHospitals = parent.getSelectedItem().toString();
          } else {

          }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
       month = month+1;
        String date = ""+month +"/" +dayOfMonth + "/" + year;
        date_edit.setText(date);
    }
}
