package com.example.dogapi

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.dogapi.api.Endpoint
import com.example.dogapi.databinding.ActivityMainBinding
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonPesquisar.setOnClickListener() { getImagem()}
    }

    private fun getImagem() {
        val url = "https://dog.ceo/"
        val retrofitClient = retrofitInstance(url)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val raca = binding.editRaca.text.toString()

        endpoint.getDog(raca).enqueue(object : Callback<JsonObject>{
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                val urlImage = response.body()?.get("message")?.asString
                Picasso.get().load(urlImage).into(binding.imageDog)
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                Toast.makeText(applicationContext,"Erro ao acessar", Toast.LENGTH_LONG)
            }

        })


        //val ursImage = "https://images.adsttc.com/media/images/5b91/86e0/f197/cc30/e000/052f/slideshow/03.A.Tercer_Lugar.jpg?1536263887"
        //Picasso.get().load(ursImage).into((binding.imageDog))
    }
    private fun retrofitInstance(url: String): Retrofit{
        return Retrofit
                .Builder()
                .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}