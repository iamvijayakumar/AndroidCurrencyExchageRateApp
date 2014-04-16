package com.vijay.currencyexchangerate;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.provider.ContactsContract.Contacts.Data;
import android.util.Log;

public class URlHelper {

	public static DataStructure getResult(String fromCurrency,
			String toCurrency, String amount) {
		DataStructure dataStr = new DataStructure();
		String url = "http://rate-exchange.appspot.com/currency?from="
				+ fromCurrency + "&to=" + toCurrency + "&q=" + amount;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		HttpResponse response = null;
		HttpEntity httpEntity;
		try {
			response = httpClient.execute(request);
			httpEntity = response.getEntity();
			String strResponse = EntityUtils.toString(httpEntity);
			//Log.e("hello", "response :: " + strResponse);
			try {
				JSONObject jsonObject = new JSONObject(strResponse);
				dataStr.setFromLabel("1 " + jsonObject.getString("from"));
				dataStr.setFromResult(jsonObject.getString("rate") + " "
						+ jsonObject.getString("to"));
				dataStr.setToLabel(amount + " " + jsonObject.getString("from"));
				dataStr.setToResult(String.valueOf(Math.round(Double.valueOf(jsonObject.getString("v")))) + " "
						+ jsonObject.getString("to"));

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// JSONObject jsonObject = new JSONObject("");
		return dataStr;

	}
}
