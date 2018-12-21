package ke.co.qkut.qkut.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class ServiceList {

    private ArrayList<Service> serviceList;

    //--------------------------------------------------------------------
    public ServiceList(){
        serviceList = new ArrayList<Service>();
    }

    //--------------------------------------------------------------------
    public ServiceList(ArrayList<Service> serviceList){
        setServiceList(serviceList);
    }

    //--------------------------------------------------------------------------------------------
    public ServiceList(JSONArray jsonServiceList) throws JSONException {
        
        serviceList = new ArrayList<>();

        try {
            for (int i = 0; i < jsonServiceList.length(); i++ ) {
                serviceList.add( new Service(jsonServiceList.getJSONObject(i)));
            }
        }
        catch (JSONException e){
                throw e;
        }
    }
    public ServiceList(JSONObject jsonServiceList) throws JSONException {
        try {
        serviceList = new ArrayList<>();
        serviceList.add( new Service(jsonServiceList));
    }
        catch (JSONException e){
        throw e;
    }
    }

    //--------------------------------------------------------------------
    public void setServiceList(ArrayList<Service> serviceList){
        this.serviceList = serviceList;
    }

    //getters
    //---------------------------------------------------------------------
    public ArrayList<Service> getServiceList(){
       return this.serviceList;
    }

    //---------------------------------------------------------------------
    public JSONObject getJsonObject(){

        JSONObject json = new JSONObject();


        return json;
    }
}
