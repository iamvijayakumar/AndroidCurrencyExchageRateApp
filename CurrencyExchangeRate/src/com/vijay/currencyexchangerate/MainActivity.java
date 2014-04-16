package com.vijay.currencyexchangerate;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	private static EditText fromText;
	private static EditText toEdittext, amount;
	private static TextView fromLabel, fromResult, toLable, toResult;
	private static LinearLayout progresslayout, resultLayout;
	private static Button checkRate;
	static DataStructure dataStr = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			ActionBar actionBar = getSupportActionBar();
			actionBar.setBackgroundDrawable(new ColorDrawable(Color
					.parseColor("#9933CC")));
		}
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			fromText = (EditText) rootView.findViewById(R.id.from_curencyrate);
			toEdittext = (EditText) rootView.findViewById(R.id.to_curencyrate);
			fromLabel = (TextView) rootView
					.findViewById(R.id.textview_fromlabel);
			fromResult = (TextView) rootView
					.findViewById(R.id.textview_fromresult);
			toLable = (TextView) rootView.findViewById(R.id.textview_tolabel);
			toResult = (TextView) rootView.findViewById(R.id.textview_toresult);
			progresslayout = (LinearLayout) rootView
					.findViewById(R.id.progress_layout);
			resultLayout = (LinearLayout) rootView
					.findViewById(R.id.result_layout);
			amount = (EditText) rootView.findViewById(R.id.curencyamount);
			checkRate = (Button) rootView.findViewById(R.id.checkRate);
			checkRate.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (!fromText.getText().toString().trim()
							.equalsIgnoreCase("")
							|| !toEdittext.getText().toString().trim()
									.equalsIgnoreCase("")
							|| !amount.getText().toString().trim()
									.equalsIgnoreCase("")) {
						progresslayout.setVisibility(View.VISIBLE);
						MyAsyncTask my = new MyAsyncTask();
						my.execute();
					} else {
						Toast.makeText(getActivity(),
								"Please enter all required field.",
								Toast.LENGTH_SHORT).show();
					}
				}
			});
			return rootView;
		}

		private class MyAsyncTask extends AsyncTask<String, Void, String> {
			String result;

			@Override
			protected String doInBackground(String... params) {
				result = "Success";

				dataStr = URlHelper.getResult(fromText.getText().toString(),
						toEdittext.getText().toString(), amount.getText()
								.toString());

				return result;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				if (dataStr != null) {
					progresslayout.setVisibility(View.GONE);
					resultLayout.setVisibility(View.VISIBLE);
					fromLabel.setText(dataStr.getFromLabel().toUpperCase());
					fromResult.setText(" = " + dataStr.getFromResult());
					toLable.setText(dataStr.getToLabel().toUpperCase());
					toResult.setText(" = " + dataStr.getToResult());
				} else {
					Toast.makeText(getActivity(), "Please Try Again.",
							Toast.LENGTH_SHORT).show();
				}

			}
		}
	}

}
