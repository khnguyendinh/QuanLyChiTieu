package app.lampstudio.com.quanlychitieu.Model;

/**
 * Created by VS9 X64Bit on 27/04/2017.
 */

public class ModelDeal {
    int id;
    String date,time;
    String content;
    long money;
    long balance;
    int type_acc_deal;//cash credit save_money
    int typeDeal;//thu chi

    public ModelDeal(int id, String date, String time, String content, long money, long balance,
                     int type_acc_deal, int typeDeal) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.content = content;
        this.money = money;
        this.balance = balance;
        this.type_acc_deal = type_acc_deal;
        this.typeDeal = typeDeal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public int getType_acc_deal() {
        return type_acc_deal;
    }

    public void setType_acc_deal(int type_acc_deal) {
        this.type_acc_deal = type_acc_deal;
    }

    public int getTypeDeal() {
        return typeDeal;
    }

    public void setTypeDeal(int typeDeal) {
        this.typeDeal = typeDeal;
    }
}
