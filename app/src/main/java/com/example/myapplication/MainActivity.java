package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;

import java.util.Random;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    // Initialization
    DrawerLayout drawerLayout;


    ImageView carte_stanga,carte_dreapta;
    TextView  scor_stanga,scor_dreapta;
    private TextView mTextViewResult;
    Button button_razboi,button_reset;;
    int scorStanga=0,scorDreapta=0;
    Random r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        carte_stanga=(ImageView) findViewById(R.id.carte_stanga);
        carte_dreapta=(ImageView) findViewById(R.id.carte_dreapta);
        scor_stanga=(TextView) findViewById(R.id.scor_stanga);
        scor_dreapta=(TextView) findViewById(R.id.scor_dreapta);
        button_razboi=(Button) findViewById(R.id.button_Razboi);
        r=new Random();

        button_razboi.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view){
        int carteStanga=r.nextInt(14)+2;
        int carteDreapta=r.nextInt(14)+2;
        int imagineStanga=getResources().getIdentifier("carte"+carteStanga,"drawable",getPackageName());
        carte_stanga.setImageResource(imagineStanga);
        int imagineDreapta=getResources().getIdentifier("carte"+carteDreapta,"drawable",getPackageName());
        carte_dreapta.setImageResource(imagineDreapta);

        if(carteStanga>carteDreapta)
            {
        scorStanga++;
        scor_stanga.setText(String.valueOf(scorStanga));
            } else if(carteDreapta>carteStanga)
            {
        scorDreapta++;
        scor_dreapta.setText(String.valueOf(scorDreapta));
            } else{
            Toast.makeText(MainActivity.this,"RAZBOI",Toast.LENGTH_SHORT).show();

        }
        }});


        mTextViewResult = findViewById(R.id.text_view_result);
        OkHttpClient client = new OkHttpClient();
        String url = "https://www.vreausatrec.ro/indexblank";
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mTextViewResult.setText(myResponse);
                        }
                    });
                }
            }
        });


        drawerLayout = findViewById(R.id.drawer_layout);

    }


    // OPEN DRAWER
    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    // CLOSE DRAWER
    public void ClickLogo( View view ){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    // MOVE TO GAME VIEW
    public void ClickGame( View view ){
        recreate();
    }

    // MOVE TO INFO VIEW
    public void  ClickInfo ( View view ){
        redirectActivity( this, Information.class);
    }

    // MOVE TO RECYCLEVIEWER
    public void ClickRecycleViewer( View view ){ redirectActivity( this, recycle_viewer.class ) ;  }

    // CLOSE GAME
    public void ClickClose( View view ){
        closeApp(this);
    }

    // POP UP ALERT
    public static void closeApp( Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle("Closing App");
        builder.setMessage("Are you sure?");

        // POSITIVE ANSWERE
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                activity.finishAffinity();
                System.exit(0);
            }
        });

        // NEGATIVE ANSWERE
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which){
                dialog.dismiss();
            }
        });

        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        activity.startActivity(intent);
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }
}