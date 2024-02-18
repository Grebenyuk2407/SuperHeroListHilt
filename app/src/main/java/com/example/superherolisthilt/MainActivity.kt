package com.example.superherolisthilt

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var apiInterface: ApiInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        val myAdapter = SuperHeroAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = myAdapter

        lifecycleScope.launch {
            try {
                val superHeroes = withContext(Dispatchers.IO){
                    apiInterface.getSuperHeroes()
                }
                myAdapter.setData(superHeroes)

            }catch (error:Throwable){
                Log.e("MainActivity", "Error: ${error.message}", error)
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@Module
@InstallIn(MyApp::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApiClient(): ApiInterface {
        return Retrofit.Builder()
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://akabab.github.io")
            .build()
            .create(ApiInterface::class.java)
    }
}