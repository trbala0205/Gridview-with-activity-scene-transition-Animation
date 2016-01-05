package com.bala.customsearchview.adapter;

import java.util.ArrayList;

import com.bala.customsearchview.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ListviewAdapter extends ArrayAdapter<String> {
	 
    Context context;
    private ArrayList<Integer> imgName;
    private ArrayList<String> nameDesc;
    private ArrayList<String> nameList;
 
    public ListviewAdapter(Context context, int resourceId, ArrayList<Integer> imgName, ArrayList<String> nameList, ArrayList<String> nameDesc) {
        super(context, resourceId, nameList);
        this.context = context;
        this.imgName = imgName;
        this.nameList = nameList;
        this.nameDesc = nameDesc;
    }
     
    public ArrayList<String> getData()
    {
    	return nameList;
    }
    
    /*private view holder class*/
    private class ViewHolder {
    	ImageView imgName;
        TextView txtName;
        TextView txtDesc;
    }
     
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        //ParameterDataUnitInfo rowItem = getItem(position);
         
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listview_item, null);
            holder = new ViewHolder();
            holder.imgName = (ImageView) convertView.findViewById(R.id.img_name);
            holder.txtName = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.txt_desc);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
                 
        holder.txtName.setText(nameList.get(position));
        holder.txtDesc.setText(nameDesc.get(position));
        holder.imgName.setBackgroundResource(imgName.get(position));
        //holder.txtDataUnit.setText(rowItem.getDataUnit());
         
        return convertView;
    }
}
