package my.awesome.tooros;



public class Guidlines_model {
int image;
String imgurl;

    public Guidlines_model() {
    }

    public Guidlines_model(int image) {
        this.image = image;
    }

    public Guidlines_model(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
