package com.etercesbd.mybauet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Student_login extends AppCompatActivity {

    private TextView logtosign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        logtosign=findViewById(R.id.logtosinguptext);



        logtosign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Student_login.this,Activity2.class));
            }
        });
    }
}
