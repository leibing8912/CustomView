package cn.jianke.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

/**
 * @className: CustomView
 * @classDescription: 自定义View（测试属性）
 * @author: leibing
 * @createTime: 2016/09/19
 */
public class CustomView extends View{
    private int defaultSize = 100;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //第二个参数就是我们在styles.xml文件中的<declare-styleable>标签
        //即属性集合的标签，在R文件中名称为R.styleable+name
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomView);

        //第一个参数为属性集合里面的属性，R文件名称：R.styleable+属性集合名称+下划线+属性名称
        //第二个参数为，如果没有设置这个属性，则设置的默认的值
        defaultSize = a.getDimensionPixelSize(R.styleable.CustomView_default_size, 100);

        //最后记得将TypedArray对象回收
        a.recycle();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取合适的宽度值
        int width = getProperSize(defaultSize, widthMeasureSpec);
        // 获取合适的高度值
        int height = getProperSize(defaultSize, heightMeasureSpec);
        // 设置宽高尺寸大小值，此方法决定view最终的尺寸大小
        setMeasuredDimension(width, height);
    }

    /**
     * 获取合适的大小
     * @author leibing
     * @createTime 2016/09/19
     * @lastModify 2016/09/19
     * @param defaultSize 默认大小
     * @param measureSpec 测量规格
     * @return
     */
    private int getProperSize(int defaultSize, int measureSpec){
        int properSize = defaultSize;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        switch (mode){
            case MeasureSpec.UNSPECIFIED:
                // 没有指定大小，设置为默认大小
                properSize = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                // 固定大小，无需更改其大小
                properSize = size;
                break;
            case MeasureSpec.AT_MOST:
                // 此处该值可以取小于等于最大值的任意值，此处取最大值的1/4
                properSize = size / 4;
        }

        return properSize;
    }
}
