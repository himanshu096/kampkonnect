package in.codekamp.codekampconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.codekamp.codekampconnect.models.*;
import in.codekamp.codekampconnect.models.Error;
import in.codekamp.codekampconnect.services.CodeKamp.Callback;
import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {




    @BindView(R.id.username_input) EditText usernameip;
    @BindView(R.id.password_input) EditText passwordip;
    @BindView(R.id.submit_button) Button submitbutton;
    @BindView(R.id.spinkrlo)ProgressBar progressBar;
//    @BindView(R.id.phone_view) TextView phone_view;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        submitbutton.setOnClickListener(this);






//
//        CodeKampService service = new CodeKampService(ACCESS_TOKEN);
//
//        service.listContacts(1, new Callback<ListResponse<Contact>>() {
//            @Override
//            public void onSuccess(ListResponse<Contact> response) {
//                List<Contact> contacts = response.getData();
//                for(i=0;i< contacts.size();i++) {
//                    Log.d("codekamp", contacts.get(i).getFirstName());
//                    Log.d("codekamp",contacts.get(i).getLastName());
//
//                    Log.d("codekamp",contacts.get(i).getEmails().getData().toString());
//
//
//                }
//
//
//              response.getMeta();
//
//            }
//
//            @Override
//            public void onFailure(Error error) {
//                Log.d("codekamp", error.getMessage());
//            }
//        });
//
//
//
//
//
//
//
////        service.createContact("Prashant", new Callback<ItemResponse<Contact>>() {
////            @Override
////            public void onSuccess(ItemResponse<Contact> response) {
////
////            }
////
////            @Override
////            public void onFailure(Error error) {
////
////            }
////        });





    }

    @Override
    public void onClick(View v) {

        progressBar.setVisibility(View.VISIBLE);

        final String username=usernameip.getText().toString();
        String password=passwordip.getText().toString();

        CodeKampService service=new CodeKampService();

        service.login(username,password,new Callback<Token>() {
            @Override
            public void onSuccess(Token response) {

               String acesstoken=response.getToken();
                String email=usernameip.getText().toString();

                Intent k= new Intent(getApplicationContext(),ListContactActivity.class);
                SharedPreferences.Editor editor = getSharedPreferences("mypref", MODE_PRIVATE).edit();
                editor.putString("accessToken", acesstoken);
                editor.putString("email",email);
                editor.commit();


                k.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(k);
                finish();

                startActivity(k);

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Error error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(),"Login Failed !",Toast.LENGTH_SHORT).show();
            }
        });






    }

}
