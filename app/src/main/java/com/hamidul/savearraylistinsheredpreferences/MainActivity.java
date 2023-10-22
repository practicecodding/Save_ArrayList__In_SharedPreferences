package com.hamidul.savearraylistinsheredpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edName,edAge;
    Button button;
    TextView textView,count;
    ArrayList <ModelClass> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edName = findViewById(R.id.edName);
        edAge = findViewById(R.id.edAge);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);
        count = findViewById(R.id.count);

        loadData();

        count.setText(""+arrayList.size());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(edName.getText().toString(),edAge.getText().toString());
                loadData();
                count.setText(""+arrayList.size());
            }
        });

        count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList = new ArrayList<>();
            }
        });


    }

    private void loadData() {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPreferences.getString("product_data",null);
        Type type = new TypeToken<ArrayList<ModelClass>>(){
        }.getType();

        arrayList = gson.fromJson(json,type);

        if (arrayList == null){
            arrayList = new ArrayList<>();
        } else {
            textView.setText("");
            for (int x=0; x<arrayList.size(); x++){
                int num = x+1;
                textView.append(num+". "+arrayList.get(x).name+"\n    "+arrayList.get(x).age+"\n\n");
            }
        }

    }

    private void saveData(String name, String age) {

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.app_name),MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        arrayList.add(new ModelClass(name,Integer.parseInt(age)));
        String json = gson.toJson(arrayList);
        editor.putString("product_data",json);
        editor.apply();


    }
}