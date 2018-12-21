package ke.co.qkut.qkut.models;
public class ScheduledQueue {
    private int id;
    private String scheduledTime;
    private Department department;
    private Service service;
    //--------------------------------------------------------------------
    public ScheduledQueue(){
    }
    //--------------------------------------------------------------------
    public ScheduledQueue(Department department, Service service){
        setQueue(0, department, service, "");
    }

    //--------------------------------------------------------------------
    public ScheduledQueue(Department department, Service service,String scheduledTime){

        setQueue(0, department, service, scheduledTime);

    }

    //setters
    //--------------------------------------------------------------------
    public void setId(int id){
        this.id = id;
    }

    //--------------------------------------------------------------------
    public void setDepartment(Department department){
        this.department = department;
    }

    //--------------------------------------------------------------------
    public void setService(Service service){
        this.service = service;
    }

    //--------------------------------------------------------------------
    public void setScheduledTime(String scheduledTime){
        this.scheduledTime = scheduledTime;
    }

    //-----------------------------------------------------------------------
    public void setQueue(int id, Department department, Service service, String scheduledTime){
        setId(id);
        setDepartment(department);
        setService(service);
        setScheduledTime(scheduledTime);
    }

    //getters
    //---------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------
    public Department getDepartment(){
        return this.department;
    }

    //---------------------------------------------------------------------
    public Service getService(){
        return this.service;
    }
    //---------------------------------------------------------------------
    public String getScheduledTime(){
        return this.scheduledTime;
    }

}
