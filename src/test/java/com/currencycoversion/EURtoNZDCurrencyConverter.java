package com.currencycoversion;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONObject;

public class EURtoNZDCurrencyConverter {
	public double eurtoNZD(double euros) throws IOException {

		URL url = new URL("https://openexchangerates.org/api/latest.json?app_id=YOUR_APP_ID&base=EUR&symbols=NZD");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		double NZdollars = Double.MIN_VALUE;
		int status = con.getResponseCode();
		if (status == 200) {
			Scanner responseScanner = new Scanner(con.getInputStream());
			String response = responseScanner.useDelimiter("\\A").next();
			responseScanner.close();
			JSONObject json = new JSONObject(response);
			double exchangeRate = json.getJSONObject("rates").getDouble("NZD");
			NZdollars = euros * exchangeRate;
			System.out.println(euros + " Euros = " + NZdollars + " NZ Dollars");
		} else {
			System.out.println("Error connecting to API: " + status);

		}
		return NZdollars;

	}
}
