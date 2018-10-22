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
import com.example.android.chattous.HomeActivity;
import com.example.android.chattous.MainActivity;
import com.example.android.chattous.MessageActivity;
import com.example.android.chattous.Model.Chat;
import com.example.android.chattous.Model.User;
import com.example.android.chattous.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> users;
    private boolean isChat;
    private String lastMsg;
    public UserAdapter(Context context, List<User> users, boolean isChat){
        this.context = context;
        this.users = users;
        this.isChat = isChat;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.user_items,viewGroup,false);

        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final User user = users.get(i);
        viewHolder.username.setText(user.getUsername());
        if(user.getImageURL().equals("default")){
            viewHolder.profileImage.setImageResource(R.mipmap.ic_launcher);

        }else{
            Glide.with(context).load(user.getImageURL()).into(viewHolder.profileImage);
        }



        if(isChat){

            if(FirebaseAuth.getInstance().getCurrentUser() != null) {

                lastMessage(user.getId(), viewHolder.lastMessage);
            }

            if(user.getStatus().equals("online")){
                viewHolder.status_on.setVisibility(View.VISIBLE);
                viewHolder.status_off.setVisibility(View.GONE);
            }else{
                viewHolder.status_off.setVisibility(View.VISIBLE);
                viewHolder.status_on.setVisibility(View.GONE);
            }
        }else{
            viewHolder.lastMessage.setVisibility(View.GONE);
            viewHolder.status_on.setVisibility(View.GONE);
            viewHolder.status_off.setVisibility(View.GONE);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profileImage;
        private ImageView status_on;
        private ImageView status_off;
        private  TextView lastMessage;
        public ViewHolder(View itemView){
            super(itemView);
            username = itemView.findViewById(R.id.username);
            profileImage = itemView.findViewById(R.id.image_profile);
            status_off = itemView.findViewById(R.id.status_off);
            status_on = itemView.findViewById(R.id.status_on);
            lastMessage = itemView.findViewById(R.id.last_message);

        }

    }

    private void lastMessage(final String userid, final TextView lastmsg){
        lastMsg = "test";
        final String firebaseUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);



                    if(chat.getReciver().equals(firebaseUser) && chat.getSender().equals(userid)
                            || chat.getSender().equals(firebaseUser) && chat.getReciver().equals(userid)){
                        lastMsg = chat.getMessage();
                    }
                }
                switch (lastMsg){
                    case "default":
                        lastmsg.setText(R.string.nomessage);
                        break;
                        default:
                            lastmsg.setText(lastMsg);
                            break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
