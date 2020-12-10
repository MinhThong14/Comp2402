package comp2402a2;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Iterator;

public class FastMinStack<T> implements MinStack<T> {

  protected Comparator<? super T> comp;
  protected LinkedList<T> ds;
  protected LinkedList<T> checkMin;

  Integer size = 0;

  public FastMinStack() {
    this(new DefaultComparator<T>());
  }

  public FastMinStack(Comparator<? super T> comp) {
    this.comp = comp;
    ds = new LinkedList<T>();
    checkMin = new LinkedList<>();
  }

  public void push(T x) {
    // TODO: Your code goes here
    if (ds.isEmpty() && checkMin.isEmpty()){
      ds.add(x);
      checkMin.add(x);
      size++;
    }else if ((comp.compare(x, checkMin.getLast()) < 0) || (comp.compare(x, checkMin.getLast()) == 0)){
      ds.add(x);
      checkMin.add(x);
      size++;
    }else{
      ds.add(x);
      size++;
    }
  }

  public T pop() {
    // TODO: Your code goes here
    if (ds.isEmpty()){
      return null;
    }else if ((comp.compare(ds.getLast(), checkMin.getLast()) == 0)){
      checkMin.removeLast();
      size--;
      return ds.removeLast();
    }
    size--;
    return ds.removeLast();
  }

  public T min() {
    // TODO: Your code goes here
    if (checkMin.isEmpty()){
      return null;
    }
    return checkMin.getLast();
  }

  public int size() {
    // TODO: Your code goes here
    return size;
  }

  public Iterator<T> iterator() {
    // TODO: Your code goes here
    return ds.iterator();
  }

  public Comparator<? super T> comparator() {
    return comp;
  }
}
