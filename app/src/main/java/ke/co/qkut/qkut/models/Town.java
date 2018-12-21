package ke.co.qkut.qkut.models;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class Town {

    int id;
    int province_id;
    String place_id;
    int lat;
    int lng;
    String name;

    //Constructors
    //--------------------------------------------------------------------------------------------
    public Town(){
        setTown(0,0,"",0,0,"");
    }
    //--------------------------------------------------------------------------------------------

    public Town(int id, int province_id, String place_id, int lat, int lng, String name){
        setTown(id, province_id, place_id, lat, lng, name);
    }

    //--------------------------------------------------------------------------------------------
    public Town(JSONObject jsonTown) throws JSONException {
        try {
            setTown(
                    jsonTown.getInt("id"),
                    jsonTown.getInt("province_id"),
                    jsonTown.getString("place_id"),
                    jsonTown.getInt("lat"),
                    jsonTown.getInt("lng"),
                    jsonTown.getString("name")
            );
        } catch (JSONException e) {
             throw e;
        }
    }

    //Setters
    //--------------------------------------------------------------------------------------------
    public void setId(int id){
        this.id = id;
    }

    //--------------------------------------------------------------------------------------------
    public void setProvinceId(int province_id){
        this.province_id = province_id;
    }

    //--------------------------------------------------------------------------------------------
    public void setPlaceId(String place_id){
        this.place_id = place_id;
    }

    //--------------------------------------------------------------------------------------------
    public void setLat(int lat){
        this.lat = lat;
    }

    //--------------------------------------------------------------------------------------------
    public void setLng(int lng){
        this.lng = lng;
    }

    //--------------------------------------------------------------------------------------------
    public void setName(String name){
        this.name = name;
    }

    //--------------------------------------------------------------------------------------------
    public void setTown(int id, int province_id, String place_id, int lat, int lng, String name){
        setId(id);
        setProvinceId(province_id);
        setPlaceId(place_id);
        setLat(lat);
        setLng(lng);
        setName(name);
    }

    //getters
    //---------------------------------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------------------------------
    public int getProviceId(){
        return this.province_id;
    }

    //---------------------------------------------------------------------------------------------
    public String getPlaceId(){
        return this.place_id;
    }

    //---------------------------------------------------------------------------------------------
    public int getLat(){
        return this.lat;
    }

    //---------------------------------------------------------------------------------------------
    public int getLng(){
        return this.lng;
    }

    //---------------------------------------------------------------------------------------------
    public String getName(){
        return this.name;
    }

    public JSONObject getJsonObject() {

        JSONObject json = new JSONObject();
        try {
            json.put("id", this.getId());
            json.put("province_id", this.getProviceId());
            json.put("place_id", this.getPlaceId());
            json.put("lat", this.getLat());
            json.put("lng", this.getLng());
            json.put("name", this.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


}
