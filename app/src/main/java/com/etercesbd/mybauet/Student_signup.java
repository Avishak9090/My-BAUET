package com.etercesbd.mybauet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Student_signup extends AppCompatActivity {

     private EditText studentid,studentname,st_mobile,studentemail,std_pass,std_conpass;
     private Button signupbtn;

    private FirebaseAuth mAuth;
    private DatabaseReference rootRef;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);

          studentid =findViewById(R.id.std_id);
          studentname =findViewById(R.id.std_name);
          st_mobile=findViewById(R.id.std_number);
          studentemail=findViewById(R.id.std_email);
          std_pass = findViewById(R.id.std_pass);
          std_conpass = findViewById(R.id.std_repass);
          signupbtn =findViewById(R.id.signupbtn);

          //firebase
          mAuth=FirebaseAuth.getInstance();
          rootRef=FirebaseDatabase.getInstance().getReference();

          progress= new ProgressDialog(this);

          signupbtn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {

                  String stdid_s= studentid.getText().toString();
                  String stdname_s= studentname.getText().toString();
                  String stdmobile_s= st_mobile.getText().toString();
                  String stdemail_s= studentemail.getText().toString();
                  String stdpass_s= std_pass.getText().toString();
                  String stdrepass_s= std_conpass.getText().toString();

                  if (stdid_s.isEmpty()||stdname_s.isEmpty()||stdmobile_s.isEmpty()||stdemail_s.isEmpty()||stdpass_s.isEmpty()||stdrepass_s.isEmpty()){
                      Toast.makeText(Student_signup.this, "Please fill all box", Toast.LENGTH_SHORT).show();
                      return;
                  }
                 if (stdpass_s.length()<6){
                     Toast.makeText(Student_signup.this, "Password must 6 ", Toast.LENGTH_SHORT).show();
                     return;
                 }

                 if (!stdpass_s.equals(stdrepass_s)){
                     Toast.makeText(Student_signup.this, "Password must same ", Toast.LENGTH_SHORT).show();
                     return;
                 }

              }
          });

    }

       private void registerUser(final String stdid_s ,final String stdname_s,final String stdmobile_s,final String stdemail_s,final String stdpass_s,final String stdrepass_s ){
        progress.setMessage("Please wait");
        progress.show();

        mAuth.createUserWithEmailAndPassword(stdemail_s,stdpass_s).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                HashMap<String, String> map=new HashMap<>();
                map.put("Student",stdname_s);
                map.put("Id",stdid_s);
                map.put("Mobile",stdmobile_s);
                map.put("Email",stdemail_s);
                map.put("password",stdpass_s);
                map.put("uid", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

                rootRef.child("Student").child(stdid_s).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()){
                          progress.dismiss();
                          Intent intent =new Intent(Student_signup.this,Student_login.class);
                          Toast.makeText(Student_signup.this,"succesfull",Toast.LENGTH_SHORT).show();
                          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                          startActivity(intent);
                          finish();
                      }
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progress.dismiss();
                Toast.makeText(Student_signup.this, "unsuccess", Toast.LENGTH_SHORT).show();
            }
        });
       }






}
