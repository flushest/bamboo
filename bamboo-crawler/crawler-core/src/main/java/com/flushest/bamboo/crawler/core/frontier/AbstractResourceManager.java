package com.flushest.bamboo.crawler.core.frontier;

import com.flushest.bamboo.crawler.core.storage.Storage;
import com.flushest.bamboo.framework.extension.ExtensionLoader;
import com.flushest.bamboo.framework.util.ClassUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Administrator on 2018/1/10 0010.
 */
public abstract class AbstractResourceManager<T> implements ResourceManager<T> {
    protected Map<String, Storage<T>> queueMap;

    public AbstractResourceManager() {
        queueMap = new HashMap<>();
    }

    public int remainNum(String key) {
        if(queueMap.containsKey(key)) {
            return queueMap.get(key).length();
        }else {
            return 0;
        }
    }

    protected Storage<T> getQueueStorage(String key) {
        Storage<T> queue = queueMap.get(key);
        if(queue == null) {
            synchronized (this) {
                if (queue == null) {
                    ExtensionLoader<Storage> extensionLoader = ExtensionLoader.getExtensionLoader(Storage.class);
                    queue = extensionLoader.getExtension(ClassUtil.getGenericClass(this, AbstractResourceManager.class, 0).getName());
                    queueMap.put(key, queue);
                }
            }
        }
        return queue;
    }

    @Override
    public T accept(String key) throws InterruptedException {
        Storage<T> queue = getQueueStorage(key);
        return queue.get();
    }

    @Override
    public T accept(String key, long timeout, TimeUnit unit) throws InterruptedException {
        Storage<T> queue = getQueueStorage(key);
        return queue.get(timeout, unit);
    }

    @Override
    public boolean offer(String key, T t) {
        Storage<T> queue = getQueueStorage(key);
        return queue.put(t);
    }




}
