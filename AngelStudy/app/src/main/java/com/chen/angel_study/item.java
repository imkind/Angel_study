package com.chen.angel_study;

import android.app.Application;

import java.util.ArrayList;

public class item extends Application {
    private String one,two;
    private int t;

    public void setword(String i,String o){
        one=i;
        two=o;
        t=2;
    }

    public String returnword1(){
        t=1;
        return one;
    }
    public String returnword2(){
        t=1;
        return two;
    }
    public int returnwordt(){
        return t--;
    }


}
