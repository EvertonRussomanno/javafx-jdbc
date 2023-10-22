package com.evertonmartins.javafxjdbc.model.services;

import com.evertonmartins.javafxjdbc.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    public List<Department> findAll(){
        List<Department> list = new ArrayList<>();
        list.add(new Department(1, "Books"));
        list.add(new Department(2, "Computers"));
        list.add(new Department(3, "Home"));
        list.add(new Department(4, "Electronics"));
        list.add(new Department(5, "Gardem"));

        return list;
    }

}
