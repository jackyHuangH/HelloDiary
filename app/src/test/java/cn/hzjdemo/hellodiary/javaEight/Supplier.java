package cn.hzjdemo.hellodiary.javaEight;

/**
 * @author:Hzj
 * @date :2018/10/19/019
 * desc  ：
 * record：
 */
@FunctionalInterface
public interface Supplier<T> {
    T get();
}
