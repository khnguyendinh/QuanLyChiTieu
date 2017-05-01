package app.lampstudio.com.quanlychitieu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import app.lampstudio.com.quanlychitieu.Adapter.StaticDealAdapter;
import app.lampstudio.com.quanlychitieu.database.DataApp;
import app.lampstudio.com.quanlychitieu.database.SqliteDatabase;

import static app.lampstudio.com.quanlychitieu.Add_transaction.TYPE_DEAL.CHI;
import static app.lampstudio.com.quanlychitieu.Add_transaction.TYPE_DEAL.THU;

public class Statis_deal extends AppCompatActivity implements View.OnClickListener{
    Button btn_Start_date;
    Button btn_End_date;
    Spinner spin_type_money;
    Spinner spin_type_spend;
    RecyclerView recyclerView;
    StaticDealAdapter staticDealAdapter;

    private String[] arraySpinner;
    Calendar mStartDate;
    Calendar mEndDate;
    int id_type_acc_cur = 0;
    Add_transaction.TYPE_DEAL type_spend = THU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statis_deal);
        initView();
    }

    private void initView() {
        mStartDate = Calendar.getInstance();
        mEndDate = Calendar.getInstance();
        btn_Start_date = (Button) findViewById(R.id.btn_start_date);
        btn_End_date = (Button) findViewById(R.id.btn_end_date);
        spin_type_money = (Spinner) findViewById(R.id.spn_type_money);
        spin_type_spend = (Spinner) findViewById(R.id.spn_type_spend);
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        btn_Start_date.setOnClickListener(this);
        btn_End_date.setOnClickListener(this);

        arraySpinner = new String[DataApp.getInstance().ListTypeAcc.size()];
        for (int i = 0; i < DataApp.getInstance().ListTypeAcc.size(); i++) {
            arraySpinner[i] = DataApp.getInstance().ListTypeAcc.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        spin_type_money.setAdapter(adapter);
        id_type_acc_cur = DataApp.getInstance().ListTypeAcc.get(0).getId();
        spin_type_money.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                id_type_acc_cur = DataApp.getInstance().ListTypeAcc.get(i).getId();
                showListDeal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin_type_spend.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        type_spend = THU;
                        break;
                    case 1:
                        type_spend = CHI;
                        break;
                }
                showListDeal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ShowDateAndTime();
        showListDeal();
    }

    private void showListDeal() {
        SqliteDatabase sqliteDatabase = new SqliteDatabase(Statis_deal.this);
        sqliteDatabase.getDeals(id_type_acc_cur,type_spend.ordinal(),btn_Start_date.getText().toString(),
                btn_End_date.getText().toString());
        staticDealAdapter = new StaticDealAdapter(this,DataApp.getInstance().listModelDeal);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(staticDealAdapter);
    }

    private void ShowDateAndTime() {
        String pattern = "dd/MM/yyyy";
        String dateInString = new SimpleDateFormat(pattern).format(new Date());
        btn_Start_date.setText(dateInString);
        btn_End_date.setText(dateInString);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_start_date:
                DatePickerDialog.OnDateSetListener dateCallBack = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        mStartDate.set(Calendar.YEAR, year);
                        mStartDate.set(Calendar.MONTH, monthOfYear);
                        mStartDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateBtnDate();
                    }

                };
                DatePickerDialog pic = new DatePickerDialog(this,dateCallBack, mStartDate.get(Calendar.YEAR),
                        mStartDate.get(Calendar.MONTH), mStartDate.get(Calendar.DATE));
                pic.setTitle("Chọn ngày bắt đầu");
                pic.show();
                break;
            case R.id.btn_end_date:
                DatePickerDialog.OnDateSetListener dateCallBackEnd = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        mEndDate.set(Calendar.YEAR, year);
                        mEndDate.set(Calendar.MONTH, monthOfYear);
                        mEndDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateBtnDate();
                    }

                };
                DatePickerDialog picEnd = new DatePickerDialog(this,dateCallBackEnd, mEndDate.get(Calendar.YEAR),
                        mEndDate.get(Calendar.MONTH), mEndDate.get(Calendar.DATE));
                picEnd.setTitle("Chọn ngày Kết thúc");
                picEnd.show();
                break;
        }
    }

    private void updateBtnDate() {
        String pattern = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        btn_Start_date.setText(sdf.format(mStartDate.getTime()));
        btn_End_date.setText(sdf.format(mEndDate.getTime()));
        if(mEndDate.after(mStartDate)){
            showListDeal();
        }else {
            Toast.makeText(this, "Please select end date after start date", Toast.LENGTH_SHORT).show();
        }
    }
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(Statis_deal.this,MainActivity.class);
//        startActivity(intent);
//    }
}
