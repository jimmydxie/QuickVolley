package com.blackey.quickvolley.NetworkHelper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.blackey.quickvolley.Request.JsonNetworkRequest;
import com.blackey.quickvolley.VolleyQueueController;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by blacKey on 2016/5/19.
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
public class JsonRequestHelper extends NetworkHelper<JSONObject> {
    String flag;

    public JsonRequestHelper(Context context) {
        super(context);
    }

    protected JsonNetworkRequest getRequestForGet(String url, Map<String, String> headers) {
        return new JsonNetworkRequest(Request.Method.GET, url, headers, null, this, this);
    }

    public void putRequestForGet(String url, Map<String, String> headers, String flag) {
        VolleyQueueController.getInstance().
                getRequestQueue(getContext()).add(getRequestForGet(url, headers));
        this.flag = flag;
    }

    protected JsonNetworkRequest getRequestForPost(String url, Map<String, String> headers, JSONObject params) {
        return new JsonNetworkRequest(Request.Method.POST, url, headers, params, this, this);
    }

    public void putRequestForPost(String url, Map<String, String> headers, JSONObject params, String flag) {
        VolleyQueueController.getInstance().
                getRequestQueue(getContext()).add(getRequestForPost(url, headers, params));
        this.flag = flag;
    }

    @Override
    protected void disposeVolleyError(VolleyError error) {
        Log.i("volleyError", error.toString());
    }

    @Override
    protected void disposeResponse(JSONObject response) {
        Log.i("response", response.toString());
        notifyDataChanged(response, flag);
    }
}
