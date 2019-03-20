package com.threemb.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Map;

public class Project implements Serializable, Parcelable
{
    public String desc;
    public String projectStatusId;
    public String custName;
    public String endDate;
    public String customerId;
    public String startDate;
    public String createdBy;
    public String projStructureId;
    public String projStructureName;
    public String projectStatus;
    public String name;
    public String id;
    public Map<String,Map<String,String>> projects;
    public String userid;
    public String flag;
    public String enabled;
    public String completionDate;
    public String sensorId;
    public String distributorId;
    public String activeFlag;
    public String customerNumber;
    public String customerAdminEmailId;
    public String customerCity;
    public String customerState;
    public String customerCountry;
    public String customerZip;
    public String customerAddress1;
    public String customerAddress2;
    public String customerPhone;
    public String customerTypeId;
    public String primarySalesrepId;
    public String customerType;
    public String primarySalesrep;
    public String distributorName;
    public String customerAdminFirstName;
    public String customerAdminLastName;


 public int describeContents() {
  return 0;
 }

 public Project()
 {};
 private Project(Parcel in) {
  desc = in.readString();
  projectStatusId = in.readString();
  custName = in.readString();
  endDate = in.readString();
  customerId = in.readString();
  startDate = in.readString();
  createdBy = in.readString();
  projStructureId = in.readString();
  projStructureName = in.readString();
  projectStatus = in.readString();
  name = in.readString();
  id = in.readString();
  projects=in.readHashMap(Map.class.getClassLoader());
 }
 public void writeToParcel(Parcel out, int flags) {
  out.writeString(desc);
  out.writeString(projectStatusId);
  out.writeString(custName);
  out.writeString(endDate);
  out.writeString(customerId);
  out.writeString(startDate);
  out.writeString(createdBy);
  out.writeString(projStructureId);
  out.writeString(projStructureName);
  out.writeString(projectStatus);
  out.writeString(name);
  out.writeString(id);
  out.writeMap(projects);
 }

 public static final Creator<Project> CREATOR
         = new Creator<Project>() {
  public Project createFromParcel(Parcel in) {
   return new Project(in);
  }

  public Project[] newArray(int size) {
   return new Project[size];
  }
 };


}
