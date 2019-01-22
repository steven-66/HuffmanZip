import java.util.Comparator;

public class PriorityQueue<E extends Comparable<E>> {
	E[] a;
	int size;//size of current queue
	public PriorityQueue(int size) {
		a=(E[]) new Comparable[size];
		this.size=0;
	}
	public boolean isEmpty() {
		return size==1;
	}
	public boolean insert(E element) {
		if(size==a.length){return false;}
		a[size]=element;
		int parent=++size/2-1;
		int current=size-1;
		while(parent>=0 && element.compareTo(a[parent])<0) {
			E tmp=a[parent];
			a[parent]=element;
			a[current]=tmp;
			current=parent;
			parent/=2;
		}
		return true;
		
	}
	public E getMin() {
		return a[0];
	}
	public E extractMin() {
		if(size==0) {
		return null;
		}
		E min=a[0];
		a[0]=a[--size];
		minHeapfy(0);
		return min;
	}
	private void minHeapfy(int i) {
		int left=2*i;
		int right=left+1;
		int min=0;
		if(left<size && a[left].compareTo(a[i])<0) {
			min=left;
		}
		if(right<size && a[right].compareTo(a[left])<0) {
			min=right;
		}
		else if(min==0) {return;}
		E tmp=a[i];
		a[i]=a[min];
		a[min]=tmp;
		minHeapfy(min);
	}
}
