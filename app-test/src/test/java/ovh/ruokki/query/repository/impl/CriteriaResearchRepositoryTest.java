package ovh.ruokki.query.repository.impl;

import ovh.ruokki.query.Application;
import ovh.ruokki.query.entity.DemoEntity;
import ovh.ruokki.query.entity.criteria.DemoEntityCriteria;
import ovh.ruokki.query.search.CriteriaResearchRepository;
import ovh.ruokki.query.repository.DemoEntityJpaRepository;
import ovh.ruokki.query.repository.DemoSubEntityJpaRepository;
import io.github.glytching.junit.extension.random.Random;
import io.github.glytching.junit.extension.random.RandomBeansExtension;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Application.class)
@Slf4j
@ExtendWith(RandomBeansExtension.class)
class CriteriaResearchRepositoryTest {

    @Autowired
    CriteriaResearchRepository demoEntityRepository;
    @Autowired
    DemoEntityJpaRepository demoEntityJpaRepository;
    @Autowired
    DemoSubEntityJpaRepository demoSubEntityJpaRepository;
    private Date dateBefore;
    private Date date;
    private Date dateAfter;
    @Random
    private DemoEntity entity;
    @Random
    private DemoEntity entity2;
    @Random
    private DemoEntity entity3;

    @BeforeEach
    public void setUp() throws ParseException {
        final ArrayList<DemoEntity> demoEntities = Lists.newArrayList(entity, entity2, entity3);
        demoEntityJpaRepository.deleteAll();
        demoSubEntityJpaRepository.deleteAll();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateBefore = simpleDateFormat.parse("23/04/2021");
        date = simpleDateFormat.parse("25/04/2021");
        dateAfter = simpleDateFormat.parse("28/04/2021");
        entity2.setDate(date);


        demoEntities.stream().forEach(demoEntity -> {
            demoSubEntityJpaRepository.save(demoEntity.getDemoSubEntity());
            demoEntity.getDemoSubEntityList().forEach(demoSubEntity -> demoSubEntityJpaRepository.save(demoSubEntity));
            demoEntityJpaRepository.save(demoEntity);
        });


    }

    @Test
    public void itShouldWorkEmpty() {

        final DemoEntityCriteria demoEntityCriteria = new DemoEntityCriteria();
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(demoEntityCriteria);
        assertThat(demoEntities).hasSize(3);
    }

    @Test
    public void itShouldWorkWithParam() {
        final DemoEntityCriteria criteria = new DemoEntityCriteria().setStringA(entity.getStringA());
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(criteria);
        assertThat(demoEntities).hasSize(1);
    }

    @Test
    public void itShouldWorkWithMultipleSameParam() {
        final DemoEntityCriteria criteria = new DemoEntityCriteria().setStringAs(Lists.newArrayList(entity.getStringA(), entity2.getStringA()));
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(criteria);
        assertThat(demoEntities).hasSize(2);
    }


    @Test
    public void itShouldWorkWithDate() {

        final DemoEntityCriteria criteria = new DemoEntityCriteria()
                .setDateStart(dateBefore)
                .setDateEnd(dateAfter);
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(criteria);
        assertThat(demoEntities).allSatisfy(demoEntity -> {
            assertThat(demoEntity.getDate()).isBetween(dateBefore, dateAfter);
        });

    }

    @Test
    public void itShouldWorkWithSubEntity() {

        final DemoEntityCriteria criteria = new DemoEntityCriteria()
                .setDemoSubEntityId(entity.getDemoSubEntity().getId());
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(criteria);
        assertThat(demoEntities).hasSize(1);
        assertThat(demoEntities.get(0).getId()).isEqualTo(entity.getId());

    }

    @Test
    public void itShouldWorkWithSubEntityList() {

        final DemoEntityCriteria criteria = new DemoEntityCriteria()
                .setDemoSubEntityListId(entity.getDemoSubEntityList().get(0).getId());
        final List<DemoEntity> demoEntities = demoEntityRepository.searchByCriteria(criteria);
        assertThat(demoEntities).hasSize(1);
        assertThat(demoEntities.get(0).getId()).isEqualTo(entity.getId());

    }
}