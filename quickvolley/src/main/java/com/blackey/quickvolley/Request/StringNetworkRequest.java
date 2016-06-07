package com.blackey.quickvolley.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by blacKey on 2016/5/11.
 *
 * Copyright (c) 2016 Jimmy.D.Xie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class StringNetworkRequest extends StringRequest {
    private Priority mPriority = Priority.HIGH;
    private Map<String, String> headers;
    private Map<String, String> params;


    public StringNetworkRequest(int method, String url,
                                Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public StringNetworkRequest(int method, String url, Map<String, String> params,
                                Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.params = params;
        setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public StringNetworkRequest(int method, String url,
                                Map<String, String> headers, Map<String, String> params,
                                Response.Listener<String> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
        this.headers = headers;
        this.params = params;
        setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers == null ? super.getHeaders() : headers;
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        try {

            String strResponse = new String(response.data, "UTF-8");

            return Response.success(strResponse,
                    HttpHeaderParser.parseCacheHeaders(response));

        } catch (Exception e) {

            return Response.error(new ParseError(e));

        }
    }

    @Override
    public Request.Priority getPriority() {
        return mPriority;
    }

    public void setPriority(Request.Priority priority) {
        mPriority = priority;
    }

    private static String urlBuilder(String url, List<NameValuePair> params) {
        return url + "?" + URLEncodedUtils.format(params, "UTF-8");
    }
}
