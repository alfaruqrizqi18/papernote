package com.alpha.papernote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity {
    EditText search_form;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search_form = findViewById(R.id.search_form);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        starterPack();
    }

    void starterPack() {
        setActionSearchToSearchForm();
    }

    void setActionSearchToSearchForm() {
        search_form.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String query = search_form.getText().toString().trim();
                    if (!query.isEmpty()) {
                        Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
                        intent.putExtra("query", query);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SearchActivity.this, "Please fill the search form", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        search_form.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        search_form.selectAll();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        search_form.requestFocus(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(search_form.getWindowToken(), 0);
                return true;
            case R.id.reset:
                search_form.setText(null);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
