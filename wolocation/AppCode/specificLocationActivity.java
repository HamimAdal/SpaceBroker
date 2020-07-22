package com.example.illuminationmodify;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


// ******  query,modify and maintain method with SPECIFIC LOCATION  ******

public class specificLocationActivity extends Activity

{


    Characteristics illumination = new Characteristics();
    floorPlan floorPlanImage= new floorPlan();


    Button btnSpaceImage;
    Button btnQuerySpecificLocation;
    Button btnModifySpecificLocation;
    Button btnMaintainSpecificLocation;

    EditText modifyWithLocationValue;
    EditText maintainWithLocationValue;


    public String x,y;
    TextView  coordinateXY, imgSize;
    ImageView spaceImageDisplay;

    public static String requestType ;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_location);



        spaceImageDisplay = findViewById(R.id.spaceimagedisplay);
        coordinateXY = findViewById(R.id.invertedxy);
        imgSize = findViewById(R.id.size);

        btnSpaceImage = findViewById(R.id.btnspaceimage);
        btnQuerySpecificLocation = findViewById(R.id.btnQueryspecificlocation);
        btnModifySpecificLocation = findViewById(R.id.btnModifyspecificlocation);
        btnMaintainSpecificLocation = findViewById(R.id.btnMaintainspecificlocation);


        modifyWithLocationValue = findViewById(R.id.modify);
        maintainWithLocationValue =  findViewById(R.id.maintainwithlocation);


        btnSpaceImage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                requestType = "returnSpaceImage";

                String queriedSpaceImage= floorPlanImage.returnSpaceImage(requestType ); // ******calling of returnSpaceImage method ******
                // ******returns space image from raspberry pi (Space Broker) ******


                // Displaying Space Image on the app
                ImageView image = findViewById(R.id.spaceimagedisplay);
                byte[] imageBytes = Base64.decode(queriedSpaceImage, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                image.setImageBitmap(decodedImage);


            }

        });

        OnTouchListener imgSourceOnTouchListener
                = new OnTouchListener()
        {

            @Override
            public boolean onTouch(View view, MotionEvent event)
            {

                String coordinate = floorPlanImage.inputLocationInSpaceImage(view,  event); // ***** This method returns Location coordinates (x and y) provided by the user *****
                // ***** by touching pixel on the Space Image *****

                String array[] = coordinate.split(":");

                x = array[0];    // ***** These 'x' and 'y' coordinate values are used as location parameters on the methods (query,modify,maintain) later on *****
                y = array[1];

                coordinateXY.setText(
                        "X Co-ordinate of touched position: "
                                + x + "\n" + "Y Co-ordinate of touched position: "
                                + y);

                imgSize.setText(
                        "\nTotal size: "
                                +"Width: "  + String.valueOf(480) + ", "
                                +"Height: "  + String.valueOf(360));

                return true;

            }
        };

        spaceImageDisplay.setOnTouchListener(imgSourceOnTouchListener);

        btnQuerySpecificLocation.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                requestType = "querySpecificLocation";
                String queryX = String.valueOf(x);
                String queryY = String.valueOf(y);

                String queriedValue= illumination.query(requestType,queryX,queryY);  // ****** Calling of query method with SPECIFIC LOCATION ******

                TextView tview = findViewById(R.id.queryvalue);
                tview.setText("Current illumination level is  = " + queriedValue + "unit");

            }
        });
        btnModifySpecificLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                requestType = "modifySpecificLocation";
                String modifyX = String.valueOf(x);
                String modifyY = String.valueOf(y);
                String modifyValue = modifyWithLocationValue.getText().toString();

                illumination.modify(requestType,modifyX,modifyY,modifyValue);  // ****** Calling of modify method with SPECIFIC LOCATION ******

            }
        });

        btnMaintainSpecificLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                requestType = "maintainSpecificLocation";
                String maintainX = String.valueOf(x);
                String maintainY = String.valueOf(y);
                String maintainValue = maintainWithLocationValue.getText().toString();

                illumination.maintain(requestType,maintainX,maintainY,maintainValue);  // ****** Calling of maintain method with SPECIFIC LOCATION ******

            }
        });





    }


    // When user touches the pixels on the Space Image


}

