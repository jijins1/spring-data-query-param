# spring-data-query-param

## Introduction
Spring Data Query Param have been create to reduce boilerplate with Spring Data. 

Spring Data is a really good framework with a lot of feature. 
But have some inconveniance 

Firstly, method in RepositoryInterface can rapidly become too complexe. 

```java 
List<User> findByFirstNameAndLastNameAndBirthdateAndSize(String firstname, String lastname, Date birthDate, Long Size) //This is too long
```

 
Secondly, Spring Data predicate can be hard to use or find(Most of spring data tuto on the net use the first method). 
And in 80% of the case you will always want to do the same thing (fieldA equals/like valueB or fieldDateC is between dateD and dateE)  


## How to use
Spring Data Query Param is based on a Annotation Processor who will generate code for you. 
For each class entity (`@Entity`) annoted with `@Criteria` a corresponding implementation of `CommonCriteria` will be created.
This implementation will have a lot of methods for addinsg parameters in your search. 

This Object will be use in `CriteriaResearchRepository.searchByCriteria(CommonCriteria)` who will return you all Object matching your criteria.



## Work in progress 
This project is in progress. 
This feature will be added :
   - Research param from subEntity (using other Annotation) 
   - Put query param from controller in Criteria
   - Ignored some field
   - Add custom field
   
    