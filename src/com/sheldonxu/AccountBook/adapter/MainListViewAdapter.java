package com.sheldonxu.AccountBook.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.sheldonxu.AccountBook.R;

import java.util.List;
import java.util.Map;

/**
 * Created by sheldon.Xu on 15-4-10.
 */
public class MainListViewAdapter extends BaseAdapter {
    private List<Map<String,Object>> list;
    private Context context;

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public MainListViewAdapter(Context context, List<Map<String, Object>> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.parseLong(""+list.get(i).get("_id"));
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            holder=new Holder();
            holder.accdate= (TextView) view.findViewById(R.id.accdate);
            holder.accmoney= (TextView) view.findViewById(R.id.accmoney);
            holder.acctypename= (TextView) view.findViewById(R.id.acctypename);
            view.setTag(holder);
        }else{
            holder= (Holder) view.getTag();
        }
        holder.accdate.setText(""+(list.get(i).get("accdate")));
        holder.accmoney.setText(""+(list.get(i).get("accmoney")));
        holder.acctypename.setText(""+(list.get(i).get("acctypename")));
        if((""+(list.get(i).get("acctype"))).length()==4){
            view.setBackgroundColor(0xffff0000);
        }else{
            view.setBackgroundColor(0xff00ff00);
        }
        return view;
    }
    class Holder{
        TextView acctypename;
        TextView accmoney;
        TextView accdate;
    }
}
