package com.example.books;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
private ProgressBar progressBar;
    private RecyclerView rv_books;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv_books = (RecyclerView)findViewById(R.id.book_list);
        LinearLayoutManager booksLayoutManager=new LinearLayoutManager(this);
        rv_books.setLayoutManager(booksLayoutManager);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        try {
            URL bookUrl=ApiUtil.buildUrl("cooking");
            new BooksQueryTask().execute(bookUrl);

        }
        catch (Exception e){
            Log.d("Error",e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu,menu);
        final MenuItem searchItem=menu.findItem(R.id.action_serch);
        final SearchView searchView=(SearchView) MenuItemCompat.getActionView(searchItem);
searchView.setOnQueryTextListener(this);
return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        try{
            URL bookUrl=ApiUtil.buildUrl(query);
            new BooksQueryTask().execute(bookUrl);
        }
        catch (Exception e){
            Log.e("Error",e.getMessage());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public class BooksQueryTask extends AsyncTask <URL,Void,String>{

     @Override
     protected String doInBackground(URL... urls) {
         URL searchUrl=urls[0];
         String result=null;
         try {
             result=ApiUtil.getJson(searchUrl);
         } catch (IOException e) {
             Log.d("Error",e.toString());
         }
       return result;
     }

     @Override
     protected void onPreExecute() {
         super.onPreExecute();
         progressBar.setVisibility(View.VISIBLE);
     }

     @Override
     protected void onPostExecute(String result) {
          progressBar.setVisibility(View.INVISIBLE);
         if(result==null){
            rv_books.setVisibility(View.INVISIBLE);
             //tvError.setVisibility(View.VISIBLE);
         }else{
             rv_books.setVisibility(View.VISIBLE);
             //tvError.setVisibility(View.INVISIBLE);
         }
         ArrayList<Book> books= ApiUtil.getBooksFromJson(result);
         String resultString=" ";
         BooksAdapter booksAdapter=new BooksAdapter(books);
         rv_books.setAdapter(booksAdapter);
     }





     }

 }


