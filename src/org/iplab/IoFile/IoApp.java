package org.iplab.IoFile;

import java.io.*;
import java.util.Properties;

/**
 * Created by js982 on 2017/6/10.
 */
public class IoApp {
    /**
     * 流程：使用键盘敲击properties类的数据“字节流”，通过缓冲的方式来写入，并转换为“字符流”，
     * 存入.ini文件，然后通过file类显示该目录下的所有文件，并然后加载生成的文件，显示到控制台上
     */

    public void Ioapp() throws IOException {

        BufferedReader bufferedReaderin = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriterout = new BufferedWriter(new OutputStreamWriter(System.out));
        BufferedWriter bufferedWriterfile = new BufferedWriter(new FileWriter("app.ini"));
        String line = null;
        while ((line = bufferedReaderin.readLine()) != null){
            if(line.equals(".")){
                break;
            }
            bufferedWriterout.write(line);
            bufferedWriterout.flush();

            bufferedWriterfile.write(line+"\n");
            bufferedWriterfile.flush();
        }
    }


    public String fileapp(File dir){
        File[] files = dir.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".ini");
            }
        });

        for(int x =0; x < files.length; x++){
            if (files[x].isDirectory()){
                fileapp(files[x]);
            }else {
                System.out.println(files[x].getAbsolutePath());
                return files[x].getAbsolutePath();
            }
        }
        return null;
    }

    public void propertyload(String fileAbsolutePath) throws IOException {
        Properties properties = new Properties();

        FileInputStream fileInputStream = new FileInputStream(fileAbsolutePath);
        properties.load(fileInputStream);
        properties.list(System.out);
    }

    public static void main (String[] args) throws IOException {
        IoApp ioApp = new IoApp();
        File dir = new File("./");
        ioApp.Ioapp();
        String fileabsolutepath = ioApp.fileapp(dir);
        ioApp.propertyload(fileabsolutepath);
    }
}
