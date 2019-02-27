package DataStructures.util;

/**
 * @Author xiongyx
 * on 2019/1/5.
 */
public class CommonUtil {

    public static void show(Object obj){
        System.out.println(obj);
    }

    public static <T> void show(T[] array){
        for (T anArray : array) {
            System.out.print(anArray + " ");
        }
        System.out.println();
    }

    public static void printLine(int lineNum){
        for(int i=0; i<lineNum; i++){
            System.out.println("======================================================");
        }
    }
}
