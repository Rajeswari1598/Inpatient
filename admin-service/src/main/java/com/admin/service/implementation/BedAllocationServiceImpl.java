package com.admin.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.admin.bean.BedAllocationBean;
import com.admin.bean.BedBean;
import com.admin.bean.DepartmentBean;
import com.admin.bean.PatientBean;
import com.admin.bean.RoomBean;
import com.admin.bean.RoomTypeBean;
import com.admin.bean.WardBean;
import com.admin.entity.BedAllocation;
import com.admin.entity.BedEntity;
import com.admin.entity.Department;
import com.admin.entity.RoomEntity;
import com.admin.entity.RoomType;
import com.admin.entity.Ward;
import com.admin.exception.RecordNotFoundException;
import com.admin.repository.BedAllocationRepository;
import com.admin.service.BedAllocationService;

@Service
public class BedAllocationServiceImpl implements BedAllocationService{

	@Autowired
	BedAllocationRepository bedAllocationRepository; 
	
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public PatientBean getDetails(int id)
	{
		String url = "http://localhost:8082/registration/"+id;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(headers);

		ResponseEntity<PatientBean> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity,
				PatientBean.class);
		
		if (responseEntity.getStatusCode().is2xxSuccessful()) {
	        // Retrieve the response body containing the BedAllocationBean
	        PatientBean  patientBean = responseEntity.getBody();

	        // Check for null before returning
	        if (patientBean != null) {
	            return patientBean;
	        } else {
	            // Handle the case where the response body is null
	            // You can throw an exception, log a message, or return a default value
	            // For example, throw new RuntimeException("Received null response for bedId: " + bedId);
	        }
	    }
		else {
	    	System.out.println("exception occured");
	    	return null;
	        // Handle non-successful HTTP status codes if needed
	        // For example, log an error message or throw an exception
	        // throw new RuntimeException("Failed to retrieve data. Status code: " + responseEntity.getStatusCodeValue());
	    }
		//BedAllocationBean bedAllocation = responseEntity.getBody();		
		
