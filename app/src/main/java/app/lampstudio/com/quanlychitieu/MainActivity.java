package app.lampstudio.com.quanlychitieu;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import app.lampstudio.com.quanlychitieu.Adapter.AdapterTypeAccount;
import app.lampstudio.com.quanlychitieu.Constant.Constant;
import app.lampstudio.com.quanlychitieu.Model.ModelTypeAcc;
import app.lampstudio.com.quanlychitieu.database.DataApp;
import app.lampstudio.com.quanlychitieu.database.SqliteDatabase;

import static app.lampstudio.com.quanlychitieu.R.id.btn_add_deal;
import static app.lampstudio.com.quanlychitieu.R.id.btn_manager_acc;
import static app.lampstudio.com.quanlychitieu.R.id.btn_statis_deal;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnAbout;
    Button btnAddDeal;
    Button btnStatiticDeal;
    Button btnManagerAcc;
    Intent intent;
    String TAG = "MainActivity";
    SqliteDatabase sqliteDatabase;
    ListView listView;
    AdapterTypeAccount adapterTypeAccount;
    public ArrayList<ModelTypeAcc> myArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAbout = (Button) findViewById(R.id.btn_about);
        btnAddDeal = (Button) findViewById(btn_add_deal);
        btnStatiticDeal = (Button) findViewById(btn_statis_deal);
        btnManagerAcc = (Button) findViewById(btn_manager_acc);
        listView = (ListView) findViewById(R.id.list_acc);
        myArrayList = new ArrayList<>();

        btnAbout.setOnClickListener(this);
        btnAddDeal.setOnClickListener(this);
        btnStatiticDeal.setOnClickListener(this);
        btnManagerAcc.setOnClickListener(this);
        sqliteDatabase = new SqliteDatabase(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_about:
                intent = new Intent(MainActivity.this,Add_transaction.class);
                startActivity(intent);
                break;
            case btn_add_deal:
                intent = new Intent(MainActivity.this,Add_transaction.class);
                startActivity(intent);
                break;
            case btn_statis_deal:
                intent = new Intent(MainActivity.this,Statis_deal.class);
                startActivity(intent);
                break;
            case btn_manager_acc:
                intent = new Intent(MainActivity.this,Manager_acc.class);
                startActivity(intent);
                break;
        }
    }
    public void ShowDataAccount(){
        DataApp.getInstance().ListTypeAcc.clear();
        DataApp.getInstance().hashMap_type_Acc.clear();
        Cursor cursor = sqliteDatabase.Rawquery(Constant.selectAllAcc);
        long sum_sum = 0;
        if(cursor != null && cursor.moveToFirst()){
            do{
                int ID = cursor.getInt(cursor.getColumnIndex(Constant.columID));
                String name = cursor.getString(cursor.getColumnIndex(Constant.columName_Account));
                long sum_money = cursor.getLong(cursor.getColumnIndex(Constant.columMoney_Account));
                ModelTypeAcc item = new ModelTypeAcc(ID,name,sum_money);
                sum_sum += sum_money;
                DataApp.getInstance().ListTypeAcc.add(item);
                DataApp.getInstance().hashMap_type_Acc.put(item.getId(),item);
            }
            while(cursor.moveToNext());
        }
        myArrayList = Untils.getInstance().Clone(DataApp.getInstance().ListTypeAcc);

        ModelTypeAcc item = new ModelTypeAcc(10,getResources().getString(R.string.sum_money),sum_sum);
        myArrayList.add(item);
        adapterTypeAccount = new AdapterTypeAccount(MainActivity.this,myArrayList);
        listView.setAdapter(adapterTypeAccount);
    }


    @Override
    protected void onResume() {
        super.onResume();
        ShowDataAccount();
    }
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
