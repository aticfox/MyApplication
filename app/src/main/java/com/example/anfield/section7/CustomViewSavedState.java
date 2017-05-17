package com.example.anfield.section7;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by ANFIELD on 17/5/2560.
 */

public class CustomViewSavedState extends View.BaseSavedState{

    private boolean blue;

    public boolean isBlue() {
        return blue;
    }

    public void setBlue(boolean blue) {
        this.blue = blue;
    }

    public CustomViewSavedState(Parcel source) {
        super(source);
        //เขียนตอนอ่านด้าย
        // Read back
        blue = source.readInt() == 1;//1=true,0=false
    }

    /*public CustomViewSavedState(Parcel source, ClassLoader loader) {
        super(source, loader);
    }*/

    public CustomViewSavedState(Parcelable superState) {
        super(superState);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        //ใส่ไรเพิ่มใส่ในนี้ * parcel ไม่สามารถใส่ boolean เข้าไปได้
        out.writeInt(blue ? 1 : 0);//blue เป็น true เขียนเป็น 1 false เขียนเป็น 0

    }

    public static final Creator CREATE = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new CustomViewSavedState(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new CustomViewSavedState[size];
        }
    };
}
