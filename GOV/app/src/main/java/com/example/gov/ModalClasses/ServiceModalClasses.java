package com.example.gov.ModalClasses;

public class ServiceModalClasses {
    private String ServiceName;
    private String Price;
    private String description;
    private String url;

    public ServiceModalClasses(String serviceName, String price, String description, String url) {
        this.ServiceName = serviceName;
        this.Price = price;
        this.description = description;
        this.url=url;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
