package com.cs115.rex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Displays a paw button menu item that links to {@link ProfileActivity} and a dog house menu item
 * that links to {@link MainActivity}
 * Every activity except {@link ProfileActivity} that displays a home button extends this activity
 * so that the menu code is shared.
 */
public class MenuHomeActivity extends AppCompatActivity {
    // DetailActivity and ResultsActivity extend this activity so that they display a home button
    // on phones and on tablets in portrait orientation

    //Menu - adds and activates dog house home button and paw button from menu_with_home.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu to add items to the app bar.
        getMenuInflater().inflate(R.menu.menu_with_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu - activates dog house button to open search interface
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent mIntent = new Intent(this, MainActivity.class);
                startActivity(mIntent);
                return true;

            //Menu - activates menu paw button to open profile interface
            case R.id.action_profile:
                Intent pIntent = new Intent(this, ProfileActivity.class);
                startActivity(pIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
