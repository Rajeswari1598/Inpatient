package com.admin.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.admin.bean.BedAllocationBean;
import com.admin.bean.BedBean;

import com.admin.exception.RecordNotFoundException;
import com.admin.service.BedService;
@Controller
@RequestMapping("/bed")
public class BedEntityController {
	@Autowired
	BedService bedService;
	private static Logger log = LoggerFactory
			.getLogger(BedAllocationController.class.getSimpleName());
	
	@PostMapping("/save")
	public ResponseEntity<BedBean> save(@RequestBody BedBean bedBean) {
		log.info("Saving Bed");
		   BedBean bed1 = bedService.save(bedBean);
		   ResponseEntity<BedBean> responseEntity = new ResponseEntity<>(bed1, HttpStatus.CREATED);
		   log.info("Saving Bed is done");
		   return responseEntity;
		
			
	}
	
	@GetMapping("/getById/{bedId}")
	public ResponseEntity<BedBean> getById(@PathVariable Long bedId) {
		 log.info("Getting Bed Details by Id");
		 
		  BedBean bed = bedService.getById(bedId);
		
		 log.info("Getting Bed Details by Id is done");
		 return new ResponseEntity<BedBean>(bed, HttpStatus.OK);
		 
		 
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<BedBean>> getAll() {
		log.info("Getting  All Bed Details");
		
		    List<BedBean> list = bedService.getAll();
		    ResponseEntity<List<BedBean>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
		    log.info("Getting  All Bed Details is done");
		    return responseEntity;
		
	}
	
	@GetMapping(path ="/getByRoomId/{id}")
	public ResponseEntity<List<BedBean>> getAllByWard(@PathVariable Long id){
		List<BedBean> list= bedService.findByBedIdRoomEntityId(id);
		return new ResponseEntity<List<BedBean>>(list,HttpStatus.OK);
	}

	@PutMapping("/update/{bedId}")
	public ResponseEntity<String> put(@PathVariable Long bedId,@RequestBody BedBean bed) {

		log.info("Updating BedStaus");
		try {
			     bedService.update(bedId,bed);
			     ResponseEntity<String> responseEntity = new ResponseEntity<>("Bed Status updated Successfully", HttpStatus.OK);
					log.info("Updating Bed is done");
					return responseEntity;
			}
			 catch (RecordNotFoundException e) {
			log.error("error handled");
			return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/deleteById/{bedId}")
	public ResponseEntity<String> deleteById(@PathVariable Long bedId) {
		log.info("Deleting BedAllocation By Id");
		try {
			bedService.getById(bedId);
		    bedService.delete(bedId);
		  ResponseEntity responseEntity = new ResponseEntity<>("Deleted Successfully",HttpStatus.OK);
		  log.info("Deleting Bed By Id is done");
		return responseEntity;
		}catch(RecordNotFoundException e) {
			  log.error("error handled");
			  return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		  }
	}

}
