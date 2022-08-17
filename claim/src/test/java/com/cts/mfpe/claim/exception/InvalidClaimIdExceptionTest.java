package com.cts.mfpe.claim.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.cts.mfpe.claim.exceptions.InvalidClaimIdException;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)
class InvalidClaimIdExceptionTest {

	InvalidClaimIdException invalidClaimIdException=new InvalidClaimIdException("Exception");
	
	@Test
	@DisplayName("Checking if InvalidClaimIdException class is loading or not")
	void invalidClaimIdExceptionIsLoadingOrNot() {
		assertThat(invalidClaimIdException).isNotNull();
	}
}
