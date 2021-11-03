package com.example.how_medicine;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PorterDuff;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.stetho.Stetho;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private static ArrayList<MedicineItem> mMedicineList;
    public MedicineItem item;

    static final String DB_NAME = "Medicine";
    static final String TABLE_NAME = "takeMedicine";

    private static MedicineDB medicineDB = null;
    private static Context mContext;

    private int position;

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    public MyRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_recycler, parent, false);
        MyRecyclerAdapter.ViewHolder vh = new MyRecyclerAdapter.ViewHolder(view);

        return vh;

        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        //return new ViewHolder(view);
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull MyRecyclerAdapter.ViewHolder holder, int position) {
        //String text = mMedicineList.get(position);
        //holder.name.setText(text);
        this.item = holder.onBind(mMedicineList.get(position));
    }

    // 생성자에서 데이터 리스트 객체를 전달받음
    public void setMedicineList(ArrayList<MedicineItem> list) {
        this.mMedicineList = list;
        notifyDataSetChanged();
    }

    // getItemCount() - 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return null != mMedicineList ? mMedicineList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView company;

        TextView efficacy;
        TextView use;
        TextView know1;
        TextView know2;
        TextView knowMedi;
        TextView sideEffect;
        TextView keep;

        Button saveBtn;

        LinearLayout textContents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.medicine_name);
            company = (TextView) itemView.findViewById(R.id.medicine_company);

            efficacy = (TextView) itemView.findViewById(R.id.medicine_contents_efficacy);
            use = (TextView) itemView.findViewById(R.id.medicine_contents_use);
            know1 = (TextView) itemView.findViewById(R.id.medicine_contents_know1);
            know2 = (TextView) itemView.findViewById(R.id.medicine_contents_know2);
            knowMedi = (TextView) itemView.findViewById(R.id.medicine_contents_knowMedi);
            sideEffect = (TextView) itemView.findViewById(R.id.medicine_contents_sideEffect);
            keep = (TextView) itemView.findViewById(R.id.medicine_contents_keep);

            textContents = (LinearLayout) itemView.findViewById(R.id.medicine_contents);

            saveBtn = (Button) itemView.findViewById(R.id.save_medicine);

            medicineDB = MedicineDB.getInstance(itemView.getContext());

            //아이템 클릭 이벤트 처리
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // 뷰홀더가 참조하는 아이템이 어댑터에서 삭제되면 getAdapterPosition() 메서드는 NO_POSITION 리턴

                        // 데이터 리스트로부터 아이템 데이터 참조
                        MedicineItem item = mMedicineList.get(pos);
                        LinearLayout textContents = (LinearLayout) itemView.findViewById(R.id.medicine_contents);
                        if (textContents.getVisibility() == View.VISIBLE) {
                            textContents.setVisibility(View.GONE);
                        } else if (textContents.getVisibility() == View.GONE) {
                            textContents.setVisibility(View.VISIBLE);
                            if(saveBtn.getVisibility() == View.GONE){
                                saveBtn.setVisibility(View.VISIBLE);
                            }
                        }

                        // db 저장
                       /* medicineDB = MedicineDB.getInstance(v.getContext());
                        mContext = v.getContext();
                        item = mMedicineList.get(pos);*/


                        Log.d("Tag","whddddddbbbbt!!!: "+medicineDB);

                        Stetho.initializeWithDefaults(v.getContext());

                        class InsertRunnable implements Runnable {

                            @Override
                            public void run() {
                                Medicine medicine = new Medicine();
                                medicine.setName(item.getName());
                                medicine.setCompany(item.getCompany());
                                medicine.setEfficacy(item.getEfficacy());
                                medicine.setUse(item.getUse());
                                medicine.setKnow1(item.getKnow1());
                                medicine.setKnow2(item.getKnow2());
                                medicine.setKnowMedi(item.getKnowMedi());
                                medicine.setEfficacy(item.getSideEffect());
                                medicine.setKeep(item.getKeep());
                                medicineDB.getInstance(v.getContext()).medicineDao().insertAll(medicine);
                                Log.d("Tag","wwwwwwwhhh!!!: "+medicine.getName());
                            }
                        }

                        saveBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                InsertRunnable insertRunnable = new InsertRunnable();
                                Thread addThread = new Thread(insertRunnable);
                                Toast toast = Toast.makeText(view.getContext(), "복용 약으로 저장되었습니다", Toast.LENGTH_SHORT);
                                addThread.start();
                                Log.d("Tag","whhhhhhhaaattt???: "+medicineDB);
                            }
                        });

                        //item.setName("aaa");
                        //item.setCompany("bbb");
                        //mMedicineList.set(pos, item);

                        // 3.아이템 클릭 이벤트 핸들러 메서드에서 리스너 객체 메서드(onItemClick) 호출
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }

        MedicineItem onBind(MedicineItem item) {
            name.setText(item.getName());
            company.setText(item.getCompany());

            efficacy.setText(item.getEfficacy());
            use.setText(item.getUse());
            know1.setText(item.getKnow1());
            know2.setText(item.getKnow2());
            knowMedi.setText(item.getKnowMedi());
            sideEffect.setText(item.getSideEffect());
            keep.setText(item.getKeep());
            return item;
        }

        // 1.커스텀 리스너 인터페이스 정의
        public interface OnItemClickListener {
            void onItemClick(View v, int pos);
        }

        // 2.리스너 객체를 전달하는 메서드(setOnItemclickListener())와 전달된 객체(mListener)를 저장할 변수 추가
        private OnItemClickListener mListener = null;   // 리스너 객체 참조를 저장하는 변수

        public void setOnItemClickListener(OnItemClickListener listener) {   // OnItemclickListener 리스너 객체 참조를 어댑터에 전달하는메서드
            this.mListener = listener;
        }

    }
}