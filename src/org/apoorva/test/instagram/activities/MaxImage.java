package org.apoorva.test.instagram.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apoorva.test.instagram.utils.InstaGramUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;

public class MaxImage extends BaseActivity {

	ImageView iv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_max_layout);

		iv = (ImageView) findViewById(R.id.ivZoom);
		String url = getIntent().getStringExtra("url");

		
		if(InstaGramUtils.isNetworkAvaliable(getApplicationContext()))
			new LoadBackground().execute(new String[] { url });
			else{
				noNetwork();
			}
		

	}

	private class LoadBackground extends AsyncTask<String, Void, Bitmap> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDilaog();
		}

		@Override
		protected Bitmap doInBackground(String... params) {

			try {
				Bitmap bitmap = BitmapFactory
						.decodeStream((InputStream) new URL(params[0])
								.getContent());
				return bitmap;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

			iv.setImageBitmap(result);
			
			dismissDialog();
		}
	}

	
	
}
