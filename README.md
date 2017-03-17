这个库的作者是这个大神写的 我只是根据我自己项目的特殊需求改了一点功能, 放到github上来用起来方便点

###[他的github地址](https://github.com/LiqiNew/HandlerFrame)
###[他的博客](http://www.jianshu.com/p/e9fbb99593cb)

## 添加依赖
1.在project目录的build.gradle的allprojects节点添加
```java maven { url "https://jitpack.io" }```
如下:

    allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
    }

2.在自己Modul的build.gradle中添加``` 'compile 'com.github.luoshihai:XXHander:V1.0.1''```
如下:

	dependencies {
	        compile 'com.github.luoshihai:XXHander:V1.0.1'
	}
##使用方法
1.我们发送消息需要handler  可以通过

	mHander = BaseHandlerOperate.getBaseHandlerOperate();
这个方法获得   
2. 接收消息的类需要实现BaseHandlerUpDate这个接口
这个接口有一个方法 用来接收消息
	
	 @Override
    public void handleMessage(Message msg) {

    }
上面的第一步和第二部你可以在一个具体类中写  假如有父类  你可以在父类中写 这样所有子类都用了  就不需要每个类都写了 例如:

	public class BaseActivity extends AppCompatActivity implements BaseHandlerUpDate{
	
	    protected BaseHandlerOperate mHander;
	    @Override
	    protected void onCreate(@Nullable Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        mHander = BaseHandlerOperate.getBaseHandlerOperate();
	    }
	
	
	    @Override
	    public void handleMessage(Message msg) {
	
	    }
	}

### 开始发消息
在发消息之前 要将自己注册到handler中 这样我才能将消息发送出去
例如: 

	 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHander.addKeyHandler(MainActivity.class, this);
然后发送消息

	  mHander.putMessageKey(SecondActivity.class, 1, "mainActivity发来的消息");
这个方法第一个参数是目标类的class ,  第二个参数是what  用来区分消息  第三个参数就是发送的内容

###接收消息

在接收消息之前 要将自己注册到handler中 这样我才能将消息接收:

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mHander.addKeyHandler(SecondActivity.class, this);
开始接收消息:

	 @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case 1:
                mTextView.setText(((String) msg.obj));
                break;
        }
    }
就是刚刚父类实现的handleMessage  在具体类中复写下就行了

