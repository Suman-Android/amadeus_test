package com.coforge.amadeus

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.coforge.amadeus.databinding.ActivityMainBinding
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    /*
        Get "weather_14 File from asset
    */
    private fun readDataFromFile() {
        val inputStream: InputStream = assets.open("weather_14.json")
        mainViewModel.readDataFromWeatherStream(inputStream)
    }


    //option menu has search bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_weather, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView

        return true
    }
}