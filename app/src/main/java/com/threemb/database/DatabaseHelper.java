/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Jun-2015    - Amol Chavan  - SqliteOpenHeler class for app database
 */

/*
 ##############################################################################################
 #####                                                                                    #####                                                                        
 #####     FILE              : DatabaseHelper.Java 	       						          #####                 
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Jun-2015                                                   #####                          
 #####                                                                                    #####                                                                              
 #####     MODIFIED BY       : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####                          
 #####                                                                                    #####                                                                              
 #####     CODE BRIEFING     : DatabaseHelper Class.          			   			      #####          
 #####                         SqliteOpenHeler class for application database			  #####
 #####                                                                                    #####                                                                              
 #####                                                                                    #####
 #####                                                                                    #####                                                                              
 ##############################################################################################
 */
package com.threemb.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.threemb.database.tables.JobsTable;
import com.threemb.database.tables.LoginTable;
import com.threemb.database.tables.ProjectsTable;

import java.text.MessageFormat;


/**
 * SqliteOpenHeler class for application database
 *
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String KEY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS {0} ({1})";
    public static final String KEY_DROP_TABLE = "DROP TABLE IF EXISTS {0}";

    public final static String SQL = "SELECT COUNT(*) FROM sqlite_master WHERE name=?";
    public static final String TAG="DatabaseHelper";
    private static final int CURRENT_DB_VERSION = 1;
    private static final String DB_NAME = "REPORTING.db";

    /**
     * Constructor using context for the class
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, CURRENT_DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createLoginTable(db);
        createJobsTable(db);
        createProjectsTable(db);

    }



    private void createProjectsTable(SQLiteDatabase db) {

        StringBuilder projectsTableFields = new StringBuilder();
        projectsTableFields.append(ProjectsTable.Cols.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ProjectsTable.Cols.USER_ID).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.DESC).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.CUSTOMER_NAME).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.END_DATE).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.CUSTOMER_ID).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.START_DATE).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.PROJECT_STATUS_ID).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.PROJECT_STRUCTURE_NAME).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.PROJECT_STRUCTURE_ID).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.STATUS).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.PROJECT_NAME).append(" VARCHAR, ")
                .append(ProjectsTable.Cols.PROJECT_ID).append(" VARCHAR");
        createTable(db, ProjectsTable.TABLE_NAME, projectsTableFields.toString());

    }

    private void createJobsTable(SQLiteDatabase db) {

        StringBuilder jobsTableFields = new StringBuilder();
        jobsTableFields.append(JobsTable.Cols.JOB_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(JobsTable.Cols.JOB_CODE).append(" VARCHAR, ")
                .append(JobsTable.Cols.CUSTOMER_ID).append(" VARCHAR, ")
                .append(JobsTable.Cols.CURRENT_JOB_STATUS_ID).append(" VARCHAR, ")
                .append(JobsTable.Cols.ACTIVITY_TYPE_ID).append(" VARCHAR, ")
                .append(JobsTable.Cols.ACTIVITY_TYPE_UPDATE_DATE).append(" VARCHAR, ")
                .append(JobsTable.Cols.ACTIVITY_TYPE_UPDATE_BY_ID).append(" VARCHAR, ")
                .append(JobsTable.Cols.LATEST_APPROVAL_DATE).append(" VARCHAR, ")
                .append(JobsTable.Cols.LATEST_APPROVAL_STATUS).append(" VARCHAR, ")
                .append(JobsTable.Cols.LATEST_APPROVED_BY).append(" VARCHAR, ")
                .append(JobsTable.Cols.JOB_START_DATE).append(" VARCHAR, ")
                .append(JobsTable.Cols.JOB_COMPLETION_DATE).append(" VARCHAR");
        createTable(db, JobsTable.TABLE_NAME, jobsTableFields.toString());
    }



    /**
     * creates UserLogin in device database
     *
     * @param db SqliteDatabase instance
     */
    private void createLoginTable(SQLiteDatabase db) {

        StringBuilder loginTableFields = new StringBuilder();
        loginTableFields.append(LoginTable.Cols.ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(LoginTable.Cols.USER_ID).append(" VARCHAR, ")
                .append(LoginTable.Cols.USER_NAME).append(" VARCHAR, ")
                .append(LoginTable.Cols.USER_FIRST_NAME).append(" VARCHAR, ")
                .append(LoginTable.Cols.USER_LAST_NAME).append(" VARCHAR, ")
                .append(LoginTable.Cols.USER_EMAIL_ID).append(" VARCHAR, ")
                .append(LoginTable.Cols.ACTIVE_FLAG).append(" VARCHAR, ")
                .append(LoginTable.Cols.CUSTOMER_ID).append(" VARCHAR, ")
                .append(LoginTable.Cols.LAST_SYNCED_ON).append(" LONG, ")
                .append(LoginTable.Cols.DISTRIBUTER_ID).append(" VARCHAR, ")
                .append(LoginTable.Cols.CUSTOMER_NAME).append(" VARCHAR, ")
                .append(LoginTable.Cols.LOGIN_ATTEMPTS).append(" INTEGER");

        createTable(db, LoginTable.TABLE_NAME, loginTableFields.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db, LoginTable.TABLE_NAME);
        dropTable(db, ProjectsTable.TABLE_NAME);
        dropTable(db,JobsTable.TABLE_NAME);
        onCreate(db);
    }

    /**
     * Drops Table from device database
     *
     * @param db   SqliteDatabase instance
     * @param name TableName
     */
    public void dropTable(SQLiteDatabase db, String name) {
        String query = MessageFormat.format(DatabaseHelper.KEY_DROP_TABLE, name);
        db.execSQL(query);
    }

    public static boolean exists(SQLiteDatabase db, String name) {
        Log.d(TAG, "Checking tables:" + name);
        Cursor cur = db.rawQuery(SQL, new String[] { name });
        cur.moveToFirst();
        int tables = cur.getInt(0);
        if (tables > 0) {
            Log.d(TAG, "table:" + name + " does exsit");
            return true;
        } else {
            Log.d(TAG, "table:" + name + " does not exsit");
            return false;
        }
    }

    /**
     * Creates Table in device database
     *
     * @param db     SqliteDatabase instance
     * @param name   TableName
     * @param fields ColumnFields
     */
    public void createTable(SQLiteDatabase db, String name, String fields) {
        String query = MessageFormat.format(DatabaseHelper.KEY_CREATE_TABLE,
                name, fields);
        db.execSQL(query);
    }
}