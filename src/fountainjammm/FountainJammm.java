package fountainjammm;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.*;


public class FountainJammm {

  // NOTE, set DEBUG to true only if you're dealing with text files
  public static final boolean DEBUG = false;
    
  private static final String INPUT_FILE_NAME = "jammm.txt";
//  private static final String INPUT_FILE_NAME = "one.jpg";
  private static final String OUTPUT_FILE_NAME = "jammm_copy.txt";
//  private static final String OUTPUT_FILE_NAME = "one_copy.jpg";

  public static void main(String[] args) throws InterruptedException, IOException {
    FountainJammm fj = new FountainJammm();
// OPEN FILE    
    //read in the bytes from the file
    byte[] fileContents = fj.read(INPUT_FILE_NAME);
    int filelen = fileContents.length; // This will be given to promptChunkSize so they don't exceed

    // set the size of the "chunks" - how many bytes per chunk
    int chunkSize = fj.promptChunkSize(filelen);

// ENCODE 
    // create ArrayList of packages, see pack.java for what this class is
    // MIKES ArrayList<pack> encoded = encodeFile(fileContents, chunkSize);
    ArrayList<pack> encoded = SmartEncodeFile(fileContents, chunkSize);
        
// DROP PACKAGES (SIMULATE INTERFERENCE)
    // remember: the second argument is how many packages to drop
    int hemoDisMany = fj.promptDroppage(filelen/3 - chunkSize);
    encoded = fj.droppage(encoded, hemoDisMany);
        
// DECODE
    byte[] decoded = decode(encoded, chunkSize);
    
// WRITE FILE    
    fj.write(decoded, OUTPUT_FILE_NAME);

    File original = new File(INPUT_FILE_NAME);
    File reconstructed = new File(OUTPUT_FILE_NAME);
    
    boolean check = fj.areFilesEqual(original, reconstructed);
    System.out.println("Start and End files identical = "+check);
    
  }
  
  /*
   * This simulates interference where packages are getting dropped
   * the first argument is the encoded ArrayList
   * the second argument is how many to drop
   */
  ArrayList<pack> droppage(ArrayList<pack> enc, int howmany){
      ArrayList<pack> ret = new ArrayList<pack>();
      
      long seed = System.nanoTime(); // seems like a pretty good seed
            if (DEBUG) System.out.println("seed is "+seed);
      Random randPaul = new Random(seed);
      int num;
      for (int i = 0; i < howmany; i++) {
          do{
              num = randPaul.nextInt(enc.size());
          } while (num == 0);
          enc.remove(num);
                if (DEBUG) System.out.println("Removed item at "+num);
      }
      return enc;
  }
  
  int promptDroppage(int len){
      int ret = 0;
      boolean valid = false;
      do{
          System.out.print("Enter number of packages to remove: (Max size roughly "+ len + ")");

          //  open up standard input
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          String entered = null;

          //  read the username from the command-line; need to use try/catch with the
          //  readLine() method
          try {
             entered = br.readLine();
          } catch (IOException ioe) {
             System.out.println("IO error trying to read your name!");
             System.exit(1);
          }

          if (isNumeric(entered)) {
              ret = Integer.parseInt(entered);
              if (ret <= len){
                  valid = true;
              }
              else
                  System.out.println("Too large, try again...");
          }
          else{
              System.out.println("Try again...");
          }

      } while (valid == false);
      return ret;
  }
  
