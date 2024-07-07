package com.esep.outdoora.cucumber

import org.springframework.http.client.ClientHttpRequest
import org.springframework.web.client.RequestCallback
import java.io.IOException

class HeaderSettingRequestCallback(val requestHeaders: Map<String, String>) : RequestCallback {
    private var body: String? = null

    fun setBody(postBody: String?) {
        this.body = postBody
    }

    @Throws(IOException::class)
    override fun doWithRequest(request: ClientHttpRequest) {
        val clientHeaders = request.headers
        for ((key, value) in requestHeaders) {
            clientHeaders.add(key, value)
        }
        if (null != body) {
            request.body.write(body!!.toByteArray())
        }
    }
}