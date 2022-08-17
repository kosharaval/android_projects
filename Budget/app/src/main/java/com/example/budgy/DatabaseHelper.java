package com.example.budgy;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.util.Log;

import com.example.budgy.Models.Budget;
import com.example.budgy.Models.CategoryItem;
import com.example.budgy.Models.DailyTransactions;
import com.example.budgy.Models.FixedAmount;
import com.example.budgy.Models.User;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String LOG="DatabaseHelper";
    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME="BM";

    //Table Names
    private static final String TBL_FIXED_AMOUNT = "FixedAmount";
    private static final String TBL_USER = "User";
    private static final String TBL_DAILY_TRANSACTION = "DailyTransaction";
    private static final String TBL_CATEGORY = "Category";
    private static final String TBL_BUDGET = "Budget";

    //FIXED AMOUNT TABLE COLUMN NAMES
    private static final String tblFixed_UserID = "userid";
    private static final String tblFixed_name = "name";
    private static final String tblFixed_amount = "amount";
    private static final String tblFixed_id = "ID";
    private static final String tblFixed_type = "type"; //I for Income and E for Expense

    //USER TABLE COLUMN NAMES
    private static final String tblUser_ID = "ID";
    private static final String tblUser_FName = "fname";
    private static final String tblUser_LName = "lname";
    private static final String tblUser_Email = "email";
    private static final String tblUser_Phone = "phone";
    private static final String tblUser_Password = "password";
    private static final String tblUser_Gender = "gender";

    //DAILY TRANSACTION TABLE COLUMN NAMES
    private static final String tblDailyTransaction_UserID = "userid";
    private static final String tblDailyTransaction_Category = "c_name";
    private static final String tblDailyTransaction_Amount = "c_amount";
    private static final String tblDailyTransaction_Type = "type";
    private static final String tblDailyTransaction_Receipt = "receipt";
    private static final String tblDailyTransaction_Date = "date";
    private static final String tblDailyTransaction_Title = "title";
    private static final String tblDailyTransaction_id = "ID";

    //DAILY CATEGORY TABLE COLUMN NAMES
    private static final String tblCategory_UserID = "userid";
    private static final String tblCategory_Name = "name";
    private static final String tblCategory_Status = "status";
    private static final String tblCategory_id = "ID";

    //BUDGET TABLE COLUMN NAMES
    private static final String tblBudget_id = "ID";
    private static final String tblBudget_UserID = "userid";
    private static final String tblBudget_month = "month";
    private static final String tblBudget_year = "year";
    private static final String tblBudget_Amount = "amount";

    //Table Create Statements
    private static final String CREATE_FIXED_TBL = "CREATE TABLE "
            + TBL_FIXED_AMOUNT + " ( ID " + "INTEGER PRIMARY KEY, "
            + tblFixed_UserID + " INTEGER, "
            + tblFixed_name + " TEXT, "
            + tblFixed_type + " TEXT, "
            + tblFixed_amount + " INTEGER )";

    private static final String CREATE_USER_TBL = "CREATE TABLE "
            + TBL_USER + " ( ID " + "INTEGER PRIMARY KEY, "
            + tblUser_FName + " TEXT, "
            + tblUser_LName + " TEXT, "
            + tblUser_Email + " TEXT, "
            + tblUser_Phone + " TEXT, "
            + tblUser_Password + " TEXT, "
            + tblUser_Gender + " TEXT )";

    private static final String CREATE_DAILY_TRANSACTION_TBL = "CREATE TABLE "
            + TBL_DAILY_TRANSACTION + " ( ID " + "INTEGER PRIMARY KEY, "
            + tblDailyTransaction_UserID + " TEXT, "
            + tblDailyTransaction_Category + " TEXT, "
            + tblDailyTransaction_Amount + " TEXT, "
            + tblDailyTransaction_Type + " TEXT, "
            + tblDailyTransaction_Receipt + " TEXT, "
            + tblDailyTransaction_Date + " INTEGER, "
            + tblDailyTransaction_Title + " TEXT )";

    private static final String CREATE_CATEGORY_TBL = "CREATE TABLE "
            + TBL_CATEGORY + " ( ID " + "INTEGER PRIMARY KEY, "
            + tblCategory_Name + " TEXT, "
            + tblCategory_Status + " TEXT, "
            + tblCategory_UserID + " TEXT ) ";

    private static final String CREATE_BUDGET_TBL = "CREATE TABLE "
            + TBL_BUDGET + " ( ID " + "INTEGER PRIMARY KEY, "
            + tblBudget_UserID + " TEXT, "
            + tblBudget_month + " TEXT, "
            + tblBudget_year + " TEXT, "
            + tblBudget_Amount + " TEXT ) ";

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_FIXED_TBL);
        db.execSQL(CREATE_USER_TBL);
        db.execSQL(CREATE_DAILY_TRANSACTION_TBL);
        db.execSQL(CREATE_CATEGORY_TBL);
        db.execSQL(CREATE_BUDGET_TBL);
        Log.e("CREATE_USER_TBL",CREATE_USER_TBL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_FIXED_AMOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_DAILY_TRANSACTION);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_BUDGET);
        onCreate(db);
    }

    public long insertFixedAmount(int userId, String name, Double amount, String type)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tblFixed_name,name);
        values.put(tblFixed_amount,amount);
        values.put(tblFixed_UserID,userId);
        values.put(tblFixed_type,type);

        long id = db.insert(TBL_FIXED_AMOUNT, null,values);

        return id;
    }

    public long insertUser (User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tblUser_FName,user.getFname());
        values.put(tblUser_LName,user.getLname());
        values.put(tblUser_Email,user.getEmail());
        values.put(tblUser_Phone,user.getPhone());
        values.put(tblUser_Password,user.getPassword());
        values.put(tblUser_Gender,user.getGender());

        long id = db.insert(TBL_USER, null,values);

        return id;
    }

    public long insertDailyTransaction (String userId, String category, String amount, String type, String receipt, String date,String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tblDailyTransaction_UserID,userId);
        values.put(tblDailyTransaction_Category,category);
        values.put(tblDailyTransaction_Amount,amount);
        values.put(tblDailyTransaction_Type,type);
        values.put(tblDailyTransaction_Receipt,receipt);
        values.put(tblDailyTransaction_Date,date);
        values.put(tblDailyTransaction_Title,title);

        long id = db.insert(TBL_DAILY_TRANSACTION, null,values);

        return id;
    }

    public long insertCategory (String category, String status, String userId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tblCategory_Name,category);
        values.put(tblCategory_Status,status);
        values.put(tblCategory_UserID,userId);

        long id = db.insert(TBL_CATEGORY, null,values);

        return id;
    }

    public long insertBudget (String month, String year, String amount, String userId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tblBudget_year, year);
        values.put(tblBudget_month, month);
        values.put(tblBudget_UserID, userId);
        values.put(tblBudget_Amount, amount);

        long id = db.insert(TBL_BUDGET, null, values);

        return id;
    }

    public List<FixedAmount> getAllFixedAmountByUserIdAndType(int userId, String type)
    {
        List<FixedAmount> fixedAmounts = new ArrayList<>();
        String selectQuery = "SELECT * FROM " +TBL_FIXED_AMOUNT + " WHERE " +tblFixed_UserID + " = " +userId + " AND type = '" + type +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst())
        {
            do{
                FixedAmount fa = new FixedAmount();
                fa.setAmount(c.getDouble(c.getColumnIndex(tblFixed_amount)));
                fa.setName(c.getString(c.getColumnIndex(tblFixed_name)));
                fa.setId(c.getInt(c.getColumnIndex(tblFixed_id)));

                fixedAmounts.add(fa);
            }while(c.moveToNext());
        }
        return fixedAmounts;
    }

    public User getUserByEmailAndPassword(String email, String password)
    {
        User user = null;
        String selectQuery = "SELECT * FROM " +TBL_USER + " WHERE " +tblUser_Email + " = '" + email + "' AND " + tblUser_Password +" = '" + password +"'";
        Log.e("User Found", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst())
        {
            do{
                String id = c.getString(c.getColumnIndex(tblUser_ID));
                String fname = c.getString(c.getColumnIndex(tblUser_FName));
                String lname = c.getString(c.getColumnIndex(tblUser_LName));
                String userEmail = email;
                String phone = c.getString(c.getColumnIndex(tblUser_Phone));
                String gender = c.getString(c.getColumnIndex(tblUser_Gender));
                user = new User(id,fname,lname,email,phone,password,gender);

            }while(c.moveToNext());
        }
        return user;
    }

    public List<DailyTransactions> getAllDailyTransactionByUserIdAndDate(int userId, int date)
    {
        List<DailyTransactions> dailyTransaction = new ArrayList<>();
        String selectQuery = "SELECT * FROM " +TBL_DAILY_TRANSACTION + " WHERE " +tblDailyTransaction_UserID + " = " +userId
                + " AND " + tblDailyTransaction_Date + " = '" + date +"'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst())
        {
            do{
                DailyTransactions transaction = new DailyTransactions();
                transaction.setCategory(c.getString(c.getColumnIndex(tblDailyTransaction_Category)));
                transaction.setAmount(c.getDouble(c.getColumnIndex(tblDailyTransaction_Amount)));
                transaction.setType(c.getString(c.getColumnIndex(tblDailyTransaction_Type)));
                transaction.setReceipt(c.getString(c.getColumnIndex(tblDailyTransaction_Receipt)));
                transaction.setDate(c.getInt(c.getColumnIndex(tblDailyTransaction_Date)));
                transaction.setId(c.getInt(c.getColumnIndex(tblDailyTransaction_id)));
                transaction.setTitle(c.getString(c.getColumnIndex(tblDailyTransaction_Title)));
                transaction.setUserId(c.getInt(c.getColumnIndex(tblDailyTransaction_UserID)));

                dailyTransaction.add(transaction);
            }while(c.moveToNext());
        }
        return dailyTransaction;
    }
    public List<DailyTransactions> getAllDailyTransactionByUserIdAndDateRange(int userId, int startDate,int endDate)
    {
        List<DailyTransactions> dailyTransaction = new ArrayList<>();
        String selectQuery = "SELECT * FROM " +TBL_DAILY_TRANSACTION + " WHERE " +tblDailyTransaction_UserID + " = " +userId
                + " AND " + tblDailyTransaction_Date + " BETWEEN '" + startDate +"' AND '" + endDate + "' ORDER BY "
                + tblDailyTransaction_Date + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery,null);

        if(c.moveToFirst())
        {
            do{
                DailyTransactions transaction = new DailyTransactions();
                transaction.setCategory(c.getString(c.getColumnIndex(tblDailyTransaction_Category)));
                transaction.setAmount(c.getDouble(c.getColumnIndex(tblDailyTransaction_Amount)));
                transaction.setType(c.getString(c.getColumnIndex(tblDailyTransaction_Type)));
                transaction.setReceipt(c.getString(c.getColumnIndex(tblDailyTransaction_Receipt)));
                transaction.setDate(c.getInt(c.getColumnIndex(tblDailyTransaction_Date)));
                transaction.setId(c.getInt(c.getColumnIndex(tblDailyTransaction_id)));
                transaction.setTitle(c.getString(c.getColumnIndex(tblDailyTransaction_Title)));
                transaction.setUserId(c.getInt(c.getColumnIndex(tblDailyTransaction_UserID)));

                dailyTransaction.add(transaction);
            }while(c.moveToNext());
        }
        return dailyTransaction;
    }

    public List<CategoryItem> getCategoryNameByUserId(String userId)
    {
        List<CategoryItem> categoryItems = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TBL_CATEGORY + " WHERE " + tblCategory_Status + "='Active' AND "+
                tblCategory_UserID + "='" + userId + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =db.rawQuery(selectQuery, null);
        if(c.moveToFirst())
        {
            do{
                CategoryItem category = new CategoryItem();
                category.setCategoryName(c.getString(c.getColumnIndex(tblCategory_Name)));
                categoryItems.add(category);

            }while(c.moveToNext());
        }
        return categoryItems;
    }

    public Budget getCurrentBudget(String userId, String year, String month)
    {
        Budget budget = new Budget();
        String selectQuery = "SELECT * FROM " + TBL_BUDGET + " WHERE " + tblBudget_month + "='" + month +
                "' AND " + tblBudget_year + "='" + year + "' AND "+
                tblBudget_UserID + "='" + userId + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c =db.rawQuery(selectQuery, null);
        if(c.moveToFirst())
        {
            do{
                budget.setId(c.getString(c.getColumnIndex(tblBudget_id)));
                budget.setMonth(c.getString(c.getColumnIndex(tblBudget_month)));
                budget.setYear(c.getString(c.getColumnIndex(tblBudget_year)));
                budget.setUserId(c.getString(c.getColumnIndex(tblBudget_UserID)));
                budget.setAmount(c.getString(c.getColumnIndex(tblBudget_Amount)));

            }while(c.moveToNext());
        }
        return budget;
    }

    public long updateBudget(String month, String year, String amount, String userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(tblBudget_year, year);
        values.put(tblBudget_month, month);
        values.put(tblBudget_UserID, userId);
        values.put(tblBudget_Amount, amount);

        return db.update(TBL_BUDGET, values, "userid=? AND month=? AND year=?", new String[]{userId, month, year});
    }

    public long deleteFixedAmountById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TBL_FIXED_AMOUNT, "ID =?", new String[]{Integer.toString(id)});
    }

    public long deleteDailyTransactionById(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TBL_DAILY_TRANSACTION,"ID =?", new String[]{Integer.toString(id)});
    }
}
