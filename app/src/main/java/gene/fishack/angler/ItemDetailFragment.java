package gene.fishack.angler;

import java.util.Locale;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
	public static final String ARG_ITEM_ID = "item_id";
	static AnglerDbAdapter db;
	static Cursor dataObject;
	static ActionBar actionBar;
	private static boolean mTwoPane;
	private String mItem;
	private static Fragment me;
	private static FragmentManager fm;
	static String[] fragmentArgs;

	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
			actionBar = getActivity().getActionBar();
		else
			actionBar = null;
		
		me = this;
		
		db = new AnglerDbAdapter(getActivity());
	    db.open();

		if (getArguments().containsKey(ARG_ITEM_ID))
			mItem = getArguments().getString(ARG_ITEM_ID);
			//Toast.makeText(getActivity(), mItem, Toast.LENGTH_LONG).show();
		
		fragmentArgs = getArguments().getString(ARG_ITEM_ID).split(" ");
		
		if (fragmentArgs[1].equals("lakes")) 
			dataObject = db.getLake(Integer.parseInt(fragmentArgs[0]));
		else 
			dataObject = db.getFish(Integer.parseInt(fragmentArgs[0]));
		
		mItem = fragmentArgs[1];
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rView = null;
		
		//Now that the view is inflated we will populate with content
		if (mItem != null && mItem.equals("lakes")) {
			rView = inflater.inflate(R.layout.fragment_lake,container, false);
			populateLakeFragment(rView, getActivity());
			if (getActivity().findViewById(R.id.item_list) != null) {
				mTwoPane = true;
			}
		}
		
		else if (mItem != null && mItem.equals("fish")) {
			rView = inflater.inflate(R.layout.fragment_fish, container, false);
			populateFishFragment(rView, getActivity());
		}
		
		else {
			Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
		}

		return rView;
	}
	
	static void populateLakeFragment(View rootView, final Context activityContext) {
		Cursor fishInLake = null;
		if (dataObject.moveToFirst()){
		
			int lakePictureResource = dataObject.getInt(4);
			String lakeName = dataObject.getString(1);
			((ImageView) rootView.findViewById(R.id.lake_detail_picture)).setImageResource(lakePictureResource);
			
			if (actionBar != null)
				actionBar.setTitle(lakeName);
			
			((TextView) rootView.findViewById(R.id.boat_feature_text)).setText(dataObject.getString(11));
			
			((TextView) rootView.findViewById(R.id.lure_feature_text)).setText(dataObject.getString(12));
			((TextView) rootView.findViewById(R.id.icefishing_feature_text)).setText(dataObject.getString(13));
			
			fishInLake = db.getFishFromLake(dataObject.getInt(0));
		}
		dataObject.close();
		
		
		LinearLayout fishList = (LinearLayout)rootView.findViewById(R.id.list_fish_layout);
		fishInLake.moveToFirst();
		
		do {	
			RelativeLayout infoLayout = new RelativeLayout(activityContext);
			
			RelativeLayout.LayoutParams imgParms = new RelativeLayout.LayoutParams(100,100);
			
			imgParms.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);					
			ImageView iv = new ImageView(activityContext);
			iv.setImageResource(fishInLake.getInt(2));
			
			TextView tv = new TextView(activityContext);
			tv.setText(fishInLake.getString(1));
			tv.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
			tv.setTextSize(20);
			
			infoLayout.addView(iv);
			infoLayout.addView(tv);
			iv.setLayoutParams(imgParms);
			
			RelativeLayout.LayoutParams txtParms = new RelativeLayout.LayoutParams(100,100);
			txtParms.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			txtParms.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
			txtParms.height = LinearLayout.LayoutParams.MATCH_PARENT;
			txtParms.leftMargin = 110;
			tv.setLayoutParams(txtParms);
			
			final int getDbId = fishInLake.getInt(0);
			infoLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mTwoPane) {
						// In two-pane mode, show the detail view in this activity by
						// adding or replacing the detail fragment using a
						// fragment transaction.
						fm = ((FragmentActivity)activityContext).getSupportFragmentManager();
						Fragment f = fm.findFragmentById(R.id.item_detail_container);
						Fragment newInstance = null;
						
						try {
							newInstance = f.getClass().newInstance();
							
						} catch (java.lang.InstantiationException e) {

							e.printStackTrace();
							
						} catch (IllegalAccessException e) {

							e.printStackTrace();
						}
						Fragment.SavedState oldState= fm.saveFragmentInstanceState(f);
			            newInstance.setInitialSavedState(oldState);
			            Bundle newFragArguments = new Bundle();
						newFragArguments.putString(ItemDetailFragment.ARG_ITEM_ID, fragmentArgs[0] + " " + "lakes");
						newInstance.setArguments(newFragArguments);
			            fm.beginTransaction().replace(R.id.item_list, newInstance).commit();
						
			            
			            //Fish Fragment
			            ItemDetailFragment fragment = new ItemDetailFragment(); 
			            Bundle newFishFragArguments = new Bundle();
			            newFishFragArguments.putString(ItemDetailFragment.ARG_ITEM_ID, getDbId + " " + "fish");
			            fragment.setArguments(newFishFragArguments);
						((FragmentActivity)activityContext).getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();

					} else {
						// In single-pane mode, simply start the detail activity
						// for the selected item ID.
						Intent detailIntent = new Intent(activityContext, ItemDetailActivity.class);
						detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, getDbId + " " + "fish");
						activityContext.startActivity(detailIntent);
					}
				}});
			
			fishList.addView(infoLayout);

		} while(fishInLake.moveToNext());
		fishInLake.close();
	}
	
	static void populateFishFragment(View rootView, final Context activityContext) {
		
		if (dataObject.moveToFirst()) {
			if (actionBar != null)
				actionBar.setTitle(dataObject.getString(2));
			
			int fishPictureResource = dataObject.getInt(1);
			((ImageView) rootView.findViewById(R.id.fish_detail_picture)).setImageResource(fishPictureResource);
			
			LinearLayout fishListLayout = (LinearLayout)rootView.findViewById(R.id.species_information_layout); 			
			for (int i = 3; i < dataObject.getColumnCount(); i++) {
				
			LinearLayout infoLayout = new LinearLayout(activityContext);
			infoLayout.setOrientation(LinearLayout.VERTICAL);
			
			TextView fieldName = new TextView(activityContext);
			fieldName.setTypeface(Typeface.create("sans-serif", Typeface.BOLD));
			fieldName.setText(snkeToNorml(dataObject.getColumnName(i)));
			fieldName.setTextSize(15);
			
			infoLayout.addView(fieldName);	
			
			TextView tv = new TextView(activityContext);
			tv.setText(dataObject.getString(i));
			tv.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
			tv.setTextSize(20);
	
			
			//Calculate scale pixels for the padding because it only accepts standard pixels
			int padding_in_dp = 20; 
		    final float scale = activityContext.getResources().getDisplayMetrics().density;
		    int padding_in_px = (int) (padding_in_dp * scale + 0.5f);
		    
			infoLayout.setPadding(padding_in_px, 15, padding_in_px, 15);
			infoLayout.addView(tv);
			
			fishListLayout.addView(infoLayout);
		}
		}

		Cursor lakesFromFish = db.getLakesFromFish(dataObject.getInt(0));
		lakesFromFish.moveToFirst();
		LinearLayout lakeList = (LinearLayout)rootView.findViewById(R.id.can_be_found_list);
		
		do {	
			RelativeLayout infoLayout = new RelativeLayout(activityContext);
			
			TextView tv = new TextView(activityContext);
			tv.setText(lakesFromFish.getString(1));
			tv.setTypeface(Typeface.create("sans-serif-light", Typeface.NORMAL));
			tv.setTextSize(20);
			
			infoLayout.addView(tv);
			
			RelativeLayout.LayoutParams txtParms = new RelativeLayout.LayoutParams(100,100);
			txtParms.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
			//txtParms.addRule(RelativeLayout.RIGHT_OF, iv.getId());
			txtParms.width = RelativeLayout.LayoutParams.WRAP_CONTENT;
			txtParms.height = LinearLayout.LayoutParams.MATCH_PARENT;
			txtParms.topMargin = 15;
			tv.setLayoutParams(txtParms);

			final int getDbId = lakesFromFish.getInt(0);
			infoLayout.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (mTwoPane) {
						// In two-p2ane mode, show the detail view in this activity by
						// adding or replacing the detail fragment using a
						// fragment transaction.
						fm = ((FragmentActivity)activityContext).getSupportFragmentManager();
						Fragment f = fm.findFragmentById(R.id.item_detail_container);
						Fragment newInstance = null;
						
						try {
							newInstance = f.getClass().newInstance();
							
						} catch (java.lang.InstantiationException e) {

							e.printStackTrace();
							
						} catch (IllegalAccessException e) {

							e.printStackTrace();
						}
						Fragment.SavedState oldState= fm.saveFragmentInstanceState(f);
			            newInstance.setInitialSavedState(oldState);
			            Bundle newFragArguments = new Bundle();
						newFragArguments.putString(ItemDetailFragment.ARG_ITEM_ID, fragmentArgs[0] + " " + "fish");
						newInstance.setArguments(newFragArguments);
			            fm.beginTransaction().replace(R.id.item_list, newInstance).commit();
						
			            
			            //Lake Fragment
			            ItemDetailFragment fragment = new ItemDetailFragment(); 
			            Bundle newFishFragArguments = new Bundle();
			            newFishFragArguments.putString(ItemDetailFragment.ARG_ITEM_ID, getDbId + " " + "lakes");
			            fragment.setArguments(newFishFragArguments);
						((FragmentActivity)activityContext).getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();

					} else {
						// In single-pane mode, simply start the detail activity
						// for the selected item ID.
						Intent detailIntent = new Intent(activityContext, ItemDetailActivity.class);
						detailIntent.putExtra(ItemDetailFragment.ARG_ITEM_ID, getDbId + " " + "lakes");
						activityContext.startActivity(detailIntent);
					}
				}});
			
			lakeList.addView(infoLayout);

		} while(lakesFromFish.moveToNext());
		
		lakesFromFish.close();
	}

	@Override
	public void onPause() {
	    super.onPause();
	    if (db != null) {
	        db.close();
	        db = null;
	    }
	    
	    if (dataObject != null) {
	    	dataObject.close();
	    	dataObject = null;
	    }
	}
	
	public static String snkeToNorml(String input) {
		String[] splitInput = input.split("");
		
		for (int i = 0; i < splitInput.length; i++) {
			if (i == 1) {
				splitInput[i] = splitInput[i].toUpperCase(Locale.US);
			}
			if (splitInput[i].equals("_")) {
				splitInput[i] = " ";
				splitInput[i + 1] = splitInput[i + 1].toUpperCase(Locale.US); 
				i++;
			}
		}
		String s = "";
		for (int i = 0; i < splitInput.length; i++) {
			s += splitInput[i];
		}
		return s;
	}	
}