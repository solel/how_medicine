package com.example.how_medicine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterActivity extends RecyclerView.Adapter<AdapterActivity.ViewHolder> implements OnTimeItemClickListener {
    private ArrayList<Time> timeList;

    public static final int REQUEST_CODE2 = 1001;

    private int tCheckedPosition;

    OnTimeItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Context context = parent.getContext();
        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());


        View view = inflater.inflate(R.layout.round_theme, parent, false);
        ViewHolder vh = new ViewHolder(view,this);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Time item = timeList.get(position);
        holder.onBind(item);
    }

    @Override
    public int getItemCount() {
        return null != timeList ? timeList.size() : 0;
    }

    public void setTimeList(ArrayList<Time> list) {
        this.timeList = list;
        notifyDataSetChanged();
    }

    public Time getItem(int position){
        return timeList.get(position);
    }

    /*@Override
    public int getItemCount() {
        return null != timeList ? timeList.size() : 0;
    }

    public interface OnItemClickEventListener {
        void onItemClick(int a_position);
    }

    private OnItemClickEventListener tItemClickListener = new OnItemClickEventListener(){

        @Override
        public void onItemClick(int a_position) {
            notifyItemChanged(tCheckedPosition, null);
            tCheckedPosition = a_position;
            notifyItemChanged(a_position, null);
        }
    };*/

    /*public interface OnItemClickListener
    {
        p
    }*/

    // 2.리스너 객체를 전달하는 메서드(setOnItemclickListener())와 전달된 객체(mListener)를 저장할 변수 추가
    //OnItemClickListener tListener = null;   // 리스너 객체 참조를 저장하는 변수

    @Override
    public void OnItemClick(ViewHolder holder, View view, int position){
        if(listener != null){
            listener.OnItemClick(holder, view, position);
        }
    }
    public void setItemClickListener(OnTimeItemClickListener listener) {   // OnItemclickListener 리스너 객체 참조를 어댑터에 전달하는메서드
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView hourText;
        TextView minuteText;
        TextView am_pm;
        TextView medicineText;
        TextView day;

        private Time item;
        private int position;

        LinearLayout textContents;

        public ViewHolder(@NonNull View itemView, OnTimeItemClickListener listener) {
            super(itemView);

            hourText = (TextView)itemView.findViewById(R.id.textTime1);
            minuteText = (TextView)itemView.findViewById(R.id.textTime2);
            am_pm = (TextView)itemView.findViewById(R.id.am_pm);
            medicineText = (TextView)itemView.findViewById(R.id.medicine_name);
            day = (TextView)itemView.findViewById(R.id.time_day);

            //아이템 클릭 이벤트 처리
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {

                        listener.OnItemClick(ViewHolder.this,v,pos);
                        // 뷰홀더가 참조하는 아이템이 어댑터에서 삭제되면 getAdapterPosition() 메서드는 NO_POSITION 리턴

                        // 데이터 리스트로부터 아이템 데이터 참조
                        //MedicineItem item = mMedicineList.get(pos);
                        /*LinearLayout textContents = (LinearLayout) itemView.findViewById(R.id.medicine_contents);
                        if (textContents.getVisibility() == View.VISIBLE) {
                            textContents.setVisibility(View.GONE);
                        } else if (textContents.getVisibility() == View.GONE) {
                            textContents.setVisibility(View.VISIBLE);
                        }*/

                        //item.setName("aaa");
                        //item.setCompany("bbb");
                        //mMedicineList.set(pos, item);

                        // 3.아이템 클릭 이벤트 핸들러 메서드에서 리스너 객체 메서드(onItemClick) 호출

                        /*if (tListener != null) {
                            tListener.onItemClick(v, pos);
                        }
                        Time item = timeList.get(pos);
                        Intent intent_time = new Intent(this, TimePickerActivity.class);
                        intent_time.putExtra("mediName",item.getName());
                        intent_time.putExtra("code",REQUEST_CODE2);

                        arrayAdapter.removeItem(position);
                        //intent.putExtra("hour", thisTime.getHour());
                        //intent.putExtra("minute",thisTime.getMinute());
                        //Log.d("thisTime: ","위: "+ position);
                        //Intent intentList = getIntent();
                        //intentList.getIntExtra("position", position);
                        startActivityForResult(intent,REQUEST_CODE2);*/

                    }
                }
            });
        }

        void onBind(Time item) {
            String ex_hour = String.valueOf(item.getHour());
            String ex_minute= String.valueOf(item.getMinute());
            hourText.setText(ex_hour+" : ");
            minuteText.setText(ex_minute);
            medicineText.setText(item.getName());
            day.setText(item.getDay());
            am_pm.setText(item.getAm_pm());
        }

    }
    public void removeItem(int position){
        timeList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
}

/*
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterActivity extends BaseAdapter {
    public ArrayList<Time> listviewitem = new ArrayList<Time>();
    private ArrayList<Time> arrayList = listviewitem;   //백업 arrayList

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Time getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.round_theme, parent, false);

            TextView hourText = (TextView)convertView.findViewById(R.id.textTime1);
            TextView minuteText = (TextView)convertView.findViewById(R.id.textTime2);
            TextView am_pm = (TextView)convertView.findViewById(R.id.am_pm);
            TextView medicineText = (TextView)convertView.findViewById(R.id.medicine_name);
            TextView day = (TextView)convertView.findViewById(R.id.time_day);

            holder.hourText = hourText;
            holder.minuteText = minuteText;
            holder.am_pm = am_pm;
            holder.name = medicineText;
            holder.day = day;

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        Time time = arrayList.get(position);
        holder.am_pm.setText(time.getAm_pm());
        holder.hourText.setText(time.getHour()+ "시");
        holder.minuteText.setText(time.getMinute()+ "분");
        holder.name.setText(time.getName());
        holder.day.setText(time.getDay()+ "일");

        return convertView;
    }


    public void addItem(int hour, int minute, String am_pm, String name, String day, int code) {
        Time time = new Time();

        time.setHour(hour);
        time.setMinute(minute);
        time.setAm_pm(am_pm);
        time.setName(name);
        time.setDay(day);
        time.setCode(code);

        listviewitem.add(time);
    }
    //List 삭제 method
    public void removeItem(int position) {
        if(listviewitem.size() < 1) {

        }
        else {
            listviewitem.remove(position);
        }
    }

    public void removeItem() {
        if(listviewitem.size() < 1) {

        }
        else {
            listviewitem.remove(listviewitem.size()-1);
        }
    }

    static class ViewHolder {
        TextView hourText, minuteText, am_pm, name, day, code;
    }
}*/
