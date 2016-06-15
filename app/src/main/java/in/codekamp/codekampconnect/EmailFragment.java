package in.codekamp.codekampconnect;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.codekamp.codekampconnect.models.DataAction;
import in.codekamp.codekampconnect.models.Error;
import in.codekamp.codekampconnect.models.ListResponse;
import in.codekamp.codekampconnect.services.CodeKamp.Callback;
import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmailFragment extends Fragment {
    public static final String type3 = "email";
    private AcustomAdapter mAdapter;
    private ArrayList<DataAction> mActionArray;

    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.list_contacts_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.load_contacts)
    ProgressBar progressBar3;
    TextView emailview;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout mswiperefresh;
    public String mAccessToken;


    public EmailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_call, container, false);

        ButterKnife.bind(this, view);
        mActionArray = new ArrayList<DataAction>();
        listactions(type3);


        mActionArray = new ArrayList<DataAction>();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.hasFixedSize();
        mAdapter = new AcustomAdapter(getActivity(), mActionArray);
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    public void listactions(String type) {

        SharedPreferences prefs = getActivity().getSharedPreferences("mypref", 0);
        String mAccessToken = prefs.getString("accessToken", null);

        CodeKampService service = new CodeKampService(mAccessToken);

        service.listactions(type, new Callback<ListResponse<DataAction>>() {

            @Override
            public void onSuccess(ListResponse<DataAction> response) {
                mActionArray.addAll(response.getData());
                mAdapter.notifyDataSetChanged();
                Log.d("codekamp", "in call fragment" + response.getData().get(0).getTitle());


            }

            @Override
            public void onFailure(Error error) {

            }
        });
    }
}
