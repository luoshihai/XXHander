package com.luoshihai.xxhander;


/**
 * BaseHandler操作对象（核心）
 * 
 * @author Liqi
 * 
 */
public class BaseHandlerOperate implements BaseHandler.BaseHandlerGetKey,
		FactoryOperateInterface {
	// 当前操作的对象class
	protected Class<?> clazz;
	private BaseHandlerMethod handler;
	private static BaseHandlerOperate handlerOperate;
	private BaseHandlerFactoryId factoryId;

	/**
	 * 获取hBaseHandler操作对象
	 * 
	 * @return
	 */
	public synchronized static BaseHandlerOperate getBaseHandlerOperate() {
		synchronized (BaseHandlerOperate.class.getName()) {
			if (null == handlerOperate) {
				handlerOperate = new BaseHandlerOperate();
			}
		}
		return handlerOperate;
	}

	private BaseHandlerOperate() {
		handler = BaseHandler.getBaseHandler();
		handler.setBaseHandlerGetKey(this);
		factoryId = BaseHandlerFactoryId.getBaseHandlerFactoryId();
	}

	/**
	 * 把当前对象对象添加到指定键里面
	 * 
	 * @param clazz
	 *            BaseHandler-要存储对象的calss
	 */
	public BaseHandlerOperate addKeyHandler(Class<?> clazz,
			BaseHandlerUpDate handlerUpDate) {
		if (null != clazz) {
			this.clazz = clazz;
			handler.addSparseArray(handlerUpDate,clazz);
		}
		return this;
	}

	/**
	 * 给指定的handler发送message
	 * 
	 * @param clazz
	 *            BaseHandler-取出对象的class
	 * @param tag
	 *            Message标识
	 * @param obj
	 *            MessageObj数据源
	 */
	public BaseHandlerOperate putMessageKey(Class<?> clazz, int tag, Object obj) {
		if (null != clazz) {
			this.clazz = clazz;
			handler.putMessage(tag, obj,clazz);
		}
		return this;
	}


	/**
	 * 移除BaseHandler里面的指定key对象
	 * 
	 * @param clazz
	 *            BaseHandler-移除对象的class
	 */
	public BaseHandlerOperate removeKeyData(Class<?> clazz) {
		if (null != clazz) {
			this.clazz = clazz;
			handler.removeKeyData();
		}
		return this;
	}

	/**
	 * 移除所有的handler里面的对象
	 */
	public BaseHandlerOperate removeAll() {
		handler.removeAll();
		return this;
	}

	public BaseHandlerMethod getBaseHandler() {
		return handler;
	}

	@Override
	public int handlerGetKey() {
		return factoryId.getFactoryId(clazz);
	}

	@Override
	public void removeFactoryKeyData() {
		factoryId.removeKeyData(clazz);
	}

	@Override
	public void removeAllFactoryData() {
		factoryId.removeAll();
	}
}
