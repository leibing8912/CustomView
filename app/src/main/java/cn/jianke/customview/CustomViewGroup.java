package cn.jianke.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @className: CustomViewGroup
 * @classDescription: 自定义ViewGroup(测试属性)
 * @author: leibing
 * @createTime: 2016/09/16
 */
public class CustomViewGroup extends ViewGroup{

    public CustomViewGroup(Context context) {
        super(context);
    }

    public CustomViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 对所有子view进行测量，触发所有子view的onMeasure函数
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        // 宽度模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 测量宽度
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        // 高度模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        // 测量高度
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // 子view数目
        int childCount = getChildCount();
        if (childCount == 0){
            // 如果当前ViewGroup没有子View，就没有存在的意义，无需占空间
            setMeasuredDimension(0, 0);
        }else {
            // 如果宽高都是包裹内容
            if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST){
                // 宽度为所有子view宽度相加，高度取子view最大高度
                int width = getTotalWidth();
                int height = getMaxHeight();
                setMeasuredDimension(width, height);
            }else if (widthMode == MeasureSpec.AT_MOST){
                // 宽度为所有子View宽度相加，高度为测量高度
                setMeasuredDimension(getTotalWidth(), heightSize);
            }else if (heightMode == MeasureSpec.AT_MOST){
                // 宽度为测量宽度，高度为子view最大高度
                setMeasuredDimension(widthSize, getMaxHeight());
            }
        }
    }

    /**
     * 获取子view最大高度
     * @author leibing
     * @createTime 2016/09/19
     * @lastModify 2016/09/19
     * @param
     * @return
     */
    private int getMaxHeight() {
        // 最大高度
        int maxHeight = 0;
        // 子view数目
        int childCount = getChildCount();
        // 遍历子view拿取最大高度
        for (int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            if (childView.getMeasuredHeight() > maxHeight)
                maxHeight = childView.getMeasuredHeight();
        }

        return maxHeight;
    }

    /**
     * 所有子view宽度相加
     * @author leibing
     * @createTime 2016/09/19
     * @lastModify 2016/09/19
     * @param
     * @return
     */
    private int getTotalWidth() {
        // 所有子view宽度之和
        int totalWidth = 0;
        // 子View数目
        int childCount  = getChildCount();
        // 遍历所有子view拿取所有子view宽度之和
        for (int i=0;i<childCount;i++){
            View childView = getChildAt(i);
            totalWidth += childView.getMeasuredWidth();
        }
        return totalWidth;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // 子view数目
        int childCount = getChildCount();
        // 记录当前宽度位置
        int currentWidth = l;
        // 逐个摆放子view
        for (int i = 0;i<childCount;i++){
            View childView = getChildAt(i);
            int height = childView.getMeasuredHeight();
            int width = childView.getMeasuredWidth();
            // 摆放子view,参数分别是子view矩形区域的左、上，右，下。
            childView.layout(currentWidth, t, currentWidth + width, t + height);
            currentWidth += width;
        }
    }
}
