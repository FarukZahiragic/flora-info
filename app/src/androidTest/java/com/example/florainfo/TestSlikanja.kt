package com.example.florainfo

import android.Manifest
import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.GrantPermissionRule
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/*
* Created by Faruk Zahiragic on 27.4.2024
*/

@RunWith(AndroidJUnit4::class)
class TestSlikanja {

    private lateinit var uslikajBiljkuButton: ViewInteraction
    private lateinit var slikaBiljkeIV: ViewInteraction


    //@get:Rule
    //val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.CAMERA)

    //@get:Rule
    //val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(android.Manifest.permission.CAMERA)

    @Before
    fun setUp() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.novaBiljkaBtn)).perform(click())
        connectLayoutElements()
    }

    @Test
    fun testSlikanje() {
        //onView(withId(R.id.slikaIV)).check(matches(isDisplayed()))

        Intents.init()
        val bitmap = BitmapFactory.decodeResource(
            getInstrumentation().targetContext.resources,
            R.drawable.bosiljak
        )
        val resultData = Intent().apply {
            putExtra("data", bitmap)
        }

        val result = Instrumentation.ActivityResult(Activity.RESULT_OK, resultData)
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result)
        uslikajBiljkuButton.perform(ViewActions.scrollTo())
        uslikajBiljkuButton.perform(click())
        Thread.sleep(2000)
        slikaBiljkeIV.check(matches(withImage(R.drawable.bosiljak)))
        Intents.release()

    }

    private fun withImage(expectedResourceId: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("with image drawable from resource id: ")
                description.appendValue(expectedResourceId)
            }

            override fun matchesSafely(view: View): Boolean {
                if (view !is ImageView) {
                    return false
                }
                val expectedBitmap = BitmapFactory.decodeResource(
                    view.resources,
                    expectedResourceId
                )
                val imageViewBitmap = (view as ImageView).drawable.toBitmap()
                return imageViewBitmap.sameAs(expectedBitmap)
            }
        }
    }

    private fun connectLayoutElements() {
        uslikajBiljkuButton = onView(withId(R.id.uslikajBiljkuBtn) )
        slikaBiljkeIV = onView(withId(R.id.slikaIV) )
    }
}