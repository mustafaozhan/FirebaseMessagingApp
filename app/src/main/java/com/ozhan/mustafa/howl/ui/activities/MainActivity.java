package com.ozhan.mustafa.howl.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ozhan.mustafa.howl.R;

import com.ozhan.mustafa.howl.core.logout.LogoutContract;
import com.ozhan.mustafa.howl.core.logout.LogoutPresenter;
import com.ozhan.mustafa.howl.ui.fragments.ConversationsFragment;
import com.ozhan.mustafa.howl.ui.fragments.NotificationsFragment;
import com.ozhan.mustafa.howl.ui.fragments.UsersFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LogoutContract.View {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private LogoutPresenter mLogoutPresenter;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.ic_messaging,
            R.drawable.ic_error_24dp,
            R.drawable.friends

    };
    public static void startActivity(Context context) {
        Intent intent = new Intent(context, MainActivity.class);

        context.startActivity(intent);
    }
    public static void startActivity(Context context, int flags) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(flags);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLogoutPresenter = new LogoutPresenter(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new ConversationsFragment(), "Conversations");
        adapter.addFrag(new NotificationsFragment(), "Notifications");
        adapter.addFrag(new UsersFragment(), "Users");

        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
           // return mFragmentTitleList.get(position);

            return null;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_listing, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                break;
            case  R.id.settings:
                startSettingsActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSettingsActivity() {
        Intent settingsActivity=new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(settingsActivity);
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout)
                .setMessage(R.string.are_you_sure)
                .setPositiveButton(R.string.logout, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mLogoutPresenter.logout();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public void onLogoutSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        LoginActivity.startIntent(this,
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onLogoutFailure(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}
