package com.example.myvideomode;


import com.example.myvideomode.MPSAlertInterface.NegativeClickListener;
import com.example.myvideomode.MPSAlertInterface.PositiveClickListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
/**
 * GikooMPS全局Alert Dialog样式定义,带两个按钮.</br>
 * 
 * 1. 遮盖层设置了30%的透明度.</br>
 * 2. 屏蔽了Dialog周围区域和返回键的点击消失事件.</br>
 * 3. 增加了Dialog按钮点击取消事件及回调接口.</br>
 * 4. 示例：
      MPSAlert2BDialog.Builder builder = new MPSAlert2BDialog.Builder(this);
      builder.setTitle(R.string.title);
      builder.setMessage(R.string.content);
      builder.setOnPositiveClickListener("yes", new PositiveClickListener() {
			@Override
			public void onPositiveClick(Dialog dialog) {
				dialog.dismiss();
				// should do
			}
		});
	  builder.setOnNegativeClickListener("no", new NegativeClickListener() {
			@Override
			public void onNegativeClick(Dialog dialog) {
				dialog.dismiss();
				// should do
			}
		});
	  builder.create().show();
 * @author tao_bp
 * @since 2014/04/22
 *
 */
public class MPSAlert2BDialog extends Dialog implements OnKeyListener, View.OnClickListener {
	private Context mContext;
	private String mTitle, mMessage, mPositiveBtnText, mNegativeBtnText;
	private PositiveClickListener mPositiveListener;
	private NegativeClickListener mNegativeListener;
	private TextView mAlertTitle, mAlertMessage;
	private Button mAlertPositiveButton, mAlertNegativeButton;
	private static Handler mHandler = new Handler(); 
	/**
	 * 单按钮自定义AlertDialog构建器
	 */
	public static class Builder {
		private final Context sContext;
		private String sTitle, sMessage, sPositiveBtnText, sNegativeBtnText;
		private PositiveClickListener sPositiveListener;
		private NegativeClickListener sNegativeListener;
		
		public Builder(Context context) {
			this.sContext = context;
		}
		// 设置标题
		public void setTitle(Object title) {
			if(title == null) return;
			if(title instanceof String) {
				this.sTitle = title.toString();
			} else if(title instanceof Integer) {
				this.sTitle = sContext.getResources().getString((Integer)title);
			} else {
				this.sTitle = null;
			}
		}
		// 设置显示内容
		public void setMessage(Object message) {
			if(message == null) return;
			if(message instanceof String) {
				this.sMessage = message.toString();
			} else if(message instanceof Integer) {
				this.sMessage = sContext.getResources().getString((Integer)message);
			} else {
				this.sMessage = null;
			}
		}
		// 设置Positive按钮文本及事件
		public void setOnPositiveClickListener(Object btnText, PositiveClickListener listener) {
			this.sPositiveListener = listener;
			if(btnText == null) return;
			if(btnText instanceof String) {
				this.sPositiveBtnText = btnText.toString();
			} else if(btnText instanceof Integer) {
				this.sPositiveBtnText = sContext.getResources().getString((Integer)btnText);
			} else {
				this.sPositiveBtnText = null;
			}
		}
		// 设置Negative按钮文本及事件
		public void setOnNegativeClickListener(Object btnText, NegativeClickListener listener) {
			this.sNegativeListener = listener;
			if(btnText == null) return;
			if(btnText instanceof String) {
				this.sNegativeBtnText = btnText.toString();
			} else if(btnText instanceof Integer) {
				this.sNegativeBtnText = sContext.getResources().getString((Integer)btnText);
			} else {
				this.sNegativeBtnText = null;
			}
		}
		// 构建AlertDialog对话框实例
		public MPSAlert2BDialog create() {
			MPSAlert2BDialog dialog = new MPSAlert2BDialog(sContext, sTitle,
					sMessage, sPositiveBtnText, sPositiveListener,
					sNegativeBtnText, sNegativeListener);
			return dialog;
		}
	}
	
