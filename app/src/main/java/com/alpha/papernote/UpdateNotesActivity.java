package com.alpha.papernote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alpha.papernote.models.NotesModel;
import com.alpha.papernote.realm_helper.RealmHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class UpdateNotesActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView toolbarTitle;
    EditText title, papernote_content;
    RadioGroup radio_group_color;
    RadioButton radio_button;
    String color_label;


    Realm realm;
    RealmHelper realmHelper;

    String iId, iTitle, iPapernote, iColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_notes);

        iId = getIntent().getExtras().getString("id");
        iTitle = getIntent().getExtras().getString("title");
        iPapernote = getIntent().getExtras().getString("papernote_content");
        iColor = getIntent().getExtras().getString("color_label");
        color_label = iColor;

        toolbarTitle = findViewById(R.id.toolbarTitle);
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.title);
        papernote_content = findViewById(R.id.papernote_content);
        radio_group_color = findViewById(R.id.radio_group_color);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Set up Realm
        // Set up
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
        realmHelper = new RealmHelper(realm);

        starterPack();
    }

    void starterPack() {
        toolbarTitle.setText(UpdateNotesActivity.this.getResources().getString(R.string.edit_papernote));
        radio_group_color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radio_button = findViewById(checkedId);
                color_label = (String) radio_button.getContentDescription();
            }
        });
        setValueToForm();
    }

    void setValueToForm(){
        title.setText(iTitle);
        papernote_content.setText(iPapernote);
        int lengthRadioGroup = radio_group_color.getChildCount();
        for (int i = 0; i < lengthRadioGroup; i++) {
            String content = (String) radio_group_color.getChildAt(i).getContentDescription();
            if (content.equals(color_label)) {
                radio_button = findViewById(radio_group_color.getChildAt(i).getId());
                radio_button.setChecked(true);
            }
        }
    }

    void updateNotes(){
        String mTitle = title.getText().toString();
        String mPapernoteContent = papernote_content.getText().toString();
        realmHelper.Update(Integer.parseInt(iId), mTitle, mPapernoteContent, color_label);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_notes_activity_toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.save:
                updateNotes();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
