package com.evertonmartins.javafxjdbc.model.services;

import com.evertonmartins.javafxjdbc.model.dao.DaoFactory;
import com.evertonmartins.javafxjdbc.model.dao.DepartmentDao;
import com.evertonmartins.javafxjdbc.model.entities.Department;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();
    public List<Department> findAll(){

//        List<Department> list = new ArrayList<>();
//        list.add(new Department(1, "Books"));
//        list.add(new Department(2, "Computers"));
//        list.add(new Department(3, "Home"));
//        list.add(new Department(4, "Electronics"));
//        list.add(new Department(5, "Gardem"));

//        return list;
        return dao.findAll();
    }

    public void saveOrUpdate(Department obj){
        if (obj.getId() == null){
            dao.insert(obj);
        }else {
            dao.update(obj);
        }
    }
}
