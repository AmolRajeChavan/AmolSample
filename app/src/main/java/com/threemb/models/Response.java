package com.threemb.models;

import java.util.ArrayList;

/**
 * Created by Amol on 8/30/2015.
 */
public class Response {

    public static String SUCCESS="SUCCES";
    public static String FAILED="FAILED";

    public static String INGEST_SUCCESS="success";
    public static String INGEST_FAILED="failure";

    public String responseCode;
    public String errorCode;
    public String errorDesc;

    public ArrayList<Advertise> advertiseList;
}
