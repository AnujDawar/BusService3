package com.example.anujdawar.busservice3;

class product
{
    private String myBusNumber, myBusType, myBusCompany, myBusFare, myBusSource, myBusDestination;
    private int ID;

    product(String myBusNumber, String myBusType, String myBusCompany, String myBusFare, String myBusSource, String myBusDestination)
    {
        this.myBusNumber = myBusNumber;
        this.myBusType = myBusType;
        this.myBusCompany = myBusCompany;
        this.myBusFare = myBusFare;
        this.myBusSource = myBusSource;
        this.myBusDestination = myBusDestination;
        this.ID = ID;
    }

    int getID() {
        return ID;
    }

    String getMyBusType() {
        return myBusType;
    }

    String getMyBusCompany() {
        return myBusCompany;
    }

    String getMyBusFare() {
        return myBusFare;
    }

    String getMyBusSource() {
        return myBusSource;
    }

    String getMyBusDestination() {
        return myBusDestination;
    }

    String getMyBusNumber() {
        return myBusNumber;
    }
}
