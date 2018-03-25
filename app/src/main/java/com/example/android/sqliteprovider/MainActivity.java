package com.example.android.sqliteprovider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //deklarasi variabel yang akan digunakan
    private Database dtBase;
    private RecyclerView rcView;
    private Adapter adapter;
    private ArrayList<AddData> data_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("To Do List App");  //set title menjadi To Do List

        rcView = findViewById(R.id.rec_view); //mengakses recyclerview berdasarkan id

        data_list = new ArrayList<>();    //membuat araylist baru
        dtBase = new Database(this); //membuat database baru
        dtBase.readdata(data_list); //memanggil method readdata

        //menginisialisasi shared preference
        SharedPreferences sharedP = this.getApplicationContext().getSharedPreferences("Preferences", 0);
        int color = sharedP.getInt("Colourground", R.color.white);


        adapter = new Adapter(this,data_list, color); //membuat adapter baru

        rcView.setHasFixedSize(true); //mengatur perubahan ukuran
        rcView.setLayoutManager(new LinearLayoutManager(this));

        rcView.setAdapter(adapter); //inisiasi adapter untuk recycler view
        hapusgeser(); //menjalankan method hapus data pada list to do
    }

    //membuat method untuk menghapus item pada to do list
    public void hapusgeser(){
        //membuat touch helper baru untuk recycler view
        ItemTouchHelper.SimpleCallback touchcall = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                AddData current = adapter.getData(position);

                //apabila item di swipe ke arah kiri
                if(direction==ItemTouchHelper.LEFT){

                    if(dtBase.removedata(current.getTodo())){ //remove item yang dipilih dengan mengenali todonya sebagai primary key
                        adapter.deleteData(position);
                        Snackbar.make(findViewById(R.id.coordinator), "List Telah Terhapus", 2000).show(); //membuat snack bar dan pemberitahuan bahwa item sudah terhapus dengan durasi 2 sekon
                    }
                }
            }
        };

        //menentukan itemtouchhelper untuk recycler view
        ItemTouchHelper touchhelp = new ItemTouchHelper(touchcall);
        touchhelp.attachToRecyclerView(rcView);
    }

    //ketika menu pada activity di buat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //method yang dijalankan ketika item di pilih
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId(); //mendapatkan id dari item yang

        if (id==R.id.action_settings){ //kondisi apabila item yang dipilih adalah setting

            Intent intent = new Intent(MainActivity.this, Settings.class); //membuat intent ke menu Settings
            startActivity(intent);
            finish();
        }
        return true;
    }

    //method yang akan dijalankan ketika tombol add di klik
    public void addlist(View view) {
        Intent intent = new Intent(MainActivity.this, AddToDo.class);
        startActivity(intent);
    }
}
