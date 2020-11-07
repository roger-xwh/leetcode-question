package com.roger.solution;

import java.util.Arrays;

public class FindMedian {
    public double execute(int[] nums1, int[] nums2) {
        return method4(nums1, nums2);
    }

    /**
     * 比O(log(m+n))更优的时间复杂度O(min(logm, logn)，采用了将数组一分为二的策略
     *
     * @param nums1
     * @param nums2
     * @return
     */
    private double method4(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;
        if (len1 > len2) {
            return method4(nums2, nums1);
        }
        int left = 0, right = len1;
        int median1 = 0, median2 = 0;
        while (left <= right) {
            int i = (left + right) / 2;
            int j = (len1 + len2 + 1) / 2 - i;

            int num1_left = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
            int num1_right = i == len1 ? Integer.MAX_VALUE : nums1[i];
            int num2_left = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
            int num2_right = j == len2 ? Integer.MAX_VALUE : nums2[j];

            if (num1_left < num2_right) {
                median1 = Math.max(num1_left, num2_left);
                median2 = Math.min(num1_right, num2_right);
                left = i + 1;
            } else {
                right = i - 1;
            }
        }
        return (len1 + len2) % 2 == 0 ? (median1 + median2) / 2.0 : median1;
    }

    /**
     * 采用标准的二分法进行索引移位，依据获取第k个元素的原理
     *
     * @param nums1
     * @param nums2
     * @return
     */
    private double method3(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        int median1 = findMedian(nums1, nums2, (len + 1) / 2);
        int median2 = 0;
        if (len % 2 == 0) {
            median2 = findMedian(nums1, nums2, len / 2 + 1);
        } else {
            median2 = median1;
        }
        return (median1 + median2) / 2.0;
    }

    private int findMedian(int[] nums1, int[] nums2, int median) {
        int k = median;
        int offset1 = 0, offset2 = 0;
        int len1 = nums1.length, len2 = nums2.length;
        while (true) {
            // 数组1越界情况,得到nums2的目标值
            if (offset1 == len1) {
                return nums2[offset2 + k - 1];
            }
            // 数组2越界情况,得到nums1的目标值
            if (offset2 == len2) {
                return nums1[offset1 + k - 1];
            }
            // 最后一个数字，返回目标数据
            if (k == 1) {
                return Math.min(nums1[offset1], nums2[offset2]);
            }
            // 正常情况，计算小值并移动两个数组的offset
            int half = k / 2;
            int newIndex1 = Math.min(offset1 + half, len1) - 1;
            int newIndex2 = Math.min(offset2 + half, len2) - 1;
            if (nums1[newIndex1] <= nums2[newIndex2]) {
                k -= newIndex1 + 1 - offset1;
                offset1 = newIndex1 + 1;
            } else {
                k -= newIndex2 + 1 - offset2;
                offset2 = newIndex2 + 1;
            }
        }
    }

    /**
     * 采用索引移动src归并的方式<br>
     * 对2个数组做索引值比较后归并到一个数组，小数所在的数组移动index<br>
     * 新数组长度为总长一半+1
     *
     * @param nums1
     * @param nums2
     * @return
     */
    private double method2(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length, len = len1 + len2;
        int index1 = 0, index2 = 0;
        int[] nums = new int[len / 2 + 1];
        for (int i = 0; i < nums.length; i++) {
            int min;
            if (index1 < len1 && index2 < len2) {
                min = Math.min(nums1[index1], nums2[index2]);
            } else if (index1 < len1) {
                min = nums1[index1];
            } else {
                min = nums2[index2];
            }
            nums[i] = min;
            if (index1 < len1 && nums1[index1] == min) {
                index1++;
            } else if (index2 < len2 && nums2[index2] == min) {
                index2++;
            }
        }
        if (len % 2 == 0) {
            return (nums[len / 2 - 1] + nums[len / 2]) / 2.0;
        } else {
            return nums[len / 2];
        }
    }

    /**
     * 采用合并、排序(达不到时间复杂度的要求）
     *
     * @param nums1
     * @param nums2
     * @return
     */
    private double method1(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int len = len1 + len2;
        int[] nums;
        if (len1 == 0 && len2 == 0) {
            return 0;
        } else if (len1 != 0 && len2 != 0) {
            nums = new int[nums1.length + nums2.length];
            System.arraycopy(nums1, 0, nums, 0, nums1.length);
            System.arraycopy(nums2, 0, nums, nums1.length, nums2.length);
        } else {
            nums = len1 == 0 ? nums2 : nums1;
        }
        if (len > 1) {
            Arrays.sort(nums);
        }
        if (len % 2 == 1) {
            return nums[len / 2];
        } else {
            return (nums[len / 2 - 1] + nums[len / 2]) / 2.0;
        }
    }
}
