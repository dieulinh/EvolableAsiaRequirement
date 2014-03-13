package com.example.sparx;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.UndeclaredThrowableException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
 

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.util.Date;
import java.lang.*;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;





public class RequestMaker {
	 private  static char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	    

	private static byte[] hmac_sha(String crypto, byte[] keyBytes, byte[] text) {
		try {
			Mac hmac;
			hmac = Mac.getInstance(crypto);
			SecretKeySpec macKey = new SecretKeySpec(keyBytes, "RAW");
			hmac.init(macKey);
			return hmac.doFinal(text);
			} catch (GeneralSecurityException gse) {
			throw new UndeclaredThrowableException(gse);
		}
}


	    public static String stringToHex(String input) throws UnsupportedEncodingException
	    {
	        if (input == null) throw new NullPointerException();
	        return asHex(input.getBytes());
	    }

	    public String hexToString(String txtInHex)
	    {
	        byte [] txtInByte = new byte [txtInHex.length() / 2];
	        int j = 0;
	        for (int i = 0; i < txtInHex.length(); i += 2)
	        {
	                txtInByte[j++] = Byte.parseByte(txtInHex.substring(i, i + 2), 16);
	        }
	        return new String(txtInByte);
	    }

	    private static String asHex(byte[] buf)
	    {
	        char[] chars = new char[2 * buf.length];
	        for (int i = 0; i < buf.length; ++i)
	        {
	            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
	            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
	        }
	        return new String(chars);
	    }

private static byte[] hexStr2Bytes(String hex) throws UnsupportedEncodingException {
// Adding one byte to get the right conversion
// Values starting with "0" can be converted
	System.out.println(hex);

    String output = stringToHex(hex);
    System.out.println(output);
	byte[] bArray = new BigInteger("10" + output, 16).toByteArray();
	// Copy all the REAL bytes, not the "first"
	byte[] ret = new byte[bArray.length - 1];
	for (int i = 0; i < ret.length; i++)
	ret[i] = bArray[i + 1];
	return ret;
}

private static final int[] DIGITS_POWER
//0 1 2 3 4 5 6 7 8
= { 1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000 };

public static String generateTOTP(String key, String time, String returnDigits) throws UnsupportedEncodingException{
	int codeDigits = Integer.decode(returnDigits).intValue();
	String result = null;
	String crypto = "HmacSHA256";
	// Using the counter
	// First 8 bytes are for the movingFactor
	// Compliant with base RFC 4226 (HOTP)
	while (time.length() < 16)
		time = "0" + time;
	
	System.out.print(time);
	
	// Get the HEX in a Byte[]
	byte[] msg = hexStr2Bytes(time);
	byte[] k = hexStr2Bytes(key);
	byte[] hash = hmac_sha(crypto, k, msg);		
	
	// put selected bytes into result int
	int offset = hash[hash.length - 1] & 0xf;
	
	int binary = ((hash[offset] & 0x7f) << 24)
	| ((hash[offset + 1] & 0xff) << 16)
	| ((hash[offset + 2] & 0xff) << 8) 
	| (hash[offset + 3] & 0xff);
	
	int otp = binary % DIGITS_POWER[codeDigits];
	
	result = Integer.toString(otp);
	while (result.length() < codeDigits) {
	result = "0" + result;
	}
	System.out.println(result);
	return result;
}
	private final String USER_AGENT = "Mozilla/5.0";
	 
	public static void main(String[] args) throws Exception {
 
		RequestMaker http = new RequestMaker();

		System.out.println("\nTesting 2 - Send Http POST request");
		http.sendPost();
 
	}
 

	// HTTP POST request
	private void sendPost() throws Exception {
 
		String url = "http://hdegip.appspot.com/eva/001/endpoint";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//		Date mDate = new Date();
		long t =  new Date().getTime() / TimeUnit.SECONDS.toMillis(30);
		String tString = String.valueOf(t);
		
		//add reuqest header
		con.setRequestMethod("POST");
		
		con.setRequestProperty("Content-type", "application/json");
		String key = "linhvn09@gmail.comHDEEVA001ABCD";
		String rtString = new String("8");
		String mrs = RequestMaker.generateTOTP(key, tString, "8");

		
		String userName = "linhvn09@gmail.com";
		String password = mrs;
		String credentials = userName + ":" + mrs;
		String encoding = Base64Converter.encode(credentials.getBytes("UTF-8"));

		String urlParameters = "userid=linhvn09@gmail.com&password=" + mrs;

		con.setRequestProperty("Authorization", String.format("Basic %s", encoding));
		con.setRequestProperty("Accept", "*/*");
		String  myString = "{'contact_email'] ='linhvn09@gmail.com', 'github_url']='https://github.com/dieulinh/EvolableAsiaRequirement'}";
		String request = "http://hdegip.appspot.com/eva/001/endpoint";
		JSONObject jo = new JSONObject();
		jo.put("contact_email", "linhvn09@gmail.com");
		jo.put("github_url", "https://github.com/dieulinh/EvolableAsiaRequirement");
		
		// Send post request

		con.setDoInput(true);

		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(jo.toString());
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + urlParameters);
		System.out.println("Response Code : " + responseCode);
 
		

 
	}
 

	/**
	 * @param args
	 * @throws IOException 
	 */
	

}
