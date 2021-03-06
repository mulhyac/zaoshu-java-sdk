package me.liangdi.zaoshu.api;

import lombok.extern.slf4j.Slf4j;
import me.liangdi.zaoshu.ApiException;
import me.liangdi.zaoshu.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by liangdi on 6/27/17.
 */
@Slf4j
public class InstanceApiTest extends ApiBase{

    InstanceApi instanceApi;

    @Before
    public void init(){
        //设置 ApiBase 中的 apiKey 和  secret
        KeyPair keyPair = new KeyPair(apiKey,secret);
        instanceApi = new InstanceApi();
        instanceApi.init(keyPair);
    }


    @Test
    public void testListInstances() throws ApiException {
        InstanceList list = instanceApi.list();

        list.getData().forEach(data -> {
            log.info("instance:\n{}",gson.toJson(data));
        });
    }

    @Test
    public void testGetSchema() throws ApiException {
        String instanceId = "1ebd240d26a14db4a3acae5e287be5ac";
        Schema schema = instanceApi.schema(instanceId);
        log.info("schema:{}",schema);
    }

    @Test
    public void testListTask() throws ApiException {
        String instanceId = "1ebd240d26a14db4a3acae5e287be5ac";
        TaskList list = instanceApi.taskList(instanceId);
        list.getData().forEach(data -> {
            log.info("task:\n{}",gson.toJson(data));
        });
    }

    @Test
    public void testRunInstance() throws ApiException {
        String instanceId = "d4351194a41f4526bbada92eff75e743";

        ApiResult apiResult = instanceApi.run(instanceId, null, null);

        log.info("run result:\n{}",gson.toJson(apiResult));
    }

    @Test
    public void testEditInstance() throws ApiException {
        String instanceId = "d4351194a41f4526bbada92eff75e743";
        String title = "modify by api";
        ApiResult apiResult = instanceApi.edit(instanceId, title, null);

        log.info("run result:\n{}",gson.toJson(apiResult));

        Instance instance = instanceApi.get(instanceId);

        log.info("new instance:\n{}",gson.toJson(instance));
        // todo 确定问题
        Assert.assertEquals(instance.getData().getTitle(),title);
    }

    @Test
    public void testDownloadJsonData() throws ApiException {
        //appInstanceId=d4351194a41f4526bbada92eff75e743&taskId=e31ccf9a07e143dbbeae61ef7c5e1dcd
        String instanceId = "d4351194a41f4526bbada92eff75e743";
        String taskId = "e31ccf9a07e143dbbeae61ef7c5e1dcd";

        String json = instanceApi.downloadJsonData(instanceId, taskId);

        log.info("json:\n{}",json);
    }
}
