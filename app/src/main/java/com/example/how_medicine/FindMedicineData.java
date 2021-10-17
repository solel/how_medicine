package com.example.how_medicine;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.os.Bundle;
        import android.text.method.ScrollingMovementMethod;
        import android.util.Log;
        import android.webkit.WebView;
        import android.widget.ListView;
        import android.widget.ScrollView;
        import android.widget.TextView;

        import org.w3c.dom.Text;
        import org.xmlpull.v1.XmlPullParser;
        import org.xmlpull.v1.XmlPullParserException;
        import org.xmlpull.v1.XmlPullParserFactory;

        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.List;
        import java.util.StringTokenizer;

public class FindMedicineData extends AppCompatActivity {

    final static String TAG = "HealthProject";

    //TextView medicine_text;
    String medicine_data;
    private ListView listview;
    private ListViewItem adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_medicine_data);

        //medicine_text = (TextView)findViewById(R.id.medicine_data_text);
        //medicine_text.setMovementMethod(new ScrollingMovementMethod());

        adapter = new ListViewItem();   //adapter 생성
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

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
                        while(st.hasMoreTokens()) {
                            String tokenName = st.nextToken();
                            String tokenText = st.nextToken();
                            for(int i=0; i<7; i++){
                                st.nextToken();
                            }
                            Log.d("tokenName: ",tokenName);
                            Log.d("tokenText: ",tokenText);
                            adapter.addItem(tokenName, tokenText);
                            adapter.notifyDataSetChanged();
                        }

                    }
                });
            }
        }).start();
    }

    // xml parsing part
    String getMedicineXmlData(){
        StringBuffer buffer = new StringBuffer();

        Intent intent2 = getIntent();

        //Bundle bundle = intent2.getExtras();
        //String symtom = bundle.getString("symtom");
        //String taking = bundle.getString("taking");
        //String desease = bundle.getString("desease");

        String symtom = intent2.getStringExtra("symtom");
        String taking = intent2.getStringExtra("taking");
        String desease = intent2.getStringExtra("desease");

        /*TextView ex_text;
        ex_text = (TextView) findViewById(R.id.ex1);
        ex_text.setText(taking);*/

        String numOfRows = "4";
        String serviceKey = "Xr1vjtjg%2FE5kyZjOZieNDg%2FcbJbEdg6XVqp1KoAZAThKmfRUYg%2BZDyPmQ7l5OJTZnpiqVglcHvdTLBsyrXhvXA%3D%3D";

        String queryUrl = "http://apis.data.go.kr/1471000/DrbEasyDrugInfoService/getDrbEasyDrugList?efcyQesitm="+symtom+"&numOfRows=10"+"&ServiceKey="+serviceKey;
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
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("efcyQesitm")) {
                            buffer.append("효능 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("useMethodQesitm")) {
                            buffer.append("사용법 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("atpnWarnQesitm")) {
                            buffer.append("주의사항경고 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("atpnQesitm")) {
                            buffer.append("주의사항 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("intrcQesitm")) {
                            buffer.append("주의해야할 약, 음식 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("seQesitm")) {
                            buffer.append("부작용 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
                            buffer.append("\n\n");
                        }
                        else if(tag.equals("depositMethodQesitm")) {
                            buffer.append("보관법 : ");
                            xpp.next();
                            buffer.append(xpp.getText());
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