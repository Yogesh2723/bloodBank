package com.jeenya.yfb.webConnection;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.jeenya.yfb.sharedPreferences.SessionManagement;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SyncWithServer {

    public void contactSync() {

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                "http://bloodbank.jeenya.com/app/users.php", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Response...", response);
                //  handleResponse(response);

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("CHECK", "Checkout Error: " + error.getMessage());
                Toast.makeText(RetriveMyApplicationContext.getAppContext(), "ERROR CONNECTING TO SERVER", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting parameters
                Map<String, String> params = new HashMap<String, String>();

                String contactsArray = String.valueOf(new JsonArrayConversion().convertToJson());
                if (contactsArray != null) {
                    params.put("friendsContact", contactsArray);
                }

                String userJson = String.valueOf(getUserInfo());
                if (userJson != null) {
                    params.put("userInfo", userJson);
                }

                return params;
            }

        };
        requestQueue.add(strReq);
    }

    private JSONObject getUserInfo() {

        JSONObject obj = new JSONObject();
        String n, b, p;
        HashMap<String, String> pData = SessionManagement.GetPersonalInfo(RetriveMyApplicationContext.getAppContext());
        n = pData.get("name");
        b = pData.get("blood");
        p = pData.get("phone");

        try {
            obj.put("name",n);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            obj.put("phone", p);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            obj.put("blood", b);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("My userJson", obj.toString());

        return obj;
    }

}
