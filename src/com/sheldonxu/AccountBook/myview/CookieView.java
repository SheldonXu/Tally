package com.sheldonxu.AccountBook.myview;

import android.app.Notification;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sheldon.Xu on 15-4-10.
 */
public class CookieView extends View {

    private final int[] color = new int[]{0xffff0000, 0xffff8800, 0xffffff00, 0xff88ff00, 0xff00ff00, 0xff00ffff, 0xff0000ff, 0xffff00ff};
    private List<String> list;
    private List<String> listname;


    private List<String> listh;

    public void setList(List<String> list,List<String> listname) {
        this.list = list;
        this.listname = listname;
        double sum=sumList(list,list.size());
        for (int i = 0; i < list.size()-1; i++) {
            String str=""+Double.parseDouble(list.get(i))/sum;
            str=str.substring(2,4)+"."+str.substring(4,6);
            listh.add(str);
        }
        listh.add(""+(100-sumList(listh,listh.size())));
        this.invalidate();
    }

    public CookieView(Context context) {
        this(context, null);
    }

    public CookieView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CookieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paintbl = new TextPaint();
        paintbl.setColor(Color.WHITE);
        paintbl.setTextSize(40);

        Paint.FontMetrics a=paintbl.getFontMetrics();
        textFoot=a.descent;
        rectF = new RectF();
        List list1 = new ArrayList<String>();
        listname = new ArrayList<String>();
        listh=new ArrayList<String>();
        list1.add(123.5 + "");
        list1.add(200.5 + "");
        list1.add(70.5 + "");
        list1.add(300.5 + "");
        listname.add("工资1");
        listname.add("工资2");
        listname.add("工资3");
        listname.add("工资4");
        setList(list1,listname);
    }

    private double sumList(List<String> list,int j) {
        double sum = 0;
        for (int i = 0; i < j; i++) {
            sum += Double.parseDouble(list.get(i));
        }
        return sum;
    }
    private float textFoot;
    private Paint paint;
    private TextPaint paintbl;
    private RectF rectF;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffffff);
        float h = this.getHeight()-100;
        rectF.set(0, 0, h, h);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        double sum = sumList(list,list.size());
        for (int i = 0; i < list.size(); i++) {
            paint.setColor(color[i]);
            canvas.drawArc(rectF, (float) ((sumList(list,i) / sum) * 360), (float) (Double.parseDouble(list.get(i)) / sum * 360), true, paint);
            canvas.drawRect(h + 20, i * 50 + 20, h + 60, i * 50 + 60, paint);
            canvas.drawText(listname.get(i)+":"+listh.get(i)+"%", h+70, i * 50 + 60-textFoot, paintbl);
        }


    }
}
