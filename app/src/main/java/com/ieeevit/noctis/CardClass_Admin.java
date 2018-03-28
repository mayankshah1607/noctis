package com.ieeevit.noctis;

import android.widget.ImageView;

/**
 * Created by Mayank on 3/25/2018.
 */

public class CardClass_Admin {
    String Name,RegistrationNumber,PhoneNumber,EmailID,RoomNumber;

    public CardClass_Admin(String name, String registrationNumber, String phoneNumber, String emailID, String roomNumber) {
        Name = name;
        RegistrationNumber = registrationNumber;
        PhoneNumber = phoneNumber;
        EmailID = emailID;
        RoomNumber = roomNumber;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        RegistrationNumber = registrationNumber;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmailID() {
        return EmailID;
    }

    public void setEmailID(String emailID) {
        EmailID = emailID;
    }

    public String getRoomNumber() {
        return RoomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        RoomNumber = roomNumber;
    }
}
