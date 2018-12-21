package ke.co.qkut.qkut.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceType {

    private int id;
    private int code;
    private String name;

    //--------------------------------------------------------------------
    public ServiceType(){

        setServiceType(0, 1, "");
    }

    //--------------------------------------------------------------------
    public ServiceType(int id, int code, String name){

        setServiceType(id, code, name);
    }

    //--------------------------------------------------------------------------------------------
    public ServiceType(JSONObject jsonService) throws JSONException {

        try {
            setServiceType(
                    jsonService.getInt("id"),
                    jsonService.getInt("code"),
                    jsonService.getString("name")
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
    public void setCode(int code){
        this.code = code;
    }


    //--------------------------------------------------------------------
    public void setName(String name){
        this.name = name;
    }



    //--------------------------------------------------------------------
    public void setServiceType(int id, int code, String name){

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
    public int getCode(){
        return this.code;
    }

    //---------------------------------------------------------------------
    public String getName(){
        return this.name;
    }

    //---------------------------------------------------------------------
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

    //compare if code is open
    //-----------------------------------------------------------------------
    public boolean isOpen(){
        return 1 == this.getCode();
    }

    //compare if code is open
    //-----------------------------------------------------------------------
    public boolean isInviteOnly(){
        return 2 == this.getCode();
    }
}
