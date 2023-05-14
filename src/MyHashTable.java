public class MyHashTable<K, V> {
    private class HashNode<K, V>{
        private K key;
        private V value;
        private HashNode<K, V> next;
        // Constructor
        public HashNode(K key, V value){
            this.key = key;
            this.value = value;
        }
        @Override
        public String toString(){
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray; //or Object []
    private int M = 11; //default number of chains
    private int size;
    private final double LOAD_FACTOR_THRESHOLD = 120;
    //constructor for creating a new hash table with DEFAULT number of chains
    public MyHashTable() {
        chainArray = new HashNode[M];
        size = 0;
    }
    //constructor for creating a new hash table with a GIVEN number of chains
    public MyHashTable(int M) {
        this.M = M;
        chainArray = new HashNode[M];
        size = 0;
    }
    //hash function to map a key to an index in the chain array
    private int hash(K key) {
        if (key instanceof MyTestingClass) {
            MyTestingClass myKey = (MyTestingClass) key;
            return Math.abs(myKey.hashCode() % M);
        }
        return Math.abs(key.hashCode() % M);
    }
    //method for putting a new key-value pair
    public void put(K key, V value) {
        int index = hash(key); //calculate the index in the chain array for the given key
        HashNode<K, V> node = chainArray[index]; //get the head of the chain at the index
        //traverse the chain to check if the key already exists, if so update the value and return
        while(node != null) {
            if(node.key.equals(key)) {
                node.value = value;
                return;
            }
            node = node.next;
        }
        //if the key doesn't exist in the chain, create a new node and add it to the head of the chain
        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = chainArray[index];
        chainArray[index] = newNode;
        size++;
        // check if the load factor exceeds the threshold and resize if necessary
        double loadFactor = (double) size / M;
        if (loadFactor > LOAD_FACTOR_THRESHOLD) {
            resize(2 * M);
        }
    }
    //method for getting the value based on the key
    public V get(K key) {
        int index = hash(key); //calculate the index in the chain array for the given key
        HashNode<K, V> node = chainArray[index]; //get the head of the chain at the index
        //traverse the chain to find the node with the given key and return its value
        while(node != null) {
            if(node.key.equals(key)) {
                return node.value;
            }
            node = node.next;
        }
        return null; //if the key is not found in the chain, return null
    }
    // Method to remove a given key
    public V remove(K key) {
        int index = hash(key); //get the index of the chain to search
        HashNode<K, V> node = chainArray[index];
        HashNode<K, V> prev = null;

        //loop through the chain until the end or until the key is found
        while(node != null) {
            if(node.key.equals(key)) { //if the key is found
                if(prev == null) { //if it's the first node in the chain
                    chainArray[index] = node.next; //set the next node as the head of the chain
                } else { //if it's not the first node in the chain
                    prev.next = node.next; //skip over the current node
                }
                size--;
                return node.value; //return the value of the node that was removed
            }
            prev = node; //update the previous node pointer
            node = node.next; //move to the next node in the chain
        }
        return null; //if the key wasn't found, return null
    }
    //method to check if the given value is in the table
    public boolean contains(V value) {
        //iterate over all the chains
        for(int i=0; i<M; i++) {
            HashNode<K, V> node = chainArray[i];
            //iterate over each node in the chain
            while(node != null) {
                //if the value matches the input value, return true
                if(node.value.equals(value)) {
                    return true;
                }
                node = node.next;
            }
        }
        //if the value was not found, return false
        return false;
    }
    //method to get key based on value
    public K getKey(V value) {
        //iterate over all the chains
        for(int i=0; i<M; i++) {
            HashNode<K, V> node = chainArray[i];
            //iterate over each node in the chain
            while(node != null) {
                //if the value matches the input value, return the key of the node
                if(node.value.equals(value)) {
                    return node.key;
                }
                node = node.next;
            }
        }
        //if the value was not found, return null
        return null;
    }
    private void resize(int newSize) {
        // create a new chainArray with the new size
        HashNode<K, V>[] newChainArray = new HashNode[newSize];
        // rehash all the elements in the old chainArray into the new chainArray
        for (int i = 0; i < M; i++) {
            HashNode<K, V> node = chainArray[i];
            while (node != null) {
                K key = node.key;
                V value = node.value;
                int index = hash(key) % newSize;
                HashNode<K, V> newNode = new HashNode<>(key, value);
                newNode.next = newChainArray[index];
                newChainArray[index] = newNode;
                node = node.next;
            }
        }
        // update the chainArray and M
        chainArray = newChainArray;
        M = newSize;
    }

    public void printBucketSizes() {
        int[] bucketSizes = new int[M];

        for (int i = 0; i < M; i++) {
            HashNode<K, V> node = chainArray[i];
            int bucketSize = 0;
            while (node != null) {
                bucketSize++;
                node = node.next;
            }
            bucketSizes[i] = bucketSize;
        }

        for (int i = 0; i < M; i++) {
            System.out.println("Bucket " + i + " contains " + bucketSizes[i] + " elements");
        }
    }

}
