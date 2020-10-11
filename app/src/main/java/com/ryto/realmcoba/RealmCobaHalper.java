package com.ryto.realmcoba;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class RealmCobaHalper {

    // implementasi
    private final Realm realm;
    private final static String TAG = RealmCobaHalper.class.getSimpleName();

    // kontruksi halper
    public RealmCobaHalper() {
        // deklarasi untuk menset realm database
        realm = Realm.getInstance(MyApplication.getInstance().setNamaNama());
    }

    // untuk menyimpan data, per-item
    public void setSimpanDataRealm(RealmCobaModels models, Realm.Transaction.OnSuccess onSuccess) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(@NonNull Realm realm) {
                RealmCobaModels realmCobaModels = realm.createObject(RealmCobaModels.class, models.getId());
                realmCobaModels.setNama(models.getNama());
                realmCobaModels.setUmur(models.getUmur());
            }
        }, onSuccess, new Realm.Transaction.OnError() {
            @Override
            public void onError(@NonNull Throwable error) {
                // tampil log
                Log.e(TAG, "" + error.getMessage());

                // tutup realm
                realm.close();
            }
        });
    }

    // untuk menghapus realm, per-item
    public void setHapusDataRealm(String id, Realm.Transaction.OnSuccess onSuccess) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<RealmCobaModels> models = realm.where(RealmCobaModels.class)
                        .equalTo("id", id)
                        .findAll();
                models.deleteAllFromRealm();
            }
        }, onSuccess, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // tampil log
                Log.e(TAG, "" + error.getMessage());
                // tutup realm
                realm.close();
            }
        });
    }

    // untuk mengedit data realm, per-item
    public void setEditDataRealm(RealmCobaModels dataRealm, Realm.Transaction.OnSuccess onSuccess) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmCobaModels models = realm.where(RealmCobaModels.class)
                        .equalTo("id", dataRealm.getId())
                        .findFirst();
                models.setNama(dataRealm.getNama());
                models.setUmur(dataRealm.getUmur());

                realm.copyFromRealm(models);
            }
        }, onSuccess, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // tampil log
                Log.e(TAG, "" + error.getMessage());
                // tutup realm
                realm.close();
            }
        });
    }

    // menampilkan data realm
    // mengkonversi RealmResults ke ArrayList
    public ArrayList<RealmCobaModels> setSemuaDataNama() {
        // mendapatkan data realm berbentuk realmresults
        RealmResults<RealmCobaModels> realmCobaModels = realm.where(RealmCobaModels.class).findAll();
        // mendeklarasi arraylist
        ArrayList<RealmCobaModels> arrayListModels = new ArrayList<>();

        // validasi
        if (realmCobaModels.size() > 0) { // jika realmresult lebih dari 0
            // membuat looping data
            for (int i = 0; i < realmCobaModels.size(); i++) {
                RealmCobaModels rcm1 = new RealmCobaModels();
                rcm1.setId(realmCobaModels.get(i).getId());
                rcm1.setNama(realmCobaModels.get(i).getNama());
                rcm1.setUmur(realmCobaModels.get(i).getUmur());
                arrayListModels.add(rcm1); // memasukkan ke array list
            }

            // tampil log
            Log.e(TAG, arrayListModels.toString());
        } else { // jika realmresult tidak lebih dari 0, maka kosong!
            // tampil log
            Log.e(TAG, "Tidak Ada Data!");
        }

        return arrayListModels;
    }
}
