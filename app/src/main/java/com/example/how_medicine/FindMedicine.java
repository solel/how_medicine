package com.example.how_medicine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.material.textfield.TextInputEditText;

public class FindMedicine extends AppCompatActivity {
    EditText presentSymptom, takingMedications, sickDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_medicine);

        presentSymptom = findViewById(R.id.presentSymptom);
        takingMedications = findViewById(R.id.takingMedications);
        sickDisease = findViewById(R.id.sickDisease);

        final Button button = findViewById(R.id.searchMedicine);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String symtom = presentSymptom.getText().toString();
                String taking = takingMedications.getText().toString();
                String desease = sickDisease.getText().toString();

                // FindMedicineData로 값을 전달, 이동
                Intent intent2 = new Intent(FindMedicine.this, FindMedicineData.class);
                intent2.putExtra("symtom", symtom);
                intent2.putExtra("taking", taking);
                intent2.putExtra("desease", desease);
                startActivity(intent2);
            }
        });
    }
}