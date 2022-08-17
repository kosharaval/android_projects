package com.example.reza.passdatafromactivitytofragmentusingfragmentobjectitself;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Reza on 2018-03-10.
 */

public class FragmentA extends Fragment{

    private Button btnAdd;
    private TextView txvResult;
    private int firstNumber = 0;
    private int secondNumber = 0;

    private MainActivity.Employee employee;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_a, container, false);

        txvResult = (TextView) view.findViewById(R.id.txvResult);

        btnAdd = (Button) view.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTwoNumbers(firstNumber, secondNumber);
                setEmployee(employee);
            }
        });

        return view;
    }
    private void addTwoNumbers(int firstNumber, int secondNumber) {

        int result = firstNumber + secondNumber;
        txvResult.setText("Result : " + result + "\n" + "Name: " + employee.Name + "\n" +
                           "Number: " + employee.employeeNumber);
    }

    public void setData(int firstNumber, int secondNumber) {

        this.firstNumber = firstNumber;
        this.secondNumber = secondNumber;
    }

    public void setEmployee(MainActivity.Employee employee) {
        this.employee = employee;
    }
}
