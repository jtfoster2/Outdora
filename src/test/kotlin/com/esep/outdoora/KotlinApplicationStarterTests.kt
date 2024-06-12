package com.esep.outdoora

import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.event.annotation.AfterTestExecution
import org.springframework.test.context.event.annotation.BeforeTestExecution
import org.testcontainers.containers.PostgreSQLContainer


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class KotlinApplicationStarterTests {
	companion object {
		private lateinit var postgres: PostgreSQLContainer<*>

        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
            postgres = PostgreSQLContainer("postgres:16-alpine")
            postgres.start()
        }
		@JvmStatic
		@DynamicPropertySource
		fun configureProperties(registry: DynamicPropertyRegistry) {
			registry.add("spring.datasource.url") { postgres.jdbcUrl }
			registry.add("spring.datasource.username") { postgres.username }
			registry.add("spring.datasource.password") { postgres.password }
		}

        @JvmStatic
        @AfterAll
        fun tearDown(): Unit {
            postgres.stop()
        }
	}


	@Test
	fun contextLoads() {
	}
}
