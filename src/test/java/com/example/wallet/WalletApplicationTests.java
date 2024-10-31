package com.example.wallet;

import com.example.wallet.domain.Wallet;
import com.example.wallet.repository.WalletRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WalletApplicationTests {

	@LocalServerPort
	protected Integer port;

	protected final TestRestTemplate restTemplate = new TestRestTemplate();

	protected static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

	@Autowired
	protected WalletRepository walletRepository;

	protected UUID presentWalletId = UUID.fromString("d0c5f731-c076-44d3-83a8-2f2304faeb58");

	@BeforeAll
	public static void beforeAll() {
		postgres.start();
	}

	@AfterAll
	public static void afterAll() {
		postgres.stop();
	}

	@DynamicPropertySource
	public static void configurePostgreSQL(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@BeforeEach
	public void setup() {
		Wallet wallet = new Wallet();
		wallet.setId(presentWalletId);
		wallet.setBalance(100D);

		walletRepository.save(wallet);
	}

}
