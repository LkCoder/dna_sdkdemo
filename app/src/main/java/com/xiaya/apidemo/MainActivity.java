package com.xiaya.apidemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import net.luculent.dnalib.card.CardHelper;
import net.luculent.dnalib.card.MifareCard;
import net.luculent.dnalib.serial.ICmd;
import net.luculent.dnalib.serial.SerialHelper;

public class MainActivity extends AppCompatActivity implements SerialHelper.OnDataReceivedListener {

    private SerialHelper mSerialHelper;
    private CardHelper mCardHelper;
    private TextView resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        initView();
    }

    private void init() {
        mSerialHelper = SerialHelper.getInstance();
        mSerialHelper.setReadThreadListener(this);
        mCardHelper = CardHelper.withCard(new MifareCard());//A卡
        mCardHelper.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSerialHelper.close();
        mCardHelper.release();
    }

    private void initView() {
        resultTxt = findViewById(R.id.measure_result);
        findViewById(R.id.read_A_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = mCardHelper.read();
                resultTxt.setText(result);
            }
        });
        findViewById(R.id.measure_temp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCmd(ICmd.CmdTyp.TEMPERATURE);
            }
        });
        findViewById(R.id.measure_acc).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCmd(ICmd.CmdTyp.ACCELERATE);
            }
        });
        findViewById(R.id.measure_speed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCmd(ICmd.CmdTyp.SPEED);
            }
        });
        findViewById(R.id.measure_shift).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCmd(ICmd.CmdTyp.SHIFT);
            }
        });
    }

    private void sendCmd(ICmd.CmdTyp cmdTyp) {
        if (mSerialHelper.open()) {
            mSerialHelper.sendCmd(cmdTyp);
        } else {
            Toast.makeText(this, "打开设备失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDataReceived(String result) {
        resultTxt.setText(result);
    }
}
