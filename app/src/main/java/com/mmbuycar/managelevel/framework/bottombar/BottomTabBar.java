package com.mmbuycar.managelevel.framework.bottombar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mmbuycar.managelevel.R;
import com.mmbuycar.managelevel.framework.bottombar.animate.Animatable;
import com.mmbuycar.managelevel.framework.bottombar.animate.FlipAnimater;
import com.mmbuycar.managelevel.framework.bottombar.animate.JumpAnimater;
import com.mmbuycar.managelevel.framework.bottombar.animate.RotateAnimater;
import com.mmbuycar.managelevel.framework.bottombar.animate.ScaleAnimater;
import com.mmbuycar.managelevel.framework.bottombar.anno.NorIcons;
import com.mmbuycar.managelevel.framework.bottombar.anno.SeleIcons;
import com.mmbuycar.managelevel.framework.bottombar.anno.Titles;
import com.mmbuycar.managelevel.utils.DensityUtil;

import java.lang.reflect.Field;

/**
 * @ClassName: JPTabBar
 * @author: 张京伟
 * @date: 2017/1/3 10:29
 * @Description: 主要的底部导航操作类, 控制导航的行为(显示隐藏徽章等等)
 * @version: 1.0
 */
public class BottomTabBar extends LinearLayout implements ViewPager.OnPageChangeListener {

    //ROTATE3D动画
    private static final int FLIP_TYPE = 1;
    //旋转动画
    private static final int ROTATE_TYPE = 2;
    //缩放动画
    private static final int SCALE_TYPE = 3;
    //跳跃动画
    private static final int JUMP_TYPE = 4;
    //默认的图标大小
    private static final int DEFAULT_ICONSIZE = 24;
    //字体默认大小
    private static final int DEFAULT_TEXTSIZE = 14;
    //默认的没有选中的文字和图标的颜色
    private static final int DEFAULT_NORMAL_COLOR = 0xffAEAEAE;
    //默认的上下背景间隔
    private static final int DEFAULT_MARGIN = 8;
    //默认的选中颜色
    private static final int DEFAULT_SELECT_COLOR = 0xff59D9B9;
    //默认是否接受颜色随着字体变化
    private static final boolean DEFAULT_ACEEPTFILTER = true;
    //默认的动画类型是放大
    private static final int DEFAULT_ANIMATE_TYPE = 3;

    //默认的徽章背景颜色
    private static final int DEFAULT_BADGE_COLOR = 0xffff0000;
    //默认的徽章字体大小
    private static final int DEFAULT_BADGE_TEXTSIZE = 10;
    //默认的徽章狂站距离
    private static final int DEFAULT_PADDING = 4;
    //默认徽章距离右边间距
    private static final int DEFAULT_BADGEHORIZONAL_MARGIN = 20;
    //默认徽章距离上面间距
    private static final int DEFAULT_BADGEVERTICAL_MARGIN = 3;
    //默认中间图标底部距离
    private static final int DEFAULT_MIDDLEICONBOTTOM = 20;
    //默认中间的左右间距
    private static final int DEFAULT_MIDDLEMARGIN = 24;

    private Context mContext;

    private int mLimit = 99;


    private TypedArray mAttribute;

    /**
     * 选中的当前Tab的位置
     */
    private int mSelectIndex;

    /**
     * 标题的数组
     */
    private String[] mTitles;

    /**
     * 没有选中的图标数组
     */
    private int[] mNormalIcons;

    /**
     * 选中的图标数组
     */
    private int[] mSelectedIcons;

    /**
     * 动画的实现类
     */
    private Animatable mAnimater;

    /**
     * 所有Tabitem
     */
    private BottomTabItem[] mBottomTabItems;

    /**
     * 中间按钮
     */
    private ImageView mMiddleItem;

    /**
     * 监听点击Tab回调的观察者
     */
    private OnTabSelectListener mTabSelectLis;

    /**
     * 判断是否需要动画,解决Viewpager回调onpageChange冲突事件
     */
    private boolean mNeedAnimate = true;

    /**
     * Tab对应的ViewPager
     */
    private ViewPager mTabPager;

    /**
     * 渐变判断
     */
    private boolean mFilter;

    /**
     * 是否滚动页面的动画
     */
    private boolean mNeedScrollAnimate;


