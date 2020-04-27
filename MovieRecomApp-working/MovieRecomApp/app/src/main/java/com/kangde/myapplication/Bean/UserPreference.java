package com.kangde.myapplication.Bean;


public class UserPreference {

	private int Up;

	private String UserName;
	private String Uploader;
	private String MovieName;

	private int rate;



	public  UserPreference()
	{

	}

	public UserPreference(int up, String userName, String uploader,String Mname,int rate) {
		super();
		Up = up;
		MovieName=Mname;
		UserName = userName;
		Uploader = uploader;
		this.rate = rate;
	}

	public int getUp() {
		return Up;
	}
	public void setUp(int up) {
		Up = up;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getUploader() {
		return Uploader;
	}
	public void setUploader(String uploader) {
		Uploader = uploader;
	}
	public int getRate() {
		return rate;
	}
	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getMovieName() {
		return MovieName;
	}

	public void setMovieName(String movieName) {
		MovieName = movieName;
	}
}
