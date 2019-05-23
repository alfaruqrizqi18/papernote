package com.alpha.papernote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alpha.papernote.adapter.SearchResultsActivityNotesAdapter;
import com.alpha.papernote.models.NotesModel;
import com.alpha.papernote.realm_helper.RealmHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class SearchResultsActivity extends AppCompatActivity {
    public boolean isEmptyStates = true;
    String iQuery;
    Toolbar toolbar;
    TextView toolbarTitle, total_notes;
    RecyclerView recyclerView;
    LinearLayout empty_layout, text_layout;
    List<NotesModel> notesModel;
    SearchResultsActivityNotesAdapter searchResultsActivityNotesAdapter;
    Realm realm;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        iQuery = getIntent().getExtras().getString("query");

        recyclerView = findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);


        empty_layout = findViewById(R.id.empty_layout);
        text_layout = findViewById(R.id.text_layout);
        total_notes = findViewById(R.id.total_notes);

        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbarTitle.setText(iQuery);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        notesModel = new ArrayList<>();
        notesModel = realmHelper.SearchNotes(iQuery);

        starterPack();

    }

    void starterPack() {
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerSwipeLeft();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showResultsNotes();
        checkIfEmptyStates();
    }

    void recyclerSwipeLeft() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                realmHelper.Delete(notesModel.get(viewHolder.getAdapterPosition()).getId());
                searchResultsActivityNotesAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                total_notes.setText(searchResultsActivityNotesAdapter.getItemCount() + " notes ");
                checkStatusEmptyState();
                checkIfEmptyStates();
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }

    public void showResultsNotes() {
        searchResultsActivityNotesAdapter = new SearchResultsActivityNotesAdapter(SearchResultsActivity.this, notesModel);
        checkStatusEmptyState();
        total_notes.setText(searchResultsActivityNotesAdapter.getItemCount() + " notes ");
        recyclerView.setAdapter(searchResultsActivityNotesAdapter);
    }

    void checkStatusEmptyState() {
        isEmptyStates = searchResultsActivityNotesAdapter.getItemCount() == 0;
    }

    void checkIfEmptyStates() {
        AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        if (isEmptyStates) {
            params.setScrollFlags(0);
            text_layout.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            empty_layout.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            text_layout.setVisibility(View.VISIBLE);
            empty_layout.setVisibility(View.GONE);
            params.setScrollFlags(
                    AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                            | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
        }
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
                return true;
            case R.id.reset:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
