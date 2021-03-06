package com.bscs.group4_sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

     EditText name, studno, contact, dob;
     Button insert, update, delete, view;
     DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studno = findViewById(R.id.studNo);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);
        insert = findViewById(R.id.btnInsert);
        update = findViewById(R.id.btnUpdate);
        delete = findViewById(R.id.btnDelete);
        view = findViewById(R.id.btnView);

        DB = new DBHelper(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              String studnoText = studno.getText().toString();
              String nameText = name.getText().toString();
              String contactText = contact.getText().toString();
              String dobText = dob.getText().toString();
              Boolean checkinsertdata = DB.insertuserdata(studnoText, nameText, contactText, dobText);

              if (checkinsertdata==true) {
                  Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
              }
              else {
                  Toast.makeText(MainActivity.this, "New Entry  Not Inserted", Toast.LENGTH_SHORT).show();
              }

            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studnoText = studno.getText().toString();
                String nameText = name.getText().toString();
                String contactText = contact.getText().toString();
                String dobText = dob.getText().toString();
                Boolean checkupdatedata = DB.updateuserdata(studnoText, nameText, contactText, dobText);

                if (checkupdatedata==true) {
                    Toast.makeText(MainActivity.this, "Entry has been updated", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Entry has Not successfully Updated", Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studnoText = studno.getText().toString();

                Boolean checkdeletedata = DB.deletedata(studnoText);

                if (checkdeletedata==true) {
                    Toast.makeText(MainActivity.this, "Entry deleted", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Entry has Not successfully deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = DB.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Stud :"+res.getString(0)+"\n");
                    buffer.append("Name :"+res.getString(1)+"\n");
                    buffer.append("Contact :"+res.getString(2)+"\n");
                    buffer.append("Date of Birth :"+res.getString(3)+"\n\n");
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("User Entries");
                builder.setMessage(buffer.toString());
                builder.show();
            }        });




    }
}
