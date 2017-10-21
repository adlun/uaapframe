package com.primeton.uaapframe.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.primeton.uaapframe.bean.CapResauthBean;
import com.primeton.uaapframe.dao.CapResauthDao;
import com.primeton.uaapframe.util.RemoteServiceUtil;

@Service
public class CapResauthService {
	
	Logger logger = LoggerFactory.getLogger(CapResauthService.class);
	
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
		if(data!=null && data.size()>0){
			logger.info("批次号:"+batchnum+" 系统:"+map.get("systemid")+" 同步数量:"+data.size());
			String json = JSON.toJSONString(data);
			System.out.println(json);
			String result = RemoteServiceUtil.post("http://127.0.0.1:8080/default/rest/services/sync/capresauth", json);
			System.out.println(result);
			JSONObject o = (JSONObject) JSON.parse(result);
			if("success".equals(o.get("status"))){
				map.put("status", "2");
			}else{
				map.put("status", "0");
			}
			session.update("com.primeton.uaapframe.mapper.CapResauthMapper.updateStatus",map);
			session.commit();
		}
	}

}
