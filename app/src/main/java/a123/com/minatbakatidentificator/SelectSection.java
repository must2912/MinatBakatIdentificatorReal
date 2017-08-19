package a123.com.minatbakatidentificator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;

/*import dev.edmt.flagsquizapp.Common.Common;*/
import a123.com.minatbakatidentificator.DBHelper.dbhelper;

public class SelectSection extends AppCompatActivity {

    Button btnGayaBelajar,btnMinatBakat;
    dbhelper db;
    private TextView mRecent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_section);

        /*seekBar = (SeekBar)findViewById(R.id.seekBar);
        txtMode = (TextView)findViewById(R.id.txtMode);*/
        btnGayaBelajar = (Button)findViewById(R.id.btnGayaBelajar);
        btnMinatBakat = (Button)findViewById(R.id.btnMinatBakat);
        mRecent = (TextView)findViewById(R.id.recentgayabelajar);

        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor meditor = mSharedPreferences.edit();

        String recent = mSharedPreferences.getString(getString(R.string.recent), "");
        mRecent.setText(recent);

        /*db = new dbhelper(this);
        try{
            db.createDataBase();
        }
        catch (IOException e){
            e.printStackTrace();
        }*/




        //Event
       /* seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress == 0)
                    txtMode.setText(Common.MODE.EASY.toString());
                else if(progress == 1)
                    txtMode.setText(Common.MODE.MEDIUM.toString());
                else if(progress == 2)
                    txtMode.setText(Common.MODE.HARD.toString());
                else if(progress == 3)
                    txtMode.setText(Common.MODE.HARDEST.toString());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/
        btnGayaBelajar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Playing.class);
                /*intent.putExtra("MODE",getPlayMode()); // Send Mode to Playing page*/
                startActivity(intent);
                finish();
            }
        });

        /*btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Score.class);
                startActivity(intent);
                finish();
            }
        });*/
    }

    /*private String getPlayMode() {
        if(seekBar.getProgress()==0)
            return Common.MODE.EASY.toString();
        else if(seekBar.getProgress()==1)
            return Common.MODE.MEDIUM.toString();
        else if(seekBar.getProgress()==2)
            return Common.MODE.HARD.toString();
        else
            return Common.MODE.HARDEST.toString();
    }*/
}
