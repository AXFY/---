package com.pactera.demo.batch;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dap.api.IService;
import com.dap.batch.dto.BatchParams;
import com.dap.batch.dto.BatchExecution;
import com.dap.batch.dto.BatchJobDTO;
import com.dap.batch.service.IBatchGroupService;
import com.dap.batch.service.IBatchJobService;
import com.dap.param.ControlData;
import com.dap.param.StringInput;
import com.dap.param.StringOutput;


@RunWith(SpringJUnit4ClassRunner.class)//RunWith就是一个运行器，@RunWith(SpringJUnit4ClassRunner.class),让测试运行于Spring测试环境
@ContextConfiguration(locations = "classpath*:spring/test-service.xml")//引入配置文件
public class DemoBatchTester1 {
    @Resource(name="batchGroupService") //只要写了，就会暴露出去
    private IBatchGroupService groupService;
    @Resource(name="batchJobService")
    private IBatchJobService jobService;

    /**
     * 暴露了三个接口，group，job，step,在控制中心里group,job里面是相当于服务器的存在，我们的batch是一样的，
     * 所以group ，job是一样的，只是我们各自的ip不一样，所以显示有四个提供者，采用负载均衡，调用不同的服务器，在这里没有使用负载均衡，采用随机的调用
     * disbatch启动，将group和job暴露在外面，启动demobatch，暴露我的step,在测试类中调用我要测试的group
     *
     * 没有阻塞，在group和job里面就看不到消费者
     * 但在job里面可以看到我的消费者，是因为我在启动bootStrap时，job消费了一次
     */

   /* @Test
    public void startGroup() {
        groupService.start("GDemoGroup");
    }*/

   /* @Test //文件生成
    public void startGroup() {
        groupService.start("fileDemoGroup");
    }*/

    @Test
    public void startGroup(){
        groupService.start("selectGroup");
    }

}
