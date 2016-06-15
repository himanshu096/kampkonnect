package in.codekamp.codekampconnect;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.codekamp.codekampconnect.models.*;
import in.codekamp.codekampconnect.models.Error;
import in.codekamp.codekampconnect.services.CodeKamp.Callback;
import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;

import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;

public class SaveContact extends AppCompatActivity implements View.OnClickListener {


    @BindView(R.id.Save_firstname) EditText firstname;
    @BindView(R.id.last_name) EditText lastname;
    @BindView(R.id.save_button) Button submit;
    @BindView(R.id.spinner2)ProgressBar progressBar2;
    @BindView(R.id.bday_ip)EditText bdayip;
    @BindView(R.id.email_ip) EditText emailip;
    @BindView(R.id.facebook_ip) EditText facebookip;
    @BindView(R.id.linkedin_ip) EditText linkedinip;
    @BindView(R.id.container1)
    LinearLayout addemaildata;
    @BindView(R.id.phone_no) EditText phonenumber;
    @BindView(R.id.container2) LinearLayout addemaildata2;
    public String[] email={""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_contact);

        ButterKnife.bind(this);

        submit.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        progressBar2.setVisibility(View.VISIBLE);
        String firstnam=firstname.getText().toString();
        String lastnam=lastname.getText().toString();
        email[0]=emailip.getText().toString();

        String bday=bdayip.getText().toString();
        String facebook=facebookip.getText().toString();
        String linkedin=linkedinip.getText().toString();
        String phoneno=phonenumber.getText().toString();

//        MainActivity activity=new MainActivity();
//        String token_key=activity.ACCESS_TOKEN;

        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String ACCESS_TOKEN = prefs.getString("accessToken", null);
        if (ACCESS_TOKEN != null) {
            String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.

        }


            CodeKampService service = new CodeKampService(ACCESS_TOKEN);


        service.createContact(firstnam,lastnam,phoneno,email[0],bday,facebook,linkedin, new Callback<ItemResponse<Contact>>() {
            @Override
            public void onSuccess(ItemResponse<Contact> response) {
                progressBar2.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(),"Contact added successfuly !",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Error error) {
                progressBar2.setVisibility(View.GONE);
                Log.d("codekamp", error.getMessage());
                Toast.makeText(getApplicationContext(),"ofho..something happens wrong,check the fields",Toast.LENGTH_LONG).show();

            }
        });
    }

    public void bhaagja(View view){
        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        prefs.edit().clear().commit();

        Intent j = new Intent(this,MainActivity.class);
        j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(j);
        finish();
    }

    public void addmore(View view){

        if(addemaildata.getVisibility()==View.INVISIBLE) {
            addemaildata.setVisibility(View.VISIBLE);
        }
        if(addemaildata.getVisibility()==View.VISIBLE) {
            addemaildata2.setVisibility(View.VISIBLE);
        }
    }

    public void removemore(View view){
        if(addemaildata2.getVisibility()==View.VISIBLE) {
            addemaildata.setVisibility(View.INVISIBLE);
        }
        if(addemaildata.getVisibility()==View.VISIBLE) {
            addemaildata2.setVisibility(View.INVISIBLE);
        }

    }
    }
