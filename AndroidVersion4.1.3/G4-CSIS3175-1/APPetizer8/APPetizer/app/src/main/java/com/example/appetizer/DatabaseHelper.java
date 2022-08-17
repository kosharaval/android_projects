package com.example.appetizer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    //region Constants
    final static String DATABASE_NAME = "Appetizer.db";
    final static int DATABASE_VERSION = 1;
    final static String TABLE1_NAME = "Restaurant";
    final static String TABLE2_NAME = "Dish";
    final static String TABLE3_NAME = "User";
    final static String TABLE4_NAME = "Orders";
    final static String TABLE5_NAME = "OrderItem";
    final static String TABLE6_NAME = "ZipCode";
    final static String TABLE7_NAME = "Invite";

    //RESTAUTANT COLUMNS
    final static String T1COL_1 = "RestId";
    final static String T1COL_2 = "RestName";
    final static String T1COL_3 = "RestImage";

    //DISH COLUMNS
    final static String T2COL_1 = "DishId";
    final static String T2COL_2 = "RestId";
    final static String T2COL_3 = "Title";
    final static String T2COL_4 = "Description";
    final static String T2COL_5 = "Price";

    //USER COLUMNS
    final static String T3COL_1 = "UserId";
    final static String T3COL_2 = "Email";
    final static String T3COL_3 = "FName";
    final static String T3COL_4 = "LName";
    final static String T3COL_5 = "Phone";
    final static String T3COL_6 = "Address";
    final static String T3COL_7 = "ZIPCode";
    final static String T3COL_8 = "Password";

    //ORDER COLUMNS
    final static String T4COL_1 = "OrderId";
    final static String T4COL_2 = "UserId";
    final static String T4COL_3 = "Date";
    final static String T4COL_4 = "Status";
    final static String T4COL_5 = "Type";
    final static String T4COL_6 = "Total";
    final static String T4COL_7 = "Delivery";
    final static String T4COL_8 = "Tax";
    final static String T4COL_9 = "Payment";

    //ORDERITEM COLUMNS
    final static String T5COL_1 = "OrderId";
    final static String T5COL_2 = "DishId";
    final static String T5COL_3 = "Qty";
    final static String T5COL_4 = "OrderPrice";
    final static String T5COL_5 = "Restriction";
    final static String T5COL_6 = T1COL_1;

    //ZIP COLUMNS
    final static String T6COL_1 = "RestId";
    final static String T6COL_2 = "ZipCode";

    //INVITE COLUMNS
    final static String T7COL_1 = "UserId";
    final static String T7COL_2 = "FriendEmail";
    final static String T7COL_3 = "isAvailable";
