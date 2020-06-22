package com.example.illuminationmodify;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import java.net.Socket;
import java.net.UnknownHostException;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity

{
    //UI Element
    Button btnModify;
    Button btnMaintain;
    Button btnQuery;
    EditText txtAddress;

    EditText queryx;
    EditText queryy;

    EditText modify;
    EditText modifyx;
    EditText modifyy;

    EditText maintain;

    Socket myAppSocket = null;
    public static String wifiModuleIp = "";
    public static int wifiModulePort = 0;
    public static String CMD = "0";        // CMD is the string or message which will be sent  to the space broker from the application,
                                           // CMD involves :
                                           //      1. choiceId ("1" means query,"2" means modify, "3" means maintain)
                                           //      2. location (x coordinate)
                                           //      3. location (y coordinate)
                                           //      4. value

    public static String choiceId = "0";
    public static String querybutton = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnModify = findViewById(R.id.btnModify);
        btnMaintain = findViewById(R.id.btnMaintain);
        btnQuery = findViewById(R.id.btnQuery);

        txtAddress = findViewById(R.id.ipAddress);

        queryx = findViewById(R.id.queryx);
        queryy = findViewById(R.id.queryy);

        modify = findViewById(R.id.modify);
        modifyx = findViewById(R.id.modifyx);
        modifyy = findViewById(R.id.modifyy);

        maintain = findViewById(R.id.maintain);

        btnQuery.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {             // if querybutton is pressed

                querybutton = "1";
                getIPandPort();

                String queryxcmd = queryx.getText().toString();    // Storing X-coordiante of location for query method
                String queryycmd = queryy.getText().toString();    // Storing Y-coordiante of location for query method

                choiceId = "1";
                CMD = choiceId + ":" + queryxcmd + ":" + queryycmd  ;    // Sending X and Y cordinate of location with choice type ("1" in the beginning means "query" method)

                Socket_AsyncTask cmd_increase_illumination = new Socket_AsyncTask();

                cmd_increase_illumination.execute();

            }
        });
        btnModify.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {          // if modifybutton is pressed
                getIPandPort();

                String modifycmd = modify.getText().toString();      // Storing value (the value we want to modify) for modify method
                String modifyxcmd = modifyx.getText().toString();    // Storing X coordinate of location for modify method
                String modifyycmd = modifyy.getText().toString();    // Storing Y coordinate of location for modify method

                choiceId = "2";
                CMD = choiceId + ":" + modifyxcmd + ":" + modifyycmd + ":" + modifycmd; // Sending X and Y coordinate of location with choice type ("2" in the beginning means "modify" method)
                                                                  // Also sending the value which needs to get modified

                Socket_AsyncTask cmd_increase_illumination = new Socket_AsyncTask();
                cmd_increase_illumination.execute();

            }
        });
        btnMaintain.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {       // if maintainbutton is pressed
                getIPandPort();

                String maintaincmd = maintain.getText().toString();  // Storing value (the value we want to maintain) for maintain method

                choiceId = "3";
                CMD = choiceId + ":" + maintaincmd;  // Sending choice type ("3" in the beginning means "maintain" method)
                                                     // Also sending the value which needs to get maintained

                Socket_AsyncTask cmd_decrease_illumination = new Socket_AsyncTask();
                cmd_decrease_illumination.execute();
            }
        });

    }

    public void getIPandPort()
    {
        String iPandPort = txtAddress.getText().toString();
        Log.d("MYTEST","IP String: "+ iPandPort);
        String temp[]= iPandPort.split(":");
        wifiModuleIp = temp[0];
        wifiModulePort = Integer.valueOf(temp[1]);
        Log.d("MY TEST","IP:" +wifiModuleIp); // Ip address of the space broker
        Log.d("MY TEST","PORT:"+wifiModulePort); // port number of the space broker
    }

    public class Socket_AsyncTask extends AsyncTask<Void,Void,Void>
    {
        Socket socket;
        String k = "";


        @Override
        protected Void doInBackground(Void... params)
        {
            try
            {
                InetAddress inetAddress = InetAddress.getByName(MainActivity.wifiModuleIp);
                socket = new java.net.Socket(inetAddress,MainActivity.wifiModulePort);


                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataOutputStream.writeUTF(CMD);
                dataOutputStream.flush();

                // as only the query method returns the queried value from space broker ,
                // so when we will figure out that the query button is pressed, only in that case we will execute the following "if" scope,
                //  which receives a message back from space broker which is the queried value and shows the value in tha application.

                if (querybutton == "1")
                {

                    DataInputStream inp = new DataInputStream(socket.getInputStream());

                    int f ;

                    k = inp.readUTF();

                    f = inp.available();

                    char c = 'c';
                    String msg= "";
                    byte[] ary = new byte[f];
                    inp.read(ary);
                    for (byte bt : ary) {
                        c = (char) bt;
                        msg = String.valueOf(c);
                        k = k + msg;
                    }

                    TextView tview = findViewById(R.id.queryvalue);

                    tview.setText("Current illumination level is  = " + k + " unit"); // We show the queried value (which was received from the space broker) in the app




                }
                socket.close();
            }
            catch (UnknownHostException e){e.printStackTrace();}catch (IOException e){e.printStackTrace();}
            return null;
        }
    }
}
