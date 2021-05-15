package com.app.lawapp.firebase;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Om sai on 26/03/2017.
 */

public class MyVolleySingleton {
    private static MyVolleySingleton mInstance;
    private static Context mctx;
    private RequestQueue rq;

    private MyVolleySingleton(Context context) {
        mctx = context;
        rq = getRequestQueue();
    }

    public static synchronized MyVolleySingleton getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyVolleySingleton(context);
        }
        return mInstance;
    }

    private RequestQueue getRequestQueue() {
        if (rq == null) {
            rq = Volley.newRequestQueue(mctx.getApplicationContext());

        }
        return rq;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        rq.add(request);
    }

}
