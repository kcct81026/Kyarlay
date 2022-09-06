package com.kyarlay.ayesunaing.holder;

import android.view.Display;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.NetworkImageView;
import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.custom_widget.CustomTextView;

public class VideoAllHolder extends RecyclerView.ViewHolder{

    public NetworkImageView imageView, pageImage;
    public CustomTextView title, body;
    public CardView cardView;
    public FrameLayout frame;
    public LinearLayout like, comment, share, reading_detail;
    public CustomTextView likes, comment_count;
    public ImageView like_image, more;
    public LinearLayout linearBottom;

    public VideoAllHolder(View itemView, Display display) {
        super(itemView);

        imageView       = (NetworkImageView) itemView.findViewById(R.id.image);
        title           = (CustomTextView) itemView.findViewById(R.id.title);
        body            = (CustomTextView) itemView.findViewById(R.id.body);
        frame           = (FrameLayout) itemView.findViewById(R.id.frame);
        pageImage       = (NetworkImageView) itemView.findViewById(R.id.page_image_url);
        cardView        = itemView.findViewById(R.id.videoCard);

    }
}
