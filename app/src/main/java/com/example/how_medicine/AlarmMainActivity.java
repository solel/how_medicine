package com.example.how_medicine;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AlarmMainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE1 = 1000;
    public static final int REQUEST_CODE2 = 1001;

    private AdapterActivity arrayAdapter;

    private Button tpBtn, removeBtn;
    private TextView textView;
    private int hour, minute, codeNum;
    private String name, day, am_pm;
    private Handler handler;
    private SimpleDateFormat mFormat;
    private int adapterPosition;

    private Time thisTime;

    RecyclerView tRecyclerView = null;
    AdapterActivity tRecyclerAdapter = null;
    ArrayList<Time> timeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_main);

        tRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        tRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tRecyclerAdapter = new AdapterActivity();
        tRecyclerView.setAdapter(tRecyclerAdapter);

        tpBtn = (Button) findViewById(R.id.addBtn);
        tpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tpIntent = new Intent(AlarmMainActivity.this, TimePickerActivity.class);
                //tpIntent.putExtra("mediName","");
                tpIntent.putExtra("code",REQUEST_CODE1);
                startActivityForResult(tpIntent, REQUEST_CODE1);
            }
        });

        tRecyclerAdapter.setItemClickListener(new OnTimeItemClickListener() {
            @Override
            public void OnItemClick(AdapterActivity.ViewHolder holder, View v, int pos) {
                Time item = tRecyclerAdapter.getItem(pos);
                //int position = tRecyclerView.getChildAdapterPosition(v);
                Intent intent_time = new Intent(AlarmMainActivity.this, TimePickerActivity.class);
                intent_time.putExtra("name", item.getName());
                intent_time.putExtra("code", REQUEST_CODE2);
                intent_time.putExtra("position", item.getCode());

                tRecyclerAdapter.removeItem(pos);
                startActivityForResult(intent_time, REQUEST_CODE2);
            }
        });
    }
    // TimePicker 셋팅값 받아온 결과를 arrayAdapter에 추가
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //시간 리스트 추가
        if(requestCode == REQUEST_CODE1 && resultCode == RESULT_OK && data != null) {
            hour = data.getIntExtra("hour", 1);
            minute = data.getIntExtra("minute", 2);
            am_pm = data.getStringExtra("am_pm");
            name = data.getStringExtra("name");
            day = data.getStringExtra("day");
            codeNum = data.getIntExtra("codeNum",3);    //~!~!~!~!~!~

            timeList.add(new Time(hour, minute, codeNum, name, day, am_pm));
            Log.d("Tag","timeList_add: "+timeList);
            tRecyclerAdapter.setTimeList(timeList);
            //arrayAdapter.notifyDataSetChanged();
        }
        //시간 리스트 터치 시 변경된 시간값 저장
        if(requestCode == REQUEST_CODE2 && resultCode == RESULT_OK && data != null) {
            hour = data.getIntExtra("hour", 1);
            minute = data.getIntExtra("minute", 2);
            am_pm = data.getStringExtra("am_pm");
            name = data.getStringExtra("name");
            day = data.getStringExtra("day");
            codeNum = data.getIntExtra("codeNum",3);    //~!~!~!~!~!~

            timeList.add(new Time(hour, minute, codeNum, name, day, am_pm));
            Log.d("Tag","timeList_list: "+timeList);
            tRecyclerAdapter.setTimeList(timeList);
            //arrayAdapter.notifyDataSetChanged();
        }
    }
}