package org.apoorva.test.instagram.utils;

public class InstagramConstants {

	public static final String APP_AUTH_URL = "https://api.instagram.com/oauth/authorize/";
	public static final String APP_TOKEN_URL = "https://api.instagram.com/oauth/access_token";
	public static final String APP_API_URL = "https://api.instagram.com/v1";
	public static final String APP_SELFIE_URL = "https://api.instagram.com/v1/tags/selfie/media/recent?access_token=";
	public static final String CLIENT_ID = "da1c9e7ace9f461aa659c75da34e3c92";
	public static final String CLIENT_SECRET = "32565f6f35ef4a51a98fdaf6a529071e";
	public static String CALLBACKURL = "http://localhost";

	public static final String AUTH_URL_STRING = APP_AUTH_URL
			+ "?client_id="
			+ CLIENT_ID
			+ "&redirect_uri="
			+ CALLBACKURL
			+ "&response_type=code&display=touch&scope=likes+comments+relationships";

	public static final String KEY_ACCESS_TOKEN = "accesstoken";
	public static final String KEY_REQUEST_TOKEN = "requesttoken";

	public static final String MSG_NO_NETWORK = "Please Check Your Network ";
	public static final String MSG_WAIT = "Please Wait...";

	public static final int REQUEST_FETCH_TOKEN = 101;
	public static final int REQUEST_FETCH_SELIEF = 102;

	public static final int RESPONSE_SUCCESS = 103;
	public static final int RESPONSE_FAILURE = 104;
	public static final int RESPONSE_NO_NETWORK = 105;

}
