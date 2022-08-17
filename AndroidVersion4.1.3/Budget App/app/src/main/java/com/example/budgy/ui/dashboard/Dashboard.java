package com.example.budgy.ui.dashboard;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.budgy.DatabaseHelper;
import com.example.budgy.Models.DailyTransactions;
import com.example.budgy.Models.FixedAmount;
import com.example.budgy.Models.LoggedInUser;
import com.example.budgy.R;
import com.example.budgy.ui.fixed.FixedIncomeExpenseAdapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@RequiresApi(api = Build.VERSION_CODES.O)
public class Dashboard extends Fragment implements AddTransactionDialog.AddCategoryDialogListener
{
    DatabaseHelper db;
    private DashboardViewModel dashboardViewModel;
    String loggedInUserId = LoggedInUser.getLoggedInUser().getID();

    TextView txtMonthlyBudget;
    TextView txtMonthlySpend;
    TextView txtMonthlyIncome;
    TextView txtMonthlySaving;

    RecyclerView categoryRecyclerView;
    DailyTransactionAdapter dailyTransactionAdapter;

    RecyclerView weeklyReportRecyclerView;
    WeeklyTransactionAdapter weeklyTransactionAdapter;

    ArrayList<DailyTransactionItem> dailyTransactionItemArrayList = new ArrayList<>();
    ArrayList<DailyTransactionItem> weeklyTransactionItemArrayList = new ArrayList<>();

    ArrayList<FixedAmount> fixedExpenseItemList = new ArrayList<>();
    ArrayList<FixedAmount> fixedIncomeItemList = new ArrayList<>();

    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
    String currentDate = formatter.format(date);

    Date date2 = new Date(date.getTime() - 604800000L);
    String startDate = formatter.format(date2);

    LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    Integer month = localDate.getMonthValue();
    Integer year = localDate.getYear();

