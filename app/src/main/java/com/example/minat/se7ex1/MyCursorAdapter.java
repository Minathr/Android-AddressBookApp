package com.example.minat.se7ex1;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {
    public MyCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    // The newView method is used to inflate a new view and return it,
    // you don't bind any data to the view at this point.
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_contact, parent, false);
    }

    // The bindView method is used to bind all data to a given view
    // such as setting the text on a TextView.
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // Find fields to populate in inflated template
        TextView id = (TextView) view.findViewById(R.id.IdLabel);
        TextView name = (TextView) view.findViewById(R.id.nameLabel);
        TextView phone = (TextView) view.findViewById(R.id.phoneLabel);
        TextView email = (TextView) view.findViewById(R.id.emailLabel);
        TextView address = (TextView) view.findViewById(R.id.addressLabel);
        TextView city = (TextView) view.findViewById(R.id.cityLabel);
        TextView stateCode = (TextView) view.findViewById(R.id.stateCodeLabel);

        // Extract properties from cursor & Populate fields with extracted properties
        id.setText(cursor.getString(0));
        name.setText(cursor.getString(1));
        phone.setText(cursor.getString(2));
        email.setText(cursor.getString(3));
        address.setText(cursor.getString(4));
        city.setText(cursor.getString(5));
        stateCode.setText(cursor.getString(6));
    }
}