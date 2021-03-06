package com.future.ojekonline.controller;

import com.future.ojekonline.entity.model.Driver;
import com.future.ojekonline.entity.response.BaseResponse;
import com.future.ojekonline.service.exception.InvalidValueException;
import com.future.ojekonline.service.exception.NotFoundException;
import com.future.ojekonline.service.interf.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DriverController {

    @Autowired
    DriverService driverService;

    @GetMapping("/drivers")
    Page<Driver> getDrivers(@RequestParam(name = "page") Integer page,
                            @RequestParam(name = "limit") Integer limit,
                            @RequestParam(name = "sort") String sort) {
        return driverService.getDrivers(
                new PageRequest(page, limit, Sort.Direction.ASC, sort));
    }

    @PostMapping("/drivers")
    BaseResponse<Driver> createDriver(@RequestBody Driver driver) {
        Driver result;
        BaseResponse<Driver> response = new BaseResponse<>();
        try {
            result = driverService.createDriver(driver);
        }
        catch (InvalidValueException e) {
             response.setStatus(400);
             response.setMessage(e.getMessage());
             return response;
        }
        response.setStatus(200);
        response.setMessage("Success");
        response.setPayload(result);
        return response;
    }

    @PutMapping("/drivers/{id}")
    BaseResponse<Driver> updateDriver(@RequestBody Driver driver, @PathVariable(name = "id") String id) {
        Driver result;
        BaseResponse<Driver> response = new BaseResponse<>();
        try {
            result = driverService.updateDriver(driver);
        }
        catch (NotFoundException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setPayload(result);
        response.setMessage("Success");
        response.setStatus(200);
        return response;
    }

    @DeleteMapping("/drivers/{id}")
    BaseResponse<Driver> deleteDriver(@PathVariable(name = "id") String id) {
        BaseResponse<Driver> response = new BaseResponse<>();
        try {
            driverService.deleteDriver(id);
        }
        catch (NotFoundException e) {
            response.setStatus(404);
            response.setMessage(e.getMessage());
            return response;
        }
        response.setStatus(200);
        response.setMessage("Success");
        return response;
    }

}
