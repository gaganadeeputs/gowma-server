package org.mihy.gowma.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.mihy.gowma.constants.AddressType;
import org.mihy.gowma.constants.Gender;
import org.mihy.gowma.constants.UserStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends BaseModel {
    @NotNull
    private String email;
    @NotNull
    private String mobileNo;
    @NotNull
    private String password;
    private UserStatus status;
    private UserDetail userDetail;
    private List<UserAddress> userAddresses;
    private List<Role> roles;

    public User() {

    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public List<UserAddress> getUserAddresses() {
        return userAddresses;
    }

    public void setUserAddresses(List<UserAddress> userAddresses) {
        this.userAddresses = userAddresses;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
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


    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserDetail extends BaseModel {

        private int userId;
        private String fname;
        private String lname;
        private Gender gender;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserAddress extends BaseModel {
        private int userId;
        private AddressCode addressCode;
        private String addressName;
        private AddressType addressType;
        private String address1;
        private String address2;
        private String landmark;
        private String phoneNo;
        private boolean isDefault;


        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public AddressCode getAddressCode() {
            return addressCode;
        }

        public void setAddressCode(AddressCode addressCode) {
            this.addressCode = addressCode;
        }

        public AddressType getAddressType() {
            return addressType;
        }

        public void setAddressType(AddressType addressType) {
            this.addressType = addressType;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getAddress1() {
            return address1;
        }

        public void setAddress1(String address1) {
            this.address1 = address1;
        }

        public String getAddress2() {
            return address2;
        }

        public void setAddress2(String address2) {
            this.address2 = address2;
        }

        public String getLandmark() {
            return landmark;
        }

        public void setLandmark(String landmark) {
            this.landmark = landmark;
        }

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean aDefault) {
            isDefault = aDefault;
        }
    }
}

