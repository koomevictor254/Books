package com.example.books;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.books.databinding.ActivityBookDetailBinding;

public class Book_detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        Book book=getIntent().getParcelableExtra("Book");
        ActivityBookDetailBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_book_detail);
                binding.setBook(book);
    }
}
