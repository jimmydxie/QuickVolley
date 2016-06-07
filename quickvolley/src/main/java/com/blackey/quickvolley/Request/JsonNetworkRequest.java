package com.blackey.quickvolley.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by blacKey on 2016/5/4.
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
public class JsonNetworkRequest extends JsonObjectRequest {
    private Priority mPriority = Priority.HIGH;
    private Map<String, String> headers;

    /*public JsonNetworkRequest(int method, String url,
                          Map<String, String> postParams, Response.Listener<JSONObject> listener,
                          Response.ErrorListener errorListener) {
        super(method, url, paramstoString(postParams), listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }*/

    public JsonNetworkRequest(int method, String url,
                       JSONObject postParams, Response.Listener<JSONObject> listener,
                       Response.ErrorListener errorListener) {
        super(method, url, postParams, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public JsonNetworkRequest(String url, List<NameValuePair> params,
                       Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this(Method.GET, urlBuilder(url, params), null, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public JsonNetworkRequest(String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        this(Method.GET, url, null, listener, errorListener);
        setRetryPolicy(new DefaultRetryPolicy(30000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    public JsonNetworkRequest(int method, String url,
                                Map<String, String> headers, JSONObject params,
                                Response.Listener<JSONObject> listener,
                                Response.ErrorListener errorListener) {
        super(method, url, params, listener, errorListener);
        this.headers = headers;
        setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private static String paramstoString(Map<String, String> params) {
        if (params != null && params.size() > 0) {
            String paramsEncoding = "UTF-8";
            StringBuilder encodedParams = new StringBuilder();
            try {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    encodedParams.append(URLEncoder.encode(entry.getKey(),
                            paramsEncoding));
                    encodedParams.append('=');
                    encodedParams.append(URLEncoder.encode(entry.getValue(),
                            paramsEncoding));
                    encodedParams.append('&');

                }
                return encodedParams.toString();
            } catch (UnsupportedEncodingException uee) {
                throw new RuntimeException("Encoding not supported: "
                        + paramsEncoding, uee);
            }
        }
        return null;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return headers == null ? super.getHeaders() : headers;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {

        try {

            JSONObject jsonObject = new JSONObject(new String(response.data, "UTF-8"));

            return Response.success(jsonObject,
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
