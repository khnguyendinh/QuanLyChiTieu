package app.lampstudio.com.quanlychitieu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.lampstudio.com.quanlychitieu.Constant.Constant;
import app.lampstudio.com.quanlychitieu.Model.ModelDeal;
import app.lampstudio.com.quanlychitieu.R;

import static app.lampstudio.com.quanlychitieu.Constant.Constant.Type_Account_Deal;
import static app.lampstudio.com.quanlychitieu.Constant.Constant.columID;

/**
 * Created by VS9 X64Bit on 26/04/2017.
 */

public class SqliteDatabase extends SQLiteOpenHelper {
    Context context;
    String TAG = "SqliteDatabase";
    private SQLiteDatabase mSQLiteDatabase;

    public SqliteDatabase(Context context) {
        super(context, Constant.nameDB, null, Constant.DB_VERSION);
        this.context = context;
        getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Constant.Create_Table_Account);
        sqLiteDatabase.execSQL(Constant.Create_Table_Deals);
        mSQLiteDatabase = sqLiteDatabase;
        ContentValues contentValues1 = new ContentValues();
        ContentValues contentValues2 = new ContentValues();
        ContentValues contentValues3 = new ContentValues();

        contentValues1.put(Constant.columName_Account,context.getResources().getString(R.string.cash));
        contentValues2.put(Constant.columName_Account,context.getResources().getString(R.string.credit));
        contentValues3.put(Constant.columName_Account,context.getResources().getString(R.string.saved_money));

        InsertData(Constant.tableAccouts,contentValues1);
        InsertData(Constant.tableAccouts,contentValues2);
        InsertData(Constant.tableAccouts,contentValues3);

    }
    public Cursor Rawquery(String sql){
        return getReadableDatabase().rawQuery(sql,null);
    }

    public long InsertData(String table, ContentValues contentValues){
        if(mSQLiteDatabase != null){
            return mSQLiteDatabase.insert(table,null,contentValues);
        }else {
            return getWritableDatabase().insert(table,null,contentValues);
        }

    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+Constant.tableAccouts);
        onCreate(sqLiteDatabase);

    }
    public void RemoveRecordAccount(String[] id){
        getWritableDatabase().delete(Constant.tableAccouts,columID+ " =? ",id);
        getWritableDatabase().delete(Constant.tableDeals,Type_Account_Deal+ " =? ",id);
    }
    public void getDeals(int type_acc,int type_deal,String start_Date,String end_Date){
        DataApp.getInstance().listModelDeal.clear();
        Cursor cursor = Rawquery(Constant.selectConditionDeals+type_acc+" AND "+
                Constant.Type_Deal+" = "+type_deal+" AND "+Constant.Date_Deal+" BETWEEN '"+start_Date+"' AND '"+end_Date+"'");
        if(cursor != null && cursor.moveToFirst()){
            do{
                int ID = cursor.getInt(cursor.getColumnIndex(Constant.ID_Deal));
                String date = cursor.getString(cursor.getColumnIndex(Constant.Date_Deal));
                String time = cursor.getString(cursor.getColumnIndex(Constant.Time_Deal));
                String content = cursor.getString(cursor.getColumnIndex(Constant.Content_Deal));
                int money = cursor.getInt(cursor.getColumnIndex(Constant.Money_Deal));
                int balance = cursor.getInt(cursor.getColumnIndex(Constant.Balance_Deal));
                int type_acc_deal = cursor.getInt(cursor.getColumnIndex(Type_Account_Deal));
                int typeDeal = cursor.getInt(cursor.getColumnIndex(Constant.Type_Deal));
                ModelDeal item = new ModelDeal(ID,date,time,content,money,balance,type_acc_deal,typeDeal);
                DataApp.getInstance().listModelDeal.add(item);
            }
            while(cursor.moveToNext());
        }
    }

    public void UpdateMoneyAcc(ContentValues contentValues) {
        String sql = "UPDATE "+Constant.tableAccouts+ " SET "+Constant.columMoney_Account +" = "+
                contentValues.get(Constant.columMoney_Account)+" WHERE "+
                Constant.columID +" = "+contentValues.get(Constant.columID)+
                ";";
        getWritableDatabase().execSQL(sql);
    }
}
