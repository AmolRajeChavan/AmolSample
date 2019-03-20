package com.threemb.database.tables;

import android.net.Uri;

import com.threemb.database.ContentDescriptor;


public class JobsTable {


    public static final String TABLE_NAME = "JobsTable";
    public static final String PATH = "JOB_TABLE";
    public static final int PATH_TOKEN = 30;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();


    public static class Cols {
        public static final String JOB_ID ="jobId";
        public static final String JOB_CODE="jobCode";
        public static final String CUSTOMER_ID="customerId";
        public static final String CURRENT_JOB_STATUS_ID="currentJobStatusId";
        public static final String ACTIVITY_TYPE_ID="activityTypeId";
        public static final String ACTIVITY_TYPE_UPDATE_DATE="activityTypeUpdateDate";
        public static final String ACTIVITY_TYPE_UPDATE_BY_ID="activityTypeUpdatedById";
        public static final String LATEST_APPROVAL_DATE="latestApprovalDate";
        public static final String LATEST_APPROVAL_STATUS="latestApprovalStatus";
        public static final String LATEST_APPROVED_BY="latestApprovedBy";
        public static final String JOB_START_DATE="jobStartDate";
        public static final String JOB_COMPLETION_DATE="jobCompletionDate";

    }

}
