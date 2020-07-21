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


// query,modify and maintain method with SPECIFIC LOCATION

public class specificLocationActivity extends Activity

{


    Characteristics illumination = new Characteristics();
    floorPlan floorPlanImage= new floorPlan();


    Button btnSpace;
    Button btnQuery;
    Button btnModify;
    Button btnMaintainWithLocation;

    EditText modifyWithLocationValue;
    EditText maintainWithLocationValue;


    public String x,y;
    TextView  coordinateXY, imgSize;
    ImageView spaceImage;

    public static String requestType ;



    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_location);



        spaceImage = findViewById(R.id.spaceImage);
        coordinateXY = findViewById(R.id.invertedxy);
        imgSize = findViewById(R.id.size);

        btnSpace = findViewById(R.id.btnspace);
        btnQuery = findViewById(R.id.btnQuery);
        btnModify = findViewById(R.id.btnModify);
        btnMaintainWithLocation = findViewById(R.id.btnMaintainwithlocation);


        modifyWithLocationValue = findViewById(R.id.modify);
        maintainWithLocationValue =  findViewById(R.id.maintainwithlocation);


        spaceImage.setOnTouchListener(imgSourceOnTouchListener);

        btnQuery.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {

                requestType = "querySpecificLocation";
                String queryX = String.valueOf(x);
                String queryY = String.valueOf(y);

                String queriedValue= illumination.query(requestType,queryX,queryY);  //Calling of query method with SPECIFIC LOCATION

                TextView tview = findViewById(R.id.queryvalue);
                tview.setText("Current illumination level is  = " + queriedValue + "unit");

            }
        });
        btnModify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                requestType = "modifySpecificLocation";
                String modifyX = String.valueOf(x);
                String modifyY = String.valueOf(y);
                String modifyValue = modifyWithLocationValue.getText().toString();

                illumination.modify(requestType,modifyX,modifyY,modifyValue);  //Calling of modify method with SPECIFIC LOCATION

            }
        });

        btnMaintainWithLocation.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                requestType = "maintainSpecificLocation";
                String maintainX = String.valueOf(x);
                String maintainY = String.valueOf(y);
                String maintainValue = maintainWithLocationValue.getText().toString();

                illumination.maintain(requestType,maintainX,maintainY,maintainValue);  //Calling of maintain method with SPECIFIC LOCATION

            }
        });

        btnSpace.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                floorPlan floorPlanImage= new floorPlan();
                requestType = "returnSpaceImage";

                String queriedSpaceImage= floorPlanImage.returnSpaceImage(requestType ); // calling of returnSpaceImage method
                                                                                       // returns space image from raspberry pi (Space Broker)


                // Displaying Space Image on the app
                ImageView image = findViewById(R.id.spaceImage);
                byte[] imageBytes = Base64.decode(queriedSpaceImage, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                image.setImageBitmap(decodedImage);


            }

        });



    }


    // When user touches the pixels on the Space Image
    OnTouchListener imgSourceOnTouchListener
            = new OnTouchListener()
    {

        @Override
        public boolean onTouch(View view, MotionEvent event)
        {

            String coordinate = floorPlanImage.inputLocationInSpaceImage(view,  event); // This method returns Location coordinates provided by the user
                                                                                        // by touching pixel on the Space Image

            String array[] = coordinate.split(":");

            x = array[0];
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

}

