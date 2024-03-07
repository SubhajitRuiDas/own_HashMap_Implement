import java.util.*;
  public class HashMapImple{
    static class MyHashMap<K,V>{
        public static final int DEFAULT_CAPACITY = 4;
        public static final float DEFAULT_LOAD_FACTOR = 0.75f;
        private int n;  // track of the hashmap size
        private class Node{
            K key;
            V value;
            Node(K key,V value){
                this.key = key;
                this.value = value;
            }
        }
        private LinkedList<Node>[] bucket;
        public MyHashMap(){
            // If any one wants to give capacity and load factor no there own then he can use this constructor by giving parameterised constructor
            initialize(DEFAULT_CAPACITY);
        }
        private void initialize(int N) {  // N -> capacity of bucket array 
            bucket = new LinkedList[N];
            for (int i = 0; i < bucket.length; i++) {
                bucket[i] = new LinkedList<Node>();  // Specify the generic type for LinkedList
            }
        }
        private int hashFunction(K key){
            int hc = key.hashCode();
            return (Math.abs(hc)) % bucket.length;
        }
        @SuppressWarnings("unchecked")
        private int searchIndex(LinkedList<Node> currBuck,K key){
            for(int i=0;i<currBuck.size();i++){
                if(currBuck.get(i).key.equals(key)){
                    return i;
                }
                
            }
            return -1;
        }
        private void reHashing(){
            LinkedList<Node>[] oldBucket = bucket;
            initialize(bucket.length*2);
            n = 0;
            for(var buck: oldBucket){
                for(var node : buck){
                    put(node.key,node.value);

                }
            }
        }
        public int capacity(){
            return bucket.length;
        }
        @SuppressWarnings("unchecked")
        public void put(K key, V value){
            int bi = hashFunction(key);
            LinkedList<Node> currBucket = bucket[bi];
            int ind = searchIndex(currBucket, key);
            if(ind == -1){
                // it that key does not exists , add new node
                Node node = new Node(key,value);
                currBucket.add(node);
                n++;
            }
            else{
                currBucket.get(ind).value = value;
            }

            // rehash
            if(n >= (bucket.length*DEFAULT_LOAD_FACTOR)){
                reHashing();
            }
        }
        public V get(K key){
            int bi = hashFunction(key);
            LinkedList<Node> currBucket = bucket[bi];
            int ind = searchIndex(currBucket,key);
            if(ind!=-1){
                return currBucket.get(ind).value;
            }
            return null;
        }
        public int size(){
            return n;
        }
        public V remove(K key){
            int bi = hashFunction(key);
            LinkedList<Node> currBucket = bucket[bi];
            int idx = searchIndex(currBucket, key);
            if(idx != -1){
                V val = currBucket.get(idx).value;
                currBucket.remove(idx);
                n--;
                return val;
            }
            return null;
        }
        
    }
    public static void main(String args[]){
      MyHashMap<String,Integer> map = new MyHashMap<>();
      map.put("Subha",75);
      map.put("kunal",60);
      map.put("Gourav",90);
      System.out.println("size is: "+map.size());
      System.out.println(map.get("Subha"));
      System.out.println(map.get("kunal"));
      System.out.println(map.get("hello"));
      System.out.println(map.remove("kunal"));
      System.out.println(map.get("kunal"));
      System.out.println("size is: "+map.size());
      map.put("A",6);
      map.put("B",2);
      System.out.println("capacity is: "+map.capacity());
      System.out.println(map.get("A"));
      System.out.println(map.size());
    }
}