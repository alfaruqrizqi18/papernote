package com.alpha.papernote;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alpha.papernote.models.NotesModel;
import com.alpha.papernote.realm_helper.RealmHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CreateNotesActivity extends AppCompatActivity {
    Toolbar toolbar;
    CoordinatorLayout container;
    EditText title, papernote_content;
    RadioGroup radio_group_color;
    RadioButton radio_button;
    String color_label;


    Realm realm;
    RealmHelper realmHelper;
    NotesModel notesModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_notes);
        container = findViewById(R.id.container);

        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        papernote_content = findViewById(R.id.papernote_content);
        radio_group_color = findViewById(R.id.radio_group_color);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Set up Realm
        Realm.init(CreateNotesActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);


        starterPack();
    }


    void starterPack() {
        papernote_content.requestFocus();
        getSelectedColor();
    }

    void saveNotes(){
        String mTitle = title.getText().toString();
        String mPapernoteContent = papernote_content.getText().toString();

        if (mTitle.isEmpty() && mPapernoteContent.isEmpty()) {
            Toast.makeText(CreateNotesActivity.this, "Please fill title or papernote content", Toast.LENGTH_SHORT).show();
        } else {
            notesModel = new NotesModel();
            notesModel.setTitle(mTitle);
            notesModel.setContent(mPapernoteContent);
            notesModel.setColor(color_label.trim());

            realmHelper = new RealmHelper(realm);
            realmHelper.Save(notesModel);

            finish();
        }
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
    public void onBackPressed() {
        if (title.getText().toString().isEmpty() && papernote_content.getText().toString().isEmpty()) {
            Toast.makeText(CreateNotesActivity.this, "Empty papernote discarded", Toast.LENGTH_SHORT).show();
        } else {
            saveNotes();
        }
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (title.getText().toString().isEmpty() && papernote_content.getText().toString().isEmpty()) {
                    Toast.makeText(CreateNotesActivity.this, "Empty papernote discarded", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    saveNotes();
                }
                return true;
            case R.id.save:
                saveNotes();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
