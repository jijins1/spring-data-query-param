package com.ruokki.query.repository.impl;

import com.ruokki.query.Application;
import com.ruokki.query.entity.DemoEntity;
import com.ruokki.query.entity.criteria.DemoEntityCriteria;
import com.ruokki.query.repository.DemoEntityJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
@Slf4j
class DemoEntityRepositoryImplTest {

    @Autowired
    DemoEntityRepositoryImpl demoEntityRepository;
    @Autowired
    DemoEntityJpaRepository demoEntityJpaRepository;
    private Date dateBefore;
    private Date date;
    private Date dateAfter;

    @BeforeEach
    public void setUp() throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateBefore = simpleDateFormat.parse("23/04/2021");
        date = simpleDateFormat.parse("25/04/2021");
        dateAfter = simpleDateFormat.parse("28/04/2021");

        final DemoEntity entity = new DemoEntity();
        entity.setId(1L);
        entity.setStringA("Absolute");
        demoEntityJpaRepository.save(entity);
        final DemoEntity entity2 = new DemoEntity();
        entity2.setId(2L);
        entity2.setStringA("Truc");
        entity2.setDate(date);
        demoEntityJpaRepository.save(entity2);
        final DemoEntity entity3 = new DemoEntity();
        entity3.setId(3L);
        entity3.setStringA("Truc2");
        demoEntityJpaRepository.save(entity3);


    }

    @Test
    public void itShouldWorkEmpty() {

        final DemoEntityCriteria demoEntityCriteria = new DemoEntityCriteria();
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(demoEntityCriteria);
        assertThat(demoEntities).hasSize(3);
    }

    @Test
    public void itShouldWorkWithParam() {
        final DemoEntityCriteria criteria = new DemoEntityCriteria().setStringA("Absolute");
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(criteria);
        assertThat(demoEntities).hasSize(1);
    }

    @Test
    public void itShouldWorkWithMultipleSameParam() {
        final DemoEntityCriteria criteria = new DemoEntityCriteria().setStringAs(Lists.newArrayList("Truc", "Truc2"));
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(criteria);
        assertThat(demoEntities).hasSize(2);
    }


    @Test
    public void itShouldWorkWithDate() {

        final DemoEntityCriteria criteria = new DemoEntityCriteria()
                .setDateStart(dateBefore)
                .setDateEnd(dateAfter);
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(criteria);
        assertThat(demoEntities).hasSize(1);
        assertThat(demoEntities.get(0).getId()).isEqualTo(2L);

    }

}