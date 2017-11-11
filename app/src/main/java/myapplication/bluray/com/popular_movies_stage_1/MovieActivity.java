package myapplication.bluray.com.popular_movies_stage_1;

//import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieActivity extends AppCompatActivity {
    //private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        TextView title =  findViewById(R.id.Movie_name);
        ImageView imageView = findViewById(R.id.toolbarImage);
        String s = getIntent().getStringExtra("title");
        String backdrop = getIntent().getStringExtra("backdrop");
        String releaseDates = getIntent().getStringExtra("releaseDate");
        TextView releaseDate = findViewById(R.id.releaseData);
        releaseDate.setText(releaseDates);
        //  TextView overViews = (TextView) findViewById(R.id.overview);
        String overview = getIntent().getStringExtra("discription");
        Picasso.with(this).load(backdrop).into(imageView);
        TextView des = findViewById(R.id.description);
        des.setText(overview);
        title.setText(s);
        TextView vote =  findViewById(R.id.vote);
        double voteString = getIntent().getDoubleExtra("voteAvarage", 0.0);

        vote.setText(Double.toString(voteString));
        ImageView backButton = findViewById(R.id.backs);

        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
