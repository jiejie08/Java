package www.code.java;


/**
 * Author:li_d
 * Created:2019/3/25
 */
class Singleton{
    //产生了唯一的一个对象
    private static Singleton singleton = new Singleton();
    //限制在外部产生对象的方法
    private Singleton(){}
    public static Singleton getSingleton(){
        return singleton;
    }
}
class Sex{
    private String name;
    private static Sex male = new Sex("男人");
    private static Sex female = new Sex("女人");

    public Sex(String name) {
        this.name = name;
    }
    public static Sex getInstance(String name){
        if (name.equals("male")){
            return male;
        }else if (name.equals("female")){
            return female;
        }else {
            return null;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}

public class Test3 {
     public static void main(String[] args) {
         Sex male = Sex.getInstance("male");
         Sex female = Sex.getInstance("female");
         System.out.println(male);
         System.out.println(female);
     }
}
