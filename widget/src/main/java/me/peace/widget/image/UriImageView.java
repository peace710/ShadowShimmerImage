package me.peace.widget.image;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

public class UriImageView extends SimpleDraweeView {
    public UriImageView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public UriImageView(Context context) {
        super(context);
    }

    public UriImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UriImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public UriImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setImage(String imageUrl,int placeHolderImage,int failureImage){
        if (!TextUtils.isEmpty(imageUrl)) {
            setImageURI(Uri.parse(imageUrl));
            getHierarchy().setFailureImage(failureImage);
        }
        getHierarchy().setPlaceholderImage(placeHolderImage);
    }

    public void setCircleImage(String imageUrl,int placeHolderImage,int failureImage,
                               int borderColor,float borderSize,float radius){
        setImage(imageUrl,placeHolderImage,failureImage,borderColor,borderSize,radius,true);
    }

    public void setCircleImage(int image,int borderColor,float borderSize,float radius){
        setImage(image,borderColor,borderSize,radius,true);
    }

    public void setRoundImage(String imageUrl,int placeHolderImage,int failureImage,
                               int borderColor,float borderSize,float radius){
        setImage(imageUrl,placeHolderImage,failureImage,borderColor,borderSize,radius,false);
    }

    public void setRoundImage(int image,int borderColor,float borderSize,float radius){
        setImage(image,borderColor,borderSize,radius,false);
    }

    private void setImage(String imageUrl,int placeHolderImage,int failureImage,
                          int borderColor,float borderSize,float radius,boolean isCircle){
        if (!TextUtils.isEmpty(imageUrl)) {
            setImageURI(Uri.parse(imageUrl));
            getHierarchy().setFailureImage(failureImage);
        }
        getHierarchy().setPlaceholderImage(placeHolderImage);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);
        roundingParams.setBorder(borderColor, borderSize);
        roundingParams.setRoundAsCircle(isCircle);
        getHierarchy().setRoundingParams(roundingParams);
    }

    public void setImage(int image,int borderColor,float borderSize,float radius,boolean isCircle){
        getHierarchy().setPlaceholderImage(image);
        RoundingParams roundingParams = RoundingParams.fromCornersRadius(radius);
        roundingParams.setBorder(borderColor, borderSize);
        roundingParams.setRoundAsCircle(isCircle);
        getHierarchy().setRoundingParams(roundingParams);
    }

    public void setImageGif(String imageUrl){
        DraweeController controller =
            Fresco.newDraweeControllerBuilder().setUri(imageUrl).setAutoPlayAnimations(true).build();
        setController(controller);
    }
}
