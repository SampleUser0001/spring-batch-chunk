package com.example.batch.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.batch.processor.UpperCaseItemProcessor;
import com.example.batch.reader.StringListItemReader;
import com.example.batch.writer.ConsoleItemWriter;

@Configuration
@EnableBatchProcessing
public class BatchConfig {

    private final JobBuilderFactory jobBuilder;
    private final StepBuilderFactory stepBuilder;

    public BatchConfig(JobBuilderFactory jobBuilder, StepBuilderFactory stepBuilder) {
        this.jobBuilder = jobBuilder;
        this.stepBuilder = stepBuilder;
    }

    @Bean
    public Job exampleJob() {
        return jobBuilder.get("exampleJob")
                .incrementer(new RunIdIncrementer())
                .flow(exampleStep())
                .end()
                .build();
    }

    @Bean
    public Step exampleStep() {
        return stepBuilder.get("exampleStep")
                .<String, String> chunk(10) // チャンクサイズの設定
                .reader(itemReader())
                .processor(itemProcessor())
                .writer(itemWriter())
                .build();
    }

    // Reader, Processor, Writer のダミー実装
    public ItemReader<String> itemReader() {
        // 実際のアプリケーションでは、データソースから読み込むロジックを実装します。
        return new StringListItemReader(List.of("apple", "banana", "cherry"));
    }

    public ItemProcessor<String, String> itemProcessor() {
        // データを加工するロジックを実装します。
        return new UpperCaseItemProcessor();
    }

    public ItemWriter<String> itemWriter() {
        // 加工後のデータを書き込むロジックを実装します。
        return new ConsoleItemWriter();
    }
}
