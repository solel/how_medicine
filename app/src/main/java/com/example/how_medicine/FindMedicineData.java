package com.example.how_medicine;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import android.content.Intent;
        import android.graphics.Color;
        import android.os.Bundle;
        import android.text.Html;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;

        import org.xmlpull.v1.XmlPullParser;
        import org.xmlpull.v1.XmlPullParserException;
        import org.xmlpull.v1.XmlPullParserFactory;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.StringTokenizer;


public class FindMedicineData extends AppCompatActivity {

    final static String TAG = "HealthProject";

    String medicine_data;
    String symtom;
    String taking;
    String desease;

    RecyclerView mRecyclerView = null;
    //RecyclerView mcRecyclerView = null;
    MyRecyclerAdapter mRecyclerAdapter = null;
    //MyRecyclerAdapter mcRecyclerAdapter = null;
    ArrayList<MedicineItem> mMlist = new ArrayList<>();

    ArrayList<MedicineItem> mCarefulList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_medicine_data);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_ok);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRecyclerAdapter = new MyRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);

        //mcRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_careful);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //mcRecyclerAdapter = new MyRecyclerAdapter();
        //mcRecyclerView.setAdapter(new LinearLayoutManager(this);

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,true));

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 아래 메소드 호출하여 XML data를 파싱해서 String 객체로 얻어오기
                // 약 데이터 API
                medicine_data = getMedicineXmlData();

                //lisetview.setOnItemClickListener(listener);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //medicine_text.setText(medicine_data);   // TextView에 문자열 data 출력
                        StringTokenizer st = new StringTokenizer(medicine_data,"\n\n");
                        mMlist = new ArrayList<>();
                        //mCarefulList = new ArrayList<>();
                        while(st.hasMoreTokens()) {
                            String tokenName = st.nextToken();
                            String tokenText = st.nextToken();
                            String tokenEfficacy = st.nextToken();
                            String tokenUse = st.nextToken();
                            String tokenKnow1 = st.nextToken();
                            String tokenKnow2 = st.nextToken();
                            String tokenKnowMedi = st.nextToken();
                            String tokenSideEffect = st.nextToken();
                            String tokenKeep = st.nextToken();

                            if(tokenKnow2.contains(desease)&&tokenKnowMedi.contains(taking)){
                                mCarefulList.add(new MedicineItem(tokenText,tokenName, tokenEfficacy, tokenUse, tokenKnow1, tokenKnow2, tokenKnowMedi, tokenSideEffect, tokenKeep));
                                continue;
                            }
                            else{
                                mMlist.add(new MedicineItem(tokenText,tokenName, tokenEfficacy, tokenUse, tokenKnow1, tokenKnow2, tokenKnowMedi, tokenSideEffect, tokenKeep));
                            }

                            /*for(int i=0; i<7; i++){
                                st.nextToken();
                            }*/

                            //mMlist.add(new MedicineItem(tokenEfficacy, tokenUse, tokenKnow1, tokenKnow2, tokenKnowMedi, tokenSideEffect, tokenKeep));
                            //Log.d("tokenKeep: ",tokenKeep);
                            //Log.d("tokenText: ",tokenText);
                            //Log.d("After Adapter--->", String.valueOf(mMlist));
                        }
                        mRecyclerAdapter.setMedicineList(mMlist);
                        //mcRecyclerAdapter.setMedicineList(mCarefulList);
                        // 4. 커스텀 이벤트 리스너 객체를 생성하여 어댑터에 전달
                        /*mRecyclerAdapter.setOnItemClickListener(new MyRecyclerAdapter.OnItemClickListener(){
                            @Override
                            public void onItemClick(View v, int position){
                                // TODO : 아이템 클릭 이벤트를 MainActivity에서 처리.

                                // FindMedicineData로 값을 전달, 이동
                                Intent intent3 = new Intent(FindMedicineData.this, ResultMedicineData.class);
                                intent2.putExtra("symtom", symtom);
                                intent2.putExtra("taking", taking);
                                intent2.putExtra("desease", desease);
                                startActivity(intent2);
                            }
                        });*/
                    }
                });
            }

        }).start();

        /*final Button button = findViewById(R.id.badM);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setBackgroundColor(Color.WHITE);
                // FindMedicineData로 값을 전달, 이동
                Intent intent3 = new Intent(FindMedicineData.this, NotFindMedicineData.class);
                intent3.putExtra("mCarefulList", mCarefulList);
                intent3.putExtra("mMlist", mMlist);

                startActivity(intent3);
            }
        });*/

        final Button button_good = findViewById(R.id.goodM);
        final Button button_bad = findViewById(R.id.badM);
        button_good.setBackgroundColor(Color.parseColor("#E4F9FF"));


        button_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mRecyclerAdapter = new MyRecyclerAdapter();
                //mRecyclerView.setAdapter(mRecyclerAdapter);
                mRecyclerAdapter.setMedicineList(mMlist);
                button_good.setBackgroundColor(Color.parseColor("#E4F9FF"));
                button_bad.setBackgroundColor(Color.parseColor("#12CAD6"));
            }
        });

        button_bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mRecyclerAdapter = new MyRecyclerAdapter();
                //mRecyclerView.setAdapter(mRecyclerAdapter);
                mRecyclerAdapter.setMedicineList(mCarefulList);
                button_bad.setBackgroundColor(Color.parseColor("#E4F9FF"));
                button_good.setBackgroundColor(Color.parseColor("#12CAD6"));
            }
        });

    }

    /*public String stripHtml(String html){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY).toString().replaceAll("\n", "").trim();
        } else {
            return Html.fromHtml(html).toString().replaceAll("\n", "").trim();
        }
    }*/

    // xml parsing part
    String getMedicineXmlData(){
        StringBuffer buffer = new StringBuffer();

        String st;

        Intent intent2 = getIntent();

        symtom = intent2.getStringExtra("symtom");
        taking = intent2.getStringExtra("taking");
        desease = intent2.getStringExtra("desease");

        String numOfRows = "10";
        String serviceKey = "Xr1vjtjg%2FE5kyZjOZieNDg%2FcbJbEdg6XVqp1KoAZAThKmfRUYg%2BZDyPmQ7l5OJTZnpiqVglcHvdTLBsyrXhvXA%3D%3D";

        String queryUrl = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?efcyQesitm="+symtom+"&numOfRows="+numOfRows+"&ServiceKey="+serviceKey;
        try {
            URL url = new URL(queryUrl);    // 문자열로 된 요청 url을 URL 객체로 생성
            InputStream is = url.openStream();  // url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));   //inputstream 으로부터 xml 입력받기

            String tag;
            xpp.next();
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){
                switch(eventType){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName(); //태그 이름 얻어오기

                        if(tag.equals("itemName")) {
                            buffer.append("제품 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("entpName")) {
                            buffer.append("업체명 : ");
                            xpp.next();
                            //Log.d("using method: ",xpp.getText());
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("efcyQesitm")) {
                            buffer.append("효능 : ");
                            xpp.next();
                            if(xpp.getText()==null){
                                buffer.append("없음");
                            }
                            else{
                                st=xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "  ");
                                buffer.append(st);
                                //Log.d("efficient: ",st);
                            }
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("useMethodQesitm")) {
                            buffer.append("사용법 : ");
                            xpp.next();
                            if(xpp.getText()==null){
                                //if(android.text.Html.escapeHtml(xpp.getText()).toString()==null){
                                buffer.append("없음");
                           }
                            else{
                                st=xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "  ");
                                buffer.append(st);
                                //Log.d("using method_문자: ",st);
                            }
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("atpnWarnQesitm")) {
                            buffer.append("주의사항경고 : ");
                            xpp.next();
                            if(xpp.getText()==null){
                                //if(android.text.Html.escapeHtml(xpp.getText()).toString()==null){
                                buffer.append("없음");
                                //Log.d("careful_널: ","ㅓㅘㅗㅓㅏㅗㅓㅏ");
                            }
                            else{
                                st=xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "  ");
                                buffer.append(st);
                                //Log.d("careful_문자: ",st);
                            }
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("atpnQesitm")) {
                            buffer.append("주의사항 : ");
                            xpp.next();
                            if(xpp.getText()==null){
                                //if(android.text.Html.escapeHtml(xpp.getText()).toString()==null){
                                buffer.append("없음");
                            }
                            else{
                                st=xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "  ");
                                buffer.append(st);
                                //Log.d("careful2: ",st);
                            }
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("intrcQesitm")) {
                            buffer.append("주의해야할 약, 음식 : ");
                            xpp.next();
                            if(xpp.getText()==null){
                                //if(android.text.Html.escapeHtml(xpp.getText()).toString()==null){
                                buffer.append("없음");
                            }
                            else{
                                st=xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "  ");
                                buffer.append(st);
                                //Log.d("food: ",st);
                            }
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("seQesitm")) {
                            buffer.append("부작용 : ");
                            xpp.next();
                            if(xpp.getText()==null){
                                //if(android.text.Html.escapeHtml(xpp.getText()).toString()==null){
                                buffer.append("없음");
                            }
                            else{
                                st=xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "  ");
                                buffer.append(st);
                                //Log.d("problem: ",st);
                            }
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("depositMethodQesitm")) {
                            buffer.append("보관법 : ");
                            xpp.next();
                            if(xpp.getText()==null){
                                //if(android.text.Html.escapeHtml(xpp.getText()).toString()==null){
                                buffer.append("없음");
                            }
                            else{
                                st=xpp.getText().replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "  ");
                                buffer.append(st);
                                //Log.d("problem: ",st);
                            }
                            buffer.append("\n\n");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName();
                        if(tag.equals("openDe")) buffer.append("\n");
                        break;
                }
                eventType= xpp.next();
                //버퍼내용 잘라서 listview 출력 후 버퍼비우기
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return buffer.toString();   //StringBuffer 문자열 객체 반

    }
}