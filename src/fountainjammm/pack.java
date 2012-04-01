/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fountainjammm;

import java.util.ArrayList;

/**
 *
 * @author mike
 */
public class pack {
    public ArrayList<Integer> blocks = new ArrayList<Integer>();
    public byte[] data;
    public boolean singleton = true;
    
    // constructor
    public pack(Integer block, byte[] data){
        //ArrayList<Integer> blocks = new ArrayList<Integer>;
        blocks.add(block);
        this.data = data;
    }
    
    // for adding blocks    
    public void addBlock(Integer block){
        if (singleton) singleton = false;
        if (!blocks.contains(block)) { // we dont want duplicates
            blocks.add(block);
        }
    }
    
    // override toString
    @Override
    public String toString()
    {
        meth methods = new meth();
        return new String("blocks " + implode(blocks,",") + " contained in data => "+ methods.displayByte(data)+", Singleton = "+singleton);
    }    
    
    // mimicks PHP implode function, putting the arraylist into a string separated by a deliminter like a ","
    public static String implode(ArrayList<Integer> ary, String delim) {
        String out = "";
        for(int i=0; i<ary.size(); i++) {
            if(i!=0) { out += delim; }
            out += ary.get(i);
        }
        return out;
    }
    
}
