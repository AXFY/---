package com.pactera.demo.batch;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dap.api.IService;
import com.dap.batch.dto.BatchParams;
import com.dap.batch.dto.BatchStepDTO;
import com.dap.batch.service.BatchStepServiceImpl;
import com.dap.param.ResponseData;
import com.dap.param.StringInput;
import com.dap.param.StringOutput;


/**
 * 任务步骤服务类，此服务分布各系统中，因此不能直接访问批量数据库
 * 
 * @author WUDUFENG
 * @since 2014年5月19日
 * 
 * 
 */
public class BatchStepServiceJsonImpl extends BatchStepServiceImpl implements IService<StringInput, StringOutput> {
    private final static Logger logger = LoggerFactory.getLogger(BatchStepServiceJsonImpl.class);


    public StringOutput handle(StringInput p) {
        StringOutput rso = new StringOutput();
        ResponseData rd = new ResponseData();
        rso.setRespData(rd);
        try {
            JSONObject jso = (JSONObject) JSON.parse(p.getBody());
            Object obj = null;
            if (jso.containsKey("obj")) {
                jso.get("obj");
            }
            Integer objIdx = null;
            if (jso.containsKey("objIdx")) {
                objIdx = jso.getInteger("objIdx");
            }
            BatchParams batchParams = null;
            if (jso.containsKey("batchParams")) {
                batchParams = jso.getObject("batchParams", BatchParams.class);
            }
            BatchStepDTO stepDTO = null;
            if (jso.containsKey("stepDTO")) {
                JSONObject bo = jso.getJSONObject("stepDTO");
                BatchStepDTO sd = new BatchStepDTO();
                sd.setClazz(bo.getString("clazz"));
                sd.setCommitNum(bo.getInteger("commitNum"));
                sd.setExeId(bo.getLong("exeId"));
                sd.setIndex(bo.getInteger("index"));
                sd.setConcurrent(bo.getInteger("pageConcurrent"));
                sd.setPageSize(bo.getInteger("pageSize"));
                sd.setTimeout(bo.getInteger("timeout"));
                sd.setStepId(bo.getString("stepId"));
                stepDTO = sd;
            }
            // 根据服务ID判断执行何种方法
            String sid = p.getCtrlData().getServcId();
            String ip = null;
            if ("executeStep".equals(sid)) {
                ip = this.executeStep(stepDTO, batchParams);
            } else if ("executeObject".equals(sid)) {
                ip = this.executeObject(stepDTO, batchParams, obj, objIdx);
            }
            rso.setBody("{ \"ip\": \"".concat(ip).concat("\" }"));
            rd.setCode("00000000");
        } catch (Exception e) {
            String sn = UUID.randomUUID().toString().replaceAll("-", "");
            logger.error("Error on handle request.", e);
            rd.setCode("PBHJS999");
            rd.setMessage(e.getMessage());
            rd.setServcSeqNo(sn);
            rd.setServerIp(RpcContext.getContext().getLocalHost());
            rd.setRecordCount(0);
            rd.setReturnTime(new Date());
        }
        return rso;
    }
}
