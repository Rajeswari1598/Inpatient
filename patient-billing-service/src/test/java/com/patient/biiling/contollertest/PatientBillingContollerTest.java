package com.patient.biiling.contollertest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import com.patient.billing.service.controller.PatientBillingController;
import com.patient.billing.service.dto.PatientBillingDTO;
import com.patient.billing.service.service.PatientBillingService;

@ExtendWith(MockitoExtension.class)
public class PatientBillingContollerTest {
	@Mock
	private PatientBillingService service;
	@InjectMocks
	private PatientBillingController controller;
	 
	private PatientBillingDTO billing;
	
	@BeforeEach
	public void setUp() {
		billing = new PatientBillingDTO();
		billing.setBillId(1);
		billing.setFirstName("jyothi");
	}

}
