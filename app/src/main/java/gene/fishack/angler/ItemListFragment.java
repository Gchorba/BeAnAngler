package gene.fishack.angler;


import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ItemListFragment extends ListFragment {

    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    AnglerDbAdapter db;
    String fragmentType;
    ActionBar actionBar;
    Bundle extras;

    private Callbacks mCallbacks = sDummyCallbacks;

    private int mActivatedPosition = ListView.INVALID_POSITION;

    public interface Callbacks {
        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };
    
    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //prepare cursor and db
        db = new AnglerDbAdapter(getActivity());
        db.open();
        
        
        //get type of fragment from activity
        extras = null;
		extras = getActivity().getIntent().getExtras();
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) 
			actionBar = getActivity().getActionBar();
		else
			actionBar = null;
		
		fragmentType = extras.getString("fragmentType");
		
        if (fragmentType.equals("lakes")) {
        	ArrayList<String> names = new ArrayList<String>();
        	Cursor cursor = db.getAllLakes();
        	cursor.moveToFirst();
        	do {	
        		names.add(cursor.getString(1));
    		} while(cursor.moveToNext());
        	cursor.close();
        	
        	setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, names));
        	
        	if (actionBar != null)
        		actionBar.setTitle("Lakes");
		}
        else if (extras.getString("fragmentType").equals("fish")){
        	ArrayList<String> names = new ArrayList<String>();
        	Cursor cursor = db.getAllFish();
        	cursor.moveToFirst();
        	do {	
        		names.add(cursor.getString(1));
    		} while(cursor.moveToNext());
        	cursor.close();
        	
        	setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, names));
        	
        	if (actionBar != null)
        		actionBar.setTitle("Fish");
        }  
        
        else { 
        	Toast.makeText(getActivity(), "error will robinson", Toast.LENGTH_LONG).show();
        }
        
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected(id + " " + fragmentType);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    
	@Override
	public void onPause() {
	    super.onPause();
	    
	    if (db != null) {
	        db.close();
	        db = null;
	    }
	}
	
	
}