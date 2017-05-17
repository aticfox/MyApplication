package com.example.anfield.section7;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {


    int sum = 0;
    TextView tvResult;
    Button btnFinish;
    private EditText edText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        //Bundle
        Intent intent = getIntent();
        sum = intent.getIntExtra("result",0);
        Bundle bundle = intent.getBundleExtra("cBundle");
        int x = bundle.getInt("x");
        int y = bundle.getInt("y");
        int z = bundle.getInt("z");

        //Serialization
        CoordinateSerializable c2 = (CoordinateSerializable)intent.getSerializableExtra("cSerializable");

        CoordinateParcelable c3 = intent.getParcelableExtra("cParcelable");

        initInstances();
    }

    private void initInstances() {
        tvResult = (TextView) findViewById(R.id.tvResult);
        tvResult.setText("Result = " + sum);

        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnFinish.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                edText = (EditText) findViewById(R.id.edittextInSec);
                String txReturn = edText.getText().toString();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",txReturn);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}
