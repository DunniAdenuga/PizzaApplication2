package com.example.pizzaapplication2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PizzaDetailsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    DBHelper dbHelper;
    public static final String NAME = "com.example.pizzaapplication.MESSAGE";
    public static final String ADDRESS = "com.example.pizzaapplicatiion.MESSAGE";
    public static final String COST = "com.example.pizzaapplicatiiion.MESSAGE";

    public static final String SIZE = "com.exammmple.piizzaappplicationtion.MESSAGE";
    public static final String CRUST = "com.exammmple.piizzaappplication.MESSAGELOV";
    public static final String PROTEIN = "coooooom.exammmple.piizzaappplication.MESSAGE";
    public static final String SAUCE = "coooooom.exammmple.piizzaappplication.MEMESSSSAGE";
    //public static final String COST = "comcom.exammmple.piizzaappplication.MESSAGE";

    double fullCost;
    int previous = Integer.MIN_VALUE;
    int current = Integer.MIN_VALUE;
    int state = 1;
    Intent intent ;
    String size = "";
    String crust = "";
    String protein = "";
    ArrayList<String> proteins = new ArrayList<>();
    String sauce = "";
    ArrayList<String> sauces = new ArrayList<>();
    String caller = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_details);
        dbHelper = new DBHelper(this);

        //Get the Intent that started this activity and extract the string
        intent = getIntent();
        caller     = intent.getStringExtra("caller");

        String message = "";
        if (caller.equals(CustomerProfileActivity.class.toString())) {
             message = intent.getStringExtra(CustomerProfileActivity.NAME) + ", please spice up your Pizza!";
        }else if (caller.equals(TypeOfOrder.class.toString())) {
             message = intent.getStringExtra(TypeOfOrder.NAME) + ", please spice up your Pizza!";
        }

        //Capture the layout's TextView and set the string as its text
        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText(message);

        Spinner spinner = (Spinner) findViewById(R.id.crust_spinner);
    // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.crust_type, android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    /**Called When the order button is Clicked*/
    public void showOrder(View view){

        Intent intent2 = new Intent(this, OrderPage.class);
        int customerId = 0;

        if (caller.equals(CustomerProfileActivity.class.toString())) {
            intent2.putExtra(NAME, intent.getStringExtra(CustomerProfileActivity.NAME));
            intent2.putExtra(ADDRESS, intent.getStringExtra(CustomerProfileActivity.ADDRESS));
            intent2.putExtra(COST, String.valueOf(fullCost));
             customerId = Integer.parseInt(intent.getStringExtra(CustomerProfileActivity.CUSTID));
        }else if (caller.equals(TypeOfOrder.class.toString())) {
            intent2.putExtra(NAME, intent.getStringExtra(TypeOfOrder.NAME));
            intent2.putExtra(ADDRESS, intent.getStringExtra(TypeOfOrder.ADDRESS));
            intent2.putExtra(COST, String.valueOf(fullCost));
            Log.i("CustId: ", TypeOfOrder.CUSTID);
            customerId = Integer.parseInt(intent.getStringExtra(TypeOfOrder.CUSTID));
        }

        intent2.putExtra("caller", PizzaDetailsActivity.class.toString());

        protein = "";
        sauce = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm aa");
        long orderId = dbHelper.insertOrder(customerId, fullCost, df.format(Calendar.getInstance().getTime()));
        for(int i = 0; i < proteins.size(); i++){
            protein = protein + proteins.get(i) + ", ";
        }
        for(int i = 0; i < sauces.size(); i++){
            sauce = sauce + sauces.get(i) + ", ";
        }
        dbHelper.insertPizza(size,crust,protein.substring(0, protein.length()-2),
                sauce.substring(0, sauce.length()-2), (int)orderId );
        intent2.putExtra(SIZE, size);
        intent2.putExtra(CRUST, crust);
        intent2.putExtra(PROTEIN, protein.substring(0, protein.length()-2));
        intent2.putExtra(SAUCE, sauce.substring(0, sauce.length()-2));
        intent2.putExtra(COST, String.valueOf(fullCost));
        startActivity(intent2);
    }

    //Gets called if checked
    public void onCheckClicked(View view){

        TextView textView = (TextView)findViewById(R.id.textView8);
        String message = "";

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            /*Protein*/
            case R.id.checkBox:
                if (checked) {
                    proteins.add(getString(R.string.chicken));
                    fullCost = fullCost + 2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);
                }else{
                    proteins.remove(getString(R.string.chicken));
                    fullCost = fullCost -  2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);}
                break;
            case R.id.checkBox2:
                if (checked) {
                    proteins.add(getString(R.string.sausage));
                    fullCost = fullCost + 2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);
                }else{
                    proteins.remove(getString(R.string.sausage));
                    fullCost = fullCost -  2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);}
                break;
            case R.id.checkBox3:
                if (checked) {
                    proteins.add(getString(R.string.bacon));
                    fullCost = fullCost + 2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);
                }else{
                    proteins.remove(getString(R.string.bacon));
                    fullCost = fullCost -  2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);}
                break;
            case R.id.checkBox4:
                if (checked) {
                    proteins.add(getString(R.string.cheese));
                    fullCost = fullCost + 2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);
                }else{
                    proteins.remove(getString(R.string.cheese));
                    fullCost = fullCost -  2.00;;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);}
                break;
            case R.id.checkBox5:
                if (checked) {
                    proteins.add(getString(R.string.pepperoni));
                    fullCost = fullCost + 2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);
                }else{
                    proteins.remove(getString(R.string.pepperoni));
                    fullCost = fullCost - 2.00;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);}
                break;
            /**Sauces*/
            case R.id.checkBox8:
                if (checked) {
                    sauces.add(getString(R.string.sauce2));
                    fullCost = fullCost + 0.50;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);
                }else{
                    sauces.remove(getString(R.string.sauce2));
                    fullCost = fullCost -  0.50;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);}
                break;
            case R.id.checkBox9:
                if (checked) {
                    sauces.add(getString(R.string.sauce1));
                    fullCost = fullCost + 0.50;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);
                }else{
                    sauces.remove(getString(R.string.sauce1));
                    fullCost = fullCost -  0.50;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);}
                break;
            case R.id.checkBox10:
                if (checked) {
                    sauces.add(getString(R.string.sauce3));
                    fullCost = fullCost + 0.50;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);
                }else{
                    sauces.remove(getString(R.string.sauce3));
                    fullCost = fullCost -  0.50;
                    message = String.format("%.2f", fullCost);
                    textView.setText(message);}
                break;
        }

    }

    /**Gets called by radio group*/
    public void onRadioButtonClicked(View view){
        //Gets called if checked
            previous = current;
            current = view.getId();
            TextView textView = (TextView)findViewById(R.id.textView8);
            String message = "";

            // Is the view now checked?
            boolean checked = ((RadioButton) view).isChecked();

            // Check which radiobutton was clicked
            if((current != previous) || (state == 1)) {
                state = 2;
                switch (view.getId()) {
            /*Sizes*/
                    case R.id.radioButton:
                        if (checked) {
                            size = "small";
                            fullCost = fullCost + 7.00;
                            if (previous == R.id.radioButton2)
                                fullCost = fullCost - 9.00;
                            else if (previous == R.id.radioButton3)
                                fullCost = fullCost - 11.00;
                            message = String.format("%.2f", fullCost);
                            textView.setText(message);
                        }
                        break;
                    case R.id.radioButton2:
                        if (checked) {
                            size = "medium";
                            fullCost = fullCost + 9.00;
                            if (previous == R.id.radioButton)
                                fullCost = fullCost - 7.00;
                            else if (previous == R.id.radioButton3)
                                fullCost = fullCost - 11.00;
                            message = String.format("%.2f", fullCost);
                            textView.setText(message);
                        }
                        break;
                    case R.id.radioButton3:
                        if (checked) {
                            size = "large";
                            fullCost = fullCost + 11.00;
                            if (previous == R.id.radioButton)
                                fullCost = fullCost - 7.00;
                            else if (previous == R.id.radioButton2)
                                fullCost = fullCost - 9.00;
                            message = String.format("%.2f", fullCost);
                            textView.setText(message);
                        }
                        break;
                }
            }
    }
    /*Gets called to get selection from Spinner*/
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        // An item was selected. You can retrieve the selected item using
        crust =  (String)parent.getItemAtPosition(pos);

    }

    public void onNothingSelected(AdapterView<?> parent){

    }

}
