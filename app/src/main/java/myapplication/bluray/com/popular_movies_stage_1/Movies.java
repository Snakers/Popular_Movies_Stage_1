package myapplication.bluray.com.popular_movies_stage_1;


public class Movies {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    private String posterPath;
    private String overViews;
    private double voteAvarage;
    private String releaseData;
    private String orignalTitle;
    public static String portUrl = "http://image.tmdb.org/t/p/original";
    public static String landUrl = "https://image.tmdb.org/t/p/w1920";
    private String backdrop_path;

    public String getBackdrop_path() {


        return "http://image.tmdb.org/t/p/original" + backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public static String getDateFormat() {
        return DATE_FORMAT;
    }

    public String getPosterPath() {


        return portUrl + posterPath;

    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;

    }

    public String getOverViews() {
        return overViews;
    }

    public void setOverViews(String overViews) {
        this.overViews = overViews;
    }

    public double getVoteAvarage() {
        return voteAvarage;
    }

    public void setVoteAvarage(double voteAvarage) {
        this.voteAvarage = voteAvarage;
    }

    public String getReleaseData() {
        return releaseData;
    }

    public void setReleaseData(String releaseData) {
        this.releaseData = releaseData;
    }

    public String getOrignalTitle() {
        return orignalTitle;
    }

    public void setOrignalTitle(String orignalTitle) {
        this.orignalTitle = orignalTitle;
    }

    Movies(String posterPath, String releaseData, String orignalTitle, double voteAvarage, String overViews) {
        this.posterPath = posterPath;
        this.releaseData = releaseData;
        this.orignalTitle = orignalTitle;
        this.voteAvarage = voteAvarage;
        this.overViews = overViews;

    }

    Movies() {

    }

}
