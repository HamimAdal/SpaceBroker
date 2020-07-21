package com.example.illuminationmodify;

import android.os.AsyncTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

class wirelessCommunication extends AsyncTask<String,Void,Void>
{
    Socket socket;
    String receivedString ;


    @Override
    protected Void doInBackground(String... arg)
    {

        String CMD = arg[0];
        String arr[] = CMD.split(":");
        String requestID =  arr[0] ;


        try
        {
            InetAddress inetAddress = InetAddress.getByName("10.0.0.32");
            socket = new java.net.Socket(inetAddress,21567);


            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF(CMD);   // Sending data from app to raspberry pi (space broker)
            dataOutputStream.flush();



            if (requestID.equals("querySpecificLocation") || requestID.equals("queryUserLocation") || requestID.equals("returnSpaceImage"))
            {


                DataInputStream inp = new DataInputStream(socket.getInputStream());

                int f;


                receivedString = inp.readUTF();  // Receiving data from raspberry pi to app (space broker)
                f = inp.available();



                char c = 'c';
                String msg= "";
                byte[] ary = new byte[f];
                inp.read(ary);
                for (byte bt : ary) {
                    c = (char) bt;
                    msg = String.valueOf(c);
                    receivedString = receivedString + msg;
                }


            }


            socket.close();
        }
        catch (UnknownHostException e){e.printStackTrace();}catch (IOException e){e.printStackTrace();}
        return null;
    }


}


