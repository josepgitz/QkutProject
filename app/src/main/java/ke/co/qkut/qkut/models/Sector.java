package ke.co.qkut.qkut.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Sector {

    private int id;
    private String code;
    private String name;
    public ArrayList<Sector> SectorList;
    //------------------------------initialise constractor--------------------------------------
    public Sector(){

        setSector(0, "", "");
    }

    //--------------------------------------------------------------------
    public Sector(int id, String code, String name){

        setSector(id, code, name);
    }

    //----------------------------------------initialise Json Object----------------------------------------------------
    public Sector(JSONObject jsonSector) throws JSONException {

        try {
            setSector(
                    jsonSector.getInt("id"),
                    jsonSector.getString("code"),
                    jsonSector.getString("name")
            );
        } catch (JSONException e) {
            throw e;
        }
    }

    //setters
    //--------------------------------------------------------------------
    public void setId(int id){
        this.id = id;
    }

    //--------------------------------------------------------------------
    public void setCode(String code){
        this.code = code;
    }


    //--------------------------------------------------------------------
    public void setName(String name){
        this.name = name;
    }



    //--------------------------------------------------------------------
    public void setSector(int id, String code, String name){

        setId(id);
        setCode(code);
        setName(name);
    }

    //getters
    //---------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------
    public String getCode(){
        return this.code;
    }

    //---------------------------------------------------------------------
    public String getName(){
        return this.name;
    }

    //-----------------------------------Assign Object Values----------------------------------
    public JSONObject getJsonObject(){

        JSONObject json = new JSONObject();
        try {
            json.put("id", this.getId());
            json.put("code", this.getCode());
            json.put("name", this.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

}
