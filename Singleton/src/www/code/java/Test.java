package www.code.java;

/**
 * Author:li_d
 * Created:2019/2/21
 */

//业务接口
interface ISubject{
    void buyComputer();

}

//真实主题类
class RealSubjectImpl implements ISubject{
    @Override
    public void buyComputer() {
        System.out.println("买一台macbook pro");
    }
}
//代理类
class ProxySubjectImpl implements ISubject{
    private ISubject realSubject;
    //构造注入，注入真实主题类
    public ProxySubjectImpl(ISubject realSubject){
        this.realSubject = realSubject;
    }

    public void beforeSubject(){
        System.out.println("去银行取钱，排队");
    }

    @Override
    public void buyComputer() {
        this.beforeSubject();
        this.realSubject.buyComputer();
        this.afterSubject();
    }

    public void afterSubject(){
        System.out.println("装各种软件，开始coding...");
    }
}

public class Test{
    public static void main(String[] args) {
        ISubject subject = new ProxySubjectImpl(new RealSubjectImpl()) ;
        subject.buyComputer();
    }
}