package ke.co.qkut.qkut.models;

import android.support.v7.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BusinessInfo {

    private int id;
    private String name;
    private String description;
    private Double distance;
    private int sub_industry_id;
    private String industry_description;
    private String address;
    private String building;
    private String country;
    private String sector;
    private String industry_group;
    private String industry;
    private String sub_industry;
    private Town town;
    private Neighbourhood neighbourhood;
    private DepartmentList departmentList;
    public ScheduledService scheduledService;


    //variables
    public ArrayList<Service> serviceList;
    //--------------------------------------------------------------------
    public BusinessInfo(){

        setBusiness(0, "", "", 0, "", "", "",
                "", "", "", "", 0.0, null, null, null);
    }

    //--------------------------------------------------------------------
    public BusinessInfo(int id, String name, String description, int sub_industry_id, String industry_description,
                        String address, String country, String sector, String industry_group, String industry,
                        String sub_industry, Double distance, Town town, Neighbourhood neighbourhood,  DepartmentList departmentList){

        setBusiness(id, name, description, sub_industry_id, industry_description, address, country,
                sector, industry_group, industry, sub_industry, distance, town, neighbourhood, departmentList);

    }

    //----------------------
  public  BusinessInfo(JSONObject jsonBusinessInfo,String string)throws JSONException{
        try {
         setBusiness( jsonBusinessInfo.getInt("id"),
                 jsonBusinessInfo.getString("name"),
                 jsonBusinessInfo.getString("description"),
                 jsonBusinessInfo.getInt("sub_industry_id"),
                 " ",
                 jsonBusinessInfo.getString("address"),
                 " ",
                 " ",
                 "",
                 " ",
                 "",
                 jsonBusinessInfo.getDouble("distane"),
                 new Town(),new Neighbourhood(),new DepartmentList(jsonBusinessInfo.getJSONArray("departmentList"),string));

                JSONArray queueScheduleJSON = jsonBusinessInfo.getJSONArray("schedule");
            if(queueScheduleJSON != null){
                scheduledService= new ScheduledService(queueScheduleJSON.getJSONObject(0),"");
            }

        }catch (JSONException e) {
            throw e;
        }
   }


    //--------------------------------------------------------------------------------------------
    public BusinessInfo(JSONObject jsonBusinessInfo) throws JSONException {
        try {

            setBusiness(
                jsonBusinessInfo.getInt("id"),
                jsonBusinessInfo.getString("name"),
                jsonBusinessInfo.getString("description"),
                jsonBusinessInfo.getInt("sub_industry_id"),
                jsonBusinessInfo.getString("industry_description"),
                jsonBusinessInfo.getString("address"),
                jsonBusinessInfo.getString("ctry"),
                jsonBusinessInfo.getString("sect"),
                jsonBusinessInfo.getString("ind_group"),
                jsonBusinessInfo.getString("ind"),
                jsonBusinessInfo.getString("sub_ind"),
                jsonBusinessInfo.getDouble("distance"),
                new Town(jsonBusinessInfo.getJSONObject("town")),
                new Neighbourhood(jsonBusinessInfo.getJSONObject("neighbourhood")),
                new DepartmentList(jsonBusinessInfo.getJSONArray("departmentList")));

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
    public void setDescription(String description){
        this.description = description;
    }

    //--------------------------------------------------------------------
    public void setIndustryId(int sub_industry_id){
        this.sub_industry_id = sub_industry_id;
    }

    //--------------------------------------------------------------------
    public void setIndustryDescription(String industry_description){
        this.industry_description = industry_description;
    }

    //--------------------------------------------------------------------
    public void setAddress(String address){
        this.address = address;
    }

    //--------------------------------------------------------------------
    public void setDistance(Double distance){
        this.distance = distance;
    }

    //--------------------------------------------------------------------
    public void setTown(Town town){
        this.town = town;
    }

    //--------------------------------------------------------------------
    public void setNeighbourhood(Neighbourhood neighbourhood){
        this.neighbourhood = neighbourhood;
    }

    //--------------------------------------------------------------------
    public void setDepartmentList(DepartmentList departmentList){
        this.departmentList = departmentList;
    }

    //--------------------------------------------------------------------
    public void setSector(String sector){
        this.sector = sector;
    }

    //--------------------------------------------------------------------
    public void setIndustryGroup(String industry_group){
        this.industry_group = industry_group;
    }

    //--------------------------------------------------------------------
    public void setIndustry(String industry){
        this.industry = industry;
    }

    //--------------------------------------------------------------------
    public void setSubIndustry(String sub_industry){
        this.sub_industry = sub_industry;
    }

    //--------------------------------------------------------------------
    public void setCountry(String country){
        this.country = country;
    }
    //---------------------------------------------------------------------


    //--------------------------------------------------------------------
    public void setBusiness(int id, String name, String description, int sub_industry_id, String industry_description,
                            String address, String country, String sector, String industry_group, String industry,
                             String sub_industry, Double distance, Town town, Neighbourhood neighbourhood,  DepartmentList departmentList ){
        setId(id);
        setName(name);
        setDescription(description);
        setIndustryId(sub_industry_id);
        setIndustryDescription(industry_description);
        setAddress(address);
        setCountry(country);
        setSector(sector);
        setIndustryGroup(industry_group);
        setIndustry(industry);
        setSubIndustry(sub_industry);
        setDistance(distance);
        setTown(town);
        setNeighbourhood(neighbourhood);
        setDepartmentList(departmentList);
    }

    //getters
    //---------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------
    public String getName(){
        return this.name;
    }

    //--------------------------------------------------------------------
    public String getDescription(){
        return this.description;
    }

    //--------------------------------------------------------------------
    public int getSubIndustryId(){ return this.sub_industry_id; }

    //--------------------------------------------------------------------
    public String getIndustryDescription(){
        return this.industry_description;    }

    //--------------------------------------------------------------------
    public String getAddress(){ return this.address; }


    //--------------------------------------------------------------------
    public Double getDistance(){ return this.distance; }

    //--------------------------------------------------------------------
    public Town getTown(){
        return this.town;
    }

    //--------------------------------------------------------------------
    public Neighbourhood getNeighbourhood(){
        return this.neighbourhood;
    }

    //--------------------------------------------------------------------
    public DepartmentList getDepartmentList(){
        return this.departmentList;
    }

    //--------------------------------------------------------------------
    public String getSector(){
        return this.sector;
    }

    //--------------------------------------------------------------------
    public String getIndustryGroup(){
        return this.industry_group;
    }

    //--------------------------------------------------------------------
    public String getIndustry(){
        return this.industry;
    }

    //--------------------------------------------------------------------
    public String getSubIndustry(){
        return this.sub_industry;
    }

    //--------------------------------------------------------------------
    public String getCountry(){
        return this.country;
    }

    //---------------------------------------------------------------------
    public JSONObject getJsonObject(){

        JSONObject json = new JSONObject();

        try {
            json.put("id", this.getId());
            json.put("name", this.getName());
            json.put("description", this.getDescription());
            json.put("sub_industry_id", this.getSubIndustryId());
            json.put("sub_industry", this.getIndustryDescription());
            json.put("address", this.getAddress());
            json.put("country", this.getCountry());
            json.put("sector", this.getSector());
            json.put("industry_group", this.getIndustryGroup());
            json.put("industry", this.getIndustry());
            json.put("sub_industry", this.getSubIndustry());
            json.put("town", this.getTown());
            json.put("neighbourhood", this.getNeighbourhood());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
