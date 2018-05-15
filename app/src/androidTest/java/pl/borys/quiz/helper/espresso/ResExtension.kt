package pl.borys.quiz.helper.espresso

import android.support.annotation.StringRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.ViewAction
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.CoreMatchers.not

fun Int.perform(viewAction: ViewAction) {
    onView(withId(this)).perform(viewAction)
}

fun Int.check(viewAssertion: ViewAssertion) {
    onView(withId(this)).check(viewAssertion)
}

fun Int.checkText(viewAssertion: ViewAssertion) {
    onView(withText(this)).check(viewAssertion)
}

fun Int.click() {
    this.perform(android.support.test.espresso.action.ViewActions.click())
}

fun Int.assertIsChecked() {
    this.check(matches(isChecked()))
}

fun Int.assertIsSelected() {
    this.check(matches(isSelected()))
}

fun Int.assertIsDisplayed() {
    this.check(matches(isCompletelyDisplayed()))
}

fun Int.assertIsNotDisplayed() {
    this.check(matches(not(isDisplayed())))
}

fun Int.assertTextIsDisplayed() {
    this.checkText(matches(isDisplayed()))
}

fun Int.assertTextIsSelected() {
    this.checkText(matches(isSelected()))
}

infix fun Int.hasText(text: String?) {
    this.check(matches(withText(text)))
}

infix fun Int.hasText(@StringRes text: Int) {
    this.check(matches(withText(text)))
}