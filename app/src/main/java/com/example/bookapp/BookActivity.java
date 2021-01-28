package com.example.bookapp;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.example.bookapp.databinding.ActivityBookBinding;

import java.util.List;

import static com.example.bookapp.DataSaver.POSITION;

public class BookActivity extends AppCompatActivity {
    DataSaver dataSaver;
    Book book;
    ActivityBookBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_book);
        dataSaver = DataSaver.getInstance(this);

        dataSaver.getBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {

                book = dataSaver.getBooks().getValue().get(getIntent().getIntExtra(POSITION,0));
                binding.setBook(book);

//                bookPhoto.setImageResource(book.getImgRes());
//                bookTitle.setText(book.getTitle());
//                bookDescription.setText(book.getDescription());
            }
        });

    }


    public void goToEitBookAction(View view) {
        Intent intent = new Intent(BookActivity.this,EditActivity.class);
        intent.putExtra(POSITION,dataSaver.getBooks().getValue().indexOf(book));
        startActivity(intent);
    }
}