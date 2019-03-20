package com.threemb.async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.threemb.R;
import com.threemb.callers.ServiceCaller;
import com.threemb.models.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;


public class GetAsync extends AsyncTask<HashMap<String,String>, Integer, String> {

    private static final String TAG = "Get async";
    private final ServiceCaller serviceCaller;
    private final String callerIdentifire;
    private final String url;
    private ProgressDialog progress;
    private Context context;
    private final boolean isProgress;

    public GetAsync(Context context, ServiceCaller caller, boolean isProgress, String callerIdentifire, String url) {
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
    protected String doInBackground(HashMap<String,String>... param) {
        String data = sendGet(null,url);
//        Log.i(TAG, "Value in Background:-" + param[0]);
        return data;
    }

    protected void onPostExecute(String response) {
        Log.i("LoginResponse",""+response);
        if(response!=null) {
            try {
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("advertiseList",new JSONArray(response));
                Response res=new Gson().fromJson(jsonObject.toString(),Response.class);
                serviceCaller.OnAsyncCallResponse(res, callerIdentifire);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else
            Toast.makeText(context, context.getString(R.string.error_in_connection), Toast.LENGTH_LONG).show();
        if(progress!=null&&progress.isShowing())
        {
            progress.dismiss();
        }
    }

    // HTTP GET request
    private String sendGet(HashMap<String,String> params,String url)
    {
        StringBuffer response = null;
        try {
           // url = /*context.getString(R.string.get_login_url)*/
            //        url+ getDataString(params);
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
            return  null;
        }
        return response == null ? null : response.toString();
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
