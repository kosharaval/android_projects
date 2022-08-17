package com.example.briskdelivery;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    //region Constants
    final static String DATABASE_NAME = "BriskDelivery.db";
    final static int DATABASE_VERSION = 1;
    final static String Restaurant = "Restaurant";
    final static String Dish = "Dish";
    final static String User = "User";
    final static String Orders = "Orders";
    final static String OrderItem = "OrderItem";

    //RESTAUTANT COLUMNS
    final static String RId = "RId";
    final static String RName = "RestName";
    final static String RPic = "RestImage";

    //DISH COLUMNS
    final static String DId = "DishId";
    final static String DRId = "RId";
    final static String DTitle = "Title";
    final static String DDesc = "Description";
    final static String Dprice = "Price";

    //USER COLUMNS
    final static String UId = "UserId";
    final static String UEmail = "Email";
    final static String UName = "Name";
    final static String UPhone = "Phone";
    final static String UAddress = "Address";
    final static String UPassword = "Password";

    //ORDER COLUMNS
    final static String OId = "OrderId";
    final static String OUId = "UserId";
    final static String ODate = "Date";
    final static String OStatus = "Status";
    final static String OType = "Type";
    final static String OTotal = "Total";
    final static String ODelivery = "Delivery";
    final static String OTax = "Tax";
    final static String OPayment = "Payment";

    //ORDERITEM COLUMNS
    final static String OIOId = "OrderId";
    final static String OIDId = "DishId";
    final static String OIQty = "Qty";
    final static String OIPrice = "OrderPrice";
    final static String OIRestriction = "Restriction";
    final static String OIRId = RId;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //Restaurant
        String rQuery = "Create Table " + Restaurant + "("+
                RId +" INTEGER PRIMARY KEY, "+
                RName +" TEXT, " +
                RPic +" TEXT )";
        //Dish
        String dQuery = "Create Table " + Dish + "("+
                DId +" INTEGER, "+
                DRId +" INTEGER, " +
                DTitle +" TEXT, " +
                DDesc +" TEXT, " +
                Dprice +" REAL)";
        //User
        String uQuery = "Create Table " + User + "("+
                UId +" INTEGER PRIMARY KEY, "+
                UEmail +" TEXT, " +
                UName +" TEXT, " +
                UPhone +" INTEGER, " +
                UAddress +" TEXT, " +
                UPassword +" TEXT )";
        //Order
        String oQuery = "Create Table " + Orders + "("+
                OId +" INTEGER PRIMARY KEY, "+
                OUId +" INTEGER, " +
                ODate +" TEXT, " +
                OStatus +" INTEGER, " +
                OType +" INTEGER, " +
                OTotal +" REAL, " +
                ODelivery +" REAL, " +
                OTax +" REAL, " +
                OPayment +" TEXT)";
        //OrderItem
        String iQuery = "Create Table " + OrderItem + "("+
                OIOId +" INTEGER, "+
                OIDId +" INTEGER, " +
                OIQty +" INTEGER, " +
                OIPrice +" REAL, " +
                OIRestriction +" TEXT," +
                OIRId + " INTEGER )";


        try{
            db.execSQL(rQuery);
            db.execSQL(dQuery);
            db.execSQL(uQuery);
            db.execSQL(oQuery);
            db.execSQL(iQuery);

        }catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + Restaurant);
        db.execSQL("Drop table if exists " + Dish);
        db.execSQL("Drop table if exists " + User);
        db.execSQL("Drop table if exists " + Orders);
        db.execSQL("Drop table if exists " + OrderItem);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UEmail, user.getEmail());
        values.put(UName, user.getUname());
        values.put(UPhone, user.getPhone());
        values.put(UAddress, user.getAddress());
        values.put(UPassword, user.getUpass());
        long r = db.insert(User, null, values);
        return r;

    }

    public int addOrders(Orders orders) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OUId, orders.getUserId());
        values.put(OStatus, orders.isStatus());
        long r = db.insert(Orders, null, values);

        return (int)r;

    }

    public boolean addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(OIOId, item.getOrderId());
        values.put(OIDId, item.getDishId());
        values.put(OIQty, item.getQty());
        values.put(OIPrice, item.getPrice());
        values.put(OIRId, item.getRestId());
        long r = db.insert(OrderItem, null, values);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    //method to add restaurants
    public boolean addRestaurant( Restaurant restaurant) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RName, restaurant.getRestName());
        values.put(RPic, restaurant.getRestImage());
        long r = db.insert(Restaurant, null, values);
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
        values.put(DId, dish.getDishId());
        values.put(DRId, dish.getRestId());
        values.put(DTitle, dish.getTitle());
        values.put(DDesc, dish.getDescription());
        values.put(Dprice, dish.getPrice());
        long r = db.insert(Dish, null, values);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }


    //check user
    public boolean checkEmailExists(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+ UId +" from " + User +
                " where "+ UEmail + "='" + email + "'" ;
        Cursor c = sqLiteDatabase.rawQuery(query,null);

        if (c.getCount() > 0) return true;
        else    return false;

    }



    //retrieve restaurant
    private Cursor viewRestaurants(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+ Restaurant +".* from " + Restaurant;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public ArrayList< Restaurant> GetRestautantList(){
        ArrayList<Restaurant> restlist = new ArrayList<Restaurant>();
        Cursor c = viewRestaurants();

        if(c.getCount() > 0){
            while(c.moveToNext()){
                restlist.add(new Restaurant(c.getInt(0), c.getString(1), c.getInt(2)));
            }
            return restlist;
        }
        return restlist;
    }

    //retrieve menu based on RId
    private Cursor viewMenu(int restId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT * from " + Dish +
                " where "+ DRId + "=" + restId;
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
        String query = "SELECT * from " + Dish +
                " where "+ DId + "=" + dishId + " and " + DRId + " = " + restId;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
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
        String query = "SELECT * from " + Orders +
                " where "+ OUId + "=" + userId +
                " and " + OStatus + " = 1";
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
        values.put(OUId, order.getUserId());
        values.put(ODate, order.getDate());
        values.put(OStatus, order.isStatus());
        values.put(OType, order.isType());
        values.put(OTotal, order.getTotal());
        values.put(ODelivery, order.getDelivery());
        values.put(OTax, order.getTax());
        values.put(OPayment, order.getPayment());

        long r = db.update(Orders,values, OId+"="+order.getOrderId(), null);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UEmail, user.getEmail());

        values.put(UName, user.getUname());
        values.put(UPhone, user.getPhone());
        values.put(UAddress, user.getAddress());
        values.put(UPassword, user.getUpass());
        long r = db.update(User,values, " userId="+user.getuId(), null);
        if(r == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor GetSubTotal(int orderId){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT i."+OIPrice+" from " +
                Orders + " o inner join " + OrderItem + " i on o."+ OId+"=i."+ OIOId+
                " WHERE o." + OId + "=" + orderId;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        return c;
    }

    public void PopulateRestaurant(){
        ArrayList< Restaurant> rests = new ArrayList<>(20);
        //change path
        rests.add(new  Restaurant("KFC" , R.drawable.kfc)); //1
        rests.add(new  Restaurant("Bella Italia" , R.drawable.bella)); //2
        rests.add(new  Restaurant("Subway" , R.drawable.subway));//3
        rests.add(new  Restaurant("McDonalds" , R.drawable.mcdonald));//4
        rests.add(new  Restaurant("Hayak Sushi" , R.drawable.sushi));//5
        rests.add(new  Restaurant("A&W" , R.drawable.aandw));//6
        rests.add(new  Restaurant("Beer Creek" , R.drawable.beercreek));//7
        rests.add(new  Restaurant("Cha Time" , R.drawable.chatime));//8
        rests.add(new  Restaurant("Dairy Queen" , R.drawable.dairyqueen));//9
        rests.add(new  Restaurant("Donair Dude" , R.drawable.donairdude));//10
        rests.add(new  Restaurant("Fresh Slice" , R.drawable.freshslice));//11
        rests.add(new  Restaurant("Jaipur Indian" , R.drawable.jaipurindian));//12
        rests.add(new  Restaurant("Pinch Of SPice " , R.drawable.pinchofspice));//13
        rests.add(new  Restaurant("Uncle Fatih" , R.drawable.unclefatih));//14
        rests.add(new  Restaurant("7 Eleven" , R.drawable.seveneleven));//15
        rests.add(new  Restaurant("Nandos " , R.drawable.nandos));//16
        rests.add(new  Restaurant("White Spot" , R.drawable.whitespot));//17


        for ( Restaurant r : rests) {
            System.out.println(r.getRestName() +": "+ this.addRestaurant(r));
        }
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
                "Deep fried salmon skin and kappa.",
                3.75));

        dishes.add(new Dish(1, 6, "Beyond Meat Burger Combo",
                "A Beyond Meat patty made from plants.",
                14.60));

        dishes.add(new Dish(2, 6, "Teen Burger Combo",
                "A perfectly seasoned grass-fed beef patty topped with real cheddar cheese.",
                14.15));


        dishes.add(new Dish(3, 6, "Spicy Habanero Chicken Burger Combo",
                "Deliciously seasoned 100% chicken breast – from chickens raised without the use of antibiotics.",
                14.75));

        dishes.add(new Dish(1, 7, "Souvlaki Platter",
                "Gluten-free. Four skewers of your choice served with rice.",
                14.00));

        dishes.add(new Dish(2, 7, "Half Roast Chicken",
                "Lemon, oregano, mustard marinade baked to order and served .",
                32.00));

        dishes.add(new Dish(3, 7, "Boutari Grande Reserve",
                "Xinomavro (Gr).",
                50.00));


        dishes.add(new Dish(1, 8, "Chatime Pearl Milk Tea Cold",
                "Tapioca pearls add a pleasant texture to our signature milk tea, a best-selling fan favourite.",
                6.79));


        dishes.add(new Dish(2, 8, "Chatime Triple",
                "Have this drink made in your choice of Signature Milk Tea, Fresh Milk or Oat Milk.",
                7.00));

        dishes.add(new Dish(3, 8, "Taro Smoothie",
                "Frosty and creamy smoothie with robust real ground taro flavour.",
                7.49));

        dishes.add(new Dish(1, 9, "OREO️ Cookie Blizzard️ Treat",
                "OREO️ cookie pieces blended with DQ️ creamy vanilla.",
                5.16));

        dishes.add(new Dish(2, 9, "Reese's️ Peanut Butter Cup Blizzard️ Treat",
                "Reese’s️ Peanut Butter Cups️ blended with DQ️ creamy vanilla.",
                5.49));

        dishes.add(new Dish(3, 9, "Royal OREO️ Blizzard️ Treat",
                "OREO️ cookies blended with creamy vanilla soft serve.",
                5.49));

        dishes.add(new Dish(1, 10, "Chicken Pita Donair",
                "Crispy chicken donair meat with your choice of spreads and fresh toppings.",
                12.99));

        dishes.add(new Dish(2, 10, "Lamb Pita Donair",
                "Freshly shaved lamb donair meat with your choice of spreads and fresh toppings.",
                13.99));

        dishes.add(new Dish(3, 10, "Chicken Donair Poutine",
                "Crispy skin-on french fries topped with crispy chicken donair meat.",
                13.99));

        dishes.add(new Dish(1, 11, "Butter Chicken Feast",
                "Creamy white garlic sauce, mozzarella, chicken, red peppers and butter chicken sauce.",
                13.75));

        dishes.add(new Dish(2, 11, "Pesto Feast",
                "Zesty tomato sauce, mozzarella, spinach, mushrooms, red onions, pesto sauce and cheddar.",
                25.00));

        dishes.add(new Dish(3, 11, "Italian Feast",
                "Zesty tomato sauce, mozzarella, ham, pepperoni, and bacon.",
                25.00));


        dishes.add(new Dish(1, 12, "Malai Chicken with Coconut",
                "Chicken pieces cooked in butter curry sauce with coconut and Indian spices.",
                12.99));

        dishes.add(new Dish(2, 12, "Shahi Paneer",
                "Homemade cheese mixed with onion, ginger, garlic, tomatoes, cashew paste and spices.",
                12.99));

        dishes.add(new Dish(3, 12, "Chana Masala",
                "Chickpeas cooked with tomatoes, onion and spices.",
                10.99));

        dishes.add(new Dish(1, 13, "Vegetable Samosa",
                "Two crispy patties stuffed with potato.",
                3.99));

        dishes.add(new Dish(2, 13, "Delhi Fish Pakora",
                "Fried pieces of basa Fish delicately battered with chickpea flour and spices.",
                9.99));

        dishes.add(new Dish(3, 13, "Garlic Naan",
                "Leavened garlic bread. Assorted handcrafted fresh pieces of bread baked in a traditional charcoal clay oven.",
                2.99));

        dishes.add(new Dish(1, 14, "Chipotle Chicken",
                "With meat. Chipotle base, chicken, onion, green pepper, red pepper, and cheddar.",
                12.95));


        dishes.add(new Dish(2, 14, "Chorizo Sausage ",
                "With meat. Hot and spicy. Chorizo, green pepper, red pepper, and cheddar.",
                20.95));

        dishes.add(new Dish(3, 14, "Hot & Spicy Lover",
                "WWith meat. Hot and spicy. Capicollo, chorizo, banana pepper, mushroom, and red pepper.",
                24.95));

        dishes.add(new Dish(1, 15, "The Reverse Slurpee Run",
                "Includes: 2 Large Slurpee, 2 Personal Size Pizzas, 2 Taquitos.",
                5.00));


        dishes.add(new Dish(2, 15, "Cravings Fuel Up Combo",
                "Includes: 2 Large Slurpee, 2 Personal Size Pizzas, 8 strips + 8 wedges & 1 dip.",
                25.00));

        dishes.add(new Dish(3, 15, "Cheddar & Pork Meatball",
                "Cheddar & Pork Meatball -6 Pieces (assorted flavours).",
                4.50));

        dishes.add(new Dish(1, 16, "Chicken Wrap",
                "Grilled chicken tenders with leaf lettuce, tomato, cucumber, sweet chilli jam and our tangy cilantro yogurt.",
                10.25));

        dishes.add(new Dish(2, 16, "Caesar Salad",
                "The classic, prepared fresh with shredded parmesan and house-made croutons.",
                10.48));


        dishes.add(new Dish(3, 16, "Spicy Mixed Olives",
                "Green and black olives perked up with garlic, peppers and chilli.",
                7.48));


        dishes.add(new Dish(1, 17, "The BC Sunny Start",
                "Crispy bacon, Cheddar cheese, grilled tomato & a fried egg with our famous Triple “O”™️ sauce",
                15.35));


        dishes.add(new Dish(2, 17, "Side Smash Brown Potatoes",
                "Our signature smash browns made from 100% never-frozen, Canadian nugget potatoes.",
                3.80));


        dishes.add(new Dish(3, 17, "Nat's Hearty Breakfast",
                "Two eggs any style, with bacon, back bacon or sausage and your choice of toast.",
                13.80));
        for(Dish d : dishes){
            System.out.println(d.getTitle() +": "+ this.addDish(d));
        }
    }
    public boolean isEmptyTable(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String query = "SELECT "+RId+" from " +
                Restaurant ;
        Cursor c = sqLiteDatabase.rawQuery(query,null);
        if (c.getCount() < 1){
            return true;
        }
        return false;
    }
}
