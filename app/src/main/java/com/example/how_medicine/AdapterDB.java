package com.example.how_medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AdapterDB extends RecyclerView.Adapter<AdapterDB.ViewHolder> {

    private List<Medicine> medicineList;
    private MedicineItem item;

    private static MedicineDB medicineDB = null;
    private static Context mContext;

    /*public AdapterDB(List<Medicine> list){
        this.medicineList = list;
        notifyDataSetChanged();
    }*/

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴
    @NonNull
    public AdapterDB.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_recycler, parent, false);
        AdapterDB.ViewHolder vh = new AdapterDB.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //holder.onBind(medicineList.get(position));
        Medicine item = medicineList.get(position);

        holder.name.setText(item.getName());
        holder.company.setText(item.getCompany());
        holder.efficacy.setText(item.getEfficacy());
        holder.use.setText(item.getUse());
        holder.know1.setText(item.getKnow1());
        holder.know2.setText(item.getKnow2());
        holder.knowMedi.setText(item.getKnowMedi());
        holder.sideEffect.setText(item.getSideEffect());
        holder.keep.setText(item.getKeep());
    }

    // 생성자에서 데이터 리스트 객체를 전달받음
    public void setMedicineList(List<Medicine> list) {
        this.medicineList = list;
        notifyDataSetChanged();
    }

    // getItemCount() - 전체 데이터 갯수 리턴
    @Override
    public int getItemCount() {
        return null != medicineList ? medicineList.size() : 0;
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

        private MedicineItem item;
        private int position;

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


            //아이템 클릭 이벤트 처리
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        // 뷰홀더가 참조하는 아이템이 어댑터에서 삭제되면 getAdapterPosition() 메서드는 NO_POSITION 리턴

                        // 데이터 리스트로부터 아이템 데이터 참조
                        //MedicineItem item = mMedicineList.get(pos);
                        LinearLayout textContents = (LinearLayout) itemView.findViewById(R.id.medicine_contents);
                        if (textContents.getVisibility() == View.VISIBLE) {
                            textContents.setVisibility(View.GONE);
                        } else if (textContents.getVisibility() == View.GONE) {
                            textContents.setVisibility(View.VISIBLE);
                            if(saveBtn.getVisibility() == View.VISIBLE){
                                saveBtn.setVisibility(View.GONE);
                            }
                        }

                        // 3.아이템 클릭 이벤트 핸들러 메서드에서 리스너 객체 메서드(onItemClick) 호출
                        if (mListener != null) {
                            mListener.onItemClick(v, pos);
                        }
                    }
                }
            });
        }

//        void onBind(Medicine item) {
//            name.setText(item.name);
//            company.setText(item.company);
//
//            efficacy.setText(item.efficacy);
//            use.setText(item.use);
//            know1.setText(item.know1);
//            know2.setText(item.know2);
//            knowMedi.setText(item.knowMedi);
//            sideEffect.setText(item.sideEffect);
//            keep.setText(item.keep);
//        }

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