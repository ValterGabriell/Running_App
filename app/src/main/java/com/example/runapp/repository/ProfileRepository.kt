package com.example.runapp.repository

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.example.runapp.model.RunModelFinal
import com.example.runapp.network.RetrofitInstance
import com.example.runapp.other.AppUtilities
import com.google.android.material.textview.MaterialTextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileRepository {

    fun getLastRun(
        context: Context,
        txtDist: MaterialTextView,
        txtKmh: MaterialTextView,
        txtCal: MaterialTextView,
        txtTimeFinal: MaterialTextView,
        progressBarProfile: ProgressBar,
        layout: LinearLayout,
        layout2: LinearLayout,
        userId: String
    ) {
        RetrofitInstance.getRetrofit().getLastRun(userId).enqueue(object : Callback<RunModelFinal> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<RunModelFinal>, response: Response<RunModelFinal>) {
                response.body().let {
                    if (it?.userId?.isNotEmpty() == true && it?.userId?.isNotBlank() == true) {
                        progressBarProfile.visibility = View.GONE
                        layout.visibility = View.VISIBLE
                        layout2.visibility = View.VISIBLE
                        txtDist.text = "${AppUtilities.formatTo2DecimalHomes(it.totalDistance)} km"
                        txtKmh.text = "${AppUtilities.formatTo2DecimalHomes(it.avergedSpeed)} kmh"
                        txtTimeFinal.text = it.timRunInSeconds
                    } else {
                        progressBarProfile.visibility = View.GONE
                        layout.visibility = View.VISIBLE
                        layout2.visibility = View.VISIBLE
                        txtDist.visibility = View.GONE
                        txtKmh.visibility = View.GONE
                        txtCal.visibility = View.GONE
                        txtTimeFinal.text = "Voc?? ainda n??o possui corridas salvas"
                    }
                }
            }

            override fun onFailure(call: Call<RunModelFinal>, t: Throwable) {
                Toast.makeText(
                    context,
                    "N??o foi poss??vel recuperar a ??ltima corrida, tente novamente em alguns instantes",
                    Toast.LENGTH_SHORT
                ).show()
                progressBarProfile.visibility = View.GONE
                layout.visibility = View.VISIBLE
                layout2.visibility = View.VISIBLE
                txtDist.visibility = View.GONE
                txtKmh.visibility = View.GONE
                txtCal.visibility = View.GONE
                txtTimeFinal.text = "Falha ao recuperar corrida, tente novamente"
            }
        })
    }


}