package com.alpha.papernote;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CreateNotesActivity extends AppCompatActivity {
    Toolbar toolbar;
    CoordinatorLayout container;
    EditText papernote_content;
    RadioGroup radio_group_color;
    RadioButton radio_button;
    String color_label;

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
        radio_group_color = findViewById(R.id.radio_group_color);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        starterPack();
    }


    void starterPack() {
        papernote_content.requestFocus();
        getSelectedColor();
    }

    void getSelectedColor() {
        color_label = CreateNotesActivity.this.getResources().getString(R.string.circle_white);
        radio_group_color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radio_button = findViewById(checkedId);
                color_label = (String) radio_button.getContentDescription();
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
                Toast.makeText(CreateNotesActivity.this, "Saved with color " + color_label, Toast.LENGTH_SHORT).show();
//                getSelectedColor();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
