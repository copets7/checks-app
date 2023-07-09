package com.yarosh.library.repository.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yarosh.library.repository.api.column.ProductsColumn;
import com.yarosh.library.repository.api.entity.ProductEntity;
import com.yarosh.library.repository.api.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductsColumnConverter implements AttributeConverter<Map<ProductEntity, Integer>, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsColumnConverter.class);

    private final ObjectMapper objectMapper;

    public ProductsColumnConverter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public String convertToDatabaseColumn(Map<ProductEntity, Integer> products) {
        try {
            final List<ProductsColumn> productsColumns = products.entrySet()
                    .stream()
                    .map(this::convertToProductColumn)
                    .toList();
            return objectMapper.writeValueAsString(productsColumns);
        } catch (JsonProcessingException e) {
            LOGGER.error("Converting product entities to json failed, message: {}", e.getMessage());
            LOGGER.debug("Converting product entities to json failed", e);
            throw new RepositoryException("Converting product entities to json failed, e: {0}", e);
        }
    }

    @Override
    public Map<ProductEntity, Integer> convertToEntityAttribute(String json) {
        try {
            return objectMapper.readValue(json, new TypeReference<List<ProductsColumn>>() {
                    })
                    .stream()
                    .collect(Collectors.toMap(ProductsColumn::convertToProductEntity, ProductsColumn::quantity));
        } catch (JsonProcessingException e) {
            LOGGER.error("Converting json to product entities failed, message: {}", e.getMessage());
            LOGGER.debug("Converting json to product entities failed", e);
            throw new RepositoryException("Converting json to product entities failed, e: {0}", e);
        }
    }

    private ProductsColumn convertToProductColumn(Map.Entry<ProductEntity, Integer> entry) {
        final ProductEntity product = entry.getKey();
        return new ProductsColumn(
                product.getId(),
                product.getDescription(),
                product.getPrice(),
                product.getDiscount(),
                entry.getValue()
        );
    }
}
