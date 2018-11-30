package com.himer.android.player.util;

import java.util.Collection;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2018/11/29.
 */
public class CollectionUtil {

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isIndexInRange(Collection collection, int index) {
        return !isEmpty(collection) && index >= 0 && index < collection.size();
    }
}
