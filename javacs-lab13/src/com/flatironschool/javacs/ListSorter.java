/**
 * Edited by Lindsey Gillaspie 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
		int size = list.size();
		if(size <= 1) {
			return list;
		}
		
		// split the elements into 2 lists
		List<T> one = mergeSort(new LinkedList<T>(list.subList(0, size/2)), comparator);
		List<T> two = mergeSort(new LinkedList<T>(list.subList(size/2, size)), comparator);

		return merge(one, two, comparator);
	}

	/**
 	 * A single sorted list is created from merging two other lists.
  	 * 
       	 */
       	private List<T> merge(List<T> first, List<T> second, Comparator<T> comparator) {
       		List<T> result = new LinkedList<T>();
       		int total_size = first.size() + second.size();
       		
		for (int i=0; i < total_size; i++) {
       			List<T> smaller_first = chooseSmallerFirst(first, second, comparator);
       			result.add(smaller_first.remove(0));
       		}
       		return result;
       	}

	/**
	 * The list with smaller first element is chosen, according to `comparator`.
	 * 
	 */
	private List<T> chooseSmallerFirst(List<T> first, List<T> second, Comparator<T> comparator) {
		if (first.size() == 0) { return second;}
		if (second.size() == 0) { return first;}
		
		int result = comparator.compare(first.get(0), second.get(0));
		
		if (result < 0) { return first;}
		if (result > 0) { return second;}
		
		return first;
	}
	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
        // Completed this
		PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
		for (T element: list) {
			if (heap.size() < k) {
				heap.offer(element);
				continue;
			}
			int cmp = comparator.compare(element, heap.peek());
		
			if (cmp > 0) {
				heap.poll();
				heap.offer(element);
			}
		}
		
		List<T> result = new ArrayList<T>();
		while (!heap.isEmpty()) {
			result.add(heap.poll());
		}
		
		return result;
	}

	/**
	 * Comparator object is used to sort a list.
	 * 
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
	    	PriorityQueue<T> heap = new PriorityQueue<T>(list.size(), comparator);
	      	heap.addAll(list);
	      	
		list.clear();
	      	
		while (!heap.isEmpty()) {
	      		list.add(heap.poll());
	      	}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
