package com.tilloop1.ratiobarcharts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements ShowResult.ClickDialogListener{

    // Class members
    EditText etTotal;
    Button btnCompute;
    ArrayList<EditText> etList;
    int total;
    double ratios[];
    Iterator<EditText> iter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ratios = new double[5];

        // Initialize views from the layout
        etTotal = (EditText) findViewById(R.id.etTotal);
        btnCompute = (Button) findViewById(R.id.btnCompute);

        // Form array list of EditTexts for easier iteration
        etList = new ArrayList<EditText>();
        etList.add((EditText) findViewById(R.id.etA));
        etList.add((EditText) findViewById(R.id.etB));
        etList.add((EditText) findViewById(R.id.etC));
        etList.add((EditText) findViewById(R.id.etD));
        etList.add((EditText) findViewById(R.id.etE));

        iter = etList.iterator();
    }

    /********************************************************************************************
     * Validates all inputs on the activity to check none of them is empty and the values entered
     * for ratios are not greater than the total.
     * @param etList: the list containing all EditText objects for which the text will be validated
     * @return whether the inputs are valid (TRUE) or not (FALSE)
     */
    private boolean validateInput(ArrayList<EditText> etList) {

        // Local variables for this function
        boolean isValid = true;
        String valueEntered;
        iter = etList.iterator();

        if (etTotal.getText().toString().isEmpty()) {
            isValid = false;
            etTotal.setError("Please enter a value!");
        }
        while (iter.hasNext()) {

            EditText currentET = iter.next();

            // Check if all the fields have valid values
            valueEntered = currentET.getText().toString();
            if (valueEntered.isEmpty()) {
                // The field is empty
                isValid = false;
                currentET.setError("Please enter a value!");
            }
            else if (!etTotal.getText().toString().isEmpty()) {

                // Variable total initialized
                total = Integer.valueOf(etTotal.getText().toString());

                if (total < Integer.valueOf(valueEntered)) {

                    // Value entered is greater than the total points, hence invalid
                    isValid = false;
                    currentET.setError("Value should be less than total!");
                }
            }
            else
            {
                currentET.setError("");
            }
        }
        return isValid;
    }

    /********************************************************************************************
     * The method that is called when Compute button is clicked
     * @param view: The view that called the method i.e. btnCompute
     */
    public void Compute(View view){

        //Variables that will be used only by this method
        double temp;
        int i = 0;
        float val = 0;

        // Validate if all inputs are valid
        if(validateInput(etList))
        {
            iter = etList.iterator();
            while(iter.hasNext()) {
                EditText current = iter.next();
                temp = Double.valueOf(current.getText().toString());
                ratios[i++] = (temp/total) * 100.0;
            }
            showAlertDialog();
        }
    }

    /**********************************************************************************************
     *  The function that shows the alert dialog window when COMPUTE is clicked.
     *********************************************************************************************/
    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ShowResult alertDialog = ShowResult.newInstance(ratios);
        alertDialog.show(fm, "Grade Distribution");
    }


    /*********************************************************************************************
     * The function that gets called when dialog is finished.
     *********************************************************************************************/
    @Override
    public void onFinishDialog() {
        Intent i = new Intent(this, BarGraphActivity.class);
        Bundle bundle = new Bundle();
        bundle.putDoubleArray("ratios", ratios);
        i.putExtras(bundle);
        startActivity(i);
    }
}