//endregion

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Restaurant
        String rQuery = "Create Table " + TABLE1_NAME + "("+
                T1COL_1 +" INTEGER PRIMARY KEY, "+
                T1COL_2 +" TEXT, " +
                T1COL_3 +" TEXT )";
        //Dish
        String dQuery = "Create Table " + TABLE2_NAME + "("+
                T2COL_1 +" INTEGER, "+
                T2COL_2 +" INTEGER, " +
                T2COL_3 +" TEXT, " +
                T2COL_4 +" TEXT, " +
                T2COL_5 +" REAL)";
        //User
        String uQuery = "Create Table " + TABLE3_NAME + "("+
                T3COL_1 +" INTEGER PRIMARY KEY, "+
                T3COL_2 +" TEXT, " +
                T3COL_3 +" TEXT, " +
                T3COL_4 +" TEXT, " +
                T3COL_5 +" INTEGER, " +
                T3COL_6 +" TEXT, " +
                T3COL_7 +" TEXT, "+
                T3COL_8 +" TEXT )";
        //Order
        String oQuery = "Create Table " + TABLE4_NAME + "("+
                T4COL_1 +" INTEGER PRIMARY KEY, "+
                T4COL_2 +" INTEGER, " +
                T4COL_3 +" TEXT, " +
                T4COL_4 +" INTEGER, " +
                T4COL_5 +" INTEGER, " +
                T4COL_6 +" REAL, " +
                T4COL_7 +" REAL, " +
                T4COL_8 +" REAL, " +
                T4COL_9 +" TEXT)";
        //OrderItem
        String iQuery = "Create Table " + TABLE5_NAME + "("+
                T5COL_1 +" INTEGER, "+
                T5COL_2 +" INTEGER, " +
                T5COL_3 +" INTEGER, " +
                T5COL_4 +" REAL, " +
                T5COL_5 +" TEXT," +
                T5COL_6 + " INTEGER )";

        //Zip
        String zQuery = "Create Table " + TABLE6_NAME + "("+
                T6COL_1 +" INTEGER, "+
                T6COL_2 +" TEXT)";

        //Invite
        String vQuery = "Create Table " + TABLE7_NAME + "("+
                T7COL_1 +" INTEGER, "+
                T7COL_2 +" TEXT," +
                T7COL_3 +" INTEGER )";

        try{
            db.execSQL(rQuery);
            db.execSQL(dQuery);
            db.execSQL(uQuery);
            db.execSQL(oQuery);
            db.execSQL(iQuery);
            db.execSQL(zQuery);
            db.execSQL(vQuery);

        }catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TABLE1_NAME);
        db.execSQL("Drop table if exists " + TABLE2_NAME);
        db.execSQL("Drop table if exists " + TABLE3_NAME);
        db.execSQL("Drop table if exists " + TABLE4_NAME);
        db.execSQL("Drop table if exists " + TABLE5_NAME);
        db.execSQL("Drop table if exists " + TABLE6_NAME);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T3COL_2, user.getEmail());
        values.put(T3COL_3, user.getFname());
        values.put(T3COL_4, user.getLname());
        values.put(T3COL_5, user.getPhone());
        values.put(T3COL_6, user.getAddress());
        values.put(T3COL_7, user.getZIPCode());
        values.put(T3COL_8, user.getPassword());
        long r = db.insert(TABLE3_NAME, null, values);
        return r;

    }

    public int addOrders(Orders orders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T4COL_2, orders.getUserId());
        //values.put(T4COL_3, orders.getDate());
        values.put(T4COL_4, orders.isStatus());
        //values.put(T4COL_5, orders.isType());
        //values.put(T4COL_6, orders.getTotal());
        //values.put(T4COL_7, orders.getDelivery());
        //values.put(T4COL_8, orders.getTax());
        //values.put(T4COL_9, orders.getPayment());
        long r = db.insert(TABLE4_NAME, null, values);

        return (int)r;

    }

    public boolean addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T5COL_1, item.getOrderId());
        values.put(T5COL_2, item.getDishId());
        values.put(T5COL_3, item.getQty());
        values.put(T5COL_4, item.getPrice());
        values.put(T5COL_5, item.getRestriction());
        values.put(T5COL_6, item.getRestId());
        long r = db.insert(TABLE5_NAME, null, values);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    //method to add restaurants
    public boolean addRestaurant(Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T1COL_2, restaurant.getRestName());
        values.put(T1COL_3, restaurant.getRestImage());
        long r = db.insert(TABLE1_NAME, null, values);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    //method to add restaurants
    public boolean addZip(int id, String zip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T6COL_1, id);
        values.put(T6COL_2, zip);
        long r = db.insert(TABLE6_NAME, null, values);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    //method to add dishes
    public boolean addDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T2COL_1, dish.getDishId());
        values.put(T2COL_2, dish.getRestId());
        values.put(T2COL_3, dish.getTitle());
        values.put(T2COL_4, dish.getDescription());
        values.put(T2COL_5, dish.getPrice());
        long r = db.insert(TABLE2_NAME, null, values);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addInvite(int userId, String email){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T7COL_1, userId);
        values.put(T7COL_2, email);
        values.put(T7COL_3, 1);
        long r = db.insert(TABLE7_NAME, null, values);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean isAvailable(int userId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+ T7COL_1 +" from " + TABLE7_NAME +
                " where "+ T7COL_1 + "= "+ userId +
                " and " + T7COL_3 + "=1";
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        if(c.getCount() > 0){
            return true;
        }else{
            return false;
        }
    }

    public void setUsedDiscount(int userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T7COL_3, 0);
        long r = db.update(TABLE7_NAME, values, T7COL_1+"="+userId , null);
    }

    //check user
    private Cursor checkUser(String email, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+ T3COL_1 +" from " + TABLE3_NAME +
                " where "+ T3COL_2 + "='" + email +
                "' and " + T3COL_8 + "='" + password +"'";
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    //check user
    public boolean checkEmailExists(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+ T3COL_1 +" from " + TABLE3_NAME +
                " where "+ T3COL_2 + "='" + email + "'" ;
        Cursor c = sqLiteDatabase.rawQuery(query,null);

        if (c.getCount() > 0) return true;
        else    return false;

    }

    //check user
    public int checkUser(User user){
        Cursor check = checkUser(user.getEmail(), user.getPassword());
        int id = 0;
        if(check.getCount()>0){
            while (check.moveToNext()) {
                id = check.getInt(0);
            }
            return id;
        }else {
            return id;
        }
    }

    private Cursor selectUser(int userId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * from " + TABLE3_NAME +
                " where "+ T3COL_1 + "=" + userId ;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public User GetUser(int userId){
        Cursor c = selectUser(userId);
        User user = null;

        if(c.getCount()>0){
            while (c.moveToNext()) {
                user = new User(c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6));
            }
            return user;
        }else {
            return null;
        }
    }

    //select all users
    public Cursor viewUsers(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * from " + TABLE3_NAME ;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    //retrieve restaurant based on ZIPCode
    private Cursor viewRestaurants(int id){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+ TABLE1_NAME +".* from " + TABLE1_NAME + " inner join " +
                        TABLE6_NAME+" on "+ TABLE1_NAME +"." + T1COL_1+ " = " + TABLE6_NAME + "." + T6COL_1+
                        " inner join " +
                        TABLE3_NAME+" on "+ TABLE3_NAME +"." + T3COL_7+ " = " + TABLE6_NAME + "."+ T6COL_2+
                        " where "+ TABLE3_NAME +"."+ T3COL_1 + "=" + id;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public ArrayList<Restaurant> GetRestautantList(int id){
        ArrayList<Restaurant> restlist = new ArrayList<Restaurant>();
        Cursor c = viewRestaurants(id);

        if(c.getCount() > 0){
            while(c.moveToNext()){
                restlist.add(new Restaurant(c.getInt(0), c.getString(1), c.getInt(2)));
            }
            return restlist;
        }
        return restlist;
    }

    //retrieve menu based on RestId
    private Cursor viewMenu(int restId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * from " + TABLE2_NAME +
                " where "+ T2COL_2 + "=" + restId;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public ArrayList<Dish> GetMenu(int restId){
        ArrayList<Dish> dishes = new ArrayList<>();
        Cursor c = viewMenu(restId);

        if(c.getCount() > 0){
            while (c.moveToNext()){
                dishes.add(new Dish(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getDouble(4)));
            }
            return dishes;
        }
        return dishes;
    }

    private Cursor viewDish(int dishId, int restId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * from " + TABLE2_NAME +
                " where "+ T2COL_1 + "=" + dishId + " and " + T2COL_2 + " = " + restId;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public Cursor viewItems(int orderId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT " + T2COL_3 + ", " + T5COL_3 + " FROM "+ TABLE5_NAME + " i INNER JOIN " + TABLE2_NAME +
                " d ON i."+T5COL_2 +" = d." + T2COL_1 + " and i." + T5COL_6 + " = d."+ T2COL_2+
                " where i."+ T5COL_1 + "=" + orderId;
        Cursor c = sqLiteDatabase.rawQuery(query, null);
        return c;
    }

    public Dish GetDish(int dishId, int restId){
        Cursor c = viewDish(dishId, restId);
        Dish d;
        if(c.getCount() > 0){
            while (c.moveToNext()){
                return d = new Dish(c.getInt(0), c.getInt(1), c.getString(2), c.getString(3), c.getDouble(4));
            }
        }
        return d = null;
    }

    //retrieve orders based on UserId
    public Cursor viewOrders(int userId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * from " + TABLE4_NAME +
                " where "+ T4COL_2 + "=" + userId +
                " and " + T4COL_4 + " = 1";
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;

    }

    public ArrayList<Orders> GetOrders(int userId){
        Cursor c = viewOrders(userId);
        ArrayList<Orders> orders = new ArrayList<>();

        if(c.getCount() > 0){
            while (c.moveToNext()){
                orders.add(new Orders(c.getInt(0),
                        c.getInt(1),
                        c.getString(2),
                        c.getInt(3) == 1 ? true : false,
                        c.getInt(4) == 1 ? true : false,
                        c.getDouble(5),
                        c.getDouble(6),
                        c.getDouble(7),
                        c.getString(8)));

            }
            return orders;
        }
        return orders;
    }

    public boolean updateOrder(Orders order){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T4COL_2, order.getUserId());
        values.put(T4COL_3, order.getDate());
        values.put(T4COL_4, order.isStatus());
        values.put(T4COL_5, order.isType());
        values.put(T4COL_6, order.getTotal());
        values.put(T4COL_7, order.getDelivery());
        values.put(T4COL_8, order.getTax());
        values.put(T4COL_9, order.getPayment());

        long r = db.update(TABLE4_NAME,values, T4COL_1+"="+order.getOrderId(), null);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(T3COL_3, user.getFname());
        values.put(T3COL_4, user.getLname());
        values.put(T3COL_5, user.getPhone());
        values.put(T3COL_6, user.getAddress());
        values.put(T3COL_7, user.getZIPCode());
        values.put(T3COL_8, user.getPassword());
        long r = db.update(TABLE3_NAME,values, " UserId="+user.getUserId(), null);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor GetSubTotal(int orderId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT i."+T5COL_4+" from " +
                TABLE4_NAME + " o inner join " + TABLE5_NAME + " i on o."+ T4COL_1+"=i."+ T5COL_1+
                " WHERE o." + T4COL_1 + "=" + orderId;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    //region populate tables
    public void PopulateRestaurant(){
        ArrayList<Restaurant> rests = new ArrayList<>(10);
        rests.add(new Restaurant("KFC" , R.drawable.kfc)); //1
        rests.add(new Restaurant("Bella Italia" , R.drawable.bellaitalia)); //2
        rests.add(new Restaurant("Subway" , R.drawable.subway_logo));//3
        rests.add(new Restaurant("McDonalds" , R.drawable.mcdonalds));//4
        rests.add(new Restaurant("Hayak Sushi" , R.drawable.hyacksushi));//5


        for (Restaurant r : rests) {
            System.out.println(r.getRestName() +": "+ this.addRestaurant(r));
        }
    }

    public void PopulateZip(){

        this.addZip(1, "V3M2B8");
        this.addZip(1, "V3V6T6");
        this.addZip(1, "V3V8B1");
        this.addZip(1, "V3R2B6");
        this.addZip(1, "V3R1E1");

        //ITALIA
        this.addZip(2, "V3LOA2");
        this.addZip(2, "V3R2B6");
        this.addZip(2, "V3V9V3");
        this.addZip(2, "V3V4F6");
        this.addZip(2, "V3V9B7");

        //SUBWAY
        this.addZip(3, "V3V4F6");
        this.addZip(3, "V3V9B7");
        this.addZip(3, "V3V8B1");
        this.addZip(3, "V3R2B6");
        this.addZip(3, "V3R4Z6");

        //MCDONALDS
        this.addZip(4, "V3V4F6");
        this.addZip(4, "V3V9B7");
        this.addZip(4, "V3V8S1");
        this.addZip(4, "V3R2B3");
        this.addZip(4, "V3M4Z0");

        //HAYACK SUSHI
        this.addZip(5, "V3L0C3");
        this.addZip(5, "V3V9B7");
        this.addZip(5, "V3K8X1");
        this.addZip(5, "V3L1AL");
        this.addZip(5, "V3F4Z0");

    }

    public void PopulateDish(){
        ArrayList<Dish> dishes = new ArrayList<>();
        dishes.add(new Dish(1, 1, "Original Chicken Bucket (8 pcs) and Medium Fries",
                "Eight pieces of KFCs original recipe chicken served with a medium order of fries.",
                14.99));

        dishes.add(new Dish(2, 1, "Chicken Share Meal (6 pcs) and Medium Fries and Gravy",
                "Six pieces of KFCs original recipe chicken served with a medium order of fries and gravy.",
                16.00));

        dishes.add(new Dish(3, 1, "Chicken Share Meal (10 hot wings) and Medium Fries and Gravy",
                "Ten pieces of KFCs signature spicy, lightly-breaded wings served with a medium order of fries and gravy.",
                16.00));


        dishes.add(new Dish(1, 2, "Funghi Arrosto",
                "Baked mushrooms in a creamy mascarpone & spinach sauce with melted mozzarella and served with ciabatta toast.",
                20.00));

        dishes.add(new Dish(2, 2, "Mozzarella Carrozza",
                "Mini cheese bites in a golden herby breadcrumb, served with diced tomato, rocket & dolce piccante sweet chilli sauce.",
                10.49));

        dishes.add(new Dish(3, 2, "Tempura King Prawns",
                "King prawns in a light batter, served with garlic & lemon mayonnaise.",
                9.99));

        dishes.add(new Dish(1, 3, "6 inch Chicken Bacon Ranch Sandwich",
                "Juicy strips of grilled chicken and strips of bacon, all topped with tangy smooth ranch sauce.(530 cals)",
                7.49));

        dishes.add(new Dish(2, 3, "6 inch Steak and Cheese Sandwich",
                "Slices of hot tender, juicy steak, served on the bread of your choice, just bursting with flavour.(370 cals)",
                7.29));

        dishes.add(new Dish(3, 3, "Sweet Onion Chicken Teriyaki Sandwich",
                "Teriyaki-glazed chicken strips with the tangy taste of fresh veggies and succulent sweet onion! COntains 45 grams of fat(370 cals)",
                6.99));

        dishes.add(new Dish(1, 4, "Big Mac Meal [680-1120 Cals]",
                "Comes with medium fries or side salad and medium fountain drink or coffee.",
                9.29));

        dishes.add(new Dish(2, 4, "Mighty Angus Original Meal [950-1390 Cals]",
                "Comes with medium fries or side salad and medium fountain drink or coffee.",
                10.69));


        dishes.add(new Dish(3, 4, "Quarter Pounder with Cheese Meal [680-1120 Cals]",
                "Comes with medium fries or side salad and medium fountain drink or coffee.",
                9.29));

        dishes.add(new Dish(1, 5, "GESO KARAAGE",
                "Japanese style deep fried squid tentacles",
                4.95));

        dishes.add(new Dish(2, 5, "SALMON NIGIRI",
                "Wasabi inside",
                1.75));


        dishes.add(new Dish(3, 5, "B.C. ROLL",
                "Deep fried salmon skin and kappa",
                3.75));

        for(Dish d : dishes){
            System.out.println(d.getTitle() +": "+ this.addDish(d));
        }
    }

    public boolean isEmptyTable(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+T1COL_1+" from " +
                TABLE1_NAME ;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        if (c.getCount() < 1){
            return true;
        }
        return false;
    }

    //endregion
}
