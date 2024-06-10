package com.example.florainfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class MedicinskiAdapter(
    private var biljke: List<Biljka>,
    private val onItemClicked: (biljka:Biljka) -> Unit,
    private val trefleDAO: TrefleDAO
) : RecyclerView.Adapter<MedicinskiAdapter.MedicinskiViewHolder>(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicinskiViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medicinski, parent, false)
        return MedicinskiViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: MedicinskiViewHolder, position: Int) {
        holder.bind(biljke[position])
        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }
    }

    fun updateBiljke(biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }
    inner class MedicinskiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val slikaBiljke: ImageView = itemView.findViewById(R.id.slikaItem)
        private val nazivBiljke: TextView = itemView.findViewById(R.id.nazivItem)
        private val medicinskoUpozorenje: TextView = itemView.findViewById(R.id.upozorenjeItem)
        private val koristi: List<TextView> = listOf<TextView>(itemView.findViewById(R.id.korist1Item),
            itemView.findViewById(R.id.korist2Item),
            itemView.findViewById(R.id.korist3Item))

        fun bind(biljka: Biljka) {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = trefleDAO.getImage(biljka)
                slikaBiljke.setImageBitmap(bitmap)
            }

            nazivBiljke.text = biljka.naziv;
            medicinskoUpozorenje.text = biljka.medicinskoUpozorenje
            for (i in 0..<3) {
                if(i < biljka.medicinskeKoristi.size) koristi[i].text = biljka.medicinskeKoristi[i].opis
                else koristi[i].text = ""
            }
        }
    }
}