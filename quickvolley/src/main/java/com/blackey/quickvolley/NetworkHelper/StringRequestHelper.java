package com.blackey.quickvolley.NetworkHelper;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.blackey.quickvolley.Request.StringNetworkRequest;
import com.blackey.quickvolley.VolleyQueueController;

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
public class StringRequestHelper extends NetworkHelper<String> {
    String flag;
    public StringRequestHelper(Context context) {
        super(context);
    }

    protected StringNetworkRequest getRequestForGet(String url, Map<String, String> headers) {
        return new StringNetworkRequest(Request.Method.GET, url, headers, null, this, this);
    }

    public void putRequestForGet(String url, Map<String, String> headers, String flag) {
        VolleyQueueController.getInstance().
                getRequestQueue(getContext()).add(getRequestForGet(url, headers));
        this.flag = flag;
    }

    protected StringNetworkRequest getRequestForPost(String url, Map<String, String> headers, Map<String, String> params) {
        return new StringNetworkRequest(Request.Method.POST, url, headers, params, this, this);
    }

    public void putRequestForPost(String url, Map<String, String> headers, Map<String, String> params, String flag) {
        VolleyQueueController.getInstance().
                getRequestQueue(getContext()).add(getRequestForPost(url, headers, params));
        this.flag = flag;
    }

    @Override
    protected void disposeVolleyError(VolleyError error) {

    }

    @Override
    protected void disposeResponse(String response) {
        notifyDataChanged(response, flag);
    }

}
