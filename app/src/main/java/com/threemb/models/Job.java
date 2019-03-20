package com.threemb.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;


public class Job implements Serializable,Parcelable {

    public final static String  JOB_ID="jobId";
    public final static String  JOB_CODE="jobCode";
    public final static String  JOB_CUSTOMER_ID="customerId";
    public final static String  JOB_STATUS="currentJobStatusId";
    public final static String  JOB_ACTIVITY="activityTypeId";
    public final static String  JOB_UPDATE_DATE="activityTypeUpdateDate";
    public final static String  JOB_UPDATE_BY_ID="activityTypeUpdatedById";
    public final static String  JOB_LATEST_APPROVAL_DATE="latestApprovalDate";
    public final static String  JOB_LATEST_APPROVAL_STATUS="latestApprovalStatus";
    public final static String  JOB_LATEST_APPROVED_BY="latestApprovedBy";
    public final static String  JOB_START_DATE="jobStartDate";
    public final static String  JOB_COMPLETION_DATE="jobCompletionDate";
    public final static String  SENSORS="sensors";

    public String jobId;
    public String jobCode;
    public String customerId;
    public String currentJobStatusId;
    public String activityTypeId;
    public String activityTypeUpdateDate;
    public String activityTypeUpdatedById;
    public String latestApprovalDate;
    public String latestApprovalStatus;
    public String latestApprovedBy;
    public String jobStartDate;
    public String jobCompletionDate;
    public String sensors;
    public String userId;

    @Override
    public int describeContents() {
        return 0;
    }

    public Job()
    {};
    public Job(Parcel in) {
        jobId = in.readString();
        jobCode = in.readString();
        customerId = in.readString();
        currentJobStatusId = in.readString();
        activityTypeId = in.readString();
        activityTypeUpdateDate = in.readString();
        activityTypeUpdatedById = in.readString();
        latestApprovalDate = in.readString();
        latestApprovalStatus = in.readString();
        latestApprovedBy = in.readString();
        jobStartDate = in.readString();
        jobCompletionDate = in.readString();
        sensors = in.readString();
        userId = in.readString();
    }
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(jobId);
        out.writeString(jobCode);
        out.writeString(currentJobStatusId);
        out.writeString(activityTypeId);
        out.writeString(customerId);
        out.writeString(activityTypeUpdateDate);
        out.writeString(activityTypeUpdatedById);
        out.writeString(latestApprovalDate);
        out.writeString(latestApprovalStatus);
        out.writeString(latestApprovedBy);
        out.writeString(jobStartDate);
        out.writeString(jobCompletionDate);
        out.writeString(sensors);
        out.writeString(userId);
     }

    public static final Creator<Job> CREATOR = new Creator<Job>() {
        public Job createFromParcel(Parcel in)
        {
            return new Job(in);
        }
        public Job[] newArray(int size)
        {
            return new Job[size];
        }
    };
}
