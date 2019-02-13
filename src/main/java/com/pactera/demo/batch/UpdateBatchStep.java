package com.pactera.demo.batch;

import com.dap.batch.dto.BatchParams;
import com.dap.batch.exception.BatchException;
import com.dap.batch.pool.ComplexThread;
import com.dap.batch.service.BatchAbstractStepExecutor;
import com.pactera.demo.po.Student;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhoulonghui
 * @Date: 2019/2/13 11:15
 */
public class UpdateBatchStep extends BatchAbstractStepExecutor<String, Integer> {
    /**
     * 把数据分为两组进行查询
     * @param batchParams
     * @return
     * @throws BatchException
     */
    @Override
    public List<String> getObjectList(BatchParams batchParams) throws BatchException {
        List<String> list = new ArrayList<String>();
        list.add("object 0");
        list.add("object 1");
        return list;
    }

    /**
     * 每一组返回2条数据
     * @param batchParams
     * @param object
     * @return
     * @throws BatchException
     */
    @Override
    public int getTotalResult(BatchParams batchParams, String object) throws BatchException {
        return 2;
    }

    /**
     * 调用getDataList和execute一条一条的处理数据
     * @param batchParams
     * @param object
     * @param pageIndex
     * @param pageSize
     * @param totalResult
     * @param commitNum
     * @param timeout
     * @throws InterruptedException
     */
    @Override
    public void processDataList(final BatchParams batchParams, final String object, int pageIndex,
                                int pageSize, int totalResult, int commitNum, int timeout) throws InterruptedException {
        int start = (pageIndex - 1) * pageSize;
        List<Integer> list = this.getDataList(batchParams, start, pageSize, object);

        // 数据量大于1的时候使用多线程并发处理，同时并发的最大线程数在BATCH_STEP表里配置
        if (list == null || list.size() == 0) {
            return;
        }

        int size = list.size();
        int index = 0;
        for (; index < size; index++) {
            // 检查线程是否中断
            ComplexThread.checkStatus();
            this.execute(batchParams, index, list.get(index), object);
        }
    }

    /**
     * 分页处理
     * @param batchParams
     * @param offset
     * @param pageSize
     * @param object
     * @return
     * @throws BatchException
     */
    public List<Integer> getDataList(BatchParams batchParams, int offset, int pageSize, String object)
            throws BatchException {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < pageSize; i++) {
            list.add(offset + i);
        }
        return list;
    }

    public void execute(BatchParams batchParams, int index, Integer data, String object)
            throws BatchException {
        Student where = new Student();
        where.setAge("22");
        Student set = new Student();
        set.setName("周龙辉");
        dao.update(set,where);
    }
}
