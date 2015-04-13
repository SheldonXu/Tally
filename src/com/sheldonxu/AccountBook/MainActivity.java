package com.sheldonxu.AccountBook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.sheldonxu.AccountBook.acctype.A;
import com.sheldonxu.AccountBook.acctype.B;
import com.sheldonxu.AccountBook.acctype.C;
import com.sheldonxu.AccountBook.adapter.MainListViewAdapter;
import com.sheldonxu.AccountBook.helper.MySQLiteOpenHelper;
import com.sheldonxu.AccountBook.info.AccInfo;
import com.sheldonxu.AccountBook.save.AccSave;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity {
    private Spinner sp_a, sp_b, sp_c;
    private List<A> list;
    private ArrayAdapter<A> aAdapter;
    private ArrayAdapter<B> bAdapter;
    private ArrayAdapter<C> cAdapter;
    private int currentA;
    private int currentB;
    private TextView textView_date;
    private EditText editTextMoney;
    private String accNo;
    private ListView listView_main;
    private Map<String, String> infoMap;
    private List<Map<String,Object>> listinfo;
    private MainListViewAdapter adapter;
    private void init() {

        listView_main = (ListView) findViewById(R.id.listView_main);
        infoMap = AccInfo.getAccInfo();
    }

    /**
     * Called when the activity is first created.
     */
    int bsp_a=0;
    int bsp_b=0;
    int bsp_c=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        init();
        try {
            list = getAs();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        adapter=new MainListViewAdapter(this,getListinfo());
        listView_main.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView_main.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i1, long l) {
                 bsp_a=0;
                 bsp_b=0;
                 bsp_c=0;
                Map<String,Object> map=getListinfo().get(i1);
                Log.i("typetypetypetypetypetypetypetype",""+map.get("acctype"));
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                builder2.setIcon(R.drawable.ic_launcher);
                builder2.setTitle("更新数据");
                final View loginView = LayoutInflater.from(MainActivity.this).inflate(
                        R.layout.dialog_save, null);
                sp_a = (Spinner) loginView.findViewById(R.id.sp_a);
                sp_b = (Spinner) loginView.findViewById(R.id.sp_b);
                sp_c = (Spinner) loginView.findViewById(R.id.sp_c);
                textView_date = (TextView) loginView.findViewById(R.id.textView_date);
                editTextMoney = (EditText) loginView.findViewById(R.id.editTextMoney);







                aAdapter = new ArrayAdapter<A>(MainActivity.this,
                        android.R.layout.simple_spinner_item, android.R.id.text1,
                        list);
                bAdapter = new ArrayAdapter<B>(MainActivity.this,
                        android.R.layout.simple_spinner_item, android.R.id.text1,
                        list.get(Integer.parseInt((map.get("acctype")+"").substring(0,2))-1).getBs());
                if((map.get("acctype")+"").length()==6) {


                    Log.i("4~6",""+Integer.parseInt((map.get("acctype") + "").substring(2, 4)));


                    Log.i("4~6",""+         list.get(Integer.parseInt((map.get("acctype")+"").substring(0,2))-1).getBs().get(2).getCs());


                    cAdapter = new ArrayAdapter<C>(MainActivity.this,
                            android.R.layout.simple_spinner_item, android.R.id.text1,
                            list.get(Integer.parseInt((map.get("acctype") + "").substring(0, 2)) - 1).getBs().get(Integer.parseInt((map.get("acctype") + "").substring(2, 4)) - 1).getCs());
                }
                aAdapter.setDropDownViewResource(R.layout.sp_item);
                bAdapter.setDropDownViewResource(R.layout.sp_item);
                cAdapter.setDropDownViewResource(R.layout.sp_item);
                sp_a.setAdapter(aAdapter);
                sp_b.setAdapter(bAdapter);
                sp_c.setAdapter(cAdapter);

                sp_a.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        currentA = position;
                        if(bsp_a>0) {

                            if (position == 0) {
                                sp_c.setVisibility(View.GONE);
                            } else {
                                sp_c.setVisibility(View.VISIBLE);
                            }
                            bAdapter = new ArrayAdapter<B>(MainActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    android.R.id.text1, list.get(position).getBs());
                            bAdapter
                                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_b.setAdapter(bAdapter);
                        }else{
                            bsp_a++;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });

                sp_b.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        currentB = position;
                        if(bsp_b>1) {
                            accNo = list.get(currentA).getBs().get(currentB).getId();
                            cAdapter = new ArrayAdapter<C>(MainActivity.this,
                                    android.R.layout.simple_spinner_item,
                                    android.R.id.text1, list.get(currentA)
                                    .getBs().get(position).getCs());
                            cAdapter
                                    .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sp_c.setAdapter(cAdapter);
                        }else{
                            bsp_b++;
                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }

                });
                sp_c.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(bsp_c>0) {
                            accNo = list.get(currentA).getBs().get(currentB).getCs().get(i).getId();
                        }else{
                            bsp_c++;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                builder2.setNegativeButton("删除数据", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        delete(i1);
                        adapter.setList(getListinfo());
                        adapter.notifyDataSetChanged();
                    }
                });
                builder2.setPositiveButton("修改数据", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        update(i1);
                        adapter.setList(getListinfo());
                        adapter.notifyDataSetChanged();
                    }
                });
                textView_date.setText("" + map.get("accdate"));
                editTextMoney.setText(""+map.get("accmoney"));
                Log.i("acctype------>", (map.get("acctype") + ""));
                sp_a.setSelection(Integer.parseInt((map.get("acctype")+"").substring(0,2))-1);
                sp_b.setSelection(Integer.parseInt((map.get("acctype")+"").substring(2,4))-1);
                if((map.get("acctype")+"").length()==6) {
                    sp_c.setSelection(Integer.parseInt((map.get("acctype") + "").substring(4, 6))-1);

                }


                builder2.setView(loginView);
                builder2.show();







                return false;
            }
        });
    }

    private void delete(int i1) {
        Map<String,Object> map=getListinfo().get(i1);
        String _id = map.get("_id").toString();
        AccSave accSave=new AccSave();
        boolean flag =accSave.delete(_id,this);
        if (flag) {
            Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
        }
    }


    public List<A> getAs() throws XmlPullParserException,
            IOException {
        List<A> as = null;
        A a = null;
        List<B> bs = null;
        B b = null;
        List<C> cs = null;
        C c = null;

        InputStream inputStream = getResources().openRawResource(
                R.raw.accountbook);

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        XmlPullParser parser = factory.newPullParser();

        parser.setInput(inputStream, "utf-8");
        int event = parser.getEventType();
        while (event != XmlPullParser.END_DOCUMENT) {
            switch (event) {
                case XmlPullParser.START_DOCUMENT:
                    as = new ArrayList<A>();
                    break;
                case XmlPullParser.START_TAG:
                    String tagName = parser.getName();
                    if ("b".equals(tagName)) {
                        a = new A();
                        bs = new ArrayList<B>();
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attriName = parser.getAttributeName(i);
                            String attriValue = parser.getAttributeValue(i);
                            if ("b_id".equals(attriName))
                                a.setId(attriValue);
                        }
                    }
                    if ("bn".equals(tagName)) {
                        a.setName(parser.nextText());
                    }
                    if ("c".equals(tagName)) {
                        b = new B();
                        cs = new ArrayList<C>();

                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attriName = parser.getAttributeName(i);
                            String attriValue = parser.getAttributeValue(i);
                            if ("c_id".equals(attriName))
                                b.setId(attriValue);
                        }
                    }
                    if ("cn".equals(tagName)) {
                        b.setName(parser.nextText());
                    }
                    if ("d".equals(tagName)) {
                        c = new C();
                        int count = parser.getAttributeCount();
                        for (int i = 0; i < count; i++) {
                            String attriName = parser.getAttributeName(i);
                            String attriValue = parser.getAttributeValue(i);
                            if ("d_id".equals(attriName))
                                c.setId(attriValue);
                        }
                        c.setName(parser.nextText());
                        cs.add(c);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if ("c".equals(parser.getName())) {
                        b.setCs(cs);
                        bs.add(b);
                    }
                    if ("b".equals(parser.getName())) {
                        a.setBs(bs);
                        as.add(a);
                    }
                    break;
            }
            event = parser.next();
        }
        return as;
    }

    public void clickButtonDate(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dDialog = new DatePickerDialog(this,
                new OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String dateString = year + "-" + (monthOfYear + 1)
                                + "-" + dayOfMonth;
                        Toast.makeText(MainActivity.this,
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

    public void clickButtonSave(View view) {
        String money = editTextMoney.getText().toString();
        String date = textView_date.getText().toString();
        String type = accNo;
        if (date == null || "".equals(date)) {
            Toast.makeText(this, "请输入日期", Toast.LENGTH_SHORT).show();
            return;
        }
        if (money != null && !"".equals(money)) {
            try {
                System.out.println(Double.parseDouble(money));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "金额格式不对", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        AccSave accSave = new AccSave();
        boolean flag = accSave.save(type, money, date, infoMap.get(type), this);
        Log.i("infoMap.get(type)", infoMap.get(type));
        if (flag) {
            Toast.makeText(this, "数据保存成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "数据保存失败", Toast.LENGTH_SHORT).show();
        }
    }


    public void update(int i) {
        Map<String,Object> map=getListinfo().get(i);
        String money = editTextMoney.getText().toString();
        String date = textView_date.getText().toString();
        String type = accNo;
        String _id = map.get("_id").toString();
        if (date == null || "".equals(date)) {
            Toast.makeText(this, "请输入日期", Toast.LENGTH_SHORT).show();
            return;
        }
        if (money != null && !"".equals(money)) {
            try {
                System.out.println(Double.parseDouble(money));
            } catch (NumberFormatException e) {
                Toast.makeText(this, "金额格式不对", Toast.LENGTH_SHORT).show();
                return;
            }
        } else {
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        AccSave accSave = new AccSave();
        boolean flag = accSave.update(_id,type, money, date, infoMap.get(type), this);
        Log.i("infoMap.get(type)", infoMap.get(type));
        if (flag) {
            Toast.makeText(this, "数据更新成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "数据更新失败", Toast.LENGTH_SHORT).show();
        }
    }


    public void clickButtonTitle(View view) {
        switch (view.getId()) {
            case R.id.insertButton:

                AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
                builder2.setIcon(R.drawable.ic_launcher);
                builder2.setTitle("插入数据");
                final View loginView = LayoutInflater.from(this).inflate(
                        R.layout.dialog_save, null);
                sp_a = (Spinner) loginView.findViewById(R.id.sp_a);
                sp_b = (Spinner) loginView.findViewById(R.id.sp_b);
                sp_c = (Spinner) loginView.findViewById(R.id.sp_c);
                textView_date = (TextView) loginView.findViewById(R.id.textView_date);
                editTextMoney = (EditText) loginView.findViewById(R.id.editTextMoney);
                aAdapter = new ArrayAdapter<A>(this,
                        android.R.layout.simple_spinner_item, android.R.id.text1,
                        list);
                bAdapter = new ArrayAdapter<B>(this,
                        android.R.layout.simple_spinner_item, android.R.id.text1,
                        list.get(0).getBs());
                cAdapter = new ArrayAdapter<C>(this,
                        android.R.layout.simple_spinner_item, android.R.id.text1,
                        list.get(0).getBs().get(0).getCs());
                aAdapter.setDropDownViewResource(R.layout.sp_item);
                bAdapter.setDropDownViewResource(R.layout.sp_item);
                cAdapter.setDropDownViewResource(R.layout.sp_item);
                sp_a.setAdapter(aAdapter);
                sp_b.setAdapter(bAdapter);
                sp_c.setAdapter(cAdapter);

                sp_a.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        currentA = position;
                        if (position == 0) {
                            sp_c.setVisibility(View.GONE);
                        } else {
                            sp_c.setVisibility(View.VISIBLE);
                        }
                        bAdapter = new ArrayAdapter<B>(MainActivity.this,
                                android.R.layout.simple_spinner_item,
                                android.R.id.text1, list.get(position).getBs());
                        bAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_b.setAdapter(bAdapter);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }
                });

                sp_b.setOnItemSelectedListener(new OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        currentB = position;
                        accNo = list.get(currentA).getBs().get(currentB).getId();
                        cAdapter = new ArrayAdapter<C>(MainActivity.this,
                                android.R.layout.simple_spinner_item,
                                android.R.id.text1, list.get(currentA)
                                .getBs().get(position).getCs());
                        cAdapter
                                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_c.setAdapter(cAdapter);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // TODO Auto-generated method stub

                    }

                });
                sp_c.setOnItemSelectedListener(new OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        accNo = list.get(currentA).getBs().get(currentB).getCs().get(i).getId();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                builder2.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder2.setPositiveButton("保存数据", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clickButtonSave(loginView);
                        adapter.setList(getListinfo());
                        adapter.notifyDataSetChanged();
                    }
                });
                builder2.setView(loginView);
                builder2.show();
                break;
            case R.id.analyzeButton:
                Intent intent=new Intent();
                intent.setClass(this,AnalyzeActivity.class);
                startActivity(intent);
                break;
        }
    }
    private List<Map<String,Object>> getListinfo(){
        MySQLiteOpenHelper mySQLiteOpenHelper=new MySQLiteOpenHelper(this);
        return mySQLiteOpenHelper.selectList("select _id,acctypename,acctype,accmoney,accdate from tb_saved order by accdate desc",null);
    }
}
