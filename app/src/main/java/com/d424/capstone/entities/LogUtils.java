package com.d424.capstone.entities;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LogUtils {

    public static String getLogcatOutput() {
        StringBuilder log = new StringBuilder();
        try {
            Process process = new ProcessBuilder("logcat", "-d").start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line).append("\n");
            }
        } catch (Exception e) {
            log.append("Error reading logcat: ").append(e.getMessage());
        }
        return log.toString();
    }

    public static String searchLogcatOutput(String query) {
        StringBuilder log = new StringBuilder();
        try {
            Process process = new ProcessBuilder("logcat", "-d").start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(query)) {
                    log.append(line).append("\n");
                }
            }
        } catch (Exception e) {
            log.append("Error reading logcat: ").append(e.getMessage());
        }
        return log.toString();
    }
}