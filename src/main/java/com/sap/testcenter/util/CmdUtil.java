package com.sap.testcenter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import com.sap.testcenter.service.impl.ReportServiceImpl;

public class CmdUtil {
    static Logger logger = Logger.getLogger(ReportServiceImpl.class);
    private static final String NPM = "C:/Users/i068096/AppData/Roaming/npm/";
    public static void run(String cmd, String runPath) {
        String[] cmds = preHandler(cmd);
        ProcessBuilder pb = new ProcessBuilder(cmds);
        if (!runPath.isEmpty()) {
            pb.directory(new File(runPath));
        }
        try {
            Process p = pb.start();
            clearProcessStream(p);
            int execResult = p.waitFor();
            logger.info("Excute result: " + (execResult == 0 ? "success" : "fail"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        try {
//            Runtime rt = Runtime.getRuntime();
//            Process p = rt.exec(cmd);
//            int exitVal = p.waitFor();
//            flag = (exitVal == 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    
    public static void run(String[] cmdArr, String runPath) {
        for (int i=0; i<cmdArr.length; i++) {
            run(cmdArr[i], runPath);
        }
    }
    
    public static void run(List<String> cmdList, String runPath) {
        for (String cmd:cmdList) {
            run(cmd, runPath);
        }
    }
    
    private static String[] preHandler(String cmd) {
        String[] cmds = cmd.split(" ");
        if (cmds != null && cmds.length > 0) {
            String specialCmd = cmds[0];
            if (specialCmd.contains("grunt") || specialCmd.contains("npm")) {
                cmds[0] = NPM + specialCmd;
            }
        }
        return cmds;
    }
    
    private static void clearProcessStream(Process p) {
        clearStream(p.getInputStream());
        clearStream(p.getErrorStream());
    }
    
    private static void clearStream(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line;
        try {
            while((line = br.readLine())!=null){
                logger.info(line);
            }
            br.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

//    public static void main(String[] args) {
//        ProcessBuilder pb = new ProcessBuilder("C:/Users/i068096/AppData/Roaming/npm/grunt.cmd","test");
//        pb.directory(new File("C:/tcgit/analyticsui/"));
//        try {
//            Process p = pb.start();
//            InputStream isInput = p.getInputStream();
//            int data = 0;
//            while((data = isInput.read()) != -1) {
//                System.out.println((byte)data);
//            }
//            InputStream isErr = p.getErrorStream();
//            data = 0;
//            while((data = isInput.read()) != -1) {
//                System.out.println((byte)data);
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
}
