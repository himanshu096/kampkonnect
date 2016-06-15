package in.codekamp.codekampconnect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.List;

import in.codekamp.codekampconnect.Utils.Animationutil;
import in.codekamp.codekampconnect.models.Contact;
import in.codekamp.codekampconnect.models.DataAction;

/**
 * Created by Himanshu on 6/14/2016.
 */
public class AcustomAdapter extends RecyclerView.Adapter<AcustomViewHolder>{





        private List<DataAction> dataList;
        private LayoutInflater inflater;
    private int previousPosition = 0;


    public AcustomAdapter(Context context, List<DataAction> dataList) {
            inflater = LayoutInflater.from(context);
            this.dataList = dataList;
        }

        @Override
        public AcustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AcustomViewHolder(inflater.inflate(R.layout.view_holder_act, parent, false));
        }

        @Override
        public void onBindViewHolder(AcustomViewHolder holder, int position) {
            holder.mTextView.setText(dataList.get(position).getTitle());
            holder.mTimeView.setText(dataList.get(position).getCreatedAt().getDate());
            //holder.contactID=dataList.get(position).getId();


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

