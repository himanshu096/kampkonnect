package in.codekamp.codekampconnect;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.codekamp.codekampconnect.models.Batch;
import in.codekamp.codekampconnect.models.DataAction;
import in.codekamp.codekampconnect.models.Error;
import in.codekamp.codekampconnect.models.ListResponse;
import in.codekamp.codekampconnect.services.CodeKamp.Callback;
import in.codekamp.codekampconnect.services.CodeKamp.CodeKampService;


/**
 * A simple {@link Fragment} subclass.
 */
public class BatchFragment extends Fragment {

    public  static final String type3="call";
    private AcustomAdapter mAdapter;
    private ArrayList<DataAction> mActionArray;

    private LinearLayoutManager mLayoutManager;
    @BindView(R.id.list_contacts_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.load_contacts)ProgressBar progressBar3;
    TextView emailview;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout mswiperefresh;
    public String mAccessToken;


    public BatchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_batch, container, false);

        ButterKnife.bind(this,view);
        mActionArray = new ArrayList<DataAction>();




        mActionArray = new ArrayList<DataAction>();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.hasFixedSize();
        mAdapter = new AcustomAdapter(getActivity(),mActionArray);
        mRecyclerView.setAdapter(mAdapter);




        return view;
    }




    private void listbatches(String accessToken, int pageNumber) {
        CodeKampService service = new CodeKampService(accessToken);
        progressBar3.setVisibility(View.VISIBLE);

       service.listbatches(pageNumber, new Callback<ListResponse<Batch>>() {

            @Override
            public void onSuccess(ListResponse<Batch> response) {
    //            mActionArray.addAll(response.getData());
                mAdapter.notifyDataSetChanged();
                mswiperefresh.setRefreshing(false);
               progressBar3.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Error error) {
                progressBar3.setVisibility(View.INVISIBLE);
                mswiperefresh.setRefreshing(false);
                Toast.makeText(getActivity(), "Failure!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
