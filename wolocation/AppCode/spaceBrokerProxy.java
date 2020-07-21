package com.example.illuminationmodify;


interface characteristicsInterface
{
    String query(String ChoiceId, String X,String Y  );
    void modify(String ChoiceId, String X,String Y,String Value);
    void maintain(String ChoiceId, String X,String Y,String Value);
}

class Characteristics   implements characteristicsInterface
{
          // *****************
          // CMD is the string or message which will be sent  to the space broker from the application which consists
          // 1. requestType and/or
          // 2. location and/or
          // 3. value
          // *****************

    public static String CMD;
    String queriedValue= "";


    @Override
    public String query(String requestType, String X,String Y )  // ****** implementation of query method ******
    {



        if (X == "NULL" && Y == "NULL")        // ****** implementation of query method with USER LOCATION ******
            {
                CMD = requestType + ":" + "NULLVALUE" ;
            }
        else                                   // ****** implementation of query method with SPECIFIC LOCATION ******
            {
                CMD = requestType + ":" + X + ":" + Y ;
            }

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(CMD);  // ****** wireless communication between app and raspberry pi (Space Broker) ******

          try
            {
                 Thread.sleep(5000);
            }
          catch (InterruptedException e)

            {
                 e.printStackTrace();
            }


        queriedValue = wirelesscommunication.receivedString;


        return queriedValue;

    }

    @Override
    public void modify(String requestType, String X,String Y,String Value)  // ****** implementation of modify method ******
    {

        if (X == "NULL" && Y == "NULL")        // ****** implementation of modify method with USER LOCATION ******
            {
                CMD = requestType + ":" + Value;
            }
        else                                   // ****** implementation of modify method with SPECIFIC LOCATION ******
            {
                CMD = requestType + ":" + X + ":" + Y + ":" + Value;
            }

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(CMD);  // ****** wireless communication between app and raspberry pi (Space Broker) ******

    }


    @Override
    public void maintain(String requestType, String X,String Y,String Value)  // implementation of maintain method
    {
        if (X == "NULL" && Y == "NULL")      // ****** implementation of maintain method with USER LOCATION ******
            {
                CMD = requestType + ":" + Value;
            }

        else                                 // ****** implementation of maintain method with SPECIFIC LOCATION ******
            {
                CMD = requestType + ":" + X + ":" + Y + ":" + Value;
            }

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(CMD);  // ****** wireless communication between app and raspberry pi (Space Broker) ******

    }


}

