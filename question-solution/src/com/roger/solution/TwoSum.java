package com.roger.solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TwoSum {
    public int[] execute(int[] nums, int target) {
        return method3(nums, target);
    }

    /**
     * 不足地方：hashmap初始空间（2^n）在一定的情况下有浪费的可能
     * 避免了hashmap的扩容导致的性能浪费
     *
     * @param nums
     * @param target
     * @return
     */
    private int[] method1(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>(nums.length, 1);
        for (int i = 0; i < nums.length; i++) {
            int other = target - nums[i];
            if (map.containsKey(other)) {
                return new int[]{map.get(other).intValue(), i};
            } else if (!map.containsKey(nums[i])) {
                map.put(nums[i], i);
            }
        }
        return new int[0];
    }

    /**
     * 无法避免：有2组数字之和都等于target
     *
     * @param nums
     * @param target
     * @return
     */
    private int[] method2(int[] nums, int target) {
        Arrays.sort(nums);
        List list = Arrays.stream(nums).boxed().collect(Collectors.toList());
        int left = 0;
        int right = 1;
        while (left != right) {
            if (target == nums[left] + nums[right]) {
                return new int[]{list.indexOf(nums[left]), list.indexOf(nums[right])};
            }
            right++;
            if (nums.length == right) {
                left++;
                right = left + 1;
            }
            if (left == nums.length - 1) {
                break;
            }
        }
        return new int[0];
    }

    /**
     * 空间和method1相比，要少
     * 循环的次数也是一致的，但是indexOf内部就是循环获取，影响效率
     *
     * @param nums
     * @param target
     * @return
     */
    private int[] method3(int[] nums, int target) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            int other = target - nums[i];
            int otherIndex = list.indexOf(other);
            if (otherIndex > -1)
                return new int[]{otherIndex, i};
            else
                list.add(nums[i]);
        }
        return new int[0];
    }
}
