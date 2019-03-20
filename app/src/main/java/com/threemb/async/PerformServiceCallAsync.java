package com.threemb.async;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.threemb.R;
import com.threemb.app.AppConstants;
import com.threemb.callers.ServiceCaller;
import com.threemb.models.Response;
import com.threemb.utils.CommonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



/**
 * Created by Amol on 7/31/2015.
 */
public class PerformServiceCallAsync extends AsyncTask<String, Integer, Response>
{
    public static final String METHOD_POST="post";
    public static final String METHOD_GET="get";
    private final Context mContext;
    private final ServiceCaller serviceCaller;
    private final String callerIdentifire;
    private final String url;
    private static final String TAG = "Post async";
    private ProgressDialog progress;
    private boolean isProgress=false;
    private boolean isSuccessCall=false;
    private String method;

    public PerformServiceCallAsync(Context context, boolean isProgress, String method, ServiceCaller caller, String callerIdentifire, String url) {
        this.mContext=context;
        this.isProgress=isProgress;
        this.serviceCaller=caller;
        this.callerIdentifire=callerIdentifire;
        this.method=method;
        this.url=url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isProgress) {
            progress = new ProgressDialog(mContext);
            progress.setMessage("Please wait......");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }
    }

    @Override
    protected Response doInBackground(String... param)
    {
        Response response=null;
        String data=null;
        if (CommonUtils.isNetworkAvaliable(mContext))
        {
            isSuccessCall = true;
            switch (callerIdentifire)
            {
                case AppConstants.PROJECT_SYNC_REQUEST:
                    if (method.equals(METHOD_POST)) {
                        if (param[0] != null) {
                            Log.i(TAG, "Value in Background:-" + param[0]);
                            data = sendPost(param[0], url);
                            if (data != null) {
                                Log.i(TAG, "Data:-" + data);
                                response = new Gson().fromJson(data, Response.class);
                            }
                        }
                    } else {
                        data = sendGet(url);
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("sensor", new JSONObject(data));
                        }catch (JSONException e) {
                            showMessage(mContext, e.getMessage());
                        }
                        if (data != null) {
                            Log.i(TAG, "Data:-" + data);
                            response = new Gson().fromJson(jsonObject.toString(), Response.class);
                        }
                    }
                    break;
                case AppConstants.JOB_SYNC_REQUEST:
                    if (method.equals(METHOD_GET))
                        data = sendGet(url);
                    else {
                        if (param[0] != null) {
                            Log.i(TAG, "Value in Background:-" + param[0]);
                            data = sendPost(param[0], url);
                        }
                    }

                    if (data != null) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("jobs", new JSONArray(data));
                            response = new Gson().fromJson(jsonObject.toString(), Response.class);
                        } catch (JSONException e) {
                            showMessage(mContext, e.getMessage());
                        }
                    }
                    break;
            }
        }else {
            showMessage(mContext, mContext.getString(R.string.please_check_internet_connection));
        }
        return response;
    }

    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);
        if (isSuccessCall && response != null) {
            serviceCaller.OnAsyncCallResponse(response, callerIdentifire);
        } else{
            Toast.makeText(mContext, "No response", Toast.LENGTH_LONG).show();
        }
        if(progress!=null&&progress.isShowing()) {
            progress.dismiss();
        }
    }

    // HTTP POST request
    private String sendPost(String requestJson,String url) {

        //String url =context.getString(R.string.get_login_url);
        StringBuffer response = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            // String urlParameters = getDataString(requestJson);

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(requestJson);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + requestJson);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            showMessage(mContext,mContext.getString(R.string.error_in_connection));
        }
        return response == null ?null : response.toString();
    }

    // HTTP GET request
    private String sendGet(String url) {

        StringBuffer response = null;
        try {
           /* url = *//*context.getString(R.string.get_login_url)*//*
                    url + getDataString(String params);*/
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            // add request header
            // con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // print result
            System.out.println(response.toString());
        } catch (IOException e) {

            // return e.getMessage();
            showMessage(mContext, mContext.getString(R.string.error_in_connection));
        }
        return response == null ? null : response.toString();
    }

    private void showMessage(final Context context, final String message) {
        ((Activity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context,message , Toast.LENGTH_LONG).show();
            }
        });
    }
}
