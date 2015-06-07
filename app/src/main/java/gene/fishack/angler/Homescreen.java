package gene.fishack.angler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Homescreen extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
	}
	
	public void onClickLakesButton(View v) {
			Intent launchFragment = new Intent();
			launchFragment.setClass(getApplicationContext(), ItemListActivity.class);
			launchFragment.putExtra("fragmentType", "lakes");
			startActivity(launchFragment);
	}
	
	public void onClickFishButton(View v) {
		Intent launchFragment = new Intent();
		launchFragment.setClass(getApplicationContext(), ItemListActivity.class);
		launchFragment.putExtra("fragmentType", "fish");
		startActivity(launchFragment);
	}
	public void onClickCaught(View v) {
		Intent launchFragment = new Intent();
		launchFragment.setClass(getApplicationContext(), CaughtFishActivity.class);
		launchFragment.putExtra("fragmentType", "fish");
		startActivity(launchFragment);
	}
	public void onClickLawsAndRegs(View v) {
		if (isNetworkAvailable()) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.eregulations.com/maine/fishing/"));
			startActivity(browserIntent);
		}
		else {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			// Add the buttons
			builder.setPositiveButton("O.K.", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               // User clicked OK button
			           }
			       });
			// Create the AlertDialog
			builder.setTitle("Internet Required");
			builder.setMessage("You need an internet connection to view Laws and Regulations");
			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}
	
	public void onClickAbout(View v) {
		((Button) findViewById(R.id.what_is)).setBackgroundColor(Color.parseColor("#33b5e5"));
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// Add the buttons
		builder.setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		               // User clicked OK button
		           }
		       });
		// Create the AlertDialog
		builder.setTitle("About Angler");
		builder.setMessage("Angler");
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
