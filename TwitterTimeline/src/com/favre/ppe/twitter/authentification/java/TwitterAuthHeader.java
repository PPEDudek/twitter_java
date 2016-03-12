package com.favre.ppe.twitter.authentification.java;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import org.apache.commons.*;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Date;


public class TwitterAuthHeader {
	
	//Declaration ours token
	private static String oauthConsumerKey = "DoZ2bSvA5pAniS8kDxJJ8KGsu";
	private static String oauthConsumerSecret = "fDQon1kUoffYBzls52iOGeeSUKY6USBcSN0CMbLjCk4CGc7djs";
	private static String oauthToken = "706818055054233600-axPLau20pgEjl752BmJRsVx0Fzaa6ON";
	private static String oauthSecretToken = "TLQ5uK4KK2ZTWnpMA1I8lA6m5IyuoPdCiJWBiB16wNKpz";
	private static String oauthVersion = "1.0";
	private static String oauthSignatureMethod = "HMAC-SHA1";
	private String oauthSignature = "";
	private String resourceUrl = "https://api.twitter.com/1.1/statuses/user_timeline.json";
	
	public String uriEscape(String s) {
		try {
			return URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
		} catch(Exception e){
			return e.getMessage();
		}
	}
	
	public void getTwitterAuth() throws URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, IOException {
		Locale local = new Locale("fr", "FR");
		
		// Creation oauth_nonce
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauthNonce = uuid_string;
		
		// Creation oauth_timestamp
		Long timestamp = System.currentTimeMillis();
		timestamp = timestamp/1000;		
		String oauthTimeStamp = Long.toString(timestamp);
			
		// Creation baseFormat
		String baseFormat = "oauth_consumer_key=%s&oauth_nonce=%s&oauth_signature_method=%s&oauth_timestamp=%s&oauth_token=%s&oauth_version=%s";		
		String tempBaseFormat = String.format(baseFormat,
				oauthConsumerKey, 
				oauthNonce, 
				oauthSignatureMethod, 
				oauthTimeStamp, 
				oauthToken, 
				oauthVersion);
		
		String baseStringTemp = "";
		String baseString = baseStringTemp.concat(uriEscape("GET")).concat("&").concat(uriEscape(resourceUrl)).concat("&").concat(uriEscape((tempBaseFormat)));
		
		//Creation oauth_signature
		String compositeKeyTemp = "";
		String compositeKey = compositeKeyTemp.concat(uriEscape(oauthConsumerSecret)).concat("&").concat(uriEscape(oauthSecretToken));
		
		Mac m = Mac.getInstance("HmacSHA1");
		SecretKeySpec signinKey = new SecretKeySpec(compositeKey.getBytes("UTF-8"), "HmacSHA1");
	    m.init(signinKey);
		byte[] signature = m.doFinal(baseString.getBytes("UTF-8"));
		oauthSignature = new String(Base64.getEncoder().encode(signature)).trim();

		// Encoding variable
		String finalTimestamp = uriEscape(oauthTimeStamp);
		String finalNonce = uriEscape(oauthNonce);
		String finalSignatureMethod = uriEscape(oauthSignatureMethod);
		String finalSignature = uriEscape(oauthSignature);
		String finalToken = uriEscape(oauthToken);
	    String finalVersion = uriEscape(oauthVersion);
	    String finalConsumerKey = uriEscape(oauthConsumerKey);
		
	    // Creation headerFormat
	    String headerFormat = "OAuth oauth_nonce=%s, oauth_signature_method=%s, oauth_timestamp=%s, oauth_consumer_key=%s, oauth_token=%s, oauth_signature=%s, oauth_version=%s";

		String tempHeaderFormat = String.format(headerFormat, 
				finalNonce, 
				finalSignatureMethod, 
				finalTimestamp, 
				finalConsumerKey, 
				finalToken, 
				finalSignature, 
				finalVersion);
		try {
			HttpsURLConnection request = (HttpsURLConnection)new URL(this.resourceUrl).openConnection();
			request.setDoInput(true);
			request.setDoOutput(true);
			request.setRequestProperty("Authorization", tempHeaderFormat);
			request.setRequestMethod("GET");
			request.addRequestProperty("Content-type", "application/x-www-form-urlencoded");
			request.addRequestProperty("Host", "https://apit.twitter.com");
			
		
			System.out.println(request.getResponseCode());
			System.out.println(request.getResponseMessage());
			System.out.println(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}	