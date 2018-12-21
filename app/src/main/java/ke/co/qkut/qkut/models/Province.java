package ke.co.qkut.qkut.models;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class Province {

    int Id;
    int country_id;
    String long_name;
    String short_name;
 
    //Constructors
    //--------------------------------------------------------------------------------------------
    public Province(){
        setProvince(0,0,"","");
    }
    //--------------------------------------------------------------------------------------------
    public Province(int id, int country_id, String long_name, String short_name){
        setProvince(id, country_id, long_name, short_name);
    }

    //--------------------------------------------------------------------------------------------
    public Province(String long_name, String short_name){
        setProvince(0,0, short_name, long_name);
    }

    //--------------------------------------------------------------------------------------------
    public Province(JSONObject jsonProvince) throws JSONException {
        try {
            setProvince(
                    jsonProvince.getInt("id"),
                    jsonProvince.getInt("country_id"),
                    jsonProvince.getString("long_name"),
                    jsonProvince.getString("short_name")
            );
        } catch (JSONException e) {
             throw e;
        }
    }

    //Setters
    //--------------------------------------------------------------------------------------------
    public void setId(int id){
        this.Id = id;
    }

    //--------------------------------------------------------------------------------------------
    public void setLongName(String long_name){
        this.long_name = long_name;
    }

    //--------------------------------------------------------------------------------------------
    public void setShortName(String shortName){
        this.short_name = shortName;
    }

    //--------------------------------------------------------------------------------------------
    public void setCountryId(int countryId){
        this.country_id = countryId;
    }

    //--------------------------------------------------------------------------------------------
    public void setProvince(int id, int county_id, String long_name, String short_name){
        setId(id);
        setLongName(long_name);
        setCountryId(county_id);
        setShortName(short_name);
    }

    //getters
    //---------------------------------------------------------------------------------------------
    public int getId(){
        return this.Id;
    }

    public String getLongName(){
        return this.long_name;
    }

    public int getCountryId(){
        return this.country_id;
    }

    public String getShortName(){
        return this.short_name;
    }

    public JSONObject getJsonObject() {

        JSONObject json = new JSONObject();

        try {
            json.put("id", this.getId());
            json.put("country_id", this.getCountryId());
            json.put("long_name", this.getLongName());
            json.put("short_name", this.getShortName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


}
