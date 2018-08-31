package com.example.minat.se7ex1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddContactActivity extends Activity {

    private EditText name;
    private EditText phone;
    private EditText email;
    private EditText address;
    private EditText city;
    private EditText stateCode;

    private Button saveBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view for the activity using XML
        setContentView(R.layout.activity_add_contact);

        // get references to the widgets
        name = (EditText) findViewById(R.id.nameText);
        phone = (EditText) findViewById(R.id.phoneText);
        email = (EditText) findViewById(R.id.emailText);
        address = (EditText) findViewById(R.id.addressText);
        city = (EditText) findViewById(R.id.cityText);
        stateCode = (EditText) findViewById(R.id.stateCodeText);

        saveBtn = (Button) findViewById(R.id.saveBtn);
    }


    public void onClickSave(View v) {
        DBAdapter db = new DBAdapter(this);
        db.open();
        db.insertContact(name.getText().toString(), phone.getText().toString(),
                                    email.getText().toString(), address.getText().toString(),
                                    city.getText().toString(), stateCode.getText().toString());
        db.close();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        this.finish();
    }
}
