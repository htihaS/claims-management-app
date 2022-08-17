package com.cts.mfpe.claim.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cts.mfpe.claim.client.PolicyClient;
import com.cts.mfpe.claim.dto.BenefitsDTO;
import com.cts.mfpe.claim.dto.ClaimAmountDTO;
import com.cts.mfpe.claim.dto.ClaimDTO;
import com.cts.mfpe.claim.dto.ClaimStatusDTO;
import com.cts.mfpe.claim.dto.ProviderDTO;
import com.cts.mfpe.claim.exceptions.PolicyException;
import com.cts.mfpe.claim.model.Benefits;
import com.cts.mfpe.claim.model.Hospital;
import com.cts.mfpe.claim.repo.ClaimRepo;



// @SpringBootTest
@ExtendWith(MockitoExtension.class)
class SubmitClaimServiceTest {

	@Spy
	@InjectMocks
	SubmitClaimService submitClaimService;
	@Mock
	ClaimRepo claimRepo;
	@Mock
	PolicyClient policyClient;
	
	@Test
	@DisplayName("Checking if SubmitClaimService class is loading or not")
	void submitClaimServiceIsLoadedOrNot() {
		assertThat(submitClaimService).isNotNull();
	}
	
	@Test
	@DisplayName("Checking if SubmitClaim method is working or not with valid claim object")
	void testSubmitClaimMethodWithValidClaimObject() {
		ClaimDTO claimDTO=new ClaimDTO("C101","Pending","Verified","Nothing",12000.0,"H101","B101","P101","M101");
		
		String token="AAA";
		Hospital hospital1 = new Hospital("H101","Apollo Hospital","Delhi-Indraprastha");
		Hospital hospital2 = new Hospital("H102","Artemis Hospital","Gurgaon");
		Hospital hospital3 = new Hospital("H103","Fortis Escorts Heart Institute","Delhi-Okhla");
		List<Hospital> hospitalList=new ArrayList<>();
		hospitalList.add(hospital1);
		hospitalList.add(hospital2);
		hospitalList.add(hospital3);
		
		Benefits b1 = new Benefits("B101","Coverage for COVID-19");
		Benefits b2 = new Benefits("B102","Coverage for hospitalization at home");
		Benefits b3 = new Benefits("B103","Ambulance charges upto 2000 covered");
		Benefits b4 = new Benefits("B104","Ambulance charges upto 3000 covered");
		Benefits b5 = new Benefits("B105","Ambulance charges upto 4000 covered");
		List<Benefits> benefitList=new ArrayList<>();
		benefitList.add(b1);
		benefitList.add(b2);
		benefitList.add(b3);
		benefitList.add(b4);
		benefitList.add(b5);
		
		ProviderDTO providerDTO=new ProviderDTO(hospitalList);
		BenefitsDTO benefitsDTO=new BenefitsDTO(benefitList);
		ClaimAmountDTO claimAmountDTO=new ClaimAmountDTO(120000);
		
		when(policyClient.getChainOfProviders("P101", token)).thenReturn(new ResponseEntity<ProviderDTO>(providerDTO,HttpStatus.OK));
		when(policyClient.getEligibleBenefits("P101", "M101", token)).thenReturn(new ResponseEntity<BenefitsDTO>(benefitsDTO,HttpStatus.OK));
		when(policyClient.getEligibleAmount("P101", "M101", token)).thenReturn(new ResponseEntity<ClaimAmountDTO>(claimAmountDTO,HttpStatus.OK));
		
		ClaimStatusDTO claimStatusDTO=new ClaimStatusDTO();
		claimStatusDTO.setClaimId("C101");
		claimStatusDTO.setClaimStatus("Pending Action");
		claimStatusDTO.setClaimDescription("All the fields are successfully verified! Please wait for furthur action");
		
		
		assertEquals(claimStatusDTO.getClaimId(), submitClaimService.submitClaim(claimDTO, token).getBody().getClaimId()); 
		assertEquals(claimStatusDTO.getClaimStatus(), submitClaimService.submitClaim(claimDTO, token).getBody().getClaimStatus()); 
		assertEquals(claimStatusDTO.getClaimDescription(),submitClaimService.submitClaim(claimDTO, token).getBody().getClaimDescription()); 
		
	}
	
	@Test
	@DisplayName("Checking Submit Claim Method with Wrong Policy ID")
	void testSubmitClaimWithInvalidPolicyId() {
		ClaimDTO claimDTO=new ClaimDTO("C101","Pending","Verified","Nothing",12000.0,"H101","B101","P1222","M101");
		String token="AAA";
		when(policyClient.getChainOfProviders("P1222", token)).thenThrow(PolicyException.class);
		assertThrows(PolicyException.class, () ->
        submitClaimService.submitClaim(claimDTO,token));
		
	}
	
