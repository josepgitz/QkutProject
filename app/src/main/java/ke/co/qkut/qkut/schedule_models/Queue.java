package ke.co.qkut.qkut.schedule_models;

import com.google.gson.annotations.Expose;

public class Queue {
    
@Expose  String id ;
@Expose String status ;
@Expose String business_account_id ;
@Expose String service_id ;
@Expose String device;
@Expose String scheduled_time;
@Expose String created_through;
@Expose String user_id ;
@Expose int ticket;
@Expose String created_at;
@Expose String updated_at;
@Expose String deleted_at;
@Expose String queue_speed;
@Expose String queue_size;
@Expose Service service;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBusiness_account_id() {
        return business_account_id;
    }

    public void setBusiness_account_id(String business_account_id) {
        this.business_account_id = business_account_id;
    }

    public String getService_id() {
        return service_id;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getScheduled_time() {
        return scheduled_time;
    }

    public void setScheduled_time(String scheduled_time) {
        this.scheduled_time = scheduled_time;
    }

    public String getCreated_through() {
        return created_through;
    }

    public void setCreated_through(String created_through) {
        this.created_through = created_through;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getTicket() {
        return ticket;
    }

    public void setTicket(int ticket) {
        this.ticket = ticket;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getQueue_speed() {
        return queue_speed;
    }

    public void setQueue_speed(String queue_speed) {
        this.queue_speed = queue_speed;
    }

    public String getQueue_size() {
        return queue_size;
    }

    public void setQueue_size(String queue_size) {
        this.queue_size = queue_size;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
