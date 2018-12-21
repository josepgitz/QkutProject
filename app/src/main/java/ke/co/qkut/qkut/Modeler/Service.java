package ke.co.qkut.qkut.Modeler;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Service {
   @Expose private String id;
    @Expose private String name ;
    @Expose private String description;
    @Expose private String status;
    @Expose private String service_type_id;
    @Expose private String department_id;
    @Expose private String user_id;
    @Expose private String created_at;
    @Expose private String updated_at;
    @Expose private String scheduled_time;
    @Expose private String  deleted_at;
    @Expose private String queue_size;
    @Expose private String queue_speed;
    @Expose private String is_queued;
    @Expose private String is_visited;
    @Expose private ServiceType serviceType;

    public String getName() {
        return name;
    }

    public String getScheduledtime() {
        return scheduled_time;
    }

    public void setScheduledtime(String scheduled_time) {
        this.scheduled_time = scheduled_time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_queued() {
        return is_queued;
    }

    public void setIs_queued(String is_queued) {
        this.is_queued = is_queued;
    }

    public String getIs_visited() {
        return is_visited;
    }

    public void setIs_visited(String is_visited) {
        this.is_visited = is_visited;
    }

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

    public String getService_type_id() {
        return service_type_id;
    }

    public void setService_type_id(String service_type_id) {
        this.service_type_id = service_type_id;
    }

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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



    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }
}
