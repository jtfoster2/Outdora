package com.esep.outdoora.cucumber

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers

class StepDefs : SpringIntegrationTest() {

    var sessionUerId = 123

    @When("^the client calls /landing$")
    @Throws(Throwable::class)
    fun the_client_issues_GET_landing() {
        executeGet("http://localhost:8080/landing")
    }

    @When("^Navigate to editProfile page$")
    @Throws(Throwable::class)
    fun the_client_issues_GET_editProfile() {
        executeGet("http://localhost:8080/editProfile")
    }

    @When("^Navigate to activityPreferences page$")
    @Throws(Throwable::class)
    fun the_client_issues_GET_activityPreferences() {
        executeGet("http://localhost:8080/activityPreferences")
    }

    @Then("^the client receives status code of (\\d+)$")
    @Throws(Throwable::class)
    fun the_client_receives_status_code_of(statusCode: Int) {
        val currentStatusCode = latestResponse!!.theResponse.statusCode
        MatcherAssert.assertThat(
            "status code is incorrect : " + latestResponse!!.body,
            currentStatusCode.value(),
            Matchers.`is`(statusCode)
        )
    }

    // write an and that takes in a string and checks that the body contains that string
    @And("^the client receives body containing \"([^\"]*)\"$")
    @Throws(Throwable::class)
    fun the_client_receives_body_containing(body: String) {
        MatcherAssert.assertThat(latestResponse!!.body, Matchers.containsString(body))
    }

    @And("^the client receives server version (.+)$")
    @Throws(Throwable::class)
    fun the_client_receives_server_version_body(version: String) {
        MatcherAssert.assertThat(latestResponse!!.body, Matchers.`is`(version))
    }

    @And("^the client calls /$")
    @Throws(Throwable::class)
    fun the_client_visits_the_home_page() {
        executeGet("http://localhost:8080/home")
    }

    @Given("^user that is login$")
    @Throws(Throwable::class)
    fun user_that_is_login() {

    }

    @When("Navigate to recommandation page")
    @Throws(Throwable::class)
    fun navigate_to_page() {
        executeGetWithUserId("http://127.0.0.1:8080/recommandation", sessionUerId)
        val currentStatusCode = latestResponse!!.theResponse.statusCode
        MatcherAssert.assertThat(
            "status code is incorrect : " + latestResponse!!.body,
            currentStatusCode.value(),
            Matchers.`is`(200)
        )
    }
//    And : fill up profile information name : Peter wall, age: 34, descriptions: I am an Engineer
//    And : Save new profile with name : Peter wall, age: 34, descriptions: I am an Engineer

}