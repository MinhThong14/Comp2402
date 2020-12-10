package comp2402a4;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RyabkoTree implements PrefixStack {

    List<Integer> ell;
    long[]  BIT;
    public RyabkoTree() {
        ell = new ArrayList<Integer>();
        BIT = new long[1];
    }

    public void push(int x) {
        // if bit length >= ell.size() + 1 then resize
        if (BIT.length <= ell.size()+1){
            // create new tree with size = bit.length * 3 + 1
            long[] newBIT = new long[BIT.length * 3+1];
            // rebuild new tree
            for(int i = 0; i < ell.size(); i++){
                int index = i+1;
                // Traverse all ancestors and add 'val'
                while (index < newBIT.length)
                {
                    // Add 'val' to current node of BI Tree
                    newBIT[index] += ell.get(i);
                    index += index & (-index);
                }
            }
            // resize tree with new double size tree
            BIT = newBIT;
        }
        // do the push
        ell.add(x);
        // update value in BIT
        // index in BITree[] is 1 more than the index in arr[]
        int index = ell.size();

        
        while (index < BIT.length)
        {
            BIT[index] += x;
            
            index += index & (-index);
        }
    }

    public int pop() {
        // if ell.size() * 3 <= BIT.length then resize
        if (ell.size() * 3 <= BIT.length) {
            long[] newBIT = new long[BIT.length/2+1];
            for(int i = 0; i < ell.size(); i++){
                int index = i+1;
                // Iterate through all ancestors and add value
                while (index < newBIT.length)
                {
                   
                    newBIT[index] += ell.get(i);
                    index += index & (-index);
                }
            }
            BIT = newBIT;
        }
        // index in BITree[] is 1 more than the index in arr[]
        int index = ell.size();
        // remove last element in stack
        int remove = ell.remove(ell.size()-1);
        // update value in BIT
        // iterate through all ancestors and subtract remove value
        while (index < BIT.length)
        {
            BIT[index] -= remove;
            
            index += index & (-index);
        }
        return remove;
    }

    public int get(int i) {
        return ell.get(i);
    }

    public int set(int i, int x) {
        // index in BITree[] is 1 more than
        // the index in arr[]
        int n = BIT.length;
        int index = i + 1;
        long diff = x - ell.get(i);
        // Traverse all ancestors and add 'val'
        while(index < n)
        {
            // Add 'diff' to current node of BIT Tree
            BIT[index] = BIT[index] + diff;

            // Update index to that of parent
            // in update View
            index += index & (-index);
        }
        return ell.set(i, x);
    }

    public long prefixSum(int i) {

        if (i >= size()){
            return 0;
        }
        long sum = 0; // Iniialize result

        // index in BITree[] is 1 more than
        // the index in arr[]
        i = i + 1;
        // Iterate through ancess of BITree[index]
        while(i>0)
        {
            // Add current element of BITree
            // to sum
            sum += BIT[i];

            // Move index to parent node in
            // getSum View
            i -= i & (-i);
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
