package masterung.androidthai.in.th.sunteenfriend.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import masterung.androidthai.in.th.sunteenfriend.R;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder>{

    private Context context;
    private ArrayList<String> nameStringArrayList,
            messageStringArrayList, iconStringArrayList;
    private LayoutInflater layoutInflater;

    public FriendAdapter(Context context,
                         ArrayList<String> nameStringArrayList,
                         ArrayList<String> messageStringArrayList,
                         ArrayList<String> iconStringArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.nameStringArrayList = nameStringArrayList;
        this.messageStringArrayList = messageStringArrayList;
        this.iconStringArrayList = iconStringArrayList;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = layoutInflater.inflate(R.layout.recyclerview_service, viewGroup, false);
        FriendViewHolder friendViewHolder = new FriendViewHolder(view);

        return friendViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder friendViewHolder, int i) {

        String nameString = nameStringArrayList.get(i);
        String message = messageStringArrayList.get(i);
        String iconString = iconStringArrayList.get(i);

        friendViewHolder.nameTextView.setText(nameString);
        friendViewHolder.messageTextView.setText(nameString);

        Picasso.get()
                .load(iconString)
                .resize(150, 150)
                .into(friendViewHolder.circleImageView);


    }

    @Override
    public int getItemCount() {
        return nameStringArrayList.size();
    }

    public class FriendViewHolder extends RecyclerView.ViewHolder{

        private TextView nameTextView, messageTextView;
        private CircleImageView circleImageView;

        public FriendViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.txtName);
            messageTextView = itemView.findViewById(R.id.txtMessage);
            circleImageView = itemView.findViewById(R.id.imvIcon);

        }
    }


}   // Main Class
