package ke.co.qkut.qkut.models;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

public class Country {

    int Id;
    String Name;
    String Code;
    String Dial;
    Bitmap flag = null;

    //Constructors
    //--------------------------------------------------------------------------------------------
    public Country(){
        setCountry(0,"","","");
    }
    //--------------------------------------------------------------------------------------------
    public Country(int id, String name, String code, String dial){
        setCountry(id,name,code,dial);
    }

    //--------------------------------------------------------------------------------------------
    public Country(String name, String code, String dial){
        setCountry(0,name,code,dial);
    }

    //--------------------------------------------------------------------------------------------
    public Country(String name, String code){
        setCountry(0,name,code,"");
    }

    //--------------------------------------------------------------------------------------------
    public Country(JSONObject jsonCountry) throws JSONException {
        try {
            setCountry(
                    jsonCountry.getInt("id"),
                    jsonCountry.getString("name"),
                    jsonCountry.getString("code"),
                    jsonCountry.getString("dial_code")
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
    public void setName(String name){
        this.Name = name;
    }

    //--------------------------------------------------------------------------------------------
    public void setCode(String code){
        this.Code = code;
    }

    //--------------------------------------------------------------------------------------------
    public void setDial(String dial){
        this.Dial = dial;
    }

    //--------------------------------------------------------------------------------------------
    public void setBitmap(Bitmap flag){
        this.flag = flag;
    }
    //--------------------------------------------------------------------------------------------
    public void setCountry(int id, String name, String code, String dial){
        setId(id);
        setName(name);
        setCode(code);
        setDial(dial);
    }

    //getters
    //---------------------------------------------------------------------------------------------
    public int getId(){
        return this.Id;
    }

    public String getName(){
        return this.Name;
    }

    public String getCode(){
        return this.Code;
    }

    public String getDial(){
        return this.Dial;
    }

    public Bitmap getBitmap(){
        return this.flag;
    }

    public JSONObject getJsonObject() {

        JSONObject json = new JSONObject();
        try {
            json.put("id", this.getId());
            json.put("name", this.getName());
            json.put("code", this.getCode());
            json.put("dial", this.getDial());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }


}
