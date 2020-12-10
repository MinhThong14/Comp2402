package comp2402a2;

import java.util.Comparator;
import java.util.*;
import java.util.Iterator;



public class FastMinDeque<T> implements MinDeque<T> {

  protected Comparator<? super T> comp;
  protected List<T> front;
  protected List<T> back;
  protected List<T> checkMinFirst;
  protected List<T> checkMinLast;


  public FastMinDeque() {
    this(new DefaultComparator<T>());
  }

  public FastMinDeque(Comparator<? super T> comp) {
    this.comp = comp;
    // TODO: Your code goes here
    front = new ArrayList<T>();
    back = new ArrayList<T>();
    checkMinFirst = new ArrayList<T>();
    checkMinLast = new ArrayList<T>();
  }

  protected void balance() {
    int n = size();
    if (size() > 1 && 3*front.size() < back.size()) {
      int s = n/2 - front.size();
      List<T> l1 = new ArrayList<>();
      List<T> l2 = new ArrayList<>();
      l1.addAll(back.subList(0,s));
      Collections.reverse(l1);
      l1.addAll(front);

      //Recalculate min first
      int index1 = 0;
      List<T> min1 = new ArrayList<>();
      min1.add(l1.get(index1));
      index1++;
      while (index1 < l1.size()){
        if((comp.compare(l1.get(index1), min1.get(min1.size() - 1)) < 0) || (comp.compare(l1.get(index1), min1.get(min1.size() - 1)) == 0)){
          min1.add(l1.get(index1));
        }
        index1++;
      }
      checkMinFirst = min1;

      l2.addAll(back.subList(s, back.size()));

      //Recalculate min last
      int index2 = 0;
      List<T> min2 = new ArrayList<>();
      min2.add(l2.get(index2));
      index2++;
      while (index2 < l2.size()){
        if((comp.compare(l2.get(index2), min2.get(min2.size() - 1)) < 0) || (comp.compare(l2.get(index2), min2.get(min2.size() - 1)) == 0)){
          min2.add(l2.get(index2));
        }
        index2++;
      }
      checkMinLast = min2;
      front = l1;
      back = l2;

    } else if (size() > 1 && 3*back.size() < front.size()) {
      int s = front.size() - n/2;
      List<T> l1 = new ArrayList<>();
      List<T> l2 = new ArrayList<>();
      l1.addAll(front.subList(s, front.size()));

      //Recalculate min first
      int index1 = 0;
      List<T> min1 = new ArrayList<>();
      min1.add(l1.get(index1));
      index1++;
      while (index1 < l1.size()){
        if((comp.compare(l1.get(index1), min1.get(min1.size() - 1)) < 0) || (comp.compare(l1.get(index1), min1.get(min1.size() - 1)) == 0)){
          min1.add(l1.get(index1));
        }
        index1++;
      }
      checkMinFirst = min1;

      l2.addAll(front.subList(0, s));
      Collections.reverse(l2);
      l2.addAll(back);

      // Recalculate minLast
      int index2 = 0;
      List<T> min2 = new ArrayList<>();
      min2.add(l2.get(index2));
      index2++;
      while (index2 < l2.size()){
        if((comp.compare(l2.get(index2), min2.get(min2.size() - 1)) < 0) || (comp.compare(l2.get(index2), min2.get(min2.size() - 1)) == 0)){
          min2.add(l2.get(index2));
        }
        index2++;
      }
      checkMinLast = min2;

      front = l1;
      back = l2;
    }
  }


  public void addFirst(T x) {
    // TODO: Your code goes here
    if (checkMinFirst.isEmpty()){
      front.add(x);
      checkMinFirst.add(x);
    }else if ((comp.compare(x, checkMinFirst.get(checkMinFirst.size() - 1)) < 0) || (comp.compare(x, checkMinFirst.get(checkMinFirst.size() - 1)) == 0)){
      front.add(x);
      checkMinFirst.add(x);
      balance();
    }else{
      front.add(x);
      balance();
    }
  }

