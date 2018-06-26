package uk.co.bigfishstudios.myapplication;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ScrollingActivity extends AppCompatActivity {

  private AppBarLayout appBarLayout;
  ConstraintSet open = new ConstraintSet();
  ConstraintSet close = new ConstraintSet();
  private ConstraintLayout constraint;
  boolean state;
  private NoAnimCollapsibleConstraintLayout noAnimCollapsibleConstraintLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_scrolling);

    constraint = findViewById(R.id.constraint);
    noAnimCollapsibleConstraintLayout = findViewById(R.id.root);
    appBarLayout = findViewById(R.id.app_bar);

    open.clone(constraint);
    close.clone(this, R.layout.close);

    appBarLayout.setOnClickListener(view -> {
      //Transition transition = new ChangeBounds();
      //transition.setInterpolator(new AnticipateOvershootInterpolator(1.0f));
      //transition.setDuration(1000);
      //TransitionManager.beginDelayedTransition(constraintLayout, transition);
      if (state) {
        Log.d("TEST", "open apply");

        open.applyTo(constraint);
      } else {
        Log.d("TEST", "close apply");
        close.applyTo(constraint);
      }
      state = !state;
    });
  }

  @Override public void onAttachedToWindow() {
    super.onAttachedToWindow();

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_scrolling, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

}
