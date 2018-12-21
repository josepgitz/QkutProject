package ke.co.qkut.qkut.schedule_models;

import com.google.gson.annotations.Expose;

public class Department {
@Expose String id;
@Expose String name;
@Expose String description;
@Expose String status;
@Expose String business_account_id;
@Expose String user_id;
@Expose String created_at;
@Expose String updated_at;
@Expose String deleted_at;
@Expose BusinessAccount business_account;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
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

    public BusinessAccount getBusiness_account() {
        return business_account;
    }

    public void setBusiness_account(BusinessAccount business_account) {
        this.business_account = business_account;
    }
}
