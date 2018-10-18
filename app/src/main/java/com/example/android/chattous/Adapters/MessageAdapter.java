package com.example.android.chattous.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.chattous.MessageActivity;
import com.example.android.chattous.Model.Chat;
import com.example.android.chattous.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    public static final int MSG_TYPE_RIGHT = 1;
    public static final int MSG_TYPE_LEFT = 0;

private Context context;
private List<Chat> chats;
private String imageURL;

FirebaseUser firebaseUser;
public MessageAdapter(Context context, List<Chat> chats, String imageURL){
        this.context = context;
        this.chats = chats;
        this.imageURL = imageURL;
        }

@NonNull
@Override
public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

    if (i == MSG_TYPE_RIGHT) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, viewGroup, false);
        return new MessageAdapter.ViewHolder(view);
    }else{
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, viewGroup, false);
        return new MessageAdapter.ViewHolder(view);
    }
}

    @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Chat chat = chats.get(i);
        viewHolder.msgs.setText(chat.getMessage());
        if(imageURL.equals("default")){
            viewHolder.profileImage.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(imageURL).into(viewHolder.profileImage);
        }

    }

@Override
    public int getItemCount() {
        return chats.size();
    }

public class ViewHolder extends RecyclerView.ViewHolder{
    public TextView msgs;
    public ImageView profileImage;

    public ViewHolder(View itemView){
        super(itemView);
        msgs = itemView.findViewById(R.id.show_message);
        profileImage = itemView.findViewById(R.id.image_profile);
    }

}

    @Override
    public int getItemViewType(int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(chats.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}

