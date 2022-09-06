package com.kyarlay.ayesunaing.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kyarlay.ayesunaing.R;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        /*
        * This is Drawable corner creation
        *
        */
        /*
          float[] outerRadii = new float[]{0,0,0,0,0,0,150,150};
        float[] innerRadii = new float[]{0,0,0,0,0,0,0,0};

        // Set the shape border
        ShapeDrawable borderDrawable = new ShapeDrawable(new RoundRectShape(
                outerRadii,
                null, // Inset
                innerRadii
        ));
        borderDrawable.getPaint().setColor(Color.TRANSPARENT);
        borderDrawable.getPaint().setStyle(Paint.Style.FILL);
        // Define the border width
        borderDrawable.setPadding(0,0,0,0);



        // Set the shape background
        ShapeDrawable backgroundShape = new ShapeDrawable(new RoundRectShape(
                outerRadii,
                null,
                innerRadii
        ));
        backgroundShape.getPaint().setColor(activity.getResources().getColor(R.color.colorSplashScreen)); // background color
        backgroundShape.getPaint().setStyle(Paint.Style.FILL); // Define background
        backgroundShape.getPaint().setAntiAlias(true);

        // Initialize an array of drawables
        Drawable[] drawables = new Drawable[]{
                borderDrawable,
                backgroundShape
        };
        // Set shape padding
        backgroundShape.setPadding(0,0,0,0);



        // Initialize a layer drawable object
        LayerDrawable layerDrawable = new LayerDrawable(drawables);

        // Finally, set the button background drawable
        textTopCorner.setBackground(layerDrawable);
         */
    }
}
