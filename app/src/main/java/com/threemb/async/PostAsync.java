package com.threemb.async;

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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Amol on 7/31/2015.
 */
public class PostAsync extends AsyncTask<String, Integer, Response> {

    private final boolean isProgress;
    private Context context;
    private final ServiceCaller serviceCaller;
    private final String callerIdentifire;
    private final String url;
    private ProgressDialog progress;
    private static final String TAG = "Post async";

    public PostAsync(Context context,ServiceCaller caller,boolean isProgress,String callerIdentifire,String url) {
        this.context =context;
        this.serviceCaller=caller;
        this.callerIdentifire=callerIdentifire;
        this.url=url;
        this.isProgress=isProgress;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (isProgress) {
            progress = new ProgressDialog(context);
            progress.setMessage("Please wait......");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.setCancelable(false);
            progress.show();
        }
    }

    @Override
    protected Response doInBackground(String... param)
    { Response res=null;
        if (callerIdentifire.equals(AppConstants.SEND_INJEST)||callerIdentifire.equals(AppConstants.SEND_NOTE))
        {
            String data = putInjest(param[0], url);
            Log.i(TAG, "Value in Background:-" + param[0]);
            if(data!=null) {
                Log.i(TAG, "Response:-" + data);
                if (!data.equals(""))
                    res = new Gson().fromJson(data, Response.class);
            }
        }else {
            String data = sendPostJsonRequest(param[0], url);
            Log.i(TAG, "Value in Background:-" + param[0]);
            if(data!=null) {
                Log.i(TAG, "Response:-" + data);
                if (!data.equals(""))
                    res = new Gson().fromJson(data, Response.class);
            }
        }
        return res;
    }


    @Override
    protected void onPostExecute(Response response) {
        super.onPostExecute(response);

        Log.i("LoginResponse", "" + response);
        if(response!=null) {
            serviceCaller.OnAsyncCallResponse(response, callerIdentifire);
        }else
            Toast.makeText(context, context.getString(R.string.error_in_connection), Toast.LENGTH_LONG).show();

        if(progress!=null&&progress.isShowing())
        {
            progress.dismiss();
        }
    }


    // HTTP POST request
    private String sendPostJsonRequest(String requestJson, String url)
    {
        //String url =context.getString(R.string.get_login_url);
        StringBuffer response = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

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
            //showMessage(context,e.getMessage());
            return null;
        }
        return response == null ? "" : response.toString();
    }

    // HTTP POST request
    private String putInjest(String parms,String url)
    {
        StringBuffer response = null;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // add reuqest header
            con.setRequestMethod("PUT");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("accept-type", "application/json");

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(parms);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
            System.out.println("Post parameters : " + parms);
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
           // showMessage(context,e.getMessage());
            return null;
        }
        return response == null ? "" : response.toString();
    }

    private String getDataString(HashMap<String, String> params)
            throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        return result.toString();
    }
}
