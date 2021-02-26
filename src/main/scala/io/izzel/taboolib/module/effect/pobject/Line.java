package io.izzel.taboolib.module.effect.pobject;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

/**
 * 表示一条线
 *
 * @author Zoyn
 */
public class Line extends ParticleObject {

    private Vector vector;
    private Location start;
    private Location end;
    /**
     * 步长
     */
    private double step;
    /**
     * 向量长度
     */
    private double length;

    /**
     * 构造一条线
     *
     * @param start 线的起点
     * @param end   线的终点
     */
    public Line(Location start, Location end) {
        this(start, end, 0.1);
    }

    /**
     * 构造一条线
     *
     * @param start 线的起点
     * @param end   线的终点
     * @param step  每个粒子之间的间隔 (也即步长)
     */
    public Line(Location start, Location end, double step) {
        this(start, end, step, 20L);
    }

    /**
     * 构造一条线
     *
     * @param start  线的起点
     * @param end    线的终点
     * @param step   每个粒子之间的间隔 (也即步长)
     * @param period 特效周期(如果需要可以使用)
     */
    public Line(Location start, Location end, double step, long period) {
        this.start = start;
        this.end = end;
        this.step = step;
        setPeriod(period);

        // 对向量进行重置
        resetVector();
    }

    @Override
    public void show() {
        for (double i = 0; i < length; i += step) {
            Vector vectorTemp = vector.clone().multiply(i);
            spawnParticle(start.clone().add(vectorTemp));
//            start.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, start.clone().add(vectorTemp), 1);
        }
    }

    /**
     * 获取线的起始坐标
     *
     * @return {@link Location}
     */
    public Location getStart() {
        return start;
    }

    /**
     * 利用给定的坐标设置线的起始坐标
     *
     * @param start 起始坐标
     * @return {@link Line}
     */
    public Line setStart(Location start) {
        this.start = start;
        resetVector();
        return this;
    }

    /**
     * 获取线的终点坐标
     *
     * @return {@link Location}
     */
    public Location getEnd() {
        return end;
    }

    /**
     * 利用给定的坐标设置线的终点坐标
     *
     * @param end 终点
     * @return {@link Line}
     */
    public Line setEnd(Location end) {
        this.end = end;
        resetVector();
        return this;
    }

    /**
     * 获取每个粒子之间的间隔
     *
     * @return 也就是循环的步长
     */
    public double getStep() {
        return step;
    }

    /**
     * 设置每个粒子之间的间隔
     *
     * @param step 间隔
     * @return {@link Line}
     */
    public Line setStep(double step) {
        this.step = step;
        resetVector();
        return this;
    }

    /**
     * 手动重设线的向量
     */
    public void resetVector() {
        vector = end.clone().subtract(start).toVector();
        length = vector.length();
        vector.normalize();
    }

    public static void buildLine(Location locA, Location locB, double step, Particle particle) {
        Vector vectorAB = locB.clone().subtract(locA).toVector();
        double vectorLength = vectorAB.length();
        vectorAB.normalize();
        for (double i = 0; i < vectorLength; i += step) {
            locA.getWorld().spawnParticle(particle, locA.clone().add(vectorAB.clone().multiply(i)), 1);
        }
    }
}
