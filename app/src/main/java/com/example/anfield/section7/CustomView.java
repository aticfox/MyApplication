package com.example.anfield.section7;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by ANFIELD on 4/5/2560.
 */

public class CustomView extends View{

    private boolean isBlue = false;
    private boolean isDown = false;
    private GestureDetector gestureDetector;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initWithAttrs(attrs,0,0);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initWithAttrs(attrs,defStyleAttr,0);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)// หรือจะใส่เป็นตัวเลขก็ได้
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initWithAttrs(attrs,defStyleAttr,defStyleRes);
    }

    private void init() {
            gestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    //ให้เราตัดสินใจว่าจะสนใจ event ที่ถูกโยนเข้ามาไหม
                    //ไม่ให้แม่ขโมยอีเว้นลูก
                    return true;//เมื่อ retuen false คำสั่งอื่นจะไม่ถุกเรียกเลย แต่ true จะถูกเรียก ( ไว้ดูว่าเราสนใจ event ชุดนั้นรึเปล่า)
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    //ถ้า onDown return true มันจะมา onShowPress ต่อ (เอาไว้ทำงานไม่ต้องคลิดลอจิก ...)
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //กดค้างไว้คือจะอยู่ที่ลูก
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    //ตอนที่จิ้มและปล่อย เทียบได้กับ Action up
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    //คือการปัด ---> พิกัดเริ่มต้น, พิกัดสุดท้าย, ค.เร็ว แกน x, ค.เร็ว แกน y
                    isBlue = !isBlue;
                    invalidate();
                    return true;
                }
            });

        setClickable(true);//Enable Click Mode ต่อยอดเอง

        setOnClickListener(new OnClickListener() {//เรียกตัวนี้ ถ้ามันยังไม่ clickable มัน จะ clickable ต่อยอดเอง
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes){
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomView,
                defStyleAttr,defStyleRes
        );

        try {
            isBlue = a.getBoolean(R.styleable.CustomView_isBlue, false);
                //is blue เปลี่ยนค่าตาม xmal
        }finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //เหมือนเปนที่ว่างๆใสๆ และอยากใส่ไรก็ใส่ในฟังก์ชันเลย อยากวาดไรวาด
        super.onDraw(canvas);
        Paint p = new Paint();
        if(isBlue)
            p.setColor(0xFF0000FF);//Blue

        else
            p.setColor(0xFFFF0000);//Red

        //วาดเส้น (start x, start y, จุดสิเนสุดแกน x,จุดสิเนสุดแกน y)
        canvas.drawLine(0,0,getMeasuredWidth(),getMeasuredHeight(),p);

        if(isDown){
            p.setColor(0xFF00FF00);//สีเขียว
            //แปลง dp เป็น px
            float px = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    5,
                    getContext().getResources().getDisplayMetrics()
            );
            p.setStrokeWidth(px);
            //สลับแกน
            canvas.drawLine(0,getMeasuredHeight(),getMeasuredWidth(),0,p);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //โยน event ลง GestureDetector
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isDown = true;
                invalidate();
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                isDown = false;
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
                isDown = false;
                invalidate();
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        //parcelable เก็บ state ของ view ต่างๆ
        Parcelable superState = super.onSaveInstanceState();//เก็บไว้เป็นตัวแปนตัวแปรนึง
        BundleSavedState savedState = new BundleSavedState(superState);
        Bundle bundle = new Bundle();
        bundle.putBoolean("isBlue",isBlue);
        savedState.setBundle(bundle);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState savedState = (BundleSavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        Bundle bundle = savedState.getBundle();
        isBlue = bundle.getBoolean("isblue");
    }
}
