package com.example.neethu.volleysample;


import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by neethu on 21/1/16.
 */
public interface BooksAPI {
    @GET("/RetrofitExample/books.json")
    public void getBooks(Callback<List<Book>> response);
}