  public void addLast(T x) {
    // TODO: Your code goes here

    if (checkMinLast.isEmpty()){
      back.add(x);
      checkMinLast.add(x);
    }else if ((comp.compare(x, checkMinLast.get(checkMinLast.size() - 1)) < 0) || (comp.compare(x, checkMinLast.get(checkMinLast.size() - 1)) == 0)){
      back.add(x);
      checkMinLast.add(x);
      balance();
    }else{
      back.add(x);
      balance();
    }
  }

  public T removeFirst() {
    // TODO: Your code goes here
    T x;
    if (front.isEmpty() && back.isEmpty()){
      return null;
    }else if (size() == 1 && !front.isEmpty()){
      x = front.remove(front.size() - 1);
      checkMinFirst.remove(checkMinFirst.size()-1);
      return x;
    }else if (size() == 1 && !back.isEmpty()){
      x = back.remove(back.size() - 1);
      checkMinLast.remove(checkMinLast.size() -1);
      return x;
    }else if(comp.compare(front.get(front.size() - 1), checkMinFirst.get(checkMinFirst.size() - 1)) == 0){
      x = front.remove(front.size() - 1);
      checkMinFirst.remove(checkMinFirst.size()-1);
      balance();
      return x;
    }
    x = front.remove(front.size() - 1);
    balance();
    return x;
  }

  public T removeLast() {
    // TODO: Your code goes here
    T x;
    if (front.isEmpty() && back.isEmpty()){
      return null;
    }else if (size() == 1 && !back.isEmpty()){
      x = back.remove(back.size() - 1);
      checkMinLast.remove(checkMinLast.size()-1);
      return x;
    }else if (size() == 1 && !front.isEmpty()){
      x = front.remove(front.size() - 1);
      checkMinFirst.remove(checkMinFirst.size() -1);
      return x;
    }else if(!back.isEmpty() && !checkMinLast.isEmpty() && comp.compare(back.get(back.size() - 1), checkMinLast.get(checkMinLast.size() - 1)) == 0){
      x = back.remove(back.size() - 1);
      checkMinLast.remove(checkMinLast.size()-1);
      balance();
      return x;
    }
    x = back.remove(back.size() - 1);
    balance();
    return x;
  }

  public T min() {
    // TODO: Your code goes here
    if (front.isEmpty() && back.isEmpty()){
      return null;
    }
    if(checkMinFirst.isEmpty()) {
      return checkMinLast.get(checkMinLast.size()-1);
    }else if(checkMinLast.isEmpty()){
      return checkMinFirst.get(checkMinFirst.size()-1);
    }else if (checkMinFirst.size() == 1 && checkMinLast.size() == 1 && comp.compare(checkMinFirst.get(checkMinFirst.size()-1), checkMinLast.get(checkMinLast.size()-1)) < 0) {
      return checkMinFirst.get(checkMinFirst.size() - 1);
    }else if (checkMinFirst.size() == 1 && checkMinLast.size() == 1 && comp.compare(checkMinLast.get(checkMinLast.size()-1), checkMinFirst.get(checkMinFirst.size()-1)) < 0) {
      return checkMinLast.get(checkMinLast.size() - 1);
    }else if (comp.compare(checkMinFirst.get(checkMinFirst.size()-1), checkMinLast.get(checkMinLast.size()-1)) < 0) {
      return checkMinFirst.get(checkMinFirst.size()-1);
    }
    return checkMinLast.get(checkMinLast.size()-1);
  }

  public int size() {
    // TODO: Your code goes here
    return front.size() + back.size();
  }

  public Iterator<T> iterator() {
    // TODO: Your code goes here
    List<T> mergeList = new ArrayList<>();
    mergeList.addAll(front);
    Collections.reverse(mergeList);
    mergeList.addAll(back);
    return mergeList.iterator();
  }

  public Comparator<? super T> comparator() {
    return comp;
  }

}
