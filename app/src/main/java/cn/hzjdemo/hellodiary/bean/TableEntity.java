package cn.hzjdemo.hellodiary.bean;

import com.bin.david.form.annotation.SmartTable;

/**
 * @author:Hzj
 * @date :2019/1/14/014
 * desc  ：表格 数据实体
 * record：
 */
@SmartTable(name = "")
public class TableEntity {
    private String date;
    private Double num1;
    private Double num2;
    private Double num3;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getNum1() {
        return num1;
    }

    public void setNum1(Double num1) {
        this.num1 = num1;
    }

    public Double getNum2() {
        return num2;
    }

    public void setNum2(Double num2) {
        this.num2 = num2;
    }

    public Double getNum3() {
        return num3;
    }

    public void setNum3(Double num3) {
        this.num3 = num3;
    }

    @Override
    public String toString() {
        return "TableEntity{" +
                "date='" + date + '\'' +
                ", num1=" + num1 +
                ", num2=" + num2 +
                ", num3=" + num3 +
                '}';
    }
}
