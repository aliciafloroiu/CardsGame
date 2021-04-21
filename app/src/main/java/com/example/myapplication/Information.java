package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Information extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Button btNotification;
    TextView txtNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        drawerLayout = findViewById(R.id.drawer_layout);
        btNotification = findViewById(R.id.bt_notification);
        txtNotification = findViewById(R.id.txtNotification);

        String message = getIntent().getStringExtra("message");
        txtNotification.setText(message);
    }

    // NOTIFICATION BUTTON
    public void ClickNotification ( View view )
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("My notification","My notification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // On Button Click - Pop up the notification
        btNotification.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick ( View v ){

                String message = " Click here to play the game!";

                // Notification action
                Intent intent = new Intent( Information.this, MainActivity.class);
                intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("message", message);

                PendingIntent pendingIntent = PendingIntent.getActivity( Information.this,1,intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // Notification Messages
                NotificationCompat.Builder builder = new NotificationCompat.Builder( Information.this, "My notification");
                builder.setSmallIcon(R.drawable.ic_notification);
                builder.setContentTitle( "Play The Game !");
                builder.setContentText( message );
                builder.setContentIntent(pendingIntent);
                builder.setAutoCancel(true);

                NotificationManagerCompat managerCompat = NotificationManagerCompat.from( Information.this);
                managerCompat.notify(1, builder.build());
            }
        });

        txtNotification.setText("Open the notification bar!");
    }


    // DRAWER
    public void ClickMenu( View view){
        MainActivity.openDrawer(drawerLayout);
    }

    public void ClickRecycleViewer( View view ){MainActivity.redirectActivity( this, recycle_viewer.class ) ;  }

    public void ClickLogo( View view ){
        MainActivity.closeDrawer(drawerLayout);
    }

    public void ClickGame ( View view ) { MainActivity.redirectActivity( this, MainActivity.class ) ; }

    public void ClickInfo( View view ){
        recreate();
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