package com.threemb.database.tables;


import android.net.Uri;

import com.threemb.database.ContentDescriptor;


public class ProjectsTable {

    public static final String TABLE_NAME = "ProjectsTable";
    public static final String PATH = "PROJECTS_TABLE";
    public static final int PATH_TOKEN = 20;
    public static final Uri CONTENT_URI = ContentDescriptor.BASE_URI.buildUpon().appendPath(PATH).build();

    /**
     * This class contains Constants to describe name of Columns of UserProjectsTable
     *
     * @author Amol Chavan
     */
    public static class Cols {
        public static final String ID = "_id";
        public static final String USER_ID = "userid";
        public static final String PROJECT_NAME = "name";
        public static final String PROJECT_ID= "project_id";
        public static final String PROJECT_STRUCTURE_NAME = "projStructureName";
        public static final String DESC = "desc";
        public static final String CUSTOMER_NAME = "custName";
        public static final String STATUS = "projectStatus";
        public static final String START_DATE = "startDate";
        public static final String END_DATE = "endDate";
        public static final String PROJECT_STRUCTURE_ID = "projStructureId";
        public static final String PROJECT_STATUS_ID = "projectStatusId";
        public static final String CUSTOMER_ID = "customer_id";

        public static final String FLAG = "flag";
        public static final String ENABLED = "enabled";
        public static final String COMPLETION_DATE= "completion_date";

        public static final String SENSOR_TYPE = "sensor_type";

    }
}
