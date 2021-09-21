package com.android.grsu.numbersgame.view;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;

@SuppressLint("ResourceAsColor")
public class DraggableButton extends AppCompatButton {

	private static final String LOG_TAG = DraggableButton.class.getSimpleName();

	private static final float HOVER_ALPHA = (float) 0.2;
	private boolean mDragInProgress;
	private boolean mHovering;

	private boolean mAcceptsDrag;

	protected CharSequence LABEL = "label";

	public DraggableButton(Context context) {
		super(context);
		constructe();
	}

	public DraggableButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		constructe();
	}

	public DraggableButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		constructe();
	}

	private void constructe() {
		setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				ClipData data = ClipData.newPlainText(LABEL,"text");
//				ClipData data = ClipData.newPlainText(LABEL,
//						(CharSequence) v.getTag(R.string.tag_number));
				v.startDrag(data, new DragShadowBuilder(v), v, 0);
				
				return true;
			}
		});

	}

	@Override
	public boolean onDragEvent(DragEvent event) {
		
		boolean result = false;
		switch (event.getAction()) {
		case DragEvent.ACTION_DRAG_STARTED: {
			// claim to accept any dragged content
			Log.i(LOG_TAG, "Drag started, event=" + event);
			// cache whether we accept the drag to return for LOCATION events
			mDragInProgress = true;
			mAcceptsDrag = result = true;
			// Redraw in the new visual state if we are a potential drop target
			if (mAcceptsDrag) {
				invalidate();
			}

		}
			break;

		case DragEvent.ACTION_DRAG_ENDED: {
			Log.i(LOG_TAG, "Drag ended.");
			if (mAcceptsDrag) {
				invalidate();
			}
			mDragInProgress = false;
			mHovering = false;
		}
			break;

		case DragEvent.ACTION_DRAG_LOCATION: {
			// we returned true to DRAG_STARTED, so return true here
			Log.i(LOG_TAG, "... seeing drag locations ...");
			result = mAcceptsDrag;
		}
			break;

		case DragEvent.ACTION_DROP: {
			Log.i(LOG_TAG, "Got a drop! dot=" + this + " event=" + event);
			processDrop(event);
			result = true;
		}
			break;

		case DragEvent.ACTION_DRAG_ENTERED: {
			Log.i(LOG_TAG, "Entered dot @ " + this);
			mHovering = true;
			invalidate();
		}
			break;

		case DragEvent.ACTION_DRAG_EXITED: {
			Log.i(LOG_TAG, "Exited dot @ " + this);
			mHovering = false;
			invalidate();
		}
			break;

		default:
			Log.i(LOG_TAG, "other drag event: " + event);
			result = mAcceptsDrag;
			break;
		}

		return result;
	}

	private void processDrop(DragEvent event) {
		DraggableButton initiator = (DraggableButton) event.getLocalState();
		CharSequence data = initiator.getText();//clipData.getItemAt(0).getText().toString();
		
		initiator.setText(this.getText());
		this.setText(data);
		
		Drawable ibg = initiator.getBackground();
		Drawable tbg = this.getBackground();
		
		initiator.setBackgroundDrawable(tbg);
		this.setBackgroundDrawable(ibg);
		
		int iC = (Integer) initiator.getTag();
		int tC = (Integer) this.getTag();
		
		Log.d(LOG_TAG, "processDrop: ic="+iC+" tc="+tC);
		initiator.setTag(tC);
		this.setTag(iC);
		
//		il.setBackgroundColor(tC);
//		tl.setBackgroundColor(iC);
		
		float initX = initiator.getX();
		float initY = initiator.getY();
		float thisX = this.getX();
		float thisY = this.getY();
		
		Log.d(LOG_TAG, "processDrop: iX="+initX+" iY="+initY+ "; tX="+thisX+" tY="+thisY);
		
//		initiator.animate().translationX(thisX-initX).translationY(thisY-initY);
//		this.animate().translationX(initX-thisX).translationY(initY-thisY);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
		if (mDragInProgress && mAcceptsDrag && mHovering) {
			setAlpha(HOVER_ALPHA);
		} else
			setAlpha(1);
	}

}
