package com.example.runapp.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.runapp.R
import com.example.runapp.model.RunModelFinal
import com.example.runapp.other.AppUtilities
import com.squareup.picasso.Picasso

class RunAdapter(private val lista: ArrayList<RunModelFinal>) :
    RecyclerView.Adapter<RunAdapter.MyViewHolder>() {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(runModelFinal: RunModelFinal) {
            setViews(itemView, runModelFinal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_list_run, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(lista[position])
        holder.itemView.findViewById<ImageButton>(R.id.btnDelete).setOnClickListener {
            val btnDelete = it.findViewById<ImageButton>(R.id.btnDelete)
            setOnClick!!.invoke(lista[position].runId!!, btnDelete, position)
        }
    }

    var setOnClick : ((Int, ImageButton, Int) -> Unit)? = null

    override fun getItemCount(): Int = lista.size

    private fun setViews(itemView: View, runModelFinal: RunModelFinal) {
        itemView.findViewById<TextView>(R.id.txtNote).text = runModelFinal.note
        itemView.findViewById<TextView>(R.id.txtDistancia).text =
            "${AppUtilities.formatTo2DecimalHomes(runModelFinal.totalDistance)} km"
        itemView.findViewById<TextView>(R.id.txtAvg).text =
            "${AppUtilities.formatTo2DecimalHomes(runModelFinal.avergedSpeed)} kmh"
        itemView.findViewById<TextView>(R.id.txtDuration).text = runModelFinal.timRunInSeconds
        itemView.findViewById<TextView>(R.id.txtDaterv).text = runModelFinal.dateRun

        val img = itemView.findViewById<ImageView>(R.id.imgRunRecycler)

        if (runModelFinal.local == "Cidade") {
            Picasso.get().load(R.drawable.img_cidade).into(img)
        } else {
            Picasso.get().load(R.drawable.img_campo).into(img)
        }


    }

}