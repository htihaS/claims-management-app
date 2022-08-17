package com.cts.mfpe.claim.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.mfpe.claim.exceptions.PolicyException;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)
class PolicyExceptionTest {
	
PolicyException policyException=new PolicyException("Exception");
	
	@Test
	@DisplayName("Checking if PolicyException class is loading or not")
	void policyExceptionIsLoadingOrNot() {
		assertThat(policyException).isNotNull();
	}
}
