package com.pactera.demo.batch;

import com.dap.batch.dto.BatchParams;
import com.dap.batch.exception.BatchException;
import com.dap.batch.service.BatchAbstractStepExecutor;
import com.pactera.demo.po.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * @Author: zhoulonghui
 * @Date: 2019/2/13 10:54
 */
public class DeleteBatcStep extends BatchAbstractStepExecutor<String,Employee>{
    Logger LOGGER = LoggerFactory.getLogger(getClass());


    /**
     * 查询符合条件的总记录数
     *
     * @param batchParams
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public int getTotalResult(BatchParams batchParams, String object) throws BatchException {
        LOGGER.info("-------------------------getTotalResult()开始");

        Employee employee = new Employee();
        int count = dao.count(employee);
        LOGGER.info("{}", count);
        return count;
    }

    /**
     * 分页查询需要处理的记录列表
     * 获取一个文件或者表的数据，使用firstResult参数支持文件分块获取数据或者对表分页查询<br/>
     * 若getObjectList.size大于1，则此方法是并发的
     *
     * @param offset 起始记录下标<br/>
     *               开始为0 ， 在分页查询或者文件分块处理时使用 ， 若使用分页查询必须要覆盖getTotalResult(Object
     *               object)方法 ， 若对查询处理的数据进行update操作，则不能使用此参数，需要使用0
     * @param object 为getObjectList()方法返回的列表的一个具体对象
     * @return
     */
    @Override
    public List<Employee> getDataList(BatchParams batchParams, int offset, int pageSize, String object)
            throws BatchException {
        LOGGER.info("getDataList()开始");

        Employee select = new Employee();
        List<Employee> list = dao.selectQueryResult(Employee.class.getName() + ".selectByGender", select, offset/pageSize+1, pageSize)
                .getResultlist();
        LOGGER.info("{}", list);
        return list;
    }


    /**
     * 处理每一条记录
     * <b>业务处理方法</b><br/>
     * 如果子类覆盖了getDataList()方法，<br/>
     * 则此方法是对dataList的单个元素进行处理，<br/>
     * 对于dataList的遍历是在StepService里进行的，每个元素单独调起一个线程进行处理;
     * 同时并发的最大线程数可在表BATCH_STEP里配置<br/>
     * 若不覆盖dataList，StepService是直接调起此方法<br/>
     * 若dataList.size大于1，则此方法是并发的
     *
     * @param index     表示data在object里的下标，代表这条记录在文件的第几行 ,从1开始
     * @param data 为getDataList()方法返回的列表里的一个具体数据，例如是文件里的一条记录
     * @param object    为getObjectList()方法返回的列表的一个具体对象,不能在此方法内修改object的任何属性
     */
    public void execute(BatchParams batchParams,int index,Integer data,String object) throws BatchException{
        LOGGER.info("execute()开始");
        Employee where = new Employee();
        where.setId(1);
        Employee set = new Employee();
        set.setName("123");
        dao.update(set,where);
    }


    /**
     * 用于最后更新表数据或者删除文件
     *
     * @param object 为getObjectList()方法返回的列表的一个具体对象
     */
    @Override
    public void updateObject(BatchParams batchParams, String object) {
        logger.info("updateObject():{} completed", object);
    }
}
