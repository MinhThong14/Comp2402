package comp2402a4;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class SlowPrefixStack implements PrefixStack {
  List<Integer> ell;

  public SlowPrefixStack() {
    ell = new ArrayList<Integer>();
  }

  public void push(int x) {
      ell.add(x);
  }

  public int pop() {
    return ell.remove(ell.size()-1);
  }


  public int get(int i) {
    return ell.get(i);
  }

  public int set(int i, int x) {
    return ell.set(i, x);
  }

  public long prefixSum(int i) {
    long sum = 0;
    for (int j = 0; j <= i; j++) {
      sum += ell.get(j);
    }
    return sum;
  }

	public int size() {
    return ell.size();
	}

	public Iterator<Integer> iterator() {
    return ell.iterator();
	}
}
