package com.example.anfield.section7;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    int x, y, z;//ทดสอบการเก็บไว้ใน activity || save/restore in Activity's instance state

    TextView tvHello;
    EditText editTextHello;
    Button btnCopy;
    EditText editText1;
    EditText editText2;
    TextView tvResult;
    Button btnCalculate;
    CustomViewGroup viewGroup1;
    CustomViewGroup viewGroup2;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

//        Toast.makeText(MainActivity.this,
//                "Width = " + width + ", Height = " + height,
//                Toast.LENGTH_SHORT)
//                .show();
    }

    private void initInstances() {
        tvHello = (TextView)findViewById(R.id.tvHello);
        tvHello.setMovementMethod(LinkMovementMethod.getInstance());
        tvHello.setText(Html.fromHtml("<b>He<u>ll</u>o</b> <i>World</i> <font color = \"#00fa00\">La la la</font> <a href=\"http://nuunneoi.com\">nuuneoi</a>"));

        editTextHello = (EditText)findViewById(R.id.editTextHello);
        editTextHello.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    tvHello.setText(editTextHello.getText());
                    return true;
                }
                return false;
            }
        });

        btnCopy = (Button)findViewById(R.id.btnCopy);
        btnCopy.setOnClickListener(this);

        /////////////////////////////
        //START
        //////////////////////////////

        editText1 = (EditText)findViewById(R.id.editText1);
        editText2 = (EditText)findViewById(R.id.editText2);
        tvResult = (TextView)findViewById(R.id.tvResult);

        btnCalculate = (Button)findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(this); // this คือ activity นี้

        viewGroup1 = (CustomViewGroup)findViewById(R.id.viewGroup1);
        viewGroup2 = (CustomViewGroup)findViewById(R.id.viewGroup2);

        viewGroup1.setButtonText("Hello");
        viewGroup2.setButtonText("World");
    }

    @Override
    public void onClick(View v) {

        if(v == btnCopy){
            tvHello.setText(editTextHello.getText());

        }

        else if(v == btnCalculate){
            int value1 = 0;
            int value2 = 0;
            int sum;


            try {
                value1 = Integer.parseInt(editText1.getText().toString());//จำเป็นต้องให้เป็น string
            }
            catch (NumberFormatException e){
            }

            try {
                value2 = Integer.parseInt(editText2.getText().toString());
            }
            catch (NumberFormatException e){
            }

            sum = value1 + value2;
            tvResult.setText(Integer.toString(sum));// + "" จะได้ไม่เด่งออกเพราะว่า sum เป็น int ต้องทำให้เป็น string ก่อน


            Toast.makeText(MainActivity.this,"Result = " + sum ,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);//เปลี่ยนหน้า แบบ 1

            intent.putExtra("result",sum);

            //O A tot A แบบ Bundle
            Coordinate c1 = new Coordinate();
            c1.x = 5;
            c1.y = 10;
            c1.z = 20;
            Bundle bundle = new Bundle();
            bundle.putInt("x", c1.x);
            bundle.putInt("y", c1.y);
            bundle.putInt("z", c1.z);
            intent.putExtra("cBundle",bundle);

            //Serializable
            CoordinateSerializable c2 = new CoordinateSerializable();
            c2.x = 5;
            c2.y = 10;
            c2.z = 20;
            intent.putExtra("cSerializable",c2);

            CoordinateParcelable c3 = new CoordinateParcelable();
            c3.x = 5;
            c3.y = 10;
            c3.z = 20;
            intent.putExtra("cParcelable",c3);

            //startActivity(intent);
            startActivityForResult(intent, 123);//Start activity พร้อมรอผลกลับ

            //startActivity(new Intent(MainActivity.this, SecondActivity.class));//เปลี่ยนหน้า แบบ 2

        }

    }

    /*private void startActivities(Intent intent) {
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Check if it is a result from SecondActivity
        if(requestCode == 123){
            //ยิงไปหลายหน้า เวลากลับมาก็กลับมาที่เดียว ดังนั้นเพื่อให้รู้ว่าไปหน้าไหนดังนั้นจึงต้องมี request code
            if(resultCode == RESULT_OK){
                //ถ้า cancel ไม่ทำไรแต่ ok ทำต่อ
                //get data from data's extra
                String returndata = data.getStringExtra("result");
                Toast.makeText(MainActivity.this,"Result = " + returndata ,Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_setting){
            return  true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }


    //Activity Life Cycle

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Bunale เก็บ state ของ activity
        super.onSaveInstanceState(outState);//เซฟทุกอย่างที่อยากให้แสดงตอนฟื้นคืนชีพ

    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);//โยนพินัยกรรมเข้ามาและเอาออกมาแสดง

    }
}
