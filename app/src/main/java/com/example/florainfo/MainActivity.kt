package com.example.florainfo

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var medicinskiAdapter: MedicinskiAdapter
    private lateinit var kuharskiAdapter: KuharskiAdapter
    private lateinit var botanickiAdapter: BotanickiAdapter
    private lateinit var listBiljke: List<Biljka>
    private lateinit var resetButton: Button
    private lateinit var bojaSpinner: Spinner
    private lateinit var pretragaEditText: EditText
    private lateinit var brzaPretragaButton: Button
    private lateinit var trefleDAO: TrefleDAO
    private var searchMode = false
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        trefleDAO = TrefleDAO()
        trefleDAO.setContext(this)
        listBiljke = biljke
        recyclerView = findViewById(R.id.biljkeRV)
        medicinskiAdapter = MedicinskiAdapter(listBiljke, { biljka -> filterMedicinski(biljka) }, trefleDAO)
        kuharskiAdapter = KuharskiAdapter(listBiljke, { biljka -> filterKuharski(biljka)}, trefleDAO)
        botanickiAdapter = BotanickiAdapter(listBiljke, { biljka -> filterBotanicki(biljka)}, trefleDAO)
        recyclerView.adapter = medicinskiAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        resetButton = findViewById(R.id.resetBtn)
        bojaSpinner = findViewById(R.id.bojaSPIN)
        pretragaEditText = findViewById(R.id.pretragaET)
        brzaPretragaButton = findViewById(R.id.brzaPretraga)
        addBojaSpinnerAdapter()

        resetButton.setOnClickListener {
            listBiljke = biljke
            updateInfo()
            searchMode = false
        }

        val spinner: Spinner = findViewById(R.id.modSpinner)
        val spinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.niz_modova,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Azuriraj sadrzaj RecyclerViewa u zavisnosti od odabira spinnera
                val selectedMode = parent?.getItemAtPosition(position).toString()
                when (selectedMode) {
                    "Medicinski" -> {
                        recyclerView.adapter = medicinskiAdapter
                        updateInfo()
                        setInvisible()
                        searchMode = false
                    }
                    "Kuharski" -> {
                        recyclerView.adapter = kuharskiAdapter
                        updateInfo()
                        setInvisible()
                        searchMode = false
                    }
                    "Botanički" -> {
                        recyclerView.adapter = botanickiAdapter
                        updateInfo()
                        setVisible()
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Nemoj nista raditi
            }
        }

        val buttonClick = findViewById<Button>(R.id.novaBiljkaBtn)
        buttonClick.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivityForResult(intent, 1)
        }

        addBrzaPretragaOnClickListener()
    }

    private fun addBrzaPretragaOnClickListener() {
        brzaPretragaButton.setOnClickListener {
            if(pretragaEditText.text.toString() != "") {
                CoroutineScope(Dispatchers.Main).launch {
                    val searchPlants = trefleDAO.getPlantsWithFlowerColor(bojaSpinner.selectedItem.toString(), pretragaEditText.text.toString())
                    botanickiAdapter.updateBiljke(searchPlants)
                }
                searchMode = true
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val novaBiljka = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                data?.getSerializableExtra("novaBiljka", Biljka::class.java)
            } else {
                data?.getSerializableExtra("novaBiljka") as Biljka
            }
            novaBiljka?.let {
                listBiljke += novaBiljka
                biljke += novaBiljka
                updateInfo()
            }
        }
    }

    private fun addBojaSpinnerAdapter() {
        val bojaSpinnerAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.niz_boja,
            android.R.layout.simple_spinner_item
        )
        bojaSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        bojaSpinner.adapter = bojaSpinnerAdapter
    }

    private fun setVisible() {
        bojaSpinner.visibility = View.VISIBLE
        pretragaEditText.visibility = View.VISIBLE
        brzaPretragaButton.visibility = View.VISIBLE
    }

    private fun setInvisible() {
        bojaSpinner.visibility = View.GONE
        pretragaEditText.visibility = View.GONE
        brzaPretragaButton.visibility = View.GONE
    }

    private fun updateInfo() {
        medicinskiAdapter.updateBiljke(listBiljke)
        kuharskiAdapter.updateBiljke(listBiljke)
        botanickiAdapter.updateBiljke(listBiljke)
    }
    private fun filterMedicinski(plant: Biljka) {
        listBiljke = listBiljke.filter { biljka -> biljka.medicinskeKoristi.any { it in plant.medicinskeKoristi } }
        updateInfo()
    }

    private fun filterKuharski(plant: Biljka) {
        listBiljke = listBiljke.filter { biljka -> biljka.jela.any { it in plant.jela } || biljka.profilOkusa == plant.profilOkusa }
        updateInfo()
    }

    private fun filterBotanicki(plant: Biljka) {
        if(!searchMode) {
            listBiljke = listBiljke.filter { biljka -> biljka.porodica == plant.porodica && biljka.klimatskiTipovi.any { it in plant.klimatskiTipovi } && biljka.zemljisniTipovi.any { it in plant.zemljisniTipovi }}
            updateInfo()
        }
    }
}
