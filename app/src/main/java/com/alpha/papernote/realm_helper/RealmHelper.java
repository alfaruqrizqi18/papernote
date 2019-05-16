package com.alpha.papernote.realm_helper;

import android.util.Log;

import com.alpha.papernote.models.NotesModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmHelper {

    Realm realm;

    public RealmHelper(Realm realm) {
        this.realm = realm;
    }

    public void Save(final NotesModel notesModel) {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (realm != null){
                    Log.e("Created", "Database was created");
                    Number currentIdNum = realm.where(NotesModel.class).max("id");
                    int nextId;
                    if (currentIdNum == null){
                        nextId = 1;
                    }else {
                        nextId = currentIdNum.intValue() + 1;
                    }
                    notesModel.setId(nextId);
                    NotesModel notes = realm.copyToRealm(notesModel);
                }else{
                    Log.e("ppppp", "execute: Database not Exist");
                }
            }
        });
    }

    public List<NotesModel> GetAllNotes(){
        RealmResults<NotesModel> results = realm.where(NotesModel.class).findAll();
        return results;
    }

    public void Delete(Integer id){
        final RealmResults<NotesModel> model = realm.where(NotesModel.class).equalTo("id", id).findAll();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                model.deleteFromRealm(0);
            }
        });
    }

}
