package com.example.reza.passdatafromactivitytofragmentusingfragmentobjectitself;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;
    private EditText etFirstNumber, etSecondNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etFirstNumber = (EditText) findViewById(R.id.etFirstNumber);
        etSecondNumber = (EditText) findViewById(R.id.etSecondNumber);

        manager = getFragmentManager();
    }

    public void sendDataToFragment(View view) {

        int firstNumber = Integer.valueOf(etFirstNumber.getText().toString());
        int secondNumber = Integer.valueOf(etSecondNumber.getText().toString());

        FragmentA fragmentA = new FragmentA();
        fragmentA.setData(firstNumber, secondNumber); // Passing primitive data type

        //Just to show that we can pass non-primitive data as well, let's pass an employee object to the Fragment
        fragmentA.setEmployee(new Employee(111222333, "Reza Abbasi"));

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.containerFragmentA, fragmentA, "fragA");
        transaction.commit();
    }

    public class Employee {

        String Name;
        int employeeNumber;

        public Employee (int employeeNumber, String Name){

            this.Name = Name ;
            this.employeeNumber = employeeNumber ;
        }

    }
}
