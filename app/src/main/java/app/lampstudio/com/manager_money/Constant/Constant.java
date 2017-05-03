package app.lampstudio.com.manager_money.Constant;

/**
 * Created by VS9 X64Bit on 26/04/2017.
 */

public class Constant {
    public static String nameDB = "quan_ly_chi_tieu.db";
    public static final String tableAccouts = "tbl_accouts";
    public static final String columID = "_id";
    public static final String columName_Account = "_name";
    public static final String columMoney_Account = "_money";
    public static final String selectAllAcc = "SELECT * FROM "+ tableAccouts;

    public static final String tableDeals = "tbl_deals";
    public static final String ID_Deal = "_id";
    public static final String Date_Deal = "_date_deal";
    public static final String Time_Deal = "_time_deal";
    public static final String Content_Deal = "_content";
    public static final String Money_Deal = "_money";
    public static final String Balance_Deal = "_balance";
    public static final String Type_Account_Deal = "_type_Account";
    public static final String Type_Deal = "_type_Deal";
    public static final String selectAllDeals = "SELECT * FROM "+ tableDeals;
    public static final String selectConditionDeals = "SELECT * FROM "+ tableDeals +" WHERE "+Type_Account_Deal +" = ";

    public static final int DB_VERSION = 1;
    public static final String Create_Table_Account = "CREATE TABLE "+tableAccouts+"("+
            columID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + columName_Account +" TEXT NOT NULL," +
            columMoney_Account +" LONG " +
            ");";
    public static final String Create_Table_Deals = "CREATE TABLE "+tableDeals+"("+
            ID_Deal+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + Date_Deal +" DATETIME DEFAULT CURRENT_TIMESTAMP, "
            + Time_Deal +" TEXT NOT NULL, "
            + Content_Deal +" TEXT NOT NULL, "
            + Money_Deal +" LONG, "
            + Balance_Deal +" LONG, "
            + Type_Account_Deal +" INTEGER,"
            + Type_Deal +" INTEGER"
            +");";
    Constant() {
    }
}
