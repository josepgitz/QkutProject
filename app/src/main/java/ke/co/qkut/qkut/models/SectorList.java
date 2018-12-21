package ke.co.qkut.qkut.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class SectorList {

    private ArrayList<Sector> SectorList;
    //--------------------------------------------------------------------
    public SectorList(){
        SectorList = new ArrayList<Sector>();
    }
    //--------------------------------------------------------------------
    public SectorList(ArrayList<Sector> SectorList){
        setSectorList(SectorList);
    }
    //--------------------------------------------------------------------------------------------
    public SectorList(JSONArray jsonSectorList) throws JSONException {
        SectorList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonSectorList.length(); i++ ) {
                SectorList.add( new Sector(jsonSectorList.getJSONObject(i)));
            }
        }
        catch (JSONException e){
                throw e;
        }
    }
    //--------------------------------------------------------------------
    public void setSectorList(ArrayList<Sector> SectorList){
        this.SectorList = SectorList;
    }
    //getters
    //---------------------------------------------------------------------
    public ArrayList<Sector> getSectorList(){
       return this.SectorList;
    }
    //---------------------------------------------------------------------
    public JSONObject getJsonObject(){
        JSONObject json = new JSONObject();
        return json;
    }
}
