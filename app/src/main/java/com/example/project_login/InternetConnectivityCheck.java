package com.example.project_login;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
/*
 *The InternetConnectivity Class is used by both the Login and Sign Up Activity(Mainly for the Purpose of Checking if there is an active Mobile Network or Wifi Connectivity)
 * //If Either One of WIFI or Mobile is Available then the checkConn fucntion will return true ,but if there is no network ,then we are given a Alert Box Form Which we can
 * Either cancel the alert box of Go to The Network Settings
 * */
public class InternetConnectivityCheck {
    Context context;
    ConnectivityManager connectivityManager;
    public InternetConnectivityCheck(Context context){
        this.context=context;
        connectivityManager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
    public Boolean checkConn(){
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null){
            if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI||networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                return true;
            }
        }
        return false;
    }
}
