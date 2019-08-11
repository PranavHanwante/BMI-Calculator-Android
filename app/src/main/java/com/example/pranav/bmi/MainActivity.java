package com.example.pranav.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup rgGender;
    Button btnRegister;
    EditText etName,etAge,etPhone;

    SharedPreferences sp;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rgGender = findViewById(R.id.rgGender);
        btnRegister = findViewById(R.id.btnRegister);
        etName = findViewById(R.id.etName);
        etAge = findViewById(R.id.etAge);
        etPhone = findViewById(R.id.etPhone);


        sp = getSharedPreferences("f1", MODE_PRIVATE);   /*SHARED PREF BANGYA USKA NAAM F1*/
        String name = sp.getString("name", "");      /*GETTING NAME THE COULD BE THERE OR NO THERE*/

        if (name.length() == 0){


            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String Name = etName.getText().toString();
                    String Age = etAge.getText().toString();
                    Integer Iage=Integer.parseInt(Age);
                    int ActualAge=Iage;

                    String Phone = etPhone.getText().toString();


                    String regex = "\\d+";

                    Boolean b = Name.matches(regex);

                    if (b == false & Age.length() > 0 & Phone.length() == 10 & ActualAge>0 & Name.length()>0) {


                        int id = rgGender.getCheckedRadioButtonId();
                        RadioButton rb = findViewById(id);
                        String Gender = rb.getText().toString();




                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("name",Name);
                        editor.putString("age",Age);
                        editor.putString("phone",Phone);
                        editor.putString("gender",Gender);
                        editor.commit();


                        Intent i = new Intent(MainActivity.this,MainPage.class);
                        startActivity(i);
                        finish();



                    } else {

                        Toast.makeText(MainActivity.this, "Enter Details properly", Toast.LENGTH_SHORT).show();
                        return;

                    }


                }
            });

    }
    else{

            Intent i = new Intent(MainActivity.this,MainPage.class);
            /*KAHA SEH JANA H( MainActivity), KAHA PEH JANA H*/


            startActivity(i);
            finish();

        }




    }
}
