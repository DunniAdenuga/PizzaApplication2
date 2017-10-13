package com.example.pizzaapplication2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    DBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
    }

    /**Called When the Pizza is Clicked*/
    public void startCustomerProfile(View view){
        Intent intent = new Intent(this, CustomerProfileActivity.class);
        startActivity(intent);
    }
}
