package com.esep.outdoora

import com.esep.outdoora.user.User
import com.esep.outdoora.user.UserRepository
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.delegate.DatabaseDelegate
import org.testcontainers.ext.ScriptUtils
import org.testcontainers.jdbc.JdbcDatabaseDelegate
import java.io.StringReader

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {
	@Autowired lateinit var userRepository: UserRepository

	lateinit var testUsers: List<User>
	// get a list of User objects for saving

	companion object {
		val logger = LoggerFactory.getLogger(IntegrationTest::class.java)
		private lateinit var postgres: PostgreSQLContainer<*>

        @JvmStatic
        @BeforeAll
        fun setUp(): Unit {
			logger.info("Starting PostgreSQL container")
            postgres = PostgreSQLContainer("postgres:16-alpine")
            postgres.start()
        }

		fun resetDatabase() {
			val resource = ClassPathResource("reset.sql")
			ScriptUtils.runInitScript(JdbcDatabaseDelegate(postgres, ""), resource.path)
		}

		fun resetUsers(userRepository: UserRepository): MutableList<User> {
			return userRepository.saveAll(getTestUsers())
		}

		private fun getTestUsers(): List<User> {
			return listOf(
				User(email = "userA@gmail.com"),
				User(email = "userB@gmail.com"),
			)
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

	@BeforeEach
	fun beforeEach() {
		logger.info("Resetting database")
		resetDatabase()
		testUsers = resetUsers(userRepository)
	}
}
