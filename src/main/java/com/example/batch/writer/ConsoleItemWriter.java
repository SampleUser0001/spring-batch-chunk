package com.example.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class ConsoleItemWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        list.forEach(System.out::println);
    }
}
