package my.awesome.tooros;

public class ModelCity {
    String City;

    public ModelCity(String city, String city_id) {
        City = city;
        this.city_id = city_id;
    }

    String city_id;

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
}
