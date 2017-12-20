## Android转场动画一说

**所谓转场动画，通俗的讲就是一个Activity跳转到另一个Activity是的动画。**

***Activity的转场动画很早就有了，5.0之前用的是overridePendingTransition（）这个方法。在5.0之后，Google使用Material Design设计风格，进而有了的新的转场转场动画的诞生，效果还是挺炫酷的，下面我们先看下效果。***

### 5.0之前的效果
<br>
<center>
<img src="http://o9o9d242i.bkt.clouddn.com/transition_before.gif" width="280" height="480" />
</center>

### 使用方法：
#### 在startActivity后加入以下代码

	startActivity(Intent(this@BeforeActivity,BeforeTwoActivity::class.java)
	//Fade(淡入淡出)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    //Slide(左右交错)
    //overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

#### 然后在finish后加入以下代码

	finish()
	//Fade(淡入淡出)
    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    //Slide(左右交错)
    //overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right)

其实这里主要是靠overridePendingTransition(int enterAnim, int exitAnim)来加载动画，顾名思义第一个参数是进场动画，第二个是出场动画。
这几个效果是可以交互使用，比如进场用Fade效果，出场用SLide效果。也可以使用自定义的效果，这里不细说。当然5.0后了类似共享元素这类的效果那就另当别论了


### 5.0之后的效果(过度效果和共享元素)
<br>
<center>
<img src="http://o9o9d242i.bkt.clouddn.com/transition_after.gif" width="280" height="480" />
<img src="http://o9o9d242i.bkt.clouddn.com/share_element.gif" width="280" height="480" />
</center>

说到这里不得不说Google在5.0MD设计中给我提供全新的过度动画ActivityOptions，以及兼容包ActivityOptionsCompat.下面来说一说它提供几种过度效果的方法

### 第一种
#### ActivityOptionsCompat makeCustomAnimation(Context context,int enterResId, int exitResId)。

#### 这个方法其实和上面的overridePendingTransition()方法使用其实差不多，第二个参数是进场动画，第三个是出场动画。(效果参考overridePendingTransition())  使用如下：

#### startActivity的时候:

	ActivityCompat.startActivity(Intent(this@BeforeActivity,BeforeTwoActivity::class.java),
                    ActivityOptionsCompat.makeCustomAnimation(this@BeforeActivity,
                    android.R.anim.fade_in, android.R.anim.fade_out).toBundle())

####finish的时候:

	ActivityCompat.finishAfterTransition(this)

#### 但是这个方法调用后，会发现并没有什么软用，没有退出的动画。打断点调试后发现：

	public boolean startExitBackTransition(final Activity activity) {
        if (mEnteringNames == null || mCalledExitCoordinator != null) {
            return false;
        } else {
            ...
        }
    }

#### 这个mEnteringNames一直是null，然后这个变量跟共享元素有关：

	/**
     * The shared elements that the calling Activity has said that they transferred to this
     * Activity.
     * 调用Activity的共享元素表示已转移到此Activity（请不要介意这个机翻，凑和看）
     */
    private ArrayList<String> mEnteringNames;

#### 因为这个两个页面之间涉及到共享元素，这里没有使用到，所以要想这个有出场动画，还是调用overridePendingTransition()来显示(有更好的方法请告知，万分感谢）

### 第二种
#### ActivityOptionsCompat makeScaleUpAnimation(View source,int startX, int startY, int startWidth, int startHeight)

#### 这个效果展示的某个小的区域放大至全屏显示,效果如下:

#### 这个方法第一个参数是目标view(也就是想要放大的view)，第二、三个参数是起始坐标，第四,五个参数是过度效果开始的宽高度 使用如下：

#### startActivity的时候:

	val options = ActivityOptionsCompat.makeScaleUpAnimation(view,view.width/2,view.height/2,
	0, 0)
	ActivityCompat.startActivity(this@AfterActivity,Intent(this@AfterActivity,AfterTwoActivity::class.java),options.toBundle())

#### finish的时候（这个没什么回退效果,暂时没找到解决方法，有更好的方法请告知，万分感谢）:

	ActivityCompat.finishAfterTransition(this)

### 第二种
#### ActivityOptionsCompat makeScaleUpAnimation(View source,int startX, int startY, int startWidth, int startHeight)

#### 这个效果展示的某个小的区域放大至全屏显示,效果如下:

<br>
<center>
<img src="http://o9o9d242i.bkt.clouddn.com/makeScaleUp.gif" width="280" height="480" />
</center>

#### 这个方法第一个参数是目标view(也就是想要放大的view)，第二、三个参数是起始坐标，第四,五个参数是过度效果开始的宽高度 使用如下：

#### startActivity的时候:

	val options = ActivityOptionsCompat.makeScaleUpAnimation(view,view.width/2,view.height/2,
	0, 0)
	ActivityCompat.startActivity(this@AfterActivity,Intent(this@AfterActivity,AfterTwoActivity::class.java),options.toBundle())

#### finish的时候（这个没什么回退效果,暂时没找到解决方法，有更好的方法请告知，万分感谢）:

	ActivityCompat.finishAfterTransition(this)

### 第三种
#### ActivityOptionsCompat makeThumbnailScaleUpAnimation(View source,Bitmap thumbnail, int startX, int startY)

#### 这个效果展示的一块的Bitmpat进行拉伸的动画,效果如下:

<br>
<center>
<img src="http://o9o9d242i.bkt.clouddn.com/makeThumbnailScaleUp.gif" width="280" height="480" />
</center>

#### 这个方法第一个参数是目标view(也就是想要放大的view)，第二参数是需要放大的图片，第四,五个参数是起始坐标 使用如下：

#### startActivity的时候:

	var bitmap = BitmapFactory.decodeResource(resources,effect.uri)
    val options = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, bitmap,
                                view.width/2, view.height/2)
	ActivityCompat.startActivity(this@AfterActivity,Intent(this@AfterActivity,AfterTwoActivity::class.java),options.toBundle())

