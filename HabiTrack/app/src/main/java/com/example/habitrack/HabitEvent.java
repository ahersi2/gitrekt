package com.example.habitrack;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import io.searchbox.annotations.JestId;


/**
 * HabitEvent
 *
 * Version 1.0
 *
 * Created by sshussai on 10/21/17.
 *
 */



public class HabitEvent {
    /**
     * Version 1.0
     * This class is the template for a habit event entity.
     *
     */

    private final Integer habitEventID;         // ID for the event
    private final Integer habitTypeID;          // ID for the corresponding habit type
    private String habitTypeEsID;               // ES ID of the corresponding habit type
    private String title;                       // title of the habit event
    private String comment;                     // comment for the habit event
    private Calendar date;                      // date of the habit event
    private String userID;                      // userId of the he maker
    @JestId
    private String id;
    // Photo related vars
    private String encodedPhoto;                // compressed string representation of the photo
    private Bitmap decodedPhoto;                // Contains the actual photo. Will be null when saved on elastic search
    // isEmpty - true when event is default created by the app
    private Boolean isEmpty;                    // default event or not
    private LatLng location;

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public HabitEvent(Integer heID, Integer htID, String givenEsID) {
        this.habitEventID = heID;
        this.habitTypeID = htID;
        this.date = Calendar.getInstance();
        this.isEmpty = Boolean.TRUE;
        this.habitTypeEsID = givenEsID;
    }

    public String getHabitTypeEsID() {
        return habitTypeEsID;
    }

    public String getEncodedPhoto() {
        return encodedPhoto;
    }

    public void setEncodedPhoto(String encodedPhoto) {
        this.encodedPhoto = encodedPhoto;
    }

    public Bitmap getDecodedPhoto() {
        return decodedPhoto;
    }

    public void setDecodedPhoto(Bitmap decodedPhoto) {
        this.decodedPhoto = decodedPhoto;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Integer getHabitEventID() {
        return habitEventID;
    }

    public Integer getHabitTypeID() {
        return habitTypeID;
    }

    public String getPhoto() {
        return this.encodedPhoto;
    }

//    public void setPhoto(String newEncodedPhoto) {
//        this.encodedPhoto = newEncodedPhoto;
//    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return getTitle();
    }
}
