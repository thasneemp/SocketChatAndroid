package examples.muhammed.com.socketchatandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.models.UserDetails;
import examples.muhammed.com.socketchatandroid.views.CTextView;

/**
 * Created by thasneem on 26/3/16.
 */
public class ContactsArrayAdapters extends RecyclerView.Adapter<ContactsArrayAdapters.BindViewHolder> {
    private ArrayList<UserDetails> mList;
    private Context mContext;
    private OnItemSelectedListener mListener;

    public ContactsArrayAdapters(Context context, ArrayList<UserDetails> userDetailsArrayList) {
        mContext = context;
        mList = userDetailsArrayList;
    }

    @Override
    public BindViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.contact_row, parent, false);
        return new BindViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BindViewHolder holder, final int position) {
        UserDetails details = mList.get(position);
        holder.mProfileNameTextView.setText(details.getName());
        holder.mLastMessageTextView.setText(details.getName());
        holder.mTimeTextView.setText("12/04/2016");
        holder.mMainRowRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemSelected(mList.get(position), position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class BindViewHolder extends RecyclerView.ViewHolder {
        protected CTextView mProfileNameTextView;
        protected CTextView mLastMessageTextView;
        protected CTextView mTimeTextView;
        protected ImageView mProfileImageView;
        protected RelativeLayout mMainRowRelativeLayout;

        public BindViewHolder(View itemView) {
            super(itemView);
            mProfileNameTextView = (CTextView) itemView.findViewById(R.id.profileNameTextView);
            mLastMessageTextView = (CTextView) itemView.findViewById(R.id.lastMessageTextView);
            mTimeTextView = (CTextView) itemView.findViewById(R.id.timeTextView);
            mProfileImageView = (ImageView) itemView.findViewById(R.id.profileImageView);
            mMainRowRelativeLayout = (RelativeLayout) itemView.findViewById(R.id.mainRowRelativeLayout);
        }
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        mListener = listener;
    }

    public interface OnItemSelectedListener {
        void onItemSelected(UserDetails details, int position);
    }
}
