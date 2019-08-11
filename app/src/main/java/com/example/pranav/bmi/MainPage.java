package com.example.pranav.bmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainPage extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{


    TextView tvWelcome,tvHeight,tvLocation,tvInch,tvWeight;
    EditText etWeight;
    Spinner etFeet,etInch;
    SharedPreferences sp;
    Button btnCalc,btnHistory;
    static DbHandler db;
    GoogleApiClient gac;
    Location loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvWeight = findViewById(R.id.tvWeight);
        etWeight = findViewById(R.id.etWeight);
        tvHeight = findViewById(R.id.tvHeight);
        etInch = findViewById(R.id.etInch);
        tvInch = findViewById(R.id.tvInch);
        etFeet = findViewById(R.id.etFeet);
        btnCalc = findViewById(R.id.btnCalc);
        btnHistory = findViewById(R.id.btnHistory);
        tvLocation = findViewById(R.id.tvLocation);


       final ArrayList<String> actualFeet = new ArrayList<>();
        actualFeet.add("-");
        actualFeet.add("1");
        actualFeet.add("2");
        actualFeet.add("3");
        actualFeet.add("4");
        actualFeet.add("5");
        actualFeet.add("6");
        actualFeet.add("7");
        actualFeet.add("8");
        actualFeet.add("9");
        actualFeet.add("10");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,actualFeet);


        etFeet.setAdapter(arrayAdapter);



       final ArrayList<String> actualInch = new ArrayList<>();
        actualInch.add("-");
        actualInch.add(".0");
        actualInch.add(".1");
        actualInch.add(".2");
        actualInch.add(".3");
        actualInch.add(".4");
        actualInch.add(".5");
        actualInch.add(".6");
        actualInch.add(".7");
        actualInch.add(".8");
        actualInch.add(".9");


        ArrayAdapter arrayAdapter2 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,actualInch);


        etInch.setAdapter(arrayAdapter2);




        GoogleApiClient.Builder builder=new GoogleApiClient.Builder(this);
        /*Builder ko batah re h ki humko kya chaiye (niche dek)*/
        builder.addOnConnectionFailedListener(this);
        builder.addConnectionCallbacks(this);
        /*KOnsa api chaiye*/
        builder.addApi(LocationServices.API);

        gac=builder.build();


        db = new DbHandler(this);




        sp = getSharedPreferences("f1",MODE_PRIVATE);
        String name = sp.getString("name","");
        tvWelcome.setText("Welcome "+name);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float FinallHeight=0f;

                try {
                    String Sfeet,Sinch,FIconcat;
                    int id1 = etFeet.getSelectedItemPosition();
                    int id2 = etInch.getSelectedItemPosition();

                    Sfeet = actualFeet.get(id1);
                    Sinch = actualInch.get(id2);

                    if(Sfeet.equals("-") || Sinch.equals("-")){

                        Toast.makeText(MainPage.this, "Select Feet and Inch", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    FIconcat=Sfeet+Sinch;
                     FinallHeight = Float.parseFloat(FIconcat);
                }catch(NumberFormatException e){


                }



                String weight=etWeight.getText().toString();

                if(weight.length()>0){

                    float finalistHeightValue= FinallHeight*0.3048f;


                    float weightValue = Float.parseFloat(weight);

                    double bmi = weightValue / (finalistHeightValue * finalistHeightValue);


                    DecimalFormat df = new DecimalFormat("#.##");
                    bmi = Double.valueOf(df.format(bmi));



                    String bmistr2 = Double.toString(bmi);




                    Intent i = new Intent(MainPage.this,ResultActivity.class);
                    i.putExtra("bmistr",bmistr2);
                    startActivity(i);
                    etWeight.setText("");

                    etInch.setSelection(0);
                    etFeet.setSelection(0);




                }
                else{

                    Toast.makeText(MainPage.this, "Enter proper Weight", Toast.LENGTH_SHORT).show();
                    return;

                }


            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this,ViewActivityBmi.class));

            }
        });














    }













    @Override
    protected void onResume() {
        super.onResume();
        if (gac != null) {
            gac.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(gac!=null)
            gac.disconnect();

    }

    @Override
    public void onConnected(Bundle bundle) {
        loc=LocationServices.FusedLocationApi.getLastLocation(gac);
        if(loc != null){
            double lat=loc.getLatitude();
            double lon=loc.getLongitude();
            String msg= "lat "+ lat +"lon "+lon;
            Geocoder g=new Geocoder(MainPage.this,Locale.ENGLISH);
            try{
                List<Address> addressList=g.getFromLocation(lat,lon,1);
                Address address=addressList.get(0);
                String add=address.getCountryName()
                        +"  "+address.getAdminArea()
                        +"  "+address.getSubAdminArea()
                        +"  "+address.getLocality()
                        +"   "+address.getSubLocality()
                        +"   "+address.getThoroughfare()
                        +"   "+address.getSubThoroughfare()
                        +"   "+address.getPostalCode();
                tvLocation.setText(add);

            }
            catch (Exception e){
                Toast.makeText(this,"Issue "+e,Toast.LENGTH_SHORT).show();
            }
            }
        else{
            Toast.makeText(this, "pls enable GPS", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

















    public boolean onCreateOptionsMenu(Menu menu) {     /*  Overflow menu dikhega */
        getMenuInflater().inflate(R.menu.m1,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(MenuItem item){        /* This will decide menu action */
        if (item.getItemId()==R.id.website){
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.google.com"));
            startActivity(i);

        }


        if (item.getItemId()==R.id.about){
            Toast.makeText(this, "Pranav", Toast.LENGTH_SHORT).show();
        }
        return  super.onOptionsItemSelected(item);


    }

}
