package com.example.japneets_3175finalpractice;

import java.time.LocalDate;

public class Concert {

    int concertId;
    int ConcertImage;
    LocalDate ConcertDate;
    String ConcertName;
    double price;
    int numberOfTickets;
    double subTotal;

    public Concert(int concertId, int concertImage, LocalDate concertDate, String concertName, double price) {
        this.concertId = concertId;
        ConcertImage = concertImage;
        ConcertDate = concertDate;
        ConcertName = concertName;
        this.price = price;
    }

    public Concert( String concertName, double price, int numberOfTickets, double subTotal) {
        this.ConcertName = concertName;
        this.price = price;
        this.numberOfTickets = numberOfTickets;
        this.subTotal = subTotal;
    }

    public int getConcertId() {
        return concertId;
    }

    public void setConcertId(int concertId) {
        this.concertId = concertId;
    }

    public int getConcertImage() {
        return ConcertImage;
    }

    public void setConcertImage(int concertImage) {
        ConcertImage = concertImage;
    }

    public LocalDate getConcertDate() {
        return ConcertDate;
    }

    public void setConcertDate(LocalDate concertDate) {
        ConcertDate = concertDate;
    }

    public String getConcertName() {
        return ConcertName;
    }

    public void setConcertName(String concertName) {
        ConcertName = concertName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
