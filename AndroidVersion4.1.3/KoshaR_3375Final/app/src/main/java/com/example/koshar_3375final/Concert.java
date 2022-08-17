package com.example.koshar_3375final;

import java.time.LocalDate;

public class Concert {

    int ConcertImage;
    LocalDate ConcertDate;
    String ConcertName;
    double price;
    int numberOfTickets;

    public Concert(int concertImage, LocalDate concertDate, String concertName, double price) {
        ConcertImage = concertImage;
        ConcertDate = concertDate;
        ConcertName = concertName;
        this.price = price;
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

    public void setPrice(double price) { this.price = price; }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public Concert(int concertImage, LocalDate concertDate, String concertName, double price, int numberOfTickets) {
        ConcertImage = concertImage;
        ConcertDate = concertDate;
        ConcertName = concertName;
        this.price = price;
        this.numberOfTickets = numberOfTickets;
    }
}
