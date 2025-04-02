package com.javaandroid.androidsqllite.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.javaandroid.androidsqllite.DatabaseHelper;
import com.javaandroid.androidsqllite.R;
import com.javaandroid.androidsqllite.model.Contact;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    private final LayoutInflater inflater;
    private final int layout;
    private final ArrayList<Contact> contactList;
    private final DatabaseHelper databaseHelper;

    public ContactAdapter(Context context, int resource, ArrayList<Contact> contacts) {
        super(context, resource, contacts);
        this.contactList = contacts;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.databaseHelper = new DatabaseHelper(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Contact contact = contactList.get(position);
        viewHolder.IdView.setText(String.valueOf(contact.getId()));
        viewHolder.NameView.setText(contact.getName());
        viewHolder.NumberView.setText(contact.getNumber());
        viewHolder.EmailView.setText(contact.getEmail());

        viewHolder.editButton.setOnClickListener(v -> {
            contact.setName("Updated Name");
            updateContactInDatabase(contact);
            notifyDataSetChanged();
        });

        viewHolder.deleteButton.setOnClickListener(v -> {
            deleteContactFromDatabase(contact);
            contactList.remove(position);
            notifyDataSetChanged();
        });

        return convertView;
    }

    private void updateContactInDatabase(Contact contact) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, contact.getName());
        values.put(DatabaseHelper.COLUMN_YEAR, contact.getNumber());
        values.put(DatabaseHelper.COLUMN_EMAIL, contact.getEmail());
        db.update(DatabaseHelper.TABLE, values, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    private void deleteContactFromDatabase(Contact contact) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE, DatabaseHelper.COLUMN_ID + " = ?", new String[]{String.valueOf(contact.getId())});
        db.close();
    }

    private static class ViewHolder {
        final Button editButton, deleteButton;
        final TextView IdView, NameView, NumberView, EmailView;

        ViewHolder(View view) {
            editButton = view.findViewById(R.id.editButton);
            deleteButton = view.findViewById(R.id.deleteButton);
            IdView = view.findViewById(R.id.textID);
            NameView = view.findViewById(R.id.textName);
            NumberView = view.findViewById(R.id.textNumber);
            EmailView = view.findViewById(R.id.textEmail);
        }
    }
}