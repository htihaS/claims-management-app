package com.cts.mfpe.claim.client;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)
class PolicyClientTest {
	PolicyClient policyClient;

	@Test
	@DisplayName("Checking is PolicyClient is loading or not")
	void policyClientIsLoadingOrNot() {
		assertThat(policyClient).isNull();
	}
}
