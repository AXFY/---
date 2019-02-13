package com.pactera.demo.batch;

import com.dap.batch.dto.BatchParams;
import com.dap.batch.exception.BatchException;
import com.dap.batch.service.BatchAbstractStepExecutor;
import com.dap.batch.utils.FileUtil;
import com.pactera.demo.po.Employee;
import java.util.List;

/**
 * @Author: zhoulonghui
 * @Date: 2019/2/13 9:14
 */
public class SelectBatchStep extends BatchAbstractStepExecutor<String ,Employee>  {
    /**
     * 把数据通过重写getTotalResult的方法查出来，采用selectQueryResult方法
     * @param batchParams
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public int getTotalResult(BatchParams batchParams,String object) throws BatchException{
        Long a = dao.selectQueryResult(new Employee(),0,0).getTotalrecord();
        return a.intValue();
    }

    /**
     * 把每条数据写到文件中，重写getDataList方法，使用selectQueryResult方法
     * 采用FileUtil.write的方法把数据写入到文件中
     * @param batchParams
     * @param offset
     * @param pageSize
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public List<Employee> getDataList(BatchParams batchParams,int offset,int pageSize,String object) throws BatchException{
        List<Employee> list = dao.selectQueryResult(new Employee(),offset/pageSize+1,pageSize).getResultlist();
        FileUtil.write("e://a.txt",list,true);
        return list;
    }
}
