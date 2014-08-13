package org.apoorva.test.instagram.activities;

import org.apoorva.test.instagram.utils.InstagramConstants;

import android.app.ProgressDialog;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

public class BaseActivity extends ActionBarActivity {

	ProgressDialog dialog = null;

	protected void showDilaog() {
		dialog = new ProgressDialog(this);
		dialog.setMessage(InstagramConstants.MSG_WAIT);
		dialog.show();
	}

	protected void dismissDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	protected void noNetwork() {
		Toast.makeText(getApplicationContext(), InstagramConstants.MSG_NO_NETWORK,Toast.LENGTH_SHORT).show();
	}

}
