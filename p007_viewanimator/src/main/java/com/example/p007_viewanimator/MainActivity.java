package com.example.p007_viewanimator;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.libanimation.viewanimator.AnimationBuilder;
import com.example.libanimation.viewanimator.AnimationListener;
import com.example.libanimation.viewanimator.ViewAnimator;

import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // SVG
    private static final String SVG_PATH = "M 42.266949,70.444915 C 87.351695,30.995763 104.25847,28.177966 104.25847,28.177966 l 87.3517,36.631356 8.45339,14.088983 L 166.25,104.25847 50.720339,140.88983 c 0,0 -45.0847458,180.33898 -39.449153,194.42797 5.635594,14.08898 67.627119,183.15678 67.627119,183.15678 l 16.90678,81.7161 c 0,0 98.622885,19.72457 115.529665,22.54237 16.90678,2.8178 70.44491,-22.54237 78.8983,-33.81356 8.45339,-11.27118 76.08051,-107.07627 33.81356,-126.80085 -42.26695,-19.72457 -132.43644,-56.35593 -132.43644,-56.35593 0,0 -33.81356,-73.26271 -19.72458,-73.26271 14.08899,0 132.43644,73.26271 138.07204,33.81356 5.63559,-39.44915 19.72457,-169.0678 19.72457,-169.0678 0,0 28.17797,-25.36017 -28.17796,-19.72457 -56.35593,5.63559 -95.80509,11.27118 -95.80509,11.27118 l 42.26695,-87.35169 8.45339,-28.177968";
    private static final int[] START_POINT = new int[]{300, 270};
    private static final int[] BOTTOM_POINT = new int[]{300, 400};
    private static final int[] LEFT_CONTROL_POINT = new int[]{450, 200};
    private static final int[] RIGHT_CONTROL_POINT = new int[]{150, 200};

    ImageView image;
    ImageView mountain;
    TextView text;
    TextView percent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image = findViewById(R.id.image);
        mountain = findViewById(R.id.mountain);
        text = findViewById(R.id.text);
        percent = findViewById(R.id.percent);

        findViewById(R.id.parallel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateParallel();
            }
        });

        findViewById(R.id.mountain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleAnimation();
            }
        });

        findViewById(R.id.sequentially).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateSequentially();
            }
        });

        findViewById(R.id.enhanced).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random = new Random().nextInt(10);
                final ViewGroup viewGroup = (ViewGroup) v.getParent();
                final ImageView imageView = new ImageView(v.getContext());
                imageView.setImageResource(R.mipmap.ic_launcher);
                viewGroup.addView(imageView);
                AnimationBuilder builder;
                switch (random) {
                    case 0:
                        builder = ViewAnimator.animate(imageView).shake().interpolator(new LinearInterpolator());
                        break;
                    case 1:
                        builder = ViewAnimator.animate(imageView).bounceIn().interpolator(new BounceInterpolator());
                        break;
                    case 2:
                        builder = ViewAnimator.animate(imageView).flash().repeatCount(4);
                        break;
                    case 3:
                        builder = ViewAnimator.animate(imageView).flipHorizontal();
                        break;
                    case 4:
                        builder = ViewAnimator.animate(imageView).wave().duration(5000);
                        break;
                    case 5:
                        builder = ViewAnimator.animate(imageView).tada();
                        break;
                    case 6:
                        builder = ViewAnimator.animate(imageView).pulse();
                        break;
                    case 7:
                        builder = ViewAnimator.animate(imageView).standUp();
                        break;
                    case 8:
                        builder = ViewAnimator.animate(imageView).swing();
                        break;
                    default:
                        builder = ViewAnimator.animate(imageView).wobble();
                        break;
                }
                builder.onStop(new AnimationListener.Stop() {
                    @Override
                    public void onStop() {
                        viewGroup.removeView(imageView);
                    }
                }).start();
            }
        });

        findViewById(R.id.path).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Path path = new Path();
                path.moveTo(START_POINT[0], START_POINT[1]);
                path.quadTo(RIGHT_CONTROL_POINT[0], RIGHT_CONTROL_POINT[1], BOTTOM_POINT[0], BOTTOM_POINT[1]);
                path.quadTo(LEFT_CONTROL_POINT[0], LEFT_CONTROL_POINT[1], START_POINT[0], START_POINT[1]);
                path.close();
                ViewAnimator.animate(v).path(path).repeatCount(2).start();
            }
        });

        findViewById(R.id.svgPath).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAnimator.animate(v).svgPath(SVG_PATH).duration(10000).repeatCount(3).start();

                final View btnWave = findViewById(R.id.wave);
                btnWave.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        ViewAnimator.animate(btnWave).wave().duration(3000).start();
                    }
                });

                final View btnShake = findViewById(R.id.shake);
                btnShake.setOnClickListener(new View.OnClickListener() {
                    @Override public void onClick(View v) {
                        ViewAnimator.animate(btnShake).shake().duration(3000).start();
                    }
                });
            }
        });
    }

    protected void simpleAnimation() {
        ViewAnimator.animate(mountain)
                .translationY(-1000, 0)
                .alpha(0, 1)
                .andAnimate(text)
                .translationX(-200, 0)
                .interpolator(new DecelerateInterpolator())
                .duration(2000)

                .thenAnimate(mountain)
                .scale(1f, 0.5f, 1f)
                .interpolator(new AccelerateInterpolator())
                .duration(1000)

                .start();
    }

    protected void animateParallel() {
        final ViewAnimator viewAnimator = ViewAnimator.animate(mountain, image)
                .dp().translationY(-1000, 0)
                .alpha(0, 1)
                .singleInterpolator(new OvershootInterpolator())

                .andAnimate(percent)
                .scale(0, 1)

                .andAnimate(text)
                .textColor(Color.BLACK, Color.WHITE)
                .backgroundColor(Color.WHITE, Color.BLACK)

                .waitForHeight()
                .singleInterpolator(new AccelerateDecelerateInterpolator())
                .duration(2000)

                .thenAnimate(percent)
                .custom(new AnimationListener.Update<TextView>() {
                    @Override
                    public void update(TextView view, float value) {
                        view.setText(String.format(Locale.US, "%.02f%%", value));
                    }
                }, 0, 1)

                .andAnimate(image)
                .rotation(0, 360)

                .duration(5000)

                .start();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                viewAnimator.cancel();
            }
        }, 3000);
    }

    protected void animateSequentially() {
        ViewAnimator.animate(image)
                .dp().width(100f, 150f)
                .alpha(1, 0.1f)
                .interpolator(new DecelerateInterpolator())
                .duration(800)
                .thenAnimate(image)
                .dp().width(150f, 100f)
                .alpha(0.1f, 1f)
                .interpolator(new AccelerateInterpolator())
                .duration(1200)
                .start();

        ViewAnimator
                .animate(image).scaleX(0, 1).scaleY(0, 1).alpha(0, 1).decelerate().duration(500)
                .thenAnimate(image).scaleX(1, 0).scaleY(1, 0).alpha(1, 0).accelerate().duration(500);
    }
}
