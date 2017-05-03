package app.lampstudio.com.manager_money.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import app.lampstudio.com.manager_money.Model.ModelTypeAcc;
import app.lampstudio.com.manager_money.R;
import app.lampstudio.com.manager_money.presenter.Presenter_Manager_Acc;

/**
 * Created by VS9 X64Bit on 26/04/2017.
 */

public class AdpterAcc_ManagerAcc extends BaseAdapter {
    Context mContext;
    ArrayList<ModelTypeAcc> mData;
    LayoutInflater mLayout;
    Presenter_Manager_Acc presenter_manager_acc;

    public AdpterAcc_ManagerAcc(Context mContext, ArrayList<ModelTypeAcc> mData,Presenter_Manager_Acc presenter_manager_acc) {
        this.mContext = mContext;
        this.mData = mData;
        this.presenter_manager_acc = presenter_manager_acc;
        this.mLayout = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = mLayout.inflate(R.layout.item_acc,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.title);
            viewHolder.btnDelete = (Button) view.findViewById(R.id.btnDelete);
            viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    final int j = i;
//                    sqliteDatabase.RemoveRecordAccount(new String[]{String.valueOf(i)});
                    presenter_manager_acc.RemoveAcc(mData.get(i).getId(),i);
                }
            });
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ModelTypeAcc modelTypeAcc = mData.get(i);
        viewHolder.name.setText(modelTypeAcc.getName());
        return view;
    }
    public class ViewHolder{
        TextView name;
        Button btnDelete;
    }
}
