package ke.co.qkut.qkut.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlertInfo {
   private  int id;
    private String companyalertname;
    private String alertservicedepartment;
    private String alertmessage;
    private String alerttime;
    private String alertdate;
    private DepartmentList departmentList;

    //variables
    public ArrayList<Service> serviceList;

    //--------------------------------------------------------------------
    public AlertInfo(){
        setAlertInfo(0, "", "", "", "", "",null);
    }
    //----------------------setAlertInfo----------------------------------------------
    public AlertInfo(int id, String companyalertname, String alertservicedepartment,  String alertmessage,
                     String alerttime, String alertdate, DepartmentList departmentList){

        setAlertInfo(id, companyalertname, alertservicedepartment, alertmessage, alerttime, alertdate,departmentList);

    }

    //--------------------------------------------------------------------------------------------
    public AlertInfo(JSONObject jsonBusinessInfo) throws JSONException {
        try {

            setAlertInfo(
                jsonBusinessInfo.getInt("id"),
                jsonBusinessInfo.getString("companyalertname"),
                jsonBusinessInfo.getString("alertservicedepartment"),
                jsonBusinessInfo.getString("alertmessage"),
                jsonBusinessInfo.getString("alerttime"),
                jsonBusinessInfo.getString("alertdate"),
                new DepartmentList(jsonBusinessInfo.getJSONArray("departmentList"))
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
    public void setcompanyalertname(String companyalertname){
        this.companyalertname = companyalertname;
    }

    //--------------------------------------------------------------------
    public void setalertservicedepartment(String alertservicedepartment){
        this.alertservicedepartment = alertservicedepartment;
    }

    //--------------------------------------------------------------------
    public void setalertmessage(String alertmessage){
        this.alertmessage = alertmessage;
    }

    //--------------------------------------------------------------------
    public void setalerttime(String alerttime){
        this.alerttime = alerttime;
    }

    //--------------------------------------------------------------------
    public void setalertdate(String alertdate){
        this.alertdate = alertdate;
    }

    //--------------------------------------------------------------------
    public void setDepartmentList(DepartmentList departmentList){
        this.departmentList = departmentList;
    }
    //--------------------------------------------------------------------
    public void setAlertInfo(int id, String companyalertname, String alertservicedepartment,  String alertmessage,
                             String alerttime, String alertdate, DepartmentList departmentList){
        setId(id);
        setcompanyalertname(companyalertname);
        setalertservicedepartment(alertservicedepartment);
        setalertmessage(alertmessage);
        setalerttime(alerttime);
        setalertdate(alertdate);
        setDepartmentList(departmentList);
    }

    //getters
    //---------------------------------------------------------------------
    public int getId(){
        return this.id;
    }

    //---------------------------------------------------------------------
    public String getcompanyalertname(){
        return this.companyalertname;
    }

    //--------------------------------------------------------------------
    public String getalertservicedepartment(){
        return this.alertservicedepartment;
    }

    //--------------------------------------------------------------------
    public String getalertmessage(){ return this.alertmessage; }

    //--------------------------------------------------------------------
    public String getalerttime(){
        return this.alerttime;    }

    //--------------------------------------------------------------------
    public String getalertdate(){ return this.alertdate; }

    //--------------------------------------------------------------------
    public DepartmentList getDepartmentList(){
        return this.departmentList;
    }
    //---------------------------------------------------------------------
    public JSONObject getJsonObject(){

        JSONObject json = new JSONObject();
        try {
            json.put("id", this.getId());
            json.put("companyalertname", this.getcompanyalertname());
            json.put("alertservicedepartment", this.getalertservicedepartment());
            json.put("alertmessage", this.getalertmessage());
            json.put("alerttime", this.getalerttime());
            json.put("alertdate", this.getalertdate());
            json.put("departmentlist", this.getDepartmentList());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json;
    }
}
