package com.example.florainfo

import android.view.View
import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasErrorText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers.anything
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/*
* Created by Faruk Zahiragic on 27.4.2024
*/
@RunWith(AndroidJUnit4::class)
class TestS2 {

    private lateinit var nazivEditText: ViewInteraction
    private lateinit var porodicaEditText: ViewInteraction
    private lateinit var medicinskoUpozorenjeEditText: ViewInteraction
    private lateinit var jeloEditText : ViewInteraction
    private lateinit var medicinskaKoristTextView: ViewInteraction
    private lateinit var medicinskaKoristListView : Matcher<View>
    private lateinit var klimatskiTipTextView: ViewInteraction
    private lateinit var klimatskiTipListView : Matcher<View>
    private lateinit var zemljisniTipTextView: ViewInteraction
    private lateinit var zemljisniTipListView : Matcher<View>
    private lateinit var profilOkusaTextView: ViewInteraction
    private lateinit var profilOkusaListView : Matcher<View>
    private lateinit var jelaTextView: ViewInteraction
    private lateinit var jelaListView : Matcher<View>
    private lateinit var dodajJeloButton: ViewInteraction
    private lateinit var dodajBiljkuButton: ViewInteraction

    @Before
    fun setUp() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        connectLayoutElements()
    }

    @Test
    fun testPrazanJeloET(){
        clickButton(dodajJeloButton)
        provjeriErrorET(jeloEditText, "Duzina polja mora biti izmedju 2 i 20")
    }

    @Test
    fun testPrekratkoJeloET(){
        unesiInput(jeloEditText, "A")
        clickButton(dodajJeloButton)
        provjeriErrorET(jeloEditText, "Duzina polja mora biti izmedju 2 i 20")
    }

    @Test
    fun testPredugoJeloET(){
        unesiInput(jeloEditText, "Neko jelo sa veoma dugim nazivom")
        clickButton(dodajJeloButton)
        provjeriErrorET(jeloEditText, "Duzina polja mora biti izmedju 2 i 20")
    }

    @Test
    fun testValidJeloET(){
        unesiInput(jeloEditText, "Grah")
        clickButton(dodajJeloButton)
        provjeriNemaErroraET(jeloEditText)
    }

    @Test
    fun testPostojiJelo(){
        unesiInput(jeloEditText, "Grah")
        clickButton(dodajJeloButton)
        jeloEditText.perform(ViewActions.scrollTo())
        unesiInput(jeloEditText, "grah")
        clickButton(dodajJeloButton)
        provjeriErrorET(jeloEditText, "Vec postoji jelo u listi!")
    }

    @Test
    fun testIzmjenaNaPostojeceJelo(){
        unesiInput(jeloEditText, "Grah")
        clickButton(dodajJeloButton)
        jeloEditText.perform(ViewActions.scrollTo())
        unesiInput(jeloEditText, "gra")
        clickButton(dodajJeloButton)
        jeloEditText.perform(ViewActions.scrollTo())
        unesiInput(jeloEditText, "grah")
        clickButton(dodajJeloButton)
        provjeriErrorET(jeloEditText, "Vec postoji jelo u listi!")
    }

    @Test
    fun testDodajBiljkuPrazniTVovi(){
        clickButton(dodajBiljkuButton)
        provjeriErrorET(nazivEditText, "Duzina polja mora biti izmedju 2 i 20")
        provjeriErrorET(porodicaEditText, "Duzina polja mora biti izmedju 2 i 20")
        provjeriErrorET(medicinskoUpozorenjeEditText, "Duzina polja mora biti izmedju 2 i 20")
    }

    @Test
    fun testDodajBiljkuPrekratkiTVovi(){
        unesiInput(nazivEditText, "A")
        unesiInput(porodicaEditText, "B")
        unesiInput(medicinskoUpozorenjeEditText, "C")
        clickButton(dodajBiljkuButton)
        provjeriErrorET(nazivEditText, "Duzina polja mora biti izmedju 2 i 20")
        provjeriErrorET(porodicaEditText, "Duzina polja mora biti izmedju 2 i 20")
        provjeriErrorET(medicinskoUpozorenjeEditText, "Duzina polja mora biti izmedju 2 i 20")
    }

    @Test
    fun testDodajBiljkuIspravniTVovi(){
        unesiInput(nazivEditText, "Kupus")
        unesiInput(porodicaEditText, "Lisicarke")
        unesiInput(medicinskoUpozorenjeEditText, "Bezopasan")
        clickButton(dodajBiljkuButton)
        provjeriNemaErroraET(nazivEditText)
        provjeriNemaErroraET(porodicaEditText)
        provjeriNemaErroraET(medicinskoUpozorenjeEditText)
    }

    @Test
    fun testDodajBiljkuPredugiTVovi(){
        unesiInput(nazivEditText, "Biljka sa veoma dugim nazivom, duzine kao ova recenica.")
        unesiInput(porodicaEditText, "Biljka sa veoma dugim nazivom porodice, duzine kao ova recenica.")
        unesiInput(medicinskoUpozorenjeEditText, "Veoma dugo medicinsko upozorenje, duzine kao ova recenica.")
        clickButton(dodajBiljkuButton)
        provjeriErrorET(nazivEditText, "Duzina polja mora biti izmedju 2 i 20")
        provjeriErrorET(porodicaEditText, "Duzina polja mora biti izmedju 2 i 20")
        provjeriErrorET(medicinskoUpozorenjeEditText, "Duzina polja mora biti izmedju 2 i 20")
    }

    @Test
    fun testNeodabraniListViewElementi(){
        clickButton(dodajBiljkuButton)
        provjeriErrorTV(medicinskaKoristTextView, "Mora biti odabran makar jedan element iz liste.")
        provjeriErrorTV(medicinskaKoristTextView, "Mora biti odabran makar jedan element iz liste.")
        provjeriErrorTV(klimatskiTipTextView, "Mora biti odabran makar jedan element iz liste.")
        provjeriErrorTV(zemljisniTipTextView, "Mora biti odabran makar jedan element iz liste.")
        provjeriErrorTV(profilOkusaTextView, "Mora biti odabran jedan element iz liste.")
        provjeriErrorTV(jelaTextView, "Mora biti dodano makar jedno jelo.")
    }

    @Test
    fun testOdabranPoJedanListViewElement(){
        clickListViewItem(medicinskaKoristListView, 3)
        clickListViewItem(klimatskiTipListView, 4)
        clickListViewItem(zemljisniTipListView, 3)
        clickListViewItem(profilOkusaListView, 3)
        clickButton(dodajBiljkuButton)
        provjeriNemaErroraTV(medicinskaKoristTextView)
        provjeriNemaErroraTV(klimatskiTipTextView)
        provjeriNemaErroraTV(zemljisniTipTextView)
        provjeriNemaErroraTV(profilOkusaTextView)
        provjeriErrorTV(jelaTextView, "Mora biti dodano makar jedno jelo.")
    }

    @Test
    fun testSviListViewValidni(){
        testValidJeloET()
        clickListViewItem(medicinskaKoristListView, 0)
        clickListViewItem(klimatskiTipListView, 1)
        clickListViewItem(zemljisniTipListView, 2)
        clickListViewItem(profilOkusaListView, 1)
        clickButton(dodajBiljkuButton)
        provjeriNemaErroraTV(medicinskaKoristTextView)
        provjeriNemaErroraTV(klimatskiTipTextView)
        provjeriNemaErroraTV(zemljisniTipTextView)
        provjeriNemaErroraTV(profilOkusaTextView)
        provjeriNemaErroraTV(jelaTextView)
    }

    @Test
    fun testSveValidnoOsimJela(){
        testDodajBiljkuIspravniTVovi()
        testOdabranPoJedanListViewElement()
    }

    @Test
    fun testSveValidno(){
        testDodajBiljkuIspravniTVovi()
        testValidJeloET()
        clickListViewItem(medicinskaKoristListView, 0)
        clickListViewItem(klimatskiTipListView, 1)
        clickListViewItem(zemljisniTipListView, 2)
        clickListViewItem(profilOkusaListView, 1)
        clickButton(dodajBiljkuButton)
        Thread.sleep(2000)
        dodajBiljkuButton.check(doesNotExist())
    }



    // Utility functions
    private fun clickButton(button: ViewInteraction) {
        button.perform(ViewActions.scrollTo())
        button.perform(click())
    }

    private fun unesiInput(editText: ViewInteraction, input: String) {
        editText.perform(typeText(input))
        Espresso.closeSoftKeyboard()
    }

    private fun provjeriErrorET(editText: ViewInteraction, errorText: String) {
        editText.check(matches((hasErrorText(errorText))))
    }

    private fun provjeriNemaErroraET(editText: ViewInteraction) {
        editText.check(matches((hasErrorText(nullValue(String::class.java)))))
    }

    private fun provjeriErrorTV(textView: ViewInteraction, errorText: String) {
        textView.check(matches(withErrorText(errorText)))
    }

    private fun provjeriNemaErroraTV(textView: ViewInteraction) {
        textView.check(matches(withErrorText(null)))
    }

    private fun withErrorText(expectedError: String?): Matcher<in View> {
        return object : TypeSafeMatcher<Any>() {
            override fun matchesSafely(item: Any): Boolean {
                if (item is TextView) {
                    return item.error?.toString() == expectedError
                }
                return false
            }

            override fun describeTo(description: Description) {
                description.appendText("with error text: $expectedError")
            }
        }
    }

    private fun clickListViewItem(listView: Matcher<View>, position: Int) {
        onView(listView).perform(ViewActions.scrollTo())
        onData(anything())
            .inAdapterView(listView)
            .atPosition(position)
            .perform(click())
    }

    private fun connectLayoutElements() {
        nazivEditText = onView(withId(R.id.nazivET))
        porodicaEditText = onView(withId(R.id.porodicaET))
        medicinskoUpozorenjeEditText = onView(withId(R.id.medicinskoUpozorenjeET))
        jeloEditText = onView(withId(R.id.jeloET))

        medicinskaKoristTextView = onView(withId(R.id.medicinskaKoristTV))
        medicinskaKoristListView = withId(R.id.medicinskaKoristLV)
        klimatskiTipTextView = onView(withId(R.id.klimatskiTipTV))
        klimatskiTipListView = withId(R.id.klimatskiTipLV)

        zemljisniTipTextView = onView(withId(R.id.zemljisniTipTV))
        zemljisniTipListView = withId(R.id.zemljisniTipLV)

        profilOkusaTextView = onView(withId(R.id.profilOkusaTV))
        profilOkusaListView = withId(R.id.profilOkusaLV)

        jelaTextView = onView(withId(R.id.jelaTV))
        jelaListView = withId(R.id.jelaLV)

        dodajJeloButton = onView(withId(R.id.dodajJeloBtn))
        dodajBiljkuButton = onView(withId(R.id.dodajBiljkuBtn))
    }
}