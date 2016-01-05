package com.bala.customsearchview;

import java.util.ArrayList;
import java.util.Arrays;

import com.bala.customsearchview.adapter.ListviewAdapter;
import com.bala.customsearchview.model.ConstantObjects;
import com.bala.materialsearchview.MaterialSearchView;

import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private MaterialSearchView searchView;
    private ListView listview;
    private ListviewAdapter adapter;
    private ArrayList<String> nameList_temp, positionList_temp;
    private ArrayList<Integer> image_temp;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(true);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_name_suggestions));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(findViewById(R.id.container), "" + query, Snackbar.LENGTH_LONG).show();
                nameList_temp.clear();
                positionList_temp.clear();
                image_temp.clear();
                for(int i=0;i<ConstantObjects.nameList.length;i++)
                {	
                	if(query.equals("") || ConstantObjects.nameList[i].contains(query))
                	{
                		nameList_temp.add(ConstantObjects.nameList[i]);
                		positionList_temp.add(ConstantObjects.positionList[i]);
                		image_temp.add(ConstantObjects.imageList[i]);
                	}
                }
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do something
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do something
            }

            @Override
            public void onSearchViewClosed() {
                //Do something
            }
        });
        
        listview = (ListView)findViewById(R.id.listview);
        
        nameList_temp = new ArrayList<String>( Arrays.asList(ConstantObjects.nameList));
        positionList_temp = new ArrayList<String>( Arrays.asList(ConstantObjects.positionList));
        image_temp = new ArrayList<Integer>( Arrays.asList(ConstantObjects.imageList));
        adapter = new ListviewAdapter(getApplicationContext(), R.layout.listview_item, image_temp, nameList_temp, positionList_temp);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MainActivity.this, CardRevealActivity.class);
				intent.putExtra("ListPosition", position);
				startActivity(intent);
			}
		});
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) 
            {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd))
                    searchView.setQuery(searchWrd, false);
            }
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_search) {
			return true;
		}else if(id == R.id.action_gridview){
			startActivity(new Intent(MainActivity.this, GridViewActivity.class));
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
    public void onBackPressed() {
        if (searchView.isSearchOpen())
            searchView.closeSearch();
        else
            super.onBackPressed();
    }
}
