package com.example.pizzaapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Dunni on 9/23/2017.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "SQLiteExample.db";
    private static final int DATABASE_VERSION = 2;

    //First Table - Customer
    private static final String CUSTOMER_TABLE_NAME = "customer";
    public static final String CUSTOMER_COLUMN_ID = "_id";
    public static final String CUSTOMER_COLUMN_NAME = "name";
    public static final String CUSTOMER_COLUMN_EMAIL = "email";
    public static final String CUSTOMER_COLUMN_ADDRESS = "address";
    public static final String CUSTOMER_COLUMN_PHONE = "phone";

    //Second Table - Order
    private static final String ORDER_TABLE_NAME = "orders";
    public static final String ORDER_COLUMN_ID = "_id";
    public static final String ORDER_COLUMN_CUSTOMER = "customerId";
    public static final String ORDER_COLUMN_COST = "cost";
    public static final String ORDER_COLUMN_DATE = "date";

    //Third Table - Pizza
    private static final String PIZZA_TABLE_NAME = "pizza";
    public static final String PIZZA_COLUMN_ID = "_id";
    public static final String PIZZA_COLUMN_ORDER = "orderId";
    public static final String PIZZA_COLUMN_SIZE = "size";
    public static final String PIZZA_COLUMN_CRUST = "crust";
    public static final String PIZZA_COLUMN_PROTEIN = "protein";
    public static final String PIZZA_COLUMN_SAUCE = "sauce";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE " + CUSTOMER_TABLE_NAME +
            " (" + CUSTOMER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
            CUSTOMER_COLUMN_NAME + " TEXT, " +
            CUSTOMER_COLUMN_ADDRESS + " TEXT, " +
            CUSTOMER_COLUMN_EMAIL + " TEXT, " +
            CUSTOMER_COLUMN_PHONE + " TEXT )");

        db.execSQL("CREATE TABLE " + ORDER_TABLE_NAME +
                " (" + ORDER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                ORDER_COLUMN_CUSTOMER + " INTEGER, " +
                ORDER_COLUMN_DATE + " TEXT, " +
                ORDER_COLUMN_COST + " REAL)");

        db.execSQL("CREATE TABLE " + PIZZA_TABLE_NAME +
                "(" + PIZZA_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                PIZZA_COLUMN_ORDER + " INTEGER, " +
                PIZZA_COLUMN_SIZE + " TEXT, " +
                PIZZA_COLUMN_CRUST + " TEXT, " +
                PIZZA_COLUMN_PROTEIN + " TEXT, " +
                PIZZA_COLUMN_SAUCE + " TEXT )");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ORDER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PIZZA_TABLE_NAME);
        onCreate(db);
    }

    //insert into customer table, returning the primary key value of the new row
    public long insertCustomer(String name, String email, String address, String phone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(CUSTOMER_COLUMN_NAME, name);
        values.put(CUSTOMER_COLUMN_EMAIL, email);
        values.put(CUSTOMER_COLUMN_ADDRESS, address);
        values.put(CUSTOMER_COLUMN_PHONE, phone);

        return db.insert(CUSTOMER_TABLE_NAME,null,values);
    }

    //insert into order table, returning the primary key value of the new row
    public long insertOrder(int customerId, double cost, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(ORDER_COLUMN_CUSTOMER, customerId);
        values.put(ORDER_COLUMN_COST, cost);
        values.put(ORDER_COLUMN_DATE, date);

        return db.insert(ORDER_TABLE_NAME,null,values);
    }

    //insert into pizza table, returning the primary key value of the new row
    public long insertPizza(String size, String crust, String protein, String sauce, int orderId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(PIZZA_COLUMN_SIZE, size);
        values.put(PIZZA_COLUMN_CRUST, crust);
        values.put(PIZZA_COLUMN_PROTEIN, protein);
        values.put(PIZZA_COLUMN_SAUCE, sauce);
        values.put(PIZZA_COLUMN_ORDER, orderId);

        return db.insert(PIZZA_TABLE_NAME,null,values);
    }

    /**Return number of rows in a specified table*/
    public int numberOfRows(String table_Name) {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, table_Name);
        return numRows;
    }

    //Update Customer
    public boolean updateCustomer(Integer id, String name, String email, String address, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CUSTOMER_COLUMN_NAME, name);
        values.put(CUSTOMER_COLUMN_EMAIL, email);
        values.put(CUSTOMER_COLUMN_ADDRESS, address);
        values.put(CUSTOMER_COLUMN_PHONE, phone);
        db.update(CUSTOMER_TABLE_NAME,values, CUSTOMER_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    //Update Order
    public boolean updateOrder(Integer id, int customerId, double cost, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ORDER_COLUMN_CUSTOMER, customerId);
        values.put(ORDER_COLUMN_COST, cost);
        values.put(ORDER_COLUMN_DATE, date);
        db.update(ORDER_TABLE_NAME,values, ORDER_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    //Update Pizza
    public boolean updatePizza(Integer orderId, String size, String crust, String protein, String sauce) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PIZZA_COLUMN_ORDER, orderId);
        values.put(PIZZA_COLUMN_SIZE, size);
        values.put(PIZZA_COLUMN_CRUST, crust);
        values.put(PIZZA_COLUMN_PROTEIN, protein);
        values.put(PIZZA_COLUMN_SAUCE, sauce);
        db.update(PIZZA_TABLE_NAME, values, PIZZA_COLUMN_ORDER + " = ? ", new String[] { Integer.toString(orderId) });
        return true;
    }

    //Delete Customer
    public Integer deleteCustomer(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(CUSTOMER_TABLE_NAME,
                CUSTOMER_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    //Delete Order
    public Integer deleteOrder(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ORDER_TABLE_NAME,
                ORDER_COLUMN_ID + " = ? ",
                new String[] { Integer.toString(id) });
    }

    //Delete Pizza
    public Integer deletePizza(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(PIZZA_TABLE_NAME,
                PIZZA_COLUMN_ORDER + " = ? ",
                new String[] { Integer.toString(id) });
    }

    //Return Customer
    public Cursor getCustomer(String name, String email, String address, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + CUSTOMER_TABLE_NAME + " WHERE " +
                CUSTOMER_COLUMN_NAME + " = ? AND " +
                CUSTOMER_COLUMN_EMAIL + " = ? AND " +
                CUSTOMER_COLUMN_ADDRESS + " = ? AND " +
                CUSTOMER_COLUMN_PHONE + " = ?",
                new String[]{name, email, address, phone});
        return res;
    }

    //Return all Orders for a particular Customer
    public Cursor getOrder(int customer) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery("SELECT * FROM " + ORDER_TABLE_NAME
                        + " WHERE " +
                        ORDER_COLUMN_CUSTOMER + " = ? ",
                new String[]{String.valueOf(customer)});
        return res;
    }

    //Return Pizza details for a particular order
    public Cursor getPizza(int orderId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + PIZZA_TABLE_NAME
                + " WHERE " + PIZZA_COLUMN_ORDER + " = ? ", new String[] {Integer.toString(orderId)});
        return res;
    }

    public Cursor getCustomers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + CUSTOMER_TABLE_NAME, null );
        return res;
    }
    public Cursor getOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + ORDER_TABLE_NAME, null );
        return res;
    }
    public Cursor getPizzas() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "SELECT * FROM " + PIZZA_TABLE_NAME, null );
        return res;
    }

}
