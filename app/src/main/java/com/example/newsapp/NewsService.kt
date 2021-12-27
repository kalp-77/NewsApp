package com.example.newsapp

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//https://newsapi.org/v2/top-headlines?country=us&apiKey=31ca2506a5c1404691300453d8f559ff
//Retrofit needs the base URI for the web service, and a converter factory to build a web services API
//The converter tells Retrofit what to do with the data it gets back from the web service
// we will use GSON converter
const val BASE_URL = "https://newsapi.org/"
const val API_KEY = "31ca2506a5c1404691300453d8f559ff"


//Retrofit interface defines how Retrofit talks to the web server using HTTP requests
interface NewsInterface {
    //Use the @GET annotation to tell Retrofit that this is GET request
    @GET("v2/top-headlines?apiKey=$API_KEY")                                         // returns a response
    fun getHeadlines(@Query("country")country : String, @Query("page")page:Int) : Call<News>

    //When the getHeadlines() method is invoked, Retrofit appends the endpoint (value) to the base URL
// Call<T> -- 	clone() Create a new, identical call to this one which can be enqueued or executed even if this call has already been.
}

//Retrofit object singleton
//In kotlin, object declarations are used to declare singleton objects.
// Singleton pattern ensures that one, and only one, instance of an object is created, has one global point of access to that object.
// Object declaration's initialization is thread-safe and done at first access.
object NewsService {
    val newsInstance : NewsInterface
    init {
        //Retrofit builder to build and create a Retrofit object
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            // Gson converter to convert json files sent by the web server into java objects
            .addConverterFactory(GsonConverterFactory.create())
            //call build() to create the Retrofit object
            .build()
        newsInstance = retrofit.create(NewsInterface::class.java)
    }
}
// To refer to the object, use its name directly.
// NewsService.newsInstance
//The call to create() function on a Retrofit object is expensive and the app needs only one instance of Retrofit API service.
//So, you expose the service to the rest of the app using object declaration
