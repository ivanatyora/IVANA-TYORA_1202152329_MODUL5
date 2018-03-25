package com.example.android.sqliteprovider;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddToDo extends AppCompatActivity {

    //deklarasi variable yang akan digunakan
    private EditText ToDo, Description, Priority;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);

        setTitle("Add To Do"); //set title menjadi add to do

        //merefer semua variabel yang ada
        ToDo = (EditText) findViewById(R.id.edt_Todo);
        Description = (EditText) findViewById(R.id.edt_Desc);
        Priority = (EditText) findViewById(R.id.edt_Priority);
        db = new Database(this);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(AddToDo.this, MainActivity.class); //intent menuju MainActivity
        startActivity(intent);   //memulai intent
        this.finish(); //menutup aktivitas setelah intent dijalankan
    }

    public void addTodo(View view) {
        //kondisi apabila data todoname, deskripsi dan prioritas di isi
        if (db.inputdata(new AddData(ToDo.getText().toString(), Description.getText().toString(), Priority.getText().toString()))){

            Toast.makeText(this, "To Do List Ditambahkan !", Toast.LENGTH_SHORT).show(); //maka akan menampilkan toast bawha data berhasil di tambahkan ke dalam list
            startActivity(new Intent(AddToDo.this, MainActivity.class)); //intent ke mainActivity
            this.finish(); //menutup aktivitas agar tidak kembali ke activity yang dijalankan setelah intent

        }else {

            Toast.makeText(this, "List tidak boleh kosong", Toast.LENGTH_SHORT).show(); //akan muncul toast bahwa tidak bisa menambah ke dalam list
            ToDo.setText(null); //set semua edit text menjadi kosong
            Description.setText(null);
            Priority.setText(null);
        }
    }

}
