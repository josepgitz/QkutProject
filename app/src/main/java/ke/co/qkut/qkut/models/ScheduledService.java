package ke.co.qkut.qkut.models;

import org.json.JSONException;
import org.json.JSONObject;



public class ScheduledService {

    private int id;
    private int department_id;
    private int queue_size;
    private int queue_speed;
    private int is_queued;
    private String scheduled_time;
    private int is_visited;
    private String name;
    private String description;
    private int status;
    private String departmentName;
    ServiceType serviceType;

    //--------------------------------------------------------------------
    public ScheduledService(){
        setDepartmentName("");
    }

    //--------------------------------------------------------------------
    public ScheduledService(int id, int department_id, String name, String description,
                            int queue_size, int queue_speed, int is_queued, int is_visited, ServiceType serviceType, String scheduled_time, int status){

        setService(id, department_id, name, description, queue_size, queue_speed, is_queued, is_visited,status,scheduled_time, serviceType);
        setDepartmentName("");
    }

    //--------------------------------------------------------------------------------------------
    public ScheduledService(JSONObject jsonService) throws JSONException {

        try {
            setService(
                    jsonService.getInt("id"),
                    jsonService.getInt("department_id"),
                    jsonService.getString("name"),
                    jsonService.getString("description"),
                    jsonService.getInt("queue_size"),
                    jsonService.getInt("queue_speed"),
                    jsonService.getInt("is_queued"),
                    jsonService.getInt("is_visited"),
                    jsonService.getInt("status"),
                    jsonService.getString("scheduled_time"),
                    new ServiceType(jsonService.getJSONObject("serviceType")));
        } catch (JSONException e) {
            throw e;
        }
    }
    public ScheduledService(JSONObject jsonService,String value) throws JSONException {

        try {
            setService(  jsonService.getInt("status"),
                    jsonService.getString("scheduled_time"));

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
    public void setDepartmentId(int department_id){
        this.department_id = department_id;
    }

    //--------------------------------------------------------------------
    public void setDescription(String description){
        this.description = description;
    }

    //--------------------------------------------------------------------
    public void setDepartmentName(String departmentName){
        this.departmentName = departmentName;
    }

     //--------------------------------------------------------------------
    public void setQueueSize(int queue_size){
        this.queue_size = queue_size;
    }

     //--------------------------------------------------------------------
    public void setQueueSpeed(int queue_speed){
        this.queue_speed = queue_speed;
    }

     //--------------------------------------------------------------------
    public void setIsQueued(int is_queued){
        this.is_queued = is_queued;
    }

     //--------------------------------------------------------------------
    public void setIsVisited(int is_visited){
        this.is_visited = is_visited;
    }

     //--------------------------------------------------------------------
    public void setServiceType(ServiceType serviceType){
        this.serviceType = serviceType;
    }
//------------------------------------------------------------------------------
    public void setScheduledtime(String scheduled_time){this.scheduled_time=scheduled_time;}
    //----------------------------------------------------------------------
    public void setstatus(int status){this.status=status;}
    //--------------------------------------------------------------------
    public void setService(int id, int department_id, String name, String description,
                     int queue_size, int queue_speed, int is_queued, int is_visited,int status,String scheduled_time, ServiceType serviceType  ){

        setId(id);
        setDepartmentId(department_id);
        setName(name);
        setDescription(description);
        setQueueSize(queue_size);
        setQueueSpeed(queue_speed);
        setIsQueued(is_queued);
        setIsVisited(is_visited);
        setServiceType(serviceType);
        setScheduledtime(scheduled_time);
        setstatus(status);

    }
    public void setService(int status,String scheduled_time){
        setstatus(status);
        setScheduledtime(scheduled_time);
    }

    //getters
    //---------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------
    public int getDepartmentId(){
        return this.department_id;
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
    public int getQueueSize(){
        return this.queue_size;
    }


    //---------------------------------------------------------------------
    public int getQueueSpeed(){
        return this.queue_speed;
    }


    //---------------------------------------------------------------------
    public int getIsQueued(){
        return this.is_queued;
    }

    //---------------------------------------------------------------------
    public int getIsVisited(){
        return this.is_visited;
    }
    //---------------------------------------------------------------------
    public int getstatus(){
        return this.status;
    }
    //---------------------------------------------------------------------
    public String getScheduledTime(){
        return this.scheduled_time;
    }
    //---------------------------------------------------------------------
    public String getDepartmentName(){
        return this.departmentName;
    }

    //---------------------------------------------------------------------
    public JSONObject getJsonObject(){

        JSONObject json = new JSONObject();
        try {
            json.put("id", this.getId());
            json.put("department_id", this.getDepartmentId());
            json.put("name", this.getName());
            json.put("description", this.getDescription());
            json.put("queue_size", this.getQueueSize());
            json.put("queue_speed", this.getQueueSpeed());
            json.put("is_queued", this.getIsQueued());
            json.put("is_visited", this.getIsVisited());
            json.put("status", this.getstatus());
            json.put("scheduled_time", this.getScheduledTime());
            json.put("serviceType", this.serviceType.getJsonObject());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }

    //compare if code is open
    //-----------------------------------------------------------------------
    public boolean isOpen(){
        return this.serviceType.isOpen();
    }

    //compare if code is open
    //-----------------------------------------------------------------------
    public boolean isInviteOnly(){
        return this.serviceType.isInviteOnly();
    }

}
