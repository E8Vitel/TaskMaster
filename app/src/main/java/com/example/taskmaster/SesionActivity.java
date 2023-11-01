package com.example.taskmaster;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.taskmaster.db.DbHelper;

public class SesionActivity extends AppCompatActivity {

    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sesion);

        btn = findViewById(R.id.btnInicio);

        DbHelper dbHelper = new DbHelper(SesionActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        btn = findViewById(R.id.btnInicio);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View view) {
                Intent intent = new Intent(SesionActivity.this,TareaActivity.class);
                startActivity(intent);

            }
        });
    }
}