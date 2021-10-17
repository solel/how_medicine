package com.example.how_medicine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewItem extends BaseAdapter implements ListAdapter {

    //private ImageView iconImageView;
    private TextView titleTextView;
    private TextView text3TextView;

    // Adapter -> ListViewItem.java
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem2> listViewItemList = new ArrayList<ListViewItem2>();

    public ListViewItem() {
        //생성자
    }


    //adapter에 사용되는 데이터의 개수 리턴
    //@Override
    public int getCount(){
        return listViewItemList.size();
    }

    //position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 converView 참조 획득
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_list_view_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        titleTextView = (TextView) convertView.findViewById(R.id.title);
        //iconImageView = (ImageView) converView.findViewById(R.id.icon);
        text3TextView = (TextView) convertView.findViewById(R.id.text3);

        ListViewItem2 listViewItem2 = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem2.getTitle());
        //iconImageView.setImageResource(listViewItem2.getIcon());
        text3TextView.setText(listViewItem2.getText3());

        //클릭이벤트 구현
        LinearLayout cmdArea = (LinearLayout) convertView.findViewById(R.id.cmdArea);
        cmdArea.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v){
            // 해당 리스트 클릭시 이벤트
            Toast.makeText(v.getContext(), listViewItemList.get(pos).getText3(), Toast.LENGTH_SHORT).show();
        }
    });
        return convertView;
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴
    //@Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    //@Override
    public Object getItem(int position){
        return listViewItemList.get(position);
    }

    // 아이템 데이터 추가를 위한 함수
    public void addItem(String title, String text3){
        ListViewItem2 item = new ListViewItem2();

        item.setTitleStr(title);
        //item.setIcon(icon);
        item.setText3Str(text3);

        listViewItemList.add(item);
    }

}