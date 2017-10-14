package com.primeton.uaapframe.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface CapResauthDao {
	
	/**
	 * 未同步数据生成批次号
	 * @param batchnum
	 */
	public void generateBatchNum(String batchnum,String systemid);
	
	/**
	 * 根据批次号获取数据
	 * @param batchnum
	 */
	public List<Map> getDataByBatchNum(String batchnum);
	
	/**
	 * 更新数据状态
	 * @param batchnum
	 */
	public void updateStatusByBatchNum(String batchnum);

}
