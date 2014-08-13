package org.apoorva.test.instagram.utils;


import java.util.List;

import org.apoorva.test.instagram.activities.R;
import org.apoorva.test.instagram.app.AppController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class ThumbnailListAdapter extends BaseAdapter {	
	private LayoutInflater mInflater;
	private List<ImageInfo> feedItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	

	public ThumbnailListAdapter(LayoutInflater inflater, List<ImageInfo> feedItems) {
		mInflater=inflater;
		this.feedItems = feedItems;
	}

	@Override
	public int getCount() {
		return feedItems.size();
	}

	@Override
	public Object getItem(int location) {
		return feedItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null)
			convertView = mInflater.inflate(R.layout.selfie_thumbnail,null);
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		
		NetworkImageView profilePic = (NetworkImageView) convertView.findViewById(R.id.image_selfie);
		ImageInfo item = feedItems.get(position);
		profilePic.setImageUrl(item.imgThumbNailUrl, imageLoader);
		return convertView;
	}

}
