package org.apoorva.test.instagram.activities;

import java.util.ArrayList;

import org.apoorva.test.instagram.utils.AsyncRequest;
import org.apoorva.test.instagram.utils.EntityResponse;
import org.apoorva.test.instagram.utils.ImageInfo;
import org.apoorva.test.instagram.utils.InstaGramUtils;
import org.apoorva.test.instagram.utils.InstagramConstants;
import org.apoorva.test.instagram.utils.ResponseListener;
import org.apoorva.test.instagram.utils.ThumbnailListAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SelfieListActivity extends BaseActivity implements
		OnItemClickListener, ResponseListener, OnScrollListener {

	private ListView mListView;
	private Context mCtContext;
	private ArrayList<ImageInfo> mThumbNailsList = new ArrayList<ImageInfo>();
	public static boolean isFirst = true;
	private ThumbnailListAdapter adapter;
	public static boolean isDownloading = false;
	private int visibleItem = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.selfie_list);
		mCtContext = getApplicationContext();
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setOnItemClickListener(this);
		mListView.setOnScrollListener(this);
		loadImages();
		isFirst = true;
	}

	private void loadImages() {
		if (InstaGramUtils.isNetworkAvaliable(mCtContext))
			downLoadImages();
		else {
			noNetwork();
		}
	}

	private void downLoadImages() {

		if (isDownloading == false) {
			isDownloading=true;
			AsyncRequest request = new AsyncRequest(this, mCtContext,
					InstagramConstants.REQUEST_FETCH_SELIEF, "");
			request.startResult();
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

		showMaxImage(arg2);
	}

	private void showMaxImage(int pos) {
		final ImageInfo info = (ImageInfo) adapter.getItem(pos);
		Intent inte = new Intent(this, MaxImage.class);
		inte.putExtra("url", info.imgMaxUrl);
		startActivity(inte);

	}

	@Override
	public void onStarted() {
		isDownloading = true;
		showDilaog();
	}

	@Override
	public void onNetworkError(int errorType) {
		isDownloading = false;
		dismissDialog();
		noNetwork();
	}

	@Override
	public void onFailure(EntityResponse erroMsg) {
		isDownloading = false;
		dismissDialog();
	}

	@Override
	public void onSuccess(EntityResponse obj) {
		dismissDialog();
		mThumbNailsList.addAll(InstaGramUtils.getThumbNailsList(obj.data));
		adapter = new ThumbnailListAdapter(getLayoutInflater(), mThumbNailsList);
		mListView.setAdapter(adapter);
		mListView.setSelection(visibleItem);
			if (mThumbNailsList.size() > 10)
			isFirst = false;
		isDownloading = false;
	}

	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

		if (arg1!=0&&isDownloading == false && (arg1 + arg2 + 1) == arg3 ) {
			 visibleItem = arg1 + arg2;
			downLoadImages();

		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	

	}

}
