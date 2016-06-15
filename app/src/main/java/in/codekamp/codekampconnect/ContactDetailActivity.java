package in.codekamp.codekampconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.codekamp.codekampconnect.models.Contact;
import in.codekamp.codekampconnect.models.DataAction;
import in.codekamp.codekampconnect.models.Error;
import in.codekamp.codekampconnect.models.ItemResponse;
import in.codekamp.codekampconnect.models.ListResponse;
import in.codekamp.codekampconnect.services.CodeKamp.Callback;
import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;

public class ContactDetailActivity extends AppCompatActivity {


    @BindView(R.id.first_name_detail)
    TextView mFirstName;
    @BindView(R.id.last_name_detail)
    TextView mLastName;
    @BindView(R.id.birthday_detail)
    TextView mBirthDay;
    @BindView(R.id.email_detail)
    TextView myemaildetail;
    @BindView(R.id.phone_detail)
    TextView myphonedetail;
    @BindView(R.id.facebook_id)
    TextView myfacebookid;
    @BindView(R.id.linkedin_id)
    TextView mylinkedinid;
    @BindView(R.id.spin_load)
    ProgressBar spinload;

    private ArrayAdapter mAdapter;
    private ArrayList<String> mActionArray;
    private int currentid = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);


        ButterKnife.bind(this);
        mActionArray = new ArrayList<String>();
        mAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mActionArray);
        int k = (int) getIntent().getExtras().get("contact_id");
        spinload.setVisibility(View.VISIBLE);
        getDetails(k);

    }


    private void getDetails(final int contactID) {
        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String mAccessToken = prefs.getString("accessToken", null);

        CodeKampService service = new CodeKampService(mAccessToken);

        service.getContact(contactID, new Callback<ItemResponse<Contact>>() {


            @Override
            public void onSuccess(ItemResponse<Contact> response) {
                mFirstName.setText(response.getData().getFirstName());
                mLastName.setText(response.getData().getLastName());
                mBirthDay.setText(response.getData().getBirthday());
                try {
                    myemaildetail.setText(response.getData().getEmails().getData().get(0).getEmail());
                    myphonedetail.setText(response.getData().getPhones().getData().get(0).getNumber());
                } catch (Exception e) {
                }

                // Log.d("codekamp",response.getData().getEmails().getData().get(0).getEmail());

                currentid = response.getData().getId();
                myfacebookid.setText(response.getData().getFacebookId());
                mylinkedinid.setText(response.getData().getLinkedinId());

                spinload.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Error error) {
                spinload.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "failiure", Toast.LENGTH_LONG).show();

            }


        });
    }


    public void updatecontact(View view) {
        Intent k = new Intent(getApplicationContext(), UpdateContact.class);
        k.putExtra("currentid", currentid);
        k.putExtra("firstname", mFirstName.getText());
        k.putExtra("lastname", mLastName.getText());
        k.putExtra("birthday", mBirthDay.getText());
        k.putExtra("email", myemaildetail.getText());
        k.putExtra("facebook", myfacebookid.getText());
        k.putExtra("linkedin", mylinkedinid.getText());
        startActivity(k);

    }




//    private void getActions(int contactID) {
//        service.fetchActions(1, contactID, new Callback<ListResponse<SingleAction>>() {
//            @Override
//            public void onSuccess(ListResponse<SingleAction> response) {
//                for (int i = 0; i < response.getData().size(); i++) {
//
//                    if (response.getData().get(i).getResponse().getActionableType().equals("Codekamp\\SmsAction")) {
//                        mActionArray.add("SMS : " + response.getData().get(i).getSms().getData().getMessage());
//                    } else if (response.getData().get(i).getResponse().getActionableType().equals("Codekamp\\CallAction")) {
//                        mActionArray.add("CALL : " + Integer.toString(response.getData().get(i).getType().getDuration()));
//                    } else if (response.getData().get(i).getEmail() != null) {
//                        mActionArray.add("EMAIL : " + response.getData().get(i).getEmail().getData().getSubject());
//
//                    }
//                }
//
//                mAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Error error) {
//
//            }
//        });
//    }
//}

    public void listactions() {

        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String mAccessToken = prefs.getString("accessToken", null);

        CodeKampService service = new CodeKampService(mAccessToken);

        service.listactions("email", new Callback<ListResponse<DataAction>>() {

            @Override
            public void onSuccess(ListResponse<DataAction> response) {
             //   mActionArray.addAll(response.getData());
                Log.d("codekamp", "in call fragment" + response.getData().get(0).getTitle());


            }

            @Override
            public void onFailure(Error error) {

            }
        });
    }
}


