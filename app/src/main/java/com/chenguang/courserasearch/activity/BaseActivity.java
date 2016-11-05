package com.chenguang.courserasearch.activity;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.chenguang.courserasearch.R;

public class BaseActivity extends AppCompatActivity {

    protected CoordinatorLayout coordinatorLayout;
    private ConnectivityManager connectivityManager;

    protected interface RetryActionListener {
        void onRetryClicked();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.base_coordinator_layout);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isConnectedToNetwork()) {
            showErrorMessage(getString(R.string.network_error_message));
        }
    }

    protected boolean isConnectedToNetwork() {
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    protected void showErrorMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    protected void showErrorMessageWithRetryAction(String message, final RetryActionListener retryActionListener) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.action_retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retryActionListener.onRetryClicked();
                    }
                })
                .show();
    }
}
