package com.example.florainfo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.CountDownLatch

class TrefleDAO {
    private lateinit var context: Context
    private lateinit var defaultBitmap: Bitmap

    fun setContext(argContext: Context) {
        context = argContext
        defaultBitmap = (ContextCompat.getDrawable(context, R.drawable.bosiljak) as BitmapDrawable).bitmap
    }

        suspend fun getImage(biljka: Biljka): Bitmap = withContext(Dispatchers.IO) {
            val latinName = extractLatinName(biljka.naziv)
            var resultUrl = ""

            try {
                // Make the network request synchronously within the IO dispatcher
                val result = withContext(Dispatchers.IO) {
                    BiljkaRepository.getBiljkeImage(latinName)
                }

                // Check the result of the network request
                when (result) {
                    is GetImageResponse -> {
                        if (result.biljke.isNotEmpty()) {
                            resultUrl = result.biljke[0].urlSlike
                        }
                    }
                }

                // If a valid URL is found, load the image from the URL
                if (resultUrl.isNotEmpty()) {
                    return@withContext loadImageFromUrl(resultUrl)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Return the default bitmap if no URL is found or an error occurs
            return@withContext defaultBitmap
        }

    private suspend fun loadImageFromUrl(url: String): Bitmap = withContext(Dispatchers.IO) {
        var bitmap: Bitmap? = null
        val latch = CountDownLatch(1)
        Glide.with(context)
            .asBitmap()
            .load(url)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    bitmap = resource
                    latch.countDown()
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    latch.countDown()
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    latch.countDown()
                }
            })
        latch.await()
        return@withContext bitmap ?: defaultBitmap
    }

    private fun extractLatinName(plantName: String): String {
        val regex = Regex("""\((.*?)\)""")
        val match = regex.find(plantName)
        return match!!.groupValues[1]
    }

    private fun extractFamilyName(familyName: String): String {
        return if(familyName.contains(' ')) familyName.substringBefore(' ')
                else familyName
    }

    suspend fun fixData(biljka: Biljka):Biljka {
        val latinName = extractLatinName(biljka.naziv)
        val slug = convertToSlug(latinName)

        try {
            // Make the network request synchronously within the IO dispatcher
            val result = withContext(Dispatchers.IO) {
                BiljkaRepository.getBiljkeData(slug)
            }

            // Check the result of the network request
            when (result) {
                is GetBiljkeDataResponse -> {
                    val family = result.data.mainSpecies.family
                    val edible = result.data.mainSpecies.edible
                    val toxicity = result.data.mainSpecies.specifications.toxicity
                    val soilTexture = result.data.mainSpecies.growth?.soilTexture
                    val light = result.data.mainSpecies.growth?.light
                    val atmosphericHumidity = result.data.mainSpecies.growth?.atmosphericHumidity

                    val familyName = extractFamilyName(biljka.porodica)
                    if (family != null && familyName != family) {
                        biljka.porodica = biljka.porodica.replace(familyName, family)
                    }
                    if (edible != null && !edible) {
                        if(biljka.jela.isNotEmpty()) {
                            biljka.jela = emptyList()
                        }
                        if(!biljka.medicinskoUpozorenje.contains("NIJE JESTIVO")) {
                            biljka.medicinskoUpozorenje += "NIJE JESTIVO"
                        }
                    }
                    if(toxicity != null && toxicity  != "none") {
                        if(!biljka.medicinskoUpozorenje.contains("TOKSIČNO")) {
                            biljka.medicinskoUpozorenje += "TOKSIČNO"
                        }
                    }
                    if(soilTexture != null) {
                        val soilType = getSoilType(soilTexture)
                        biljka.zemljisniTipovi.filter { it == soilType }
                        if (soilType != null)
                            if (!biljka.zemljisniTipovi.contains(soilType)) {
                                biljka.zemljisniTipovi += soilType
                            }
                    }

                    if(light != null && atmosphericHumidity != null) {
                        val climateType = getClimateType(light, atmosphericHumidity)
                        biljka.klimatskiTipovi.filter { it in climateType }
                        if (climateType.isNotEmpty()) {
                            climateType.forEach { climate ->
                                if (!biljka.klimatskiTipovi.contains(climate)) {
                                    biljka.klimatskiTipovi += climateType
                                }
                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return biljka
    }

    private fun convertToSlug(latinName: String): String {
        return latinName.lowercase().replace(" ", "-")
    }

    private fun getSoilType(soilTexture: Int): Zemljiste? {
        return when (soilTexture) {
            1, 2 -> {
                Zemljiste.GLINENO
            }
            3, 4 -> {
                Zemljiste.PJESKOVITO
            }
            5, 6 -> {
                Zemljiste.ILOVACA
            }
            7, 8 -> {
                Zemljiste.CRNICA
            }
            9 -> {
                Zemljiste.SLJUNKOVITO
            }
            10 -> {
                Zemljiste.KRECNJACKO
            }
            else -> {
                null
            }
        }
    }

    private fun getClimateType(light: Int, atmosphericHumidity: Int): List<KlimatskiTip> {
        val climates = emptyList<KlimatskiTip>().toMutableList()

        if(light in 6..9 && atmosphericHumidity in 1..5) climates += KlimatskiTip.SREDOZEMNA
        if(light in 8..10 && atmosphericHumidity in 7..10) climates += KlimatskiTip.TROPSKA
        if(light in 6..9 && atmosphericHumidity in 5..8) climates += KlimatskiTip.SUBTROPSKA
        if(light in 4..7 && atmosphericHumidity in 3..7) climates += KlimatskiTip.UMJERENA
        if(light in 7..9 && atmosphericHumidity in 1..2) climates += KlimatskiTip.SUHA
        if(light in 0..5 && atmosphericHumidity in 3..7) climates += KlimatskiTip.PLANINSKA

        return climates
    }

    suspend fun getPlantsWithFlowerColor(flower_color:String,substr:String):List<Biljka> {
        var slugs = emptyList<String>()
        val plants = emptyList<Biljka>().toMutableList()
        try {
            // Make the network request synchronously within the IO dispatcher
            val result = withContext(Dispatchers.IO) {
                BiljkaRepository.searchPlant(substr, flower_color)
            }

            // Check the result of the network request
            when (result) {
                is GetBiljkeResponse -> {
                    slugs = result.data.map {plant ->
                        plant.slug
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }



        slugs.forEach { slug ->
            try {
                // Make the network request synchronously within the IO dispatcher
                val result = withContext(Dispatchers.IO) {
                    BiljkaRepository.getBiljkeData(slug)
                }

                // Check the result of the network request
                when (result) {
                    is GetBiljkeDataResponse -> {
                        val family = result.data.mainSpecies.family
                        val edible = result.data.mainSpecies.edible
                        val toxicity = result.data.mainSpecies.specifications.toxicity
                        val soilTexture = result.data.mainSpecies.growth?.soilTexture
                        val light = result.data.mainSpecies.growth?.light
                        val atmosphericHumidity = result.data.mainSpecies.growth?.atmosphericHumidity
                        val soil = soilTexture?.let { getSoilType(it) }
                        val soils = mutableListOf<Zemljiste>()
                        if(soil != null) {
                            soils += soil
                        }
                        var climates = emptyList<KlimatskiTip>()
                        if(light != null && atmosphericHumidity != null) {
                            climates = getClimateType(light, atmosphericHumidity)
                        }

                        plants += Biljka(
                            (result.data.commonName ?: "") + " (" + (result.data.scientificName ?: "") + ")",
                            family ?: "",
                            "",
                            emptyList(),
                            null,
                            emptyList(),
                            climates,
                            soils
                        )

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return plants
    }
}