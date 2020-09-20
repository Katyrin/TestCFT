package com.example.testcft;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BlankFragment extends DialogFragment {
    private static int viewInt;
    private String nameCurrency;
    private int nominalInt;
    private double currencyDouble;
    private double resultDouble;
    private int inputRubles = 0;
    private Context context;
    private String charCode;
    // счетчик количества попыток открыть диалоговое окно
    static int count = 0;
    private String inStr;

    public void setNameCurrency(String str){
        nameCurrency = str;
    }
    public void setNominalInt(int i){ nominalInt = i;}
    public void setCurrencyDouble(double d){ currencyDouble = d; }
    public void setContext(Context context){ this.context = context; }
    public void setCharCode(String charCode) { this.charCode = charCode; }

    public static BlankFragment newInstance(int viewInt) {
        BlankFragment.viewInt = viewInt;
        return new BlankFragment();
    }

    public BlankFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(viewInt, container, false);
        final EditText ipt = view.findViewById(R.id.rub_enter);
        TextView currencyView = view.findViewById(R.id.currency);
        Button countButton = view.findViewById(R.id.count_view_button);
        Button cancelButton = view.findViewById(R.id.cancel_view_button);
        final TextView countResult = view.findViewById(R.id.count_result_view);
        TextView charResult = view.findViewById(R.id.char_result_view);

        currencyView.setText(nameCurrency);
        ipt.setInputType(InputType.TYPE_CLASS_PHONE);
        charResult.setText(charCode);

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                inStr = ipt.getText().toString();
                if (!inStr.equals("")){
                    inputRubles = Integer.parseInt(inStr);
                    resultDouble = inputRubles / currencyDouble * nominalInt;
                    BigDecimal bd = new BigDecimal(Double.toString(resultDouble));
                    bd = bd.setScale(4, RoundingMode.HALF_UP);
                    countResult.setText(String.valueOf(bd.doubleValue()));
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
                count = 0;
            }
        });
        return view;
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        count = 0;
    }
}
