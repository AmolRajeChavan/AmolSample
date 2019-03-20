package com.threemb.callers;


import com.threemb.models.Response;

/**
 * Created by Amol on 6/15/2015.
 */
public interface ServiceCaller {
    public void OnAsyncCallResponse(Response response, String label);
}
