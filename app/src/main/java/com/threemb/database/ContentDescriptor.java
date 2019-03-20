

/*
' History Header:      Version         - Date        - Developer Name   - Work Description
' History       :        1.0           - June-2015   - Amol Chavan  - Class contains application database content provider description
 */

/*
 ##############################################################################################
 #####                                                                                    #####                                                                        
 #####     FILE              : ContentDescriptor.Java 	      		 			          #####                 
 #####     CREATED BY        : Amol Chavan                                                #####
 #####     CREATION DATE     : June-2015                                                  #####                          
 #####                                                                                    #####                                                                              
 #####     MODIFIED  BY      : Amol Chavan                                                #####
 #####     MODIFIED ON       :                                                   	      #####                          
 #####                                                                                    #####                                                                              
 #####     CODE BRIEFING     : ContentDescriptor Class.      		       			      #####          
 #####                         Class contains application database  					  #####
 #####						   content provider description								  #####
 #####                                                                                    #####                                                                              
 #####                                                                                    #####
 #####                                                                                    #####                                                                              
 ##############################################################################################
 */
package com.threemb.database;
import android.content.UriMatcher;
import android.net.Uri;
import com.threemb.database.tables.JobsTable;
import com.threemb.database.tables.LoginTable;
import com.threemb.database.tables.ProjectsTable;

/**
 * This class contains description about
 * application database content providers
 *
 * @author Amol Chavan
 */
public class ContentDescriptor {

    public static final String AUTHORITY = "com.threemb";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    /**
     * @return UriMatcher for database table Uris
     */
    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, LoginTable.PATH, LoginTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, ProjectsTable.PATH, ProjectsTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, JobsTable.PATH,JobsTable.PATH_TOKEN);
        return matcher;
    }
}