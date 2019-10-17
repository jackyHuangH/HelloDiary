package com.zenchn.support.fragmentutil;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import java.util.List;

/**
 * @author:Hzj
 * @date :2019/9/23/023
 * desc  ：定义一个BackHandlerHelper工具类，用于实现分发back事件,
 * Fragment和Activity的外理逻辑是一样，所以两者都需要调用该类的方法
 * record：
 */
public class BackHandlerHelper {

    /**
     * 将back事件分发给 FragmentManager 中管理的子Fragment，如果该 FragmentManager 中的所有Fragment都
     * 没有处理back事件，则尝试 FragmentManager.popBackStack()
     *
     * @return 如果处理了back键则返回 <b>true</b>
     * @see #handleBackPress(Fragment)
     * @see #handleBackPress(FragmentActivity)
     */
    public static boolean handleBackPress(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments == null) {
            return false;
        }
        //子fragment请求处理返回事件
        for (int i = fragments.size() - 1; i >= 0; i--) {
            Fragment child = fragments.get(i);
            if (isFragmentBackHandled(child)) {
                return true;
            }
        }
        //fragment栈不为空时，优先退栈
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
            return true;
        }
        return false;
    }

    public static boolean handleBackPress(Fragment fragment) {
        return handleBackPress(fragment.getChildFragmentManager());
    }

    public static boolean handleBackPress(FragmentActivity fragmentActivity) {
        return handleBackPress(fragmentActivity.getSupportFragmentManager());
    }

    /**
     * 判断Fragment是否处理了Back键
     *
     * @return 如果处理了back键则返回 <b>true</b>
     */
    public static boolean isFragmentBackHandled(Fragment fragment) {
        return fragment != null
                && fragment.isVisible()
                && fragment.getUserVisibleHint() //for ViewPager
                && fragment instanceof FragmentBackHandler
                && ((FragmentBackHandler) fragment).onFragmentBackPressed();
    }
}
