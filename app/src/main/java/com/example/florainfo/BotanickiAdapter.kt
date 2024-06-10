package com.example.florainfo;

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
import kotlin.coroutines.CoroutineContext

class BotanickiAdapter(
    private var biljke: List<Biljka>,
    private val onItemClicked: (biljka:Biljka) -> Unit,
    private val trefleDAO: TrefleDAO
) : RecyclerView.Adapter<BotanickiAdapter.BotanickiViewHolder>(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BotanickiViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_botanicki, parent, false)
        return BotanickiViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: BotanickiViewHolder, position: Int) {
        holder.bind(biljke[position])
        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }
    }

    fun updateBiljke(biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }

    inner class BotanickiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val slikaBiljke: ImageView = itemView.findViewById(R.id.slikaItem)
        private val nazivBiljke: TextView = itemView.findViewById(R.id.nazivItem)
        private val porodicaBiljke: TextView = itemView.findViewById(R.id.porodicaItem)
        private val prviKlimatskiTipBiljke: TextView = itemView.findViewById(R.id.klimatskiTipItem)
        private val prviZemljisniTipBiljke: TextView = itemView.findViewById(R.id.zemljisniTipItem)

        fun bind(biljka: Biljka) {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = trefleDAO.getImage(biljka)
                slikaBiljke.setImageBitmap(bitmap)
            }
            nazivBiljke.text = biljka.naziv
            porodicaBiljke.text = biljka.porodica
            if(biljka.klimatskiTipovi.isNotEmpty()) prviKlimatskiTipBiljke.text = biljka.klimatskiTipovi[0].opis
            else prviKlimatskiTipBiljke.text = ""
            if(biljka.zemljisniTipovi.isNotEmpty()) prviZemljisniTipBiljke.text = biljka.zemljisniTipovi[0].naziv
            else prviZemljisniTipBiljke.text = ""
        }
    }
}

