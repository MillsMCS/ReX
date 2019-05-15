package com.cs115.rex;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Displays a paw button menu item that links to {@link ProfileActivity}
 * Every activity that does not display a home button except ProfileActivity extends this activity
 * so that the menu code is shared.
 * ProfileActivity has its own specific local menu code.
 */
public class MenuActivity extends AppCompatActivity {

    //Menu - adds paw button from main menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu to add items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //Menu - activates menu paw button to open profile interface
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
