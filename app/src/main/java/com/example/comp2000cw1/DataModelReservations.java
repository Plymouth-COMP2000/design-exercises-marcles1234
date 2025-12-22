package com.example.comp2000cw1;

public class DataModelReservations {

    int reservationId;
    String reservationName, reservationDate, reservationTime, reservationGuests;

    //RESERVATIONS DATAMODEL (NAME, DATE, TIME, GUESTS)
    public DataModelReservations(String reservationName, String reservationDate, String reservationTime, String reservationGuests) {
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationGuests = reservationGuests;
    }

    //RESERVATIONS DATAMODEL (DATE, TIME, GUESTS)
    public DataModelReservations(String reservationDate, String reservationTime, String reservationGuests) {
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationGuests = reservationGuests;
    }

    //RESERVATIONS DATAMODEL (ID, NAME, DATE, TIME, GUESTS)
    public DataModelReservations(int reservationId, String reservationName, String reservationDate, String reservationTime, String reservationGuests) {
        this.reservationId = reservationId;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationGuests = reservationGuests;
    }

    public int getReservationId() {
        return reservationId;
    }
    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationName() {
        return reservationName;
    }
    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public String getReservationDate() {
        return reservationDate;
    }
    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationTime() {
        return reservationTime;
    }
    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getReservationGuests() {
        return reservationGuests;
    }
    public void setReservationGuests(String reservationGuests) {
        this.reservationGuests = reservationGuests;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "reservationName='" + reservationName + '\'' +
                ", reservationDate='" + reservationDate + '\'' +
                ", reservationTime='" + reservationTime + '\'' +
                ", reservationGuests='" + reservationGuests +
                '}';
    }
}
