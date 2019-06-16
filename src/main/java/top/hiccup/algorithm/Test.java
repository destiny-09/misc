package top.hiccup.algorithm;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import sun.reflect.generics.tree.Tree;

/**
 * 测试
 *
 * @author wenhy
 * @date 2019/6/2
 */
public class Test {
    private static void swap(int[] arr, int s, int t) {
        int tmp = arr[s];
        arr[s] = arr[t];
        arr[t] = tmp;
    }

    public void bubble(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }
    }

    public void select(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIdx]) {
                    minIdx = j;
                }
            }
            if (minIdx != i) {
                swap(arr, minIdx, i);
            }
        }
    }

    public void insert(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            int tmp = arr[i];
            int j = i;
            for (; j > 0; j--) {
                if (arr[j - 1] > tmp) {
                    arr[j] = arr[j - 1];
                } else {
                    break;
                }
            }
            arr[j] = tmp;
        }
    }


    public void heap(int[] arr) {
        int n = arr.length;
        int[] tmpArr = new int[n + 1];
        // 转换一下，方便计算
        System.arraycopy(arr, 0, tmpArr, 1, n);
        // 第一步，建堆（两种思路：1、初始堆只有一个元素，依此添加2~n的元素并调整 2、从最后一个非叶子节点（n/2）开始依此往上调整即可）
        for (int i = (n + 1) / 2; i > 0; i--) {
            int j = i;
            // 向下调整
            while (true) {
                int maxIdx = j;
                if (j * 2 <= n && tmpArr[j] < tmpArr[j * 2]) {
                    maxIdx = j * 2;
                }
                if (j * 2 + 1 <= n && tmpArr[maxIdx] < tmpArr[j * 2 + 1]) {
                    maxIdx = j * 2 + 1;
                }
                if (j == maxIdx) {
                    break;
                }
                swap(tmpArr, j, maxIdx);
                j = maxIdx;
            }
        }
        swap(tmpArr, 1, n);
        // 第二步，建堆后，堆顶元素则是最大元素
        for (int i = n - 1; i > 1; i--) {
            // 再次调整堆
            int j = 1;
            while (true) {
                int maxIdx = j;
                if (j * 2 <= i && tmpArr[j] < tmpArr[j * 2]) {
                    maxIdx = j * 2;
                }
                if (j * 2 + 1 <= i && tmpArr[maxIdx] < tmpArr[j * 2 + 1]) {
                    maxIdx = j * 2 + 1;
                }
                if (j == maxIdx) {
                    break;
                }
                swap(tmpArr, j, maxIdx);
                j = maxIdx;
            }
            swap(tmpArr, 1, i);
        }
        System.arraycopy(tmpArr, 1, arr, 0, n);
    }


    @org.junit.Test
    public void test() {
        int[] arr = new int[]{5, 2, 12, 7, 9};
//        bubble(arr);
//        select(arr);
//        insert(arr);
        heap(arr);
        System.out.println(Arrays.toString(arr));
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Solution {
        List<String> paths;

        public List<String> binaryTreePaths(TreeNode root) {
            paths = new ArrayList<>();
            if (root == null) {
                return paths;
            }
            pathInternal(root, new LinkedList<>());
            return paths;
        }

        private void pathInternal(TreeNode node, LinkedList<Integer> list) {
            if (node == null) {
                return;
            }
            list.addLast(node.val);
            if (node.left == null && node.right == null) {
                StringBuilder sb = new StringBuilder();
                for (Integer i : list) {
                    sb.append(i);
                    sb.append("->");
                }
                paths.add(sb.substring(0, sb.length() - 2));
            }
            pathInternal(node.left, list);
            pathInternal(node.right, list);
            list.removeLast();
        }
    }

    @org.junit.Test
    public void test2() {
        Solution solution = new Solution();
        TreeNode root = new TreeNode(5);

        TreeNode t4 = new TreeNode(4);
        TreeNode t8 = new TreeNode(8);
        root.left = t4;
        root.right = t8;

        TreeNode t11 = new TreeNode(11);
        t4.left = t11;

        t11.left = new TreeNode(7);
        t11.right = new TreeNode(2);

        TreeNode t13 = new TreeNode(13);
        TreeNode t42 = new TreeNode(4);
        t8.left = t13;
        t8.right = t42;

        t42.left = new TreeNode(5);
        t42.right = new TreeNode(1);

        System.out.println(solution.pathSum(root, 22));
    }
}