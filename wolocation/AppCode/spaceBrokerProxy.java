package com.example.illuminationmodify;


interface characteristicsInterface
{
    String query(String ChoiceId, String X,String Y  );
    void modify(String ChoiceId, String X,String Y,String Value);
    void maintain(String ChoiceId, String X,String Y,String Value);
}

class Characteristics   implements characteristicsInterface
{

    public static String CMD;        // CMD is the string or message which will be sent  to the space broker from the application which consists
                                     // 1. requestype and/or
                                     // 2. location and/or
                                     // 3. value and/or
    String queriedValue= "";


    @Override
    public String query(String RequestId, String X,String Y )  // implementation of query method
    {



        if (X == "NULL" && Y == "NULL")        // implementation of query method with USER LOCATION
            {
                CMD = RequestId + ":" + "NULLVALUE" ;
            }
        else                                   // implementation of query method with SPECIFIC LOCATION
            {
                CMD = RequestId + ":" + X + ":" + Y ;
            }

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(CMD);  //wireless communication between app and raspberry pi (Space Broker)

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
    public void modify(String RequestId, String X,String Y,String Value)  // implementation of modify method
    {

        if (X == "NULL" && Y == "NULL")        // implementation of modify method with USER LOCATION
            {
                CMD = RequestId + ":" + Value;
            }
        else                                   // implementation of modify method with SPECIFIC LOCATION
            {
                CMD = RequestId + ":" + X + ":" + Y + ":" + Value;
            }

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(CMD);  //wireless communication between app and raspberry pi (Space Broker)

    }


    @Override
    public void maintain(String RequestId, String X,String Y,String Value)  // implementation of maintain method
    {
        if (X == "NULL" && Y == "NULL")      // implementation of maintain method with USER LOCATION
            {
                CMD = RequestId + ":" + Value;
            }

        else                                 // implementation of maintain method with SPECIFIC LOCATION
            {
                CMD = RequestId + ":" + X + ":" + Y + ":" + Value;
            }

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(CMD);  //wireless communication between app and raspberry pi (Space Broker)

    }


}

