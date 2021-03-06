package com.example.illuminationmodify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

// ****** query,modify and maintain method with USER LOCATION ******

public class MainActivity extends AppCompatActivity

{

    Characteristics illumination = new Characteristics();

    Button btnQueryUserLocation;
    Button btnModifyUserLocation;
    Button btnMaintainUserLocation;
    Button btnSpecificLocation;

    EditText modifyValueUserLocation;
    EditText maintainValueUserLocation;

    public static String requestType ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btnQueryUserLocation = findViewById(R.id.btnqueryuserlocation);
        btnModifyUserLocation = findViewById(R.id.btnModifyuserlocation);
        btnMaintainUserLocation = findViewById(R.id.btnMaintainuserLocation);
        btnSpecificLocation = findViewById(R.id.btnSpecificLocation);



        modifyValueUserLocation = findViewById(R.id.modifyvalueuserlocation);
        maintainValueUserLocation = findViewById(R.id.maintainvalueuserLocation);

        // ******************
        // The User locations are sent as NULL values from app to  raspberry pi .
        // Because in the Demo, the app itself is not providing USER LOCATION (In the real product it will)
        // Rather we are getting USER LOCATION directly from the raspberrypi (By detecting red object by using raspberry pi's camera)
        // ******************

        btnQueryUserLocation.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                requestType = "queryUserLocation";
                String queryX = "NULL";
                String queryY = "NULL";

                String queriedValue= illumination.query(requestType,queryX,queryY);  // ****** Calling of query method with USER LOCATION ******

                TextView tview = findViewById(R.id.queryvalue);
                tview.setText("Current illumination level is  = " + queriedValue + "unit");

            }
        });
        btnModifyUserLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                requestType = "modifyUserLocation";
                String modifyX = "NULL";
                String modifyY = "NULL";
                String modifyValue = modifyValueUserLocation.getText().toString();

                illumination.modify(requestType,modifyX,modifyY,modifyValue);  // ****** Calling of modify method with USER LOCATION ******

            }
        });
        btnMaintainUserLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                requestType = "maintainUserLocation";
                String maintainX = "NULL";
                String maintainY = "NULL";
                String maintainValue = maintainValueUserLocation.getText().toString();

                illumination.maintain(requestType,maintainX,maintainY,maintainValue);  // ****** Calling of maintain method with USER LOCATION ******
            }
        });

        btnSpecificLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                    // ****** Going to the second page which deal with query,modify,maintain method with Specific Location ******

                    Intent intent = new Intent(MainActivity.this, specificLocationActivity.class);
                    startActivity(intent);

            }
        });



    }

}
