package com.pactera.demo.batch;

import com.dap.batch.dto.BatchParams;
import com.dap.batch.exception.BatchException;
import com.dap.batch.service.BatchAbstractStepExecutor;
import com.pactera.demo.po.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhoulonghui
 * @Date: 2019/2/13 10:18
 */
public class InsertBatchStep extends BatchAbstractStepExecutor<String,Integer> {
  @Override
    public int getTotalResult(BatchParams batchParams,String object) throws BatchException{
      return 1000;
  }
    public List<Integer> getDataList(BatchParams batchParams, int offset, int pageSize, String object) throws BatchException {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < pageSize; i++) {
            list.add(offset + i);
        }
        return list;
    }
    public void execute(BatchParams batchParams, int index, Employee employee, String object) throws BatchException {
        logger.info("执行=====================================[{}]======================="+index);
        employee.setName("小四");
        employee.setSex("男");
        employee.setDept("开发");
        employee.setAge(18);
        dao.insert(employee);
    }
}
