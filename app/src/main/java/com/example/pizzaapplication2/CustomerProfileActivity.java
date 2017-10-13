package com.example.pizzaapplication2;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Paint;
import android.support.v4.util.LogWriter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CustomerProfileActivity extends AppCompatActivity {

    DBHelper dbHelper;
    Cursor cursor;
    public static final String NAME = "com.example.pizzaapplication.MESSAGE";
    public static final String ADDRESS = "com.example.pizzaappplication.MESSAGE";
    public static final String EMAIL = "com.example.pizzaappplicatioon.MESSAGE";
    public static final String PHONE = "com.example.piizzaappplication.MESSAGE";
    public static final String CUSTID = "com.example.piizzaappplicatiooon.MESSSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextView textView = (TextView)findViewById(R.id.textView);
        //textView.setPaintFlags(textView.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        setContentView(R.layout.activity_customer_profile);
        dbHelper = new DBHelper(this);
    }

    /*Call this when button is pressed*/
    public void pickPizzaDetails(View view){
            String custId = "";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            CharSequence text = "Please complete all fields.";
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            // Do something in response to button
            EditText name = (EditText)findViewById(R.id.editText);
            EditText email = (EditText)findViewById(R.id.editText2);
            EditText address = (EditText)findViewById(R.id.editText3);
            EditText phone = (EditText)findViewById(R.id.editText4);


            if((name.getText().toString().isEmpty()) || (email.getText().toString().isEmpty()) || (address.getText().toString().isEmpty())
                    || (phone.getText().toString().isEmpty())){
                toast.show();
            }
            else
            {
                toast.cancel();
                Intent intent = new Intent(this, TypeOfOrder.class);
                Intent intent2 = new Intent(this, PizzaDetailsActivity.class);

                intent.putExtra(NAME, name.getText().toString());
                intent.putExtra(ADDRESS, address.getText().toString());
                intent.putExtra(EMAIL, email.getText().toString());
                intent.putExtra(PHONE, phone.getText().toString());
                intent.putExtra("caller", CustomerProfileActivity.class.toString());

                intent2.putExtra(NAME, name.getText().toString());
                intent2.putExtra(ADDRESS, address.getText().toString());
                intent2.putExtra(EMAIL, email.getText().toString());
                intent2.putExtra(PHONE, phone.getText().toString());
                intent2.putExtra("caller", CustomerProfileActivity.class.toString());

                cursor = dbHelper.getCustomer(name.getText().toString(),email.getText().toString(),
                                address.getText().toString(),phone.getText().toString() );
                Log.d("cursorCount: ", String.valueOf(cursor.getCount()));
                if(cursor.getCount() > 0) {
                    cursor.moveToFirst();
                    custId = String.valueOf(cursor.getInt
                            (cursor.getColumnIndex(dbHelper.CUSTOMER_COLUMN_ID)));
                    intent.putExtra(CUSTID, custId);
                    startActivity(intent);
                }else{
                    long customerId = dbHelper.insertCustomer(name.getText().toString(),email.getText().toString(),
                            address.getText().toString(),phone.getText().toString());
                     custId = String.valueOf(customerId);
                    intent2.putExtra(CUSTID, custId);
                    startActivity(intent2);
                }
            }

    }

}
