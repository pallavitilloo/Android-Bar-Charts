package com.tilloop1.ratiobarcharts;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

/***************************************************************************************************
 *
 *  The Dialog Fragment shows the message showing ratios of the numbers in a dialog box
 *  @author Pallavi Tilloo
 *
 **************************************************************************************************/

public class ShowResult extends DialogFragment implements DialogInterface.OnClickListener {

    static double[] ratios = new double[5];
    static char[] alphabets = {'A','B','C','D','E'};
    String msg_ratios;
    StringBuilder sb = new StringBuilder();

    // Defines the listener interface with a method passing back data result.
    public interface ClickDialogListener {
        void onFinishDialog();
    }

    public ShowResult() {
        // Empty constructor required for DialogFragment
    }

    // Constructor to initialize the question no for the fragment
    public static ShowResult newInstance(double[] calc_percents) {
        ShowResult frag = new ShowResult();
        ratios = calc_percents;
        return frag;
    }

    /********************************************************************************************
     * The function creates a formatted string with values of the ratios to be shown in the dialog
     * @param calc_percents: Array of the percents calculated by Main Activity
     */
    private void formStringFromRatios(double[] calc_percents){
        sb.append(getString(R.string.lblPopUpMsg));
        sb.append("\n");

        for(int i =0; i<calc_percents.length; i++){
            sb.append(alphabets[i]);
            sb.append("s : ");
            sb.append(String.valueOf(calc_percents[i]));
            sb.append(" %\n");
        }
        msg_ratios = sb.toString();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Create the dialog fragment with the supplied title, message and buttons
        formStringFromRatios(ratios);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(msg_ratios);
        alertDialogBuilder.setPositiveButton(R.string.btnPlot, this::onClick);
        return alertDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        // Go back to activity to show bar charts
        ClickDialogListener listener = (ClickDialogListener) getActivity();
        listener.onFinishDialog();
        dismiss();
    }
}

/*************************************** End of class *******************************************/
