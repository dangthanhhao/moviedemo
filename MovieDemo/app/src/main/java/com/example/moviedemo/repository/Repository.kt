package com.example.moviedemo.repository


import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.example.moviedemo.repository.local.*
import com.example.moviedemo.repository.network.BASE_IMAGE_URL
import com.example.moviedemo.repository.network.Movie
import com.example.moviedemo.repository.network.MovieApi
import com.example.moviedemo.repository.network.PopularMoviesResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class Repository @Inject constructor(
    val userDAO: UserDAO,
    val movieApi: MovieApi,
    val favDAO: FavDAO,
    val reminderDAO: ReminderDAO
) {

    fun getUserProfile(): LiveData<UserModel> {
        //An action check if current user is created in DB. If not, create (insert) a row and then return the row (current user)
        val action = userDAO.checkCurrentUser().doOnError({ it.printStackTrace() })
            .switchIfEmpty(Maybe.just(UserModel())).flatMapCompletable {
//                Timber.i("Current user ${it.toString()}")
                if (it.userID == 0L)
                    return@flatMapCompletable userDAO.insert(UserModel())
                else
                    return@flatMapCompletable Completable.complete()
            }.andThen(
                userDAO.getCurrentUser()
            )
        return LiveDataReactiveStreams.fromPublisher(
            action
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        )
    }

    fun updateUserProfile(user: UserModel) {
        userDAO.update(user).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun getPopularMovie(page:Int=1): Observable<PopularMoviesResponse> {
        return movieApi.getPopularMovies(page = page)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }


    companion object {
        fun getUrLImage(relativeURL: String?): String {
            if (relativeURL.isNullOrEmpty()) return ""
            return BASE_IMAGE_URL + relativeURL.substring(1)
        }
    }


    fun getMovieDetail(id: Int): Observable<Movie> {
        return movieApi.getMovieDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }


    fun getFavMovies(): LiveData<List<FavMovieModel>> {
        return LiveDataReactiveStreams.fromPublisher(
            favDAO.getAllFavIDs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        )

    }

    fun insertFavMovie(id: Int, title: String) {
        val favMovie = FavMovieModel(movieID = id, title = title)
        favDAO.insertFavID(favMovie)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, {
                if (it is SQLiteConstraintException) {
                    Timber.i("Duplicate id, remove favourite movie $id!")
                    deleteFavMovie(id)
                }
            })
    }

    fun deleteFavMovie(movieid: Int) {
        favDAO.deleteFavID(movieid)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    //    reminders
    fun getAllReminders(): LiveData<List<ReminderMovieModel>> {
        return LiveDataReactiveStreams.fromPublisher(
            reminderDAO.getAll().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
        )

    }

    fun insertOrUpdateReminder(reminder: ReminderMovieModel) {
        reminderDAO.insert(reminder).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe({},{
                Timber.i("insert repostory got reminder ${reminder.reminderDate}")
                if (it is SQLiteConstraintException){
                    Timber.i("Duplicated reminder movieDID, Go update!")

                    updateReminder(reminder)
                }
            })
    }

    fun deleteReminder(movieid: Int) {
        reminderDAO.delete(movieid).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe()
    }

    fun updateReminder(reminder: ReminderMovieModel) {

        reminderDAO.update(reminder).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()).subscribe()
    }
    fun getReminder(movieID:Int):LiveData<ReminderMovieModel>{
        return LiveDataReactiveStreams.fromPublisher(
        reminderDAO.getReminder(movieID).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io()))
    }

}