package com.example.batch.reader;

import org.springframework.batch.item.ItemReader;
import java.util.Iterator;
import java.util.List;

public class StringListItemReader implements ItemReader<String> {
    
    private final Iterator<String> dataIterator;

    public StringListItemReader(List<String> dataList) {
        this.dataIterator = dataList.iterator();
    }

    @Override
    public String read() throws Exception {
        if (this.dataIterator.hasNext()) {
            return this.dataIterator.next();
        } else {
            return null; // データの終わりを示すためにnullを返す
        }
    }
}

