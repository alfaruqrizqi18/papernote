package com.alpha.papernote;

import android.app.ActionBar;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarTitle;
    public boolean isEmptyStates = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        checkIfEmptyStates();
    }

    void checkIfEmptyStates() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        if (isEmptyStates) {
            params.setScrollFlags(0);
        } else {
            params.setScrollFlags(
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                            | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS
                            | AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
}
