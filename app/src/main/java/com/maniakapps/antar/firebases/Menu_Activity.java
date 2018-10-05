package com.maniakapps.antar.firebases;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class Menu_Activity extends AppCompatActivity {
    BottomBar bottomBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        bottomBar = findViewById(R.id.bottomBar);

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId){

                    case R.id.tab_maniana:
                        maniana manianaFragment = new maniana();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, manianaFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;
                    case R.id.tab_tarde:
                        tarde tardeFragment = new tarde();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, tardeFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;
                    case R.id.tab_noche:
                        noche nocheFragment = new noche();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, nocheFragment)
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).addToBackStack(null).commit();
                        break;


                }
            }
        });

    }
}
