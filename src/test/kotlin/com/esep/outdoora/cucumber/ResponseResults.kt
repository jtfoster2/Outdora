package com.esep.outdoora.cucumber

import org.springframework.http.client.ClientHttpResponse
import org.testcontainers.shaded.org.apache.commons.io.IOUtils
import java.io.StringWriter

class ResponseResults internal constructor(
    val theResponse: ClientHttpResponse
) {
    val body: String

    init {
        val bodyInputStream = theResponse.body
        val stringWriter = StringWriter()
        IOUtils.copy(bodyInputStream, stringWriter)
        this.body = stringWriter.toString()
    }
}