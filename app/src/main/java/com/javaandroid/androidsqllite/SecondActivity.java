package com.javaandroid.androidsqllite;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.javaandroid.androidsqllite.model.Contact;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    public void onSubmitButtonClick(View sender){
        Intent intent = new Intent(this, MainActivity.class);
        TextView txtv1 = findViewById(R.id.editId);
        TextView txtv2 = findViewById(R.id.editName);
        TextView txtv3 = findViewById(R.id.editNumber);
        TextView txtv4 = findViewById(R.id.editEmail);
        Contact contact = new Contact(Integer.parseInt(txtv1.getText().toString()), txtv2.getText().toString(), txtv3.getText().toString(), txtv4.getText().toString());
        intent.putExtra(Contact.class.getSimpleName(), contact);
        startActivity(intent);
    }
}