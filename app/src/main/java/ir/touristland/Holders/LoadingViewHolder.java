package ir.touristland.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import ir.touristland.R;

public class LoadingViewHolder extends RecyclerView.ViewHolder {
    public ProgressBar progressBar;

    public LoadingViewHolder(View itemView) {
        super(itemView);
        progressBar = itemView.findViewById(R.id.progressBar1);
    }

}