  int promptChunkSize(int len){
      int ret = 0, max = len/3;
      boolean valid = false;
      do{
          System.out.print("Enter chunks size in bytes: (Max size for this file is "+ max + ")");

          //  open up standard input
          BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
          String entered = null;

          //  read the username from the command-line; need to use try/catch with the
          //  readLine() method
          try {
             entered = br.readLine();
          } catch (IOException ioe) {
             System.out.println("IO error trying to read your name!");
             System.exit(1);
          }

          if (isNumeric(entered)) {
              ret = Integer.parseInt(entered);
              if (ret <= max){
                  valid = true;
              }
              else
                  System.out.println("Too large, try again...");
          }
          else{
              System.out.println("Try again...");
          }

      } while (valid == false);
      return ret;
  }
  
public static boolean isNumeric(String str)  
{  
  try  
  {  
    int i = Integer.parseInt(str);  
  }  
  catch(NumberFormatException nfe)  
  {  
    return false;  
  }  
  return true;  
}
  
  
  /** Read the given binary file, and return its contents as a byte array.*/ 
  byte[] read(String aInputFileName){
    meth methods = new meth();
    log("Reading in binary file named : " + aInputFileName);
    File file = new File(aInputFileName);
    log("File size: " + file.length());
    byte[] result = new byte[(int)file.length()];
    try {
      InputStream input = null;
      try {
        int totalBytesRead = 0;
        input = new BufferedInputStream(new FileInputStream(file));
        while(totalBytesRead < result.length){
          int bytesRemaining = result.length - totalBytesRead;
          //input.read() returns -1, 0, or more :
          int bytesRead = input.read(result, totalBytesRead, bytesRemaining); 
          if (bytesRead > 0){
            totalBytesRead = totalBytesRead + bytesRead;
          }
        }
        /*
         the above style is a bit tricky: it places bytes into the 'result' array; 
         'result' is an output parameter;
         the while loop usually has a single iteration only.
        */
        log("Num bytes read: " + totalBytesRead);
      }
      finally {
          String str = new String(result); 

for(final byte b : result){
    // THE BELOW IS USEFUL, DO NOT DELETE
    //System.out.println(Integer.toString(b & 0xFF, 2)); 
    if (DEBUG) System.out.println(methods.displayByte(b));
}
          if (DEBUG) System.out.println("");
          byte one = result[0];
          byte two = result[1];
         
          if (DEBUG) System.out.println("Byte one is \t"+methods.displayByte(one));
          if (DEBUG) System.out.println("Byte two is \t"+methods.displayByte(two));
          
          byte rByte = xor(one,two);
          if (DEBUG) System.out.println("------------------------");
          if (DEBUG) System.out.println("XOR result is \t"+methods.displayByte(xor(one, two)));
          if (DEBUG) System.out.println("\nAs a string, the contents read: \n"+str);
        input.close();
      }
    }
    catch (FileNotFoundException ex) {
      log("File not found.");
    }
    catch (IOException ex) {
      log(ex);
    }
    return result;
  }
  
  /**
   Write a byte array to the given filename
  */
  void write(byte[] aInput, String aOutputFileName){

    log("Writing binary file...");
    try {
      OutputStream output = null;
      try {
        output = new BufferedOutputStream(new FileOutputStream(aOutputFileName));
        output.write(aInput);
      }
      finally {
        output.close();
      }
    }
    catch(FileNotFoundException ex){
      log("File not found.");
    }
    catch(IOException ex){
      log(ex);
    }
  }
  
  /** Read the given binary file, and return its contents as a byte array.*/ 
  byte[] readAlternateImpl(String aInputFileName){
    log("Reading in binary file named : " + aInputFileName);
    File file = new File(aInputFileName);
    log("File size: " + file.length());
    byte[] result = null;
    try {
      InputStream input =  new BufferedInputStream(new FileInputStream(file));
      result = readAndClose(input);
    }
    catch (FileNotFoundException ex){
      log(ex);
    }
    return result;
  }
  
  /**
   Read an input stream, and return it as a byte array.  
   Sometimes the source of bytes is an input stream instead of a file. 
   This implementation closes aInput after it's read.
  */
  byte[] readAndClose(InputStream aInput){
    //carries the data from input to output :    
    byte[] bucket = new byte[32*1024]; 
    ByteArrayOutputStream result = null; 
    try  {
      try {
        //Use buffering? No. Buffering avoids costly access to disk or network;
        //buffering to an in-memory stream makes no sense.
        result = new ByteArrayOutputStream(bucket.length);
        int bytesRead = 0;
        while(bytesRead != -1){
          //aInput.read() returns -1, 0, or more :
          bytesRead = aInput.read(bucket);
          if(bytesRead > 0){
            result.write(bucket, 0, bytesRead);
          }
        }
      }
      finally {
        aInput.close();
        //result.close(); this is a no-operation for ByteArrayOutputStream
      }
    }
    catch (IOException ex){
      log(ex);
    }
    return result.toByteArray();
  }
  
