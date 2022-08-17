package com.example.budgy.ui.reports;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.budgy.DatabaseHelper;
import com.example.budgy.Models.CategoryItem;
import com.example.budgy.Models.DailyTransactions;
import com.example.budgy.Models.LoggedInUser;
import com.example.budgy.R;
import com.example.budgy.ui.dashboard.DailyTransactionItem;
import com.example.budgy.ui.dashboard.DashboardViewModel;
import com.example.budgy.ui.invite.InviteViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS;

public class Reports extends Fragment {

    private ReportsViewModel reportsViewModel;
    Spinner reportMonths;
    DatabaseHelper db;

    String loggedInUserId = LoggedInUser.getLoggedInUser().getID();
    List<DailyTransactions> monthlyTransactions = null;
    HashMap<String, String> hashmapCategoryandTotalAmount= null;

    private BarChart chart;
    private PieChart piechart;

    public static Reports newInstance() {
        return new Reports();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        db = new DatabaseHelper(getActivity());


        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reports, container, false);

        reportMonths = root.findViewById(R.id.spMonth);
        List<String> months = new ArrayList<String>();
        months.add("Bar Chart (Monthly Transactions)");
        months.add("Pie Chart (Monthly Category Wise");

        ArrayAdapter<String> adapterIncome = new ArrayAdapter<String>(getContext(), R.layout.support_simple_spinner_dropdown_item, months);
        adapterIncome.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterIncome.notifyDataSetChanged();
        reportMonths.setAdapter(adapterIncome);

        Button btnGenerate = root.findViewById(R.id.button);
        chart = root.findViewById(R.id.barchart);
        piechart = root.findViewById(R.id.piechart);
        btnGenerate.setOnClickListener(new View.OnClickListener()
        {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v)
            {
                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                String currentDate = formatter.format(date);
                Date date2 = new Date(date.getTime() - 604800000L);
                String startDate = formatter.format(date2);
                LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Integer month = localDate.getMonthValue();
                Integer year = localDate.getYear();
                String startOfMonth = year + month.toString() + "01";

                 monthlyTransactions = null;
                 monthlyTransactions = db.getAllDailyTransactionByUserIdAndDateRange(
                        Integer.parseInt(loggedInUserId),
                        Integer.parseInt(startDate),
                        Integer.parseInt(currentDate)
                );


                if(reportMonths.getSelectedItemPosition()==0)
                {
                    initializeBarChart();
                   createBarChart();

                   chart.setVisibility(View.VISIBLE);
                    piechart.setVisibility(View.INVISIBLE);

                }else{
                    chartCategoryCals();

                    chart.setVisibility(View.INVISIBLE);
                    piechart.setVisibility(View.VISIBLE);
                }






            }
        });

        chart.setVisibility(View.INVISIBLE);
        piechart.setVisibility(View.INVISIBLE);
        return root;
    }



    private void initializeBarChart() {
        chart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart.setMaxVisibleValueCount(4);
        chart.getXAxis().setDrawGridLines(false);
        // scaling can now only be done on x- and y-axis separately
        chart.setPinchZoom(false);

        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.getAxisLeft().setEnabled(true);
        chart.getXAxis().setDrawGridLines(false);
        // add a nice and smooth animation
        chart.animateY(1500);


        chart.getLegend().setEnabled(false);

        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawLabels(true);
        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getXAxis().setEnabled(true);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.invalidate();

    }

    private void createBarChart() {
        ArrayList<BarEntry> values = new ArrayList<>();
        ArrayList<String> severityStringList = new ArrayList<>();

        int i =0;
        for(DailyTransactions monthly : monthlyTransactions)
        {
            values.add(new BarEntry(i, Float.parseFloat(monthly.getAmount().toString())));
            severityStringList.add(monthly.getTitle());
            i++;
        }



        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColors(MATERIAL_COLORS);
            set1.setDrawValues(true);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            chart.setData(data);
            chart.setVisibleXRange(1,4);
            chart.setFitBars(true);
            XAxis xAxis = chart.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setGranularityEnabled(true);
            xAxis.setValueFormatter(new IndexAxisValueFormatter(severityStringList));//setting String values in Xaxis
            for (IDataSet set : chart.getData().getDataSets())
                set.setDrawValues(!set.isDrawValuesEnabled());

            chart.invalidate();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        reportsViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void chartCategoryCals(){

        hashmapCategoryandTotalAmount = new HashMap<String, String>();
        List<CategoryItem> lstCategories = db.getCategoryNameByUserId(loggedInUserId);
        List<PieEntry> yvals = new ArrayList<>();
        for(CategoryItem ca : lstCategories)
        {
            Double totalAmount = 0.0;
            for(DailyTransactions monthly : monthlyTransactions)
            {
               if(monthly.getCategory().equals(ca.categoryName))
               {
                   totalAmount+=monthly.getAmount();
               }
            }

            hashmapCategoryandTotalAmount.put(ca.categoryName, totalAmount.toString());
            yvals.add(new PieEntry(Float.parseFloat(totalAmount.toString()), ca.categoryName));
        }


        // Set the color of each copy
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#6785f2"));
        colors.add(Color.parseColor("#675cf2"));
        colors.add(Color.parseColor("#496cef"));
        colors.add(Color.parseColor("#aa63fa"));
        colors.add(Color.parseColor("#f5a658"));
        PieChartManagger pieChartManagger=new PieChartManagger(piechart);
        pieChartManagger.showSolidPieChart(yvals,colors);

    }

}