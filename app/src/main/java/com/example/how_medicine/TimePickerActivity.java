package com.example.how_medicine;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimePickerActivity extends AppCompatActivity {

    public static final int REQUEST_CODE2 = 1001;
    private int code;

    private Button okButton, cancelButton;
    private Button cancel;
    private TimePicker timePicker;
    private EditText editName;

    private int hour, minute;
    private  String am_pm;
    private Date currentTime;
    private String stName, stDay;

    private int number=0;

    private int position;

    @RequiresApi(api = Build.VERSION_CODES.M)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picker);

        timePicker=(TimePicker)findViewById(R.id.time_picker);
        okButton =(Button)findViewById(R.id.okBtn);
        editName = (EditText)findViewById(R.id.edit_name);

        Intent intentList = getIntent();
        intentList.getIntExtra("code",code);

        if(code==REQUEST_CODE2){
            stName = intentList.getStringExtra("mediName");
            editName.setText(stName);
            intentList.getIntExtra("position",position);

            AlarmManager alarmManager_cancel=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager_cancel != null) {
                Intent intent = new Intent(this, AlarmReceiver.class);
                intent.putExtra("code", number);
                intent.putExtra("name", stName);
                PendingIntent cancelIntent = PendingIntent.getBroadcast(TimePickerActivity.this, position, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                alarmManager_cancel.cancel(cancelIntent);
                Toast.makeText(TimePickerActivity.this, "알람이 취소되었습니다.", Toast.LENGTH_LONG).show();
            }
        }

        Calendar calendar = Calendar.getInstance();//=
        calendar.setTimeInMillis(System.currentTimeMillis());//=

        currentTime = Calendar.getInstance().getTime();//=
        SimpleDateFormat day = new SimpleDateFormat("dd", Locale.getDefault());//=
        SimpleDateFormat month = new SimpleDateFormat("MM", Locale.getDefault());//=

        //stName = month.format(currentTime);//=
        stDay = day.format(currentTime);//=

        okButton.setOnClickListener(v->{
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //intentList.getIntExtra("hour",hour);
                //intentList.getIntExtra("minute",minute);
                hour = timePicker.getHour();
                minute = timePicker.getMinute();
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);

                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }
            }
            else {
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();
            }

            am_pm = AM_PM(hour);
            hour = timeSet(hour);
            stName = editName.getText().toString();
            /*Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            int hour=timePicker.getHour();
            int minute=timePicker.getMinute();
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }*/

            AlarmManager alarmManager=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                number = calendar.YEAR + calendar.MONTH + calendar.DATE + calendar.HOUR_OF_DAY + calendar.MINUTE + calendar.SECOND;
                Intent intent_receiver = new Intent(this, AlarmReceiver.class);
                intent_receiver.putExtra("code",number);
                intent_receiver.putExtra("name",stName);

                PendingIntent alarmIntent = PendingIntent.getBroadcast(this, number, intent_receiver, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);

                Toast.makeText(TimePickerActivity.this,"알람이 저장되었습니다.",Toast.LENGTH_LONG).show();
                //number+=1;
            }

            Intent sendIntent = new Intent(TimePickerActivity.this, MainActivity.class);//=

            sendIntent.putExtra("hour", hour);//=
            sendIntent.putExtra("minute", minute);//=
            sendIntent.putExtra("am_pm", am_pm);//=
            sendIntent.putExtra("name", stName);//=
            sendIntent.putExtra("day", stDay);//=
            sendIntent.putExtra("codeNum",number);
            sendIntent.putExtra("position",position);

            setResult(RESULT_OK, sendIntent);//=

            finish();//=
        });

        /*cancel=(Button)findViewById(R.id.cancleBtn);
        cancel.setOnClickListener(V->{
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(System.currentTimeMillis());
            int hour=timePicker.getHour();
            int minute=timePicker.getMinute();
            calendar2.set(Calendar.HOUR_OF_DAY,hour);
            calendar2.set(Calendar.MINUTE,minute);

            if (calendar2.before(Calendar.getInstance())) {
                calendar2.add(Calendar.DATE, 1);
            }

            AlarmManager alarmManager=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                Intent intent = new Intent(this, AlarmReceiver.class);
                PendingIntent cancelIntent=PendingIntent.getBroadcast(TimePickerActivity.this, position, intent,PendingIntent.FLAG_MUTABLE);
                alarmManager.cancel(cancelIntent);

                Toast.makeText(TimePickerActivity.this,"알람이 취소되었습니다.",Toast.LENGTH_LONG).show();
            }
        });*/

    }

    // 24시 시간제 바꿔줌
    private int timeSet(int hour) {
        if(hour > 12) {
            hour-=12;
        }
        return hour;
    }
    // 오전, 오후 선택
    private String AM_PM(int hour) {
        if(hour >= 12) {
            am_pm = "오후";
        }
        else {
            am_pm = "오전";
        }
        return am_pm;
    }
}