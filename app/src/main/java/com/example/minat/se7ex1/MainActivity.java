package com.example.minat.se7ex1;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    ListView contactList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactList = (ListView) findViewById(android.R.id.list);

        DBAdapter db = new DBAdapter(this);

        //--get all contacts---
        //Opens a DB connection and returns all records currently available.
        //Scrolls through the Cursor "Toasting" one at a time.
        db.open();
        Cursor c = db.getAllContacts();
        c.moveToFirst();
        MyCursorAdapter myAdaptor = new MyCursorAdapter(this, c);
        contactList.setAdapter(myAdaptor);
        db.close();

        contactList.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                startActivity(new Intent(getApplicationContext(), AddContactActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id){

        Intent myIntent = new Intent(getApplicationContext(), ViewContactActivity.class);
        myIntent.putExtra("idLong", id);
        startActivity(myIntent);
    }
}
