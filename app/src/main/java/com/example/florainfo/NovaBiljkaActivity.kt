package com.example.florainfo

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NovaBiljkaActivity() : AppCompatActivity() {
    private lateinit var nazivEditText: EditText
    private lateinit var porodicaEditText: EditText
    private lateinit var medicinskoUpozorenjeEditText: EditText
    private lateinit var jeloEditText : EditText
    private lateinit var medicinskaKoristTextView: TextView
    private lateinit var medicinskaKoristListView : ListView
    private lateinit var klimatskiTipTextView: TextView
    private lateinit var klimatskiTipListView : ListView
    private lateinit var zemljisniTipTextView: TextView
    private lateinit var zemljisniTipListView : ListView
    private lateinit var profilOkusaTextView: TextView
    private lateinit var profilOkusaListView : ListView
    private lateinit var jelaTextView: TextView
    private lateinit var jelaListView : ListView
    private lateinit var dodajJeloButton: Button
    private lateinit var dodajBiljkuButton: Button
    private lateinit var uslikajBiljkuButton: Button
    private lateinit var slikaBiljkeIV: ImageView
    private lateinit var imageUrl: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_nova_biljka)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addFunctionalities()
    }

    private fun addFunctionalities() {
        connectLayoutElements()
        attachMedicinskaKoristAdapter()
        attachKlimatskiTipAdapter()
        attachZemljisniTipAdapter()
        attachProfilOkusaAdapter()
        attachJelaAdapter()
        addDodajBiljkuOnClickListener()
        addUslikajBiljkuButtonOnClickListener()
    }

    private fun addUslikajBiljkuButtonOnClickListener() {
        uslikajBiljkuButton.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(takePictureIntent, 1)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            // Get the image data from the intent
            val imageBitmap = data?.extras?.get("data") as Bitmap?

            // Set the image bitmap to the ImageView
            slikaBiljkeIV.setImageBitmap(imageBitmap)
        }
    }

    private fun connectLayoutElements() {
        nazivEditText = findViewById(R.id.nazivET)
        porodicaEditText = findViewById(R.id.porodicaET)
        medicinskoUpozorenjeEditText = findViewById(R.id.medicinskoUpozorenjeET)
        jeloEditText = findViewById(R.id.jeloET)

        medicinskaKoristTextView = findViewById(R.id.medicinskaKoristTV)
        medicinskaKoristListView = findViewById(R.id.medicinskaKoristLV)
        klimatskiTipTextView = findViewById(R.id.klimatskiTipTV)
        klimatskiTipListView = findViewById(R.id.klimatskiTipLV)

        zemljisniTipTextView = findViewById(R.id.zemljisniTipTV)
        zemljisniTipListView = findViewById(R.id.zemljisniTipLV)

        profilOkusaTextView = findViewById(R.id.profilOkusaTV)
        profilOkusaListView = findViewById(R.id.profilOkusaLV)

        jelaTextView = findViewById(R.id.jelaTV)
        jelaListView = findViewById(R.id.jelaLV)

        dodajJeloButton = findViewById(R.id.dodajJeloBtn)
        dodajBiljkuButton = findViewById(R.id.dodajBiljkuBtn)
        uslikajBiljkuButton = findViewById(R.id.uslikajBiljkuBtn)

        slikaBiljkeIV = findViewById(R.id.slikaIV)

    }

    private fun attachMedicinskaKoristAdapter() {
        val medicinskeKoristi = ArrayList<String>()
        for (medicinskaKorist in MedicinskaKorist.entries) {
            medicinskeKoristi.add(medicinskaKorist.opis)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, medicinskeKoristi)
        medicinskaKoristListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        medicinskaKoristListView.adapter = adapter
        setListViewHeightBasedOnChildren(medicinskaKoristListView)
    }

    private fun attachKlimatskiTipAdapter() {
        val klimatskiTipovi = ArrayList<String>()
        for (klimatskiTip in KlimatskiTip.entries) {
            klimatskiTipovi.add(klimatskiTip.opis)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, klimatskiTipovi)
        klimatskiTipListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        klimatskiTipListView.adapter = adapter
        setListViewHeightBasedOnChildren(klimatskiTipListView)
    }

    private fun attachZemljisniTipAdapter() {
        val zemljisniTipovi = ArrayList<String>()
        for (zemljisniTip in Zemljiste.entries) {
            zemljisniTipovi.add(zemljisniTip.naziv)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, zemljisniTipovi)
        zemljisniTipListView.choiceMode = ListView.CHOICE_MODE_MULTIPLE
        zemljisniTipListView.adapter = adapter
        setListViewHeightBasedOnChildren(zemljisniTipListView)
    }

    private fun attachProfilOkusaAdapter() {
        val profiliOkusa = ArrayList<String>()
        for (profilOkusa in ProfilOkusaBiljke.entries) {
            profiliOkusa.add(profilOkusa.opis)
        }
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, profiliOkusa)
        profilOkusaListView.choiceMode = ListView.CHOICE_MODE_SINGLE
        profilOkusaListView.adapter = adapter
        setListViewHeightBasedOnChildren(profilOkusaListView)
    }

    private fun attachJelaAdapter() {
        val jela = ArrayList<String>()

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, jela)
        jelaListView.adapter = adapter
        var lastClickedPosition = -1

        jelaListView.setOnItemClickListener { parent, _, position, _ ->
            val selectedItem = parent.getItemAtPosition(position).toString()
            jeloEditText.setText(selectedItem)
            jeloEditText.setSelection(jeloEditText.length())
            lastClickedPosition = position
            dodajJeloButton.text = getString(R.string.izmijeni_jelo)
        }

        dodajJeloButton.setOnClickListener {
            resetErrors()
            val inputText = jeloEditText.text.toString().trim()
            if(!validateEditTextLength(jeloEditText)) {
                jeloEditText.error = "Duzina polja mora biti izmedju 2 i 20"
            }
            else if((dodajJeloButton.text == getString(R.string.dodaj_jelo) && isItemAlreadyExists(inputText, jela)) ||
                (dodajJeloButton.text == getString(R.string.izmijeni_jelo) && isItemAlreadyExistsExcludingIndex(lastClickedPosition, inputText, jela))) {
                jeloEditText.error = "Vec postoji jelo u listi!"
            }
            else {
                if(inputText.isNotEmpty() && dodajJeloButton.text == getString(R.string.dodaj_jelo)) {
                    // Dodaj jelo
                    jela.add(inputText)
                }
                else {
                    if (lastClickedPosition != -1) {
                        // Izmijeni jelo
                        if(inputText.isEmpty()) jela.removeAt(lastClickedPosition)
                        else jela[lastClickedPosition] = inputText
                        adapter.notifyDataSetChanged()
                        dodajJeloButton.text = getString(R.string.dodaj_jelo)
                        lastClickedPosition = -1
                        jeloEditText.text.clear()
                        jelaListView.clearChoices()
                        setListViewHeightBasedOnChildren(jelaListView)
                        return@setOnClickListener
                    }
                }
                adapter.notifyDataSetChanged()
                jeloEditText.text.clear()
                setListViewHeightBasedOnChildren(jelaListView)
            }
        }
    }

    private fun isItemAlreadyExists(inputText: String, itemArrayList: ArrayList<String>): Boolean {
        val lowerCaseInput = inputText.lowercase()
        for(item in itemArrayList) {
            if(item.lowercase() == lowerCaseInput) {
                return true
            }
        }
        return false
    }

    private fun isItemAlreadyExistsExcludingIndex(index: Int, inputText: String, itemArrayList: ArrayList<String>): Boolean {
        val lowerCaseInput = inputText.lowercase()
        for(item in itemArrayList) {
            if(itemArrayList.indexOf(item) != index && item.lowercase() == lowerCaseInput) {
                return true
            }
        }
        return false
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return

        val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null

        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)

            if (i == 0)
                view.layoutParams = ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.MATCH_PARENT)

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }

        val params = listView.layoutParams
        params.height = totalHeight + (listView.dividerHeight * (listAdapter.count))

        listView.layoutParams = params
        listView.requestLayout()
    }

    private fun addDodajBiljkuOnClickListener() {
        dodajBiljkuButton.setOnClickListener {
            resetErrors()
            displayErrors()
            if(validateFields()) {
                val trefleDAO = TrefleDAO()
                trefleDAO.setContext(this)
                CoroutineScope(Dispatchers.Main).launch {
                    var novaBiljka = createBiljka()
                    dodajBiljku(trefleDAO.fixData(novaBiljka))
                }
            }
        }
    }

    private fun createBiljka(): Biljka {
        return Biljka(
            naziv = nazivEditText.text.toString(),
            porodica = porodicaEditText.text.toString(),
            medicinskoUpozorenje = medicinskoUpozorenjeEditText.text.toString(),
            medicinskeKoristi = getSelectedItems<MedicinskaKorist>(medicinskaKoristListView),
            profilOkusa = getSelectedItems<ProfilOkusaBiljke>(profilOkusaListView).component1(),
            jela = getAllItems(jelaListView),
            klimatskiTipovi = getSelectedItems<KlimatskiTip>(klimatskiTipListView),
            zemljisniTipovi = getSelectedItems<Zemljiste>(zemljisniTipListView)
        )
    }

    private fun dodajBiljku(novaBiljka: Biljka) {
        val intent = Intent()
        intent.putExtra("novaBiljka", novaBiljka)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun getAllItems(listView: ListView): List<String> {
        val allItems = mutableListOf<String>()
        val adapter = listView.adapter
        if (adapter != null) {
            for (i in 0 until adapter.count) {
                allItems.add(adapter.getItem(i).toString())
            }
        }
        return allItems
    }

    private inline fun <reified T : Enum<T>> getSelectedItems(listView: ListView): List<T> {
        val selectedItems = mutableListOf<T>()
        val adapter = listView.adapter as ArrayAdapter<*>
        for (i in 0 until adapter.count) {
            if (listView.isItemChecked(i)) {
                selectedItems.add(enumValues<T>()[i])
            }
        }
        return selectedItems
    }

    private fun displayErrors() {
        if(!validateEditTextLength(nazivEditText)) nazivEditText.error = "Duzina polja mora biti izmedju 2 i 40"
        if(!validateEditTextLength(porodicaEditText)) porodicaEditText.error = "Duzina polja mora biti izmedju 2 i 20"
        if(!validateEditTextLength(medicinskoUpozorenjeEditText)) medicinskoUpozorenjeEditText.error = "Duzina polja mora biti izmedju 2 i 20"
        if(!isAtLeastOneItemSelected(medicinskaKoristListView)) medicinskaKoristTextView.error = "Mora biti odabran makar jedan element iz liste."
        if(!isAtLeastOneItemSelected(klimatskiTipListView)) klimatskiTipTextView.error = "Mora biti odabran makar jedan element iz liste."
        if(!isAtLeastOneItemSelected(zemljisniTipListView)) zemljisniTipTextView.error = "Mora biti odabran makar jedan element iz liste."
        if(!containsItems(jelaListView)) jelaTextView.error = "Mora biti dodano makar jedno jelo."
        if(!isOneItemSelected(profilOkusaListView)) profilOkusaTextView.error = "Mora biti odabran jedan element iz liste."
        if(!validateNazivContainsLatinName()) nazivEditText.error = "Naziv mora sadrzati latinsko ime u zagradama"
    }

    private fun isOneItemSelected(listView: ListView) : Boolean {
        return listView.checkedItemPosition != ListView.INVALID_POSITION
    }

    private fun containsItems(listView: ListView) : Boolean {
        return listView.childCount > 0
    }

    private fun validateFields(): Boolean {
        return validateEditTextLength(nazivEditText) &&
            validateEditTextLength(porodicaEditText) &&
            validateEditTextLength(medicinskoUpozorenjeEditText) &&
                isAtLeastOneItemSelected(medicinskaKoristListView) &&
                isAtLeastOneItemSelected(klimatskiTipListView) &&
                isAtLeastOneItemSelected(zemljisniTipListView) &&
                containsItems(jelaListView) &&
                isOneItemSelected(profilOkusaListView) &&
                validateNazivContainsLatinName()
    }
    private fun validateEditTextLength(editText: EditText): Boolean {
        val text = editText.text.toString().trim()
        if(editText == nazivEditText) return text.length in 2..40
        return text.length in 2..20
    }

    private fun validateNazivContainsLatinName(): Boolean {
        val pattern = Regex("\\(.*\\)")
        Log.e("NovaBiljka", pattern.containsMatchIn(nazivEditText.text.toString()).toString())
        return pattern.containsMatchIn(nazivEditText.text.toString())
    }

    private fun isAtLeastOneItemSelected(listView: ListView): Boolean {
        return listView.checkedItemCount > 0
    }

    private fun resetErrors() {
        nazivEditText.error = null
        porodicaEditText.error = null
        medicinskoUpozorenjeEditText.error = null
        jeloEditText.error = null
        medicinskaKoristTextView.error = null
        klimatskiTipTextView.error = null
        zemljisniTipTextView.error = null
        profilOkusaTextView.error = null
        jelaTextView.error = null
    }
}