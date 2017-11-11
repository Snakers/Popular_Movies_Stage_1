package myapplication.bluray.com.popular_movies_stage_1;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements AdapterRecy.ListenerClick {
    private static final String URL_BASED_POPULAR = "http://api.themoviedb.org/3/movie/popular";
    private int drawableResourceId = R.drawable.menu;
    private AsyncTask as;
    private FloatingActionButton btn, btnDown;
    private Menu menu;
    private TextView errorText;
    private static boolean off = false;
    private Movies[] returned_data;
    private ProgressBar prog;
    private ProgressBar prog2;
    AdapterRecy.ListenerClick listenerClick;
    private static final String API_QUERY_KEY = "api_key";
    private static final String API_KEY = "fa1f527110bfa3592e9ef452a25eadb2";
    private static final String URL_BASED_RATE = "https://api.themoviedb.org/3/movie/top_rated";
    private AdapterRecy adapterRecy;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        menu.findItem(R.id.op).setIcon(drawableResourceId);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (R.id.op == i) {

            if (drawableResourceId == R.drawable.menu) {
                as = null;
                setTitle("High Rated Movies");
                as = new Fetcher().execute((popularurl(URL_BASED_RATE)));

                drawableResourceId = R.mipmap.menu_popu;

                invalidateOptionsMenu();


            } else if (drawableResourceId == R.mipmap.menu_popu) {
                as = new Fetcher().execute((popularurl(URL_BASED_POPULAR)));

                drawableResourceId = R.drawable.menu;
                setTitle("Most Popular Movies");
                invalidateOptionsMenu();
            }


        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        prog = findViewById(R.id.progressBar);
        btn = findViewById(R.id.upFAB);

        adapterRecy = new AdapterRecy(this, this);

        final LinearLayoutManager gridLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        RecyclerView recyclerView;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            setContentView(R.layout.landy);
            btnDown = findViewById(R.id.landFab);
            final GridLayoutManager g = new GridLayoutManager(this, 3);
            g.setAutoMeasureEnabled(true);
            gridLayoutManager.setAutoMeasureEnabled(true);
            recyclerView = findViewById(R.id.recy);
            recyclerView.setLayoutManager(g);
            recyclerView.setAdapter(adapterRecy);

            btnDown.setImageResource(R.mipmap.updown);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (gridLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {
                        btnDown.setImageResource(R.mipmap.upland);

                        btnDown.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                g.isSmoothScrollbarEnabled();

                                g.scrollToPositionWithOffset(0, 0);

                            }
                        });
                    }
                }
            });

            Movies.portUrl = "https://image.tmdb.org/t/p/w185";

        } else {
            recyclerView = findViewById(R.id.reycler);
            Movies.portUrl = "http://image.tmdb.org/t/p/original";
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapterRecy);
            btn.setImageResource(R.mipmap.updown);

            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    if (gridLayoutManager.findFirstCompletelyVisibleItemPosition() != 0) {


                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                gridLayoutManager.scrollToPositionWithOffset(0, 0);


                            }
                        });
                    } else {
                        btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                gridLayoutManager.isSmoothScrollbarEnabled();
                                recyclerView.scrollToPosition(returned_data.length - 1);

                            }
                        });
                    }
                }
            });
        }


        as = new Fetcher().execute((popularurl(URL_BASED_POPULAR)));
    }


    class Fetcher extends AsyncTask<URL, Void, Movies[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            prog.setVisibility(View.VISIBLE);

        }

        @Override
        protected Movies[] doInBackground(URL... urls) {
            URL url = urls[0];
            String last;

            try {


                last = NetworkUrl.getResponseFromUrl(url);
                returned_data = moviesJson(last);

                return returned_data;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(Movies[] movies) {
            if (movies != null) {
                prog.setVisibility(View.INVISIBLE);

                adapterRecy.setWeatherData(movies);
                super.onPostExecute(movies);
            } else {
                prog.setVisibility(View.VISIBLE);

            }

        }
    }


    private static URL popularurl(String mode) {
        Uri build = Uri.parse(mode).buildUpon().appendQueryParameter(API_QUERY_KEY, API_KEY).build();
        Log.v("mahz", build.toString());
        URL url = null;
        try {
            url = new URL(build.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v("urlString", url.toString());

        return url;
    }

    private Movies[] moviesJson(String moviesJsonStr) throws JSONException {
        // JSON tags
        final String TAG_RESULTS = "results";
        final String TAG_ORIGINAL_TITLE = "original_title";
        final String TAG_POSTER_PATH = "poster_path";
        final String TAG_OVERVIEW = "overview";
        final String TAG_VOTE_AVERAGE = "vote_average";
        final String TAG_RELEASE_DATE = "release_date";
        final String BACKDROP_PATH = "backdrop_path";

        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray resultsArray = moviesJson.getJSONArray(TAG_RESULTS);

        Movies[] movies = new Movies[resultsArray.length()];

        for (int i = 0; i < resultsArray.length(); i++) {
            movies[i] = new Movies();

            JSONObject movieInfo = resultsArray.getJSONObject(i);

            movies[i].setOrignalTitle(movieInfo.getString(TAG_ORIGINAL_TITLE));
            movies[i].setPosterPath(movieInfo.getString(TAG_POSTER_PATH));
            movies[i].setOverViews(movieInfo.getString(TAG_OVERVIEW));
            movies[i].setVoteAvarage(movieInfo.getDouble(TAG_VOTE_AVERAGE));
            movies[i].setReleaseData(movieInfo.getString(TAG_RELEASE_DATE));
            movies[i].setBackdrop_path(movieInfo.getString(BACKDROP_PATH));
            Log.v("Hold On", movies[i].getPosterPath());

        }
        return movies;
    }

    @Override
    public void onItemListner(int itemIndex) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        Movies[] movies = adapterRecy.getMovies();

        intent.putExtra("title", movies[itemIndex].getOrignalTitle());
        intent.putExtra("image", movies[itemIndex].getPosterPath());
        intent.putExtra("releaseDate", movies[itemIndex].getReleaseData());
        intent.putExtra("discription", movies[itemIndex].getOverViews());
        intent.putExtra("image", movies[itemIndex].getPosterPath());
        intent.putExtra("backdrop", movies[itemIndex].getBackdrop_path());
        intent.putExtra("voteAvarage", movies[itemIndex].getVoteAvarage());


        startActivity(intent);

    }


}