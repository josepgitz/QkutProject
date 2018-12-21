package ke.co.qkut.qkut.models;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import ke.co.qkut.qkut.schedule_models.ScheduleInfo;
import ke.co.qkut.qkut.schedule_models.ScheduleManager;

public class ConfirmSchedule {


        @Expose String is_queue;
        @Expose String id;
        @Expose String description;
        @Expose String department;
        @Expose String service_id;
        @Expose String service;
        @Expose String business_id;
        @Expose String business;
        @Expose String ticket;
        @Expose String status;
        @Expose String scheduled_time;
        @Expose String address;
        @Expose String queue_size;
        @Expose String queue_speed;
        @Expose String username;
        @Expose String usermobile;
        @Expose String useremail;

    public String getIs_queue() {
        return is_queue;
    }

    public void setIs_queue(String is_queue) {
        this.is_queue = is_queue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScheduled_time() {
        return scheduled_time;
    }

    public void setScheduled_time(String scheduled_time) {
        this.scheduled_time = scheduled_time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getQueue_size() {
        return queue_size;
    }

    public void setQueue_size(String queue_size) {
        this.queue_size = queue_size;
    }

    public String getQueue_speed() {
        return queue_speed;
    }

    public void setQueue_speed(String queue_speed) {
        this.queue_speed = queue_speed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsermobile() {
        return usermobile;
    }

    public void setUsermobile(String usermobile) {
        this.usermobile = usermobile;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }



    public static ConfirmSchedule getConfirmScheduleFrom1(String confirmSchedule){
        Gson gson= new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        ConfirmSchedule confirmSchedule1=gson.fromJson(confirmSchedule,ConfirmSchedule.class);
        return confirmSchedule1;


    }




  
}
