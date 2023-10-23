package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public final class FileRead {
    private final File file;

    public FileRead(File file) {
        this.file = file;
    }

    public String predicate(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
            int data;
            while ((data = i.read()) != -1) {
                if (filter.test((char) data)) {
                    output.append((char) data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output.toString();
    }

    public String getContent() {
        return predicate(filter -> true);
    }

    public String getContentWithoutUnicode() {
        return predicate(filter -> filter < 0x80);
    }

}
