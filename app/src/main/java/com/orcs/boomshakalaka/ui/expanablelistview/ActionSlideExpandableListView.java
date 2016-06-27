package com.orcs.boomshakalaka.ui.expanablelistview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.orcs.boomshakalaka.R;


/**
 * A more specific expandable listview in which the expandable area
 * consist of some buttons which are context actions for the item itself.
 *
 * It handles event binding for those buttons and allow for adding
 * a listener that will be invoked if one of those buttons are pressed.
 *
 * @author tjerk
 * @date 6/26/12 7:01 PM
 */
public class ActionSlideExpandableListView extends SlideExpandableListView implements OnScrollListener {
	private OnActionClickListener listener;
	private int[] buttonIds = null;
	private View footView;
	Context context;
	int totalCount;
	int last;
	boolean isLoading=false;
	public ActionSlideExpandableListView(Context context) {
		super(context);
		this.context=context;
	}

	public ActionSlideExpandableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
	}

	public ActionSlideExpandableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
	}

	public void setItemActionListener(OnActionClickListener listener, int ... buttonIds) {
		this.listener = listener;
		this.buttonIds = buttonIds;
	}
	public void initView(){
		LayoutInflater inflater=  LayoutInflater.from(context);
		footView= inflater.inflate(R.layout.foot_layout, null);
//		footView.findViewById(R.id.foot_layout).setVisibility(View.GONE);
		this.addFooterView(footView);
		this.setOnScrollListener(this);
	}
	public void remoView(){
		if(footView!=null){
			this.removeFooterView(footView);
		}
	}
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		this.last=arg1+arg2;
		this.totalCount=arg3;
	}
	//	��������
	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {
		if(totalCount==last&&arg1==SCROLL_STATE_IDLE){
			if(!isLoading){
				if(footView!=null) {
				isLoading=true;
					footView.findViewById(R.id.foot_layout).setVisibility(View.VISIBLE);
					iLoadListener.onLoad();
					setOnItemClickListener(null);
				}
			}
		}
	}
	ILoadListener iLoadListener;
	//	���ü���
	public void setLoadListener( ILoadListener iLoadListener){
		this.iLoadListener=iLoadListener;
	}
	//	����ص��ӿ�
	public interface ILoadListener{
		public void onLoad();
	}
	//	������ɵġ�
	public void loadComplete(){
		isLoading=false;
		if(footView!=null){
			footView.setVisibility(View.GONE);
		}
//			footView.findViewById(R.id.foot_layout).setVisibility(View.GONE);
		this.setOnScrollListener(this);
		setOnItemClickListener(listener2);
	}
	AdapterView.OnItemClickListener listener2=null;
	@Override
	public void setOnItemClickListener(AdapterView.OnItemClickListener listener) {
		super.setOnItemClickListener(listener);
		if(listener!=null) {
			this.listener2 = listener;
		}
	}

	/**
	 * Interface for callback to be invoked whenever an action is clicked in
	 * the expandle area of the list item.
	 */
	public interface OnActionClickListener {
		/**
		 * Called when an action item is clicked.
		 *
		 * @param itemView the view of the list item
		 * @param clickedView the view clicked
		 * @param position the position in the listview
		 */
		public void onClick(View itemView, View clickedView, int position);
	}

	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(new WrapperListAdapterImpl(adapter) {

			@Override
			public View getView(final int position, View view, ViewGroup viewGroup) {
				final View listView = wrapped.getView(position, view, viewGroup);
				// add the action listeners
				if(buttonIds != null && listView!=null) {
					for(int id : buttonIds) {
						View buttonView = listView.findViewById(id);
						if(buttonView!=null) {
							buttonView.findViewById(id).setOnClickListener(new View.OnClickListener() {
								@Override
								public void onClick(View view) {
									if(listener!=null) {
										listener.onClick(listView, view, position);
									}
								}
							});
						}
					}
				}
				return listView;
			}
		});
	}
//	@Override
//	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		 int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
//	                MeasureSpec.AT_MOST);  
//	        super.onMeasure(widthMeasureSpec, expandSpec);  
//	}
}