	@Test
	@DisplayName("Checking Submit Claim Method with valid Policy ID But Null record")
	void testSubmitClaimWithNullProvider() {
		ClaimDTO claimDTO=new ClaimDTO("C101","Pending","Verified","Nothing",12000.0,"H101","B101","P101","M101");
		String token="AAA";
		
		Benefits b1 = new Benefits("B101","Coverage for COVID-19");
		Benefits b2 = new Benefits("B102","Coverage for hospitalization at home");
		Benefits b3 = new Benefits("B103","Ambulance charges upto 2000 covered");
		Benefits b4 = new Benefits("B104","Ambulance charges upto 3000 covered");
		Benefits b5 = new Benefits("B105","Ambulance charges upto 4000 covered");
		List<Benefits> benefitList=new ArrayList<>();
		benefitList.add(b1);
		benefitList.add(b2);
		benefitList.add(b3);
		benefitList.add(b4);
		benefitList.add(b5);
		
		ProviderDTO providerDTO=new ProviderDTO();
		BenefitsDTO benefitsDTO=new BenefitsDTO(benefitList);
		ClaimAmountDTO claimAmountDTO=new ClaimAmountDTO(120000);
		
		when(policyClient.getChainOfProviders("P101", token)).thenReturn(new ResponseEntity<ProviderDTO>(providerDTO,HttpStatus.OK));
		when(policyClient.getEligibleBenefits("P101", "M101", token)).thenReturn(new ResponseEntity<BenefitsDTO>(benefitsDTO,HttpStatus.OK));
		when(policyClient.getEligibleAmount("P101", "M101", token)).thenReturn(new ResponseEntity<ClaimAmountDTO>(claimAmountDTO,HttpStatus.OK));
		
		ClaimStatusDTO claimStatusDTO=new ClaimStatusDTO();
		claimStatusDTO.setClaimId("C101");
		claimStatusDTO.setClaimStatus("Claim Rejected");
		claimStatusDTO.setClaimDescription("Providers Details are not valid");
		
		assertEquals(claimStatusDTO.getClaimId(), submitClaimService.submitClaim(claimDTO, token).getBody().getClaimId()); 
		assertEquals(claimStatusDTO.getClaimStatus(), submitClaimService.submitClaim(claimDTO, token).getBody().getClaimStatus()); 
		assertEquals(claimStatusDTO.getClaimDescription(),submitClaimService.submitClaim(claimDTO, token).getBody().getClaimDescription()); 
	}
	
	@Test
	@DisplayName("Checking Submit Claim Method with valid Benefit ID But Null record")
	void testSubmitClaimWithNullBenefits() {
		ClaimDTO claimDTO=new ClaimDTO("C101","Pending","Verified","Nothing",12000.0,"H101","B101","P101","M101");
		String token="AAA";
		
		Hospital hospital1 = new Hospital("H101","Apollo Hospital","Delhi-Indraprastha");
		Hospital hospital2 = new Hospital("H102","Artemis Hospital","Gurgaon");
		Hospital hospital3 = new Hospital("H103","Fortis Escorts Heart Institute","Delhi-Okhla");
		List<Hospital> hospitalList=new ArrayList<>();
		hospitalList.add(hospital1);
		hospitalList.add(hospital2);
		hospitalList.add(hospital3);
		
		
		
		ProviderDTO providerDTO=new ProviderDTO(hospitalList);
		BenefitsDTO benefitsDTO=new BenefitsDTO();
		ClaimAmountDTO claimAmountDTO=new ClaimAmountDTO(120000.0);
		
		when(policyClient.getChainOfProviders("P101", token)).thenReturn(new ResponseEntity<ProviderDTO>(providerDTO,HttpStatus.OK));
		when(policyClient.getEligibleBenefits("P101", "M101", token)).thenReturn(new ResponseEntity<BenefitsDTO>(benefitsDTO,HttpStatus.OK));
		when(policyClient.getEligibleAmount("P101", "M101", token)).thenReturn(new ResponseEntity<ClaimAmountDTO>(claimAmountDTO,HttpStatus.OK));
		
		ClaimStatusDTO claimStatusDTO=new ClaimStatusDTO();
		claimStatusDTO.setClaimId("C101");
		claimStatusDTO.setClaimStatus("Claim Rejected");
		claimStatusDTO.setClaimDescription("Benefits Details are not valid");
		
		assertEquals(claimStatusDTO.getClaimId(), submitClaimService.submitClaim(claimDTO, token).getBody().getClaimId()); 
		assertEquals(claimStatusDTO.getClaimStatus(), submitClaimService.submitClaim(claimDTO, token).getBody().getClaimStatus()); 
		assertEquals(claimStatusDTO.getClaimDescription(),submitClaimService.submitClaim(claimDTO, token).getBody().getClaimDescription()); 
	}
	
