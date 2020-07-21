package com.example.illuminationmodify;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;



interface floorPlanInterface
{
    String returnSpaceImage(String imageString  );
    String inputLocationInSpaceImage(View view, MotionEvent event);

}

class floorPlan implements floorPlanInterface
{


    public static String requestSpaceImage ;


    // The following method is for retrieving space image from raspberry pi (Space Broker)
    public String returnSpaceImage(String choiceId  )
    {


        requestSpaceImage = choiceId + ":" + "NULLVALUE" ;

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(requestSpaceImage); //wireless communication between app and raspberry pi (Space Broker)

        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException e)

        {
            e.printStackTrace();
        }

        String queriedSpaceImage= wirelesscommunication.receivedString;

        return queriedSpaceImage;

    }

    // The following method is for providing Location coordinates from the user by touching pixel on the Space Image
    public String inputLocationInSpaceImage(View view, MotionEvent event)
    {
        int x,y;
        float eventX = event.getX();
        float eventY = event.getY();
        float[] eventXY = new float[] {eventX, eventY};

        Matrix invertMatrix = new Matrix();
        ((ImageView)view).getImageMatrix().invert(invertMatrix);

        invertMatrix.mapPoints(eventXY);
        x = Integer.valueOf((int)eventXY[0]);
        y = Integer.valueOf((int)eventXY[1]);
        Drawable imgDrawable = ((ImageView)view).getDrawable();
        Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();


        double bitwidth = bitmap.getWidth();
        double x1 = (x / bitwidth) * 480;
        x = (int) x1;

        double bitheight = bitmap.getHeight();
        double y1 = ((bitmap.getHeight()- y) / bitheight) * 360;
        y = (int) y1;

        String coordinate = x + ":" + y;
        return coordinate;


    }


}

