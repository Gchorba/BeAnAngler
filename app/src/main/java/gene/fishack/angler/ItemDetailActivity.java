package gene.fishack.angler;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

public class ItemDetailActivity extends FragmentActivity {
	Context appContext;
	String fragmentType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);
		appContext = this;
		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			
			fragmentType = getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID);
			arguments.putString(ItemDetailFragment.ARG_ITEM_ID, fragmentType);
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent launchFragment = new Intent();
			launchFragment.setClass(getApplicationContext(), ItemListActivity.class);
			String[] fragType = fragmentType.split(" ");
			launchFragment.putExtra("fragmentType", fragType[1]);
			startActivity(launchFragment);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
