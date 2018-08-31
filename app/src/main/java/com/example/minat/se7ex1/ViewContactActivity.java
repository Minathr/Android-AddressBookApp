package com.example.minat.se7ex1;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ViewContactActivity extends AppCompatActivity {

    private TextView id;
    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText address;
    private EditText city;
    private EditText stateCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view for the activity using XML
        setContentView(R.layout.activity_view_contact);

        Long selectedId = getIntent().getLongExtra("idLong", 0);

        // get references to the widgets
        id = (TextView) findViewById(R.id.idText);
        name = (EditText) findViewById(R.id.nameText);
        phone = (EditText) findViewById(R.id.phoneText);
        email = (EditText) findViewById(R.id.emailText);
        address = (EditText) findViewById(R.id.addressText);
        city = (EditText) findViewById(R.id.cityText);
        stateCode = (EditText) findViewById(R.id.stateCodeText);


        DBAdapter db = new DBAdapter(this);
        db.open();
        Cursor c = db.getContact(selectedId);
        if (c.moveToFirst()) {
            id.setText(c.getString(0));
            name.setText(c.getString(1));
            phone.setText(c.getString(2));
            email.setText(c.getString(3));
            address.setText(c.getString(4));
            city.setText(c.getString(5));
            stateCode.setText(c.getString(6));
        }
        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DBAdapter db = new DBAdapter(this);

        Long itemId = Long.parseLong(id.getText().toString());
        switch (item.getItemId()) {
            case R.id.menu_update:
                db.open();
                boolean success = db.updateContact(itemId, name.getText().toString(), phone.getText().toString(),
                        email.getText().toString(), address.getText().toString(),
                        city.getText().toString(), stateCode.getText().toString());
                db.close();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                this.finish();
                return true;
            case R.id.menu_delete:
                db.open();
                db.deleteContact(itemId);
                db.close();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
