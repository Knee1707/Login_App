package vn.iotstar.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfig {

	static {
		System.setProperty("net.bytebuddy.experimental", "true");
	}

	private static final EntityManagerFactory factory =
			Persistence.createEntityManagerFactory("CategoryCRUD");

	public static EntityManager getEntityManager() {
		return factory.createEntityManager();
	}
}
