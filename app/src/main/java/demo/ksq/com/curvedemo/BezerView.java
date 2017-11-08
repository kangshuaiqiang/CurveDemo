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
 * Created by 黑白 on 2017/11/7.
 */

public class BezerView extends View {

    public BezerView(Context context) {
        this(context, null);
    }

    public BezerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    Path kSrcBezer = new Path();
    private Path kBezer = new Path();
    private Paint kPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


    private void init() {
        //初始化画笔参数
//        Paint paint = kPaint;
        kPaint.setAntiAlias(true);
        kPaint.setDither(true);
        kPaint.setStyle(Paint.Style.STROKE);
        kPaint.setStrokeWidth(5);
        kPaint.setColor(Color.GRAY);
        //系统提供到 三阶贝塞尔曲线
        kSrcBezer.cubicTo(200, 700, 500, 500, 700, 200);

        //线程实现初始化贝塞尔曲线
        new Thread() {
            @Override
            public void run() {
                super.run();
                //初始化贝塞尔曲线  4阶
                initBazerView();
            }
        }.start();


    }

    //实现
    private void initBazerView() {

        //X和Y(0,0),(300,300),(200,700),(500,500),.(700,1200)
        float[] xPoints = new float[]{0, 200, 500, 700,600};
        float[] yPoints = new float[]{0, 700, 500, 200,800};
        Path path = kBezer;
        int fps = 10000;
        //刷新率
        for (int i = 0; i < fps; i++) {
            //必须转为float型  不然没有小数后面的数据
            float pregress = i / (float) fps;
            float x = calculateBezer(pregress, xPoints);
            float y = calculateBezer(pregress, yPoints);
            //使用链接的方式  当xy变动足够小的话就是平滑曲线
            path.lineTo(x, y);
            //界面刷新
            postInvalidate();

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 计算某时刻的贝塞尔所处的值（x或y）
     *
     * @param t      时间0——1之间的值
     * @param values 贝塞尔曲线的集合点 （x——y）
     * @return当t时刻的贝塞尔所处的点
     */
    private float calculateBezer(float t, float... values) {
        //采用双层循环
        final int len = values.length;//4
        for (int i = len - 1; i > 0; i--) {
            //外层
            for (int j = 0; j < i; j++) {
                //内层计算
                values[j] = values[j] + (values[j + 1] - values[j]) * t;

            }
        }

        //运算时结果保存在第一位
        //所以我们返回第一位
        return values[0];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        kPaint.setColor(Color.BLACK);
        canvas.drawPath(kSrcBezer, kPaint);
        kPaint.setColor(Color.BLUE);
        canvas.drawPath(kBezer, kPaint);
    }
}
