package in.codekamp.codekampconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.codekamp.codekampconnect.models.Contact;
import in.codekamp.codekampconnect.models.Error;
import in.codekamp.codekampconnect.models.ItemResponse;
import in.codekamp.codekampconnect.services.CodeKamp.Callback;
import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;

public class UpdateContact extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.upSave_firstname)EditText upfirstname;
    @BindView(R.id.uplast_name)EditText uplastname;
    @BindView(R.id.update_button)
    Button updatebutton;
    @BindView(R.id.uppinner2)
    ProgressBar upspin;
    @BindView(R.id.upbday_ip)EditText bdayip;
    @BindView(R.id.upemail_ip) EditText upemailip;
    @BindView(R.id.upfacebook_ip) EditText upfacebookip;
    @BindView(R.id.uplinkedin_ip) EditText uplinkedinip;
  //  @BindView(R.id.phone_input) EditText phoneinput;
    public String[] upemail={""};
    public String[] phonenumbers={""};
    public String currentid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        ButterKnife.bind(this);
        currentid= String.valueOf(getIntent().getExtras().get("currentid"));

        upfirstname.setText(getIntent().getExtras().get("firstname").toString());
        uplastname.setText(getIntent().getExtras().get("lastname").toString());
        bdayip.setText(getIntent().getExtras().get("birthday").toString());
        upemailip.setText(getIntent().getExtras().get("email").toString());
        upfacebookip.setText(getIntent().getExtras().get("facebook").toString());
        uplinkedinip.setText(getIntent().getExtras().get("linkedin").toString());
      //  phoneinput.setText(getIntent().getExtras().get("phone_numbers").toString());
        updatebutton.setOnClickListener(this);






    }


    @Override
    public void onClick(View v) {

        upspin.setVisibility(View.VISIBLE);

        String upfirstnam=upfirstname.getText().toString();
        String uplastnam=uplastname.getText().toString();
        upemail[0]=upemailip.getText().toString();
    //    phonenumbers[0]=phoneinput.getText().toString();
        String upbday=bdayip.getText().toString();
        String upfacebook=upfacebookip.getText().toString();
        String uplinkedin=uplinkedinip.getText().toString();


        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String mAccessToken = prefs.getString("accessToken", null);

        CodeKampService service = new CodeKampService(mAccessToken);

        service.updateContact(currentid,upfirstnam,uplastnam,upbday,upfacebook,uplinkedin, new Callback<ItemResponse<Contact>>() {
            @Override
            public void onSuccess(ItemResponse<Contact> response) {

                upspin.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Contact updated done!",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Error error) {
                upspin.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Somehing went wrong!"+error.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



    }
}