    public BottomTabBar(Context context) {
        super(context);
        init(context, null);
    }

    public BottomTabBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    /**
     * 初始化TabBar
     *
     * @param context 上下文
     * @param set     XML结点集合
     */
    private void init(Context context, AttributeSet set) {

        mContext = context;

        mAttribute = context.obtainStyledAttributes(set, R.styleable.BottomTabBar);

        setMinimumHeight(DensityUtil.dp2px(mContext, 48));
        boolean haveAnno = reflectAnnotation();
        if (haveAnno) {
            initFromAttribute();
        }

    }

    private void setAnimater(int type) {
        if (type == SCALE_TYPE) {
            mAnimater = new ScaleAnimater();
        } else if (type == ROTATE_TYPE) {
            mAnimater = new RotateAnimater();
        } else if (type == FLIP_TYPE) {
            mAnimater = new FlipAnimater();
        } else if (type == JUMP_TYPE) {
            mAnimater = new JumpAnimater();
        }
    }

    /**
     * 从类获取注解,映射值到mTiles,mNormalIcons,mSelectedIcons
     *
     * @return boolean 表示是否有注解的存在
     */
    private boolean reflectAnnotation() {
        int total = 0;//表示获得注解的总数
        //反射注解
        Field[] fields = mContext.getClass().getDeclaredFields();

        //遍历所有字段,寻找标记
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Titles.class)) {
                //标题
                try {
                    if (field.get(mContext).getClass().equals(String[].class)) {
                        mTitles = (String[]) field.get(mContext);
                    } else if (field.get(mContext).getClass().equals(int[].class)) {
                        int[] title_Res = (int[]) field.get(mContext);
                        mTitles = new String[title_Res.length];
                        for (int i = 0; i < title_Res.length; i++) {
                            mTitles[i] = mContext.getString(title_Res[i]);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (mTitles != null) total++;
            } else if (field.isAnnotationPresent(NorIcons.class)) {
                try {
                    mNormalIcons = (int[]) field.get(mContext);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (mNormalIcons != null) total++;
            } else if (field.isAnnotationPresent(SeleIcons.class)) {
                try {
                    mSelectedIcons = (int[]) field.get(mContext);

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (mSelectedIcons != null) total++;
            }

        }
        return total > 0;
    }

    Animatable getAnimater() {
        return mAnimater;
    }

    /**
     * 获取所有节点属性
     */
    private void initFromAttribute() {

        int normalColor = mAttribute.getColor(R.styleable.BottomTabBar_TabNormalColor, DEFAULT_NORMAL_COLOR);
        int selectColor = mAttribute.getColor(R.styleable.BottomTabBar_TabSelectColor, DEFAULT_SELECT_COLOR);
        int textSize = DensityUtil.px2sp(mContext, mAttribute.getDimensionPixelSize(R.styleable.BottomTabBar_TabTextSize, DensityUtil.sp2px(mContext, DEFAULT_TEXTSIZE)));
        //这里
        int iconSize = mAttribute.getDimensionPixelSize(R.styleable.BottomTabBar_TabIconSize, DensityUtil.dp2px(mContext, DEFAULT_ICONSIZE));
        int margin = mAttribute.getDimensionPixelOffset(R.styleable.BottomTabBar_TabMargin, DensityUtil.dp2px(mContext, DEFAULT_MARGIN));
        int AnimateType = mAttribute.getInt(R.styleable.BottomTabBar_TabAnimate, DEFAULT_ANIMATE_TYPE);
        int BadgeColor = mAttribute.getColor(R.styleable.BottomTabBar_BadgeColor, DEFAULT_BADGE_COLOR);
        int BadgeTextSize = DensityUtil.px2sp(mContext, mAttribute.getDimensionPixelSize(R.styleable.BottomTabBar_BadgeTextSize, DensityUtil.sp2px(mContext, DEFAULT_BADGE_TEXTSIZE)));
        int badgePadding = DensityUtil.px2dp(mContext, mAttribute.getDimensionPixelOffset(R.styleable.BottomTabBar_BadgePadding, DensityUtil.dp2px(mContext, DEFAULT_PADDING)));
        int badgeVerMargin = DensityUtil.px2dp(mContext, mAttribute.getDimensionPixelOffset(R.styleable.BottomTabBar_BadgeVerticalMargin, DensityUtil.dp2px(mContext, DEFAULT_BADGEVERTICAL_MARGIN)));
        int badgeHorMargin = DensityUtil.px2dp(mContext, mAttribute.getDimensionPixelOffset(R.styleable.BottomTabBar_BadgeHorizontalMargin, DensityUtil.dp2px(mContext, DEFAULT_BADGEHORIZONAL_MARGIN)));
        boolean acceptFilter = mAttribute.getBoolean(R.styleable.BottomTabBar_TabIconFilter, DEFAULT_ACEEPTFILTER);
        Drawable tabselectbg = mAttribute.getDrawable(R.styleable.BottomTabBar_TabSelectBg);

        setAnimater(AnimateType);

        //假如所有都为空默认已经开启了
        if (!isInEditMode()) {
            CheckIfAssertError(mTitles, mNormalIcons, mSelectedIcons);

            //计算Tab的宽度
            mBottomTabItems = new BottomTabItem[mNormalIcons.length];
            //实例化TabItem添加进去
            for (int i = 0; i < mBottomTabItems.length; i++) {
                final int temp = i;

                mBottomTabItems[i] = new BottomTabItem.Builder(mContext).setTitle(mTitles == null ? null : mTitles[i]).setIndex(temp).setTextSize(textSize)
                        .setNormalColor(normalColor).setSelectBg(tabselectbg).setBadgeColor(BadgeColor)
                        .setBadgeTextSize(BadgeTextSize).setNormalIcon(mNormalIcons[i])
                        .setSelectedColor(selectColor).setBadgeHorMargin(badgeHorMargin)
                        .setBadgePadding(badgePadding).setIconSize(iconSize).setIconFilte(acceptFilter)
                        .setBadgeVerMargin(badgeVerMargin).setMargin(margin)
                        .setSelectIcon(mSelectedIcons == null ? 0 : mSelectedIcons[i]).build();
                mBottomTabItems[i].setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTabPager != null && mTabPager.getAdapter() != null && mTabPager.getAdapter().getCount() >= mBottomTabItems.length) {
                            mNeedAnimate = true;
                            mTabPager.setCurrentItem(temp, false);
                        } else if (mTabPager != null && mTabPager.getAdapter() != null && mTabPager.getAdapter().getCount() <= mBottomTabItems.length) {
                            mNeedAnimate = true;
                            mTabPager.setCurrentItem(temp, false);
                            setSelectTab(temp);
                        } else {
                            setSelectTab(temp, true);
                        }

                    }
                });
                addView(mBottomTabItems[i]);

                //判断是不是准备到中间的tab,假如设置了中间图标就添加进去
                if (i == (mBottomTabItems.length / 2 - 1)) {
                    mMiddleItem = BuildMiddleBtn();
                }
            }

            mBottomTabItems[0].setSelect(mAnimater, true, true, false);
            for (int i = 1; i < mBottomTabItems.length; i++) {
                mBottomTabItems[i].setSelect(mAnimater, false, false);
            }
            mAttribute.recycle();
        }
    }

    /**
     * 切换Tab页面,是否带动画
     */
    private void setSelectTab(int index, boolean animated) {
        if (mBottomTabItems == null || index > mBottomTabItems.length - 1) return;
        mSelectIndex = index;
        //把全部tab selected设置为false
        for (int i = 0; i < mBottomTabItems.length; i++) {
            if (i == index) {
                continue;
            }
            if (!mBottomTabItems[i].isSelect()) {
                mBottomTabItems[i].setSelect(mAnimater, false, animated);
            } else {
                mBottomTabItems[i].setSelect(mAnimater, false, animated);
            }

        }

        mBottomTabItems[index].setSelect(mAnimater, true, animated);

        if (mTabSelectLis != null) {
            mTabSelectLis.onTabSelect(index);
        }
    }

    /**
     * 判断有没有声明变量的错误
     *
     * @param titles       标题
     * @param normalIcon   没有选中的图标
     * @param selectedIcon 选中的图标
     */
    private void CheckIfAssertError(String[] titles, int[] normalIcon, int[] selectedIcon) {
        if (normalIcon == null) {
            throw new TabException("you must set the NormalIcon for the JPTabbar!!!");
        }
        int originlen = normalIcon.length;
        //判断注解里面的数组长度是否一样
        if ((mTitles != null && originlen != titles.length)
                || (selectedIcon != null && originlen != selectedIcon.length)) {
            throw new TabException("Every Array length is not equal,Please Check Your Annotation in your Activity,Ensure Every Array length is equals!");
        }
    }

    private ImageView BuildMiddleBtn() {
        int icon_res = mAttribute.getResourceId(R.styleable.BottomTabBar_TabMiddleIcon, 0);
        int bottom_dis = mAttribute.getDimensionPixelSize(R.styleable.BottomTabBar_TabMiddleBottomDis, DensityUtil.dp2px(mContext, DEFAULT_MIDDLEICONBOTTOM));
        int margin = mAttribute.getDimensionPixelOffset(R.styleable.BottomTabBar_TabMiddleHMargin, DensityUtil.dp2px(mContext, DEFAULT_MIDDLEMARGIN));
        if (icon_res == 0) return null;
        ImageView middleBtn = new ImageView(mContext);
        Drawable icon = mContext.getResources().getDrawable(icon_res);
        int width = icon.getIntrinsicWidth();
        int height = icon.getIntrinsicHeight();

        middleBtn.setScaleType(ImageView.ScaleType.FIT_XY);
        middleBtn.setImageDrawable(icon);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            middleBtn.setElevation(30);
        }
        middleBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTabSelectLis != null)
                    mTabSelectLis.onClickMiddle(mMiddleItem);
            }
        });
        if (getParent().getClass().equals(RelativeLayout.class)) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(width, height);
            params.setMargins(0, 0, 0, bottom_dis);
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            middleBtn.setLayoutParams(params);
        } else if (getParent().getClass().equals(FrameLayout.class)) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
            params.setMargins(0, 0, 0, bottom_dis);
            params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
            middleBtn.setLayoutParams(params);
        }
        ((ViewGroup) getParent()).addView(middleBtn);

        //添加中间的占位距离控件
        View stement_view = new View(mContext);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(margin, ViewGroup.LayoutParams.MATCH_PARENT);
        stement_view.setLayoutParams(params);
        addView(stement_view);
        return middleBtn;
    }


    /****-------提供给开发者调用的方法---------****/


    /**
     * 切换Tab页面
     */
    public void setSelectTab(int index) {
        setSelectTab(index, true);
    }

    /**
     * 得到TabItem的数量
     */
    public int getTabsCount() {
        return mBottomTabItems == null ? 0 : mBottomTabItems.length;
    }

    /**
     * 设置容器和TabBar联系在一起
     */
    public void setContainer(ViewPager pager) {
        if (pager != null) {
            mTabPager = pager;
            mTabPager.setOnPageChangeListener(this);
        }
    }

    /**
     * 设置Badge消息数量最大限制
     */
    public void setCountLimit(int limit) {
        mLimit = limit;

    }

    /**
     * 设置图标和标题的滑动渐变以及点击渐变是否使用
     */
    public BottomTabBar setUseFilter(boolean filter) {
        this.mFilter = filter;
        return this;
    }

    /**
     * 设置是否需要页面滚动动画
     */
    public BottomTabBar setUseScrollAnimate(boolean scrollAnimate) {
        this.mNeedScrollAnimate = scrollAnimate;
        return this;
    }

    /**
     * 显示图标的Badge，默认不可拖动
     */
    public void showBadge(int pos, String text) {
        showBadge(pos, text, false);
    }

    /**
     * 显示图标的Badge
     */
    public void showBadge(int pos, String text, boolean draggable) {
        if (mBottomTabItems != null) {
            mBottomTabItems[pos].showTextBadge(text);
            mBottomTabItems[pos].getBadgeViewHelper().setDragable(draggable);
        }
    }

    /**
     * 显示圆点徽章,默认为不可拖动
     */
    public void showCircleBadge(int pos) {
        showCircleBadge(pos, false);
    }

    /**
     * 显示圆点徽章,是否可以拖动
     */
    public void showCircleBadge(int pos, boolean draggable) {
        if (mBottomTabItems != null) {
            mBottomTabItems[pos].showCirclePointBadge();
            mBottomTabItems[pos].getBadgeViewHelper().setDragable(draggable);
        }
    }

    /**
     * 重载方法
     * 设置徽章,传入int,,默认为不可拖动
     */
    public void showBadge(int pos, int count) {
        showBadge(pos, count, false);
    }

    /**
     * 重载方法
     * 设置徽章,传入int,是否可拖动
     */
    public void showBadge(int pos, int count, boolean draggable) {
        if (mBottomTabItems[pos] == null) return;
        mBottomTabItems[pos].getBadgeViewHelper().setDragable(draggable);
        if (count == 0) {
            mBottomTabItems[pos].hiddenBadge();

        } else if (count > mLimit) {
            mBottomTabItems[pos].showTextBadge(mLimit + "+");
        } else {
            mBottomTabItems[pos].showTextBadge(count + "");
        }
    }


    /**
     * 隐藏徽章
     *
     * @param position
     */
    public void hideBadge(int position) {
        if (mBottomTabItems != null)
            mBottomTabItems[position].hiddenBadge();
    }

    /**
     * 设置标题数组
     *
     * @param titles
     */
    public BottomTabBar setTitles(String... titles) {
        this.mTitles = titles;
        return this;
    }

    public BottomTabBar setTitles(int... titles) {
        if (titles != null && titles.length > 0) {
            mTitles = new String[titles.length];

            for (int i = 0; i < titles.length; i++) {

                mTitles[i] = mContext.getString(titles[i]);
            }
        }
        return this;
    }

    /**
     * 设置为选中的图标数组
     */
    public BottomTabBar setNormalIcons(int... normalIcons) {
        this.mNormalIcons = normalIcons;
        return this;
    }

    /**
     * 设置选中图标
     */
    public BottomTabBar setSelectedIcons(int... selectedIcons) {
        this.mSelectedIcons = selectedIcons;
        return this;
    }

    /**
     * 生成TabItem
     */
    public void generate() {
        int[] pos = new int[2];
        getLocationOnScreen(pos);
        if (mBottomTabItems == null)
            initFromAttribute();
    }


    /**
     * 获得选中的位置
     */
    public int getSelectPosition() {

        return mSelectIndex;
    }

    /**
     * 获取徽章是否在显示
     */
    public boolean isBadgeShow(int index) {
        if (mBottomTabItems != null)
            return mBottomTabItems[index].isBadgeShow();

        return false;
    }

    /**
     * 获得中间的TABItem
     *
     * @return
     */
    public ImageView getMiddleBtn() {
        return mMiddleItem;
    }


    /**
     * 设置自定义动画
     */
    public void setCustomAnimate(@NonNull Animatable customAnimate) {
        this.mAnimater = customAnimate;
    }

    /**
     * 设置点击TabBar事件的观察者
     */
    public void setTabListener(OnTabSelectListener listener) {
        mTabSelectLis = listener;
    }

    /**
     * 设置badgeView消失的回调事件
     */
    public void setDismissListener(BadgeDismissListener listener) {
        for (BottomTabItem item : mBottomTabItems) {
            item.setDismissDelegate(listener);
        }
    }

    /****
     * ---------------
     ****/

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mBottomTabItems == null || position > mBottomTabItems.length - 1 || 1 + position > mBottomTabItems.length - 1)
            return;
        if (positionOffset > 0f) {
            if (mFilter) {
                mBottomTabItems[position].changeAlpha(1 - positionOffset);
                mBottomTabItems[position + 1].changeAlpha(positionOffset);
            }

            if (mAnimater != null && mNeedScrollAnimate) {
                if (mAnimater.isNeedPageAnimate()) {
                    mNeedAnimate = false;
                    mAnimater.onPageAnimate(mBottomTabItems[position].getIconView(), 1 - positionOffset);
                    mAnimater.onPageAnimate(mBottomTabItems[position + 1].getIconView(), positionOffset);
                } else {
                    mNeedAnimate = true;
                }
            } else mNeedAnimate = true;
        }
    }

    @Override
    public void onPageSelected(int position) {
        setSelectTab(position, mNeedAnimate);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == 1) {
            mNeedAnimate = false;
        }
    }


}

