package ke.co.qkut.qkut.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ConfirmScheduledList {


    @Expose
    List<ConfirmSchedule> results=new ArrayList<>();

    public List<ConfirmSchedule> getconfirmSchedule() {
        return results;
    }

    public static List<ConfirmSchedule> getconfirmScheduleFrom(String confirmSchedule){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ConfirmScheduledList consultants1=gson.fromJson(confirmSchedule,ConfirmScheduledList.class);
        return consultants1.getconfirmSchedule();
    }

}
