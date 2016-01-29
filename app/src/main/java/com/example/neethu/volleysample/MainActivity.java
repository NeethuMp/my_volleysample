package com.example.neethu.volleysample;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {


    public static final String ROOT_URL = "http://simplifiedcoding.16mb.com/";


    public static final String KEY_BOOK_ID = "key_book_id";
    public static final String KEY_BOOK_NAME = "key_book_name";
    public static final String KEY_BOOK_PRICE = "key_book_price";
    public static final String KEY_BOOK_STOCK = "key_book_stock";
    private ListView listView;


    private List<Book> books;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        listView = (ListView) findViewById(R.id.listViewBooks);

        //Calling the method that will fetch data
        getBooks();

        //Setting onItemClickListener to listview
        listView.setOnItemClickListener(this);
    }

    private void getBooks() {
        //While the app fetched data we are displaying a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Fetching Data", "Please wait...", false, false);

        //Creating a rest adapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();

        //Creating an object of our api interface
        BooksAPI api = adapter.create(BooksAPI.class);

        //Defining the method
        api.getBooks(new Callback<List<Book>>() {
            @Override
            public void success(List<Book> list, Response response) {
                //Dismissing the loading progressbar
                loading.dismiss();

                //Storing the data in our list
                books = list;
                Log.d("RETRO_RESP ", response.getBody().toString());
                //Calling a method to show the list
                showList();
            }

            @Override
            public void failure(RetrofitError error) {
                //you can handle the errors here
            }
        });
    }


    //Our method to show list
    private void showList() {
        //String array to store all the book names
        String[] items = new String[books.size()];

        //Traversing through the whole list to get all the names
        for (int i = 0; i < books.size(); i++) {
            //Storing names to string array
            items[i] = books.get(i).name;
        }

        //Creating an array adapter for list view
        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.simple_list, items);

        //Setting adapter to listview
        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Creating an intent
        Intent intent = new Intent(this, ShowBookDetails.class);

        //Getting the requested book from the list
        Book book = books.get(position);

        //Adding book details to intent
        intent.putExtra(KEY_BOOK_ID, book.bookId);
        intent.putExtra(KEY_BOOK_NAME, book.name);
        intent.putExtra(KEY_BOOK_PRICE, book.price);
        intent.putExtra(KEY_BOOK_STOCK, book.inStock);

        //Starting another activity to show book details
        startActivity(intent);
    }

}

