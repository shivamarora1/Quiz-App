package com.example.shivam.quiz_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class final_activity extends AppCompatActivity {

    TextView fin_score;
    Button play_again;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_screen);
        String scores_tot=getIntent().getStringExtra("res");
        String[] score_split=scores_tot.split(" ");
        fin_score=(TextView)findViewById(R.id.final_score);
        play_again=(Button)findViewById(R.id.play_ag);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(final_activity.this,Option_Activity.class);
                startActivity(i);
            }
        });
        fin_score.setText("You Scored "+score_split[0]+" out of "+score_split[1]);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(final_activity.this,Option_Activity.class));
    }
}
