package com.example.budgy.ui.fixed;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgy.DatabaseHelper;
import com.example.budgy.Models.FixedAmount;
import com.example.budgy.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class FixedIncomeExpense extends Fragment implements FixedDialog.FixedDialogListener, SeekBar.OnSeekBarChangeListener {

    DatabaseHelper db;

    private FixedIncomeExpenseViewModel fixedIncomeExpenseViewModel;

    //Expenses Recycler View
    private RecyclerView fixedExpenseRecyclerView;
    private FixedIncomeExpenseAdapter fixedExpenseRecyclerViewAdapter;
    private RecyclerView.LayoutManager fixedExpenseRecyclerViewLayoutManager;

    //Income Recycler View
    private RecyclerView fixedIncomeRecyclerView;
    private FixedIncomeExpenseAdapter fixedIncomeRecyclerViewAdapter;
    private RecyclerView.LayoutManager fixedIncomeRecyclerViewLayoutManager;


    ArrayList<FixedAmountItem> fixedExpenseItemList = new ArrayList<>();
    ArrayList<FixedAmountItem> fixedIncomeItemList = new ArrayList<>();
    ArrayList<BarEntry> yAmounts = new ArrayList<BarEntry>();

    private BarChart chart;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        fixedIncomeExpenseViewModel =
                new ViewModelProvider(this).get(FixedIncomeExpenseViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fixed, container, false);



        fixedIncomeExpenseViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {


                fixedExpenseRecyclerView = root.findViewById(R.id.recyclerViewExpenses);
                fixedExpenseRecyclerView.setHasFixedSize(true);
                fixedExpenseRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
                fixedExpenseRecyclerViewAdapter = new FixedIncomeExpenseAdapter(fixedExpenseItemList);

                fixedExpenseRecyclerView.setLayoutManager(fixedExpenseRecyclerViewLayoutManager);
                fixedExpenseRecyclerView.setAdapter(fixedExpenseRecyclerViewAdapter);

                fixedExpenseRecyclerViewAdapter.setOnItemClickListener(new FixedIncomeExpenseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                    //    Toast.makeText(getContext(),"Hello"  + position,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeleteClick(int position) {
                        db.deleteFixedAmountById(fixedExpenseItemList.get(position).getId());
                        fixedExpenseItemList.remove(position);
                        fixedExpenseRecyclerViewAdapter.notifyDataSetChanged();

                        displayChart(calculateTotal(fixedIncomeItemList), calculateTotal(fixedExpenseItemList));
                    }
                });



                fixedIncomeRecyclerView = root.findViewById(R.id.recyclerViewIncome);
                fixedIncomeRecyclerView.setHasFixedSize(true);
                fixedIncomeRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
                fixedIncomeRecyclerViewAdapter = new FixedIncomeExpenseAdapter(fixedIncomeItemList);

                fixedIncomeRecyclerView.setLayoutManager(fixedIncomeRecyclerViewLayoutManager);
                fixedIncomeRecyclerView.setAdapter(fixedIncomeRecyclerViewAdapter);

                fixedIncomeRecyclerViewAdapter.setOnItemClickListener(new FixedIncomeExpenseAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                     //   Toast.makeText(getContext(),"testing purposes"  + position,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onDeleteClick(int position) {
                        db.deleteFixedAmountById(fixedIncomeItemList.get(position).getId());
                        fixedIncomeItemList.remove(position);
                        fixedIncomeRecyclerViewAdapter.notifyDataSetChanged();

                        displayChart(calculateTotal(fixedIncomeItemList), calculateTotal(fixedExpenseItemList));

                    }
                });

                ImageButton btnAddFixedIncome = (ImageButton)root.findViewById(R.id.btnaddfixedincome);
                ImageButton btnAddFixedExpense = (ImageButton)root.findViewById(R.id.btnaddfixedExpense);


                Bundle bundleIncome = new Bundle();
                bundleIncome.putString("type", "I");
                btnAddFixedIncome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FixedDialog fixedDialog = new FixedDialog();
                        fixedDialog.setTargetFragment(FixedIncomeExpense.this, 0);
                        fixedDialog.setArguments(bundleIncome);
                        fixedDialog.show(getParentFragmentManager(), "Add Income");
                    }
                });



                Bundle bundleExpense = new Bundle();
                bundleExpense.putString("type", "E");
                btnAddFixedExpense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FixedDialog fixedDialog = new FixedDialog();
                        fixedDialog.setTargetFragment(FixedIncomeExpense.this, 0);
                        fixedDialog.setArguments(bundleExpense);
                        fixedDialog.show(getParentFragmentManager(), "Add Expense");
                    }
                });
            }
        });


        db = new DatabaseHelper(getActivity());

        getAllFixedAmounts("I");
        getAllFixedAmounts("E");

        chart = root.findViewById(R.id.stackedChart);

        displayChart(calculateTotal(fixedIncomeItemList), calculateTotal(fixedExpenseItemList));

        return root;
    }


    public void getAllFixedAmounts(String type)
    {
        List<FixedAmount> fixedAmounts;

        fixedAmounts =  db.getAllFixedAmountByUserIdAndType(1,type);
        for (FixedAmount fa : fixedAmounts) {

            if(type.equals("I"))
            {
                fixedIncomeItemList.add(new FixedAmountItem(R.drawable.iconfixedincome, fa.getName(),fa.getAmount().toString(),fa.getId()));
            }
            else {
                fixedExpenseItemList.add(new FixedAmountItem(R.drawable.iconsprocurement, fa.getName(),fa.getAmount().toString(),fa.getId()));
            }
        }

    }



    @Override
    public void apply(String name, Double amount, Character amountType)
    {
        if(amountType.equals('E'))
        {
            //fixedExpenseItemList.add(new FixedAmountItem(R.drawable.iconsprocurement, name,amount.toString()));

           fixedExpenseItemList.clear();
            db.insertFixedAmount(1,name,amount, "E");
            getAllFixedAmounts("E");
            fixedExpenseRecyclerViewAdapter.notifyDataSetChanged();

        }
        else {
           // fixedIncomeItemList.add(new FixedAmountItem(R.drawable.iconfixedincome, name,amount.toString()));
            fixedIncomeItemList.clear();
            db.insertFixedAmount(1,name,amount, "I");
            getAllFixedAmounts("I");
            fixedIncomeRecyclerViewAdapter.notifyDataSetChanged();



        }

        displayChart(calculateTotal(fixedIncomeItemList), calculateTotal(fixedExpenseItemList));
    }

    public void displayChart(float totalIncome, float totalExpense){

        yAmounts.clear();
        yAmounts.add(new BarEntry(10,new float[]{totalIncome, totalExpense}));

        BarDataSet set1;
        set1 = new BarDataSet(yAmounts,"Amounts");
        set1.setDrawIcons(false);
        set1.setDrawValues(false);
        //  set1.setStackLabels(new String[]{"Income, Expense","Others"});
        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        BarData data= new BarData(set1);
        //  data.setValueFormatter(new MyValueFormatter());
        chart.setData(data);
        //chart.setFitBars(true);
        chart.getDescription().setEnabled(false);    // Hide the description
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawLabels(false);

        chart.getLegend().setEnabled(false);   // Hide the legend
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);

        chart.setDrawGridBackground(false);
        chart.animateXY(2000, 2000, Easing.Linear);
        chart.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        chart.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private float calculateTotal(ArrayList<FixedAmountItem> listAmount) {
        float sum = 0;
        if(!listAmount.isEmpty()) {
            for (FixedAmountItem item : listAmount) {
              sum +=  Double.parseDouble(item.getmText2());
            }
        }
        return sum;
    }
}
