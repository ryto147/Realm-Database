package com.ryto.realmcoba;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import io.realm.Realm;

public class RealmCobaAdapter extends RecyclerView.Adapter<RealmCobaAdapter.MyViewHolder> {

    // implementasi
    private ArrayList<RealmCobaModels> realmCobaModels;
    private Context context;

    private RealmCobaHalper halper;

    // kontruksi adapter
    public RealmCobaAdapter(Context context, ArrayList<RealmCobaModels> realmCobaModels) {
        this.context = context;
        this.realmCobaModels = realmCobaModels;

        // deklarasi helper
        halper = new RealmCobaHalper();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // membuat layout inflater
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_realmcobaadapter,
                parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // menampilkan data per item
        holder.onView(position);
    }

    @Override
    public int getItemCount() {
        // mengembalikan data set
        return realmCobaModels.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // implementasi
        private TextView tvId, tvNama, tvUmur;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // deklarasi
            tvId = itemView.findViewById(R.id.id);
            tvNama = itemView.findViewById(R.id.nama);
            tvUmur = itemView.findViewById(R.id.umur);

            // memberi event klik pada item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // mendapatkan posisi adapter
                    int position = getAdapterPosition();

                    // mendapatkan posisi item
                    RealmCobaModels models = realmCobaModels.get(position);
                    // membuat string pilihan
                    String[] pilih = {"Hapus", "Edit"};

                    // menampilkan alert dialog
                    new AlertDialog.Builder(context)
                            .setTitle("Pilihan")
                            .setItems(pilih, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0: // jika memilih 0, maka sama dengan HAPUS
                                            // halper untuk menghapus data
                                            halper.setHapusDataRealm(models.getId()
                                                    , new Realm.Transaction.OnSuccess() {
                                                        @Override
                                                        public void onSuccess() {
                                                            // jika berhasil akan menampilkan pesan
                                                            Toast.makeText(context, "Berhasil DiHapus!",
                                                                    Toast.LENGTH_SHORT).show();

                                                            // lalu menghapus pada arraylist yang dipilih
                                                            realmCobaModels.remove(position);
                                                            // menyegarkan tampilan recyclerview
                                                            notifyDataSetChanged();
                                                        }
                                                    });
                                            break;
                                        case 1: // jika memilih 1, maka sama dengan EDIT
                                            // pindah halaman dan mengirim data ke MainForm.class
                                            Intent intent = new Intent(context, MainForm.class);
                                            intent.putExtra("iId", models.getId());
                                            intent.putExtra("iNama", models.getNama());
                                            intent.putExtra("iUmur", String.valueOf(models.getUmur()));
                                            context.startActivity(intent);
                                            break;
                                    }
                                }
                            })
                            .create()
                            .show();
                }
            });
        }

        // deklarasi untuk tampilan data
        @SuppressLint("SetTextI18n")
        public void onView(int position) {
            // mendapatkan posisi item
            RealmCobaModels models = realmCobaModels.get(position);

            // menerapkan pada textview
            tvId.setText(models.getId());
            tvNama.setText((position + 1) + "." + models.getNama()); // maksut koding (position + 1), untuk menampilkan nomor urut otomatis.
            tvUmur.setText("" + models.getUmur());
        }
    }
}
