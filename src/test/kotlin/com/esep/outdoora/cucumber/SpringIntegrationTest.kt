package com.esep.outdoora.cucumber

import com.esep.outdoora.profile.CucumberIntegrationTest
import io.cucumber.spring.CucumberContextConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestTemplate
import java.io.IOException

@SpringBootTest(classes = [CucumberIntegrationTest::class])
@CucumberContextConfiguration
class SpringIntegrationTest {
    protected var restTemplate: RestTemplate = RestTemplate()

    private fun addSessionAttribute(restTemplate: RestTemplate, key: String, value: Any) {
        val requestFactory = restTemplate.requestFactory
        if (requestFactory is org.springframework.http.client.SimpleClientHttpRequestFactory) {
            // Disable buffering for session attributes
            val clientHttpRequestInterceptor =
                ClientHttpRequestInterceptor { request, body, execution ->
                    request.headers.add(key, value.toString())
                    execution.execute(request, body)
                }
            restTemplate.interceptors.add(clientHttpRequestInterceptor)
        }
    }

    @Throws(IOException::class)
    fun executeGet(url: String?) {
        val headers: MutableMap<String, String> = HashMap()
        headers["Accept"] = "application/json"
        val requestCallback = HeaderSettingRequestCallback(headers)
        val errorHandler = ResponseResultErrorHandler()
        restTemplate!!.errorHandler = errorHandler
        latestResponse = restTemplate!!.execute(url!!, HttpMethod.GET, requestCallback, { response ->
            if (errorHandler.hadError) {
                return@execute (errorHandler.getResults())
            } else {
                return@execute (ResponseResults(response))
            }
        })
    }

    @Throws(IOException::class)
    fun executeGetWithUserId(url: String?, useId: Int?) {
        val headers: MutableMap<String, String> = HashMap()
        headers["Accept"] = "application/json"
        val requestCallback = HeaderSettingRequestCallback(headers)
        val errorHandler = ResponseResultErrorHandler()
        if (useId != null) addSessionAttribute(restTemplate, "userId", useId)
        restTemplate!!.errorHandler = errorHandler
        latestResponse = restTemplate!!.execute(url!!, HttpMethod.GET, requestCallback, { response ->
            if (errorHandler.hadError) {
                return@execute (errorHandler.getResults())
            } else {
                return@execute (ResponseResults(response))
            }
        })
    }

    @Throws(IOException::class)
    fun executePost() {
        val headers: MutableMap<String, String> = HashMap()
        headers["Accept"] = "application/json"
        val requestCallback: HeaderSettingRequestCallback = HeaderSettingRequestCallback(headers)
        val errorHandler = ResponseResultErrorHandler()

        if (restTemplate == null) {
            restTemplate = RestTemplate()
        }

        restTemplate!!.errorHandler = errorHandler
        latestResponse = restTemplate!!
            .execute("http://localhost:8080/baeldung", HttpMethod.POST, requestCallback, { response ->
                if (errorHandler.hadError) {
                    return@execute (errorHandler.getResults())
                } else {
                    return@execute (ResponseResults(response))
                }
            })
    }

    private inner class ResponseResultErrorHandler : ResponseErrorHandler {
        private var results: ResponseResults? = null
        var hadError: Boolean = false

        fun getResults(): ResponseResults? {
            return results
        }

        @Throws(IOException::class)
        override fun hasError(response: ClientHttpResponse): Boolean {
            hadError = response.statusCode.value() >= 400
            return hadError
        }

        @Throws(IOException::class)
        override fun handleError(response: ClientHttpResponse) {
            results = ResponseResults(response)
        }
    }

    companion object {
        var latestResponse: ResponseResults? = null
    }
}