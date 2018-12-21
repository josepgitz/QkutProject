package ke.co.qkut.qkut.models;
public class Queue {
    private int id;
    private String scheduledTime;
    private BusinessInfo businessInfo;
    private Service service;
    //--------------------------------------------------------------------
    public Queue(){
    }
    //--------------------------------------------------------------------
    public Queue(BusinessInfo businessInfo, Service service){
        setQueue(0, businessInfo, service, "");
    }

    //--------------------------------------------------------------------
    public Queue(BusinessInfo businessInfo, Service service,String scheduledTime){

        setQueue(0, businessInfo, service, scheduledTime);

    }

    //setters
    //--------------------------------------------------------------------
    public void setId(int id){
        this.id = id;
    }

    //--------------------------------------------------------------------
    public void setBusinessAccount(BusinessInfo businessInfo){
        this.businessInfo = businessInfo;
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
    public void setQueue(int id, BusinessInfo businessInfo, Service service, String scheduledTime){
        setId(id);
        setBusinessAccount(businessInfo);
        setService(service);
        setScheduledTime(scheduledTime);
    }

    //getters
    //---------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------
    public BusinessInfo getBusinessAccount(){
        return this.businessInfo;
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
