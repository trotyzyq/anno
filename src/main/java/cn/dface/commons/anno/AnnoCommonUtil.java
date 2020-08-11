package cn.dface.commons.anno;


import java.lang.annotation.Annotation;

/**
 * @Author zyq
 * @Description 注解排序
 * @Date 2020/8/7 17:10
 */
public class AnnoCommonUtil {
    public static void sort(Annotation[] annotations){
//        Annotation temp = null;
//        for(int j = 0; j < annotations.length ; j++){
//            for(int i = j; i < annotations.length; i++){
//                int index = i;
//                System.out.println(1);
//                try {
//                    Method fields = annotations[i].annotationType().getDeclaredMethod("level");
//                    fields.getClass().geta
//                    System.out.println(1);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
////                if(annotations[i].annotationType() < annotations[index]){
////                    index = i;
////                }
////                temp =  annotations[j]; annotations[j] = annotations[index];annotations[index] = temp;
//            }
//        }
    }
    public static void sort(int[] x){
        int temp = 0;
        for(int j = 0; j < x.length ; j++){
            int index = 0;
            for(int i = j; i < x.length; i++){
                index = i;
                if(x[i] < x[index]){
                    index = i;
                }
            }
            temp =  x[j]; x[j] = x[index];x[index] = temp;
        }

    }
    public static void main(String[] args) {
        int[] a = {6,5,4,3};
        sort(a);
//        System.out.println(JSON.toJSONString(a));
    }
}
