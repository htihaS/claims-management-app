package com.cts.mfpe.claim.repo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


// @SpringBootTest
@ExtendWith(MockitoExtension.class)
class ClaimRepoTest {
	@Mock
	private ClaimRepo claimRepo;
	
	@Test
	@DisplayName("Checking if Claim Repo methods Are working or not")
	void testClaimRepo() {
		when(claimRepo.getStatus("C101")).thenReturn("Pending");
		when(claimRepo.getDescription("C101")).thenReturn("Verified");
		assertEquals("Pending", claimRepo.getStatus("C101"));
		assertEquals("Verified", claimRepo.getDescription("C101"));
	}
}