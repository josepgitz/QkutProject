package ke.co.qkut.qkut.models;


public class Visit {

    private int id;
    private BusinessInfo businessInfo;
    private Service service;
    private String scheduledTime;

    //--------------------------------------------------------------------
    public Visit(BusinessInfo businessInfo, Service service){

        setVisit(0, businessInfo, service, "");

    }
    //--------------------------------------------------------------------
    public Visit(BusinessInfo businessInfo, Service service, String scheduledTime){

        setVisit(0, businessInfo, service,scheduledTime);

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
//__----------------------------------------------

    public void setscheduledTime(String scheduledTime){
        this.scheduledTime = scheduledTime;
    }
    //--------------------------------------------------------------------
    public void setVisit(int id, BusinessInfo businessInfo, Service service, String scheduledTime){
        setId(id);
        setBusinessAccount(businessInfo);
        setService(service);
        setscheduledTime(scheduledTime);
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

    ////=-----------------------------------------------------------------
    public String getScheduledTime(){
        return this.scheduledTime;
    }

}
