package com.example.how_medicine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.facebook.stetho.Stetho;

import java.lang.reflect.Field;
import java.util.List;

public class MedicineLogActivity extends AppCompatActivity {

    private List<Medicine> medicineList;
    private MedicineDB medicineDB = null;
    private Context mContext = null;
    private AdapterDB adapterDB;
    private Button backMainBtn;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_log);

        backMainBtn = (Button) findViewById(R.id.return_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_mediLog);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mContext = getApplicationContext();

        // DB 생성
        medicineDB = MedicineDB.getInstance(this);

        adapterDB = new AdapterDB();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        // main thread에서 DB 접근 불가 -> data 읽고 쓸 대 thread 사용하기
        class InsertRunnable implements Runnable {

            @Override
            public void run() {
                try {
                    medicineList = MedicineDB.getInstance(mContext).medicineDao().getAll();

                    adapterDB.setMedicineList(medicineList);
                    mRecyclerView.setAdapter(adapterDB);

                    adapterDB.notifyDataSetChanged();

                    Stetho.initializeWithDefaults(mContext);
                }
                catch (Exception e) {

                }
            }
        }
        InsertRunnable insertRunnable = new InsertRunnable();
        Thread t = new Thread(insertRunnable);
        t.start();

        backMainBtn.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}