package com.umotic.people;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.umotic.people.Bean.User;

public class SharedManager {
    SharedPreferences userDataShared;

    SharedManager(Context c) {
        userDataShared = c.getApplicationContext().getSharedPreferences("UserData", 0);
    }


    public void writeInfoShared( String[] value ){
        SharedPreferences.Editor editor = userDataShared.edit();

        //NAME
        if(value[0] != null) {
            editor.putString("Name", value[0]);
        }

        //SURNAME
        if(value[1] != null) {
            editor.putString("Surname", value[1]);
        }

        //MAIL
        if(value[2] != null) {
            editor.putString("Mail", value[2]);
        }

        //PASSWORD
        if(value[3]!=null) {
            editor.putString("Pwd", value[3]);
        }

        //SEX
        if(value[4]!=null) {
            String sex = "";
            if(value[4].contentEquals("0")){
                sex="M";
            }else{
                sex="F";
            }
            editor.putString("Sex", sex);
        }

        //AGE
        if(value[5]!=null) {
            editor.putString("Age", value[5]);
        }




        //SURNAME
        if(value[1] != null) {
            editor.putString("Surname", value[1]);
        }
        editor.commit();
    }

    public User getUserInfoShared (){

        User user = new User();

        user.setUserSex(userDataShared.getString("Sex", "0"));

        user.setUserAge(userDataShared.getString("Age", "0"));

        user.setName(userDataShared.getString("Name", "0"));

        user.setSurname(userDataShared.getString("Surname", "0"));

        user.setMail(userDataShared.getString("Mail", "0"));

        user.setPwd(userDataShared.getString("Pwd", "0"));

        return user;
    }

}