  private static void log(Object aThing){
    System.out.println(String.valueOf(aThing));
  }
  
  public static ArrayList<pack> SmartEncodeFile(byte[] b, int numBytes){
      // set up variable to be returned at end.
      ArrayList<pack> ret = new ArrayList<pack>();

      // dont get too scared about this List , I'm using this to make extra nulls
      // in case they pick a "chunk size" that's not divisible by total bytes
      List<Byte> bList = new ArrayList<Byte>();
      for (int i = 0; i < b.length; i++) {
          bList.add((Byte)b[i]);
      }
      
          for (int i = 0; i < bList.size(); i++) {
              if (DEBUG) System.out.println("list working "+meth.displayByte(bList.get(i)));
          }
      
      // Make first byte Singleton
      byte[] single = new byte[numBytes]; 
      for (int i = 0; i < numBytes; i++) {
          single[i] = bList.get(0);
          bList.remove(0);
      }
      
      // Creating a pack class of the singlton
      pack temppack = new pack(0, single);
      ret.add(temppack); // adding Singleton to the return object
      
      // Loop through and add all the others
      byte[] tempArr = new byte[numBytes];
      byte[] tempArr2 = new byte[numBytes];
      
      int forIter = bList.size()/numBytes-1;  // this needs to be outside the loop
          if (DEBUG) System.out.println("forIter is "+forIter);
          
      // numBytes is handed into this function, it's the "chunk size"
      for (int i = 0; i < forIter; i++) {
          for (int j = 0; j < numBytes; j++) { // grab say, first two bytes, and remove them from list
              tempArr[j] = bList.get(0);
              bList.remove(0);
          }
          for (int j = 0; j < numBytes; j++) { // grab say, next two bytes to "look ahead" do not remove from list
              tempArr2[j] = bList.get(j);
          }

              if (DEBUG) {
                  System.out.println("\nTRYING TO COMBINE:");
                  System.out.println("first: \t\t"+meth.displayByte(tempArr));
                  System.out.println("second: \t"+meth.displayByte(tempArr2));
                  System.out.println("----------------------------");
                  System.out.println("results: \t"+meth.displayByte(xor(tempArr,tempArr2)));
              }
              
          temppack = new pack(i+1, xor(tempArr,tempArr2)); // combine i and i+1 - // remember, plus one because we've already removed stuff for singleton and loop starts at 0
          temppack.addBlock(i+2); // remember, plus two because we've already removed stuff for singleton and loop starts at 0

          ret.add(temppack);    // 1,2  2,3  3,4 and so on
          
          temppack = new pack(0, xor(tempArr,single)); // combine 0 and i
          temppack.addBlock(i+1); // remember, plus one because we've already removed stuff for singleton and loop starts at 0
          ret.add(temppack);    // Link them all to singleton
      }
      
      // make condition to fill in nulls if there's an uneven number
      // if there are leftovers in the bList, add them and pad with nulls
      // we'll combine tempArr2 with a padded version of 
      
      // Since tempArr2 didn't remove them, we need to flush that from the list
        for (int j = 0; j < numBytes; j++) { 
            bList.remove(0);
        }      
        
      int leftovers = b.length % numBytes;
          if (DEBUG) System.out.println("leftovers "+leftovers);
          if (DEBUG) System.out.println("blist size "+bList.size());
      for (int i = 0; i < leftovers; i++) { // fill in with the remainder
          tempArr[i] = bList.get(0);
                if (DEBUG) System.out.println("want to remove index "+i);
          //bList.remove(i);
          bList.remove(0);
      }
      
      // pretty straightforward variable names, they mean what they're named
      int padThisManyNulls = numBytes - leftovers;
      if (DEBUG) System.out.println("paddthismanynulls is "+padThisManyNulls);
      for (int i = leftovers; i < (leftovers+padThisManyNulls); i++) { // pad with as many nulls as needed
          tempArr[i] = 0;
      }
      
          if (DEBUG) {
              System.out.println("Combine these two:");
              System.out.println("one:\t "+meth.displayByte(tempArr));
              System.out.println("two:\t "+meth.displayByte(tempArr2));
              System.out.println("------------------------");
              System.out.println("is:\t "+meth.displayByte(xor(tempArr, tempArr2)));          
          }
      
      temppack = new pack(ret.size(), xor(tempArr,tempArr2)); // add the leftover ones XORed to the return
      temppack.addBlock(forIter+1);
      ret.add(temppack);
      
      return ret;
  }  
  
