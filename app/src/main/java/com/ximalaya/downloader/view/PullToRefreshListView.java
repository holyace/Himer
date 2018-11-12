package com.ximalaya.downloader.view;

import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ximalaya.downloader.R;

import java.util.Date;


public class PullToRefreshListView extends ListView implements OnScrollListener {

	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;

	// 实际的padding的距离与界面上偏移距离的比例
	private final static int RATIO = 3;

	private LayoutInflater inflater;

	private LinearLayout headView;
	/**
	 * 下拉刷新
	 */
	private TextView tipsTextview;
	/**
	 * 上次更新时间
	 */
	private TextView lastUpdatedTextView;
	/**
	 * 箭头图片
	 */
	private ImageView arrowImageView;
	/**
	 * 松手刷新时的进度
	 */
	private ProgressBar progressBar;

	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// 用于保证startY的值在一个完整的touch事件中只被记录一次
	private boolean isRecored;

	private int headContentHeight;

	private int startY;
	private int firstItemIndex;

	private int state;

	private boolean isBack;

	private OnRefreshListener refreshListener;

	private boolean isRefreshable;

	private OnStopScrollListner onStopScroll;
	private OnScrollListner onStopScroll2;
	private int mCurrentScrollState;

	public PullToRefreshListView(Context context) {
		super(context);
		init(context);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		setCacheColorHint(context.getResources().getColor(android.R.color.transparent));
		inflater = LayoutInflater.from(context);

		headView = (LinearLayout) inflater
				.inflate(R.layout.listview_head, null);
		arrowImageView = (ImageView)headView
				.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView
				.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView
				.findViewById(R.id.head_lastUpdatedTextView);

		// 计算headView的宽高,便于后面getMeasuredHeight,getMeasuredWidth
		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		// headView.invalidate();

		addHeaderView(headView, null, false);
		setOnScrollListener(this);

		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		isRefreshable = false;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		firstItemIndex = firstVisibleItem;

		if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& state != REFRESHING) {
			if (getLastVisiblePosition() == getCount() - 1) {
				onStopScroll(view);
			}
		} else if (mCurrentScrollState == SCROLL_STATE_FLING
				&& ((firstVisibleItem + visibleItemCount - 1) == (totalItemCount - 1))) {
			onStopScroll(view);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;

		if (null != onStopScroll2) {
			onStopScroll2.onMyScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		if (isRefreshable) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (firstItemIndex == 0 && !isRecored) {
					isRecored = true;
					startY = (int) event.getY();
				}
				break;
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:

				if (state != REFRESHING && state != LOADING) {
					if (state == DONE) {
						// 什么都不做
					}
					if (state == PULL_To_REFRESH) {
						state = DONE;
						changeHeaderViewByState();

					}
					if (state == RELEASE_To_REFRESH) {
						state = REFRESHING;
						changeHeaderViewByState();
						onRefresh();

					}
				}

				isRecored = false;
				isBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();

				if (!isRecored && firstItemIndex == 0) {
					isRecored = true;
					startY = tempY;
				}

				if (state != REFRESHING && isRecored && state != LOADING) {

					// 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

					// 可以松手去刷新了
					if (state == RELEASE_To_REFRESH) {

						setSelection(0);

						// 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
						if (((tempY - startY) / RATIO < headContentHeight)
								&& (tempY - startY) > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();

						}
						// 一下子推到顶了
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

						}
						// 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
						else {
							// 不用进行特别的操作，只用更新paddingTop的值就行了
						}
					}
					// 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
					if (state == PULL_To_REFRESH) {

						setSelection(0);

						// 下拉到可以进入RELEASE_TO_REFRESH的状态
						if ((tempY - startY) / RATIO >= headContentHeight) {
							state = RELEASE_To_REFRESH;
							isBack = true;
							changeHeaderViewByState();

						}
						// 上推到顶了
						else if (tempY - startY <= 0) {
							state = DONE;
							changeHeaderViewByState();

						}
					}

					// done状态下
					if (state == DONE) {
						if (tempY - startY > 0) {
							state = PULL_To_REFRESH;
							changeHeaderViewByState();
						}
					}

					// 更新headView的size
					if (state == PULL_To_REFRESH) {
						headView.setPadding(0, -1 * headContentHeight
								+ (tempY - startY) / RATIO, 0, 0);

					}

					// 更新headView的paddingTop
					if (state == RELEASE_To_REFRESH) {
						headView.setPadding(0, (tempY - startY) / RATIO
								- headContentHeight, 0, 0);
					}

				}

				break;
			}
		}

		return super.onTouchEvent(event);
	}

	// 当状态改变时候，调用该方法，以更新界面
	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText("松开刷新");

			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);
			// 是由RELEASE_To_REFRESH状态转变来的
			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText("下拉刷新");
			} else {
				tipsTextview.setText("下拉刷新");
			}
			break;

		case REFRESHING:
			smoothPaddingTo(0);
			break;
		case DONE:
			smoothPaddingTo(-1 * headContentHeight);
			break;
		}
	}

	private void changeViewState() {
		switch (state) {
		case REFRESHING:
			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("正在刷新...");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			break;
		case DONE:

			progressBar.setVisibility(View.GONE);
			arrowImageView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			tipsTextview.setText("下拉刷新");
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			break;

		}
	}

	private void smoothPaddingTo(final int padding) {
		int headViewNowPadding = headView.getPaddingTop();
		int deltaPadding = headViewNowPadding - padding;
		if(soomthRunnable==null){
			soomthRunnable = new SoomthRunnable(deltaPadding, padding);
			post(soomthRunnable);
		}else{
			soomthRunnable.cancleAnimation();
			soomthRunnable = new SoomthRunnable(deltaPadding, padding);
			post(soomthRunnable);
		}

	}
	
	private SoomthRunnable soomthRunnable;

	private class SoomthRunnable implements Runnable {

		private long lastTime = SystemClock.uptimeMillis();
		private long nowTime;
		private int duraTime = 100;
		private int deltaPadding;
		private int padding;
		private boolean isCancle = true;
		
		public SoomthRunnable(int deltaPadding,int padding){
			this.deltaPadding = deltaPadding;
			this.padding = padding;
			isCancle = false;
		}
		
		public void startAnimation(int deltaPadding,int padding){
			this.deltaPadding = deltaPadding;
			this.padding = padding;
			isCancle = false;
		}

		private double deltaDistance() {
			double distance = deltaPadding / (double) duraTime
					* (nowTime - lastTime);
			lastTime = SystemClock.uptimeMillis();
			return distance;
		}
		
		public void cancleAnimation(){
			isCancle = true;
		}

		@Override
		public void run() {
			
			if(isCancle)
				return;
			
			nowTime = SystemClock.uptimeMillis();
			int nowPadding = headView.getPaddingTop();
			if (deltaPadding > 0) {
				if (nowPadding - padding > 0) {
					int deltP = (int) (nowPadding - deltaDistance());
					if (deltP < padding)
						deltP = padding;
					headView.setPadding(0, deltP, 0, 0);
					post(this);
				} else {
					changeViewState();
				}
			} else {
				if (nowPadding - padding < 0) {
					int deltP = (int) (nowPadding - deltaDistance());
					if (deltP > padding)
						deltP = padding;
					headView.setPadding(0, deltP, 0, 0);
					post(this);
				} else {
					changeViewState();
				}
			}

		}

	}

	public void setOnRefreshListener(OnRefreshListener refreshListener) {
		this.refreshListener = refreshListener;
		isRefreshable = true;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

	public void onRefreshComplete() {
		state = DONE;
		lastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
		changeHeaderViewByState();
	}

	public void toRefreshing() {
		if (state != REFRESHING) {
			state = REFRESHING;
			changeHeaderViewByState();
			onRefresh();
		}
	}
	
	public boolean isRefreshing() {
		return state == REFRESHING;
	}
	
	public void onRefresh() {
		if (refreshListener != null) {
			refreshListener.onRefresh();
		}
	}

	// 此方法直接照搬自网络上的一个下拉刷新的demo，此处是“估计”headView的width以及height
	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ListView#setAdapter(android.widget.ListAdapter)
	 */
	@Override
	public void setAdapter(ListAdapter adapter) {
		lastUpdatedTextView.setText("最近更新:" + new Date().toLocaleString());
		super.setAdapter(adapter);
	}

	private void onStopScroll(AbsListView view) {
		if (null != onStopScroll) {
			onStopScroll.onStopScroll(view);
		}
	}

	public interface OnScrollListner {
		public void onMyScrollStateChanged(AbsListView view, int scrollState);
	}

	public interface OnStopScrollListner {
		public void onStopScroll(AbsListView view);
	}

	public void setOnStopScrollListener(OnStopScrollListner ossl) {
		onStopScroll = ossl;
	}

	public void setMyScrollListener2(OnScrollListner ossl) {
		onStopScroll2 = ossl;
	}
}
