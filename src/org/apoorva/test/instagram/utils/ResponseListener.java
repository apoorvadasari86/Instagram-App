package org.apoorva.test.instagram.utils;


public interface ResponseListener {
	
	public void onStarted();
	public void onNetworkError(int errorType);
	public void onFailure(EntityResponse erroMsg);
	public void onSuccess(EntityResponse obj);
	
	

}
