/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fountainjammm;

import java.util.Comparator;

/**
 *
 * @author Mike
 */
public class blockCompare implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        pack p1 = (pack)o1;
        pack p2 = (pack)o2;
        if (p1.blocks.size() < p2.blocks.size())
            return -1;
        else
            return 1;        
    }
}