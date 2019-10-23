package com.example.myapp1;

//数据库的实体类，封装了id，货币的名称curName，货币的汇率curRate
public class RateItem {
    private  int id;
    private String curName;
    private String curRate;

    //construct构造函数
    public RateItem() {
        this.curName = "";
        this.curRate = "";
    }

    //construct构造函数
    public RateItem(String curName, String curRate) {
        this.curName = curName;
        this.curRate = curRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurName() {
        return curName;
    }

    public void setCurName(String curName) {
        this.curName = curName;
    }

    public String getCurRate() {
        return curRate;
    }

    public void setCurRate(String curRate) {
        this.curRate = curRate;
    }
}
