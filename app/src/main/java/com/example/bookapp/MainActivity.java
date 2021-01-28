package com.example.bookapp;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static com.example.bookapp.DataSaver.POSITION;


public class MainActivity extends AppCompatActivity {
    LayoutInflater inflater;
    DataSaver dataSaver;
    static BookAdapter adapter;
    public static void costil()
    {
        adapter.notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater = getLayoutInflater();
        dataSaver = DataSaver.getInstance(this);
        dataSaver.getBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.notifyDataSetChanged();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler);
        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);
    }

    public void addBookAction(View view) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
//        intent.putExtra(POSITION,position);
        startActivity(intent);
    }

    private class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(inflater.inflate(R.layout.item_book,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull final  ViewHolder holder,final int position) {
            Book book = dataSaver.getBooks().getValue().get(position);
            holder.bookPhoto.setImageResource(book.getImgRes());
            holder.bookAuthor.setText(book.getAuthor());
            holder.bookTitle.setText(book.getTitle());
            holder.bookPrice.setText(book.getStringPrice()  );
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,BookActivity.class);
                    intent.putExtra(POSITION,position);
                    startActivity(intent, ActivityOptions
                            .makeSceneTransitionAnimation(MainActivity.this,
                                    holder.bookPhoto,
                                    getResources().getString(R.string.trans)).toBundle());
                }
            });
        }

        @Override
        public int getItemCount() {
            return dataSaver.getBooks().getValue().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            final ImageView bookPhoto;
            final TextView bookTitle;
            final TextView bookAuthor;
            final TextView bookPrice;
            final  View view;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                bookPhoto = itemView.findViewById(R.id.bookPhoto);
                bookTitle = itemView.findViewById(R.id.bookTitle);
                bookAuthor = itemView.findViewById(R.id.bookAuthor);
                bookPrice = itemView.findViewById(R.id.bookPrice);
                view = itemView;
            }
        }
    }
}