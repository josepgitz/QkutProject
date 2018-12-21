package ke.co.qkut.qkut.constants;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ke.co.qkut.qkut.datastore.LocalDatabase;

public class GLobalHeaders {
    public static JSONObject getGlobalHeaders(AppCompatActivity appCompatActivity) {
        JSONObject headers = new JSONObject();
        try {
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer " + LocalDatabase.getToken(appCompatActivity));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headers;
    }

    public static JSONObject getGlobalHeaders1(Context context) {
        JSONObject headers = new JSONObject();
        try {
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer " + LocalDatabase.getToken(context));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headers;
    }

    public static JSONObject getGlobalHeaders(Context context) {
        JSONObject headers = new JSONObject();
        try {
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer " + LocalDatabase.getToken(context));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headers;
    }

    public static JSONObject getGlobalHeadersforApprove(Context context) {
        JSONObject headers = new JSONObject();
        try {
            headers.put("Content-Type", "application/json");
            headers.put("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjJlYTAyOTEwMGUwMGE0YjNlZjNiMmExZGY2MzA4OTM4OGRiMzU0YjM5NjJiYzUzODgyOGYxMTgzMzBiZGM5YzJlYjM1MTQ1MjZhYmQ2OWE0In0.eyJhdWQiOiIxIiwianRpIjoiMmVhMDI5MTAwZTAwYTRiM2VmM2IyYTFkZjYzMDg5Mzg4ZGIzNTRiMzk2MmJjNTM4ODI4ZjExODMzMGJkYzljMmViMzUxNDUyNmFiZDY5YTQiLCJpYXQiOjE1NDI3MDgwMTEsIm5iZiI6MTU0MjcwODAxMSwiZXhwIjoxNTc0MjQ0MDExLCJzdWIiOiIzMyIsInNjb3BlcyI6W119.KPxZPainTA6opoqK44BuF1XWVWB_9VP53R-XhrZHKyFVMvIXlWRr1CD1Gr_nYlqPz4Sq0o9h5DO3d9QnKjms3--mjBPVe7KbDNeuEQYx4hQRq246guXdd0H-nh2PYjQMMvt8YDkjRocWSH3jmg5phVZ7ozSWgP-PbcpTRrldZHHqwGTZJNqhQpCh21tizQ7zUcpyK1fEtnfAxuGXXvjeVRsK6r_J6qcCw1QPV0_18lbYf3GVEK_RgboAKNE0KHLHiCfKQWV0RQDKG4sNliHXGJ3j19pzWjqhWjg6krLwkAcGc87nbatP52UrnKsVqjfW5VFbPzT-YHTc8FieI7WsAMMXHmTB42C0pyCHKFZUO1XISVVv-BRW5CSgzXJ2UM1QzmvKfvXbENi8WiQkhM3DOQDv_P9YKmjuXRw_ZYI9F6lzSQ6i2Opw6idBTUbkwroyfgwtXzNXJX81TPuC5clVsWmVsvHhUVYbU0tOh_0V89cOcmUqywudeuC2rg2vLlZeimM0Oqi1Qqutd41pnnD059GqZD0bf9BND_PIa2cAOXCISHH_E-4VDWUZjqF3wlXYuNGgQEtimzgaB6oCqv20RbgjeCkD4lKxKsM7DRJiYELyI9AyQ1ed0L-5m9UFZjcCqB9HsTUEtiq9byWrzxdwnNUkVTgUryjDmw2c_tBQVsg");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headers;
    }
}
