package gene.fishack.angler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Homescreen extends Activity{
	private String[] drawerListViewItems;
	private DrawerLayout drawerLayout;
	private ListView drawerListView;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
		// get list items from strings.xml
		drawerListViewItems = getResources().getStringArray(R.array.items);
		// get ListView defined in activity_main.xml
		drawerListView = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		drawerListView.setAdapter(new ArrayAdapter<String>(this,
				R.layout.listview_item_row, drawerListViewItems));

		// 2. App Icon
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		// 2.1 create ActionBarDrawerToggle
		actionBarDrawerToggle = new ActionBarDrawerToggle(
				this,                  /* host Activity */
				drawerLayout,         /* DrawerLayout object */
				R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
				R.string.drawer_open,  /* "open drawer" description */
				R.string.drawer_close  /* "close drawer" description */
		);

		// 2.2 Set actionBarDrawerToggle as the DrawerListener
		drawerLayout.setDrawerListener(actionBarDrawerToggle);

		// 2.3 enable and show "up" arrow
		getActionBar().setDisplayHomeAsUpEnabled(true);

		// just styling option
		//drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		drawerListView.setOnItemClickListener(new DrawerItemClickListener());
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		actionBarDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		// call ActionBarDrawerToggle.onOptionsItemSelected(), if it returns true
		// then it has handled the app icon touch event

		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView parent, View view, int position, long id) {
			Toast.makeText(Homescreen.this, ((TextView) view).getText(), Toast.LENGTH_LONG).show();

			onClickLakesButton(view);
			drawerLayout.closeDrawer(drawerListView);

		}
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

		//Intent detailIntent = new Intent(this, ItemDetailActivity.class);
		//detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, 1 + " " + "fish");
		//this.startActivity(detailIntent);
	}
	public void onClickBassSpotted(View v) {
//		Intent launchFragment = new Intent();
//		launchFragment.setClass(getApplicationContext(), CaughtFishActivity.class);
//		launchFragment.putExtra("fragmentType", "fish");
//		startActivity(launchFragment);
		//Intent detailIntent = new Intent(this, ItemDetailActivity.class);
		//detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, "1 fish" + "");

		//startActivity(detailIntent);
		Intent detailIntent = new Intent(this, ItemDetailActivity.class);
		detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, 17 + " " + "fish");
		this.startActivity(detailIntent);
	}
	public void onClickCrappieBlack(View v) {
//		Intent launchFragment = new Intent();
//		launchFragment.setClass(getApplicationContext(), CaughtFishActivity.class);
//		launchFragment.putExtra("fragmentType", "fish");
//		startActivity(launchFragment);
		//Intent detailIntent = new Intent(this, ItemDetailActivity.class);
		//detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, "1 fish" + "");

		//startActivity(detailIntent);
		Intent detailIntent = new Intent(this, ItemDetailActivity.class);
		detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, 2 + " " + "fish");
		this.startActivity(detailIntent);
	}
	public void onClickLawsAndRegs(View v) {
		if (isNetworkAvailable()) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.georgiawildlife.com/fishing/regulations"));
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
	
//	public void onClickAbout(View v) {
//	//	((Button) findViewById(R.id.what_is)).setBackgroundColor(Color.parseColor("#33b5e5"));
//
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		// Add the buttons
//		builder.setPositiveButton("Got It!", new DialogInterface.OnClickListener() {
//		           public void onClick(DialogInterface dialog, int id) {
//		               // User clicked OK button
//		           }
//		       });
//		// Create the AlertDialog
//		builder.setTitle("About Angler");
//		builder.setMessage("Angler");
//		AlertDialog dialog = builder.create();
//		dialog.show();
//	}
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}
	
}
