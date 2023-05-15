package com.currencycoversion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class EURtoCADCurrencyConverter {
	public double eurtoCAD(double euros) throws IOException {

		URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=YOUR_APP_ID&base=EUR&symbols=CAD");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		double CAD = Double.MIN_VALUE;
		int status = con.getResponseCode();
		if (status == 200) {
			Scanner responseScanner = new Scanner(con.getInputStream());
			String response = responseScanner.useDelimiter("\\A").next();
			responseScanner.close();
			JSONObject json = new JSONObject(response);
			double exchangeRate = json.getJSONObject("rates").getDouble("USD");
			CAD = euros * exchangeRate;
			System.out.println(euros + " Euros = " + CAD + " CA Dollars");
		} else {
			System.out.println("Error connecting to API: " + status);

		}
		return CAD;

	}
}
