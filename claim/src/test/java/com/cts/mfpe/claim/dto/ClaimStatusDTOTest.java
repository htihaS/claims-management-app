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

class ClaimStatusDTOTest {

	@Spy
	@InjectMocks
	ClaimStatusDTO claimStatusDTO=new ClaimStatusDTO();
	
	@Test
	@DisplayName("Checking if claimStatusDTO class is loaded or not")
	void claimStatusDTOIsLoadingOrNot() {
		assertThat(claimStatusDTO).isNotNull();
	}
	
	@Test
	@DisplayName("Checking if all the getter and setter are working ")
	void checkClaimStatusDTO() {
		
		ClaimStatusDTO claimStatusDTO=new ClaimStatusDTO("C101","Pending","Verified");
		
		claimStatusDTO.setClaimId("C102");
		claimStatusDTO.setClaimDescription("Invalid Details");
		claimStatusDTO.setClaimStatus("Rejected");
		
		assertEquals("C102", claimStatusDTO.getClaimId());
		assertEquals("Rejected", claimStatusDTO.getClaimStatus());
		assertEquals("Invalid Details", claimStatusDTO.getClaimDescription());
	}
	
}
