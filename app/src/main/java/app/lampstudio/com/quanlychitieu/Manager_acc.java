package app.lampstudio.com.quanlychitieu;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import app.lampstudio.com.quanlychitieu.Adapter.AdpterAcc_ManagerAcc;
import app.lampstudio.com.quanlychitieu.Constant.Constant;
import app.lampstudio.com.quanlychitieu.Model.ModelTypeAcc;
import app.lampstudio.com.quanlychitieu.database.SqliteDatabase;
import app.lampstudio.com.quanlychitieu.presenter.Presenter_Manager_Acc;
import app.lampstudio.com.quanlychitieu.view.View_Manager_acc;

public class Manager_acc extends AppCompatActivity implements View_Manager_acc{
    SqliteDatabase sqliteDatabase;
    ListView listView;
    ArrayList<ModelTypeAcc> arrayList;
    AdpterAcc_ManagerAcc adapterTypeAccount;
    EditText type_acc_add;
    Button btn_add;
    Presenter_Manager_Acc presenter_manager_acc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter_manager_acc = new Presenter_Manager_Acc(this,this);
        setContentView(R.layout.activity_manager_acc);
        sqliteDatabase = new SqliteDatabase(Manager_acc.this);
        listView = (ListView) findViewById(R.id.list_acc);
        arrayList = new ArrayList<>();
        type_acc_add = (EditText) findViewById(R.id.type_acc_add);
        btn_add = (Button) findViewById(R.id.btn_add);
        ShowDataAccount();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String cotent = type_acc_add.getText().toString();
                presenter_manager_acc.AddAcc(cotent);
            }
        });

    }
    public void ShowDataAccount(){
        Cursor cursor = sqliteDatabase.Rawquery(Constant.selectAllAcc);
        if(cursor != null && cursor.moveToFirst()){
            do{
                int ID = cursor.getInt(cursor.getColumnIndex(Constant.columID));
                String name = cursor.getString(cursor.getColumnIndex(Constant.columName_Account));
                long sum_money = cursor.getLong(cursor.getColumnIndex(Constant.columMoney_Account));
                ModelTypeAcc item = new ModelTypeAcc(ID,name,sum_money);
                arrayList.add(item);
            }
            while(cursor.moveToNext());
        }
        adapterTypeAccount = new AdpterAcc_ManagerAcc(Manager_acc.this,arrayList,presenter_manager_acc);
        listView.setAdapter(adapterTypeAccount);
    }

    @Override
    public void RemoveAccSuccess(int index) {
        arrayList.remove(index);
        adapterTypeAccount.notifyDataSetChanged();
    }

    @Override
    public void AddAccSuccess(ModelTypeAcc modelTypeAcc) {
        arrayList.add(modelTypeAcc);
        adapterTypeAccount.notifyDataSetChanged();
        type_acc_add.setText("");
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(Manager_acc.this,MainActivity.class);
//        startActivity(intent);
//    }
}
