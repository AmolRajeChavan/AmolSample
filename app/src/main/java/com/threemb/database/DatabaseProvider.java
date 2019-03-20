/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - Jun-2015    - Amol Chavan   - ContentProvider for Application database
 */

/*
 ##############################################################################################
 #####                                                                                    #####                                                                        
 #####     FILE              : DatabaseProvider.Java 	       						      #####                 
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : Jun-2015                                                   #####                          
 #####                                                                                    #####                                                                              
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####                          
 #####                                                                                    #####                                                                              
 #####     CODE BRIEFING     : DatabaseProvider Class.          			   			  #####          
 #####                         ContentProvider for Application database					  #####
 #####                                                                                    #####                                                                              
 #####                                                                                    #####
 #####                                                                                    #####                                                                              
 ##############################################################################################
 */
package com.threemb.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.threemb.database.tables.JobsTable;
import com.threemb.database.tables.LoginTable;
import com.threemb.database.tables.ProjectsTable;


/**
 * This class provides Content Provider for application database
 *
 * @author Amol Chavn
 */
public class DatabaseProvider extends ContentProvider {

    private static final String UNKNOWN_URI = "Unknown URI ";

    private DatabaseHelper dbHelper;
    private String key = "123456";

    @Override
    public boolean onCreate() {
        //SQLiteDatabase.loadLibs(getContext());
        dbHelper = new DatabaseHelper(getContext());
        dbHelper.getWritableDatabase();
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        final int token = ContentDescriptor.URI_MATCHER.match(uri);

        Cursor result = null;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doQuery(db, uri, LoginTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }
            case ProjectsTable.PATH_TOKEN: {
                result = doQuery(db, uri, ProjectsTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }
            case JobsTable.PATH_TOKEN:{
                result = doQuery(db, uri, JobsTable.TABLE_NAME, projection,
                        selection, selectionArgs, sortOrder);
                break;
            }

        }

        return result;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        Uri result = null;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doInsert(db, LoginTable.TABLE_NAME,
                        LoginTable.CONTENT_URI, uri, values);
                break;
            }
            case ProjectsTable.PATH_TOKEN: {
                result = doInsert(db, ProjectsTable.TABLE_NAME,
                        ProjectsTable.CONTENT_URI, uri, values);
                break;
            }
            case JobsTable.PATH_TOKEN: {
                result = doInsert(db, JobsTable.TABLE_NAME,
                        JobsTable.CONTENT_URI, uri, values);
                break;
            }
        }

        if (result == null) {
            throw new IllegalArgumentException(UNKNOWN_URI + uri);
        }

        return result;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        String table = null;
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                table = LoginTable.TABLE_NAME;
                break;
            }
            case ProjectsTable.PATH_TOKEN:{
                table = ProjectsTable.TABLE_NAME;
                break;
            }
            case JobsTable.PATH_TOKEN:{
                table = JobsTable.TABLE_NAME;
                break;
            }
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.beginTransaction();

        for (ContentValues cv : values) {
            db.insert(table, null, cv);
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return values.length;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        int result = 0;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doDelete(db, uri, LoginTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case ProjectsTable.PATH_TOKEN: {
                result = doDelete(db, uri, ProjectsTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
            case JobsTable.PATH_TOKEN: {
                result = doDelete(db, uri, JobsTable.TABLE_NAME, selection,
                        selectionArgs);
                break;
            }
        }

        return result;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int token = ContentDescriptor.URI_MATCHER.match(uri);

        int result = 0;

        switch (token) {
            case LoginTable.PATH_TOKEN: {
                result = doUpdate(db, uri, LoginTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case ProjectsTable.PATH_TOKEN: {
                result = doUpdate(db, uri, ProjectsTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
            case JobsTable.PATH_TOKEN: {
                result = doUpdate(db, uri, JobsTable.TABLE_NAME, selection,
                        selectionArgs, values);
                break;
            }
        }

        return result;
    }

    /**
     * Performs query to specified table using the projection, selection and
     * sortOrder
     *
     * @param db            SQLiteDatabase instance
     * @param uri           ContentUri for watch
     * @param tableName     Name of table on which query has to perform
     * @param projection    needed projection
     * @param selection     needed selection cases
     * @param selectionArgs needed selection arguments
     * @param sortOrder     sort order if necessary
     * @return Cursor cursor from the table tableName matching all the criterion
     */
    private Cursor doQuery(SQLiteDatabase db, Uri uri, String tableName,
                           String[] projection, String selection, String[] selectionArgs,
                           String sortOrder) {

        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(tableName);
        Cursor result = builder.query(db, projection, selection, selectionArgs,
                null, null, sortOrder);

        result.setNotificationUri(getContext().getContentResolver(), uri);

        return result;
    }

    /**
     * performs update to the specified table row or rows
     *
     * @param db            SQLiteDatabase instance
     * @param uri           uri of the content that was changed
     * @param tableName     Name of table on which query has to perform
     * @param selection     needed selection cases
     * @param selectionArgs needed selection arguments
     * @param values        content values to update
     * @return success or failure
     */
    private int doUpdate(SQLiteDatabase db, Uri uri, String tableName,
                         String selection, String[] selectionArgs, ContentValues values) {
        int result = db.update(tableName, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    /**
     * deletes the row/rows from the table
     *
     * @param db            SQLiteDatabase instance
     * @param uri           uri of the content that was changed
     * @param tableName     Name of table on which query has to perform
     * @param selection     needed selection cases
     * @param selectionArgs needed selection arguments
     * @return success or failure
     */
    private int doDelete(SQLiteDatabase db, Uri uri, String tableName,
                         String selection, String[] selectionArgs) {
        int result = db.delete(tableName, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }

    /**
     * insert rows to the specified table
     *
     * @param db         SQLiteDatabase instance
     * @param tableName  Name of table on which query has to perform
     * @param contentUri ContentUri to build the path
     * @param uri        uri of the content that was changed
     * @param values     content values to update
     * @return success or failure
     */
    private Uri doInsert(SQLiteDatabase db, String tableName, Uri contentUri,
                         Uri uri, ContentValues values) {
        long id = db.insert(tableName, null, values);
        Uri result = contentUri.buildUpon().appendPath(String.valueOf(id))
                .build();
        getContext().getContentResolver().notifyChange(uri, null);
        return result;
    }
}