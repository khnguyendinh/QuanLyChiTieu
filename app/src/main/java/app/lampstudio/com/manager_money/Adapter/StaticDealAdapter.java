package app.lampstudio.com.manager_money.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.lampstudio.com.manager_money.Model.ModelDeal;
import app.lampstudio.com.manager_money.R;
import app.lampstudio.com.manager_money.database.DataApp;

/**
 * Created by VS9 X64Bit on 30/04/2017.
 */

public class StaticDealAdapter extends RecyclerView.Adapter<StaticDealAdapter.Holder> {
    Context context;
    ArrayList<ModelDeal> listDeals;
    LayoutInflater layoutInflater;

    public StaticDealAdapter(Context context, ArrayList<ModelDeal> listDeals) {
        this.context = context;
        this.listDeals = listDeals;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = layoutInflater.inflate(R.layout.item_static_deal,parent,false);
        Holder holder = new Holder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.date.setText(listDeals.get(position).getTime()+" "+listDeals.get(position).getDate());
        holder.content.setText(listDeals.get(position).getContent());
        holder.money.setText(listDeals.get(position).getMoney()+"");
        holder.balance.setText(listDeals.get(position).getBalance()+"");
        String type_acc = DataApp.getInstance().hashMap_type_Acc.get(listDeals.get(position).getType_acc_deal())
                .getName();
        holder.type_acc.setText(type_acc);
        String type_deal = DataApp.getInstance().hashMap_type_Deal.get(listDeals.get(position).getTypeDeal());
        holder.type_deal.setText(type_deal);
    }

    @Override
    public int getItemCount() {
        return listDeals.size();
    }

    class Holder extends RecyclerView.ViewHolder{
        TextView date;
        TextView content;
        TextView money;
        TextView balance;
        TextView type_acc;
        TextView type_deal;

        public Holder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.date);
            content = (TextView) itemView.findViewById(R.id.content);
            money = (TextView) itemView.findViewById(R.id.money);
            balance = (TextView) itemView.findViewById(R.id.balance);
            type_acc = (TextView) itemView.findViewById(R.id.type_acc);
            type_deal = (TextView) itemView.findViewById(R.id.type_deal);
        }
    }
}
