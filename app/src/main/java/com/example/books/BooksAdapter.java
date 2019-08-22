package com.example.books;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {
    private final ArrayList<Book> books;


    public BooksAdapter(ArrayList<Book> books){




        this.books = books;
    }


    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View ItemView=LayoutInflater.from(context).inflate(R.layout.books_list,parent,false);
        return new BookViewHolder(ItemView);

    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
    Book book=books.get(position);
    holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {

       public final TextView tvTitle;
       public final TextView tvAuthors;

       public final TextView tvDate;
       public final TextView tvPublisher;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle=(TextView)itemView.findViewById(R.id.book_title);
            tvAuthors=(TextView)itemView.findViewById(R.id.author);
            tvDate=(TextView)itemView.findViewById(R.id.published_date);
            tvPublisher=(TextView)itemView.findViewById(R.id.publisher);
itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
    int position=getAdapterPosition();
    Book selectedBook=books.get(position);
    Intent intent=new Intent(view.getContext(),Book_detail.class);
    intent.putExtra("Book",selectedBook);
    view.getContext().startActivity(intent);
    }
});
        }
        public void bind(Book book){
            tvTitle.setText(book.title);

            tvAuthors.setText(book.authors);
            tvPublisher.setText(book.publisher);
            tvDate.setText(book.publishedDate);

        }


    }
}
