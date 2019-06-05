/**<ul>
 * <li>OrphanAsyncTaskThreadTuto</li>
 * <li>com.android2ee.thread.orphan.asynctask</li>
 * <li>13 janv. 2012</li>
 * 
 * <li>======================================================</li>
 *
 * <li>Projet : Mathias Seguy Project</li>
 * <li>Produit par MSE.</li>
 *
 /**
 * <ul>
 * Android Tutorial, An <strong>Android2EE</strong>'s project.</br> 
 * Produced by <strong>Dr. Mathias SEGUY</strong>.</br>
 * Delivered by <strong>http://android2ee.com/</strong></br>
 *  Belongs to <strong>Mathias Seguy</strong></br>
 ****************************************************************************************************************</br>
 * This code is free for any usage but can't be distribute.</br>
 * The distribution is reserved to the site <strong>http://android2ee.com</strong>.</br>
 * The intelectual property belongs to <strong>Mathias Seguy</strong>.</br>
 * <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * 
 * *****************************************************************************************************************</br>
 *  Ce code est libre de toute utilisation mais n'est pas distribuable.</br>
 *  Sa distribution est reservée au site <strong>http://android2ee.com</strong>.</br> 
 *  Sa propriété intellectuelle appartient à <strong>Mathias Seguy</strong>.</br>
 *  <em>http://mathias-seguy.developpez.com/</em></br> </br>
 * *****************************************************************************************************************</br>
 */
package com.android2ee.thread.orphan.asynctask;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

/**
 * @author Mathias Seguy (Android2EE)
 * @goals
 *        This class aims to show that if your not managing your AsyncTask according to your activity's lifecycle
 *        you generate a memory leak.
 *        !!!!!!!! Don't use this class, it's a memory leak proof !!!!!!!!!!!!!
 */
public class OrphanAsyncTaskThreadTutoActivity extends Activity {
	/******************************************************************************************/
	/** Managing the Handler and the Thread *************************************************/
	/******************************************************************************************/
	/**
	 * The Handler
	 */
	private MyAsyncTask asyncTask;
	/******************************************************************************************/
	/** Others attributes **************************************************************************/
	/******************************************************************************************/
	/**
	 * The string for the log
	 */
	private final static String TAG = "OrphanAsyncTaskThreadTutoActivity";
	/**
	 * The ProgressBar
	 */
	private ProgressBar progressBar;
	/**
	 * The way the progress bar increment
	 */
	private boolean reverse = false;
	/**
	 * The activity name
	 */
	private String activityName;

	/******************************************************************************************/
	/** Constructors **************************************************************************/
	/******************************************************************************************/

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// Instantiate the progress bar
		progressBar = (ProgressBar) findViewById(R.id.progressbar);
		progressBar.setMax(100);
		// use a random double to give a name to the thread, the handler and the activity
		double randomD = Math.random();
		final int randomName = (int) (randomD * 100);
		activityName = "Activity" + randomName;
		//instantiate the AsyncTask
		asyncTask = new MyAsyncTask(this, activityName);
		asyncTask.execute(null);
	}

	/******************************************************************************************/
	/** Life Cycle **************************************************************************/
	/******************************************************************************************/
	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	protected void onDestroy() {
		Log.w(TAG, "onDestroy called");
		super.onDestroy();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPause()
	 */
	protected void onPause() {
		Log.w(TAG, "onPause called");
		super.onPause();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onResume()
	 */
	protected void onResume() {
		Log.w(TAG, "onResume called");
		super.onResume();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onSaveInstanceState(android.os.Bundle)
	 */
	protected void onSaveInstanceState(Bundle outState) {
		// Save the state of the reverse boolean
		outState.putBoolean("reverse", reverse);
		// then save the others GUI elements state
		super.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 */
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// Restore the state of the reverse boolean
		reverse = savedInstanceState.getBoolean("reverse");
		// then restore the others GUI elements state
		super.onRestoreInstanceState(savedInstanceState);
	}

	/******************************************************************************************/
	/** Inner package methods **************************************************************************/
	/******************************************************************************************/
	/**
	 * The method that update the progressBar
	 */
	 void updateProgress() {
		Log.e(TAG, "updateProgress called  (on activity n°"+activityName+")");
		
		// get the current value of the progress bar
		int progress = progressBar.getProgress();
		// if the max is reached then reverse the progressbar's progress
		// if the 0 is reached then set the progressbar's progress normal
		if (progress == progressBar.getMax()) {
			reverse = true;
		} else if (progress == 0) {
			reverse = false;
		}
		// increment the progress bar according to the reverse boolean
		if (reverse) {
			progressBar.incrementProgressBy(-1);
		} else {
			progressBar.incrementProgressBy(1);
		}
	}

}