	@Test
	@DisplayName("Checking Submit Claim Method with Invalid Amount record")
	void testSubmitClaimWithInvalidAmount() {
		ClaimDTO claimDTO=new ClaimDTO("C101","Pending","Verified","Nothing",12000.0,"H101","B101","P101","M101");
		String token="AAA";
		
		Hospital hospital1 = new Hospital("H101","Apollo Hospital","Delhi-Indraprastha");
		Hospital hospital2 = new Hospital("H102","Artemis Hospital","Gurgaon");
		Hospital hospital3 = new Hospital("H103","Fortis Escorts Heart Institute","Delhi-Okhla");
		List<Hospital> hospitalList=new ArrayList<>();
		hospitalList.add(hospital1);
		hospitalList.add(hospital2);
		hospitalList.add(hospital3);
		Benefits b1 = new Benefits("B101","Coverage for COVID-19");
		Benefits b2 = new Benefits("B102","Coverage for hospitalization at home");
		Benefits b3 = new Benefits("B103","Ambulance charges upto 2000 covered");
		Benefits b4 = new Benefits("B104","Ambulance charges upto 3000 covered");
		Benefits b5 = new Benefits("B105","Ambulance charges upto 4000 covered");
		List<Benefits> benefitList=new ArrayList<>();
		benefitList.add(b1);
		benefitList.add(b2);
		benefitList.add(b3);
		benefitList.add(b4);
		benefitList.add(b5);
		
		ProviderDTO providerDTO=new ProviderDTO(hospitalList);
		BenefitsDTO benefitsDTO=new BenefitsDTO(benefitList);
		ClaimAmountDTO claimAmountDTO=new ClaimAmountDTO(120.0);
		
		when(policyClient.getChainOfProviders("P101", token)).thenReturn(new ResponseEntity<ProviderDTO>(providerDTO,HttpStatus.OK));
		when(policyClient.getEligibleBenefits("P101", "M101", token)).thenReturn(new ResponseEntity<BenefitsDTO>(benefitsDTO,HttpStatus.OK));
		when(policyClient.getEligibleAmount("P101", "M101", token)).thenReturn(new ResponseEntity<ClaimAmountDTO>(claimAmountDTO,HttpStatus.OK));
		
		ClaimStatusDTO claimStatusDTO=new ClaimStatusDTO();
		claimStatusDTO.setClaimId("C101");
		claimStatusDTO.setClaimStatus("Claim Rejected");
		claimStatusDTO.setClaimDescription("Claim amount is not valid");
		
		assertEquals(claimStatusDTO.getClaimId(), submitClaimService.submitClaim(claimDTO, token).getBody().getClaimId()); 
		assertEquals(claimStatusDTO.getClaimStatus(), submitClaimService.submitClaim(claimDTO, token).getBody().getClaimStatus()); 
		assertEquals(claimStatusDTO.getClaimDescription(),submitClaimService.submitClaim(claimDTO, token).getBody().getClaimDescription()); 
	}
	
	
	@Test
	@DisplayName("Checking Submit Claim Method with Wrong Member ID")
	void testSubmitClaimWithInvalidMemeberId() {
		ClaimDTO claimDTO=new ClaimDTO("C101","Pending","Verified","Nothing",12000.0,"H101","B101","P101","M1222");
		String token="AAA";
		Hospital hospital1 = new Hospital("H101","Apollo Hospital","Delhi-Indraprastha");
		Hospital hospital2 = new Hospital("H102","Artemis Hospital","Gurgaon");
		Hospital hospital3 = new Hospital("H103","Fortis Escorts Heart Institute","Delhi-Okhla");
		List<Hospital> hospitalList=new ArrayList<>();
		hospitalList.add(hospital1);
		hospitalList.add(hospital2);
		hospitalList.add(hospital3);
		ProviderDTO providerDTO=new ProviderDTO(hospitalList);
		when(policyClient.getChainOfProviders("P101", token)).thenReturn(new ResponseEntity<ProviderDTO>(providerDTO,HttpStatus.OK));
		when(policyClient.getEligibleBenefits("P101","M1222", token)).thenThrow(PolicyException.class);
		assertThrows(PolicyException.class, () ->
        submitClaimService.submitClaim(claimDTO,token));
		
	}
	
	
	
	
	
	
}
