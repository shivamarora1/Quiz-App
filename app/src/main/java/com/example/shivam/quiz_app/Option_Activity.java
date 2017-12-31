package com.example.shivam.quiz_app;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Option_Activity extends AppCompatActivity {

    EditText option_question_no;
    int sel_pos;
    Spinner option_difficulty;
    Spinner option_category;
    Button start_btn;

    public void onBackPressed()
    {
        finishAffinity();
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_quiz_option);
        option_question_no=(EditText)findViewById(R.id.option_question_no);
        option_difficulty=(Spinner)findViewById(R.id.option_difficulty);
        option_category=(Spinner)findViewById(R.id.option_category);
        start_btn=(Button)findViewById(R.id.start_btn);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num_of_que=option_question_no.getText().toString();
                String diff=option_difficulty.getSelectedItem().toString();
                sel_pos=option_category.getSelectedItemPosition();
                int diff_pos=option_difficulty.getSelectedItemPosition();
                if(num_of_que.equals("") || diff_pos==0 || sel_pos==0 || num_of_que.equals(""))
                {
                    Toast.makeText(Option_Activity.this, "Please fill all the details...", Toast.LENGTH_SHORT).show();
                }
                else
                    {
                        int int_num_of_que=Integer.parseInt(num_of_que);
                        if(int_num_of_que>50)
                        {
                            Toast.makeText(Option_Activity.this, "Question must be less than 50...", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Intent i=new Intent(Option_Activity.this,MainActivity.class);
                            i.putExtra("val",num_of_que+" "+diff+" "+sel_pos);
                            startActivity(i);
                        }
                }


            }
        });

    }
}
