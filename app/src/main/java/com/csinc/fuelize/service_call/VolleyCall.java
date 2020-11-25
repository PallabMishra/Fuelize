package com.csinc.fuelize.service_call;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.csinc.fuelize.LoginActivity;
import com.csinc.fuelize.appdata.SharedPref;
import com.csinc.fuelize.utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolleyCall {

    private static String TAG = VolleyCall.class.getSimpleName();

    public static void sendLogIn(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendDashboard(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_DASHBOARD, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                        InvalidUserLogout(response, context);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTripStart(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_TRIP_START, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                        InvalidUserLogout(response, context);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTripStatusSync(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_TRIP_STATUS_SYNC, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                        InvalidUserLogout(response, context);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getOrderList(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_ORDER_LIST, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                        InvalidUserLogout(response, context);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTripUpdate(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_TRIP_UPDATE, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                        InvalidUserLogout(response, context);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendTripMultiStatusSync(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_TRIP_MULTI_STATUS_SYNC, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                        InvalidUserLogout(response, context);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendOrderMultiStatusSync(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_MULTI_ORDER_SYNC, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                        InvalidUserLogout(response, context);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendOrderDetails(final Context context, final Map<String, String> dataForPost, final MDUNetworkListener mduNetworkListener) {

        try {
            JSONObject jsonObject = new JSONObject(dataForPost);
            RequestQueue mRequestQueue = CustomRequestQueue.getInstance(context).getRequestQueue();

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Constants.URL_ORDER_DETAILS, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response != null) {
                        mduNetworkListener.onSuccess(response);
                        InvalidUserLogout(response, context);
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if (error != null) {
                        mduNetworkListener.onError(error);
                        Log.e(TAG, "onErrorResponse: 1" + error.getMessage() + error.getLocalizedMessage());
                    } else {
                        Log.e(TAG, "An error occurred while parsing the data! Stack trace follows:");
                    }
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(7000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            mRequestQueue.add(jsonObjectRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void InvalidUserLogout(JSONObject jsonObject, Context context) {
        // check for authorized user.
        try {
            if (jsonObject.has("not_authorized")) {
                if (jsonObject.getString("not_authorized").equalsIgnoreCase("1")) {
                    SharedPref sharedPref1 = new SharedPref();
                    sharedPref1.clearSharedPreference(context);
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface MDUNetworkListener {

        void onSuccess(JSONObject object);

        void onError(VolleyError error);
    }

}
