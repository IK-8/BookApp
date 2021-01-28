package com.example.bookapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.bookapp.DataSaver.POSITION;


public class EditActivity extends AppCompatActivity {
    DataSaver dataSaver;


    EditText bookTitle;
    EditText bookAuthor;
    EditText bookDescription;
    Book book = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        dataSaver = DataSaver.getInstance(this);


        ImageView   bookPhoto = findViewById(R.id.bookPhoto);
        bookTitle = findViewById(R.id.bookTitle);
        bookDescription = findViewById(R.id.bookDescription);
        bookAuthor = findViewById(R.id.bookAuthor);


        int position =getIntent()
                .getIntExtra(POSITION,-1);
        if(position!=-1) {
            book = dataSaver.getBooks().getValue().get(position);
                    bookPhoto.setImageResource(book.getImgRes());
                    bookTitle.setText(book.getTitle());
                    bookDescription.setText(book.getDescription());
                    bookAuthor.setText(book.getAuthor());

        }


    }


    public void saveBookAction(View view) {
        if(book==null)
            dataSaver.addBook(new Book(R.drawable.photo_book,bookTitle.getText().toString(),
                bookAuthor.getText().toString(),bookDescription.getText().toString(),125));
        else
        {
            book.setAuthor(bookAuthor.getText().toString());
            book.setDescription(bookDescription.getText().toString());
            book.setTitle(bookTitle.getText().toString());
            book.setImgRes(R.drawable.photo_book);
            book.setPrice(450);
            dataSaver.updateBook(book);
        }
    }
}