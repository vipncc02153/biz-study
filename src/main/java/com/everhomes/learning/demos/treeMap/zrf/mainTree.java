public class mainTree {
    public static void main(String[] args){
        treeMap t = new treeMap(1L,2L,10);
        t.put(2L,4L);
        System.out.println(t.get(2L));
        t.put(2L,6L);
        System.out.println(t.get(2L));
        t.remove(2L);
        System.out.println(t.get(2L));
    }
}
