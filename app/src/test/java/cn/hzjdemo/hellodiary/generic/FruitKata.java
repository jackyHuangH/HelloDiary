package cn.hzjdemo.hellodiary.generic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author:Hzj
 * @date :2019/6/17/017
 * desc  ：泛型测试
 * record：
 */
public class FruitKata {

    class Fruit {
    }

    class Apple extends Fruit {
    }

    public void eat(List<Fruit> fruitList) {
    }

    @Test
    public void test() {
        List<? extends Fruit> list = new ArrayList<>();
        List<? super Apple> as=new ArrayList<>();
        as.add(new Apple());

    }

    public <T extends Apple> T plant(T fruit) {
        return fruit;
    }
}
