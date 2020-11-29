package my.awesome.tooros;

import android.widget.TextView;

public class CarBookingModel {
    int carimage;
    String carname;
    String fueltype;
    String price;
    String seat;
    String geartype;
    String baggage;
    String status;

    public CarBookingModel(int carimage, String carname, String fueltype, String price, String seat, String geartype, String baggage, String status) {
        this.carimage = carimage;
        this.carname = carname;
        this.fueltype = fueltype;
        this.price = price;
        this.seat = seat;
        this.geartype = geartype;
        this.baggage = baggage;
        this.status = status;
    }

    public int getCarimage() {
        return carimage;
    }

    public void setCarimage(int carimage) {
        this.carimage = carimage;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getFueltype() {
        return fueltype;
    }

    public void setFueltype(String fueltype) {
        this.fueltype = fueltype;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getGeartype() {
        return geartype;
    }

    public void setGeartype(String geartype) {
        this.geartype = geartype;
    }

    public String getBaggage() {
        return baggage;
    }

    public void setBaggage(String baggage) {
        this.baggage = baggage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
