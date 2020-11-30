package cn.gjy.framerwork.Invocation;

import cn.gjy.framerwork.annotation.Service;
import cn.gjy.framerwork.annotation.Transactional;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
@Transactional
@Service(TestService.class)
public class TestServiceImpl implements TestService{

    private TestService testService;

    public void setTestService(TestService testService) {
        this.testService = testService;
    }

    @Override
    public void run() {
        System.out.println("run");
        //System.out.println(1 / 0);

        testService.r();
    }

    @Override
    public void r() {
        System.out.println("rrrr");
        System.out.println(1 / 0);
    }
}
