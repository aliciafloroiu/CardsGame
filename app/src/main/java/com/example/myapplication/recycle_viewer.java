package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.IDNA;
import android.os.Bundle;
import android.view.View;

public class recycle_viewer extends AppCompatActivity {

    DrawerLayout drawerLayout;
    RecyclerView recycleView;
    String s1[], s2[];
    int images[] = { R.drawable.carte8, R.drawable.carte9, R.drawable.carte10, R.drawable.carte11, R.drawable.carte12, R.drawable.carte13, R.drawable.carte14,R.drawable.carte15};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_viewer);


        drawerLayout = findViewById(R.id.drawer_layout);
        recycleView = findViewById(R.id.recycle_view);

        s1 = getResources().getStringArray(R.array.Cards);
        s2 = getResources().getStringArray(R.array.Description);

        MyAdaptor myAdaptor = new MyAdaptor(this, s1, s2, images);
        recycleView.setAdapter(myAdaptor);
        recycleView.setLayoutManager( new LinearLayoutManager(this));
    }


    // DRAWER
    public void ClickRecycleViewer( View view ){ recreate(); }

    public void ClickMenu( View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickLogo( View view ){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickGame ( View view ) { MainActivity.redirectActivity( this, MainActivity.class ) ; }

    public void ClickInfo( View view ){
        MainActivity.redirectActivity( this, Information.class);
    }

    public void ClickClose ( View view) {
        MainActivity.closeApp( this);
    }

    @Override
    protected void onPause(){
        super.onPause();

        MainActivity.closeDrawer(drawerLayout);
    }
}