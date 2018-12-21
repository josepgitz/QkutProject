package ke.co.qkut.qkut.Modeler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.List;

public class Message {
    @Expose private String status;
    @Expose  String reason;
    private @Expose List<Organisation> data;
    public List<Organisation> getOrganisations() {
        return data;
    }

    public void setOrganisations(List<Organisation> data) {
        this.data = data;
    }
    public String  getMessage(){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.toJson(this);
    }
    public static  Message  setMessage(String message){
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        return gson.fromJson(message,Message.class);
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