#### finish的时候（这个没什么回退效果,暂时没找到解决方法，有更好的方法请告知，万分感谢）:

	ActivityCompat.finishAfterTransition(this)

### 第四种
#### ActivityOptionsCompat makeClipRevealAnimation(View source,int startX, int startY, int width, int height)

#### 这个效果展示的从一个点以圆形渐变到满屏,效果如下:

<br>
<center>
<img src="http://o9o9d242i.bkt.clouddn.com/makeClipReveal.gif" width="280" height="480" />
</center>

#### 这个方法第一个参数是目标view(也就是想要放大的view)，第二、三个参数是起始坐标，第四,五个参数是过度效果开始的宽高度 使用如下：

#### startActivity的时候:

	val options = ActivityOptionsCompat.makeClipRevealAnimation(view,view.width/2,
                                view.height/2,0, 0)
	ActivityCompat.startActivity(this@AfterActivity,Intent(this@AfterActivity,AfterTwoActivity::class.java),options.toBundle())

#### finish的时候（这个没什么回退效果,暂时没找到解决方法，有更好的方法请告知，万分感谢）:

	ActivityCompat.finishAfterTransition(this)

### 第五种
####ActivityOptions CompatmakeSceneTransitionAnimation(Activity activity,Pair<View, String>... sharedElements)

#### 这个展示的多种效果,效果如下:

<br>
<center>
<img src="http://o9o9d242i.bkt.clouddn.com/transition_after.gif" width="280" height="480" />
</center>

#### 这个方法第一个参数是目标view(也就是想要放大的view)，第二个参数是共享元素需要的(这里的效果不涉及) 使用如下：

#### startActivity的时候:

	startActivity(Intent(this@AfterActivity, AfterTwoActivity::class.java),
		ActivityOptionsCompat.makeSceneTransitionAnimation(this@AfterActivity).toBundle())

#### 然后在跳转后的页面设置效果(这里是AfterTwoActivity):

	//Explode
	window.enterTransition = Explode()
    window.exitTransition = Explode()
    //Slide
	window.enterTransition = Slide()
    window.exitTransition = Slide()
    //Fade
	window.enterTransition = Fade()
    window.exitTransition = Fade()

### 共享元素
#### 所谓的共享元素指的是Activity A中一个View和Activity B中的一个View做一个平滑过渡的效果。

#### 效果展示(类似微信朋友圈的图片放大效果)	:

<br>
<center>
<img src="http://o9o9d242i.bkt.clouddn.com/share_element.gif" width="280" height="480" />
</center>

#### 来看一下如何来让两个页面之间的View做一个过渡:

* 1.在A和B的布局中为需要进行过渡效果的View设置两个相同的 *android:transitionName = “标识名称”*

* 2.1 在startActivity的时候(适用单个view过渡):

		//第二参数传入过渡的view，第三个参数传入 android:transitionName 标识名称
		startActivity(Intent(this@ShareElementActivity, ShareElementTwoActivity::class.java),
		ActivityOptions.makeSceneTransitionAnimation(this@ShareElementActivity,view,"shareImg").toBundle())

* 2.2 在startActivity的时候(适用多个个view过渡):

		//其实就是把过个需要过渡的View集合起来
		var one = android.support.v4.util.Pair<View, String>(img5, "shareImg5")

        var two = android.support.v4.util.Pair<View, String>(img6, "shareImg6")

        var pairs = arrayOf(one,two)

        val transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, *pairs)
            startActivity(Intent(this@ShareElementActivity, ShareElementThreeActivity::class.java),
                    transitionActivityOptions.toBundle())



#### 以上就是要说的转场动画，如若有不恰当的地方，欢迎指正

#### 这是文中涉及的代码[Demo地址](https://github.com/HellForGate/TransitionDemo)

