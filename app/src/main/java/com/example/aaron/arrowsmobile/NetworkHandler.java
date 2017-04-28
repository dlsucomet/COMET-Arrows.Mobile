package com.example.aaron.arrowsmobile;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

// handles the transportation of data from json to sqlite db
public class NetworkHandler {
    TextView mTxtDisplay;
    String url = "http://frozen-escarpment-35603.herokuapp.com/json";
    OnNetworkSuccessListener onNetworkSuccessListener;

    NetworkHandler(Context context, OnNetworkSuccessListener listener) throws JSONException {
        onNetworkSuccessListener = listener;
        final Context ctx = context;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(ctx, "Getting JSON Data...", Toast.LENGTH_LONG).show();
                        JSONParser jsonParser = new JSONParser(response);
                        jsonParser.parseJSON(ctx);
                        onNetworkSuccessListener.onNetworkSuccess(true);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "JSON Get Error");
                        onNetworkSuccessListener.onNetworkSuccess(false);
                    }
                });
        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
    }
}