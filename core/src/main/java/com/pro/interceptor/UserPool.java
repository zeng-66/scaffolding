package com.pro.interceptor;

import com.pro.entity.User;

public class UserPool {
    private static final InheritableThreadLocal<User> userPool = new InheritableThreadLocal<User>();

    public static void clear(){
        userPool.set(null);
        userPool.remove();
    }

    public static User getUser()   {
        return userPool.get();
    }

    public static void setUser(User user)  {
        userPool.set(user);
    }
}
