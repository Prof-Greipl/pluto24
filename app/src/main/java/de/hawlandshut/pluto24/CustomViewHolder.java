package de.hawlandshut.pluto24;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public final TextView mLine1;
    public final TextView mLine2;

    public CustomViewHolder(View view) {
        super(view);
        Log.d("VIEW XX", "Viewholder erzeugt ");
        mLine1 = (TextView) view.findViewById(R.id.post_view_line1);
        mLine2 = (TextView) view.findViewById(R.id.post_view_line2);
    }
}