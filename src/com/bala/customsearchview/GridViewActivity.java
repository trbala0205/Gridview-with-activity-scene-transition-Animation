package com.bala.customsearchview;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bala.customsearchview.adapter.RecyclerViewAdapter;
import com.bala.customsearchview.model.ViewModel;

public class GridViewActivity extends AppCompatActivity implements RecyclerViewAdapter.OnItemClickListener{

	private static List<ViewModel> items = new ArrayList<>();
	private int ListPosition = 0;
	boolean isSingleGrid = false;
	private Button btn_grid;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview_layout);
        
        if(items.size() == 0){
	        items.add(new ViewModel("Tim Cook", "", getResources().getIdentifier("time_cook", "drawable", ""+getPackageName()))); 
	    	items.add(new ViewModel("Mario Draghi", "", getResources().getIdentifier("mario_draghi", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Xi Jinping", "", getResources().getIdentifier("xi_jinping", "drawable", ""+getPackageName())));  
	   		items.add(new ViewModel("Pope Francis", "", getResources().getIdentifier("pope_francis", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Narendra Modi", "", getResources().getIdentifier("narendra_modi", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Taylor Swift", "", getResources().getIdentifier("taylor_swift", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Joanne Liu", "", getResources().getIdentifier("joanne_liu", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("John Roberts Jr.", "", getResources().getIdentifier("john_roberts", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Mary Barra", "", getResources().getIdentifier("mary_barra", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Joshua Wong", "", getResources().getIdentifier("joshua_wong", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("James Comey", "", getResources().getIdentifier("james_comey", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Mark Carney", "", getResources().getIdentifier("mark_carney", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Ellen Johnson Sirleaf", "", getResources().getIdentifier("ellen_johnson_sirleaf", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Howard Schultz", "", getResources().getIdentifier("howard_schultz", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Helena Morrissey", "", getResources().getIdentifier("helena_morrissey", "drawable", ""+getPackageName()))); 
	   		items.add(new ViewModel("Mark Zuckerberg", "", getResources().getIdentifier("mark_zuckerberg", "drawable", ""+getPackageName()))); 
        }
   		initRecyclerView();
        
	}
	
	public RecyclerView recyclerView;
	public RecyclerViewAdapter adapter = null;
	private void initRecyclerView() {
		
		Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar);            
		setSupportActionBar(mActionBarToolbar);
		getSupportActionBar().setTitle("Grid view");
		
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecyclerViewAdapter(items);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        
        btn_grid = (Button)findViewById(R.id.btn_grid);
        btn_grid.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(!isSingleGrid){
					recyclerView.setLayoutManager(new GridLayoutManager(GridViewActivity.this, 1));
					adapter.notifyDataSetChanged();
					isSingleGrid = true;
					btn_grid.setText("MULTI GRID");
				}else{
					recyclerView.setLayoutManager(new GridLayoutManager(GridViewActivity.this, 2));
					adapter.notifyDataSetChanged();
					isSingleGrid = false;
					btn_grid.setText("SINGLE GRID");
				}
					
			}
		});
    }

	@SuppressWarnings("unchecked")
	@Override
	public void onItemClick(View view, int position) {
		Intent intent = new Intent(GridViewActivity.this, TransitionActivity.class);
		intent.putExtra("image_id", position);
		//ctivityOptionsCompat.makeSceneTransitionAnimation(CardRevealActivity.this, imageView, "Image").toBundle();
		ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(GridViewActivity.this,
				// Now we provide a list of Pair items which contain the view we can transitioning
                // from, and the name of the view it is transitioning to, in the launched activity
                new Pair<View, String>(view.findViewById(R.id.image),
                		TransitionActivity.VIEW_NAME_HEADER_IMAGE));
		ActivityCompat.startActivity(GridViewActivity.this, intent, activityOptions.toBundle());
	}
}
