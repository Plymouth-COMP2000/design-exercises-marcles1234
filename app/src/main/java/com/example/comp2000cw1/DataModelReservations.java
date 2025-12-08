package com.example.comp2000cw1;

public class DataModelReservations {
    String reservationName, reservationDate, reservationTime, reservationGuests;

    public DataModelReservations(String reservationName, String reservationDate, String reservationTime, String reservationGuests) {
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.reservationGuests = reservationGuests;
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
