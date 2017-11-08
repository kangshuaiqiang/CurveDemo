package demo.ksq.com.curvedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 贝塞尔曲线
 * Created by 黑白 on 2017/11/6.
 */

public class Curve extends View {


    public Curve(Context context) {
        this(context, null);
    }

    public Curve(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Curve(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private Paint kPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private Path kPath = new Path();

    private void init() {


        Paint paint = kPaint;
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GRAY);

        //一阶贝塞尔
        oneCurve();
    }

    //一阶贝塞尔
    private void oneCurve() {
        //速度快  一阶贝塞尔
        Path path = kPath;
        path.moveTo(50, 50);
        path.lineTo(150, 150);

        //二阶贝塞尔  原点坐标
//        path.quadTo(500, 100, 700, 300);
        //二阶贝塞尔相对的实现  相对于结束坐标
        path.rQuadTo(50, -100, 200, 0);

        //三阶贝塞尔  先移动
        path.moveTo(50, 200);
        //相对于原点
//        path.cubicTo(200, 150, 200, 500, 300, 300);
        //相对于结束点
        path.rCubicTo(150, -50, 150, 300, 250, 100);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //一阶贝塞尔
        oneCurveDraw(canvas);
    }

    //一阶贝塞尔
    private void oneCurveDraw(Canvas canvas) {
        canvas.drawPath(kPath, kPaint);

        canvas.drawPoint(200, 150, kPaint);
        canvas.drawPoint(200, 500, kPaint);


    }
}
