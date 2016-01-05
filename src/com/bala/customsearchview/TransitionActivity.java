package com.bala.customsearchview;

import com.bala.customsearchview.model.ConstantObjects;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.graphics.Palette.PaletteAsyncListener;
import android.transition.Transition;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

@SuppressLint("NewApi")
public class TransitionActivity extends AppCompatActivity{

	// View name of the header image. Used for activity scene transitions
    public static final String VIEW_NAME_HEADER_IMAGE = "detail:header:image";
    
	private ImageView imageView;
	private int image_id;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transition_layout);
        
        //supportPostponeEnterTransition();
        
        imageView = (ImageView)findViewById(R.id.transition_imageView);
        image_id = getIntent().getIntExtra("image_id", 0);
        //imageView.setImageResource(ConstantObjects.imageList[image_id]);
        
        ViewCompat.setTransitionName(imageView, VIEW_NAME_HEADER_IMAGE);
        
        /*Picasso.with(this).load(ConstantObjects.imageList[image_id]).into(imageView, new Callback() {
            @SuppressWarnings("static-access")
			@Override public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                Palette.generate(bitmap).generateAsync(bitmap, new PaletteAsyncListener() {
					
					@Override
					public void onGenerated(Palette arg0) {
						supportStartPostponedEnterTransition();
					}
				});
            }

            @Override public void onError() {

            }
        });*/
        loadItem();
	}
	
	private void loadItem() {
        // Set the title TextView to the item's name and author

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // && addTransitionListener()
            // If we're running on Lollipop and we have added a listener to the shared element
            // transition, load the thumbnail. The listener will load the full-size image when
            // the transition is complete.
            loadThumbnail();
        } else {
            // If all other cases we should just load the full-size image now
            loadFullSizeImage();
        }
    }

    /**
     * Load the item's thumbnail image into our {@link ImageView}.
     */
    private void loadThumbnail() {
        Picasso.with(imageView.getContext())
                .load(ConstantObjects.imageList[image_id])
                .noFade()
                .into(imageView);
    }

    /**
     * Load the item's full-size image into our {@link ImageView}.
     */
    private void loadFullSizeImage() {
        Picasso.with(imageView.getContext())
                .load(ConstantObjects.imageList[image_id])
                .noFade()
                .into(imageView);
    }
    
	private boolean addTransitionListener() {
        final Transition transition = getWindow().getSharedElementEnterTransition();

        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(new Transition.TransitionListener() {
                @Override
                public void onTransitionEnd(Transition transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage();

                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionStart(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionCancel(Transition transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this);
                }

                @Override
                public void onTransitionPause(Transition transition) {
                    // No-op
                }

                @Override
                public void onTransitionResume(Transition transition) {
                    // No-op
                }
            });
            return true;
        }

        // If we reach here then we have not added a listener
        return false;
    }
}
