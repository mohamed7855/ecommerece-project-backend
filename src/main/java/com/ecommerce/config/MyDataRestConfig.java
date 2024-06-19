package com.ecommerce.config;

import com.ecommerce.entity.Country;
import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductCategory;
import com.ecommerce.entity.State;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {

    private EntityManager entityManager;

    @Autowired
    public MyDataRestConfig(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod [] theUnSupportedMethods = { HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE };

        // disable HTTP methods for Product: PUT, POST and DELETE
        disableHttpMethods(Product.class, config, theUnSupportedMethods);

        // disable HTTP methods for ProductCategory: PUT, POST and DELETE
        disableHttpMethods(ProductCategory.class, config, theUnSupportedMethods);

        // disable HTTP methods for Country: PUT, POST and DELETE
        disableHttpMethods(Country.class, config, theUnSupportedMethods);

        // disable HTTP methods for State: PUT, POST and DELETE
        disableHttpMethods(State.class, config, theUnSupportedMethods);

        // call an internal helper method
        exposeIds(config);

    }

    private void disableHttpMethods(Class theClass, RepositoryRestConfiguration config, HttpMethod[] theUnSupportedMethods) {
        config.getExposureConfiguration()
                .forDomainType(theClass)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedMethods));
    }

    private void exposeIds(RepositoryRestConfiguration config) {

        // expose entity ids
        //

        // - get a list of all entity classes from the entity manager
        Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();

        // - create an array of the entity types
        List<Class> entityClasses = new ArrayList<>();

        // - get the entity types for the entities
        for (EntityType tempEntityType : entities) {
            entityClasses.add(tempEntityType.getJavaType());
        }

        // - expose the entity ids for the array of entity/domain types
        Class[] domainTypes = entityClasses.toArray(new Class[0]);
        config.exposeIdsFor(domainTypes);
    }

}