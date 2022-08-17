package com.cts.mfpe.claim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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

import com.cts.mfpe.claim.dto.ClaimStatusDTO;
import com.cts.mfpe.claim.exceptions.InvalidClaimIdException;
import com.cts.mfpe.claim.repo.ClaimRepo;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)
class ClaimStatusServiceTest {

	@Spy
	@InjectMocks
	private ClaimStatusService claimStatusService;
	@Mock
	private ClaimRepo claimRepo;
	
	@Test
	@DisplayName("Checking if ClaimStatusService class is loading or not")
	void claimStatusServiceIsLoadedOrNot() {
		assertThat(claimStatusService).isNotNull();
	}
	
	@Test
	@DisplayName("Checking if getClaimStatus method is working or not with valid Id")
	void getClaimStatusTestWithValidId() {
		ClaimStatusDTO claimStatusDTO=new ClaimStatusDTO();
		claimStatusDTO.setClaimId("C101");
		claimStatusDTO.setClaimStatus("Pending");
		claimStatusDTO.setClaimDescription("Verified");
		
		
		when(claimRepo.getStatus("C101")).thenReturn("Pending");
		when(claimRepo.getDescription("C101")).thenReturn("Verified");
		
		
		assertEquals(claimStatusDTO.getClaimId(), claimStatusService.getClaimStatus("C101").getBody().getClaimId()); 
		assertEquals(claimStatusDTO.getClaimStatus(), claimStatusService.getClaimStatus("C101").getBody().getClaimStatus()); 
		assertEquals(claimStatusDTO.getClaimDescription(), claimStatusService.getClaimStatus("C101").getBody().getClaimDescription()); 
	}
	
	@Test
	@DisplayName("Checking if getClaimStatus method is working or not with Invalid Id")
	void testGetClaimStatusWithInvalidValidId() {
		
		assertThrows(InvalidClaimIdException.class, () ->
         claimStatusService.getClaimStatus("C1561"));
	}
	
	
}
