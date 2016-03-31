package examples.muhammed.com.socketchatandroid.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;

import examples.muhammed.com.socketchatandroid.R;
import examples.muhammed.com.socketchatandroid.models.ChatModel;
import examples.muhammed.com.socketchatandroid.views.CTextView;

/**
 * Created by thasneem on 28/3/16.
 */
public class ChatAdapter extends ArrayAdapter<ChatModel> {
    private Context mContext;
    private ArrayList<ChatModel> mChatModels;

    public ChatAdapter(Context context, ArrayList<ChatModel> chatModels) {
        super(context, R.layout.chat_row, chatModels);
        mContext = context;
        mChatModels = chatModels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chat_row, parent, false);
            viewHolder.mChatContainerLinearLayout = (LinearLayout) convertView.findViewById(R.id.singleMessageContainer);
            viewHolder.mChatTextView = (CTextView) convertView.findViewById(R.id.singleMessage);
            viewHolder.mChatTime = (CTextView) convertView.findViewById(R.id.timeTextView);
            viewHolder.mChatWindow = (LinearLayout) convertView.findViewById(R.id.chatWindow);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mChatTextView.setText(mChatModels.get(position).getMessage());
        viewHolder.mChatTime.setText(mChatModels.get(position).getTime());
        if (mChatModels.get(position).isMine()) {
            viewHolder.mChatWindow.setBackgroundResource(R.drawable.msg_out);
            viewHolder.mChatContainerLinearLayout.setGravity(Gravity.RIGHT);
        } else {
            viewHolder.mChatWindow.setBackgroundResource(R.drawable.msg_in);
            viewHolder.mChatContainerLinearLayout.setGravity(Gravity.LEFT);
        }
        return convertView;
    }


    public void addMessage(ChatModel model) {
        mChatModels.add(model);
        notifyDataSetChanged();
    }

    private static class ViewHolder {
        private CTextView mChatTextView;
        private LinearLayout mChatContainerLinearLayout;
        private LinearLayout mChatWindow;
        private CTextView mChatTime;

    }
}
