package com.kyarlay.ayesunaing.custom_widget;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ComposeShader;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.widget.ImageView.ScaleType;

public class StreamDrawable  extends Drawable {

    /** The corner radius. */
    private final float mCornerRadius;

    /** The bitmap shader. */
    private final BitmapShader mBitmapShader;

    /** The paint. */
    private final Paint mPaint;

    /** The margin. */
    private final int mMargin;

    /** The is vignette. */
    private boolean isVignette;

    /** The scale type. */
    private ScaleType mScaleType;

    /** The shader matrix. */
    private final Matrix mShaderMatrix = new Matrix();

    /** The bounds. */
    private final RectF mBounds = new RectF();

    /** The drawable rect. */
    private final RectF mDrawableRect = new RectF();

    /** The bitmap rect. */
    private final RectF mBitmapRect = new RectF();

    /** The border rect. */
    private final RectF mBorderRect = new RectF();

    /** The bitmap width. */
    private final int mBitmapWidth;

    /** The bitmap height. */
    private final int mBitmapHeight;

    /**
     * Instantiates a new stream drawable.
     *
     * @param bitmap the bitmap
     * @param cornerRadius the corner radius
     * @param margin the margin
     * @param isVignette true for shadow on image
     * @param scaleType the ScaleType
     * @see android.widget.ImageView.ScaleType
     */
    StreamDrawable(Bitmap bitmap, float cornerRadius, int margin, boolean isVignette, ScaleType scaleType) {
        if(scaleType == null)
            mScaleType = ScaleType.FIT_CENTER;
        mScaleType = scaleType;
        mBitmapRect.set(0, 0, bitmap.getWidth(), bitmap.getHeight());
        mBitmapHeight = bitmap.getHeight();
        mBitmapWidth = bitmap.getWidth();
        mCornerRadius = cornerRadius;
        this.isVignette = isVignette;
        mBitmapShader = new BitmapShader(bitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(mBitmapShader);
        mMargin = margin;
    }



    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#onBoundsChange(android.graphics.Rect)
     */
    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        mBounds.set(bounds);
        mBorderRect.set(bounds);
        mDrawableRect.set(0 + mMargin, 0 + mMargin, mBorderRect.width() - mMargin, mBorderRect.height() - mMargin);
        float scale;
        float dx;
        float dy;
        switch (mScaleType) {
            case FIT_CENTER:

                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.CENTER);
                mShaderMatrix.mapRect(mBorderRect);
                mDrawableRect.set(mBorderRect.left + mMargin, mBorderRect.top + mMargin, mBorderRect.right - mMargin, mBorderRect.bottom - mMargin);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect, Matrix.ScaleToFit.FILL);
                break;

            case CENTER:

                mBorderRect.set(mBounds);
                mDrawableRect.set(0 + mMargin, 0 + mMargin, mBorderRect.width() - mMargin, mBorderRect.height() - mMargin);
                mShaderMatrix.set(null);
                mShaderMatrix.setTranslate((int) ((mDrawableRect.width() - mBitmapWidth) * 0.5f + 0.5f), (int) ((mDrawableRect.height() - mBitmapHeight) * 0.5f + 0.5f));
                break;

            case CENTER_CROP:

                mBorderRect.set(mBounds);
                mDrawableRect.set(0 + mMargin, 0 + mMargin, mBorderRect.width() - mMargin, mBorderRect.height() - mMargin);
                mShaderMatrix.set(null);
                dx = 0;
                dy = 0;
                if (mBitmapWidth * mDrawableRect.height() > mDrawableRect.width() * mBitmapHeight) {
                    scale = (float) mDrawableRect.height() / (float) mBitmapHeight;
                    dx = (mDrawableRect.width() - mBitmapWidth * scale) * 0.5f;
                } else {
                    scale = (float) mDrawableRect.width() / (float) mBitmapWidth;
                    dy = (mDrawableRect.height() - mBitmapHeight * scale) * 0.5f;
                }

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));

                break;

            case CENTER_INSIDE:

                mShaderMatrix.set(null);
                if (mBitmapWidth <= mBounds.width() && mBitmapHeight <= mBounds.height()) {
                    scale = 1.0f;
                } else {
                    scale = Math.min((float) mBounds.width() / (float) mBitmapWidth,
                            (float) mBounds.height() / (float) mBitmapHeight);
                }

                dx = (int) ((mBounds.width() - mBitmapWidth * scale) * 0.5f + 0.5f);
                dy = (int) ((mBounds.height() - mBitmapHeight * scale) * 0.5f + 0.5f);

                mShaderMatrix.setScale(scale, scale);
                mShaderMatrix.postTranslate(dx, dy);

                mBorderRect.set(mBitmapRect);
                mShaderMatrix.mapRect(mBorderRect);
                mDrawableRect.set(mBorderRect.left + mMargin, mBorderRect.top + mMargin, mBorderRect.right - mMargin, mBorderRect.bottom - mMargin);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_END:

                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.END);
                mShaderMatrix.mapRect(mBorderRect);
                mDrawableRect.set(mBorderRect.left + mMargin, mBorderRect.top + mMargin, mBorderRect.right - mMargin, mBorderRect.bottom - mMargin);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_START:

                mBorderRect.set(mBitmapRect);
                mShaderMatrix.setRectToRect(mBitmapRect, mBounds, Matrix.ScaleToFit.START);
                mShaderMatrix.mapRect(mBorderRect);
                mDrawableRect.set(mBorderRect.left + mMargin, mBorderRect.top + mMargin, mBorderRect.right - mMargin, mBorderRect.bottom - mMargin);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect, Matrix.ScaleToFit.FILL);
                break;

            case FIT_XY:

                mBorderRect.set(mBounds);
                mDrawableRect.set(0 + mMargin, 0 + mMargin, mBorderRect.width() - mMargin, mBorderRect.height() - mMargin);
                mShaderMatrix.set(null);
                mShaderMatrix.setRectToRect(mBitmapRect, mDrawableRect, Matrix.ScaleToFit.FILL);
                break;

            case MATRIX:
                break;

            default:

                mDrawableRect.set(mMargin, mMargin, bounds.width() - mMargin, bounds.height() - mMargin);
                break;
        }

        mBitmapShader.setLocalMatrix(mShaderMatrix);

        if (isVignette) {
            RadialGradient vignette = new RadialGradient(
                    mDrawableRect.centerX(), mDrawableRect.centerY() * 1.0f / 0.7f, mDrawableRect.centerX() * 1.9f,
                    new int[] { 0, 0, 0x7f000000 }, new float[] { 0.0f, 0.7f, 1.0f },
                    Shader.TileMode.CLAMP);
            Matrix oval = new Matrix();
            oval.setScale(1.0f, 0.7f);
            vignette.setLocalMatrix(oval);
            mPaint.setShader(
                    new ComposeShader(mBitmapShader, vignette, PorterDuff.Mode.SRC_OVER));
        }
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas)
     */
    @Override
    public void draw(Canvas canvas) {


        canvas.drawRoundRect(mDrawableRect, 0, mCornerRadius, mPaint);
       // canvas.drawRoundRect(new RectF(0, 100, 100, 300), mCornerRadius, mCornerRadius, mPaint);

    }


    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#getOpacity()
     */
    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#setAlpha(int)
     */
    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    /* (non-Javadoc)
     * @see android.graphics.drawable.Drawable#setColorFilter(android.graphics.ColorFilter)
     */
    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }
}
