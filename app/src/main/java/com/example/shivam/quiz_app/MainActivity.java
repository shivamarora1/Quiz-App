package com.example.shivam.quiz_app;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int que_num=0;
    AlertDialog.Builder alertDiag;
    int min=1;
    int max=4;
    int score=0;
    TextView card_question,next_que;
    Button optuion_a,optuion_b,optuion_c,optuion_d;
    public String URL;
    ProgressDialog pg;
    List<Question> question_list;
    int cat_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pg=new ProgressDialog(this);
        alertDiag = new AlertDialog.Builder(MainActivity.this);
        pg.setTitle("Loading");
        pg.setMessage("Please Wait...");
        pg.show();
        String merged=getIntent().getStringExtra("val");
        String merged_arr[]=merged.split(" ");
        String category_=merged_arr[2];
        String diffculty_=merged_arr[1];
        diffculty_=diffculty_.toLowerCase();
        String num_ques_=merged_arr[0];
        int int_num_ques_=Integer.parseInt(num_ques_);
        int int_category=Integer.parseInt(category_)+8;

        URL="https://opentdb.com/api.php?amount="+int_num_ques_+"&category="+int_category+"&difficulty="+diffculty_+"&type=multiple&encode=url3986";

        optuion_a=(Button)findViewById(R.id.optuion_a);
        optuion_b=(Button)findViewById(R.id.optuion_b);
        optuion_c=(Button)findViewById(R.id.optuion_c);
        optuion_d=(Button)findViewById(R.id.optuion_d);
        next_que=(TextView)findViewById(R.id.next_que);
        card_question=(TextView)findViewById(R.id.card_question);
        question_list=new ArrayList<>();
        fillQuestionList();
        //setQuestion();
    }


    public void next_question(View v)
    {
        if(que_num<question_list.size()-1)
        {
            this.que_num++;
            setQuestion();
        }
        else
        {
            Intent i=new Intent(MainActivity.this,final_activity.class);
            i.putExtra("res",score+" "+(que_num+1));
            startActivity(i);
        }
    }

    private void setQuestion() {
        Random r=new Random();
        int correct_pos=min+r.nextInt(max);
        card_question.setText(question_list.get(que_num).getQuestion_title());
        String[] incorrect_option=question_list.get(que_num).getIncorrect_ans();
        switch (correct_pos){
            case 1:
            {
                optuion_a.setText(question_list.get(que_num).getCorrect_ans());
                optuion_b.setText(incorrect_option[0]);
                optuion_c.setText(incorrect_option[1]);
                optuion_d.setText(incorrect_option[2]);
            }
            case 2:
            {
                optuion_a.setText(incorrect_option[1]);
                optuion_b.setText(question_list.get(que_num).getCorrect_ans());
                optuion_c.setText(incorrect_option[0]);
                optuion_d.setText(incorrect_option[2]);
            }
            case 3:
            {
                optuion_a.setText(incorrect_option[2]);
                optuion_b.setText(incorrect_option[1]);
                optuion_c.setText(question_list.get(que_num).getCorrect_ans());
                optuion_d.setText(incorrect_option[0]);
            }
            case 4:
            {
                optuion_a.setText(incorrect_option[1]);
                optuion_b.setText(incorrect_option[2]);
                optuion_c.setText(incorrect_option[0]);
                optuion_d.setText(question_list.get(que_num).getCorrect_ans());
            }
        }
//        optuion_a.setText(question_list.get(que_num).getCorrect_ans());
//        optuion_b.setText(incorrect_option[0]);
//        optuion_c.setText(incorrect_option[1]);
//        optuion_d.setText(incorrect_option[2]);
    }

    public void check_answer(View v)
    {
        Button selected_btn=(Button)v;
        String selected_btn_txt=selected_btn.getText().toString();
        String correct_asr=question_list.get(que_num).getCorrect_ans();
        if(selected_btn_txt.equals(correct_asr))
        {
            score++;
        }
        next_question(next_que);
    }

    private void fillQuestionList() {

        RequestQueue rq= Volley.newRequestQueue(MainActivity.this);
        StringRequest sr=new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ///DO any thing Here
                try {
                    JSONObject jObj=new JSONObject(response);
                    String response_code=jObj.getString("response_code");
                    if(response_code.equals("0")){
                        JSONArray result_arr=jObj.getJSONArray("results");
                        for(int i=0;i<result_arr.length();i++)
                        {
                            try {
                            String inCorrect_ans_array[]=new String[3];
                            JSONObject jsonObject=result_arr.getJSONObject(i);

                            String question_title=jsonObject.getString("question");
                            String decoded_question_title= URLDecoder.decode(question_title,"UTF-8");

                            String question_ans=jsonObject.getString("correct_answer");
                            String decoded_question_ans= URLDecoder.decode(question_ans,"UTF-8");

                            JSONArray incorrect_choices=jsonObject.getJSONArray("incorrect_answers");
                            for(int j=0;j<incorrect_choices.length();j++)
                            {
                                inCorrect_ans_array[j]=URLDecoder.decode(incorrect_choices.getString(j),"UTF-8");;
                            }
                            question_list.add(new Question(decoded_question_title,decoded_question_ans,inCorrect_ans_array));
                            }
                            catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        pg.dismiss();
                        setQuestion();
                    }
                    else{
                        alertDiag.setTitle("Error");
                        alertDiag.setMessage("Try other category or difficulty level");
                        alertDiag.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(MainActivity.this,Option_Activity.class));
                            }
                        });
                        alertDiag.show();
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ////Do anything Here
            }
        });
        rq.add(sr);
    }

    public void onBackPressed()
    {

        alertDiag.setTitle("Warning");
        alertDiag.setMessage("Are you sure to exit?");
        alertDiag.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent in=new Intent(MainActivity.this,final_activity.class);
                in.putExtra("res",score+" "+(que_num+1));
                startActivity(in);
            }
        });
        alertDiag.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDiag.show();
    }

    private void displayArrayList() {
        for (Question i:question_list)
        {
            Log.i("Hello",i.getQuestion_title()+" "+i.getCorrect_ans()+" "+i.getIncorrect_ans()[0]+" "+i.getIncorrect_ans()[1]+" "+i.getIncorrect_ans()[2]);
        }
    }
}
