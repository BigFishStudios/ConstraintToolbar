package uk.co.bigfishstudios.constrainttoolbar;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.AppBarLayout;
import android.support.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;

public class AnimCollapsibleConstraintLayout extends ConstraintLayout
    implements AppBarLayout.OnOffsetChangedListener {

  private static final String TAG = AnimCollapsibleConstraintLayout.class.getSimpleName();
  private float transitionThreshold = 0.35f;
  private int lastPosition = 0;
  private boolean toolbarOpen = true;

  private ConstraintSet openToolbarSet = new ConstraintSet();
  private ConstraintSet closeToolbarSet = new ConstraintSet();
  private ConstraintLayout constraint;

  public AnimCollapsibleConstraintLayout(Context context) {
    this(context, null);
  }

  public AnimCollapsibleConstraintLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AnimCollapsibleConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    inflate(getContext(), R.layout.open, this);
    constraint = findViewById(R.id.constraint);
    openToolbarSet.clone(constraint);
    closeToolbarSet.clone(getContext(), R.layout.close);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (getParent() instanceof AppBarLayout) {
      Log.d(TAG, "Instance AppBarLayout");
      AppBarLayout appBarLayout = (AppBarLayout) getParent();
      appBarLayout.addOnOffsetChangedListener(this);
    } else {
      Log.d(TAG, "Instance if Not Parent");
    }
  }

  @Override
  public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    Log.d(TAG, "onOffsetChanged");
    if (lastPosition == verticalOffset) {
      return;
    }

    lastPosition = verticalOffset;
    Float progress = (Math.abs(verticalOffset / (float) (appBarLayout.getHeight())));

    AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) getLayoutParams();
    params.topMargin = -verticalOffset;
    setLayoutParams(params);
    TransitionManager.beginDelayedTransition(constraint);
    if (toolbarOpen && progress > transitionThreshold) {
      Log.d(TAG, "Apply close.xml");
      closeToolbarSet.applyTo(constraint);
      toolbarOpen = false;
    } else if (!toolbarOpen && progress < transitionThreshold) {
      Log.d(TAG, "Apply open.xml");
      openToolbarSet.applyTo(constraint);
      toolbarOpen = true;
    }
  }
}