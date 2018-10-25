package algorithm.DataStructure;

import java.util.Arrays;

/**
 * 免去boxing的优先队列
 */
public class PriorityHeap {
    private int[] arr;
    private int size;
    public PriorityHeap() {
        this(64);
    }
    public PriorityHeap(int initSize) {
        if (initSize <= 0) {
            initSize = 64;
        }
        arr = new int[initSize];
        size = 0;
    }
    public int peek() {
        return arr[0];
    }
    public int poll() {
        int t = peek();
        arr[0] = arr[--size];
        sink(0);
        return t;
    }
    public void add(int data) {
        resize(1);
        arr[size] = data;
        pop(size);
        size++;
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    /**
     * key下沉方法
     */
    private void sink(int i) {
        while (2 * i <= size - 1) {
            int child = 2 * i;
            if (child < size - 1 && arr[child] > arr[child + 1]) {
                child++;
            }
            if (arr[i] <= arr[child]) {
                break;
            }
            swap(i, child);
            i = child;
        }
    }
    /**
     * key上浮方法
     */
    private void pop(int i) {
        while (i > 0) {
            int parent = i>>1;
            if (arr[i] >= arr[parent]) {
                break;
            }
            swap(i, parent);
            i = parent;
        }
    }

    /**
     * 重新调整数组大小
     */
    private void resize(int increaseSize) {
        if ((size + increaseSize) > arr.length) {
            int newSize = Math.max(size + increaseSize,arr.length<<1);
            int[] t = arr;
            arr = Arrays.copyOf(t, newSize);
        }
    }
    /**
     * Swaps arr[a] with arr[b].
     */
    private void swap(int a, int b) {
        int t = arr[a];
        arr[a] = arr[b];
        arr[b] = t;
    }
}