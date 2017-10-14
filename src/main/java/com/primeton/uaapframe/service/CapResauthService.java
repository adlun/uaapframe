package com.primeton.uaapframe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.primeton.uaapframe.bean.CapResauthBean;
import com.primeton.uaapframe.dao.CapResauthDao;
import com.primeton.uaapframe.util.RemoteServiceUtil;

@Service
public class CapResauthService {
	
	@Autowired
	private CapResauthDao capResauthDao;
	
	@Autowired
	private SqlSessionFactoryBean sqlSessionFactoryBean;
	
	public void operateCapResauth() throws Exception {
		String batchnum = UUID.randomUUID().toString();
//		System.out.println(batchnum);
//		List<Map> data = capResauthDao.getDataByBatchNum(batchnum);
//		System.out.println(data);
		SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBean.getObject();
		SqlSession session = sqlSessionFactory.openSession();
		HashMap<String,String> map = new HashMap<String , String>();
		map.put("batchnum", batchnum);
		map.put("systemid", "default");
		session.update("com.primeton.uaapframe.mapper.CapResauthMapper.updateBtachNum", map);
		session.commit();
		List<CapResauthBean> data = session.selectList("com.primeton.uaapframe.mapper.CapResauthMapper.getDataByBatchNum",batchnum);
		String json = JSON.toJSONString(data);
		System.out.println(json);
		String result = RemoteServiceUtil.post("http://127.0.0.1:8080/default/rest/services/RoleService/addRole", json);
		System.out.println(result);
		if("fail".equals(result)){
			map.put("status", "0");
		}else{
			map.put("status", "2");
		}
		session.update("com.primeton.uaapframe.mapper.CapResauthMapper.updateStatus",map);
		session.commit();
	}

}
