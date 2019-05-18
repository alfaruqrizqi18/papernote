package com.alpha.papernote;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alpha.papernote.gesture_helper.OnSwipeTouchListener;

public class CreateNotesActivity extends AppCompatActivity {
    Toolbar toolbar;
    CoordinatorLayout container;
    EditText papernote_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_create_notes);
        container = findViewById(R.id.container);

        toolbar = findViewById(R.id.toolbar);
        papernote_content = findViewById(R.id.papernote_content);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        starterPack();
    }


    void starterPack() {
        containerGesture();
    }

    void containerGesture() {
        container.setOnTouchListener(new OnSwipeTouchListener(CreateNotesActivity.this) {
            public void onSwipeTop() {
                Toast.makeText(CreateNotesActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                finish();
            }

            public void onSwipeLeft() {
                Toast.makeText(CreateNotesActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(CreateNotesActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_notes_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save:
                Toast.makeText(CreateNotesActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
