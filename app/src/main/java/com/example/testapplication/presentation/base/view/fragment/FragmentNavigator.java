package com.example.testapplication.presentation.base.view.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.testapplication.R;

public class FragmentNavigator {
    private final FragmentManager mFragmentManager;

    public FragmentNavigator(final FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    public FragmentManager getFragmentManager() {
        return mFragmentManager;
    }

    public void clearBackStack() {
        mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//        for (int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
//            mFragmentManager.popBackStackImmediate();
//        }
    }

    public void popBackStack() {
        mFragmentManager.popBackStack();
    }

    public void replaceFragment(final int container, final Fragment fragment) {
        replaceFragment(container, fragment, false, null, false);
    }

    public void replaceFragment(final int container, final Fragment fragment, final boolean addToBackStack, final String tag) {
        replaceFragment(container, fragment, addToBackStack, tag, false);
    }

    public void replaceFragment(final int container, final Fragment fragment, final boolean animate) {
        replaceFragment(container, fragment, false, null, animate);
    }

    public void replaceFragment(final int container, final Fragment fragment, final boolean addToBackStack, final String tag, final boolean animate) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
//        if (animate) {
//            transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
//        }
        if (tag == null) {
            transaction.replace(container, fragment);
        } else {
            transaction.replace(container, fragment, tag);
        }
        if (addToBackStack) {
            transaction.addToBackStack(tag);
        }
        transaction.commit();
    }
}