  /* Please do not change the below, use as reference when making Smart encoding */
  public static ArrayList<pack> encodeFile(byte[] b, int numBytes){
      // set up variable to be returned at end.
      ArrayList<pack> ret = new ArrayList<pack>();

      // dont get too scared about this List , I'm using this to make extra nulls
      // in case they pick a "chunk size" that's not divisible by total bytes
      List<Byte> bList = new ArrayList<Byte>();
      for (int i = 0; i < b.length; i++) {
          bList.add((Byte)b[i]);
      }
      
          for (int i = 0; i < bList.size(); i++) {
              if (DEBUG) System.out.println("list working "+meth.displayByte(bList.get(i)));
          }
      
      // Make first byte Singleton
      byte[] single = new byte[numBytes]; 
      for (int i = 0; i < numBytes; i++) {
          single[i] = bList.get(0);
          bList.remove(0);
      }
      
      // Creating a pack class of the singlton
      pack temppack = new pack(0, single);
      ret.add(temppack); // adding Singleton to the return object
      
      // Loop through and add all the others
      byte[] tempArr = new byte[numBytes];
      byte[] tempArr2 = new byte[numBytes];
      
      int forIter = bList.size()/numBytes-1;  // this needs to be outside the loop
          if (DEBUG) System.out.println("forIter is "+forIter);
          
      // numBytes is handed into this function, it's the "chunk size"
      for (int i = 0; i < forIter; i++) {
          for (int j = 0; j < numBytes; j++) { // grab say, first two bytes, and remove them from list
              tempArr[j] = bList.get(0);
              bList.remove(0);
          }
          for (int j = 0; j < numBytes; j++) { // grab say, next two bytes to "look ahead" do not remove from list
              tempArr2[j] = bList.get(j);
          }

              if (DEBUG) {
                  System.out.println("\nTRYING TO COMBINE:");
                  System.out.println("first: \t\t"+meth.displayByte(tempArr));
                  System.out.println("second: \t"+meth.displayByte(tempArr2));
                  System.out.println("----------------------------");
                  System.out.println("results: \t"+meth.displayByte(xor(tempArr,tempArr2)));
              }
              
          temppack = new pack(i+1, xor(tempArr,tempArr2)); // combine i and i+1 - // remember, plus one because we've already removed stuff for singleton and loop starts at 0
          temppack.addBlock(i+2); // remember, plus two because we've already removed stuff for singleton and loop starts at 0

          ret.add(temppack);    // 1,2  2,3  3,4 and so on
          
          temppack = new pack(0, xor(tempArr,single)); // combine 0 and i
          temppack.addBlock(i+1); // remember, plus one because we've already removed stuff for singleton and loop starts at 0
          ret.add(temppack);    // Link them all to singleton
      }
      
      // make condition to fill in nulls if there's an uneven number
      // if there are leftovers in the bList, add them and pad with nulls
      // we'll combine tempArr2 with a padded version of 
      
      // Since tempArr2 didn't remove them, we need to flush that from the list
        for (int j = 0; j < numBytes; j++) { 
            bList.remove(0);
        }      
        
      int leftovers = b.length % numBytes;
          if (DEBUG) System.out.println("leftovers "+leftovers);
          if (DEBUG) System.out.println("blist size "+bList.size());
      for (int i = 0; i < leftovers; i++) { // fill in with the remainder
          tempArr[i] = bList.get(0);
                if (DEBUG) System.out.println("want to remove index "+i);
          //bList.remove(i);
          bList.remove(0);
      }
      
      // pretty straightforward variable names, they mean what they're named
      int padThisManyNulls = numBytes - leftovers;
      if (DEBUG) System.out.println("paddthismanynulls is "+padThisManyNulls);
      for (int i = leftovers; i < (leftovers+padThisManyNulls); i++) { // pad with as many nulls as needed
          tempArr[i] = 0;
      }
      
          if (DEBUG) {
              System.out.println("Combine these two:");
              System.out.println("one:\t "+meth.displayByte(tempArr));
              System.out.println("two:\t "+meth.displayByte(tempArr2));
              System.out.println("------------------------");
              System.out.println("is:\t "+meth.displayByte(xor(tempArr, tempArr2)));          
          }
      
      temppack = new pack(ret.size(), xor(tempArr,tempArr2)); // add the leftover ones XORed to the return
      temppack.addBlock(forIter+1);
      ret.add(temppack);
      
      return ret;
  }
  
  
  public static ArrayList<pack> mimickPPT(){
    System.out.println("\n------- ENCODING PPT EXAMPLE -------\n");
    ArrayList<pack> done = new ArrayList<pack>(); // these are packs we dont want to add more blocks to
    ArrayList<pack> pool = new ArrayList<pack>(); // these we may add more blocks to    
    
    meth methods = new meth();
    
    /*
    
    pack p = new pack(5, (byte)109);
    pool.add(p);
    
    p = new pack(1, (byte)112);
    p.addBlock(3);
    p.addBlock(5);
    pool.add(p);    
    
    p = new pack(2, (byte)8);
    p.addBlock(5);
    pool.add(p);  
    
    
    p = new pack(1, (byte)50);
    p.addBlock(2);
    pool.add(p);
    
    //p = new pack(2, (byte)118);
    p = new pack(2, (byte)86);
    p.addBlock(7);
    pool.add(p);
    
    p = new pack(1, (byte)58);
    p.addBlock(6);
    p.addBlock(7);
    pool.add(p);
    
    p = new pack(4, (byte)12);
    p.addBlock(5);
    pool.add(p);
    */
    
    for (int i = 0; i < pool.size(); i++) {
      System.out.println(i+" => "+pool.get(i));
    }
    
    // at some point move all the pool values to done
      
      done.addAll(pool);
      return done;
    
  }
  
