package com.ryto.realmcoba;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    // implementasi
    private Realm realm;
    private RealmCobaHalper halper;
    private RealmCobaAdapter adapter;

    private RecyclerView recyclerView;

    public static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // deklarasi recyclerview
        recyclerView = findViewById(R.id.rec);

        // agar method bisa diakses publik perlu mendeklarasi ini.
        // terkadang menggunakan cara ini bisa mengakibatkan memori leak saya belum
        // menemukan cara yang lain.
        activity = this;

        // deklarasi untuk realm dan halpernya
        realm = Realm.getInstance(MyApplication.getInstance().setNamaNama());
        halper = new RealmCobaHalper();

        // membuat method setadapter agar bisa di akses publik
        setRecyclerview();
    }

    // mendeklarasi adapter dan recyclerview,
    // ingat method dibuat publik jangan private
    public void setRecyclerview() {
        // deklarasi adapternya
        adapter = new RealmCobaAdapter(this, halper.setSemuaDataNama());

        // deklarasi recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // agar ketika menutup aplikasi, database realm kita tutup.
        realm.close();
    }

    // membuat opsi menu di title bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opsi, menu);

        return true;
    }

    // mendeklarasi opsi menu, pada item yg akan di beri event klik.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tambah:
                startActivity(new Intent(this, MainForm.class));
                break;
        }

        return true;
    }
}
