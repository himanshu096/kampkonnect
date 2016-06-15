package in.codekamp.codekampconnect;

import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import in.codekamp.codekampconnect.models.DataAction;
import in.codekamp.codekampconnect.models.Error;
import in.codekamp.codekampconnect.models.ListResponse;
import in.codekamp.codekampconnect.services.CodeKamp.Callback;
import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;

public class ActionActivity extends AppCompatActivity {
    ViewPager pager;
    public  static final String type1="call";
    private ArrayList<DataAction> mActionArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        pageAdapter adapter= new pageAdapter(getSupportFragmentManager());

        pager=(ViewPager)findViewById(R.id.pager);

        pager.setAdapter(adapter);
        mActionArray = new ArrayList<DataAction>();



        listactions(type1);



    }

private class pageAdapter extends FragmentPagerAdapter {

    public pageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:return new CallFragment();
            case 1: return new EmailFragment();
            case 2: return new SmsFragment();
            default: return new SmsFragment();

        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "Calls";
            case 1: return  "Emails";
            case 2: return "Messages";
            default:return "Email";
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}




    public void listactions(String type) {

        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        String mAccessToken = prefs.getString("accessToken", null);

        CodeKampService service = new CodeKampService(mAccessToken);

        service.listactions(type, new Callback<ListResponse<DataAction>>() {

            @Override
            public void onSuccess(ListResponse<DataAction> response) {
                mActionArray.addAll(response.getData());

                Log.d("codekamp",response.getData().toString());
                Log.d("codekamp","its from array  :-"+mActionArray.get(0).getTitle());

            }

            @Override
            public void onFailure(Error error) {

            }
        });
    }

}


