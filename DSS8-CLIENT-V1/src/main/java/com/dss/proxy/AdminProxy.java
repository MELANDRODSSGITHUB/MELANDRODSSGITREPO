package com.dss.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "admin-service")
public interface AdminProxy {
    @GetMapping("/dss/api/admin/{email}/{password}")
    ResponseEntity<String> doLogin(@PathVariable String email, @PathVariable String password);

    @GetMapping("/dss/api/admin/instance")
    public String getServiceInstance();
}
