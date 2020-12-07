package cn.gjy.blog.framework.tool;

/**
 * @author gujianyang
 * @date 2020/12/4
 */
public class XssTool {
    private static final char[] unsafe={'\'','"','&','>','<'};
    private static final char[][] safe={"&#039;".toCharArray(),"&#34;".toCharArray(),"&#38;".toCharArray()
        ,"&#60;".toCharArray(),"&#62;".toCharArray()};

    //转换param到安全字符
    public static String encode(String str){
        if(str==null)
            return null;
        StringBuilder sb=new StringBuilder();
        int l=str.length();
        int index=0;
        char c;
        char[] arr=str.toCharArray();
        boolean replace;
        while (index<l){
            c=arr[index];
            replace=false;
            for (int i = 0; i < unsafe.length; i++) {
                if(c==unsafe[i]){
                    replace=true;
                    sb.append(safe[i]);
                    break;
                }
            }
            if(!replace){
                sb.append(c);
            }
            index++;
        }
        return sb.toString();
    }

    //转换回不安全编码
    public static String decode(String str){
        for (int i = 0; i < safe.length; i++) {
            str=str.replace(new String(safe[i]),unsafe[i]+"");
        }
        return str;
    }
}
