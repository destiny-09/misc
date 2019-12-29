package top.hiccup.misc;

/**
 * 局部性原理测试
 *
 * @author wenhy
 * @date 2019/10/24
 * @VM -Xms1024m -Xmx8192m
 */
public class LocalityTest {

    /**
     * 遍历10亿次
     */
    private static final int max = 1024 * 1024 * 1024;

    private static final int[] arr = new int[max];

    private static long cacheLine() {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            arr[i] = 1;
        }
        return System.currentTimeMillis() - startTime;
    }

    private static long noCacheLine() {
        long startTime = System.currentTimeMillis();
        // 这里步长比cacheLine增加了16倍，理论上“访问内存”的次数少了16倍，性能基本应该能提升16倍左右
        // 但是由于x86缓存行一般64Byte，所以对数组遍历时可以有效利用局部性原理，就算步长增加到16倍，也都还是可以直接访问缓存行，而不用去内存加载
//        for (int i = 0; i < max; i += 16) {
//      for (int i = 0; i < max; i += 32) {
        for (int i = 0; i < max; i += 64) {
            arr[i] = 1;
        }
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        long cacheTotal = 0;
        for (int i = 0; i < 10; i++) {
            long time = cacheLine();
            System.out.println("测试1耗时：" + time);
            cacheTotal += time;
        }
        System.out.println("测试1平均耗时：" + cacheTotal / 10);

        long noCacheTotal = 0;
        for (int i = 0; i < 10; i++) {
            long time = noCacheLine();
            System.out.println("测试2耗时：" + time);
            noCacheTotal += time;
        }
        System.out.println("测试2平均耗时：" + noCacheTotal / 10);
    }
}
