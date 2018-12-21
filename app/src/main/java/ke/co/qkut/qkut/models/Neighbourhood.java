package ke.co.qkut.qkut.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Neighbourhood {

    int id;
    int town_id;
    String place_id;
    int lat;
    int lng;
    String name;

    //Constructors
    //--------------------------------------------------------------------------------------------
    public Neighbourhood(){
        setNeighbourhood(0,0,"",0,0,"");
    }
    //--------------------------------------------------------------------------------------------
    
    public Neighbourhood(int id, int town_id, String place_id, int lat, int lng, String name){
        setNeighbourhood(id, town_id, place_id, lat, lng, name);
    }
 
    //--------------------------------------------------------------------------------------------
    public Neighbourhood(JSONObject jsonNeighbourhood) throws JSONException {
        try {
            setNeighbourhood(
                    jsonNeighbourhood.getInt("id"),
                    jsonNeighbourhood.getInt("town_id"),
                    jsonNeighbourhood.getString("place_id"),
                    jsonNeighbourhood.getInt("lat"),
                    jsonNeighbourhood.getInt("lng"),
                    jsonNeighbourhood.getString("name")
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
    public void setNeighbourhoodId(int town_id){
        this.town_id = town_id;
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
    public void setNeighbourhood(int id, int town_id, String place_id, int lat, int lng, String name){
        setId(id);
        setNeighbourhoodId(town_id);
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
        return this.town_id;
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
            json.put("town_id", this.getProviceId());
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
