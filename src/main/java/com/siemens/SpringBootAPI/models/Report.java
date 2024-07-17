package com.siemens.SpringBootAPI.models;

import com.siemens.SpringBootAPI.FactoryDesign.Categories;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Report {

    private Map<Integer, String> allRegistereduserMap;

    private Double totalPriceOfOrders;

    private Double averagePriceOfOrders;

    private Integer totalNoOfOrders;

    private List<Categories> uniqueCategories;

}
