package com.example.weather.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.Daily
import java.sql.Date
import java.text.SimpleDateFormat

class DayAdapter:RecyclerView.Adapter<DayAdapter.ViewHolder>() {

    private var listOfDays : List<Daily>?=null

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val imageView : ImageView = view.findViewById(R.id.imageView)
        val dayTextView : TextView = view.findViewById(R.id.dayDayItem)
        val dayDesc : TextView = view.findViewById(R.id.dayItemDesc)
        val maxTemp : TextView = view.findViewById(R.id.maxTemp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.day_item,parent,false)
        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val day = listOfDays!![position]
        val sdf = SimpleDateFormat("EEEE")
        val netDate = Date(day.dt.toLong()*1000)
        val date =sdf.format(netDate)
        val temp = "${(day.temp.day-273).toInt()}°C"
        holder.dayTextView.text = date
        holder.dayDesc.text = day.weather[0].main
        holder.maxTemp.text = temp
        when(day.weather[0].main){
            "Clear" -> holder.imageView.setImageResource(R.drawable.sun)
            "Rain" -> holder.imageView.setImageResource(R.drawable.rain)
            "Clouds" -> holder.imageView.setImageResource(R.drawable.cloud)
            else->holder.imageView.setImageResource(R.drawable.rain)
        }

    }

    override fun getItemCount(): Int {
        return if(listOfDays==null)0
        else listOfDays!!.size
    }

    fun setList(list:List<Daily>){
        listOfDays=list
        notifyDataSetChanged()
    }
}