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

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
	
 class ClaimAmountDTOTest {

	@Spy
	@InjectMocks
	 ClaimAmountDTO claimAmountDTO=new ClaimAmountDTO();
	 
	 @Test
	 @DisplayName("Checking if claimAmountDTO class is loading or not")
	 void claimAmountDTOisLoadingOrNot() {
		 assertThat(claimAmountDTO).isNotNull();
	 }
	 
	 @Test
	 @DisplayName("Checking all the getter and setter method")
	 void checkClaimAmountDTO() {
		 
		 ClaimAmountDTO claimAmountDTO=new ClaimAmountDTO(10000.0);
		 
		 claimAmountDTO.setEligibleAmount(12000.0);
		 
		 assertEquals(12000.0, claimAmountDTO.getEligibleAmount());
	 }
}