  public static byte[] decode(ArrayList<pack> droplets, int numBytes){
    if (DEBUG) System.out.println("\n------- DECODING -------\n");
    meth methods = new meth();
    pack p = null;
    TreeMap<Integer, String> aa = new TreeMap<Integer, String>(); // aa = associative array
    TreeMap<Integer, byte[]> aaData = new TreeMap<Integer, byte[]>(); // aa = associative array
    // find singleton, assume there's only one
    Integer singletonInt = null;
    for (int i = 0; i < droplets.size(); i++) {
      if (droplets.get(i).singleton == true){
          singletonInt = droplets.get(i).blocks.get(0);
          break;
      }
    }
      if (DEBUG) System.out.println("Found singleton package to be block "+singletonInt);
      if (DEBUG) System.out.println("Building array of packages containing "+singletonInt+"...");
    
    // List which packs contain the singleton, put them in array, sort it
    ArrayList<pack> containsSingleton = new ArrayList<pack>();
    for (int i = 0; i < droplets.size(); i++) {
      p = droplets.get(i);
      if (methods.in_array(p.blocks, singletonInt)) {
          containsSingleton.add(p);
      }
    }
if (DEBUG) System.out.println("1 SIZE of containsSingleton is "+containsSingleton.size());
    
    // list contains.. without sort
    if (DEBUG) System.out.println("");
      if (DEBUG) System.out.println("Unsorted singleton subset:");
      for (int i = 0; i < containsSingleton.size(); i++) {
          if (DEBUG) System.out.println(containsSingleton.get(i));
      }
      
      Collections.sort(containsSingleton, new blockCompare());
    
    // list contains.. WITH sort
      if (DEBUG) System.out.println("");
      if (DEBUG) System.out.println("Sorted singleton subset:");
      for (int i = 0; i < containsSingleton.size(); i++) {
          if (DEBUG) System.out.println(containsSingleton.get(i));
      }
      if (DEBUG) System.out.println("");
      
      // remove first entry as it's the singleton itself, add it to HashMap
      pack singleton = containsSingleton.get(0);
      byte[] singletonByte = singleton.data;
      containsSingleton.remove(singleton);
      droplets.remove(singleton);
      aaData.put(singletonInt, singleton.data);
      
      // take first entry, make sure it has two blocks, xor it
if (DEBUG) System.out.println("2 SIZE of containsSingleton is "+containsSingleton.size());
      if (containsSingleton.size() == 0){
          System.out.println("BAD NEWS:\nThere are no packages that link to the singleton. Chunk size and number to drop are likely too large. Try again.");
          System.exit(1);
      }
      pack d1 = containsSingleton.get(0);
      if (d1.blocks.size() == 2) {
          byte[] d1Byte = d1.data;
          byte[] firstXORbyte = xor(singletonByte, d1Byte);
          // to add it to associative array (HashMap), remove the singleton block first
          // key code here
          
          if (DEBUG) {
              System.out.println("Reverse engineering:");
              System.out.println("\t\t "+meth.displayByte(singletonByte));
              System.out.println("\t\t "+meth.displayByte(d1Byte));
              System.out.println("---------------------");
              System.out.println("FirstXORbyte\t "+meth.displayByte(firstXORbyte));              
          }
          
          droplets.remove(d1); // remove from superset
          d1.blocks.remove(singletonInt); // isolate what we're decoding
          aaData.put(d1.blocks.get(0), firstXORbyte); // add it to associative array
          containsSingleton.remove(d1); // remove it from subset
      }else{
          System.out.println("Error, you have no package with 2 blocks that matches the singleton");
      }
      
      // the next entries dont have to have exactly 2 entries, so new block of code
      // Keep looping until nothing has been decoded, which means nothing CAN be decoded
      // LOOPING CONTAINS SINGLETON
      boolean changed = false;
      ArrayList<Integer> matches = new ArrayList<Integer>();
      do {
          changed = false;
          // Can we decode a new block? Subtract all blocks stores in aa from those in d1. 1 left?
          //TODO, LEFTOFF
          for (int i = 0; i < containsSingleton.size(); i++) {
              matches.clear();
              d1 = containsSingleton.get(i);
              // loop through each block contained by the selected pack d1
              for (int j = 0; j < d1.blocks.size(); j++) {
                  if (aaData.containsKey(d1.blocks.get(j))) {
                      matches.add(d1.blocks.get(j));
                  }                  
              }
              // if the difference between the # of blocks MINUS how many match in aa, we can decode it
              if (d1.blocks.size() - matches.size() == 1) {
                  // WE CAN DECODE THIS
                  changed = true;
                  //remove it from superset droplets before modifying
                  droplets.remove(d1);
                  // remove all, so we're just left with the one to decode
                  d1.blocks.removeAll(matches); 
                  
                  // loop through matches array and xor all of them, then with d1's data
                  // TODO: what if there are zero matches
                  byte[] combinedXOR = new byte[numBytes]; 
                  if (matches.size() == 1) {
                      // there's only one XOR to do
                      byte[] temp0 = new byte[numBytes];
                      temp0 = aaData.get(matches.get(0));
                      combinedXOR = temp0;
                      matches.remove(0);
                  }else{
                      // there are multiple packs to XOR
                      byte[] temp1 = new byte[numBytes];
                      byte[] temp2 = new byte[numBytes];
                      temp1 = aaData.get(matches.get(0));
                      byte[] first = temp1;
                      byte[] second;
                      matches.remove(0);
                      do {
                          temp2 = aaData.get(matches.get(0));
                          second = temp2;
                          Integer inter = 0; //intermediate
                          first = xor(first, second); // always converts it into int first
                          matches.remove(0);
                      } while (matches.size() > 0);
                      combinedXOR = first;
                      // TODO the above is super temporary
                  }
                  byte[] finalXORbyte = xor(d1.data, combinedXOR);
                  aaData.put(d1.blocks.get(0), finalXORbyte);
                  containsSingleton.remove(d1);
              }

          }
      } while (changed);
      if (DEBUG) System.out.println("Finished Decoding all we can from Singleton List");
      
      
      // print out associative array (HashMap) for debugging
      if (DEBUG) System.out.println("HashMap after singleton subset: "+aaData.toString());
      
      if (DEBUG) System.out.println("");
      if (DEBUG) System.out.println("Singleton list after singleton decoding:");
      for (int i = 0; i < containsSingleton.size(); i++) {
          if (DEBUG) System.out.println(containsSingleton.get(i));
      }
      
      if (DEBUG) System.out.println("");
      if (DEBUG) System.out.println("Unsorted droplets list left to decode:");
      for (int i = 0; i < droplets.size(); i++) {
          if (DEBUG) System.out.println(droplets.get(i));
      }
      
      Collections.sort(droplets, new blockCompare());
      
      if (DEBUG) System.out.println("");
      if (DEBUG) System.out.println("Sorted droplets list after left to decode:");
      for (int i = 0; i < droplets.size(); i++) {
          if (DEBUG) System.out.println(droplets.get(i));
      }
      
// go through and decode all we can from the Droplets list now
        do {
            changed = false;
          // Can we decode a new block? Subtract all blocks stores in aa from those in d1. 1 left?
          for (int i = 0; i < droplets.size(); i++) {
              matches.clear();
              d1 = droplets.get(i);
              
              //KEEP BELOW, GOOD FOR DEBUGGING
//              System.out.println("d1 is "+meth.displayByte(d1.data));
//              System.out.println("d1 is "+meth.byteArr2String(d1.data));
              // loop through each block contained by the selected pack d1
              for (int j = 0; j < d1.blocks.size(); j++) {
                  if (aaData.containsKey(d1.blocks.get(j))) {
                      matches.add(d1.blocks.get(j));
                  }                  
              }
              // if the difference between the # of blocks MINUS how many match in aa, we can decode it
              if (d1.blocks.size() - matches.size() == 1) {
                  // WE CAN DECODE THIS
                  changed = true;
                  //remove it from superset droplets before modifying
                  droplets.remove(d1);
                  // remove all, so we're just left with the one to decode
                  d1.blocks.removeAll(matches); 
                  
                  // loop through matches array and xor all of them, then with d1's data
                  // TODO: what if there are zero matches
                  byte[] combinedXOR = new byte[numBytes];
                  if (matches.size() == 1) {
                      //System.out.println("only one xor");
                      // there's only one XOR to do
                      byte[] temp0 = new byte[numBytes];
                      temp0 = aaData.get(matches.get(0));
                      combinedXOR = temp0;
                      matches.remove(0);
                  }else{
                      // there are multiple packs to XOR
                      byte[] temp1 = new byte[numBytes];
                      byte[] temp2 = new byte[numBytes];
                      temp1 = aaData.get(matches.get(0));
                      byte[] first = temp1;
                      byte[] second;
                      matches.remove(0);
                      do {
                          temp2 = aaData.get(matches.get(0));
                          second = temp2;
                          Integer inter = 0; //intermediate
                          first = xor(first, second); // always converts it into int first
                          matches.remove(0);
                      } while (matches.size() > 0);
                      combinedXOR = first;
                      // TODO this above needs work
                  }
                  byte[] finalXORbyte = xor(d1.data, combinedXOR);

                  aaData.put(d1.blocks.get(0), finalXORbyte);
                  droplets.remove(d1);
              }

          }
      } while (changed);      
// end droplet decoding
      
      //get ready to return a byte array to main()
      byte[] ret = new byte[numBytes * aaData.size()];
      int retInd = 0;
      
      // print out associative array (Treemap) for debugging
      if (DEBUG) System.out.println("size of data is "+aaData.size());
      
//      System.out.println("TEMP size is "+aaData.size());
//      Integer removeLast = aaData.lastKey();
//      System.out.println("lastkey = "+removeLast);
//      removeLast = aaData.floorKey(removeLast);
//      System.out.println("floorKey = "+removeLast);
//      aaData.remove(removeLast);
//      removeLast = aaData.lastKey();
//      aaData.remove(removeLast);
//      System.out.println("TEMP size is "+aaData.size());
//      
//      System.out.println("TEMP finished successfully removing last one");
      
      
      byte[] tmpByte = null;
        for (Map.Entry<Integer, byte[]> entry : aaData.entrySet())
        {
            tmpByte = entry.getValue();
//            System.out.println("TEMP !!!!!!!!!!!! "+entry.getKey());
            
            if (DEBUG) System.out.println("aaData["+entry.getKey()+"] => " + meth.byteArr2String(tmpByte));
            for (int i = 0; i < tmpByte.length; i++) {
                ret[retInd] = tmpByte[i];
                retInd++;
            }
        }

      //pause();
      
      if (DEBUG) System.out.println("");
      if (droplets.isEmpty()){
          if (DEBUG) System.out.println("Successfully decoded all packages.");
      }
      else{
          if (DEBUG) System.out.println("Not able to decode / leftover packages:");
          for (int i = 0; i < droplets.size(); i++) {
              if (DEBUG) System.out.println(droplets.get(i));
          }
      }
      
      // clean up the extra nulls at the end of it. 
      // dont worry if you dont understand this
      int minused = ret.length-numBytes;
      byte[] nullfreeRet = new byte[minused];
      for (int i = 0; i < minused; i++) {
          nullfreeRet[i] = ret[i];
      }      
      
      return nullfreeRet;
  }
  
