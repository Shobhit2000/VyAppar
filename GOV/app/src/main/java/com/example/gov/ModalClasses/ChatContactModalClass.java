package com.example.gov.ModalClasses;

public class ChatContactModalClass {

    private String Name;
    private String Lastmsg;
    private String Time;

    public ChatContactModalClass(String name, String lastmsg, String time) {
        Name = name;
        Lastmsg = lastmsg;
        Time = time;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastmsg() {
        return Lastmsg;
    }

    public void setLastmsg(String lastmsg) {
        Lastmsg = lastmsg;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }
}
