package com.luoshihai.xxhander;

import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;

import java.lang.ref.SoftReference;
import java.util.HashMap;

/**
 * 用集合存储软引用调用的handler(核心)
 *
 * @author Liqi
 */
public class BaseHandler extends Handler implements BaseHandlerMethod {
    private static BaseHandler baseHandler;
    private BaseHandlerGetKey baseHandlerGetKey;
    private SparseArray<SoftReference<BaseHandlerUpDate>> arrayReference;
    private FactoryOperateInterface factoryOperateInterface;

    private HashMap<String, Object> tempMap = new HashMap<>();

    public void setFactoryOperateInterface(FactoryOperateInterface factoryOperateInterface) {
        this.factoryOperateInterface = factoryOperateInterface;
    }

    /**
     * 获取handler对象
     *
     * @return
     */
    public synchronized static BaseHandler getBaseHandler() {
        synchronized (BaseHandler.class.getName()) {
            if (baseHandler == null) {
                baseHandler = new BaseHandler();
            }
        }
        return baseHandler;
    }

    private BaseHandler() {
        if (null == arrayReference)
            arrayReference = new SparseArray<SoftReference<BaseHandlerUpDate>>();
    }

    @Override
    public void handleMessage(Message msg) {
        if (null != arrayReference && arrayReference.size() > 0) {
            if (null != baseHandlerGetKey) {
                SoftReference<BaseHandlerUpDate> reference = arrayReference
                        .get(baseHandlerGetKey.handlerGetKey());
                if (null != reference) {
                    BaseHandlerUpDate baseActivity = reference.get();
                    if (null != baseActivity)
                        baseActivity.handleMessage(msg);
                }
            } else
                System.out.println("handleMessage>>>>handler获取Key接口为空");
        }
    }

    /**
     * 把传进来的对象通过塞入软引用添加进SparseArray集合里面
     *
     * @param activity
     */

    @Override
    public void addSparseArray(BaseHandlerUpDate activity, Class<?> clazz) {
        if (null != baseHandlerGetKey && null != clazz) {
            arrayReference.put(baseHandlerGetKey.handlerGetKey(),
                    new SoftReference<BaseHandlerUpDate>(activity));
            for (String s : tempMap.keySet()) {
                if (s.contains(clazz.getName())) {
                    int tag = Integer.parseInt(s.split(clazz.getName())[1]);
                    Message message = this.obtainMessage();
                    message.what = tag;
                    message.obj = tempMap.get(s);
                    this.sendMessage(message);
                    tempMap.remove(s);
                    break;

                }
            }
        } else {
            System.out.println("addSparseArray>>>handler获取Key接口为空");
        }

    }


    /**
     * 发送message信息个handler
     *
     * @param tag
     * @param obj
     */
    @Override
    public void putMessage(int tag, Object obj, Class clazz) {
        if (null == arrayReference.get(baseHandlerGetKey.handlerGetKey())) {
            tempMap.put(clazz.getName() + tag, obj);
        } else {
            Message message = this.obtainMessage();
            message.what = tag;
            message.obj = obj;
            this.sendMessage(message);
        }
    }

    /**
     * 清除SparseArray集合中指定键的软引用的数据 (!--Key是通过接口获取的，只需要调用此方法即可)
     */
    @Override
    public void removeKeyData() {
        if (null != arrayReference && arrayReference.size() > 0) {
            if (null != baseHandlerGetKey) {
                SoftReference<BaseHandlerUpDate> reference = arrayReference
                        .get(baseHandlerGetKey.handlerGetKey());
                if (null != reference) {
                    reference.clear();
                    arrayReference.remove(baseHandlerGetKey.handlerGetKey());
                    if (factoryOperateInterface != null) {
                        factoryOperateInterface.removeFactoryKeyData();
                    }
                }
            } else
                System.out.println("removeKeyData>>>handler获取Key接口为空");
        }
    }

    /**
     * 清除SparseArray集合里面所有值
     */
    @Override
    public void removeAll() {
        if (null != arrayReference && arrayReference.size() > 0) {
            for (int i = 0; i < arrayReference.size(); i++) {
                arrayReference.valueAt(i).clear();
            }
            arrayReference.clear();
            if (factoryOperateInterface != null) {
                factoryOperateInterface.removeAllFactoryData();
            }
        }
    }

    @Override
    public void setBaseHandlerGetKey(BaseHandlerGetKey baseHandlerGetKey) {
        this.baseHandlerGetKey = baseHandlerGetKey;
    }

}
