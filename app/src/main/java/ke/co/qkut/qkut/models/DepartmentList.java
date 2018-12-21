package ke.co.qkut.qkut.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class DepartmentList {

    private ArrayList<Department> departmentList;

    //--------------------------------------------------------------------
    public DepartmentList(){
        departmentList = new ArrayList<Department>();

    }

    //--------------------------------------------------------------------
    public DepartmentList(ArrayList<Department> departmentList){
        setDepartmentList(departmentList);
    }

    //--------------------------------------------------------------------------------------------
    public DepartmentList(JSONArray jsonDepartmentList) throws JSONException {

        departmentList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonDepartmentList.length(); i++ ) {
                departmentList.add( new Department(jsonDepartmentList.getJSONObject(i)));
            }
        }
        catch (JSONException e){
            Log.e("DEapartment exe",e.getMessage());
                throw e;
        }
    }
   public DepartmentList(JSONArray jsonDepartmentList,String message) throws JSONException {

        departmentList = new ArrayList<>();
        try {
            for (int i = 0; i < jsonDepartmentList.length(); i++ ) {
                departmentList.add( new Department(jsonDepartmentList.getJSONObject(i),message));
            }
        }
        catch (JSONException e){
            Log.e("DEapartment exe",e.getMessage());
            throw e;
        }
    }

    //--------------------------------------------------------------------
    public void setDepartmentList(ArrayList<Department> departmentList){
        this.departmentList = departmentList;
    }

    //getters
    //---------------------------------------------------------------------
    //--------------------------------------------------------------------
    public ArrayList<Department> getDepartmentList(){
       return this.departmentList;
    }


    //---------------------------------------------------------------------
    public JSONObject getJsonObject(){

        JSONObject json = new JSONObject();


        return json;
    }
}
