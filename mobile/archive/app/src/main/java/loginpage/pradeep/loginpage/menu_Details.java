package loginpage.pradeep.loginpage;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class menu_Details extends AppCompatActivity {

    public RatingBar ratingBar;
    EditText comment;
    String rating;
    String review;
    String dish ;
    String uname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__details);
        Intent intent = getIntent();
        String Description = intent.getStringExtra("DESC");
        TextView Desc = (TextView) findViewById(R.id.Desc);
        //Desc.setText(Description);
        Desc.setText(Description);

        TextView hname = (TextView) findViewById(R.id.Hname);
        TextView dname = (TextView) findViewById(R.id.Dname);

        hname.setText(intent.getStringExtra("HOTEL"));
        dname.setText(intent.getStringExtra("DISH"));
        dish = intent.getStringExtra("DISH");
        uname = intent.getStringExtra("UNAME");


        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                rateMe(view);


            }
        });;




        Button submit = (Button) findViewById(R.id.SumitReview);

        submit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                EditText comment = (EditText) findViewById(R.id.Comment);
                rating = Double.toString(ratingBar.getRating());
                review = comment.getText().toString();
                System.out.println(review + "  WRITE THIS  " + rating);
                postToServer(review,rating);

            }
        });;

    }
    public void rateMe(View view){


        Toast.makeText(getApplicationContext(), Double.toString(ratingBar.getRating()),
                Toast.LENGTH_LONG).show();
    }

    public void postToServer(String review,String rating){


        String serverUrl = "http://hungrymeser.herokuapp.com/hotel/users/" + uname + "/menu/" + dish ;
        JSONObject jsonObj = new JSONObject();

        try{
            jsonObj.put("review", rating);
            jsonObj.put("comment", review);
        }catch (JSONException e){

        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT,serverUrl,jsonObj,
                        new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject res){

                                System.out.println("Success full entered");

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        System.out.println("Something went wrong");
                        error.printStackTrace();

                    }
                }){

        };
        MySingleTon.getInstance(this).addToReqQue(jsonObjectRequest);
    }
}