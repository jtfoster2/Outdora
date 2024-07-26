package com.esep.outdoora.cucumber

import io.cucumber.java.en.And
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers

class StepDefs : SpringIntegrationTest() {
    @When("^the client calls /landing$")
    @Throws(Throwable::class)
    fun the_client_issues_GET_landing() {
        executeGet("http://localhost:8080/landing")
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
}