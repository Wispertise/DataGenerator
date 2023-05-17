package com.cgl.utils;

import org.apache.poi.ss.formula.functions.T;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: SunnyDeer
 * @time: 2023/4/26
 */
public class TxtWriterUtil {

    public static void writeIntoTxt(String filePath, ArrayList<ArrayList<String>> arrayLists, List<String> headers, int n,String userid){
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        filePath = filePath+userid+".txt";
        try {
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter buffer = new BufferedWriter(writer);
            for(String header : headers){
                buffer.write(header);
                buffer.write(" ");
            }
            buffer.newLine();
            for (int i = 0;i<n;i++) {
                for (int j = 0;j<headers.size();j++) {
                    buffer.write(arrayLists.get(j).get(i));
                    buffer.write(" ");
                }
                buffer.newLine();
            }
            buffer.close();
            writer.close();
            System.out.println("write into txt success" + filePath);
        } catch (IOException e) {
            System.out.println("write into txt filde:" + e.getMessage());
        }
    }

}
