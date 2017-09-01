package org.iplab.Internet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;

/**
 * Created by js982 on 2017/8/30.
 */
public class DoPost {

    public static void main(String[] args) throws IOException {
        Properties postproperties = new Properties();
        try(InputStream in = Files.newInputStream(Paths.get(args[0]))){
            postproperties.load(in);
        }
        String url = postproperties.remove("url").toString();
        String result = dopost(url, postproperties);
        System.out.print(result);
    }

    private static String dopost(String url, Map<Object, Object>postproperties) throws IOException {
        URL urlfile = new URL(url);
        URLConnection urlConnection = urlfile.openConnection();
        urlConnection.setDoOutput(true);
        try(PrintWriter out = new PrintWriter(urlConnection.getOutputStream())){
            Boolean first = true;
            for(Map.Entry<Object, Object>pairs: postproperties.entrySet()){
                if(first)
                    first = false;
                out.print("&");
                String name = pairs.getKey().toString();
                String value = pairs.getValue().toString();

                out.print(name);
                out.print("=");
                out.print(URLEncoder.encode(value,"UTF-8"));
            }
        }

        StringBuilder response = new StringBuilder();
        try(Scanner in = new Scanner(urlConnection.getInputStream())){
            while(in.hasNextLine()){
                response.append(in.nextLine());
                response.append("\n");
            }
        }
        catch(IOException e){
            if(!(urlConnection instanceof HttpURLConnection))
                throw e;
            InputStream erro = ((HttpURLConnection)urlConnection).getErrorStream();
            if(erro == null)
                throw e;
            Scanner erroin = new Scanner(erro);
            response.append(erroin.nextLine());
            response.append("\n");
        }
        return response.toString();
    }
}
