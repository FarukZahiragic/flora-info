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
import kotlin.coroutines.CoroutineContext

class KuharskiAdapter(
        private var biljke: List<Biljka>,
        private val onItemClicked: (biljka:Biljka) -> Unit,
        private val trefleDAO: TrefleDAO
) : RecyclerView.Adapter<KuharskiAdapter.KuharskiViewHolder>(), CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KuharskiViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_kuharski, parent, false)
        return KuharskiViewHolder(view)
    }

    override fun getItemCount(): Int = biljke.size

    override fun onBindViewHolder(holder: KuharskiViewHolder, position: Int) {
        holder.bind(biljke[position])
        holder.itemView.setOnClickListener{ onItemClicked(biljke[position]) }
    }

    fun updateBiljke(biljke: List<Biljka>) {
        this.biljke = biljke
        notifyDataSetChanged()
    }

    inner class KuharskiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val slikaBiljke: ImageView = itemView.findViewById(R.id.slikaItem)
        private val nazivBiljke: TextView = itemView.findViewById(R.id.nazivItem)
        private val profilOkusaBiljke: TextView = itemView.findViewById(R.id.profilOkusaItem)
        private val jela: List<TextView> = listOf<TextView>(
            itemView.findViewById(R.id.jelo1Item),
            itemView.findViewById(R.id.jelo2Item),
            itemView.findViewById(R.id.jelo3Item)
        )

        fun bind(biljka: Biljka) {
            CoroutineScope(Dispatchers.Main).launch {
                val bitmap = trefleDAO.getImage(biljka)
                slikaBiljke.setImageBitmap(bitmap)
            }
            nazivBiljke.text = biljka.naziv;
            profilOkusaBiljke.text = biljka.profilOkusa?.opis ?: ""
            for (i in 0..<3) {
                if(i < biljka.jela.size) jela[i].text = biljka.jela[i]
                else jela[i].text = ""
            }
        }
    }
}