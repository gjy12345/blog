package cn.gjy.framerwork.Invocation;

/**
 * @author gujianyang
 * @date 2020/11/26
 */
public class TestServiceImpl implements TestService{
    @Override
    public void run() {
        System.out.println("run");
        //System.out.println(1 / 0);
    }
}
