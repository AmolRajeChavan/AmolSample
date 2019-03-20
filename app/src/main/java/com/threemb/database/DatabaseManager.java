/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Jan-2015    - Amol Chavan  - Provide methods to handle database interactions
 */

/*
 ##############################################################################################
 #####                                                                                    #####                                                                        
 #####     FILE              : DatabaseManager.Java 	       						      #####                 
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Jan-2015                                                   #####                          
 #####                                                                                    #####                                                                              
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####                          
 #####                                                                                    #####                                                                              
 #####     CODE BRIEFING     : DatabaseManager Class.          			   			      #####          
 #####                         Provide methods to handle database interactions			  #####
 #####                                                                                    #####                                                                              
 #####                                                                                    #####
 #####                                                                                    #####                                                                              
 ##############################################################################################
 */
package com.threemb.database;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.threemb.app.AppConstants;
import com.threemb.database.tables.JobsTable;
import com.threemb.database.tables.LoginTable;
import com.threemb.database.tables.ProjectsTable;
import com.threemb.models.Job;
import com.threemb.models.Project;
import com.threemb.models.User;
import com.threemb.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * This class acts as an interface between database and UI. It contains all the
 * methods to interact with device database.
 *
 * @author Amol Chavan
 */

public class DatabaseManager {


    private static final String TAG = "DatabaseManager";

    /**
     * Save User to UserLogin table
     *
     * @param context Context
     * @param user    User
     */
    public static void saveUser(Context context, User user) {
        if (user != null) {
            ContentValues values = getContentValuesUserLoginTable(context, user);
            String condition = LoginTable.Cols.USER_ID + "='" + user.id + "'";
            saveValues(context, LoginTable.CONTENT_URI, values, condition);
        }
    }

