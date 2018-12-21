package ke.co.qkut.qkut.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Department {

    private int id;
    private int business_account_id;
    private String name;
    private String description;
    private ServiceList serviceList;

    //--------------------------------------------------------------------
    public Department(){

    }

    //--------------------------------------------------------------------
    public Department(int id, int business_account_id, String name, String description, ServiceList serviceList){

        setDepartment(id, business_account_id, name, description, serviceList);

    }

    //--------------------------------------------------------------------------------------------
    public Department(JSONObject jsonDepartment) throws JSONException {

        try {
            setDepartment(
                    jsonDepartment.getInt("id"),
                    jsonDepartment.getInt("business_account_id"),
                    jsonDepartment.getString("name"),
                    jsonDepartment.getString("description"),
                    new ServiceList(jsonDepartment.getJSONArray("services"))
            );
        } catch (JSONException e) {
            throw e;
        }
    }
    public Department(JSONObject jsonDepartment,String message) throws JSONException {

        try {
            setDepartment(
                    jsonDepartment.getInt("id"),
                    jsonDepartment.getInt("business_account_id"),
                    jsonDepartment.getString("name"),
                    jsonDepartment.getString("description"),
                    new ServiceList(jsonDepartment.getJSONArray("services"))
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
    public void setName(String name){
        this.name = name;
    }

    //--------------------------------------------------------------------
    public void setBusinessAccountId(int business_account_id){
        this.business_account_id = business_account_id;
    }

    //--------------------------------------------------------------------
    public void setDescription(String description){
        this.description = description;
    }

    //--------------------------------------------------------------------
    public void setServiceList(ServiceList serviceList){
        this.serviceList = serviceList;
    }

    //--------------------------------------------------------------------
    public void setDepartment(int id, int business_account_id, String name, String description, ServiceList serviceList){
        setId(id);
        setBusinessAccountId(business_account_id);
        setName(name);
        setDescription(description);
        setServiceList(serviceList);
    }

    //getters
    //---------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------
    public int getBusinessAccountId(){
        return this.business_account_id;
    }

    //---------------------------------------------------------------------
    public String getName(){
        return this.name;
    }

    //---------------------------------------------------------------------
    public String getDescription(){
        return this.description;
    }

    //---------------------------------------------------------------------
    public ServiceList getServiceList(){
        return this.serviceList;
    }

    //---------------------------------------------------------------------
    public JSONObject getJsonObject(){

        JSONObject json = new JSONObject();
        try {
            json.put("id", this.getId());
            json.put("business_account_id", this.getBusinessAccountId());
            json.put("name", this.getName());
            json.put("description", this.getDescription());
            json.put("serviceList", this.getServiceList());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
