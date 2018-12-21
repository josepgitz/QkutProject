package ke.co.qkut.qkut.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.List;

import ke.co.qkut.qkut.schedule_models.ScheduleInfo;
import ke.co.qkut.qkut.schedule_models.ScheduleManager;

public class ConfirmManager {

    @Expose
    private String status;
    @Expose
    private String reason;
    //  private @Expose ScheduleData data;
    private @Expose List<ConfirmSchedule>  data;

    public List<ConfirmSchedule> getData() {
        return data;
    }

    public void setData(List<ConfirmSchedule> data) {
        this.data=data;
    }
    //    public void setScheduleData(ScheduleData data) {
//        this.data = data;
//    }

    public String  getConfirmManager(){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Log.d("scheduleddata",gson.toJson(this));
        return gson.toJson(this);
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


    public static ConfirmManager setConfirmManager(String message) {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(message, ConfirmManager.class);
    }
}