    private static void saveValues(Context context, Uri table, ContentValues values, String condition) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(table, null,
                condition, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            resolver.update(table, values, condition, null);
        } else {
            resolver.insert(table, values);
        }
        if (cursor != null) {
            cursor.close();
        }
    }

    public static User getCurrentLoggedInUser(Context context) {
        String condition = LoginTable.Cols.USER_EMAIL_ID + "='" + SharedPrefManager.getStringValue(context, SharedPrefManager.USER_NAME) + "' and " + LoginTable.Cols.USER_ID + "='" + SharedPrefManager.getStringValue(context, SharedPrefManager.USER_ID) + "'";
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(LoginTable.CONTENT_URI, null,
                condition, null, null);
        // ArrayList<User> userList = getUserListFromCurser(cursor);
        User user = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            // userList = new ArrayList<User>();
            while (!cursor.isAfterLast()) {
                user = getUserFromCursor(cursor);
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return user;
    }

    /**
     * @param context
     * @param uname
     */
    public static ArrayList<User> getUser(Context context,
                                          String uname) {
        String condition = LoginTable.Cols.USER_EMAIL_ID + "='" + uname + "'";
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(LoginTable.CONTENT_URI, null,
                condition, null, null);
        ArrayList<User> userList = getUserListFromCurser(cursor);
        if (cursor != null) {
            cursor.close();
        }
        return userList;
    }

    private static ArrayList<User> getUserListFromCurser(Cursor cursor) {
        ArrayList<User> userList = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            User user;
            userList = new ArrayList<User>();
            while (!cursor.isAfterLast()) {
                user = getUserFromCursor(cursor);
                userList.add(user);
                cursor.moveToNext();
            }
        }
        return userList;
    }

    @NonNull
    private static User getUserFromCursor(Cursor cursor) {
        User user;
        user = new User();
        user.customerName = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.CUSTOMER_NAME));
        user.distributorId = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.DISTRIBUTER_ID));
        user.firstName = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_FIRST_NAME));
        user.lastName = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_LAST_NAME));
        user.activeFlag = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.ACTIVE_FLAG));
        user.id = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_ID));
        user.userName = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_NAME));
        user.custId = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.CUSTOMER_ID));
        user.emailId = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.USER_EMAIL_ID));
        // user.password = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.PASSWORD));
        user.lastsyncedon = cursor.getString(cursor.getColumnIndex(LoginTable.Cols.LAST_SYNCED_ON));
        return user;
    }

    /**
     * Get ContentValues from the Contact to insert it into UserLogin Table
     *
     * @param context Context
     * @param user    User
     */
    private static ContentValues getContentValuesUserLoginTable(Context context, User user) {
        ContentValues values = new ContentValues();
        try {
            values.put(LoginTable.Cols.USER_ID, user.id);
            values.put(LoginTable.Cols.USER_NAME, user.userName);
            values.put(LoginTable.Cols.USER_FIRST_NAME, user.firstName);
            values.put(LoginTable.Cols.USER_LAST_NAME, user.lastName);
            values.put(LoginTable.Cols.USER_EMAIL_ID, user.emailId);
            values.put(LoginTable.Cols.ACTIVE_FLAG, user.activeFlag);
            values.put(LoginTable.Cols.CUSTOMER_ID, user.custId);
            values.put(LoginTable.Cols.DISTRIBUTER_ID, user.distributorId);
            values.put(LoginTable.Cols.CUSTOMER_NAME, user.customerName);
            values.put(LoginTable.Cols.LAST_SYNCED_ON, user.lastsyncedon);
            values.put(LoginTable.Cols.LOGIN_ATTEMPTS, "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    /**
     * Clear all the table contents
     *
     * @param context Context
     */
    public static void clearAllCache(Context context) {
        deleteAllUserLoginDetails(context);
    }

    /**
     * Clear contents from UserLogin Table
     *
     * @param context
     */
    public static void deleteAllUserLoginDetails(Context context) {
        try {
            context.getContentResolver().delete(LoginTable.CONTENT_URI, null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserLoginDetails(Context context, String user_id) {
        try {
            String condition = LoginTable.Cols.USER_ID + "='" + user_id + "'";
            context.getContentResolver().delete(LoginTable.CONTENT_URI, condition,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void saveJobs(Context context, ArrayList<Job>jobs) {

        if (jobs!=null&&jobs.size()>0)
        {
            for (Job job:jobs) {
                saveJob(context, job);
                //  progressListener.increaseProgress();
            }
        }
    }

    private static void saveJob(Context context, Job job) {

        ContentValues values=getContentValuesUserJob(context, job);
        String condition = JobsTable.Cols.JOB_ID + "='" + job.jobId + "'";
        saveValues(context, JobsTable.CONTENT_URI, values, condition);
    }

    private static ContentValues getContentValuesUserJob(Context context, Job job) {
        ContentValues values = new ContentValues();
        try {
            values.put(JobsTable.Cols.JOB_CODE, job.jobCode);
            values.put(JobsTable.Cols.JOB_ID, job.jobId);
            values.put(JobsTable.Cols.CURRENT_JOB_STATUS_ID,job.currentJobStatusId);
            values.put(JobsTable.Cols.ACTIVITY_TYPE_ID,job.activityTypeId);
            values.put(JobsTable.Cols.ACTIVITY_TYPE_UPDATE_DATE,job.activityTypeId);
            values.put(JobsTable.Cols.ACTIVITY_TYPE_UPDATE_BY_ID, job.activityTypeUpdatedById);
            values.put(JobsTable.Cols.LATEST_APPROVAL_DATE,job.latestApprovalDate);
            values.put(JobsTable.Cols.LATEST_APPROVAL_STATUS, job.latestApprovalStatus);
            values.put(JobsTable.Cols.LATEST_APPROVED_BY,job.latestApprovedBy);
            values.put(JobsTable.Cols.JOB_START_DATE, job.jobStartDate);
            values.put(JobsTable.Cols.JOB_COMPLETION_DATE, job.jobCompletionDate);
            values.put(JobsTable.Cols.CUSTOMER_ID, job.customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }




    public static ArrayList<Job> getAllJobs(Context mContext,String status_id) {

        String condition = JobsTable.Cols.CURRENT_JOB_STATUS_ID + "='" +status_id + "'";
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(JobsTable.CONTENT_URI, null,
                condition, null, null);
        ArrayList<Job> jobsList = getJobsFromCursor(cursor);

        if (cursor != null) {
            cursor.close();
        }
        return jobsList;
    }


    private static  ArrayList<Job> getJobsFromCursor(Cursor cursor) {
        ArrayList<Job> jobsList = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Job job;
            jobsList = new ArrayList<Job>();
            while (!cursor.isAfterLast()) {
                job = new Job();
                job.jobCode = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.JOB_CODE));
                job.jobId = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.JOB_ID));
                job.activityTypeId = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.ACTIVITY_TYPE_ID));
                job.activityTypeUpdateDate = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.ACTIVITY_TYPE_UPDATE_DATE));
                job.activityTypeUpdatedById = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.ACTIVITY_TYPE_UPDATE_BY_ID));
                job.currentJobStatusId = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.CURRENT_JOB_STATUS_ID));
                job.jobStartDate = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.JOB_START_DATE));
                job.latestApprovalStatus = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.LATEST_APPROVAL_STATUS));
                job.jobCompletionDate = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.JOB_COMPLETION_DATE));
                job.customerId = cursor.getString(cursor.getColumnIndex(JobsTable.Cols.CUSTOMER_ID));
                job.latestApprovalDate= cursor.getString(cursor.getColumnIndex(JobsTable.Cols.LATEST_APPROVAL_DATE));
                job.latestApprovedBy= cursor.getString(cursor.getColumnIndex(JobsTable.Cols.LATEST_APPROVED_BY));

                jobsList.add(job);
                cursor.moveToNext();
            }
        }
        return jobsList;
    }



    public static ArrayList<Job> getDetailsFromJobId(Context mContext,String job_id) {

        String condition = JobsTable.Cols.JOB_ID + "='" +job_id + "'";
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(JobsTable.CONTENT_URI, null,
                condition, null, null);
        ArrayList<Job> jobsList = getJobsFromCursor(cursor);

        if (cursor != null) {
            cursor.close();
        }
        return jobsList;
    }








    public static void deleteUserCache(Context context) {
        try {
            context.getContentResolver().delete(ProjectsTable.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static void saveProjects(Context context, ProgressListener progressListener, ArrayList<Project> projects) {

        if (projects != null && projects.size() > 0) {
            for (Project project : projects) {
                saveProject(context, project, progressListener);
                progressListener.increaseProgress();
            }
        }
    }*/

    public static void saveProjects(Context context, ArrayList<Project> projects) {

        if (projects != null && projects.size() > 0) {
            for (Project project : projects) {
                saveProject(context, project);
               // progressListener.increaseProgress();
            }
        }
    }

    private static void saveProject(Context context, Project project) {

      //  saveSegements(context, project.id, project.projStructureId, project.segments, progressListener);
        ContentValues values = getContentValuesUserProject(context, project);
        String condition = ProjectsTable.Cols.PROJECT_ID + "='" + project.id + "'";
        saveValues(context, ProjectsTable.CONTENT_URI, values, condition);
    }

    private static ContentValues getContentValuesUserProject(Context context, Project project) {
        ContentValues values = new ContentValues();
        try {
            values.put(ProjectsTable.Cols.DESC, project.desc);
            values.put(ProjectsTable.Cols.PROJECT_STATUS_ID, project.projectStatusId);
            values.put(ProjectsTable.Cols.CUSTOMER_NAME, project.custName);
            values.put(ProjectsTable.Cols.END_DATE, project.endDate);
            values.put(ProjectsTable.Cols.CUSTOMER_ID, project.customerId);
            values.put(ProjectsTable.Cols.START_DATE, project.startDate);
            values.put(ProjectsTable.Cols.PROJECT_STRUCTURE_ID, project.projStructureId);
            values.put(ProjectsTable.Cols.PROJECT_STRUCTURE_NAME,project.projStructureName);
            values.put(ProjectsTable.Cols.STATUS, project.projectStatus);
            values.put(ProjectsTable.Cols.PROJECT_NAME, project.name);
            values.put(ProjectsTable.Cols.PROJECT_ID, project.id);
            values.put(ProjectsTable.Cols.USER_ID, SharedPrefManager.getStringValue(context, SharedPrefManager.USER_ID));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    public static ArrayList<Project> getAllProjects(Context mContext) {
        ContentResolver resolver = mContext.getContentResolver();
        Cursor cursor = resolver.query(ProjectsTable.CONTENT_URI, null,
                null, null, null);
        ArrayList<Project> projectList = getProjectsFromCursor(cursor);

        if (cursor != null) {
            cursor.close();
        }
        return projectList;
    }

    public static ArrayList<Project> getAllProjects(Context mContext, String status) {
        ContentResolver resolver = mContext.getContentResolver();
        String condition = ProjectsTable.Cols.PROJECT_STATUS_ID + "='" + status + "'";

        Cursor cursor = resolver.query(ProjectsTable.CONTENT_URI, null,
                condition, null, ProjectsTable.Cols.ID + " ASC");

        ArrayList<Project> projectList = getProjectsFromCursor(cursor);

        if (cursor != null) {
            cursor.close();
        }
        return projectList;
    }

    public static ArrayList<Project> getProjectsFromCursor(Cursor cursor) {
        ArrayList<Project> projectList = null;
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Project project;
            projectList = new ArrayList<Project>();
            while (!cursor.isAfterLast()) {

                project = new Project();
                project.desc = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.DESC));
                project.projectStatusId = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.PROJECT_STATUS_ID));
                project.custName = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.CUSTOMER_NAME));
                project.endDate = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.END_DATE));
                project.customerId = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.CUSTOMER_ID));
                project.startDate = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.START_DATE));
                project.projStructureId = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.PROJECT_STRUCTURE_ID));
                project.projStructureName = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.PROJECT_STRUCTURE_NAME));
                project.projectStatus = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.STATUS));
                project.name = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.PROJECT_NAME));
                project.id = cursor.getString(cursor.getColumnIndex(ProjectsTable.Cols.PROJECT_ID));
                projectList.add(project);
                cursor.moveToNext();
            }
        }
        return projectList;
    }




    public static LinkedHashMap<String, ArrayList<Project>> getProjectsByStatus(Context mContext)
    {
        LinkedHashMap<String, ArrayList<Project>> projects = new LinkedHashMap<String, ArrayList<Project>>();
        ArrayList<Project> completedProjects = getAllProjects(mContext, AppConstants.STATUS_COMPLETED_CODE);
        if (completedProjects != null && completedProjects.size() > 0) {
            projects.put(AppConstants.STATUS_COMPLETED, completedProjects);
        }
        ArrayList<Project> inProgressProjects = getAllProjects(mContext, AppConstants.STATUS_IN_PROGRESS_CODE);
        if (inProgressProjects != null && inProgressProjects.size() > 0) {
            projects.put(AppConstants.STATUS_IN_PROGRESS, inProgressProjects);
        }
        ArrayList<Project> inDraftProjects = getAllProjects(mContext, AppConstants.STATUS_DRAFT_CODE);
        if (inProgressProjects != null && inProgressProjects.size() > 0) {
            projects.put(AppConstants.STATUS_IN_PROGRESS, inDraftProjects);
        }
        ArrayList<Project> inCancelledProjects = getAllProjects(mContext, AppConstants.STATUS_CANCELLED_CODE);
        if (inProgressProjects != null && inProgressProjects.size() > 0) {
            projects.put(AppConstants.STATUS_IN_PROGRESS, inCancelledProjects);
        }
        return projects;
    }
}