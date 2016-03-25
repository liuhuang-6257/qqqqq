package com.example.myvideomode;

import android.app.Dialog;

public class MPSAlertInterface {
	public interface PositiveClickListener {
		public abstract void onPositiveClick(Dialog dialog);
	}
	
	public interface NegativeClickListener {
		public abstract void onNegativeClick(Dialog dialog);
	}
}