    String startOfMonth = year + month.toString() +"01";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,@Nullable Bundle savedInstanceState)
    {
        db = new DatabaseHelper(getActivity());
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        txtMonthlyBudget = (TextView) root.findViewById(R.id.txtMonthBudget);
        txtMonthlySpend = (TextView) root.findViewById(R.id.txtMonthSpend);
        txtMonthlySaving = (TextView) root.findViewById(R.id.txtSaving);
        txtMonthlyIncome = (TextView) root.findViewById(R.id.txtMonthIncome);

        setTopCard();
        ImageButton btnAdd = (ImageButton) root.findViewById(R.id.btnAdd);
        Bundle bundleCategory = new Bundle();
        bundleCategory.putString("type", "C");
        btnAdd.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AddTransactionDialog addTransactionDialog = new AddTransactionDialog();
                addTransactionDialog.setTargetFragment(Dashboard.this, 0);
                addTransactionDialog.setArguments(bundleCategory);
                addTransactionDialog.show(getParentFragmentManager(), "Add new Entry");
            }
        });

        categoryRecyclerView = root.findViewById(R.id.rvCategoryList);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dailyTransactionAdapter = new DailyTransactionAdapter(dailyTransactionItemArrayList);
        categoryRecyclerView.setAdapter(dailyTransactionAdapter);

        weeklyReportRecyclerView = root.findViewById(R.id.rvWeeklyReportList);
        weeklyReportRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        weeklyTransactionAdapter = new WeeklyTransactionAdapter(weeklyTransactionItemArrayList);
        weeklyReportRecyclerView.setAdapter(weeklyTransactionAdapter);

        dailyTransactionAdapter.setOnItemClickListener(new DailyTransactionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onDeleteClick(int position) {
                db.deleteDailyTransactionById(dailyTransactionItemArrayList.get(position).getId());
                dailyTransactionItemArrayList.remove(position);
                dailyTransactionAdapter.notifyDataSetChanged();
                setTopCard();
                getAllDailyTransaction();
                getALlWeeklyTransaction();
            }
        });

        weeklyTransactionAdapter.setOnItemClickListener(new WeeklyTransactionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position) {
                db.deleteDailyTransactionById(weeklyTransactionItemArrayList.get(position).getId());
                weeklyTransactionItemArrayList.remove(position);
                weeklyTransactionAdapter.notifyDataSetChanged();
                setTopCard();
                getAllDailyTransaction();
                getALlWeeklyTransaction();
            }
        });

        getAllDailyTransaction();
        getALlWeeklyTransaction();

        return root;
    }

    public void getAllDailyTransaction()
    {
        List<DailyTransactions> dailyTransactionsList = null;
        dailyTransactionsList = db.getAllDailyTransactionByUserIdAndDate(
                Integer.parseInt(loggedInUserId),
                Integer.parseInt(currentDate)
        );
        dailyTransactionItemArrayList.clear();
        for(DailyTransactions dt : dailyTransactionsList)
        {
            dailyTransactionItemArrayList.add(new DailyTransactionItem(
                    dt.getType(),
                    dt.getCategory(),
                    dt.getAmount(),
                    dt.getReceipt(),
                    dt.getDate(),
                    dt.getTitle(),
                    dt.getUserId(),
                    dt.getId()
            ));
        }
        if(!dailyTransactionItemArrayList.isEmpty())
        {
            dailyTransactionAdapter.notifyDataSetChanged();
        }
    }

    public void getALlWeeklyTransaction()
    {
        List<DailyTransactions> weeklyTransactionList=null;
        weeklyTransactionList=db.getAllDailyTransactionByUserIdAndDateRange(
                Integer.parseInt(loggedInUserId),
                Integer.parseInt(startDate),
                Integer.parseInt(currentDate)
        );
        weeklyTransactionItemArrayList.clear();
        for(DailyTransactions weekly : weeklyTransactionList)
        {
            weeklyTransactionItemArrayList.add(new DailyTransactionItem(
                    weekly.getType(),
                    weekly.getCategory(),
                    weekly.getAmount(),
                    weekly.getReceipt(),
                    weekly.getDate(),
                    weekly.getTitle(),
                    weekly.getUserId(),
                    weekly.getId())
            );
        }
        if(!dailyTransactionItemArrayList.isEmpty())
        {
            weeklyTransactionAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void apply(String category, String amount, String type, String imageURL,String date,String title)
    {
        String dateFormat = date.replace("-","");
        db.insertDailyTransaction(loggedInUserId,category,amount,type,imageURL.toString(),dateFormat,title);
        dailyTransactionAdapter.notifyDataSetChanged();
        getAllDailyTransaction();
        getALlWeeklyTransaction();
        setTopCard();
    }

    public double getAllFixedAmounts(String type)
    {
        List<FixedAmount> fixedAmounts;
        double totalFixedAmount = 0;
        fixedAmounts =  db.getAllFixedAmountByUserIdAndType(Integer.parseInt(loggedInUserId),type);
        for (FixedAmount fa : fixedAmounts)
        {
            totalFixedAmount += fa.getAmount();
        }
        return totalFixedAmount;
    }

    public void setTopCard()
    {
        List<DailyTransactions> dailyTransactionsSum = db.getAllDailyTransactionByUserIdAndDateRange(
                Integer.parseInt(loggedInUserId),
                Integer.parseInt(startOfMonth),
                Integer.parseInt(currentDate));

        double totalExpenses = 0 ;
        double totalIncome = 0;
        for(DailyTransactions dt : dailyTransactionsSum)
        {
            String type = dt.getType();
            if(type.equals("Income"))
            {
                totalIncome = totalIncome + dt.getAmount();
            }
            else {
                totalExpenses = totalExpenses + dt.getAmount();
            }

        }

        double totalFixedIncome = getAllFixedAmounts("I");
        double totalFixedExpense = getAllFixedAmounts("E");
        Double monthlyBudget = Double.parseDouble(LoggedInUser.getCurrentBudget());
        Double totalIncomeAmount =  totalFixedIncome + totalIncome;
        Double totalExpenseAmount =  totalFixedExpense + totalExpenses ;
        Double saving = monthlyBudget - totalExpenseAmount;

        DecimalFormat format = new DecimalFormat("##.##");
        txtMonthlyBudget.setText(format.format(monthlyBudget).toString());
        txtMonthlyIncome.setText(format.format(totalIncomeAmount).toString());
        txtMonthlySpend.setText(format.format(totalExpenseAmount).toString());
        txtMonthlySaving.setText(format.format(saving).toString());
    }

}