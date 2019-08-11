package com.example.pranav.bmi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewActivityBmi extends AppCompatActivity {
    TextView tvDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bmi);


        tvDetails = findViewById(R.id.tvDetails);

        String data = MainPage.db.viewBmi();

        if(data.length()==0){

            tvDetails.setText("no records Fooundd");


        }
        else{
            tvDetails.setText(data);
        }



    }
}
