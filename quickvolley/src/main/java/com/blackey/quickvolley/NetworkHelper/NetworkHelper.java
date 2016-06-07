package com.blackey.quickvolley.NetworkHelper;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.blackey.quickvolley.UIDataListener;

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
public abstract class NetworkHelper<T> implements Response.Listener<T>, Response.ErrorListener {
    private Context context;

    public NetworkHelper(Context context) {
        this.context = context;
    }

    protected Context getContext() {
        return context;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        disposeVolleyError(error);
    }

    @Override
    public void onResponse(T response) {
        disposeResponse(response);
    }

    protected abstract void disposeVolleyError(VolleyError error);

    protected abstract void disposeResponse(T response);

    private UIDataListener<T> uiDataListener;

    public void setUiDataListener(UIDataListener<T> uiDataListener) {
        this.uiDataListener = uiDataListener;
    }

    protected void notifyDataChanged(T data, String flag) {
        if (uiDataListener != null) {
            uiDataListener.onDataChanged(data, flag);
        }
    }

    protected void notifyErrorHappened(String errorCode, String errorMessage) {
        if (uiDataListener != null) {
            uiDataListener.onErrorHappened(errorCode, errorMessage);
        }
    }
}
