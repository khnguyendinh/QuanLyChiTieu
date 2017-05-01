package app.lampstudio.com.quanlychitieu.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.lampstudio.com.quanlychitieu.Model.ModelTypeAcc;
import app.lampstudio.com.quanlychitieu.R;

/**
 * Created by VS9 X64Bit on 26/04/2017.
 */

public class AdapterTypeAccount extends BaseAdapter {
    Context mContext;
    ArrayList<ModelTypeAcc> mData;
    LayoutInflater mLayout;

    public AdapterTypeAccount(Context mContext, ArrayList<ModelTypeAcc> mData) {
        this.mContext = mContext;
        this.mData = mData;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = mLayout.inflate(R.layout.item_infor_account,viewGroup,false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.title);
            viewHolder.money = (TextView) view.findViewById(R.id.money);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ModelTypeAcc modelTypeAcc = mData.get(i);
        viewHolder.name.setText(modelTypeAcc.getName());
        viewHolder.money.setText(modelTypeAcc.getSum_money()+"");
        return view;
    }
    public class ViewHolder{
        TextView name;
        TextView money;
    }
}
