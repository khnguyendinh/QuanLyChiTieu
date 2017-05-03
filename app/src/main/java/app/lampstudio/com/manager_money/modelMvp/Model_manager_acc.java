package app.lampstudio.com.manager_money.modelMvp;

import android.content.ContentValues;
import android.content.Context;

import app.lampstudio.com.manager_money.Constant.Constant;
import app.lampstudio.com.manager_money.Model.ModelTypeAcc;
import app.lampstudio.com.manager_money.database.SqliteDatabase;

/**
 * Created by VS9 X64Bit on 27/04/2017.
 */

public class Model_manager_acc {
    RPListener_Manager_acc rpListener_manager_acc;
    Context context;
    SqliteDatabase sqliteDatabase;
    public Model_manager_acc(RPListener_Manager_acc rpListener_manager_acc, Context context) {
        this.rpListener_manager_acc = rpListener_manager_acc;
        this.context = context;
        sqliteDatabase = new SqliteDatabase(context);
    }

    public void AddAcc(String typeAcc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.columName_Account, typeAcc);
        sqliteDatabase.InsertData(Constant.tableAccouts,contentValues);
        ModelTypeAcc modelTypeAcc = new ModelTypeAcc(1,typeAcc,0);
        rpListener_manager_acc.AddAccSuccess(modelTypeAcc);
    }

    public void RemoveAcc(int indexDB,int index) {
        sqliteDatabase.RemoveRecordAccount(new String[]{String.valueOf(indexDB)});
        rpListener_manager_acc.RemoveAccSuccess(index);
    }
}
