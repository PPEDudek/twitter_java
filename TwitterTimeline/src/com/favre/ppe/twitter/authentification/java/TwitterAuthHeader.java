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

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.org.apache.xml.internal.utils.URI;

import java.util.Date;


public class TwitterAuthHeader {
	public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	
	//Declaration ours token
	static String oauthConsumerKey = "DoZ2bSvA5pAniS8kDxJJ8KGsu";
	static String oauthConsumerSecret = "fDQon1kUoffYBzls52iOGeeSUKY6USBcSN0CMbLjCk4CGc7djs";
	static String oauthToken = "706818055054233600-axPLau20pgEjl752BmJRsVx0Fzaa6ON";
	static String oauthTokenSecret = "TLQ5uK4KK2ZTWnpMA1I8lA6m5IyuoPdCiJWBiB16wNKpz";
	static String oauthVersion = "1.0";
	static String oauthSignatureMethod = "HMAC-SHA1";
	

	public String encoding(String value) {
	  String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        StringBuilder buf = new StringBuilder(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && (i + 1) < encoded.length()
                    && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
        }
        return buf.toString();
	}
	
	private static String createSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException 
	{
	    SecretKey secretKey = null;

	    byte[] keyBytes = keyString.getBytes();
	    secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

	    Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(secretKey);

	    byte[] text = baseString.getBytes();

	    return new String(Base64.encode(mac.doFinal(text))).trim();
	}
	
	public String getTwitterAuthHeader() throws URISyntaxException, NoSuchAlgorithmException, InvalidKeyException, IOException {
		Locale local = new Locale("fr", "FR");
		
		// Creation oauth_nonce
/*		DateFormat df = new SimpleDateFormat(DATE_FORMAT_NOW);
		Date today = Calendar.getInstance().getTime();		
		String reportDate = df.format(today);
		byte[] reportDateByte = reportDate.getBytes(StandardCharsets.UTF_8);
	
		String oauthNonce = Base64.encode(reportDateByte);
		oauthNonce = oauthNonce.toString();
*/
		
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauthNonce = uuid_string;
		
		// Creation oauth_timestamp
		long timestamp = System.currentTimeMillis();
		timestamp = timestamp/1000;		
		String oauthTimeStamp = Long.toString(timestamp);
		
		//Creation url user_timeline
		String resourceUrl = "https://api.twitter.com/1.1/statuses/user_timeline.json";
	
		// Generation an Encypted oauth_signature
		String baseFormat = "oauth_consumer_key=%s&oauth_nonce=%s&oauth_signature_method=%s"
							+"&oauth_timestamp=%s&oauth_token=%s&oauth_version=%s";		
		String tempBaseString = String.format(baseFormat, oauthConsumerKey, oauthNonce, 
											oauthSignatureMethod, oauthTimeStamp, oauthToken, oauthVersion);
		
		String baseStringTemp = "";
		String baseString = baseStringTemp.concat("GET&")
				.concat(StringEscapeUtils.escapeJava(resourceUrl))
				.concat("&")
				.concat(StringEscapeUtils.escapeJava(tempBaseString));
		
		//
		String finalConsumerSecret = StringEscapeUtils.escapeJava(oauthConsumerSecret);
	
		String finalTokenSecret = StringEscapeUtils.escapeJava(oauthTokenSecret);
		String finalConsumer = StringEscapeUtils.escapeJava(oauthConsumerKey);
		
		String compositeKeyTemp = "";
		String compositeKey = compositeKeyTemp.concat(finalConsumerSecret).concat(finalTokenSecret);
		
		String oauthSignature;
		
		SecretKeySpec signinKey = new SecretKeySpec(compositeKey.getBytes(), "HmacSHA1");
		Mac m = Mac.getInstance(signinKey.getAlgorithm());
	    m.init(signinKey);
		byte[] signature = m.doFinal(baseString.getBytes());
		oauthSignature = signature.toString();
		oauthSignature = encode(oauthSignature);
		
		// Generate Authentification header
	    String finalNonce = encode(oauthNonce);
	    finalNonce = StringEscapeUtils.escapeJava(oauthNonce);
	    String finalSignatureMethod = encode(oauthSignatureMethod);
	    finalSignatureMethod = StringEscapeUtils.escapeJava(oauthSignatureMethod);
	    String finalSignature = encode(oauthSignature);
	    finalSignature = StringEscapeUtils.escapeJava(oauthSignature);
	    String finalTimestamp = encode(oauthTimeStamp);
	    finalTimestamp = StringEscapeUtils.escapeJava(oauthTimeStamp);
	    String finalToken = encode(oauthToken);
	    finalToken = StringEscapeUtils.escapeJava(oauthToken);
	    String finalVersion = encode(oauthVersion);
	    finalVersion = StringEscapeUtils.escapeJava(oauthVersion);
	    
		/*String authHeader = "oauth_consumer_key=%s&oauth_nonce=%s&oauth_signature_method=%s" +
	            "&oauth_timestamp=%s&oauth_token=%s&oauth_version=%s";*/
	    
	    String authHeader = "OAuth oauth_nonce=%s, oauth_signature_method=%s, " +
	            "oauth_timestamp=%s, oauth_consumer_key=%s, " +
	            "oauth_token=%s, oauth_signature=%s, " +
	            "oauth_version=%s";
		String tempHeaderFormat = String.format(authHeader, finalNonce, finalSignatureMethod, 
					finalTimestamp, finalConsumer, finalToken, finalSignature, finalVersion);
		
		HttpsURLConnection request = (HttpsURLConnection)new URL(resourceUrl).openConnection();
		request.setDoOutput(true);
		request.addRequestProperty("Authorization", tempHeaderFormat);;
		request.setRequestMethod("POST");
		request.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	
		DataOutputStream dos = new DataOutputStream(request.getOutputStream());
	
		System.out.println(request.getResponseMessage());
		System.out.println(request.getResponseCode());
		System.out.println(authHeader);
		System.out.println(tempHeaderFormat);
		System.out.println(request);
		
		return oauthNonce;
	}	
}	