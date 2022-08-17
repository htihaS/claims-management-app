package com.cts.mfpe.claim.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.cts.mfpe.claim.dto.ClaimDTO;
import com.cts.mfpe.claim.dto.ClaimStatusDTO;
import com.cts.mfpe.claim.exceptions.InvalidClaimIdException;
import com.cts.mfpe.claim.exceptions.InvalidTokenException;
import com.cts.mfpe.claim.service.ClaimStatusService;
import com.cts.mfpe.claim.service.SubmitClaimService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
@Api(value = "For dealing with claim ")
public class ClaimController {

	@Autowired
	private ClaimStatusService claimStatusService;
	@Autowired
	private SubmitClaimService submitClaimService;
	
//	@GetMapping(path="/Hi")
//	@ApiOperation(value = "To return Claim status", response = ClaimStatusDTO.class,httpMethod = "GET")
//	public String Hi(@RequestHeader(name = "Authorization", required = true) String token) {
//		return "Hi There";
//	}

	
	@GetMapping(path="/getClaimStatus/{id}")
	@ApiOperation(value = "To return Claim status", response = ClaimStatusDTO.class,httpMethod = "GET")
	public ResponseEntity<ClaimStatusDTO> getClaimStatus(@PathVariable("id") String id,@RequestHeader(name = "Authorization", required = true) String token)throws InvalidClaimIdException,InvalidTokenException {
		log.info(token);
		/*
		 * if (!authClient.getsValidity(token).isValidStatus()) { throw new
		 * InvalidTokenException("Token is either expired or invalid"); }
		 */
		
		
		log.info("inside the get Claim Status : BEGIN");
		return claimStatusService.getClaimStatus(id); 
	}
	

	
	@PostMapping(path ="/submitClaim")
	@ApiOperation(value = "To submit Claim", response = ClaimStatusDTO.class,httpMethod = "POST")
	public ResponseEntity<ClaimStatusDTO> submitClaim(@RequestBody ClaimDTO claimDTO,@RequestHeader(name = "Authorization", required = true) String token)throws InvalidTokenException,NullPointerException {
		log.info(token);
		/*
		 * if (!authClient.getsValidity(token).isValidStatus()) { throw new
		 * InvalidTokenException("Token is either expired or invalid"); }
		 */
		
		log.info("inside the submit Claim : BEGIN");
		return submitClaimService.submitClaim(claimDTO,token);
	}
	
}
