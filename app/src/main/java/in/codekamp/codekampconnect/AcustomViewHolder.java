package in.codekamp.codekampconnect;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Himanshu on 6/14/2016.
 */
public class AcustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.title_text)
    TextView mTextView;
    @BindView(R.id.time_text)
    TextView mTimeView;
    public AcustomViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void onClick(View v) {

    }
}