		//return bedAllocation;
		return null;
	}
	public BedAllocationBean save(BedAllocationBean bedAllocationBean) {
		// TODO Auto-generated method stub
		BedAllocation bedAllocation=new BedAllocation();
		beanToEntity(bedAllocationBean,bedAllocation);
	    bedAllocationRepository.save( bedAllocation);
	    return bedAllocationBean;
	}

	private void beanToEntity(BedAllocationBean bedAllocationBean, BedAllocation bedAllocation) {
		// TODO Auto-generated method stub
		bedAllocation.setId(bedAllocationBean.getId());
		
		BedBean bedBean=bedAllocationBean.getBedId();
		BedEntity bedEntity=new BedEntity();
		beanToEntity(bedBean,bedEntity);
		bedAllocation.setBedId(bedEntity);
		
		bedAllocation.setPatientId(bedAllocationBean.getPatientId());
		bedAllocation.setNoOfDays(bedAllocationBean.getNoOfDays());
		bedAllocation.setStartDate(bedAllocationBean.getStartDate());
		bedAllocation.setEndDate(bedAllocationBean.getEndDate());
		bedAllocation.setStatus(bedAllocationBean.getStatus());
	}
    
	private void beanToEntity(BedBean bedBean, BedEntity bedEntity) {
	
	   
		RoomBean roomBean=bedBean.getRoomId();
	    RoomEntity roomEntity=new RoomEntity();
	    beanToEntity(roomEntity,roomBean);
	    bedEntity.setId(bedBean.getId());
	    bedEntity.setRoomId(roomEntity);
	    bedEntity.setBedNo(bedBean.getBedNo());
		bedEntity.setStatus(bedBean.getStatus());
	}

	public void beanToEntity(RoomEntity roomEntity, RoomBean roomBean) {
		roomEntity.setId(roomBean.getId());
		roomEntity.setRoomNo(roomBean.getRoomNo());
		RoomTypeBean roomTypeBean=roomBean.getRoomTypeId();
		RoomType roomType=new RoomType();
		beanToEntity(roomTypeBean,roomType);
		roomEntity.setRoomTypeId(roomType);
		roomEntity.setRoomPrice(roomBean.getRoomPrice());
		roomEntity.setRoomSharing(roomBean.getRoomSharing());
		WardBean wardBean=roomBean.getWardId();
		Ward ward=new Ward();
		beanToEntity(ward,wardBean);
		roomEntity.setWardId(ward);
	}
	
	private void beanToEntity(RoomTypeBean roomTypeBean, RoomType roomType) {
		// TODO Auto-generated method stub
		roomType.setId(roomTypeBean.getId());
		roomType.setName(roomTypeBean.getName());
	}
	
	private void beanToEntity(Ward ward, WardBean wardBean) {
		ward.setId(wardBean.getId());
		ward.setName(wardBean.getName());
		ward.setCapacity(wardBean.getCapacity());
		ward.setAvailability(wardBean.getAvailability());
		DepartmentBean DepartmentBean = wardBean.getDepartmentId();
		Department Department = new Department();
		beanToEntity(DepartmentBean, Department);
		ward.setDepartmentId(Department);

	}
	
	public void beanToEntity(DepartmentBean DepartmentBean, Department Department) {
		Department.setId(DepartmentBean.getId());
		Department.setName(DepartmentBean.getName());

	}
	@Override
	public BedAllocationBean getById(int id) {
		// TODO Auto-generated method stub
		BedAllocation bedAllocation= bedAllocationRepository.findById(id).orElseThrow(()->new RecordNotFoundException("No Record Found with given id"));
		BedAllocationBean bedAllocationBean=new BedAllocationBean();
		entityToBean(bedAllocation,bedAllocationBean);
		return bedAllocationBean;
	}

	private void entityToBean(BedAllocation bedAllocation, BedAllocationBean bedAllocationBean) {
		// TODO Auto-generated method stub
		bedAllocationBean.setId(bedAllocation.getId());
		bedAllocationBean.setEndDate(bedAllocation.getEndDate());
		bedAllocationBean.setNoOfDays(bedAllocation.getNoOfDays());
		bedAllocationBean.setStartDate(bedAllocation.getStartDate());
		bedAllocationBean.setPatientId(bedAllocation.getPatientId());
		BedEntity bedEntity=bedAllocation.getBedId();
		BedBean bedBean=new BedBean();
		entityToBean(bedEntity,bedBean);
		bedAllocationBean.setBedId(bedBean);

	
		bedAllocationBean.setStatus(bedAllocation.getStatus());
	}

	@Override
	public List<BedAllocationBean> getAll() {
		// TODO Auto-generated method stub
		 List<BedAllocation> entityList= bedAllocationRepository.findAll();
		 List<BedAllocationBean> beanList=new ArrayList<>();
		 entityToBean(entityList,beanList);
		 return beanList;
	}

	private void entityToBean(List<BedAllocation> entitylist, List<BedAllocationBean> beanList) {
		// TODO Auto-generated method stub
		for(BedAllocation bedAllocation: entitylist)
		{
			BedAllocationBean bedAllocationBean=new BedAllocationBean();
			bedAllocationBean.setId(bedAllocation.getId());
			bedAllocationBean.setEndDate(bedAllocation.getEndDate());
			BedEntity bedEntity=bedAllocation.getBedId();
			BedBean bedBean=new BedBean();
			entityToBean(bedEntity,bedBean);
			bedAllocationBean.setBedId(bedBean);
			bedAllocationBean.setNoOfDays(bedAllocation.getNoOfDays());
			bedAllocationBean.setStartDate(bedAllocation.getStartDate());
			bedAllocationBean.setPatientId(bedAllocation.getPatientId());
			
			bedAllocationBean.setStatus(bedAllocation.getStatus());
			beanList.add(bedAllocationBean);
		}
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		bedAllocationRepository.deleteById(id);
		
	}

	@Override
	public void update(BedAllocationBean bedAllocationBean) {
		// TODO Auto-generated method stub
		BedAllocation bedAllocation= bedAllocationRepository.getReferenceById(bedAllocationBean.getId());
		bedAllocation.setId(bedAllocationBean.getId());
		bedAllocation.setStartDate(bedAllocationBean.getStartDate());
		bedAllocation.setEndDate(bedAllocationBean.getEndDate());
		bedAllocation.setNoOfDays(bedAllocationBean.getNoOfDays());
		bedAllocation.setPatientId(bedAllocationBean.getPatientId());
		BedBean bedBean=bedAllocationBean.getBedId();
		BedEntity bedEntity=new BedEntity();
		beanToEntity(bedBean,bedEntity);
		bedAllocation.setBedId(bedEntity);
		bedAllocation.setStatus(bedAllocationBean.getStatus());
		bedAllocationRepository.save(bedAllocation);

	}

	
	private void entityToBean(BedEntity bedEntity, BedBean bedBean) {
		// TODO Auto-generated method stub
		bedBean.setId(bedEntity.getId());
		bedBean.setBedNo(bedEntity.getBedNo());
		RoomEntity roomEntity=bedEntity.getRoomId();
		RoomBean roomBean=new RoomBean();
		entityToBean(roomEntity,roomBean);
		bedBean.setStatus(bedEntity.getStatus());
	}
public void entityToBean(RoomEntity roomEntity, RoomBean roomBean) {

		
		roomBean.setId(roomEntity.getId());

		RoomType roomType = roomEntity.getRoomTypeId();
		RoomTypeBean roomTypeBean = new RoomTypeBean();
		entityToBean(roomType, roomTypeBean);
		roomBean.setRoomTypeId(roomTypeBean);

		roomBean.setRoomNo(roomEntity.getRoomNo());
		roomBean.setRoomPrice(roomEntity.getRoomPrice());
		roomBean.setRoomSharing(roomEntity.getRoomSharing());
		
		Ward entity = roomEntity.getWardId();
		WardBean wardBean = new WardBean();
		entityToBean(wardBean, entity);
		roomBean.setWardId(wardBean);

	}

private void entityToBean(RoomType roomType, RoomTypeBean roomTypeBean) {
	// TODO Auto-generated method stub
	roomTypeBean.setId(roomType.getId());
	roomTypeBean.setName(roomType.getName());
}
	private void entityToBean(WardBean wardBean, Ward ward) {
		wardBean.setId(ward.getId());
		wardBean.setName(ward.getName());
		wardBean.setCapacity(ward.getCapacity());
		wardBean.setAvailability(ward.getAvailability());
		DepartmentBean DepartmentBean = new DepartmentBean();
		Department Department = ward.getDepartmentId();
		entityToBean(Department, DepartmentBean);
		wardBean.setDepartmentId(DepartmentBean);

	}
	public void entityToBean(Department Department, DepartmentBean DepartmentBean) {
		DepartmentBean.setId(Department.getId());
		DepartmentBean.setName(Department.getName());
	}

}

