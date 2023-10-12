package ru.job4j.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.validator.routines.UrlValidator;

import static java.lang.System.out;

public class Wget implements Runnable {
    private final String url;
    private final int speed;
    private final String fileName;

    public Wget(String url, int speed, String fileName) {
        this.url = url;
        this.speed = speed;
        this.fileName = fileName;
    }

    public static boolean urlValidator(String url) {
        UrlValidator defaultValidator = new UrlValidator();
        return defaultValidator.isValid(url);
    }

    @Override
    public void run() {
        var file = new File(fileName);
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            int download = 0;
            var timeStart = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                download += bytesRead;
                if (download >= speed) {
                    var timeFinish = System.nanoTime();
                    long time = timeFinish - timeStart;
                    if (time < 1000) {
                        Thread.sleep(1000 - time);
                    }
                    download = 0;
                    timeStart = System.currentTimeMillis();

                }
                out.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 3) {
            out.println("You have not entered all the arguments");
        } else {
            String url = args[0];
            if (!urlValidator(url)) {
                out.println("url is not valid");
            } else {
                int speed = Integer.parseInt(args[1]);
                String fileName = args[2];
                Thread wget = new Thread(new Wget(url, speed, fileName));
                wget.start();
                wget.join();
            }
        }
    }
}
