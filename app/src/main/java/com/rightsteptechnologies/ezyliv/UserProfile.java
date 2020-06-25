package com.rightsteptechnologies.ezyliv;

import java.security.PublicKey;

public class UserProfile {
    public String userName;
    public String userEmail;
    public String userNumber;
    public String userPGname;

    public UserProfile(){

    }


    public UserProfile(String userName, String userEmail, String userNumber, String userPGname){
        this.userName = userName;
        this.userEmail =userEmail;
        this.userNumber =userNumber;
        this.userPGname =userPGname;
    }
    public String getUserName(){
        return userName;
    }
    public void setUserid(String userName) {
        this.userName =userName;
    }
    public String getUserEmail(){
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail =userEmail;
    }
    public String getUserNumber(){
        return userNumber;
    }
    public void setUserNumber(String userNumber) {
        this.userNumber =userNumber;
    }
    public String getUserPGname() {
        return userPGname;
    }
    public void setUserPGname(String userPGname) {
        this.userPGname = userPGname;
    }
}
