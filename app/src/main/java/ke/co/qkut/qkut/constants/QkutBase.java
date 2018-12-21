package ke.co.qkut.qkut.constants;

import android.app.Application;

import android.support.multidex.MultiDexApplication;

import ke.co.qkut.qkut.models.Person;

public class QkutBase extends MultiDexApplication {
    private static Person person= new Person();
    public static  Person getUser(){
        return  person;
    }

    public static void setPerson(Person person) {
        QkutBase.person = person;
    }
}
