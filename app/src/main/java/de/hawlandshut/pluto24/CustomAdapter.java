package de.hawlandshut.pluto24;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hawlandshut.pluto24.Model.Post;

public class CustomAdapter  extends RecyclerView.Adapter<CustomViewHolder> {

    // Initialisiere leere Postlist
    ArrayList<Post> mPostList = new ArrayList<Post>();

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.post_view, parent, false);
        return new CustomViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Post post = mPostList.get( position );
        holder.mLine1.setText( post.email + " (" +  post.createdAt +")");
        holder.mLine2.setText( post.body );
    }

    @Override
    public int getItemCount() {
        return mPostList.size();
    }
}
