package com.sample;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.DefaultHttpClient;


public class Twitter {
	static final String URL_BOT="localhost:3000/post-tweet";

	public void send(String message){
		HttpClient httpclient = new DefaultHttpClient();

		URIBuilder builder = new URIBuilder();
		builder.setScheme("http").setHost(URL_BOT)
		.setParameter("tweet", message);
		URI uri;
		try {
			uri = builder.build();
			HttpGet httpget = new HttpGet(uri);
			httpclient.execute(httpget);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}