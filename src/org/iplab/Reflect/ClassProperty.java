package org.iplab.Reflect;

import java.lang.reflect.*;
import java.util.Scanner;

/**
 * Created by js982 on 2017/8/28.
 */
public class ClassProperty {

    public static void main(String[] args){
        String name;
        if(args.length > 0)
            name = args[0];
        else {
            Scanner in = new Scanner(System.in);
            System.out.println("输入（类）名字");
            name = in.next();
        }
        try{
            Class c1 = Class.forName(name);
            Class superc1 =c1.getSuperclass();
            String modifiers = Modifier.toString(c1.getModifiers());
            if(modifiers.length() > 0)
                System.out.print(modifiers+" ");
            System.out.print("Class " + name);
            if(superc1 != null && superc1 !=Object.class)
                System.out.println("extends"+superc1.getName());
            System.out.print("\n{\n");

            printConstrutors(c1);
            System.out.println();

            printMethods(c1);
            System.out.println();

            printFlieds(c1);
            System.out.println("}");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(0);
    }

    private static void printMethods(Class c1) {
        Method[] methods = c1.getDeclaredMethods();
        for(Method m : methods){
            Class retType = m.getReturnType();
            String name = m.getName();
            System.out.print("  ");
            String modifiers = Modifier.toString(m.getModifiers());
            if(modifiers.length() > 0)
                System.out.print(modifiers+" ");
            System.out.print(retType.getName() + " " + name + "(");
            Class[] paramTypes = m.getParameterTypes();
            for(int j = 0; j < paramTypes.length; j++){
                if(j > 0)
                    System.out.print(",  ");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    private static void printFlieds(Class c1) {
        Field[] fields = c1.getDeclaredFields();
        for(Field f : fields){
            Class type = f.getType();
            String name = f.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(f.getModifiers());
            if(modifiers.length() > 0)
                System.out.print(type.getName() + " " + name + ";");
        }
    }

    private static void printConstrutors(Class c1) {
        Constructor[] constructors = c1.getDeclaredConstructors();
        for(Constructor c : constructors){
            String name = c.getName();
            System.out.print("   ");
            String modifiers = Modifier.toString(c.getModifiers());
            if(modifiers.length() > 0)
                System.out.print(modifiers+" ");
            System.out.print(name+"(");

            Class[] paramTypes = c.getParameterTypes();
            for(int j = 0; j < paramTypes.length; j++){
                if(j > 0)
                    System.out.print(",");
                System.out.print(paramTypes[j].getName());
            }
            System.out.println(");");
        }
    }
}
