package my.awesome.tooros;

public class Model_bookinghistory {

    String startdate, enddate,carname,price , booking_id;

    public Model_bookinghistory(String startdate, String enddate, String carname, String price, String booking_id ) {
        this.startdate = startdate;
        this.enddate = enddate;
        this.carname = carname;
        this.price = price;
        this.booking_id=booking_id;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getCarname() {
        return carname;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
