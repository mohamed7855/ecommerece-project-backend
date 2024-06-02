package com.ecommerce.config;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.ProductCategory;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class MyDataRestConfig implements RepositoryRestConfigurer {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
        HttpMethod [] theUnSupportedMethods = { HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE };

        // disable HTTP methods for Product: PUT, POST and DELETE
        config.getExposureConfiguration()
                .forDomainType(Product.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedMethods));

        // disable HTTP methods for ProductCategory: PUT, POST and DELETE
        config.getExposureConfiguration()
                .forDomainType(ProductCategory.class)
                .withItemExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedMethods))
                .withCollectionExposure((metdata, httpMethods) -> httpMethods.disable(theUnSupportedMethods));
    }
}
