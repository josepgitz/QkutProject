package ke.co.qkut.qkut.Modeler;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Organisation {
    @Expose  private  String id;
    @Expose  private  String name;
    @Expose  private  String description;
    @Expose  private  String address;
    @Expose  private  String status;
    @Expose  private  String sub_industry_id;
    @Expose  private  String town_id;
    @Expose  private  String neighbourhood_id;
    @Expose  private  String user_id;
    @Expose  private  String created_at;
    @Expose  private  String updated_at;
    @Expose  private  String deleted_at;
     @Expose private List<Department> departmentList;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSub_industry_id() {
        return sub_industry_id;
    }

    public void setSub_industry_id(String sub_industry_id) {
        this.sub_industry_id = sub_industry_id;
    }

    public String getTown_id() {
        return town_id;
    }

    public void setTown_id(String town_id) {
        this.town_id = town_id;
    }

    public String getNeighbourhood_id() {
        return neighbourhood_id;
    }

    public void setNeighbourhood_id(String neighbourhood_id) {
        this.neighbourhood_id = neighbourhood_id;
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

    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }
}
