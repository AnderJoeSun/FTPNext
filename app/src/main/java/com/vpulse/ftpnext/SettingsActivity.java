package com.vpulse.ftpnext;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import com.vpulse.ftpnext.core.AppInfo;
import com.vpulse.ftpnext.core.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    private final static String TAG = "SETTINGS ACTIVITY";

    private TextView mMinimumDownloadTextView;
    private TextView mMaximumDownloadTextView;
    private TextView mDownloadValueTextView;
    private SeekBar mDownloadSeekBar;
    private Switch mWifiOnlySwitch;
    private Switch mDarkThemeSwitch;

    private ViewGroup mWifiOnlyLayout;
    private ViewGroup mDarkThemeLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        initializeGUI();
        initializeViews();
        initializeViewsListeners();
    }

    private void initializeGUI() {
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setCustomView(R.layout.action_bar_settings);
    }

    private void initializeViews() {
        // Find by view ID
        mMinimumDownloadTextView = findViewById(R.id.settings_text_min_download);
        mMaximumDownloadTextView = findViewById(R.id.settings_text_max_download);
        mDownloadValueTextView = findViewById(R.id.settings_download_text_value);
        mDownloadSeekBar = findViewById(R.id.settings_seek_bar);

        mWifiOnlySwitch = findViewById(R.id.settings_wifi_only_switch);
        mDarkThemeSwitch = findViewById(R.id.settings_dark_theme_switch);

        mWifiOnlyLayout = findViewById(R.id.settings_wifi_only_layout);
        mDarkThemeLayout = findViewById(R.id.settings_dark_theme_layout);

        // Set values
        mWifiOnlySwitch.setChecked(PreferenceManager.isWifiOnly());
        mDarkThemeSwitch.setChecked(PreferenceManager.isDarkTheme());

        mMinimumDownloadTextView.setText(String.valueOf(AppInfo.MINIMUM_SIMULTANEOUS_DOWNLOAD));
        mMaximumDownloadTextView.setText(String.valueOf(AppInfo.MAXIMAL_SIMULTANEOUS_DOWNLOAD));
        mDownloadValueTextView.setText(String.valueOf(PreferenceManager.getMaxTransfers()));
        mDownloadSeekBar.setProgress(PreferenceManager.getMaxTransfers() - 1);
        mDownloadSeekBar.setMax(AppInfo.MAXIMAL_SIMULTANEOUS_DOWNLOAD - 1);
    }

    private void initializeViewsListeners() {
        mWifiOnlyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mWifiOnlySwitch
            }
        });
//        mDarkThemeLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        mDownloadSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar iSeekBar, int iProgress, boolean iFromUser) {
                mDownloadValueTextView.setText(String.valueOf(iProgress + 1));
                PreferenceManager.setMaxTransfers(iProgress + 1);
            }

            @Override
            public void onStartTrackingTouch(SeekBar iSeekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar iSeekBar) {
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }

    private void showBottomSheetDialog() {

    }

    public void onClickTest(View view) {
        showBottomSheetDialog();
    }
}