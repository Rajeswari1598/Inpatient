package com.admin.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.bean.RoomTypeBean;
import com.admin.entity.RoomType;
import com.admin.exception.RecordNotFoundException;
import com.admin.repository.RoomTypeRepository;
import com.admin.service.RoomTypeService;

@Service
public class RoomTypeServiceImpl implements RoomTypeService{

	@Autowired
	RoomTypeRepository roomTypeRepository;
	@Override
	public RoomTypeBean save(RoomTypeBean roomTypeBean) {
		// TODO Auto-generated method stub
		RoomType roomType=new RoomType();
		beanToEntity(roomTypeBean,roomType);
		roomTypeRepository.save(roomType);
		return roomTypeBean;
		
	}

	private void beanToEntity(RoomTypeBean roomTypeBean, RoomType roomType) {
		// TODO Auto-generated method stub
		roomType.setId(roomTypeBean.getId());
		roomType.setName(roomTypeBean.getName());
	}

	@Override
	public List<RoomTypeBean> getAll() {
		// TODO Auto-generated method stub
		List<RoomType> entityList=roomTypeRepository.findAll();
		List<RoomTypeBean> beanList=new ArrayList<>();
		entityListToBeanList(entityList,beanList);
		return beanList;
	}

	private void entityListToBeanList(List<RoomType> entityList, List<RoomTypeBean> beanList) {
		// TODO Auto-generated method stub
		for(RoomType roomType:entityList)
		{
			RoomTypeBean roomTypeBean=new RoomTypeBean();
			entityToBean(roomType,roomTypeBean);
			beanList.add(roomTypeBean);
		}
	}

	@Override
	public RoomTypeBean getById(long id) {
		// TODO Auto-generated method stub
		
		RoomType roomType=roomTypeRepository.findById(id).orElseThrow(()->new RecordNotFoundException("There is no record with the given id"));
		
		RoomTypeBean roomTypeBean=new RoomTypeBean();
		entityToBean(roomType,roomTypeBean);
		
		return roomTypeBean;
	}

	private void entityToBean(RoomType roomType, RoomTypeBean roomTypeBean) {
		// TODO Auto-generated method stub
		roomTypeBean.setId(roomType.getId());
		roomTypeBean.setName(roomType.getName());
	}

	@Override
	public void delete(long id) {
		// TODO Auto-generated method stub
		roomTypeRepository.deleteById(id);
	}

	@Override
	public RoomType update(long id) {
		// TODO Auto-generated method stub
		return null;
	}

}