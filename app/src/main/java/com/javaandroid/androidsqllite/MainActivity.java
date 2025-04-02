package com.javaandroid.androidsqllite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.javaandroid.androidsqllite.adapter.ContactAdapter;
import com.javaandroid.androidsqllite.model.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ArrayList<Contact> contacts;
    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseHelper = new DatabaseHelper(this);
        contacts = loadContactsFromDatabase();

        Bundle arguments = getIntent().getExtras();
        if (arguments != null) {
            Contact contact = (Contact) arguments.getSerializable(Contact.class.getSimpleName());
            if (contact != null) {
                addContactToDatabase(contact);
                contacts.add(contact);
            }
        }

        ListView contactList = findViewById(R.id.contactList);
        adapter = new ContactAdapter(this, R.layout.list_item, contacts);
        contactList.setAdapter(adapter);
    }

    private ArrayList<Contact> loadContactsFromDatabase() {
        ArrayList<Contact> contactList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID);
            int nameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME);
            int numberIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_YEAR);
            int emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL);

            do {
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String number = cursor.getString(numberIndex);
                String email = cursor.getString(emailIndex);
                contactList.add(new Contact(id, name, number, email));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    private void addContactToDatabase(Contact contact) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_NAME, contact.getName());
        values.put(DatabaseHelper.COLUMN_YEAR, contact.getNumber());
        values.put(DatabaseHelper.COLUMN_EMAIL, contact.getEmail());
        db.insert(DatabaseHelper.TABLE, null, values);
        db.close();
    }
}
