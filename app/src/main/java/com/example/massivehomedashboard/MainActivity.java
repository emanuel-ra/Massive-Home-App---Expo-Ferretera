package com.example.massivehomedashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.massivehomedashboard.db.DbHelper;

public class MainActivity extends AppCompatActivity {

    CardView oCardViewProspectos,cardOptionDownloads;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createdb();

        oCardViewProspectos = findViewById(R.id.cardOptionProspects);
        cardOptionDownloads = findViewById(R.id.cardOptionDownloads);

        oCardViewProspectos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oIntent = new Intent(MainActivity.this, ProspectosActivity.class);
                startActivity(oIntent);
            }
        });

        cardOptionDownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToDownloadModule();
            }
        });

    }

    private void createdb(){
        DbHelper oDbHelper = new DbHelper(MainActivity.this);
        SQLiteDatabase db = oDbHelper.getWritableDatabase();
        db.close();
    }
    private void GoToDownloadModule(){
        Intent oIntent = new Intent(MainActivity.this,ModuleDownloadsActivity.class);
        startActivity(oIntent);
    }
}