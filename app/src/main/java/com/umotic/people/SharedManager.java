package com.umotic.people;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.umotic.people.Bean.User;

public class SharedManager {
    SharedPreferences pref;

    SharedManager(Context c) {
        pref = c.getApplicationContext().getSharedPreferences("UserData", 0);
    }


    public void writeInfoShared( String[] value ){
        SharedPreferences.Editor editor = pref.edit();

        for(String s : value) {
            Log.i("DEDES",s );
        }
        if(value[0]!=null) {
            String sex = "";
            if(value[0]=="0"){
                sex="M";
            }else{
                sex="F";
            }
            editor.putString("Sex", sex);
        }
        if(value[1]!=null) {
            editor.putString("Age", value[5]);
        }

        if(value[2]!=null) {
            editor.putString("Pwd", value[2]);
        }
        if(value[3]!=null) {
            editor.putString("Name", value[3]);
        }
        if(value[4]!=null) {
            editor.putString("Surname", value[4]);
        }
        if(value[5]!=null) {
            editor.putString("Mail", value[1]);
        }

        editor.clear();
        editor.commit();
    }

    public User getUserInfoShared (){

        User user = new User();

        user.setUserSex(pref.getString("Sex", "0"));

        user.setUserAge(pref.getString("Age", "0"));

        user.setName(pref.getString("Name", "0"));

        user.setSurname(pref.getString("Surname", "0"));

        user.setMail(pref.getString("Mail", "0"));

        user.setPwd(pref.getString("Pwd", "0"));

        return user;
    }

}
