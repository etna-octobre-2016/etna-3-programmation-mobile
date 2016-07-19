package dev.etna.jabberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import dev.etna.jabberclient.fragments.ChatFragment;
import dev.etna.jabberclient.fragments.ContactAddFragment;
import dev.etna.jabberclient.tasks.LogoutTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //TODO: Delete next line before merge to develop repo
        this.switchFragment(new ChatFragment());
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout)
        {
            JabberClientApplication app = (JabberClientApplication)this.getApplication();
            LogoutTask task = new LogoutTask(app.getXmppService(), this);
            task.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.sidenav_account)
        {
            Log.i("SIDENAV", "click on my account button");
        }
        else if (id == R.id.sidenav_contact_list)
        {
            Log.i("SIDENAV", "click on contact list button");
        }
        else if (id == R.id.sidenav_contact_add)
        {
            this.switchFragment(new ContactAddFragment());
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchFragment(Fragment newFragment)
    {
        Fragment currentFragment;
        FragmentManager manager;
        FragmentTransaction transaction;

        manager = this.getFragmentManager();
        currentFragment = manager.findFragmentByTag("MAIN_FRAGMENT");
        if (currentFragment == null || !(currentFragment.getClass().equals(newFragment.getClass())))
        {
            transaction = manager.beginTransaction();
            transaction.replace(R.id.mainFragmentContainer, newFragment, "MAIN_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else
        {
            Log.i("MainActivity", "same fragment will not be loaded twice in a row");
        }
    }
}
