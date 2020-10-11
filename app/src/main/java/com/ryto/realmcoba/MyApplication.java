package com.ryto.realmcoba;

import android.app.Application;

import androidx.annotation.Nullable;

import io.realm.DynamicRealm;
import io.realm.FieldAttribute;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/*
 * jika membuat class Application
 * jangan lupa tambahkan pada manifest ya
 *
 * <application
 *       android:name=".MyApplication" <---- yg ini
 *       ...
 *       ...
 *
 * disini saya tidak membuat konfigurasi default realm database melainkan membuat custom.
 */

public class MyApplication extends Application {

    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    // membuat konfigurasi realm (ingat konfigurasi ini tidak default ya!)
    public RealmConfiguration setNamaNama() {
        Realm.init(mInstance);
        return new RealmConfiguration.Builder()
                .name("NamaNama.realm") // nama realm database
                .schemaVersion(0) // versi schema databesa yang digunakan
                .migration(new MyNamaNama()) // membuat sekema migrasi
                .build();
    }

    private static class MyNamaNama implements RealmMigration {

        @Override
        public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
            // mendapatkan sekema realm database untuk mengedit, menambah, dan menghapus kolom maupun kelas
            RealmSchema schema = realm.getSchema();

            // validasi
            // jika versi sama dengan 0 membuat sekema baru
            if (oldVersion == 0) {
                schema.create("RealmModels")
                        .addField("id", String.class, FieldAttribute.PRIMARY_KEY)
                        .addField("nama", String.class)
                        .addField("umur", int.class);
                oldVersion++;
            }
        }

        @Override
        public int hashCode() {
            return MyNamaNama.class.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj == null) {
                return false;
            }

            return obj instanceof MyNamaNama;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
