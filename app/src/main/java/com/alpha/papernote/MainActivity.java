package com.alpha.papernote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
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
import android.widget.Toast;

import com.alpha.papernote.adapter.MainActivityNotesAdapter;
import com.alpha.papernote.models.NotesModel;
import com.alpha.papernote.realm_helper.RealmHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {
    public boolean isEmptyStates = true;
    CoordinatorLayout container;
    Realm realm;
    RealmHelper realmHelper;
    TextView toolbarTitle, total_notes;
    List<NotesModel> notesModel;
    MainActivityNotesAdapter mainActivityNotesAdapter;
    Toolbar toolbar;
    FloatingActionButton createNotes;
    RecyclerView recyclerView;
    LinearLayout empty_layout, text_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        container = findViewById(R.id.container);
        toolbar = findViewById(R.id.toolbar);
        toolbarTitle = findViewById(R.id.toolbarTitle);
        total_notes = findViewById(R.id.total_notes);
        createNotes = findViewById(R.id.createNotes);
        empty_layout = findViewById(R.id.empty_layout);
        text_layout = findViewById(R.id.text_layout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Setup Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        notesModel = new ArrayList<>();
        notesModel = realmHelper.GetAllNotes();

        starterPack();
    }

    void starterPack() {
        createNotesClicked();
        setToolbarTitleBasedOnTime();
        recyclerSwipeLeft();
    }

    void setToolbarTitleBasedOnTime() {
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            toolbarTitle.setText("Hi, Good Morning!");
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            toolbarTitle.setText("Hi, Good Afternoon!");
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            toolbarTitle.setText("Hi, Good Evening!");
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            toolbarTitle.setText("Hi, Good Night!");
        }
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
                mainActivityNotesAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                total_notes.setText(mainActivityNotesAdapter.getItemCount() + " notes ");
                checkStatusEmptyState();
                checkIfEmptyStates();
            }
        };
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        showAllNotes();
        checkIfEmptyStates();
    }

    void checkStatusEmptyState() {
        isEmptyStates = mainActivityNotesAdapter.getItemCount() == 0;
    }

    public void showAllNotes() {
        mainActivityNotesAdapter = new MainActivityNotesAdapter(MainActivity.this, notesModel);
        checkStatusEmptyState();
        total_notes.setText(mainActivityNotesAdapter.getItemCount() + " notes ");
        recyclerView.setAdapter(mainActivityNotesAdapter);
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

    void createNotesClicked() {
        createNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateNotesActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.search:
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
