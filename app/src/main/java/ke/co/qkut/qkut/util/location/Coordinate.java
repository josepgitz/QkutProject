package ke.co.qkut.qkut.util.location;

public class Coordinate {
    double latitude;
    double longitude;

    @Override
    public String toString() {
        return "Latitude :"+latitude+" Longitude :"+longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
