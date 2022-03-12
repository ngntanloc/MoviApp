package com.example.moviapp.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviapp.AppExecutors;
import com.example.moviapp.Models.Cast;
import com.example.moviapp.Models.Genre;
import com.example.moviapp.Models.MovieDetails;
import com.example.moviapp.Models.MovieModel;
import com.example.moviapp.response.CastSearchResponse;
import com.example.moviapp.response.GetGenreResponse;
import com.example.moviapp.response.MovieSearchResponse;
import com.example.moviapp.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private static MovieApiClient instance;

    // Making Global Request
    private RetrieveMovieNowPlayingRunnable retrieveMovieNowPlayingRunnable;
    private RetrieveMovieDetailRunnable retrieveMovieDetailRunnable;
    private RetrieveCastInformationSummary retrieveCastInformationSummary;
    private RetrieveMovieUpcoming retrieveMovieUpcoming;
    private RetrieveSearchMovie retrieveSearchMovie;
    private RetrieveSearchGenres retrieveSearchGenres;
    private RetrieveMovieDetailList retrieveMovieDetailList;

    // LiveData for nowPlayingMovies
    private MutableLiveData<List<MovieModel>> mMoviesNowPlaying;

    // Live Data for MovieDetails
    private MutableLiveData<MovieDetails> mMoviesDetails;

    // Live Data for CastInfo
    private MutableLiveData<List<Cast>> mCastInfo;

    // Live Data for Upcoming Movies
    private MutableLiveData<List<MovieModel>> mMoviesUpcoming;

    // Live Data for Searching movie
    private MutableLiveData<List<MovieModel>> mSearchMovies;

    // Live Data for searchAllGenre
    private MutableLiveData<List<Genre>> mSearchGenres;

    // Live Data for searching watch list
    private MutableLiveData<List<MovieDetails>> mMovieWatchList;

    public static MovieApiClient getInstance() {
        if (instance == null)
            instance = new MovieApiClient();
        return instance;
    }

    public MovieApiClient() {
        mMoviesNowPlaying = new MutableLiveData<>();
        mMoviesDetails = new MutableLiveData<>();
        mCastInfo = new MutableLiveData<>();
        mMoviesUpcoming = new MutableLiveData<>();
        mSearchMovies = new MutableLiveData<>();
        mSearchGenres = new MutableLiveData<>();
    }

    public void searchMovieNowPlaying(int pageNumber) {
        if (retrieveMovieNowPlayingRunnable != null) {
            retrieveMovieNowPlayingRunnable = null;
        }

        retrieveMovieNowPlayingRunnable = new RetrieveMovieNowPlayingRunnable(pageNumber);

        final Future myHandlerNowPlaying = AppExecutors.getInstance().networkIO().submit(retrieveMovieNowPlayingRunnable);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlerNowPlaying.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    public void searchMovieDetails(int movieID) {
        if (retrieveMovieDetailRunnable != null) {
            retrieveMovieDetailRunnable = null;
        }

        retrieveMovieDetailRunnable = new RetrieveMovieDetailRunnable(movieID);

        final Future myHandlerMovieDetail = AppExecutors.getInstance().networkIO().submit(retrieveMovieDetailRunnable);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlerMovieDetail.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }


    public void searchCastInformationSummary(int movieID) {
        if (retrieveCastInformationSummary != null) {
            retrieveCastInformationSummary = null;
        }

        retrieveCastInformationSummary = new RetrieveCastInformationSummary(movieID);

        final Future myHandlerCastInfo = AppExecutors.getInstance().networkIO().submit(retrieveCastInformationSummary);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandlerCastInfo.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    public void searchUpcomingMovies(int pageNumber) {
        if (retrieveMovieUpcoming != null)
            retrieveMovieUpcoming = null;

        retrieveMovieUpcoming = new RetrieveMovieUpcoming(pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMovieUpcoming);
        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }



    public void searchMovies(String query, int pageNumber) {
        if (retrieveSearchMovie != null) {
            retrieveSearchMovie = null;
        }

        retrieveSearchMovie = new RetrieveSearchMovie(query, pageNumber);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveSearchMovie);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    public void searchGenres() {
        if (retrieveSearchGenres != null) {
            retrieveSearchGenres = null;
        }

        retrieveSearchGenres = new RetrieveSearchGenres();

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveSearchGenres);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);

    }

    public void searchMovieWatchList(List<Integer> movieID) {
        if (retrieveMovieDetailList != null) {
            retrieveMovieDetailList = null;
        }
        retrieveMovieDetailList = new RetrieveMovieDetailList(movieID);

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMovieDetailList);

        AppExecutors.getInstance().networkIO().schedule(new Runnable() {
            @Override
            public void run() {
                myHandler.cancel(true);
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    private class RetrieveCastInformationSummary implements Runnable {
        private int movieID;
        boolean cancelRequest;

        public RetrieveCastInformationSummary(int movieID) {
            this.movieID = movieID;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {

                Response response = getCastInfor(movieID).execute();

                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<Cast> casts = new ArrayList<>(((CastSearchResponse) response.body()).getmCast());
                    mCastInfo.postValue(casts);
                } else {
                    mCastInfo.postValue(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Call<CastSearchResponse> getCastInfor(int movieID) {
            return Servicey.getMovieApi().getSummaryCastInfo(
                    movieID,
                    Credentials.API_KEY
            );
        }

        private void cancelRequest() {
            Log.d("Tagy", "Canceling search request cast information's summary");
            cancelRequest = true;
        }
    }

    private class RetrieveMovieDetailRunnable implements Runnable {
        private int movieID;
        boolean cancelRequest;

        public RetrieveMovieDetailRunnable(int movieID) {
            this.movieID = movieID;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getMovieDetail(movieID).execute();
                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    MovieDetails movieDetails = (MovieDetails) response.body();
                    mMoviesDetails.postValue(movieDetails);
                } else {
                    mMoviesDetails.postValue(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private Call<MovieDetails> getMovieDetail(int movieID) {
            return Servicey.getMovieApi().getMoviesDetails(
                    movieID,
                    Credentials.API_KEY
            );
        }

        private void cancelRequest() {
            Log.d("Tagy", "Canceling search request now playing movie");
            cancelRequest = true;
        }
    }

    private class RetrieveMovieDetailList implements Runnable {
        private List<MovieDetails> movies;
        private List<Integer> movieID;
        boolean cancelRequest;

        public RetrieveMovieDetailList(List<Integer> movieID) {
            this.movieID = movieID;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                for (Integer id: movieID
                     ) {

                    Response response = getMovieDetail(id).execute();
                    if (cancelRequest) {
                        return;
                    }

                    if (response.code() == 200) {
                        MovieDetails movieDetails = (MovieDetails) response.body();
                        movies.add(movieDetails);
                    } else {
                        Log.d("Tagy", "Warning at Retrieve data");
                    }

                }
                mMovieWatchList.postValue(movies);

            } catch (Exception e) {
                e.printStackTrace();
                mMovieWatchList.postValue(null);
            }
        }

        private Call<MovieDetails> getMovieDetail(int movieID) {
            return Servicey.getMovieApi().getMoviesDetails(
                    movieID,
                    Credentials.API_KEY
            );
        }

        private void cancelRequest() {
            Log.d("Tagy", "Canceling search request now playing movie");
            cancelRequest = true;
        }
    }


    // Retrieving data from RestAPI by Runnable class
    // We have 1 type of query: page number
    private class RetrieveMovieNowPlayingRunnable implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMovieNowPlayingRunnable(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response responseNowMovie = getNowPlaying(pageNumber).execute();

                if (cancelRequest) {
                    return;
                }
                if (responseNowMovie.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) responseNowMovie.body()).getMovies());

                    if (pageNumber == 1) {
                        mMoviesNowPlaying.postValue(list);
                    } else {
                        List<MovieModel> currentMovies = mMoviesNowPlaying.getValue();
                        currentMovies.addAll(list);
                        mMoviesNowPlaying.postValue(currentMovies);
                    }

                } else {
                    String error = responseNowMovie.errorBody().string();
                    Log.d("TanLoc", "Error: " + error);
                    mMoviesNowPlaying.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mMoviesNowPlaying.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getNowPlaying(int pageNumber) {
            return Servicey.getMovieApi().getDiscoveryMovie(
                    Credentials.API_KEY,
                    pageNumber
            );
        }

        private void cancelRequest() {
            Log.d("Tagy", "Canceling search request now playing movie");
            cancelRequest = true;
        }
    }

    private class RetrieveMovieUpcoming implements Runnable {

        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMovieUpcoming(int pageNumber) {
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getUpcomingMovies(pageNumber).execute();

                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (list != null) {
                        mMoviesUpcoming.postValue(list);
                    }
                } else {
                    mMoviesUpcoming.postValue(null);
                }

            } catch (Exception e) {
                e.printStackTrace();
                mMoviesUpcoming.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getUpcomingMovies(int pageNumber) {
            return Servicey.getMovieApi().getUpcomingMovie(
                    Credentials.API_KEY,
                    pageNumber
            );
        }
    }

    public class RetrieveSearchMovie implements Runnable {
        private int pageNumber;
        private String query;
        boolean cancelRequest;

        public RetrieveSearchMovie(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;
        }

        @Override
        public void run() {

            try {
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse) response.body()).getMovies());
                    if (pageNumber == 1) {
                        mSearchMovies.postValue(list);
                    } else {
                        List<MovieModel> addedMovie = mSearchMovies.getValue();
                        addedMovie.addAll(list);
                        mSearchMovies.postValue(addedMovie);
                    }
                } else {
                    mSearchMovies.postValue(null);
                }

            } catch (IOException e) {
                e.printStackTrace();
                mSearchMovies.postValue(null);
            }
        }

        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return Servicey.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    pageNumber
            );
        }
    }

    public class RetrieveSearchGenres implements Runnable {

        boolean cancelRequest;

        public RetrieveSearchGenres() {
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getGenres().execute();

                if (cancelRequest) {
                    return;
                }

                if (response.code() == 200) {
                    List<Genre> list = new ArrayList<>(((GetGenreResponse) response.body()).getGenres());
                    mSearchGenres.postValue(list);
                } else {
                    mSearchGenres.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mSearchGenres.postValue(null);
            }
        }

        private Call<GetGenreResponse> getGenres() {
            return Servicey.getMovieApi().getAllGenres(
                    Credentials.API_KEY
            );
        }
    }

    public LiveData<List<MovieModel>> getmMoviesNowPlaying() {
        return mMoviesNowPlaying;
    }

    public LiveData<MovieDetails> getmMoviesDetails() {
        return mMoviesDetails;
    }

    public LiveData<List<Cast>> getmCastInfo() {
        return mCastInfo;
    }

    public LiveData<List<MovieModel>> getmMoviesUpcoming() {
        return mMoviesUpcoming;
    }

    public LiveData<List<MovieModel>> getmSearchMovie() {
        return mSearchMovies;
    }

    public LiveData<List<Genre>> getmGetAllGenres() {
        return mSearchGenres;
    }

    public LiveData<List<MovieDetails>> getmMovieWatchList() {
        return mMovieWatchList;
    }

}