  public static void pause(){
      System.out.println("\nPress Enter to continue...");
       Scanner in = new Scanner(System.in);
       String name = in.nextLine();
  }
  
  public static byte xor(byte a, byte b){ // deals with individual byte primitives
      Integer xorint = a ^ b;
      byte ret = xorint.byteValue();
      return ret;
  }
  
  /* We are assuming that both byte arrays have the same length */
  public static byte[] xor(byte[] a, byte[] b){ // deals with byte arrays
      //int condition = Math.max(a.length, b.length);
      byte[] ret = new byte[a.length];
      Integer tmp;
      for (int i = 0; i < a.length; i++) {
          tmp = a[i] ^ b[i];
          //System.out.println("tmp is "+tmp);
          ret[i] = tmp.byteValue();
          //System.out.println("tmp is now ret "+meth.displayByte(ret[i]));
      }
      return ret;
  }
  
 boolean areFilesEqual(File f1, File f2) throws IOException {
	 // compare file sizes
	 if (f1.length() != f2.length())
	 return false;

	 // read and compare bytes pair-wise
	 InputStream i1 = new FileInputStream(f1);
	 InputStream i2 = new FileInputStream(f2);
	 int b1, b2;
	 do {
             b1 = i1.read();
             b2 = i2.read();
	 } while (b1 == b2 && b1 != -1);
         
	 i1.close();
	 i2.close();

	 // true only if end of file is reached
	 return b1 == -1;
 }  
} 