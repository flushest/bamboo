package com.flushest.bamboo.runtime.util.convert;

/**
 * Created by Administrator on 2017/10/14 0014.
 */
public interface TypeConverter<S,T> {
    T convert(S source);
}
