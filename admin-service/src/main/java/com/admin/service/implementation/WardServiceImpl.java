package com.admin.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.tomcat.util.http.ConcurrentDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.bean.DepartmentBean;
import com.admin.bean.WardBean;
import com.admin.entity.Department;
import com.admin.entity.Ward;
import com.admin.exception.RecordNotFoundException;
import com.admin.repository.WardRepository;
import com.admin.service.WardService;

@Service
public class WardServiceImpl implements WardService {
	@Autowired
	private WardRepository wardRepository;

	@Override
	public WardBean save(WardBean wardBean) {

		Ward ward = new Ward();
		beanToEntity(ward, wardBean);
        wardRepository.save(ward);
        return wardBean;

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

	public WardBean getById(Long id) {
		WardBean wardBean = new WardBean();

		Ward ward;
	
			ward = wardRepository.findById(id).orElseThrow(()->new RecordNotFoundException("No Record Found with given id"));
			entityToBean(wardBean, ward);

	
		return wardBean;
	}

	@Override
	public void delete(Long id) {
		
		wardRepository.deleteById(id);

	}

	@Override
	public List<WardBean> getAll() {
		List<Ward> entityList = wardRepository.findAll();
		List<WardBean> beanList = new ArrayList<>();
		entityToBean(entityList, beanList);
		return beanList;
	}

	private void entityToBean(List<Ward> entityList, List<WardBean> beanList) {
		for (Ward ward : entityList) {
			WardBean wardBean = new WardBean();
			wardBean.setId(ward.getId());
			wardBean.setName(ward.getName());
			wardBean.setCapacity(ward.getCapacity());
			wardBean.setAvailability(ward.getAvailability());
			DepartmentBean DepartmentBean = new DepartmentBean();
			Department Department = ward.getDepartmentId();
			entityToBean(Department, DepartmentBean);
			wardBean.setDepartmentId(DepartmentBean);
			beanList.add(wardBean);
		}

	}

	@Override
	public void update(WardBean wardBean)  {
		Optional<Ward>optional  = wardRepository.findById(wardBean.getId());
		if(optional.isPresent()) {
			Ward ward=optional.get();
			ward.setId(wardBean.getId());
			ward.setName(wardBean.getName());
			ward.setCapacity(wardBean.getCapacity());
			ward.setAvailability(wardBean.getAvailability());
			Department Department = ward.getDepartmentId();
			
			ward.setDepartmentId(Department);
			wardRepository.save(ward);
			
		}
		else {
			throw new RecordNotFoundException("not found in details");
		}
		


	}

	public void beanToEntity(DepartmentBean DepartmentBean, Department Department) {
		Department.setId(DepartmentBean.getId());
		Department.setName(DepartmentBean.getName());

	}

	public void entityToBean(Department department, DepartmentBean departmentBean) {
		departmentBean.setId(department.getId());
		departmentBean.setName(department.getName());
	}

	@Override
	public List<Ward> findByDepartmentId(Long departmentId) {
		// TODO Auto-generated method stub
		return wardRepository.findByDepartmentId_Id(departmentId);
	}

	


}