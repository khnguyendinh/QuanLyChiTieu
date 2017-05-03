package app.lampstudio.com.manager_money.modelMvp;

import android.content.ContentValues;
import android.content.Context;

import app.lampstudio.com.manager_money.Constant.Constant;
import app.lampstudio.com.manager_money.Model.ModelDeal;
import app.lampstudio.com.manager_money.database.SqliteDatabase;

/**
 * Created by VS9 X64Bit on 27/04/2017.
 */

public class Model_add_deals {
    RPListener_add_deal rpListener_add_deal;
    Context context;
    SqliteDatabase sqliteDatabase;

    public Model_add_deals(RPListener_add_deal rpListener_add_deal, Context context) {
        this.rpListener_add_deal = rpListener_add_deal;
        this.context = context;
        this.sqliteDatabase = new SqliteDatabase(context);
    }
    public void AddDeal(ModelDeal modelDeal){
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.Date_Deal, modelDeal.getDate());
        contentValues.put(Constant.Time_Deal, modelDeal.getTime());
        contentValues.put(Constant.Content_Deal, modelDeal.getContent());
        contentValues.put(Constant.Money_Deal, modelDeal.getMoney());
        contentValues.put(Constant.Balance_Deal, modelDeal.getBalance());
        contentValues.put(Constant.Type_Account_Deal, modelDeal.getType_acc_deal());
        contentValues.put(Constant.Type_Deal, modelDeal.getTypeDeal());
        sqliteDatabase.InsertData(Constant.tableDeals,contentValues);
        rpListener_add_deal.AddDealSuccess();

    }

    public void UpdateMoneyAcc(int idAcc,long sum_mmoney) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.columID, idAcc);
        contentValues.put(Constant.columMoney_Account, sum_mmoney);
        sqliteDatabase.UpdateMoneyAcc(contentValues);
    }
}
