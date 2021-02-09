package com.example.onlinemarket.model.customer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
@Entity(tableName = "customerTable")
public class Customer {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "primaryId")
    private int primaryId;

    @SerializedName("date_modified_gmt")
    @ColumnInfo(name = "dateModifiedGmt")
    private String dateModifiedGmt;

    @SerializedName("role")
    @ColumnInfo(name = "role")
    private String role;

    /* @SerializedName("_links")
     private Links links;
 */
    @SerializedName("date_created")
    @ColumnInfo(name = "date_created")
    private String dateCreated;

    @SerializedName("last_name")
    @ColumnInfo(name = "last_name")
    private String lastName;

    @SerializedName("date_created_gmt")
    @ColumnInfo(name = "date_created_gmt")
    private String dateCreatedGmt;

    /*@SerializedName("billing")
    private Billing billing;
*/
    @SerializedName("date_modified")
    @ColumnInfo(name = "date_modified")
    private String dateModified;

    /* @SerializedName("shipping")
     private Shipping shipping;
 */
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    private String avatarUrl;

    /* @SerializedName("meta_data")
     private List<MetaDataItem> metaData;
 */
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int id;

    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    private String firstName;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("is_paying_customer")
    @ColumnInfo(name = "is_paying_customer")
    private boolean isPayingCustomer;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String username;


    public int getPrimaryId() {
        return primaryId;
    }

    public String getDateModifiedGmt() {
        return dateModifiedGmt;
    }

    public String getRole() {
        return role;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDateCreatedGmt() {
        return dateCreatedGmt;
    }

    public String getDateModified() {
        return dateModified;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isPayingCustomer() {
        return isPayingCustomer;
    }

    public String getUsername() {
        return username;
    }

    public void setPrimaryId(int primaryId) {
        this.primaryId = primaryId;
    }

    public void setDateModifiedGmt(String dateModifiedGmt) {
        this.dateModifiedGmt = dateModifiedGmt;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateCreatedGmt(String dateCreatedGmt) {
        this.dateCreatedGmt = dateCreatedGmt;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPayingCustomer(boolean payingCustomer) {
        isPayingCustomer = payingCustomer;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}