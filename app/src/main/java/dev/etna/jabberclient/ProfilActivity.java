package dev.etna.jabberclient;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import dev.etna.jabberclient.fragments.ProfilFragment;

public class ProfilActivity extends AppCompatActivity implements ProfilFragment.OnFragmentInteractionListener{
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        if (savedInstanceState == null) {
            this.switchFragment(new ProfilFragment());
        }


    }

    public void switchFragment(Fragment newFragment)
    {
        Fragment currentFragment;
        FragmentManager manager;
        FragmentTransaction transaction;

        manager = this.getFragmentManager();
        currentFragment = manager.findFragmentByTag("PROFIL_FRAGMENT");
        if (currentFragment == null || !(currentFragment.getClass().equals(newFragment.getClass())))
        {
            transaction = manager.beginTransaction();
            transaction.replace(R.id.activity_profil, newFragment, "PROFIL_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else
        {
            Log.i("MainActivity", "same fragment will not be loaded twice in a row");
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        
    }
}
