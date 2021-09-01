package com.example.weather.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.model.Hourly
import java.sql.Date
import java.text.SimpleDateFormat

class HourAdapter: RecyclerView.Adapter<HourAdapter.ViewHolder>() {

    private var listOFHourly:List<Hourly>?=null

    class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val hourNumber:TextView = view.findViewById(R.id.timeHourItem)
        val hourDesc:TextView = view.findViewById(R.id.hourItemDesc)
        val hourTemp:TextView = view.findViewById(R.id.hourTemp)
        val hourImage:ImageView = view.findViewById(R.id.hourImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hour_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hour = listOFHourly!![position]
        val sdf = SimpleDateFormat("hh:mm a")
        val netDate = Date(hour.dt.toLong()*1000)
        val date =sdf.format(netDate)
        val tempString = (hour.temp-273.15).toInt().toString()+"°C"
        holder.hourNumber.text = date
        holder.hourDesc.text = hour.weather[0].main
        holder.hourTemp.text = tempString
        when(hour.weather[0].main){
            "Clear" -> holder.hourImage.setImageResource(R.drawable.sun)
            "Rain" -> holder.hourImage.setImageResource(R.drawable.rain)
            "Clouds" -> holder.hourImage.setImageResource(R.drawable.cloud)
            else->holder.hourImage.setImageResource(R.drawable.rain)
        }
    }

    override fun getItemCount(): Int {
        return if (listOFHourly==null)0
        else listOFHourly!!.size
    }
    fun setList(list: List<Hourly>){
        listOFHourly = list
        notifyDataSetChanged()
    }
}


