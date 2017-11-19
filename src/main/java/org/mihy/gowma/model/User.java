package org.mihy.gowma.model;

import java.util.List;

public class User extends BaseModel {
    private String email;
    private String mobileNo;
    private String password;
    private UserStatus status;
    private String fname;
    private String lname;
    private Gender gender;

    public User()
    {

    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Role> getRoles() {
        return null;
    }

    public Object getAuthorities() {
        return null;
    }

    public static enum UserStatus{
        ACTIVE,
        INACTIVE
    }

    public static enum Gender{
        MALE,
        FEMALE,
        OTHER
    }
}

