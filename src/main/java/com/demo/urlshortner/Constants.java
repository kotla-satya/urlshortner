package com.demo.urlshortner;

/**
 * Contains the client IDs and scopes for allowed clients consuming your API.
 */
public class Constants {
  public static final String WEB_CLIENT_ID = "replace this with your web client ID";
  public static final String ANDROID_CLIENT_ID = "replace this with your Android client ID";
  public static final String IOS_CLIENT_ID = "replace this with your iOS client ID";
  public static final String ANDROID_AUDIENCE = WEB_CLIENT_ID;

  public static final String EMAIL_SCOPE = "https://www.googleapis.com/auth/userinfo.email";

  public static final String PROJECT_ID = "sampleendpoint-1280";
  public static final String BASE_URL = "https://sbx.bly.com/";
  public static final int BASE_URL_LENGTH = BASE_URL.length();
}
