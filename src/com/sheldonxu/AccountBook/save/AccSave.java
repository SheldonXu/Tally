package com.sheldonxu.AccountBook.save;

import android.content.Context;
import com.sheldonxu.AccountBook.helper.MySQLiteOpenHelper;

/**
 * Created by sheldon.Xu on 15-4-9.
 */
public class AccSave {
    public boolean save(String type, String money, String date, String typename, Context context) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        String sql = "insert into tb_saved(accmoney,acctype,accdate,acctypename) values (?,?,?,?)";
        boolean flag = helper.execData(sql, new String[]{money, type, date, typename});
        return flag;
    }

    public boolean update(String _id, String type, String money, String date, String typename, Context context) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        String sql = "update tb_saved set accmoney=?,acctype=?,accdate=?,acctypename=?  where _id=? ";
        boolean flag = helper.execData(sql, new String[]{money, type, date, typename, _id});
        return flag;
    }

    public boolean delete(String _id, Context context) {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(context);
        String sql = "delete from tb_saved where _id=?";
        boolean flag = helper.execData(sql, new String[]{_id});
        return flag;
    }


}
