package com.ryto.realmcoba;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.RealmField;
import io.realm.annotations.Required;

/*
 * jangan lupa tambahkan anotasinya ya
 *
 * @Required : Untuk memeriksa agar tidak membolehkan bernilai null.
 * @Ignore : Kolom tidak harus disimpan.
 * @Index : Menambahkan index pencarian pada kolom.
 * @PrimaryKey : Identifikasi unik pada tiap data pada database, tetapi tidak menyediakan auto-increment.
 *
 * ingat jangan gunakan @Required pada nilai "int" aplikasi anda akan force close
 * karena nilai @Required itu bernilai "null" bukan "0"
 *
 */

public class RealmCobaModels extends RealmObject {

    @PrimaryKey
    @Required
    @RealmField(name = "id") // Nama yang digunakan dalam file Realm
    private String id; // Nama yang digunakan java
    @Required
    @RealmField(name = "nama") // Nama yang digunakan dalam file Realm
    private String nama; // Nama yang digunakan java
    @RealmField(name = "umur") // Nama yang digunakan dalam file Realm
    private int umur; // Nama yang digunakan java

    public RealmCobaModels() {
    }

    public RealmCobaModels(String id, String nama, int umur) {
        this.id = id;
        this.nama = nama;
        this.umur = umur;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getUmur() {
        return umur;
    }

    public void setUmur(int umur) {
        this.umur = umur;
    }

    @Override
    public String toString() {
        return "RealmCobaModels{" +
                "id='" + id + '\'' +
                ", nama='" + nama + '\'' +
                ", umur=" + umur +
                '}';
    }
}
