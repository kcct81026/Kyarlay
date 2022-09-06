package com.kyarlay.ayesunaing.holder;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class TextHolder extends RecyclerView.ViewHolder {

    public CustomTextView title, txtWatch, body;
    public FrameLayout imageframe;
    public LinearLayout layout_image, layout_watch;
    public ProgressBar previewProgress;
    public NetworkImageView image;



    public TextHolder(@NonNull View itemView) {
        super(itemView);

        title = itemView.findViewById(R.id.title);
        txtWatch = itemView.findViewById(R.id.txtWatch);
        body = itemView.findViewById(R.id.body);
        layout_image = itemView.findViewById(R.id.layout_image);
        imageframe = itemView.findViewById(R.id.imageframe);
        layout_watch = itemView.findViewById(R.id.layout_watch);
        previewProgress = itemView.findViewById(R.id.previewProgress);
        image = itemView.findViewById(R.id.image);
    }
}
