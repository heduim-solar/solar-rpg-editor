package org.solar.editor.core.compatible.war3;

import org.solar.lang.StringUtil;

/**
 * 32进制定义表
 * 0=0
 * 1=1
 * 2=2
 * 3=3
 * 4=4
 * 5=5
 * 6=6
 * 7=7
 * 8=8
 * 9=9
 * 10=a
 * 11=b
 * 12=c
 * 13=d
 * 14=e
 * 15=f
 * 16=g
 * 17=h
 * 18=i
 * 19=j
 * 20=k
 * 21=l
 * 22=m
 * 23=n
 * 24=o
 * 25=p
 * 26=q
 * 27=r
 * 28=s
 * 29=t
 * 30=u
 * 31=v
 * 32=10
 * 注： 32位对应表
 * ① 26个字母中去除【w,x,y,z】(java 的Integer.toString(int, 32) 是这样对应的)
 * 魔兽这里是可以用32位整数的 但是为了更方便编辑和查看 所以只取32位的小写字母
 */
public class War3MapIdGenerater {

    public static String idPrefix = "q";
    public static int idStart = 0;
    public static int id_index = 0;
    static {
        init();
    }

    public static void init() {
        id_index = 0;
        idStart = MapStatistics.getIdMaxInt10(idPrefix);
        //        idStart =166;//指定起始

        String idStart_32_str = idPrefix +
                StringUtil.leftPad(Integer.toString(idStart, 32), 3, '0');

        System.out.println("War3MapIdGenerater.static{}:id_start ="
                +  War3MapIdGenerater.idStart +
                "(" + idStart_32_str + ")");
    }



    public static synchronized String nextId() {
        id_index++;
        int id = idStart + id_index;
        if (id >= 32768) { //32进制 3位最大值
            throw new RuntimeException("id大于或等于32768!已超过3位32进制字符的最大值 id=" + id);
        }
        return idPrefix + StringUtil.leftPad(Integer.toString(id, 32), 3, '0');
    }

    public static synchronized String nextUnitId() {
        return nextId();
    }

    public static synchronized String nextDoodadId() {
        return nextId();
    }

    public static synchronized String nextItemId() {
        return nextId();
    }

    public static synchronized String nextAbilityId() {
        return nextId();
    }

    public static synchronized String nextBuffId() {
        return nextId();
    }

    public static synchronized String nextUpgradeId() {
        return nextId();
    }


}
