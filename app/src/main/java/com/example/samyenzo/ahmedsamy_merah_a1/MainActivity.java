package com.example.samyenzo.ahmedsamy_merah_a1;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Contacts;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity
{
    //declared field variables for all UI elements
    Spinner spnTip;
    Spinner spnPeople;
    EditText edtAmount;
    CheckBox checkHST;
    TextView txtTip;
    TextView txtTotal;
    TextView txtPerPerson;
    Button btnClear;

    //Inner class for the clear button
    private class ClearFieldsListener implements View.OnClickListener
    {
        //method to clear the fields and selections
        @Override
        public void onClick(View v)
        {

            //clears the amount edit text
            edtAmount.setText("");

            //conditional statements to see if the check box is checked
            checkHST.setChecked(false);

            //if statements for spinners to reset them to the first element
            spnTip.setSelection(0);
            spnPeople.setSelection(0);

            //clears the text views of the totals
            txtTip.setText("");
            txtTotal.setText("");
            txtPerPerson.setText("");
        }
    }

    private class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            calculation();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // No change
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //method call for the entire setup of views
        viewSetup();

        //Creating the array adapter for the spinner that contains the
        //amount of people by using the Guest class created
        ArrayList<Guest> people = Guest.getGuests(this);

        ArrayAdapter<Guest> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, people
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting the adapter for the UI
        spnPeople.setAdapter(adapter);

        // Attach event listeners
        setupListeners();

    }

    private void setupListeners()
    {
        // Edit text listener
        edtAmount.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No change
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                int input = s.length();

                //check if the user has inputed anything
                if (input > 0)
                {
                    calculation();

                    //set the visibility of the TextViews
                    txtTip.setVisibility(View.VISIBLE);
                    txtTotal.setVisibility(View.VISIBLE);

                    //if the amount of people is greater than 1 the per person view has to be visible
                    if(!spnPeople.getSelectedItem().toString().equals("1"))
                    {
                        txtPerPerson.setVisibility(View.VISIBLE);
                    }
                }

                //if no input is provided nothing will be shown
                else
                {
                    txtTip.setVisibility(View.GONE);
                    txtTotal.setVisibility(View.GONE);
                    txtPerPerson.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                // No change
            }
        });

        // Spinners
        spnTip.setOnItemSelectedListener(new MyOnItemSelectedListener());
        spnPeople.setOnItemSelectedListener(new MyOnItemSelectedListener());

        // Clear button
        btnClear.setOnClickListener(new ClearFieldsListener());


        //checkbox
        checkHST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
            calculation();
            }
        });
    }


    private void calculation()
    {

        //variables to hold amount
        double amt;
        //setting a constant value for tax as it does not change
        double tax = 1.13;

        // Get all data from fields
        try
        {
            amt = Double.parseDouble(edtAmount.getText().toString());
        }
        catch(Exception e)
        {
            return;
        }

        //getting all the data from spinners
        double tipPercent = Double.parseDouble(spnTip.getSelectedItem().toString());
        double numPeople = Double.parseDouble(spnPeople.getSelectedItem().toString());

        double totalBill;
        double tipDivision;
        double tipped;
        double perPerson;

        // Choose which calculation to do based on the check box
        if(checkHST.isChecked())
        {
            tipped = amt*(tipPercent/100);
            totalBill = (amt + (tipped)) * tax;
            tipDivision = tipped;
            txtTotal.setText(getResources().getString(R.string.txt_Total, totalBill));
            txtTip.setText(getResources().getString(R.string.txt_tipTotal, tipDivision));

            //show per person if more than 1 is selected
            if(numPeople > 1)
            {
                perPerson = totalBill/numPeople;
                txtPerPerson.setText(getResources().getString(R.string.txt_perPerson, perPerson));
                txtPerPerson.setVisibility(View.VISIBLE);
            }

            else
            {
                txtPerPerson.setVisibility(View.GONE);
            }
        }

        else
        {
            tipped = amt*(tipPercent/100);
            totalBill = (amt + (tipped));
            tipDivision = tipped;
            txtTotal.setText(getResources().getString(R.string.txt_Total, totalBill));
            txtTip.setText(getResources().getString(R.string.txt_tipTotal, tipDivision));

            //show per person if more than 1 is selected
            if(numPeople > 1)
            {
                perPerson = totalBill/numPeople;
                txtPerPerson.setText(getResources().getString(R.string.txt_perPerson, perPerson));
                txtPerPerson.setVisibility(View.VISIBLE);
            }

            else
            {
                txtPerPerson.setVisibility(View.GONE);
            }
        }


    }

    //Class dedicated to associate Views other than spinners in code to not cluster onCreate
    private void viewSetup()
    {
        //Associating the spinners with the right ID's
        spnTip = (Spinner) findViewById(R.id.spnr_percents);
        spnPeople = (Spinner) findViewById(R.id.spnr_people);
        edtAmount = (EditText)findViewById(R.id.edt_amount);
        checkHST = (CheckBox)findViewById(R.id.chckBox_HST);
        txtTip = (TextView)findViewById(R.id.txt_TipBill);
        txtTotal = (TextView)findViewById(R.id.txt_TotalBill);
        txtPerPerson = (TextView)findViewById(R.id.txt_perGuest);
        btnClear = (Button)findViewById(R.id.btn_Clear);
    }
}
