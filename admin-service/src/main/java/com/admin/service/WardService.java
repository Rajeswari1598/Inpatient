package com.admin.service;

import java.util.List;

import com.admin.bean.WardBean;
import com.admin.entity.Ward;
import com.admin.exception.RecordNotFoundException;

public interface WardService {


	WardBean getById(Long id);

	void delete(Long id);

	List<WardBean> getAll();


	List<Ward> findByDepartmentId(Long departmentId);
	void update(WardBean wardBean);



	WardBean save(WardBean wardBean);

}