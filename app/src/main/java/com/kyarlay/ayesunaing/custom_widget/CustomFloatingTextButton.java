package com.kyarlay.ayesunaing.custom_widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.cardview.widget.CardView;

import com.kyarlay.ayesunaing.R;
import com.kyarlay.ayesunaing.data.DimensionUtils;


public class CustomFloatingTextButton extends FrameLayout {
    public CardView container;
    public ImageView leftIconView;
    public ImageView rightIconView;
    public CustomTextView titleView;
    public String title;
    public int titleColor;
    public Drawable leftIcon;
    public Drawable rightIcon;
    public int background;
    public LinearLayout layout_container;

    public CustomFloatingTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.inflateLayout(context);
        this.initAttributes(attrs);
        this.initView();
    }

    public void setTitle(String newTitle) {
        this.title =  newTitle;
        if (newTitle != null && newTitle.length() != 0) {
            this.titleView.setVisibility(VISIBLE);
        } else {
            this.titleView.setVisibility(GONE);
        }

        this.titleView.setText(newTitle);
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitleColor(@ColorInt int color) {
        this.titleColor = color;
        this.titleView.setTextColor(color);
    }

    @ColorInt
    public int getTitleColor() {
        return this.titleColor;
    }

    public void setBackgroundColor(@ColorInt int color) {
        this.background = color;
        this.container.setCardBackgroundColor(color);
    }

    @ColorInt
    public int getBackgroundColor() {
        return this.background;
    }

    public void setLeftIconDrawable(Drawable drawable) {
        this.leftIcon = drawable;
        if (drawable != null) {
            this.leftIconView.setVisibility(VISIBLE);
            this.leftIconView.setImageDrawable(drawable);
        } else {
            this.leftIconView.setVisibility(GONE);
        }

    }

    public void setRightIconDrawable(Drawable drawable) {
        this.rightIcon = drawable;
        if (drawable != null) {
            this.rightIconView.setVisibility(VISIBLE);
            this.rightIconView.setImageDrawable(drawable);
        } else {
            this.rightIconView.setVisibility(GONE);
        }

    }

    public Drawable getLeftIconDrawable() {
        return this.leftIcon;
    }

    public Drawable getRightIconDrawable() {
        return this.rightIcon;
    }

    public void setOnClickListener(OnClickListener listener) {
        this.container.setOnClickListener(listener);
    }

    public boolean hasOnClickListeners() {
        return this.container.hasOnClickListeners();
    }

    public void setOnLongClickListener(OnLongClickListener listener) {
        this.container.setOnLongClickListener(listener);
    }

    private void inflateLayout(Context context) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.widget_floating_text_button, this, true);
        this.container = (CardView)view.findViewById(R.id.layout_button_container);
        this.leftIconView = (ImageView)view.findViewById(R.id.layout_button_image_left);
        this.rightIconView = (ImageView)view.findViewById(R.id.layout_button_image_right);
        this.titleView = (CustomTextView) view.findViewById(R.id.layout_button_text);
        this.layout_container   = (LinearLayout) view.findViewById(R.id.layout_container);
    }

    private void initAttributes(AttributeSet attrs) {

        TypedArray styleable = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.FloatingTextButton,
                0,
                0
        );

        title = styleable.getString(R.styleable.FloatingTextButton_floating_title);
        titleColor = styleable.getColor(R.styleable.FloatingTextButton_floating_title_color, Color.WHITE);
        leftIcon = styleable.getDrawable(R.styleable.FloatingTextButton_floating_left_icon);
        rightIcon = styleable.getDrawable(R.styleable.FloatingTextButton_floating_right_icon);
        background = styleable.getColor(R.styleable.FloatingTextButton_floating_background_color, getResources().getColor(R.color.background));

        styleable.recycle();

    }

    private void initView() {

        this.setTitle(this.title);
        this.setTitleColor(this.titleColor);
        this.setLeftIconDrawable(this.leftIcon);
        this.setRightIconDrawable(this.rightIcon);
        this.setBackgroundColor(this.background);
        this.container.setContentPadding(this.getHorizontalPaddingValue(10), this.getVerticalPaddingValue(8), this.getHorizontalPaddingValue(10), this.getVerticalPaddingValue(8));
        this.initViewRadius();
    }

    private void initViewRadius() {
        this.container.post(new Runnable() {
            public void run() {
                //CustomFloatingTextButton.this.container.setRadius((float)(CustomFloatingTextButton.this.container.getHeight() / 2));
                //  CustomFloatingTextButton.this.layout_container.getLayoutParams().width = ( getDisplay().getWidth() * 9 ) /10 ;
                //CustomFloatingTextButton.this.container.getLayoutParams().width = ( getDisplay().getWidth() * 9 ) /10 ;
                // CustomFloatingTextButton.this.titleView.getLayoutParams().width = ( getDisplay().getWidth() * 7 ) /10;
                // CustomFloatingTextButton.this.container.setRadius((float) CustomFloatingTextButton.this.container.getHeight());
            }
        });
    }

    private int getVerticalPaddingValue(int dp) {
        return Build.VERSION.SDK_INT < 21 ? DimensionUtils.dpToPx(this.getContext(), dp) / 4 : DimensionUtils.dpToPx(this.getContext(), dp);
    }

    private int getHorizontalPaddingValue(int dp) {
        return Build.VERSION.SDK_INT < 21 ? DimensionUtils.dpToPx(this.getContext(), dp) / 2 : DimensionUtils.dpToPx(this.getContext(), dp);
    }
}