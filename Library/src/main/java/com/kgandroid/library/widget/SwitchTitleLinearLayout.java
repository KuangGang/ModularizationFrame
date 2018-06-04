package com.kgandroid.library.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.uxin.chake.library.R;
import com.uxin.chake.library.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Description:
 * @author 冯亚柔 fengyarou@xin.com
 *
 *
 * 使用方式：
 * 1.在xml中定义，然后findView
 * 2.SwitchTitleLinearLayout.initData(names);//names是个String[],对应显示几个Item
 * 3.SwitchTitleLinearLayout.setOnTitleSelectListener(this);//监听选择的是第几个Item
 * ViewPage 中调用
 * 4.SwitchTitleLinearLayout.changeTitle(position);//改变当前选中的item
 * 5.SwitchTitleLinearLayout.scrollLine(position, offset);
 */
public class SwitchTitleLinearLayout extends LinearLayout implements OnClickListener {
	Context context;
	LinearLayout mLlgroup;
	View mTvLine;
	List<TextView> list_title;
	private String[] mNames;
	int colorSelected, colorB;
	int screenW;
	LayoutParams mlParams;
	private int mBottomLineWidth;
	int mScreenWidth;
	int mOffset;//滑动模块边距
	private int tabNum;//页签数
	int currIndex = 0;// 当前页卡编号
	OnTitleSelectListener selectListener;
	public SwitchTitleLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SwitchTitleLinearLayout(Context context) {
		super(context);
		initView(context);
	}
	private void initView(Context context){
		this.context = context;
		colorSelected = context.getResources().getColor(R.color.title_bg);
		colorB = context.getResources().getColor(R.color.second_title_color);
		LayoutInflater inflater = LayoutInflater.from(context);
		View vBoot = inflater.inflate(R.layout.ui_switch_title, this, true);
		mLlgroup = (LinearLayout) vBoot.findViewById(R.id.lin_lst_title);
		mTvLine = vBoot.findViewById(R.id.uitv_lst_line);
	}
//	//设置背景色
//	public void setLinBackGround(){
//		lin_group.setBackgroundResource(R.color.white);
//	}

	public void updateTitles(String[] names){
		this.mNames = names;
		for (int i = 0; i < list_title.size(); i++){
			list_title.get(i).setText(mNames[i]);
		}
	}
	/**
	 * 初始化标签
	 * @param names
	 */
	public void initData(String[] names){
		this.mNames = names;
		tabNum = names.length;
		mBottomLineWidth = getResources().getDimensionPixelOffset(R.dimen.switch_bottom_line_width);
		DisplayMetrics dpMetrics = new DisplayMetrics();
		((Activity) context).getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		mScreenWidth = dpMetrics.widthPixels;
		mOffset = (mScreenWidth / tabNum - mBottomLineWidth) / 2;
		int height = DensityUtil.dip2px(context, 2);
		mlParams = new LayoutParams(mBottomLineWidth, height);
		mlParams.setMargins(mOffset, 0, 0, 0);
		mTvLine.setLayoutParams(mlParams);
		LayoutInflater lInflater = LayoutInflater.from(context);
		LayoutParams lin_a = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		lin_a.weight = 1;
		list_title = new ArrayList<TextView>();
		mLlgroup.removeAllViews();
		list_title.clear();
		for(int i=0;i<tabNum;i++){
			TextView tv_item = (TextView) lInflater.inflate(R.layout.ui_switch_item, mLlgroup, false);
			tv_item.setLayoutParams(lin_a);
			tv_item.setText(names[i]);
			tv_item.setId(i);
			tv_item.setOnClickListener(this);
			tv_item.setTextColor(colorB);
			mLlgroup.addView(tv_item);
			list_title.add(tv_item);
		}
		list_title.get(0).setTextColor(colorSelected);
	}
	
	public void changeTitle(int posi){
		list_title.get(posi).setTextColor(colorSelected);
		list_title.get(currIndex).setTextColor(colorB);
		currIndex = posi;
	}

	@Override
	public void onClick(View view) {
		int arg0 = view.getId();
		if(selectListener!=null)
			selectListener.setSelectedId(arg0);
	}

	/**
	 * 滚动线条
	 * @param position
	 * @param offset
     */
	public void scrollLine(final int position, final float offset){
		post(new Runnable() {
			@Override
			public void run() {
				if (currIndex == position)
				{
					mlParams.leftMargin = (int) (offset * (mScreenWidth * 1.0 / tabNum) + currIndex
							* (mScreenWidth / tabNum)) + mOffset;
					Log.e("scroll", "leftmargin="+mlParams.leftMargin);

				} else if(currIndex > position && (currIndex - position) == 1) // 1->0
				{
					mlParams.leftMargin = (int) (-(1 - offset)
							* (mScreenWidth * 1.0 / tabNum) + currIndex
							* (mScreenWidth / tabNum)) + mOffset;
					Log.e("scroll", "leftmargin="+mlParams.leftMargin);

				}else{
					Log.e("scroll", "======");
				}
				mTvLine.setLayoutParams(mlParams);
			}
		});

	}
	public void setOnTitleSelectListener(OnTitleSelectListener selectListener){
		this.selectListener = selectListener;
	}
	public interface OnTitleSelectListener{
		void setSelectedId(int id);
	}
}
