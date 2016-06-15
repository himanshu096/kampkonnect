package in.codekamp.codekampconnect;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.multiselector.MultiSelector;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.codekamp.codekampconnect.Utils.Animationutil;
import in.codekamp.codekampconnect.models.Contact;
import in.codekamp.codekampconnect.models.Error;
import in.codekamp.codekampconnect.models.ListResponse;
import in.codekamp.codekampconnect.models.Pagination;
import in.codekamp.codekampconnect.services.CodeKamp.Callback;
import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;

public class ListContactActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SwipeRefreshLayout.OnRefreshListener{
    private CustomAdapter mAdapter;
    private List<Contact> mContactsList;
    private int mPageNumberToFetch = 1;
    String myemail;




    private int previousTotal = 0;           // The total number of items in the dataset after the last load
    private boolean loading = true;         // True if we are still waiting for the last set of data to load.
    private int visibleThreshold = 1;       // The minimum amount of items to have below your current scroll position before loading more.
    int firstVisibleItem, visibleItemCount, totalItemCount;

    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.list_contacts_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.load_contacts)ProgressBar progressBar3;
    TextView emailview;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout mswiperefresh;
    public String mAccessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent k= new Intent(getApplicationContext(),SaveContact.class);
                startActivity(k);
            }
        });

        ButterKnife.bind(this);



        SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
        mAccessToken = prefs.getString("accessToken", null);
        myemail=prefs.getString("email",null);


        mContactsList = new ArrayList<Contact>();
        mLayoutManager = new LinearLayoutManager(ListContactActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.hasFixedSize();
        mAdapter = new CustomAdapter(ListContactActivity.this, mContactsList);
        mRecyclerView.setAdapter(mAdapter);
        mswiperefresh.setOnRefreshListener(this);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = mLayoutManager.getItemCount();
                firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && (totalItemCount - visibleItemCount)
                        <= (firstVisibleItem + visibleThreshold)) {
                    // End has been reached

                    // Do something
                    mPageNumberToFetch++;
                    fetchContactsList(mAccessToken, mPageNumberToFetch);
                    loading = true;
                }


            }
        });



        fetchContactsList(mAccessToken, mPageNumberToFetch);






        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        emailview= (TextView) findViewById(R.id.email_view);
        emailview.setText(myemail);
        getMenuInflater().inflate(R.menu.list_contact, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            Intent i=new Intent(this,ActionActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_slideshow) {



        } else if (id == R.id.nav_manage) {
            SharedPreferences prefs = getSharedPreferences("mypref", MODE_PRIVATE);
            prefs.edit().clear().commit();

            Intent j = new Intent(this,MainActivity.class);
            j.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(j);
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
















    }


    private void fetchContactsList(String accessToken, int pageNumber) {
        CodeKampService service = new CodeKampService(accessToken);
        progressBar3.setVisibility(View.VISIBLE);

        service.listContacts(pageNumber, new Callback<ListResponse<Contact>>() {
            @Override
            public void onSuccess(ListResponse<Contact> response) {
                mContactsList.addAll(response.getData());
                mAdapter.notifyDataSetChanged();
                mswiperefresh.setRefreshing(false);
                progressBar3.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Error error) {
                progressBar3.setVisibility(View.INVISIBLE);
                mswiperefresh.setRefreshing(false);
                Toast.makeText(ListContactActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        mContactsList.clear();
        mAdapter.notifyDataSetChanged();
        mPageNumberToFetch=1;
        fetchContactsList(mAccessToken, mPageNumberToFetch);
    }


    //View Holder Class
    public class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        public int contactID;

        @BindView(R.id.view_holder_custom_text_view)
        TextView mTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View v) {
            Intent m=new Intent(getApplicationContext(), ContactDetailActivity.class  );
            m.putExtra("contact_id",contactID);
            startActivity(m);
        }

        @Override
        public boolean onLongClick(View v) {


            int pos=this.getAdapterPosition();

            MultiSelector selector = new MultiSelector();
            selector.setSelected(pos,contactID, true);
            selector.isSelected(pos,contactID);



            Toast.makeText(getApplicationContext(),"Long click done"+contactID+"  "+pos,Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    //Adapter Class
    public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder> {


        private int previousPosition = 0;

        private List<Contact> dataList;
        private LayoutInflater inflater;

        public CustomAdapter(Context context, List<Contact> dataList) {
            inflater = LayoutInflater.from(context);
            this.dataList = dataList;
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new CustomViewHolder(inflater.inflate(R.layout.view_holder_custom, parent, false));
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            holder.mTextView.setText(dataList.get(position).getFirstName() + " " + dataList.get(position).getLastName());
            holder.contactID=dataList.get(position).getId();

            if(position > previousPosition){ // We are scrolling DOWN

                Animationutil.animate(holder, true);

            }else{ // We are scrolling UP

                Animationutil.animate(holder, false);


            }

            previousPosition = position;


            final int currentPosition = position;



        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

}


