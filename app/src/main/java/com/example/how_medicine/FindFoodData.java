package com.example.how_medicine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.how_medicine.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class FindFoodData extends AppCompatActivity {

    public int number = 0;
    private String URL;
    private Bundle bundle;
    private String food_url;
    private String food;
    private String wantFood;

    String encodeVal = "";
    String decodeVal = "";

    ArrayList<String> foods = new ArrayList<>();

    private String str;

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_food_data);

        editText = findViewById(R.id.editText);

        Intent intent2 = getIntent();

        String desease = intent2.getStringExtra("desease");
        editText.setText(desease);

        final Button button = findViewById(R.id.searchButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foods = new ArrayList<>();
                bundle = new Bundle();

                TextView textView = findViewById(R.id.textView);
                editText = findViewById(R.id.editText);

                wantFood = editText.getText().toString();

                try {
                    encodeVal = URLEncoder.encode(wantFood, "utf-8");
                    //Log.d("Tag","인코딩: "+encodeVal);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                /*try {
                    decodeVal = URLDecoder.decode(encodeVal, "utf-8");
                    Log.d("Tag","디코딩: "+decodeVal);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }*/

                Handler handler = new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(@NonNull Message msg) {
                        bundle = msg.getData();    //new Thread에서 작업한 결과물 받기
                        textView.setText("");
                        textView.setText(bundle.getString("food_info").replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "\n"));    //받아온 데이터 textView에 출력
                    }
                };

                new Thread() {
                    @Override
                    public void run(){
                        try {
                            URL="http://www.amc.seoul.kr/asan/healthinfo/mealtherapy/mealTherapyList.do?searchCondition=all&searchKeyword="+encodeVal;
                            Document doc_ex = Jsoup.connect(URL).get();	//URL 웹사이트에 있는 html 코드를 다 끌어오기
                            Elements contents_ex = doc_ex.select(".txtellipsis");	//끌어온 html에서 클래스네임이 "temperature_text" 인 값만 선택해서 빼오기
                            contents_ex = contents_ex.select("a");
                            number=0;
                            for(Element buff:contents_ex){
                                number+=1;
                                String foodBuff = buff.getElementsByAttribute("href").attr("href");
                                String save = foodBuff.substring(55) + " ";
                                if(number==5){
                                    foods.add(save);
                                }
                            }
                            for(String strBuff:foods){
                                food_url = strBuff+"\n";
                            }

                            foods = new ArrayList<>();
                            bundle = new Bundle();

                            URL= "http://www.amc.seoul.kr/asan/healthinfo/mealtherapy/mealTherapyDetail.do?mtId="+food_url;
                            Document doc = Jsoup.connect(URL).get();	//URL 웹사이트에 있는 html 코드를 다 끌어오기
                            Elements contents = doc.select(".descDl");	//끌어온 html에서 클래스네임이 "temperature_text" 인 값만 선택해서 빼오기
                            contents = contents.select("p");
                            number=0;
                            for(Element buff:contents){
                                number+=1;
                                //String[] foodBuff = buff.text().split(" ");
                                String save = buff + " ";
                                if(number<=5) {
                                    foods.add(save);
                                }
                            }
                            food="<< "+wantFood+"식 >>\n";
                            for(String strBuff:foods){
                                food_url = strBuff+"\n";
                                food += food_url;
                            }

                            bundle.putString("food_info", food); //bundle 이라는 자료형에 뽑아낸 결과값 담아서 main Thread로 보내기

                            Message msg = handler.obtainMessage();
                            msg.setData(bundle);
                            handler.sendMessage(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

            }
        });
    }
}