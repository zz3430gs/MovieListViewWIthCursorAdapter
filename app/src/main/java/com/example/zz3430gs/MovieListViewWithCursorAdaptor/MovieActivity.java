package com.example.zz3430gs.MovieListViewWithCursorAdaptor;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;

import com.example.zz3430gs.moviedb.R;

public class MovieActivity extends AppCompatActivity implements MovieCursorAdapter.RatingChangedListener{

    private static final String TAG = "MOVIE ACTIVITY";
    DatabaseManager dbManager;
    MovieCursorAdapter cursorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        dbManager = new DatabaseManager(this);

        Button addNew = (Button) findViewById(R.id.add_movie_button);
        final EditText newMovieNameET = (EditText) findViewById(R.id.add_movie_name);
        final RatingBar newMovieRB = (RatingBar) findViewById(R.id.add_movie_rating_bar);

        final ListView movieList = (ListView) findViewById(R.id.movie_list_view);
        final Cursor cursor = dbManager.getAllMovies();
        cursorListAdapter = new MovieCursorAdapter(this, cursor, true);
        movieList.setAdapter(cursorListAdapter);

        //TODO create cursor
        //TODO create CursorAdapter
        //TODO Configure ListView to use this adapter

        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = newMovieNameET.getText().toString();
                float rating = newMovieRB.getRating();
                dbManager.addMovie(name, rating); //todo detect errors and handle appropriately
                cursorListAdapter.changeCursor(dbManager.getAllMovies());
                //TODO Update list

            }
        });

    }
    public void notifyRatingChanged(int movieID, float rating) {
        dbManager.updateRating(movieID,rating);
        cursorListAdapter.changeCursor(dbManager.getAllMovies());

    }


    //Don't forget these! Close and re-open DB as Activity pauses/resumes.

    @Override
    protected void onPause(){
        super.onPause();
        dbManager.close();
    }

    @Override
    protected void onResume(){
        super.onResume();
        dbManager = new DatabaseManager(this);
    }

}
