package com.example.pizzaapplication2;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TypeOfOrder extends AppCompatActivity {
    DBHelper dbHelper;
    private ListView mListView;
    public static final String NAME = "com.example.pizzaapplication.MESSAGE";
    public static final String ADDRESS = "com.example.pizzaappplication.MESSAGE";
    public static final String EMAIL = "com.example.pizzaappplicatioon.MESSAGE";
    public static final String PHONE = "com.example.piizzaappplication.MESSAGE";
    public static final String CUSTID = "com.exammmple.piizzaappplication.MESSAGE";

    public static final String SIZE = "com.exammmple.piizzaappplicationtion.MESSAGE";
    public static final String CRUST = "com.exammmple.piizzaappplication.MESSAGELOV";
    public static final String PROTEIN = "coooooom.exammmple.piizzaappplication.MESSAGE";
    public static final String SAUCE = "coooooom.exammmple.piizzaappplication.MEMESSSSAGE";
    public static final String COST = "comcom.exammmple.piizzaappplication.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_of_order);
        dbHelper = new DBHelper(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra(CustomerProfileActivity.NAME);
        String email = intent.getStringExtra(CustomerProfileActivity.EMAIL);
        String address = intent.getStringExtra(CustomerProfileActivity.ADDRESS);
        String phone = intent.getStringExtra(CustomerProfileActivity.PHONE);

        Cursor cursor = dbHelper.getCustomer(name, email, address, phone);
        int customer = Integer.MIN_VALUE;
        if (cursor.moveToFirst()) {//I'm expecting one customer
            Log.i("cursor: ", String.valueOf(cursor.getCount()));
            //while (!cursor.isAfterLast()) {
            customer = cursor.getInt(cursor.getColumnIndex(dbHelper.CUSTOMER_COLUMN_ID));
            cursor.moveToNext();
            //}
        }

        cursor = dbHelper.getOrder(customer);
        Log.i("cursorOrder: ", String.valueOf(cursor.getCount()));
        ArrayList<Integer> orderIDList = new ArrayList<>();
        ArrayList<Double> orderCostList = new ArrayList<>();
        ArrayList<String> orderDateList = new ArrayList<>();

        if (cursor.moveToFirst()) {//May be more than 1 order for 1 customer
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex(dbHelper.ORDER_COLUMN_ID));
                double cost = cursor.getDouble(cursor.getColumnIndex(dbHelper.ORDER_COLUMN_COST));
                String date = cursor.getString(cursor.getColumnIndex(dbHelper.ORDER_COLUMN_DATE));

                orderIDList.add(id);
                orderCostList.add(cost);
                orderDateList.add(date);

                cursor.moveToNext();
            }
        }

        ArrayList<String[]> pizzaList = new ArrayList<>();
        for (int i = 0; i < orderIDList.size(); i++) {

            cursor = dbHelper.getPizza(orderIDList.get(i));//I'm sending the private key
            //cursor = dbHelper.getPizzas();
            // so, I expect one answer
            Log.i("orderIDListLength&ID", String.valueOf(orderIDList.size()) + " & " + String.valueOf(orderIDList.get(i)));
            Log.i("cursorPizza: ", String.valueOf(cursor.getCount()));
            cursor.moveToFirst();
            String size = cursor.getString(cursor.getColumnIndex(dbHelper.PIZZA_COLUMN_SIZE));
            String crust = cursor.getString(cursor.getColumnIndex(dbHelper.PIZZA_COLUMN_CRUST));
            String protein = cursor.getString(cursor.getColumnIndex(dbHelper.PIZZA_COLUMN_PROTEIN));
            String sauce = cursor.getString(cursor.getColumnIndex(dbHelper.PIZZA_COLUMN_SAUCE));
            String[] strings = {size, crust, protein, sauce};
            pizzaList.add(strings);
        }

        mListView = (ListView) findViewById(R.id.order_list_view);
// 1
// 2
        final String[] listItems = new String[pizzaList.size()];
        for(int y = 0; y < listItems.length; y++){
            listItems[y] = "";
        }
// 3
        for (int i = 0; i < pizzaList.size(); i++) {
            String[] pizzaListItems = pizzaList.get(i);
            for (int x = 0; x < pizzaListItems.length; x++) {
                listItems[i] = listItems[i] + pizzaListItems[x] + "\n";
            }
            listItems[i] = listItems[i] + String.valueOf(orderCostList.get(i)) + "\n";
            listItems[i] = listItems[i] + String.valueOf(orderDateList.get(i));
        }
// 4
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        mListView.setAdapter(adapter);
        //Add Listener to ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent2 = getIntent();
                Intent intent = new Intent(TypeOfOrder.this, OrderPage.class);
                intent.putExtra(NAME, intent2.getStringExtra(CustomerProfileActivity.NAME));
                intent.putExtra(ADDRESS, intent2.getStringExtra(CustomerProfileActivity.ADDRESS));
                intent.putExtra(EMAIL, intent2.getStringExtra(CustomerProfileActivity.EMAIL));
                intent.putExtra(PHONE, intent2.getStringExtra(CustomerProfileActivity.PHONE));
                intent.putExtra(CUSTID, intent2.getStringExtra(CustomerProfileActivity.CUSTID));

                String[] pizzaDetails = listItems[position].split("\n");
                intent.putExtra(SIZE, pizzaDetails[0]);
                intent.putExtra(CRUST, pizzaDetails[1]);
                intent.putExtra(PROTEIN, pizzaDetails[2]);
                intent.putExtra(SAUCE, pizzaDetails[3]);
                intent.putExtra(COST, pizzaDetails[4]);

                intent.putExtra("caller", TypeOfOrder.class.toString());

                startActivity(intent);
            }
        });

    }

    /*Happens when button is clicked*/
    public void pickPizaDetails(View view) {
        Intent intent2 = getIntent();
        Intent intent = new Intent(this, PizzaDetailsActivity.class);
        intent.putExtra(NAME, intent2.getStringExtra(CustomerProfileActivity.NAME));
        intent.putExtra(ADDRESS, intent2.getStringExtra(CustomerProfileActivity.ADDRESS));
        intent.putExtra(EMAIL, intent2.getStringExtra(CustomerProfileActivity.EMAIL));
        intent.putExtra(PHONE, intent2.getStringExtra(CustomerProfileActivity.PHONE));
        intent.putExtra(CUSTID, intent2.getStringExtra(CustomerProfileActivity.CUSTID));
        intent.putExtra("caller", TypeOfOrder.class.toString());
        startActivity(intent);

    }

}
