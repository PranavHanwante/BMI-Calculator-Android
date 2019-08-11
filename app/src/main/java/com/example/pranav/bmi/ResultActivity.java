package com.example.pranav.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    TextView tvResult2,tvUnderWeight,tvNormal,tvOverWeight,tvObese;
    String ansTheory;
    Button btnBack,btnShare,btnSave;

    SharedPreferences sp;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tvResult2 = findViewById(R.id.tvResult2);




        tvUnderWeight = findViewById(R.id.tvUnderWeight);
        tvNormal = findViewById(R.id.tvNormal);
        tvOverWeight = findViewById(R.id.tvOverWeight);
        tvObese = findViewById(R.id.tvObese);





        btnBack = findViewById(R.id.btnBack);
        btnShare = findViewById(R.id.btnShare);
        btnSave = findViewById(R.id.btnSave);

        sp = getSharedPreferences("f1",MODE_PRIVATE);

        String name1 = sp.getString("name","");
        String name2 = sp.getString("age","");
        String name3 = sp.getString("phone","");
        String name4 = sp.getString("gender","");



        StringBuilder sb = new StringBuilder();
        sb = sb.append("Name "+name1+"\n");
        sb = sb.append("Age "+name2+"\n");
        sb = sb.append("Phone "+name3+"\n");
        sb = sb.append("Gender "+name4+"\n");








        final Intent i = getIntent();
        final String msg = i.getStringExtra("bmistr");





        float theFinalResult=Float.parseFloat(msg);





        if(theFinalResult<18.5){
            ansTheory="Underweight BMI";
            tvUnderWeight.setTextColor(Color.RED);
            sb = sb.append("Underweight BMI \n");



        }
        else if (theFinalResult>18.5 & theFinalResult<25){
            ansTheory="Normal BMI";
            tvNormal.setTextColor(Color.RED);
            sb = sb.append("Normal BMI \n");

        }
        else if (theFinalResult>25 & theFinalResult<30){
            ansTheory="over BMI";
            tvOverWeight.setTextColor(Color.RED);
            sb = sb.append("over BMI \n");

        }

        else{
            ansTheory="obese BMI";
            tvObese.setTextColor(Color.RED);
            sb = sb.append("obese BMI \n");
        }




        tvResult2.setText("Your bmi is "+theFinalResult+" is "+ansTheory);

        final String s12=sb.toString();





        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ResultActivity.this,MainPage.class);
                startActivity(i);
                finish();
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Date objDate = new Date();
                // Display the Date & Time using toString()

                String sp=objDate.toString();




                Student s1 = new Student(msg, sp);

                /* L6_2  16:26  dbhandeler ka ek object banaya h(db) in mainActivity class  static DbHandler db;      db = new DbHandler(this);*/

                MainPage.db.addBmi(s1);


            }
        });


        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:"+"mootoo3g@gmail.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Airline Feedback");
                emailIntent.putExtra(Intent.EXTRA_TEXT,s12);
                startActivity(emailIntent);



            }
        });











    }
}
