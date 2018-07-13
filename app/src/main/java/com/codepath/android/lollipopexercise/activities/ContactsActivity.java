package com.codepath.android.lollipopexercise.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.codepath.android.lollipopexercise.R;
import com.codepath.android.lollipopexercise.adapters.ContactsAdapter;
import com.codepath.android.lollipopexercise.models.Contact;

import java.util.List;

// Make sure to import this line at the top!

public class ContactsActivity extends AppCompatActivity {
    private RecyclerView rvContacts;
    private ContactsAdapter mAdapter;
    private List<Contact> contacts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        // Find RecyclerView and bind to adapter
        rvContacts = findViewById(R.id.rvContacts);

        // allows for optimizations
        rvContacts.setHasFixedSize(true);

        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(ContactsActivity.this, 2);

        // Unlike ListView, you have to explicitly give a LayoutManager to the RecyclerView to position items on the screen.
        // There are three LayoutManager provided at the moment: GridLayoutManager, StaggeredGridLayoutManager and LinearLayoutManager.
        rvContacts.setLayoutManager(layout);

        // get data
        contacts = Contact.getContacts();

        // Create an adapter
        mAdapter = new ContactsAdapter(ContactsActivity.this, contacts);

        // Bind adapter to list
        rvContacts.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Handle presses on the action bar items
        switch (id) {
            case R.id.miCompose:
                onComposeAction();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // Define the click listener as a member
    View.OnClickListener myOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            contacts.remove(0);
            mAdapter.notifyItemChanged(0);
            rvContacts.scrollToPosition(0);
        }
    };

    public void onComposeAction() {
        Contact contact = Contact.getRandomContact(this);
        contacts.add(contact);
        mAdapter.notifyItemChanged(0);
        rvContacts.scrollToPosition(0);
        Snackbar.make(rvContacts, R.string.snackbar_text, Snackbar.LENGTH_LONG)
                .setAction(R.string.snackbar_action, myOnClickListener)
                .show(); // Don’t forget to show!
    }
}

