package com.cts.mfpe.claim.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.cts.mfpe.claim.exceptions.GlobalExceptionHandler;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)

class GlobalExceptionHandlerTest {

	@Spy
	@InjectMocks
	GlobalExceptionHandler globalExceptionHandler;
	@Mock
	GlobalExceptionHandler gloabalExceptionHandlerMockBean;
	
	@Test
	@DisplayName("Checking if GlobalExceptionHandler class is loading or not")
	void globalExceptionHandlerIsLoadingOrNot() {
		
		assertThat(globalExceptionHandler).isNotNull();
	}
	
	
	
}
