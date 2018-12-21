package ke.co.qkut.qkut.messages;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ke.co.qkut.qkut.interfaces.OnReceiveingNewService;
import ke.co.qkut.qkut.models.Service;

public class MessageReceivers {
    private  static List<OnReceiveingNewService> onReceiveingNewServices= new ArrayList<>();
    public static List<OnReceiveingNewService> getMessageReceiver(){
        return onReceiveingNewServices;
    }
    public  static void  addNewServiceListener(OnReceiveingNewService onReceiveingNewService){
        onReceiveingNewServices.add(onReceiveingNewService);
    }
    public  static  void notify(Service service){

        for ( OnReceiveingNewService onReceiveingNewServices:onReceiveingNewServices) {

           onReceiveingNewServices.OnRecevingNewServiceNotication(service);
        }
    }
}
