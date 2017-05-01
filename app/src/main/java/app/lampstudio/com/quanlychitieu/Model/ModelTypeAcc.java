package app.lampstudio.com.quanlychitieu.Model;

/**
 * Created by VS9 X64Bit on 26/04/2017.
 */

public class ModelTypeAcc {
    int id;
    String name;
    long sum_money;

    public ModelTypeAcc(int id, String name, long sum_money) {
        this.id = id;
        this.name = name;
        this.sum_money = sum_money;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSum_money() {
        return sum_money;
    }

    public void setSum_money(long sum_money) {
        this.sum_money = sum_money;
    }
}
