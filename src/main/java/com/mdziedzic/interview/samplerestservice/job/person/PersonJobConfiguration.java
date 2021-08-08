package com.mdziedzic.interview.samplerestservice.job.person;

import com.mdziedzic.interview.samplerestservice.cache.MemoryCache;
import com.mdziedzic.interview.samplerestservice.model.Person;
import javax.sql.DataSource;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class PersonJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource datasource;
    private final MemoryCache memoryCache;

    @Bean
    ItemReader<Person> personReader() {
        return new JdbcCursorItemReaderBuilder<Person>()
            .dataSource(datasource)
            .sql("SELECT * from person")
            .rowMapper(new BeanPropertyRowMapper<>(Person.class))
            .saveState(false)
            .build();
    }

    @Bean
    public PersonProcessor personProcessor() {
        return new PersonProcessor();
    }

    @Bean
    public ItemWriter<Person> personWriter() {
        return items -> items.forEach(person -> memoryCache.put(person.getId(), person));
    }

    @Bean
    public Job personJob() {
        return jobBuilderFactory.get("personJob")
            .incrementer(new RunIdIncrementer())
            .start(personStep())
            .build();
    }

    @Bean
    public Step personStep() {
        return stepBuilderFactory.get("personStep")
            .<Person, Person>chunk(100)
            .reader(personReader())
            .processor(personProcessor())
            .writer(personWriter())
            .allowStartIfComplete(true)
            .build();
    }
}
