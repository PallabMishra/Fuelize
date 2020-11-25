package com.csinc.fuelize.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.csinc.fuelize.model.OrderData;
import com.csinc.fuelize.model.TripData;
import com.csinc.fuelize.utility.Constants;
import com.csinc.fuelize.utility.MainApplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "OrderDatas";
    public static final String TABLE_LAT_LNG = "LatLngData";
    public static final String TABLE_ORDER_LOG = "OrderData";
    public static final String TABLE_ODOMETER_LOG = "OdometerData";

    // Table Data
    public static final String COLUMN_ORDER_ID = "orderID";
    public static final String COLUMN_CRM_NUMBER = "crmNumber";
    public static final String COLUMN_SAP_ID = "sapID";
    public static final String COLUMN_CUSTOMER_ID = "customerID";
    public static final String COLUMN_CUSTOMER_NAME = "customerName";
    public static final String COLUMN_ADDRESS = "customerAddress";
    public static final String COLUMN_RO_NAME = "roName";
    public static final String COLUMN_RO_ADDRESS = "roAddress";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_DECANT_QUANTITY = "decantQty";
    public static final String COLUMN_PER_UNIT_PRICE = "pricePerUnit";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_SPEED = "speed";
    public static final String COLUMN_RO_LATITUDE = "roLatitude";
    public static final String COLUMN_RO_LONGITUDE = "roLongitude";
    public static final String COLUMN_DECANT_LATITUDE = "decantLat";
    public static final String COLUMN_DECANT_LONGITUDE = "decantLong";
    public static final String COLUMN_INVOICE_NUMBER = "invoiceNumber";
    public static final String COLUMN_TOTALIZER_START = "totalizerValue";
    public static final String COLUMN_TOTALIZER_END = "totalizerValueEnd";
    public static final String COLUMN_ODOMETER_START = "odometerStart";
    public static final String COLUMN_ODOMETER_END = "odometerEnd";
    public static final String COLUMN_TRANSACTION_STATUS = "transactionStatus";
    public static final String COLUMN_RFID = "rfid";
    public static final String COLUMN_RO_RFID = "ro_rfid";
    public static final String COLUMN_MASTER_TAG = "masterTag";
    public static final String COLUMN_COMMENTS = "comments";
    public static final String COLUMN_OTP = "otp";
    public static final String COLUMN_OTP_CODE = "otp_code";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    /* LOG TABLE LAT LONG */

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LAT_LONG_STATUS = "lat_lng_status";
    public static final String COLUMN_DRIVER_ID = "driver_id";
    public static final String COLUMN_VEHICLE_ID = "vehicle_id";
    public static final String COLUMN_TRIP_ID = "trip_id";
    public static final String COLUMN_DEVICE_ID = "device_id";


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ORDER_ID + " TEXT PRIMARY KEY ," + COLUMN_CRM_NUMBER + " TEXT," + COLUMN_SAP_ID + " TEXT," + COLUMN_CUSTOMER_ID + " TEXT," + COLUMN_CUSTOMER_NAME + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT," + COLUMN_RO_NAME + " TEXT," + COLUMN_RO_ADDRESS + " TEXT," + COLUMN_STATUS + " TEXT," + COLUMN_QUANTITY + " TEXT," + COLUMN_PER_UNIT_PRICE + " TEXT,"
                    + COLUMN_LATITUDE + " TEXT," + COLUMN_LONGITUDE + " TEXT," + COLUMN_RO_LATITUDE + " TEXT," + COLUMN_RO_LONGITUDE + " TEXT," + COLUMN_INVOICE_NUMBER + " TEXT,"
                    + COLUMN_TRANSACTION_STATUS + " TEXT," + COLUMN_RFID + " TEXT," + COLUMN_RO_RFID + " TEXT," + COLUMN_OTP + " TEXT," + COLUMN_TIMESTAMP + " TEXT" + ")";

    // Create table SQL query
    public static final String CREATE_TABLE_LAT_LNG =
            "CREATE TABLE " + TABLE_LAT_LNG + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_LATITUDE + " TEXT," + COLUMN_LONGITUDE + " TEXT," + COLUMN_SPEED + " TEXT,"
                    + COLUMN_DRIVER_ID + " TEXT," + COLUMN_TRIP_ID + " TEXT," + COLUMN_VEHICLE_ID + " TEXT," + COLUMN_DEVICE_ID + " TEXT," + COLUMN_LAT_LONG_STATUS + " TEXT," + COLUMN_ORDER_ID + " TEXT,"
                    + COLUMN_TIMESTAMP + " TEXT" + ")";

    // create table order log
    // Create table SQL query
    public static final String CREATE_TABLE_ORDER_LOG =
            "CREATE TABLE " + TABLE_ORDER_LOG + "(" + COLUMN_ORDER_ID + " TEXT," + COLUMN_STATUS + " TEXT," + COLUMN_DECANT_QUANTITY + " TEXT,"
                    + COLUMN_DECANT_LATITUDE + " TEXT," + COLUMN_DECANT_LONGITUDE + " TEXT," + COLUMN_TOTALIZER_START + " TEXT," + COLUMN_TOTALIZER_END + " TEXT,"
                    + COLUMN_ODOMETER_START + " TEXT," + COLUMN_ODOMETER_END + " TEXT," + COLUMN_TRANSACTION_STATUS + " TEXT," + COLUMN_RFID + " TEXT," + COLUMN_RO_RFID + " TEXT," + COLUMN_MASTER_TAG + " TEXT,"
                    + COLUMN_COMMENTS + " TEXT," + COLUMN_OTP_CODE + " TEXT," + COLUMN_TIMESTAMP + " TEXT" + ")";

    public static final String CREATE_TABLE_ODOMETER_LOG =
            "CREATE TABLE " + TABLE_ODOMETER_LOG + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_ORDER_ID + " TEXT,"
                    + COLUMN_ODOMETER_START + " TEXT," + COLUMN_ODOMETER_END + " TEXT," + COLUMN_TIMESTAMP + " TEXT" + ")";

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "mdu_db.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create OrderData table
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE_LAT_LNG);
        db.execSQL(CREATE_TABLE_ORDER_LOG);
        db.execSQL(CREATE_TABLE_ODOMETER_LOG);
        Log.e("TAG", "onCreate: TABLE CREATED ---> ");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LAT_LNG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER_LOG);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ODOMETER_LOG);
        // Create tables again
        onCreate(db);
    }

    public void ClearTablesForLogout() {
        SQLiteDatabase writeDatabase = this.getWritableDatabase();
        writeDatabase.execSQL("DELETE FROM " + TABLE_NAME);
        writeDatabase.execSQL("DELETE FROM " + TABLE_LAT_LNG);
        writeDatabase.execSQL("DELETE FROM " + TABLE_ORDER_LOG);
        writeDatabase.execSQL("DELETE FROM " + TABLE_ODOMETER_LOG);
        writeDatabase.close();
    }

    public long insertOrderData(OrderData orderData) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        try {
            // `id` and `timestamp` will be inserted automatically.
            // no need to add them
            values.put(COLUMN_ORDER_ID, orderData.getOrderId());
            values.put(COLUMN_CRM_NUMBER, orderData.getOrderCRMNumber());
            values.put(COLUMN_SAP_ID, orderData.getOrderSapID());
            values.put(COLUMN_CUSTOMER_ID, orderData.getOrderCustomerID());
            values.put(COLUMN_CUSTOMER_NAME, orderData.getOrderCustomerName());
            values.put(COLUMN_ADDRESS, orderData.getOrderCustomerAddress());
            values.put(COLUMN_RO_NAME, orderData.getRoName());
            values.put(COLUMN_RO_ADDRESS, orderData.getRoAddress());
            values.put(COLUMN_STATUS, orderData.getStatus());
            values.put(COLUMN_QUANTITY, orderData.getOrderQuantity());
            values.put(COLUMN_PER_UNIT_PRICE, orderData.getOrderUnitPrice());
            values.put(COLUMN_LATITUDE, orderData.getLatitude());
            values.put(COLUMN_LONGITUDE, orderData.getLongitude());
            values.put(COLUMN_RO_LATITUDE, orderData.getROLatitude());
            values.put(COLUMN_RO_LONGITUDE, orderData.getROLongitude());
            values.put(COLUMN_INVOICE_NUMBER, orderData.getOrderInvoiceNumber());
            values.put(COLUMN_TRANSACTION_STATUS, orderData.getOrderTransactionStatus());
            values.put(COLUMN_RFID, orderData.getOrderRFID());
            values.put(COLUMN_RO_RFID, orderData.getRoRFID());
            values.put(COLUMN_OTP, orderData.getOtp());
            values.put(COLUMN_TIMESTAMP, getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // insert row
        long id = db.insert(TABLE_NAME, null, values);
        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public OrderData getOrderData(String id) {
        // prepare Order object
        OrderData order = new OrderData();
        try {
            // get readable database as we are not inserting anything
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME,
                    new String[]{COLUMN_ORDER_ID, COLUMN_CRM_NUMBER, COLUMN_SAP_ID, COLUMN_CUSTOMER_ID, COLUMN_CUSTOMER_NAME, COLUMN_ADDRESS, COLUMN_RO_NAME, COLUMN_RO_ADDRESS,
                            COLUMN_STATUS, COLUMN_QUANTITY, COLUMN_PER_UNIT_PRICE, COLUMN_LATITUDE, COLUMN_LONGITUDE, COLUMN_RO_LATITUDE,
                            COLUMN_RO_LONGITUDE, COLUMN_INVOICE_NUMBER, COLUMN_RFID, COLUMN_RO_RFID, COLUMN_OTP}, COLUMN_ORDER_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            assert cursor != null;
            order.setOrderId(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_ID)));
            order.setOrderCRMNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CRM_NUMBER)));
            order.setOrderSapID(cursor.getString(cursor.getColumnIndex(COLUMN_SAP_ID)));
            order.setOrderCustomerID(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ID)));
            order.setOrderCustomerName(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)));
            order.setOrderCustomerAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
            order.setRoName(cursor.getString(cursor.getColumnIndex(COLUMN_RO_NAME)));
            order.setRoAddress(cursor.getString(cursor.getColumnIndex(COLUMN_RO_ADDRESS)));
            order.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
            order.setOrderQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
            order.setOrderUnitPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PER_UNIT_PRICE)));
            order.setLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)));
            order.setLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)));
            order.setROLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_RO_LATITUDE)));
            order.setROLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_RO_LONGITUDE)));
            order.setOrderInvoiceNumber(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NUMBER)));
            order.setOrderRFID(cursor.getString(cursor.getColumnIndex(COLUMN_RFID)));
            order.setRoRFID(cursor.getString(cursor.getColumnIndex(COLUMN_RO_RFID)));
            order.setOtp(cursor.getString(cursor.getColumnIndex(COLUMN_OTP)));

            // close the db connection
            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public List<OrderData> getAllOrderData() {
        List<OrderData> OrderDatas = new ArrayList<>();
        try {
            // Select All Query
            String selectQuery = "SELECT * FROM " + TABLE_NAME;

            SQLiteDatabase db = this.getReadableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        OrderData order = new OrderData();
                        order.setOrderId(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_ID)));
                        order.setOrderCRMNumber(cursor.getString(cursor.getColumnIndex(COLUMN_CRM_NUMBER)));
                        order.setOrderSapID(cursor.getString(cursor.getColumnIndex(COLUMN_SAP_ID)));
                        order.setOrderCustomerID(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_ID)));
                        order.setOrderCustomerName(cursor.getString(cursor.getColumnIndex(COLUMN_CUSTOMER_NAME)));
                        order.setOrderCustomerAddress(cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS)));
                        order.setRoName(cursor.getString(cursor.getColumnIndex(COLUMN_RO_NAME)));
                        order.setRoAddress(cursor.getString(cursor.getColumnIndex(COLUMN_RO_ADDRESS)));
                        order.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                        order.setOrderQuantity(cursor.getString(cursor.getColumnIndex(COLUMN_QUANTITY)));
                        order.setOrderUnitPrice(cursor.getString(cursor.getColumnIndex(COLUMN_PER_UNIT_PRICE)));
                        order.setLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)));
                        order.setLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                        order.setROLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_RO_LATITUDE)));
                        order.setROLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_RO_LONGITUDE)));
                        order.setOrderInvoiceNumber(cursor.getString(cursor.getColumnIndex(COLUMN_INVOICE_NUMBER)));
                        order.setOrderRFID(cursor.getString(cursor.getColumnIndex(COLUMN_RFID)));
                        order.setRoRFID(cursor.getString(cursor.getColumnIndex(COLUMN_RO_RFID)));
                        order.setOtp(cursor.getString(cursor.getColumnIndex(COLUMN_OTP)));
                        double distance = MainApplication.distance(Constants.latitude, Constants.longitude, Double.valueOf(order.getLatitude()), Double.valueOf(order.getLongitude()));
                        order.setOrderDistance((distance));
                        OrderDatas.add(order);
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            // close db connection
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Collections.sort(OrderDatas, new Comparator<OrderData>() {
            @Override
            public int compare(OrderData o1, OrderData o2) {
                return Double.compare(o1.getOrderDistance(), o2.getOrderDistance());
            }
        });
        // return OrderDatas list
        return OrderDatas;
    }

    public OrderData getRODetails() {
        OrderData order = new OrderData();
        try {
            // Select All Query
            String selectQuery = "SELECT * FROM " + TABLE_NAME;

            SQLiteDatabase db = this.getReadableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);
            // looping through all rows and adding to list
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        order.setRoName(cursor.getString(cursor.getColumnIndex(COLUMN_RO_NAME)));
                        order.setRoAddress(cursor.getString(cursor.getColumnIndex(COLUMN_RO_ADDRESS)));
                        order.setROLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_RO_LATITUDE)));
                        order.setROLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_RO_LONGITUDE)));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            // close db connection
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }

    public float getOrderQty() {
        float OrderQty = 0;
        try {
            String countQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        OrderQty = OrderQty + cursor.getFloat(cursor.getColumnIndex(COLUMN_QUANTITY));
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return count
        return OrderQty;
    }

    public boolean IsAllOrdersExecuted() {
        boolean isAllOrderExecuted = true;
        try {
            String countQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        String Status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
                        if (!Status.equals("E0004") && !Status.equals("E0005")) {
                            isAllOrderExecuted = false;
                        }
                    } while (cursor.moveToNext());
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAllOrderExecuted;
    }

    public boolean IsDecantQtyClear() {
        boolean isDecantQtyClear = true;
        try {
            String countQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    do {
                        String Status = cursor.getString(cursor.getColumnIndex(COLUMN_STATUS));
                        if (!Status.equals("1") && !Status.equals("E0001") && !Status.equals("E00010")) {
                            isDecantQtyClear = false;
                        }
                    } while (cursor.moveToNext());
                }
            } else {
                isDecantQtyClear = false;
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDecantQtyClear;
    }

    public void updateOrderData(OrderData orderData) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_STATUS, orderData.getStatus());
            // updating row
            db.update(TABLE_NAME, values, COLUMN_ORDER_ID + " = ?", new String[]{String.valueOf(orderData.getOrderId())});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderData(OrderData orderData) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, COLUMN_ORDER_ID + " = ?", new String[]{orderData.getOrderId()});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderData() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_NAME, null, null);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExecuteQuery(String query) {
        try {
            SQLiteDatabase writeDatabase = this.getWritableDatabase();
            writeDatabase.execSQL(query);
            writeDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* LAT LONG TABLE */

    public long insertLatLongData(TripData tripData) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        try {
            values.put(COLUMN_LATITUDE, tripData.getLatitude());
            values.put(COLUMN_LONGITUDE, tripData.getLongitude());
            values.put(COLUMN_SPEED, tripData.getSpeed());
            values.put(COLUMN_LAT_LONG_STATUS, tripData.getIsSynced());
            values.put(COLUMN_DRIVER_ID, tripData.getDriverId());
            values.put(COLUMN_VEHICLE_ID, tripData.getVehicleNo());
            values.put(COLUMN_DEVICE_ID, tripData.getDeviceId());
            values.put(COLUMN_TRIP_ID, tripData.getTripId());
            values.put(COLUMN_ORDER_ID, tripData.getOrderID());
            values.put(COLUMN_TIMESTAMP, getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // insert row
        long id = db.insert(TABLE_LAT_LNG, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<TripData> getAllLogData() {
        List<TripData> tripDataArray = new ArrayList<>();
        try {

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_LAT_LNG;

            SQLiteDatabase db = this.getWritableDatabase();
            @SuppressLint("Recycle") Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    TripData tripData = new TripData();
                    tripData.setTripId(cursor.getString(cursor.getColumnIndex(COLUMN_TRIP_ID)));
                    tripData.setDeviceId(cursor.getString(cursor.getColumnIndex(COLUMN_DEVICE_ID)));
                    tripData.setDriverId(cursor.getString(cursor.getColumnIndex(COLUMN_DRIVER_ID)));
                    tripData.setVehicleNo(cursor.getString(cursor.getColumnIndex(COLUMN_VEHICLE_ID)));
                    tripData.setLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_LATITUDE)));
                    tripData.setLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                    tripData.setSpeed(cursor.getString(cursor.getColumnIndex(COLUMN_SPEED)));
                    tripData.setIsSynced(cursor.getString(cursor.getColumnIndex(COLUMN_LAT_LONG_STATUS)));
                    tripData.setTimeTaken(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                    tripData.setOrderID(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_ID)));
                    tripDataArray.add(tripData);
                } while (cursor.moveToNext());
            }

            // close db connection
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return OrderDatas list
        return tripDataArray;
    }

    public void deleteTripLog(String time) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_LAT_LNG, COLUMN_TIMESTAMP + "=?", new String[]{time});
            Log.e("TAG", "deleteTripLog:Deleted " + time);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* --------------------------------- ORDER LOG ----------------------------------*/

    public long insertOrderLogData(OrderData orderData) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        try {
            values.put(COLUMN_ORDER_ID, orderData.getOrderId());
            values.put(COLUMN_STATUS, orderData.getStatus());
            values.put(COLUMN_DECANT_QUANTITY, orderData.getDecantQty());
            values.put(COLUMN_DECANT_LATITUDE, orderData.getDecantLatitude());
            values.put(COLUMN_DECANT_LONGITUDE, orderData.getDecantLongitude());
            values.put(COLUMN_TOTALIZER_START, orderData.getOrderTotalizerStart());
            values.put(COLUMN_TOTALIZER_END, orderData.getOrderTotalizerEnd());
            values.put(COLUMN_ODOMETER_START, orderData.getOdometerStart());
            values.put(COLUMN_ODOMETER_END, orderData.getOdometerEnd());
            values.put(COLUMN_TRANSACTION_STATUS, orderData.getOrderTransactionStatus());
            values.put(COLUMN_RFID, orderData.getOrderRFID()); //assets
            values.put(COLUMN_RO_RFID, orderData.getRoRFID());
            values.put(COLUMN_MASTER_TAG, orderData.getMasterTag());
            values.put(COLUMN_COMMENTS, orderData.getComments());
            values.put(COLUMN_OTP_CODE, orderData.getOtpCode());
            values.put(COLUMN_TIMESTAMP, getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // insert row
        long id = db.insert(TABLE_ORDER_LOG, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<OrderData> getAllOrderLogData() {
        List<OrderData> OrderDatas = new ArrayList<>();
        try {

            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ORDER_LOG;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    OrderData order = new OrderData();
                    order.setOrderId(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_ID)));
                    order.setStatus(cursor.getString(cursor.getColumnIndex(COLUMN_STATUS)));
                    order.setDecantQty(cursor.getString(cursor.getColumnIndex(COLUMN_DECANT_QUANTITY)));
                    order.setDecantLatitude(cursor.getString(cursor.getColumnIndex(COLUMN_DECANT_LATITUDE)));
                    order.setDecantLongitude(cursor.getString(cursor.getColumnIndex(COLUMN_DECANT_LONGITUDE)));
                    order.setOrderTotalizerStart(cursor.getString(cursor.getColumnIndex(COLUMN_TOTALIZER_START)));
                    order.setOrderTotalizerEnd(cursor.getString(cursor.getColumnIndex(COLUMN_TOTALIZER_END)));
                    order.setOdometerStart(cursor.getString(cursor.getColumnIndex(COLUMN_ODOMETER_START)));
                    order.setOdometerEnd(cursor.getString(cursor.getColumnIndex(COLUMN_ODOMETER_END)));
                    order.setOrderRFID(cursor.getString(cursor.getColumnIndex(COLUMN_RFID)));
                    order.setRoRFID(cursor.getString(cursor.getColumnIndex(COLUMN_RO_RFID)));
                    order.setMasterTag(cursor.getString(cursor.getColumnIndex(COLUMN_MASTER_TAG)));
                    order.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                    order.setComments(cursor.getString(cursor.getColumnIndex(COLUMN_COMMENTS)));
                    order.setOtpCode(cursor.getString(cursor.getColumnIndex(COLUMN_OTP_CODE)));
                    OrderDatas.add(order);
                } while (cursor.moveToNext());
            }
            cursor.close();
            // close db connection
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // return OrderDatas list
        return OrderDatas;
    }

    public void deleteOrderLog(String time) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_ORDER_LOG, COLUMN_TIMESTAMP + "=?", new String[]{time});
            Log.e("TAG", "deleteOrderLog:Deleted " + time);
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /* -------------------------------ODOMETER LOG -----------------------------------*/

    public long insertOdometerLogData(OrderData orderData) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        try {
            // `id` and `timestamp` will be inserted automatically.
            values.put(COLUMN_ORDER_ID, orderData.getOrderId());
            values.put(COLUMN_ODOMETER_START, orderData.getOdometerStart());
            values.put(COLUMN_ODOMETER_END, orderData.getOdometerEnd());
            values.put(COLUMN_TIMESTAMP, getDateTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // insert row
        long id = db.insert(TABLE_ODOMETER_LOG, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<OrderData> getAllOdometerLogData() {
        List<OrderData> OdometerDatas = new ArrayList<>();
        try {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_ODOMETER_LOG;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    OrderData order = new OrderData();
                    order.setOrderId(cursor.getString(cursor.getColumnIndex(COLUMN_ORDER_ID)));
                    order.setOdometerStart(cursor.getString(cursor.getColumnIndex(COLUMN_ODOMETER_START)));
                    order.setOdometerEnd(cursor.getString(cursor.getColumnIndex(COLUMN_ODOMETER_END)));
                    order.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_TIMESTAMP)));
                    OdometerDatas.add(order);
                } while (cursor.moveToNext());
            }
            cursor.close();
            // close db connection
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return OdometerDatas;
    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
