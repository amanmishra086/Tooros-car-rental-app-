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
    String weekendcost;
    String security;
    String car_id;

    public CarBookingModel(int carimage, String carname, String fueltype, String price, String seat, String geartype, String baggage, String status, String weekendcost, String security,String car_id) {
        this.carimage = carimage;
        this.carname = carname;
        this.fueltype = fueltype;
        this.price = price;
        this.seat = seat;
        this.geartype = geartype;
        this.baggage = baggage;
        this.status = status;
        this.weekendcost = weekendcost;
        this.security = security;
        this.car_id=car_id;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
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

    public String getWeekendcost() {
        return weekendcost;
    }

    public void setWeekendcost(String weekendcost) {
        this.weekendcost = weekendcost;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }
}
