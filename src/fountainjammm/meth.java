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
public class meth {
    
    public static String byteArr2String(byte a){
        byte[] b = new byte[1];
        b[0] = a;
        String ret = new String(b);
        return ret;
    }
    public static String byteArr2String(byte[] b){
        String ret = new String(b);
        return ret;
    }
    
  public String displayByte(Integer i){
      String oneStr = String.format("%08d", i); // this will pad the left side with zeroes
      return oneStr;
  }
  
  public static String displayByte(byte b){
      String oneStr = Integer.toBinaryString(b);
      Integer test = Integer.parseInt(oneStr);
      oneStr = String.format("%08d", test); // this will pad the left side with zeroes
      return oneStr;
  }    
  
  public static String displayByte(byte[] bArr){
      StringBuilder ret = new StringBuilder();
      for (byte b : bArr){
        String oneStr = Integer.toBinaryString(b);
        Integer test = Integer.parseInt(oneStr);
        ret.append(String.format("%08d", test)+" "); // this will pad the left side with zeroes          
      }
      return ret.toString();
  }     
    
    public static boolean in_array(ArrayList<Integer> haystack, Integer needle) {
        
        for(int i=0;i<haystack.size();i++) {
            //Integer t1 = haystack.get(i);
            //System.out.println(t1);
            //System.out.println("needle is "+needle);
            if(haystack.get(i).equals(needle)) {
                return true;
            }
        }
        return false;
    }  
  // the below may be deprecated
  public static String int2string(Integer i){
          byte[] b = new byte[1];
          b[0] = i.byteValue();
          
          return new String(b);
  }    
  public static String byte2string(byte b0){
          byte[] b = new byte[1];
          //b[0] = b0.byteValue();
          b[0] = b0;
          
          return new String(b);
  }    
}
