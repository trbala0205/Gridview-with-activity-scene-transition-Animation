package com.bala.customsearchview;

import com.bala.customsearchview.model.ConstantObjects;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.ChangeImageTransform;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class CardRevealActivity extends AppCompatActivity {

	private float pixelDensity;
	ImageView imageView;
	TextView txt_desc, txt_leader_name;
    ImageButton imageButton;
    LinearLayout revealView, layoutButtons;
    Animation alphaAnimation, animZoomIn;
    boolean flag = true;
    int ListPosition;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		//setupTransition();
		super.onCreate(savedInstanceState);
        setContentView(R.layout.card_reveal_layout);
       
        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);            
		setSupportActionBar(mActionBarToolbar);
		getSupportActionBar().setTitle("Grid view");
		
        ListPosition = getIntent().getIntExtra("ListPosition", 0);
        pixelDensity = getResources().getDisplayMetrics().density;
        imageButton = (ImageButton) findViewById(R.id.launchTwitterAnimation);
        revealView = (LinearLayout) findViewById(R.id.linearView);
        layoutButtons = (LinearLayout) findViewById(R.id.layoutButtons);
        
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageResource(ConstantObjects.imageList[ListPosition]);
        
        txt_desc = (TextView)findViewById(R.id.txt_desc);
        txt_desc.setText(ConstantObjects.positionList[ListPosition]);
        
        txt_leader_name = (TextView)findViewById(R.id.txt_leader_name);
        txt_leader_name.setText(ConstantObjects.nameList[ListPosition]);
        
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        animZoomIn = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        animZoomIn.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				
			}
		});
        imageView.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("unchecked")
			@Override
			public void onClick(View v) {
				//imageView.startAnimation(animZoomIn);
				Intent intent = new Intent(CardRevealActivity.this, TransitionActivity.class);
				intent.putExtra("image_id", ListPosition);
				//ctivityOptionsCompat.makeSceneTransitionAnimation(CardRevealActivity.this, imageView, "Image").toBundle();
				ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(CardRevealActivity.this,
						// Now we provide a list of Pair items which contain the view we can transitioning
		                // from, and the name of the view it is transitioning to, in the launched activity
		                new Pair<View, String>(v.findViewById(R.id.imageView),
		                		TransitionActivity.VIEW_NAME_HEADER_IMAGE));
				ActivityCompat.startActivity(CardRevealActivity.this, intent, activityOptions.toBundle());
			}
		});
	}
	
	public static final int TRAN_TYPE_EXPLODE = 0;

    public static final int TRAN_TYPE_SLIDE = 1;
    public static final int TRAN_TYPE_FADE = 2;
    public static final int TRAN_TYPE_VIEW_SHARE = 3;
    
	private void setupTransition() {

        int transitionType = 3; //getIntent().getIntExtra(EXTRA_TRANSITION_TYPE, -1);

        Window window = getWindow();
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        window.setAllowEnterTransitionOverlap(true);

        switch (transitionType) {
            case TRAN_TYPE_EXPLODE:
                window.setEnterTransition(new Explode());
                window.setExitTransition(new Explode());
                break;
            case TRAN_TYPE_SLIDE:
                window.setEnterTransition(new Slide(Gravity.LEFT));
                window.setExitTransition(new Slide(Gravity.RIGHT));
                break;
            case TRAN_TYPE_FADE:
                window.setEnterTransition(new Fade(Fade.MODE_IN));
                window.setExitTransition(new Fade(Fade.MODE_OUT));
                break;
            case TRAN_TYPE_VIEW_SHARE:
                window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
                window.setSharedElementEnterTransition(new ChangeImageTransform());
                window.setSharedElementExitTransition(new ChangeImageTransform());
                break;
        }

    }
	
	public void launchTwitter(View view) {
		
		int x = imageView.getRight();
        int y = imageView.getBottom();
        x -= ((28 * pixelDensity) + (16 * pixelDensity));
        /*
        MARGIN_RIGHT = 16;
        FAB_BUTTON_RADIUS = 28;
        */
        int hypotenuse = (int) Math.hypot(imageView.getWidth(), imageView.getHeight());
        
        if (flag) {
        	imageButton.setBackgroundResource(R.drawable.rounded_cancel_button);
            imageButton.setImageResource(R.mipmap.image_cancel);
            
            FrameLayout.LayoutParams parameters = (FrameLayout.LayoutParams)revealView.getLayoutParams();
            parameters.height = imageView.getHeight();
            revealView.setLayoutParams(parameters);
            
            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, 0, hypotenuse);
            anim.setDuration(400);
            
            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    layoutButtons.setVisibility(View.VISIBLE);
                    layoutButtons.startAnimation(alphaAnimation);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            revealView.setVisibility(View.VISIBLE);
            anim.start();

            flag = false;
        }else {

            imageButton.setBackgroundResource(R.drawable.rounded_button);
            imageButton.setImageResource(R.mipmap.ic_like);

            Animator anim = ViewAnimationUtils.createCircularReveal(revealView, x, y, hypotenuse, 0);
            anim.setDuration(300);

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    revealView.setVisibility(View.GONE);
                    layoutButtons.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });

            anim.start();
            flag = true;
        }
	}
}