	private MPSAlert2BDialog(Context context, int theme, String title,
			String message, String positiveBtnText,
			PositiveClickListener positiveListener, String negativeBtnText,
			NegativeClickListener negativeListener) {
		super(context, theme);
		this.mContext = context;
		this.mTitle = title;
		this.mMessage = message;
		this.mPositiveBtnText = positiveBtnText;
		this.mNegativeBtnText = negativeBtnText;
		this.mPositiveListener = positiveListener;
		this.mNegativeListener = negativeListener;
	}

	private MPSAlert2BDialog(Context context, String title, String message,
			String positiveBtnText, PositiveClickListener positiveListener,
			String negativeBtnText, NegativeClickListener negativeListener) {
		super(context, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
		this.mContext = context;
		this.mTitle = title;
		this.mMessage = message;
		this.mPositiveBtnText = positiveBtnText;
		this.mNegativeBtnText = negativeBtnText;
		this.mPositiveListener = positiveListener;
		this.mNegativeListener = negativeListener;
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mps_alert_dialog_2b);
		
		// 设置Dialog周围区域点击不能取消
		this.setCanceledOnTouchOutside(false);
		// 设置Dialog返回键失效
		this.setOnKeyListener(this);
		// 设置Dialog遮盖层透明度
		LayoutParams params = getWindow().getAttributes();
		params.dimAmount = 0.3f;
		getWindow().setAttributes(params);
		
		// 设置Dialog的显示宽度
		int widthOffset = mContext.getResources().getDimensionPixelOffset(R.dimen.mps_alert_dialog_width_offset);
		int screenWidth = ((Activity)mContext).getWindowManager().getDefaultDisplay().getWidth();
		this.getWindow().getAttributes().width = screenWidth - widthOffset;
		// 初始化控件
		mAlertTitle = (TextView) findViewById(R.id.alert_title);
		mAlertMessage = (TextView) findViewById(R.id.alert_message);
		mAlertPositiveButton = (Button) findViewById(R.id.alert_btn_1x);
		mAlertNegativeButton = (Button) findViewById(R.id.alert_btn_2x);
		
		if(TextUtils.isEmpty(mTitle)) {
			mAlertTitle.setVisibility(View.GONE);
		} else {
			mAlertTitle.setVisibility(View.VISIBLE);
			mAlertTitle.setText(mTitle);
		}
		
		if(TextUtils.isEmpty(mMessage)) {
			mAlertMessage.setVisibility(View.GONE);
		} else {
			mAlertMessage.setVisibility(View.VISIBLE);
			mAlertMessage.setText(mMessage);
		}
		mAlertPositiveButton.setText(mPositiveBtnText==null ? "" : mPositiveBtnText);
		mAlertPositiveButton.setOnClickListener(this);
		
		mAlertNegativeButton.setText(mNegativeBtnText==null ? "" : mNegativeBtnText);
		mAlertNegativeButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// switch case isn't supported in ADT 22.
		int id = v.getId();
		if(id == R.id.alert_btn_1x) {
			mHandler.postDelayed(new Runnable(){
				@Override
				public void run() {
					if(mPositiveListener != null) {
						mPositiveListener.onPositiveClick(MPSAlert2BDialog.this);
					} else {
						dismiss();
					}
				}
			}, 10);
		} else if (id == R.id.alert_btn_2x) {
			mHandler.postDelayed(new Runnable(){
				@Override
				public void run() {
					if(mNegativeListener != null) {
						mNegativeListener.onNegativeClick(MPSAlert2BDialog.this);
					} else {
						dismiss();
					}
				}
			}, 10);
		}
		
	}
	
	@Override
	public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
		if(KeyEvent.KEYCODE_BACK == keyCode && KeyEvent.ACTION_UP == event.getAction()) {
			return true;
		}
		return false;
	}

}
