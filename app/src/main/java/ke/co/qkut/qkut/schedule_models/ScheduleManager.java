package ke.co.qkut.qkut.schedule_models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.List;

import ke.co.qkut.qkut.Modeler.Message;
import ke.co.qkut.qkut.Modeler.Organisation;

public class ScheduleManager {

    @Expose
    private String status;
    @Expose
    private String reason;
  //  private @Expose ScheduleData data;
    private @Expose List<ScheduleInfo>  data;

    public List<ScheduleInfo> getData() {
        return data;
    }

    public void setData(List<ScheduleInfo> data) {
        this.data=data;
    }
    //    public void setScheduleData(ScheduleData data) {
//        this.data = data;
//    }

    public String  getScheduleManager(){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Log.d("scheduleddata",gson.toJson(this));
        return gson.toJson(this);
    }
    public static ScheduleManager setScheduleManager(String message){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(message,ScheduleManager.class);
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}