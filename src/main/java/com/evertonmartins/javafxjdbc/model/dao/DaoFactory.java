package com.evertonmartins.javafxjdbc.model.dao;

import com.evertonmartins.javafxjdbc.db.DB;
import com.evertonmartins.javafxjdbc.model.dao.impl.DepartmentDaoJDBC;
import com.evertonmartins.javafxjdbc.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

	public static SellerDao createSellerDao() {
		return new SellerDaoJDBC(DB.getConnection());
	}
	
	public static DepartmentDao createDepartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
