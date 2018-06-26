package uk.co.bigfishstudios.myapplication;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.AppBarLayout;
import android.support.transition.TransitionManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;

public class NoAnimCollapsibleConstraintLayout extends ConstraintLayout
    implements AppBarLayout.OnOffsetChangedListener {

  private static final String TAG = NoAnimCollapsibleConstraintLayout.class.getSimpleName();
  private float mTransitionThreshold = 0.35f;
  private int mLastPosition = 0;
  private boolean mToolbarOpen = true;

  private ConstraintSet mOpenToolbarSet = new ConstraintSet();
  private ConstraintSet mCloseToolbarSet = new ConstraintSet();
  private ConstraintLayout constraint;

  public NoAnimCollapsibleConstraintLayout(Context context) {
    this(context, null);
  }

  public NoAnimCollapsibleConstraintLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public NoAnimCollapsibleConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    LayoutInflater.from(getContext()).inflate(R.layout.open, this, true);
    constraint = findViewById(R.id.constraint);
  }

  @Override
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
    if (getParent() instanceof AppBarLayout) {
      Log.d(TAG, "Instance AppBarLayout");
      AppBarLayout appBarLayout = (AppBarLayout) getParent();
      appBarLayout.addOnOffsetChangedListener(this);
      mOpenToolbarSet.clone(constraint);
      mCloseToolbarSet.clone(getContext(), R.layout.close);
    } else {
      Log.d(TAG, "Instance if Not Parent");
    }
  }

  @Override
  public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
    Log.d(TAG, "onOffsetChanged");
    if (mLastPosition == verticalOffset) {
      return;
    }

    mLastPosition = verticalOffset;
    Float progress = (Math.abs(verticalOffset / (float) (appBarLayout.getHeight())));

    AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) getLayoutParams();
    params.topMargin = -verticalOffset;
    setLayoutParams(params);
    TransitionManager.beginDelayedTransition(constraint);
    if (mToolbarOpen && progress > mTransitionThreshold) {
      Log.d(TAG, "Apply close.xml");
      mCloseToolbarSet.applyTo(constraint);
      mToolbarOpen = false;
    } else if (!mToolbarOpen && progress < mTransitionThreshold) {
      Log.d(TAG, "Apply open.xml");
      mOpenToolbarSet.applyTo(constraint);
      mToolbarOpen = true;
    }
  }
}