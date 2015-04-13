package com.sheldonxu.AccountBook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.sheldonxu.AccountBook.helper.DateHelper;
import com.sheldonxu.AccountBook.helper.MySQLiteOpenHelper;
import com.sheldonxu.AccountBook.myview.CookieView;

import java.util.*;

/**
 * Created by sheldon.Xu on 15-4-10.
 */
public class AnalyzeActivity extends Activity {
    private TextView textView_date;
    private Spinner sp_size;
    private Spinner sp_type;
    private CookieView cookieView;
    private int acctype;
    String startdate;
    String enddate;

    List<Map<String,Object>> list;
    private void init(){
        textView_date= (TextView) findViewById(R.id.textView_date);
        sp_size= (Spinner) findViewById(R.id.sp_size);
        cookieView= (CookieView) findViewById(R.id.cookieView);
        sp_type= (Spinner) findViewById(R.id.sp_type);
        acctype=6;
        startdate=textView_date.getText().toString();
        enddate=null;
        final List<String> list_size=new ArrayList<String>();
        list_size.add("周");
        list_size.add("月");
        list_size.add("年");
        List<String> list_type=new ArrayList<String>();
        list_type.add("收入");
        list_type.add("支出");
        SpinnerAdapter spinnerAdapter_type = new ArrayAdapter<String>(AnalyzeActivity.this,
                android.R.layout.simple_spinner_item, android.R.id.text1,
                list_type);
        sp_type.setAdapter(spinnerAdapter_type);
        sp_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                SpinnerAdapter spinnerAdapter;
                switch(i){
                    case 0:
                        acctype=4;
                        spinnerAdapter = new ArrayAdapter<String>(AnalyzeActivity.this,
                                android.R.layout.simple_spinner_item, android.R.id.text1,
                                list_size);
                        sp_size.setAdapter(spinnerAdapter);
                        break;
                    case 1:
                        acctype=6;
                        spinnerAdapter = new ArrayAdapter<String>(AnalyzeActivity.this,
                                android.R.layout.simple_spinner_item, android.R.id.text1,
                                list_size);
                        sp_size.setAdapter(spinnerAdapter);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<String>(AnalyzeActivity.this,
                android.R.layout.simple_spinner_item, android.R.id.text1,
                list_size);
        sp_size.setAdapter(spinnerAdapter);
        sp_size.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                startdate=textView_date.getText().toString();
                enddate=null;


                switch (position){
                    case 0:
                        enddate= DateHelper.getDate(startdate,7);
                        break;
                    case 1:
                        enddate= DateHelper.getDate(startdate,30);
                        break;
                    case 2:
                        enddate= DateHelper.getDate(startdate,365);
                        break;
                }
                MySQLiteOpenHelper mySQLiteOpenHelper=new MySQLiteOpenHelper(AnalyzeActivity.this);
                String sql="select acctype,acctypename,sum(accmoney) as accmoney from tb_saved where ?<=accdate and accdate<? group by acctype";

                if (startdate==null){
                    startdate="1000-01-01";
                }
                if(enddate==null){
                    enddate="1000-01-01";
                }
                Log.i("list1",startdate+"  "+enddate);
                list=mySQLiteOpenHelper.selectList(sql,new String[] {startdate,enddate});
                Log.i("list1",list.size()+"");
                List<String> list1=new ArrayList<String>();
                List<String> list2=new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    Map<String,Object> map= new HashMap<String, Object>();
                    map=list.get(i);
                    if(map.get("acctype").toString().length()==acctype){
                        list1.add(map.get("accmoney").toString());
                        list2.add(map.get("acctypename").toString());
                    }
                }
                if(list1.size()>0)
                Log.i("list1",list1.get(0));
//                list1.add(123.5 + "");
//                list1.add(200.5 + "");
//                list1.add(70.5 + "");
//                list1.add(300.5 + "");
//                list2.add("工资1");
//                list2.add("工资2");
//                list2.add("工资3");
//                list2.add("工资4");
                cookieView.setList(list1, list2);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analyze_activity);
        init();
    }

    public void clickButtonDate(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dateString = year + "-" + (monthOfYear + 1)
                                + "-" + dayOfMonth;
                        Toast.makeText(AnalyzeActivity.this,
                                "您选择的日期：" + dateString, Toast.LENGTH_SHORT)
                                .show();
                        String [] str=dateString.split("-");
                        if(str[1].length()==1){
                            dateString=dateString.substring(0,5)+"0"+dateString.substring(5,dateString.length());
                        }
                        if(str[2].length()==1){
                            dateString=dateString.substring(0,8)+"0"+dateString.substring(8,dateString.length());
                        }
                        textView_date.setText(dateString);
                    }
                }, year, monthOfYear, dayOfMonth);
        dDialog.show();
    }
}