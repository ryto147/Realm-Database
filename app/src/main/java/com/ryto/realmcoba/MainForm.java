package com.ryto.realmcoba;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import io.realm.Realm;

public class MainForm extends AppCompatActivity {

    // implementasi
    private EditText edNama, edUmur;
    private Button btnSimpan;

    private String iId;
    private static final String TAG = MainForm.class.getSimpleName();

    private Realm realm;
    private RealmCobaHalper halper;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        // mendeklarasi
        edNama = findViewById(R.id.nama);
        edUmur = findViewById(R.id.umur);
        btnSimpan = findViewById(R.id.simpan);

        // mendeklarasi realm dan halpernya
        realm = Realm.getInstance(MyApplication.getInstance().setNamaNama());
        halper = new RealmCobaHalper();

        // khusus ini digunakan untuk update ya!
        // bundle ini digunakan untuk menampung data yang telah dikirim oleh intent
        Bundle bundle = getIntent().getExtras();
        // validasi
        if (bundle != null) { // jika bundle terdapat data maka di eksekusi
            iId = bundle.getString("iId");
            edNama.setText(bundle.getString("iNama"));
            edUmur.setText(bundle.getString("iUmur"));

            // mengubah text buttom
            btnSimpan.setText("Update");
        } else { // jika tidak ada maka kosong!
            // tampilkan log
            Log.e(TAG, "Tidak Ada Data!");
        }

        // memberi event klik pada buttom
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sId = UUID.randomUUID().toString(); // membuat id rondom menggunakan UUID
                String sNama = edNama.getText().toString(); // mengambil nama dari edittext
                String sUmur = edUmur.getText().toString(); // mengambil umur dari edittext

                // validasi
                // jika text pada buttom bertuliskan "Simpan" maka akan mengeksekusi yang ini
                if (btnSimpan.getText().equals("Simpan")) {
                    // validasi
                    if (sNama.isEmpty()) { // jika nama kosong tampil peringatan
                        Toast.makeText(MainForm.this, "Nama Kosong!", Toast.LENGTH_SHORT).show();
                    } else if (sUmur.isEmpty() || sUmur.length() > 9) { // jika umur kosong dan panjang umur lebih dari 9 maka muncul peringatan
                        Toast.makeText(MainForm.this, "Umur Kosong!/Kepanjangan!", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // mengisi di dalam model "RealmCobaModels"
                        RealmCobaModels realmCobaModels = new RealmCobaModels(sId, sNama, Integer.parseInt(sUmur));
                        // halper untuk menyimpan data
                        halper.setSimpanDataRealm(realmCobaModels, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                // jika berhasil, menampilkan pesan
                                Toast.makeText(MainForm.this, "Berhasil Disimpan!", Toast.LENGTH_SHORT).show();

                                // lalu mengkosongkan edittext nama, umur dan memberikan fokus pada edittext nama.
                                edNama.setText("");
                                edUmur.setText("");
                                edNama.requestFocus();
                            }
                        });
                    }
                } else if (btnSimpan.getText().equals("Update")) { // jika text pada buttom bertuliskan "Update" maka akan mengeksekusi yang ini
                    // validasi
                    if (sNama.isEmpty()) { // jika nama kosong muncul peringatan
                        Toast.makeText(MainForm.this, "Nama Kosong!", Toast.LENGTH_SHORT).show();
                    } else if (sUmur.isEmpty() || sUmur.length() > 9) { // jika umur kosong dan panjang umur lebih dari 9 maka muncul peringatan
                        Toast.makeText(MainForm.this, "Umur Kosong!/Kepanjangan!", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // mengisi di dalam model "RealmCobaModels"
                        RealmCobaModels realmCobaModels = new RealmCobaModels(iId, sNama, Integer.parseInt(sUmur));
                        // halper untuk mengupdate data
                        halper.setEditDataRealm(realmCobaModels, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                // jika berhasil akan menampilkan pesan
                                Toast.makeText(MainForm.this, "Berhasil DiUpdate!", Toast.LENGTH_SHORT)
                                        .show();

                                // menutup activity
                                finish();
                                // dan mengaksekusi method setRecyclerview() pada MainActivity.class
                                MainActivity.activity.setRecyclerview();
                            }
                        });
                    }
                } else { // jika text pada buttom tidak bertulisakan apa2
                    // menampilkan log
                    Log.e(TAG, "Tidak Ada Aksi Pada Tombol!");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // agar ketika menutup aplikasi, database realm kita tutup.
        realm.close();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // ketika kita menekan tombol beck pada hp,
        // maka akan mengaksekusi method setRecyclerview() pada MainActivity.class
        MainActivity.activity.setRecyclerview();
    }
}
