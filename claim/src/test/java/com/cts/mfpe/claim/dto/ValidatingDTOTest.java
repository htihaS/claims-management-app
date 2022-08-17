package com.cts.mfpe.claim.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)

class ValidatingDTOTest {
	@Spy
	@InjectMocks
	ValidatingDTO validatingDTO=new ValidatingDTO();
	
	@Test
	@DisplayName("Checking if ValidatingDTO class is loading or not")
	void validatingDTOIsLoadingOrNot() {
		assertThat(validatingDTO).isNotNull();
	}
	
	@Test
	@DisplayName("Checking all the getter and setter ")
	void checkValidatingDTO() {
		ValidatingDTO validatingDTO=new ValidatingDTO(true);
		
		validatingDTO.setValidStatus(false);
		
		assertEquals(false, validatingDTO.isValidStatus());
	}
	
}