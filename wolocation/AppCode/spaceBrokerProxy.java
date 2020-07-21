package com.example.illuminationmodify;


interface characteristicsInterface
{
    String query(String ChoiceId, String X,String Y  ); // return value of  that location
    void modify(String ChoiceId, String X,String Y,String Value); // modify value of that location
    void maintain(String ChoiceId, String X,String Y,String Value); // maintain a value of the current location
}

class Characteristics   implements characteristicsInterface
{

    public static String CMD;        // CMD is the string or message which will be sent  to the space broker from the application,
    String queriedValue= "";


    @Override
    public String query(String RequestId, String X,String Y )
    {



        if (X == "NULL" && Y == "NULL")
            {
                CMD = RequestId + ":" + "NULLVALUE" ;
            }
        else
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
    public void modify(String RequestId, String X,String Y,String Value)
    {

        if (X == "NULL" && Y == "NULL")
            {
                CMD = RequestId + ":" + Value;
            }
        else
            {
                CMD = RequestId + ":" + X + ":" + Y + ":" + Value;
            }

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(CMD);  //wireless communication between app and raspberry pi (Space Broker)

    }


    @Override
    public void maintain(String RequestId, String X,String Y,String Value)
    {
        if (X == "NULL" && Y == "NULL")
            {
                CMD = RequestId + ":" + Value;
            }

        else
            {
                CMD = RequestId + ":" + X + ":" + Y + ":" + Value;
            }

        wirelessCommunication wirelesscommunication = new wirelessCommunication();
        wirelesscommunication.execute(CMD);  //wireless communication between app and raspberry pi (Space Broker)

    }


}

