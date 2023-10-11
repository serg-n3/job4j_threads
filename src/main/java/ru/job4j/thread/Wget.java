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

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public static boolean urlValidator(String url) {
        UrlValidator defaultValidator = new UrlValidator();
        return defaultValidator.isValid(url);
    }

    @Override
    public void run() {
        var file = new File("new.xml");
        try (var in = new URL(url).openStream();
             var out = new FileOutputStream(file)) {
            var dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                var timeStart = System.nanoTime();
                out.write(dataBuffer, 0, bytesRead);
                var timeFinish = System.nanoTime();
                double realSpeed =  1024.0 / ((timeFinish - timeStart) / 1000000.0);
                var pause = realSpeed / speed;
                Thread.sleep((long) pause);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        if (!urlValidator(url)) {
            out.println("url is not valid");
        } else {
            int speed = Integer.parseInt(args[1]);
            Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        }
    }
}
