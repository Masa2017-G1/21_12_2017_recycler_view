package com.sheygam.masa_2017_21_12;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MyAdapter.AdapterClickListener {
    private RecyclerView myList;
    private MyAdapter adapter;
    private MySecondAdapter secondAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myList = findViewById(R.id.my_list);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager manager1 = new GridLayoutManager(this, 3);
        adapter = new MyAdapter(this);
        secondAdapter = new MySecondAdapter();
        myList.setLayoutManager(manager);
        myList.setAdapter(secondAdapter);

        RecyclerView.ItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        myList.addItemDecoration(divider);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new MyTochHelperCallback());
        touchHelper.attachToRecyclerView(myList);
        myList.addOnItemTouchListener(new MyOnItemTouchListener());
    }

    @Override
    public void onItemClick(View view, int position, Person data) {
        Toast.makeText(this, "Was clicked " + data.getName(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_item) {
            adapter.addItem(new Person("New Person", "newemail@mail.com"));
        } else if (item.getItemId() == R.id.delete_item) {
            adapter.remove(4);
        }
        return super.onOptionsItemSelected(item);
    }

    class MyTochHelperCallback extends ItemTouchHelper.Callback {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return makeMovementFlags(0, ItemTouchHelper.END | ItemTouchHelper.START);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            adapter.moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            if (direction == ItemTouchHelper.END) {
                adapter.remove(viewHolder.getAdapterPosition());
            } else {
                Toast.makeText(MainActivity.this, "Was swipe to Start", Toast.LENGTH_SHORT).show();
                adapter.updateItem(viewHolder.getAdapterPosition());
            }
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        public static final float ALPHA_FULL = 1.0f;

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                // Get RecyclerView item from the ViewHolder
                View itemView = viewHolder.itemView;

                Paint p = new Paint();
                Bitmap icon;

                if (dX > 0) {
            /* Note, ApplicationManager is a helper class I created
               myself to get a context outside an Activity class -
               feel free to use your own method */

                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_left);

            /* Set your color for positive displacement */
                    p.setARGB(255, 0, 120, 0);

                    // Draw Rect with varying right side, equal to displacement dX
                    c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
                            (float) itemView.getBottom(), p);

                    // Set the image icon for Right swipe
                    c.drawBitmap(icon,
                            (float) itemView.getLeft() + convertDpToPx(16),
                            (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight())/2,
                            p);
                } else {
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_right);

            /* Set your color for negative displacement */
                    p.setARGB(255, 217, 108, 0);

                    // Draw Rect with varying left side, equal to the item's right side
                    // plus negative displacement dX
                    c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
                            (float) itemView.getRight(), (float) itemView.getBottom(), p);

                    //Set the image icon for Left swipe
                    c.drawBitmap(icon,
                            (float) itemView.getRight() - convertDpToPx(16) - icon.getWidth(),
                            (float) itemView.getTop() + ((float) itemView.getBottom() - (float) itemView.getTop() - icon.getHeight())/2,
                            p);
                }

                // Fade out the view as it is swiped out of the parent's bounds
                final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);

            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        private int convertDpToPx(int dp){
            return Math.round(dp * (getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
        }
    }


    class MyOnItemTouchListener implements RecyclerView.OnItemTouchListener {
        GestureDetector detector;

        public MyOnItemTouchListener() {
            detector = new GestureDetector(MainActivity.this, new GestureDetector.SimpleOnGestureListener() {

                @Override
                public boolean onSingleTapUp(MotionEvent e) {

                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    Toast.makeText(MainActivity.this, "Long press", Toast.LENGTH_SHORT).show();
                    super.onLongPress(e);
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(final RecyclerView rv, MotionEvent e) {
            View view = rv.findChildViewUnder(e.getX(), e.getY());
            if (view != null && detector.onTouchEvent(e)) {
                int position = rv.getChildAdapterPosition(view);
                Person p = secondAdapter.getItem(position);
                onItemClick(view, position, p);
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}
